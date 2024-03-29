/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author javier.arufe
 */
public class Utils {
    public static final String ENCODING_UTF_8 = "UTF-8";
    public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";
    
    final static int[] daysmonth = {29,31,28,31,30,31,30,31,31,30,31,30,31};

    public Utils() {
    }

/**
 * Create a dummy event with one dummy stage while we don't have connection
 * to the backend to get real or test values
 * @return eu.oreplay.db.Event Dummy event with one dummy stage
 */
public static eu.oreplay.db.Event createDummyEventOneStage () {
    eu.oreplay.db.Event voEveSrc = null;
    try {
        //Create a basic event and stage information
        voEveSrc = new eu.oreplay.db.Event();
        voEveSrc.setId("8f3b542c-23b9-4790-a113-b83d476c0ad9");
        voEveSrc.setDescription("First CSV start list, event");
        voEveSrc.setCreated(new java.util.Date());
        //The stage
        eu.oreplay.db.Stage voSta = new eu.oreplay.db.Stage();
        voSta.setId("51d63e99-5d7c-4382-a541-8567015d8eed");
        voSta.setOrderNumber(1);
        voSta.setDescription("First CSV start list, stage");
        voSta.setBaseDate(Utils.parse("01/02/2024", "dd/MM/yyyy"));
        voSta.setBaseTime(Utils.parse("11:00:00", "HH:mm:ss"));
        voSta.setCreated(new java.util.Date());
        //Add the stage to the event
        ArrayList<eu.oreplay.db.Stage> vlSta = new ArrayList<eu.oreplay.db.Stage>();
        vlSta.add(voSta);
        voEveSrc.setStageList(vlSta);
    }catch(Exception e) {
        voEveSrc = null;
    }
    return voEveSrc;
}
    
/**
 * Converts a string with hexadecimal representation to a byte array
 * @param s String String with hexadecimal representation
 * @return byte[] byte array
 */
public static byte[] hexStringToByteArray(String s) {
    byte[] data = null;
    try {
        int len = s.length();
        data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
    }catch(Exception e) {
        data = null;
    }
    return data;
}    
/**
 * Converts a byte array to a string with hexadecimal representation
 * @return String Sring with hexadecimal representation
 */
public static String byteArrayToHexString(byte[] paRaw) {
    String vcResul = "";
    try {
        if (paRaw!=null) {
            for (int i=0; i < paRaw.length; i++) {
                vcResul +=
                    (Integer.toString( ( paRaw[i] & 0xff ) + 0x100, 16).substring( 1 )).toUpperCase();
            }
        }
    }catch(Exception e) {
        vcResul = "";
    }
    return vcResul;
}

/**
 * Converts a Base64 string to a byte array
 * @param s String Base64 String
 * @return byte[] byte array
 */
public static byte[] base64ToByteArray(String s) {
    byte[] data = null;
    try {
        data = java.util.Base64.getDecoder().decode(s);
    }catch(Exception e) {
        data = null;
    }
    return data;
}    

/**
 * Converts a byte array to a Base64 string, for example to insert an image in an HTML page
 * @return String Base64 representation of a byte array
 */
public static String byteArrayToBase64(byte[] paRaw) {
    String vcResul = "";
    try {
        if (paRaw!=null) {
            vcResul = java.util.Base64.getEncoder().encodeToString(paRaw);
        }
    }catch(Exception e) {
        vcResul = "";
    }
    return vcResul;
}

/**
 * Gets to Strings with date values and compares them
 * @return boolean. Flag that indicates if the first date is greater than the second (true en this case)
 * @param format String. Date format of the input strings
 * @param dateMin String. Text of the first date using the format in the first parameter
 * @param dateMax String. Text of the second date using the format in the first parameter
 */
public static boolean compareDate(String format, String dateMin, String dateMax) throws Exception{
    if (dateMin == null || dateMax == null || dateMin.equals("") || dateMax.equals(""))
        return true;
    else {
        String voFormatAux = "yyyyMMddHHmm";
        long firstDate = Long.parseLong(transform(dateMin, format, voFormatAux).toString());
        long secondDate = Long.parseLong(transform(dateMax, format, voFormatAux).toString());
        if (firstDate > secondDate)
            return false;
        else
            return true;
    }
}

/**
 * Returns the week day of the first day of a month
 * @param year int
 * @param month int
 */
public static int GetIniDay(int year, int month) {
    if (month < 0 || month > 12)
        return -1;
    int inter = 365*(year - 1980) + (year - 1981)/4;
    inter = inter + 2 ; //increase
    if(month>1) { for (int i=1; i<month; i++){
        inter = inter + daysmonth[i];}
    }
    int vnResul =  inter % 7 ;
    return vnResul;
}
/**
 * Gets the number of days of a month
 * @param year int
 * @param month int
 */ 
public static int GetDaysMonth(int year,int month){
    if (month < 0 || month > 12)
        return -1;
    if (year%4 == 0) {
        daysmonth[2]=29;
    }
    else {
        daysmonth[2]=28;
    }
    return daysmonth[month];
}
/**
 * Returns if one date is inside bounds of another two dates
 * @return boolean. Flag that indicates if first date is inside the interval (bounds included)
 * @param date java.util.Date. Date to compare
 * @param dateMin java.util.Date. lower bound of the interval
 * @param dateMax java.util.Date. upper bound of the interval
 */
public static boolean dateInInterval(Date date, Date dateMin, Date dateMax) throws Exception{
    if (date == null || dateMin == null || dateMax == null || dateMin.equals("") || dateMax.equals("") || dateMin.equals(""))
        throw new Exception("Some empty date");
    else {
        String voFormatAux = "yyyyMMdd";
        int nDate = Integer.parseInt(format(date, voFormatAux).toString());
        int firstDate = Integer.parseInt(format(dateMin, voFormatAux).toString());
        int secondDate = Integer.parseInt(format(dateMax, voFormatAux).toString());
        if (nDate >= firstDate && nDate <= secondDate)
            return true;
        else
            return false;
    }
}
/**
 * Returns current system's date
 * @return java.util.Date System's date
 * @exception java.lang.Exception Exception
 */
public static java.util.Date getCurDate() {
    java.util.Date vdCurrent = null;
    try {
        vdCurrent = new java.util.Date();
    } catch (Exception e1) {
        vdCurrent = null;
    }
    return vdCurrent;
}
/**
 * Returns the current date formatted to be used in GPS, that's say, "yyyy-mm-ddTHH:mm:ss.S"
 * @return String Current date
 */
public static String getDateTimeForGpx () {
    String vcResul = "";
    try {
        java.util.Date vdCurrent = Utils.getCurDate();
        String vcDate = Utils.format(vdCurrent, "yyyy-MM-dd");
        String vcTime = Utils.format(vdCurrent, "HH:mm:ss.S");
        vcResul = vcDate + "T" + vcTime;
    } catch (Exception e) {
        vcResul = "";
    }
    return vcResul;
}
/**
 * Giving a time in milliseconds, it returns a String with the format of NMEA, hhmmss
 * @return String Time in the required format
 */
public static String getNMEAFromMillisec (long pnMilis) {
    String vcResul = "";
    try {
        SimpleDateFormat df2 = new SimpleDateFormat ("HHmmss");
        df2.setLenient(false);
        Date vdValue = new Date(pnMilis);
        vcResul = df2.format(vdValue);
    } catch (Exception e) {
        vcResul = "";
    }
    return vcResul;
}
/**
 * Giving two times in format HH:mm:ss, this method substracts the values and returns
 * the difference in format HH:mm:ss too
 * @param pcFinish java.lang.String. Finish time, in format HH:mm:ss
 * @param pcStart java.lang.String. Start time, in format HH:mm:ss
 * @return String. Substracted time, in format HH:mm:ss
 */
public static String substractTimes(String pcFinish, String pcStart) {
    String vcResul = "";

    SimpleDateFormat df1 = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
    SimpleDateFormat df2 = new SimpleDateFormat ("HH:mm:ss");
    df1.setLenient(false);
    df2.setLenient(false);
    try {
        Date vdValue2 = df1.parse ("01/01/2000 " + pcFinish);
        Date vdValue1 = df1.parse ("01/01/2000 " + pcStart);
        Date vdResul = new Date(vdValue2.getTime()-vdValue1.getTime()-3600000);
        vcResul = df2.format(vdResul);
    } catch (Exception e){
        vcResul = "";
    }

    return vcResul;
}

/**
 * Giving a date and a format, this method returns a string with the representation of the date in that format
 * @return java.lang.String
 * @param pdValue java.util.Date
 * @param pcFormat java.lang.String
 * @exception java.lang.Exception Exception thrown
 */
public static String format(Date pdValue, String pcFormat) throws java.lang.Exception {
    String vcResul = "";
    try {
        SimpleDateFormat df = new SimpleDateFormat (pcFormat);
        df.setLenient(false);
        vcResul = df.format(pdValue);
    }catch(Exception e) {
        vcResul = "";
    }
    return vcResul;
}
/**
 * Giving a String that represents a distance, returns a value in meters
 * <BR>
 * Format can be "00.00Km" or "00.00m" or "00,00 Km", etc
 * @param pcValue String. Distance as a string
 * @return double. Value in meters
 */
public static double formatStringAsDistanceInMeters (String pcValue) {
    double vnResul = 0;
    int vnFactor = 1;
    int vnInd = -1;
    String vcSub = "";
    try {
        if (pcValue!=null) {
            //Only searching km and m
            vcSub = pcValue.toLowerCase().trim();
            vnInd = vcSub.indexOf("km");
            if (vnInd>0)
                vnFactor = 1000;
            else {
                vnInd = vcSub.indexOf("m");
            }
            //Gets the first part of the string, the distance
            if (vnInd>0) {
                vcSub = vcSub.substring(0, vnInd);
            }
            //Replace comma by point
            vcSub = vcSub.replaceAll(",", ".");
            vnResul = Double.parseDouble(vcSub)*vnFactor;
        }
    } catch (Exception e) {
        vnResul = 0;
    }
    return vnResul;
}
/**
 * Giving a String that contains a valid time HH:mm:ss,
 * it returns a value in seconds
 * @param pcValue String. Time in HH:mm:ss
 * @return long. Value in seconds
 */
public static long formatTimeInSeconds (String pcValue) {
    long vnResul = 0;
    try {
        if (pcValue!=null) {
            if (pcValue.length()==8) {
                String vcHours = pcValue.substring(0, 2);
                String vcMinutes = pcValue.substring(3, 5);
                String vcSeconds = pcValue.substring(6, 8);
                vnResul = (Integer.parseInt(vcHours)*3600) + 
                            (Integer.parseInt(vcMinutes)*60) +
                            Integer.parseInt(vcSeconds);
            }
        }
    } catch (Exception e) {}
    return vnResul;
}
/**
 * Giving a long that contains a value of a time in milliseconds,
 * it returns a String in format HH:mm:ss
 * @param pnTimeMilli long. Time value in milliseconds
 * @return String. HH:mm:ss
 */
public static String formatLongInTime (long pnTimeMilli) {
    String vcResul = "";
    int vnSeconds = 0;
    int vnMinutes = 0;
    int vnHours = 0;
    long vnTimeInSeconds = pnTimeMilli / 1000;
    int vnParte1 = (int)vnTimeInSeconds/60;
    if (vnParte1>=60) {
        vnHours = vnParte1/60;
        vnMinutes = vnParte1%60;
    } else {
        vnMinutes = vnParte1;
    }
    vnSeconds = (int)vnTimeInSeconds%60;
    if (vnSeconds<10)
        vcResul = "0" + vnSeconds;
    else
        vcResul = vnSeconds + "";
    if (vnMinutes<10)
        vcResul = "0" + vnMinutes + ":" + vcResul;
    else
        vcResul = vnMinutes + ":" + vcResul;
    if (vnHours<10)
        vcResul = "0" + vnHours + ":" + vcResul;
    else
        vcResul = vnHours + ":" + vcResul;    
    return vcResul;
}
/**
 * Giving a long that contains a value of a time in milliseconds,
 * it returns a String in format 0d 0h 0m 0s
 * @param pnTimeMilli long. Time value in milliseconds
 * @param pcLetterDays String. Letter for the days
 * @param pcLetterHours String. Letter for the hours
 * @param pcLetterMinutes String. Letter for the minutes
 * @param pcLetterSeconds String. Letter for the seconds
 * @return String. 0d 0h 0m 0s
 */
public static String formatLongInDaysHoursMinutesSeconds (long pnTimeMilli, String pcLetterDays,
        String pcLetterHours, String pcLetterMinutes, String pcLetterSeconds) {
    String vcResul = "";
    try {
        if (pnTimeMilli>0) {
            long vnTimeInSeconds = pnTimeMilli / 1000;
            int vnHoras = 0;
            int vnMinutos = 0;
            int vnSegundos = 0;
            int vnMinutosEnteros = (int)(vnTimeInSeconds / 60);
            int vnHorasEnteros = (int)(vnMinutosEnteros / 60);
            int vnDias = (int)(vnHorasEnteros / 24);
            vnSegundos = (int)vnTimeInSeconds%60;
            vnMinutos = (int)vnMinutosEnteros%60;
            vnHoras = (int)vnHorasEnteros%24;
            if (vnDias>0)
                vcResul = vnDias + pcLetterDays + " ";
            vcResul = vcResul + vnHoras + pcLetterHours;
            vcResul = vcResul + " " + vnMinutos + pcLetterMinutes;
            vcResul = vcResul + " " + vnSegundos + pcLetterSeconds;
        }
    }catch(Exception eTime) {
        vcResul = "";
    }
    return vcResul;
}
/**
 * Giving a relative time as a string (starting from 00:00:00), it returns a Date object
 * with the value of the time from a base datetime
 * @param pcSrc String Relative time from 00:00:00
 * @param poBaseDate Date Date's part of a base datetime
 * @param poBaseTime Date Time's part of a base datetime
 * @return Date Calulated value adding the relative time to the base datetime
 */
public static Date formatRelativeTimeFromBase (String pcSrc, Date poBaseDate, Date poBaseTime) {
    Date vdResul = null;
    try {
        //Gets the date part of the base value
        String vcDate = Utils.format(poBaseDate, "yyyy-MM-dd");
        if (vcDate.equals(""))
            vcDate = "2001-01-01";
        //Gets the time part of the base value
        String vcTime = Utils.format(poBaseTime, "HH:mm:ss");
        if (vcTime.equals(""))
            vcTime = "10:30:00";
        //Compose a Date with the Date and Time of the base value
        Date vdBase = Utils.parse(vcDate + " " + vcTime, "yyyy-MM-dd HH:mm:ss");
        //Call another method to do the calculation
        vdResul = formatRelativeTimeFromBase (pcSrc, vdBase);
    } catch (Exception e) {
        vdResul = null;
    }
    return vdResul;
}
/**
 * Giving a relative time as a string (starting from 00:00:00), it returns a Date object
 * with the value of the time from a base datetime
 * @param pcSrc String Relative time from 00:00:00
 * @param poBaseDateTime Date Base datetime
 * @return Date Calulated value adding the relative time to the base datetime
 */
public static Date formatRelativeTimeFromBase (String pcSrc, Date poBaseDateTime) {
    Date vdResul = null;
    long vnTime = 0;
    try {
        long vnBase = poBaseDateTime.getTime();
        if (!pcSrc.equals("")) {
            //Converts time to milliseconds
            String[] vaSrc = pcSrc.split(":");
            if (vaSrc.length==3) {
                vnTime = vnTime + Long.parseLong(vaSrc[0].replaceAll("\"", ""))*3600000;
                vnTime = vnTime + Long.parseLong(vaSrc[1].replaceAll("\"", ""))*60000;
                vnTime = vnTime + Long.parseLong(vaSrc[2].replaceAll("\"", ""))*1000;
            } else if (vaSrc.length==2) {
                vnTime = vnTime + Long.parseLong(vaSrc[0].replaceAll("\"", ""))*60000;
                vnTime = vnTime + Long.parseLong(vaSrc[1].replaceAll("\"", ""))*1000;
            } else if (vaSrc.length==1) {
                vnTime = vnTime + Long.parseLong(vaSrc[0].replaceAll("\"", ""))*1000;
            }
            long vnResul = vnBase + vnTime;
            vdResul = new Date(vnResul);
        }
    } catch (Exception e) {
        vdResul = null;
    }
    return vdResul;
}

/**
 * Giving an integer, it returns a String with format +00:00 to indicate an UTC offset
 * @param pnUtc int. Offset value as integer (-1, 0, 2, etc)
 * @return String. +nn:00
 */
public static String formatUTC (int pnUtc) {
    String vcResul = "+";
    String vcTens = "0";
    if (pnUtc>23 || pnUtc<-23)
        vcResul = "+00:00";
    else {
        if (pnUtc<0)
            vcResul = "-";
        if (pnUtc<-9 || pnUtc>9)
            vcTens = "";
        vcResul = vcResul + vcTens + pnUtc + ":00";
    }
    return vcResul;
}
/**
 * Giving a String with format +00:00 to indicate an UTC offset, it returns an integer
 * @param pcUtc String. +nn:nn
 * @return int. Value of the offset as integer (-1, 0, 2, etc)
 */
public static int parseUTC (String pcUtc) {
    int vnResul = 0;
    try {
        String[] vaValue = pcUtc.split(":");
        if (vaValue.length>0)
            vnResul = Integer.parseInt(vaValue[0]);
    }catch (Exception e) {
        vnResul = 0;
    }
    return vnResul;
}
/**
 * Giving an String object that contains a date value and a format, it returns a Date object representing the value
 * @return java.util.Date
 * @param pcDate java.lang.String
 * @param pcFormat java.lang.String
 * @exception java.lang.Exception Exception
 */
public static Date parse(String pcDate, String pcFormat) throws java.lang.Exception {
    java.util.Date vdResul = null;
    try {
        SimpleDateFormat df = new SimpleDateFormat (pcFormat);
        df.setLenient(false);
        vdResul = df.parse(pcDate);
    }catch(Exception e) {
        vdResul = null;
    }
    return vdResul;
}
/**
 * Giving a String that contains a date and two formats (source and target), 
 * this method returns the same initial date but transformed between source and target formats;
 * @return java.lang.String Text with the date transformed to the second format
 * @param pcText java.lang.String. Text with a date value in the first format
 * @param pcFormat1 java.lang.String. Source format. For example: DD/MM/YYYY
 * @param pcFormat2 java.lang.String. Target format. For example: YYYY-MM-DD
 * @exception java.lang.Exception. Exception
 */
public static String transform(String pcText, String pcFormat1, String pcFormat2) throws java.lang.Exception {
    String vcResul = "";

    SimpleDateFormat df1 = new SimpleDateFormat (pcFormat1);
    SimpleDateFormat df2 = new SimpleDateFormat (pcFormat2);
    Date vdValue = new Date();
    df1.setLenient(false);
    df2.setLenient(false);
    try {
        vdValue = df1.parse(pcText);
        vcResul = df2.format(vdValue);
    } catch (Exception e){
        vcResul = "";
    }

    return vcResul;
}

/**
 * Reads the content of a file and returns a byte array
 * @param poFile File. Input file
 * @return byte[]. Output byte array
 * @throws IOException
 */
public static byte[] getBytesFromFile(File poFile) throws IOException {
    InputStream voIs = new FileInputStream(poFile);
    long vnLength = poFile.length();
    byte[] vaBytes = new byte[(int)vnLength];
    int offset = 0;
    int numRead = 0;
    while (offset < vaBytes.length && (numRead=voIs.read(vaBytes, offset, vaBytes.length-offset)) >= 0) {
        offset += numRead;
    }
    if (offset < vaBytes.length) {
        throw new IOException("Could not completely read file "+poFile.getName());
    }
    voIs.close();
    return vaBytes;
}
/**
 * Reads the content of an InputStream and returns a byte array
 * @param poIs InputStream. Input file
 * @return byte[]. Output byte array
 * @throws IOException
 */
public static byte[] getBytesFromInputStream(InputStream poIs) throws IOException {
    long vnLength = poIs.available();
    byte[] vaBytes = new byte[(int)vnLength];
    int offset = 0;
    int numRead = 0;
    while (offset < vaBytes.length && (numRead=poIs.read(vaBytes, offset, vaBytes.length-offset)) >= 0) {
        offset += numRead;
    }
    if (offset < vaBytes.length) {
        throw new IOException("Could not completely read file");
    }
    poIs.close();
    return vaBytes;
}
/**
 * Checks the beginning of a text file and decides whether is Unicode encoded or not (if it has BOM marker)
 * @param poFile File Input text file
 * @return boolean Flag that indicates if it has BOM marker or not
 * @throws IOException 
 */
public static boolean isFileContainsBOM(File poFile) throws IOException {
    boolean vbResul = false;
    if(!poFile.exists()) {
        //throw new IllegalArgumentException("File: " + poFile.getName() + " does not exists!");
    } else {
        byte[] vaBom = new byte[3];
        try(InputStream voIs = new FileInputStream(poFile)){
            // read first 3 bytes of a file.
            voIs.read(vaBom);
            // BOM encoded as ef bb bf
            String vcFirstBytes = new String(Hex.encodeHex(vaBom));
            if ("efbbbf".equalsIgnoreCase(vcFirstBytes)) {
                vbResul = true;
            }
        }
    }
    return vbResul;
  }

/**
 * Generates a random text string to use it as a password
 * @return String Random text value
 */
public static String generateKey () {
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    java.util.Random voRandom = new java.util.Random();
    String vcResul = "";
    try {
        vcResul = voRandom.ints(leftLimit, rightLimit + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(targetStringLength)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
    }catch (Exception e) {}
    return vcResul;
}

}
