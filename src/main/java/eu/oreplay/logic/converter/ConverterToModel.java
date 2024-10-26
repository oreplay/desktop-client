/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.logic.converter;

import java.io.*;
import eu.oreplay.utils.*;
import org.apache.commons.io.input.BOMInputStream;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.apache.logging.log4j.*;


/**
 * Superclass that represents various types of converters from timekeeping
 * software exports to OReplay data model
 * @author javier.arufe
 */
@JsonRootName(value = "data_structure")
@JsonInclude(Include.NON_NULL)
public abstract class ConverterToModel {
    Logger oLog = null;
    public static final String OTHER_VALUES = "Other";
    public static final String EXT_HTML = "HTML";
    public static final String EXT_CSV = "CSV";
    public static final String EXT_XML = "XML";
    public static final String IOF_VERSION_2 = "2.0";
    public static final String IOF_VERSION_3 = "3.0";
    public static final String CONTENTS_START = "StartList";
    public static final String CONTENTS_RESULT = "ResultList";
    public static final String RES_TOTALS = "Totals";
    public static final String RES_BREAKDOWN = "Breakdown";
    public static final String RES_RADIO = "Radiocontrols";
    public static final String SRC_GENERICXML = "IOFXML";
    public static final String SRC_OE2010 = "OE2010";
    public static final String SRC_OS2010 = "OS2010";
    public static final String SRC_OESCORE2010 = "OEScore2010";
    public static final String SRC_OEV12 = "OEv12";
    public static final String SRC_OSV12 = "OSv12";
    public static final String SRC_OESCOREV12 = "OEScorev12";
    public static final String SRC_SITIMING = "SiTiming";
    public static final String SRC_MEOS = "MeOS";

    private String cFile;
    private boolean bExists;
    private String cExtension;
    private boolean bUtf;
    private boolean bKnownData;
    private String cContents;
    private String cResultsType;
    private boolean bOneStage;
    private String cSource;
    private String cIofVersion;
    private boolean bIncludeScore;

    public ConverterToModel() {
        initializeValues();
    }

    @JsonIgnore
    public Logger getoLog() {
        return oLog;
    }
    public void setoLog(Logger poLog) {
        oLog = poLog;
    }

    @JsonProperty("file")
    public String getcFile() {
        return cFile;
    }

    public void setcFile(String cFile) {
        this.cFile = cFile;
    }

    @JsonProperty("exists")
    @JsonIgnore
    public boolean isbExists() {
        //If there is a file name, checks the existence of that file; if not, returns the current value of the flag
        if (!cFile.equals("")) {
            File voFile = new File(cFile);
            bExists = voFile.exists();
            voFile = null;
        }
        return bExists;
    }

    public void setbExists(boolean bExists) {
        this.bExists = bExists;
    }

    @JsonProperty("extension")
    public String getcExtension() {
        return cExtension;
    }

    public void setcExtension(String cExtension) {
        this.cExtension = cExtension;
    }

    @JsonProperty("utf")
    public boolean isbUtf() {
        return bUtf;
    }

    public void setbUtf(boolean bUtf) {
        this.bUtf = bUtf;
    }

    @JsonProperty("known_data")
    public boolean isbKnownData() {
        return bKnownData;
    }

    public void setbKnownData(boolean bKnownData) {
        this.bKnownData = bKnownData;
    }

    @JsonProperty("contents")
    public String getcContents() {
        return cContents;
    }

    public void setcContents(String cContents) {
        this.cContents = cContents;
    }

    @JsonProperty("results_type")
    public String getcResultsType() {
        return cResultsType;
    }

    public void setcResultsType(String cResultsType) {
        this.cResultsType = cResultsType;
    }

    @JsonProperty("one_stage")
    public boolean isbOneStage() {
        return bOneStage;
    }

    public void setbOneStage(boolean bOneStage) {
        this.bOneStage = bOneStage;
    }

    @JsonProperty("source")
    public String getcSource() {
        return cSource;
    }

    public void setcSource(String cSource) {
        this.cSource = cSource;
    }

    @JsonProperty("iof_version")
    public String getcIofVersion() {
        return cIofVersion;
    }

    public void setcIofVersion(String cIofVersion) {
        this.cIofVersion = cIofVersion;
    }

    @JsonProperty("include_score")
    public boolean isbIncludeScore() {
        return bIncludeScore;
    }

    public void setbIncludeScore(boolean bIncludeScore) {
        this.bIncludeScore = bIncludeScore;
    }
    
    /**
     * Returns the flag that checks the existence of the source file
     * @return boolean
     */
    public boolean exists() {
        return isbExists();
    }
    /**
     * Initialize the values of the properties
     */
    public void initializeValues () {
        cFile = "";
        bExists = false;
        cExtension = OTHER_VALUES;
        bUtf = true;
        bKnownData = false;
        cContents = OTHER_VALUES;
        cResultsType = OTHER_VALUES;
        bOneStage = true;
        cSource = OTHER_VALUES;
        cIofVersion = OTHER_VALUES;
        bIncludeScore = false;
    }
    /**
     * Copy values of the properties from another object
     * @param poSrc ConverterToModel Source object
     */
    public void copyValues (ConverterToModel poSrc) {
        oLog = poSrc.getoLog();
        cFile = poSrc.cFile;
        bExists = poSrc.isbExists();
        cExtension = poSrc.getcExtension();
        bUtf = poSrc.isbUtf();
        bKnownData = poSrc.isbKnownData();
        cContents = poSrc.getcContents();
        cResultsType = poSrc.getcResultsType();
        bOneStage = poSrc.isbOneStage();
        cSource = poSrc.getcSource();
        cIofVersion = poSrc.getcIofVersion();
        bIncludeScore = poSrc.isbIncludeScore();
    }
    @JsonIgnore
    public boolean isCsv () {
        return (getcExtension()!=null?getcExtension().equals(ConverterToModel.EXT_CSV):false);
    }
    @JsonIgnore
    public boolean isXml () {
        return (getcExtension()!=null?getcExtension().equals(ConverterToModel.EXT_XML):false);
    }
    @JsonIgnore
    public boolean isClassic () {
        boolean vbResul = false;
        if (getcSource()!=null) {
            if (getcSource().equals(ConverterToModel.SRC_OE2010) ||
                    getcSource().equals(ConverterToModel.SRC_OEV12)) {
                vbResul = true;
            }
        }
        return vbResul;
    }    
    @JsonIgnore
    public boolean isRelay () {
        boolean vbResul = false;
        if (getcSource()!=null) {
            if (getcSource().equals(ConverterToModel.SRC_OS2010) ||
                    getcSource().equals(ConverterToModel.SRC_OSV12)) {
                vbResul = true;
            }
        }
        return vbResul;
    }    
    @JsonIgnore
    public boolean isScoring () {
        boolean vbResul = false;
        if (getcSource()!=null) {
            if (getcSource().equals(ConverterToModel.SRC_OESCORE2010) ||
                    getcSource().equals(ConverterToModel.SRC_OESCOREV12) ||
                    isbIncludeScore()) {
                vbResul = true;
            }
        }
        return vbResul;
    }    
    /**
     * Makes some initial inspection to get the kind of contents of the file;
     * the information is stored in the properties of this class
     * @param pcFile String path+name of the file to be inspected
     */
    public void inspectFile (String pcFile) {
        File voFile = new File(pcFile);
        inspectFile (voFile);
        voFile = null;
    }
    /**
     * Makes some initial inspection to get the kind of contents of the file;
     * the information is stored in the properties of this class
     * @param poFile File The file to be inspected
     */
    public void inspectFile (File poFile) {
        if (poFile!=null) {
            //Gets the name of the file
            cFile = poFile.getPath();
            //Checks the existence of the file
            bExists = poFile.exists();
            //Checks the extension of the file
            if (cFile.toLowerCase().endsWith("xml"))
                cExtension = EXT_XML;
            else if (cFile.toLowerCase().endsWith("csv"))
                cExtension = EXT_CSV;
            else if (cFile.toLowerCase().endsWith("html") ||
                    cFile.toLowerCase().endsWith("htm"))
                cExtension = EXT_HTML;
            else
                cExtension = OTHER_VALUES;
            //Checks if it has UTF encoding
            try {
                bUtf = Utils.isFileContainsBOM(poFile);
            }catch (IOException e) {
                bUtf = false;
            }
            bOneStage = true;
            //Checks, generically, if the file has known and processable contents
            //Also, cContents, cResultsType, cSource, cIofVersion
            bKnownData = checkForKnownData(poFile);
            //Additional checking for type of results (totals, breakdown, radiocontrols)
            if (bKnownData && cExtension.equals(EXT_XML) && cContents.equals(CONTENTS_RESULT) && 
                    cIofVersion.equals(IOF_VERSION_3)) {
                checkForIofXmlResultsType(poFile);
            }
        } else {
            initializeValues();
        }
    }
    /**
     * Makes a generic checking for kwnown data, trying to find if the beginning
     * of the file contents strings like OE0001, OE0016, ResultList, etc
     * @param poFile File The file to be inspected
     * @return boolean Flag that indicates a correct checking
     */
    private boolean checkForKnownData (File poFile) {
        boolean vbResul = false;
        try {
            //Get a BufferedReader from the source file
            InputStream voIs;
            if (bUtf)
                voIs = new BOMInputStream(new FileInputStream(poFile));
            else
                voIs = new FileInputStream(poFile);
            BufferedReader voBr = new BufferedReader(
                          new InputStreamReader(voIs, (bUtf?Utils.ENCODING_UTF_8:Utils.ENCODING_ISO_8859_1)));
            //Read the first characters of the file and concatenate them
            String vcTopValues = "";
            char[] vaChars = new char[500];
            int vnMaxRead = voBr.read(vaChars, 0, 500);
            vcTopValues = new String(vaChars, 0, vnMaxRead);
            //Removes all " characters
            vcTopValues = vcTopValues.replaceAll("\"", "");
            vcTopValues = vcTopValues.replaceAll("'", "");
            //Ask for some values
            if (cExtension.equals(EXT_CSV)) {
                cContents = OTHER_VALUES;
                cResultsType = OTHER_VALUES;
                cIofVersion = OTHER_VALUES;
                if (vcTopValues.startsWith("OE0001_V12")) {
                    cContents = CONTENTS_START;
                    cSource = SRC_OEV12;
                } else if (vcTopValues.startsWith("OE0001")) {
                    cContents = CONTENTS_START;
                    cSource = SRC_OE2010;
                } else if (vcTopValues.startsWith("OESco0009_V12")) {
                    cContents = CONTENTS_START;
                    cSource = SRC_OESCOREV12;
                } else if (vcTopValues.startsWith("OESco0001")) {
                    cContents = CONTENTS_START;
                    cSource = SRC_OESCORE2010;
                } else if (vcTopValues.startsWith("OS0009_V12")) {
                    cContents = CONTENTS_START;
                    cSource = SRC_OSV12;
                } else if (vcTopValues.startsWith("OS0001")) {
                    cContents = CONTENTS_START;
                    cSource = SRC_OS2010;
                } else if (vcTopValues.startsWith("RaceNumber;CardNumbers;MembershipNumbers;Name;Category;Club;Country;CourseClass;StartTime;StartTimePreference")) {
                    cContents = CONTENTS_START;
                    cSource = SRC_SITIMING;
                } else if (vcTopValues.startsWith("OE0012_V12")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_TOTALS;
                    cSource = SRC_OEV12;
                } else if (vcTopValues.startsWith("OE0014_V12")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_BREAKDOWN;
                    cSource = SRC_OEV12;
                } else if (vcTopValues.startsWith("OE0016_V12")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_RADIO;
                    cSource = SRC_OEV12;
                } else if (vcTopValues.startsWith("OE0012")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_TOTALS;
                    cSource = SRC_OE2010;
                } else if (vcTopValues.startsWith("OE0014")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_BREAKDOWN;
                    cSource = SRC_OE2010;
                } else if (vcTopValues.startsWith("OE0016")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_RADIO;
                    cSource = SRC_OE2010;
                } else if (vcTopValues.startsWith("OESco0012_V12")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_TOTALS;
                    cSource = SRC_OESCOREV12;
                } else if (vcTopValues.startsWith("OESco0014_V12")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_BREAKDOWN;
                    cSource = SRC_OESCOREV12;
                } else if (vcTopValues.startsWith("OESco0012")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_TOTALS;
                    cSource = SRC_OESCORE2010;
                } else if (vcTopValues.startsWith("OESco0014")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_BREAKDOWN;
                    cSource = SRC_OESCORE2010;
                } else if (vcTopValues.startsWith("OS0012_V12")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_TOTALS;
                    cSource = SRC_OSV12;
                } else if (vcTopValues.startsWith("OS0014_V12")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_BREAKDOWN;
                    cSource = SRC_OSV12;
                } else if (vcTopValues.startsWith("OS0016a")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_RADIO;
                    cSource = SRC_OS2010;
                } else if (vcTopValues.startsWith("OS0016")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_RADIO;
                    cSource = SRC_OSV12;
                } else if (vcTopValues.startsWith("OS0012")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_TOTALS;
                    cSource = SRC_OS2010;
                } else if (vcTopValues.startsWith("OS0014")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_BREAKDOWN;
                    cSource = SRC_OS2010;
                } else if (vcTopValues.startsWith("RaceNumber;CardNumbers;MembershipNumbers;Name;Category;Club;Country;CourseClass;StartTime;FinishTime")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_BREAKDOWN;
                    cSource = SRC_SITIMING;
                } else if (vcTopValues.startsWith("OE0013a_V12")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_TOTALS;
                    cSource = SRC_OEV12;
                    bOneStage = false;
                }
                //If there is a valid cContents value, then the result is true
                if (cContents.equals(CONTENTS_RESULT) || cContents.equals(CONTENTS_START)) {
                    vbResul = true;
                }
            } else if (cExtension.equals(EXT_XML)) {
                if (vcTopValues.contains("StartList") || vcTopValues.contains("ResultList")) {
                    //Contents
                    if (vcTopValues.contains("StartList")) {
                        cContents = CONTENTS_START;
                        cResultsType = OTHER_VALUES;
                    } else {
                        cContents = CONTENTS_RESULT;
                    }
                    //Source
                    if (vcTopValues.contains("SportSoftware OE2010")) {
                        cSource = SRC_OE2010;
                    } else if (vcTopValues.contains("SportSoftware OE12")) {
                        cSource = SRC_OEV12;
                    } else if (vcTopValues.contains("SportSoftware OEScore")) {
                        if (vcTopValues.contains("V.11"))
                            cSource = SRC_OESCORE2010;
                        else if (vcTopValues.contains("V.12"))
                            cSource = SRC_OESCOREV12;
                    } else if (vcTopValues.contains("SportSoftware OS2010")) {
                        cSource = SRC_OS2010;
                    } else if (vcTopValues.contains("SportSoftware OS12")) {
                        cSource = SRC_OSV12;
                    } else if (vcTopValues.contains("SiTiming")) {
                        cSource = SRC_SITIMING;
                    } else if (vcTopValues.contains("MeOS")) {
                        cSource = SRC_MEOS;
                    }
                    //IOF Version
                    if (vcTopValues.contains("iofVersion=2.0")) {
                        cIofVersion = IOF_VERSION_2;
                    } else if (vcTopValues.contains("iofVersion=3.0")) {
                        cIofVersion = IOF_VERSION_3;
                    }
                    vbResul = true;
                } else {
                    cContents = OTHER_VALUES;
                    cResultsType = OTHER_VALUES;
                    cSource = OTHER_VALUES;
                    cIofVersion = OTHER_VALUES;
                }               
            } else if (cExtension.equals(EXT_HTML)) {
                if (vcTopValues.contains("sitiming:eventid")) {
                    cSource = SRC_SITIMING;
                    cContents = OTHER_VALUES;
                    cResultsType = OTHER_VALUES;
                    cIofVersion = OTHER_VALUES;
                    vbResul = true;
                }
            }
            voIs.close();
        } catch (Exception e) {
            vbResul = false;
            if (oLog!=null)
                oLog.error("error_exception", e);

        }
        return vbResul;
    }

    /**
     * If IOF XML, makes an extra generic checking to see the type of results:
     * totals, breakdown or intermediate times
     * @param poFile File The file to be inspected
     * @return boolean Flag that indicates a correct checking
     */
    private boolean checkForIofXmlResultsType (File poFile) {
        boolean vbResul = false;
        try {
            //Get a BufferedReader from the source file
            InputStream voIs;
            if (bUtf)
                voIs = new BOMInputStream(new FileInputStream(poFile));
            else
                voIs = new FileInputStream(poFile);
            BufferedReader voBr = new BufferedReader(
                          new InputStreamReader(voIs, (bUtf?Utils.ENCODING_UTF_8:Utils.ENCODING_ISO_8859_1)));
            //Read the first characters of the file and concatenate them (more than the checking for known data)
            String vcTopValues = "";
            char[] vaChars = new char[5000];
            int vnMaxRead = voBr.read(vaChars, 0, 5000);
            vcTopValues = new String(vaChars, 0, vnMaxRead);
            //Removes all " characters
            vcTopValues = vcTopValues.replaceAll("\"", "");
            vcTopValues = vcTopValues.replaceAll("'", "");
            //Put value by default
            cResultsType = OTHER_VALUES;
            //Ask for some values if confirmed that is XML for results
            if (cExtension.equals(EXT_XML) && cContents.equals(CONTENTS_RESULT)) {
                if (vcTopValues.contains("<SplitTime")) {
                    cResultsType = RES_BREAKDOWN;
                    if (vcTopValues.contains("SplitTimeControls:")) {
                        cResultsType = RES_RADIO;
                    }
                } else {
                    cResultsType = RES_TOTALS;
                }               
                //If XML contains Score tags, set the flag to true
                if (vcTopValues.contains("<Score type=")) {
                    bIncludeScore = true;
                }
                vbResul = true;
            }
            voIs.close();
        } catch (Exception e) {
            vbResul = false;
            if (oLog!=null)
                oLog.error("error_exception", e);
        }
        return vbResul;
    }

    public abstract eu.oreplay.db.Event convertStartList (String pcFile);
    public abstract eu.oreplay.db.Event convertStartList (File poFile);
    public abstract eu.oreplay.db.Event convertResultList (String pcFile);
    public abstract eu.oreplay.db.Event convertResultList (File poFile);
}
