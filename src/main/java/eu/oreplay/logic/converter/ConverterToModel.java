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
    public static final String CONTENTS_ENTRY = "EntryList";
    public static final String RES_TOTALS = "Totals";
    public static final String RES_BREAKDOWN = "Breakdown";
    public static final String RES_RADIO = "Radiocontrols";
    public static final String RES_ANSWERS = "Answers";
    public static final String SRC_GENERICXML = "IOFXML";
    public static final String SRC_OE2010 = "OE2010";
    public static final String SRC_OS2010 = "OS2010";
    public static final String SRC_OESCORE2010 = "OEScore2010";
    public static final String SRC_OEV12 = "OEv12";
    public static final String SRC_OSV12 = "OSv12";
    public static final String SRC_OESCOREV12 = "OEScorev12";
    public static final String SRC_SITIMING = "SiTiming";
    public static final String SRC_MEOS = "MeOS";
    public static final String SRC_OPRE = "ControlOPrecision";
    public static final String TRAILO_TYPE_PREO = "PreO";
    public static final String TRAILO_TYPE_TEMPO = "TempO";
    public static final String TRAILO_TYPE_SPRINT = "Sprint";
    public static final String TRAILO_AT_FINISH = "Finish";
    public static final String TRAILO_AT_INTERMEDIATE = "Intermediate";
    public static final String TOT_TIME = "TotalizationTime";
    public static final String TOT_POINTS = "TotalizationPoints";

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
    private String cStageDate;
    private String cStageZeroTime;
    private String cDateFormat;
    private boolean bForce;
    private String cTrailoType;
    private String cTrailoAt;
    private String cTrailoNormal;
    private String cTrailoGroup;
    private String cTotalization;

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

    @JsonIgnore
    public String getcStageDate() {
        return cStageDate;
    }
    public void setcStageDate(String cStageDate) {
        this.cStageDate = cStageDate;
    }
    @JsonIgnore
    public String getcStageZeroTime() {
        return cStageZeroTime;
    }
    public void setcStageZeroTime(String cStageZeroTime) {
        this.cStageZeroTime = cStageZeroTime;
    }
    @JsonIgnore
    public String getcDateFormat() {
        return cDateFormat;
    }
    public void setcDateFormat(String cDateFormat) {
        this.cDateFormat = cDateFormat;
    }
    @JsonProperty("force")
    @JsonIgnore
    public boolean isbForce() {
        return bForce;
    }
    public void setbForce(boolean bForce) {
        this.bForce = bForce;
    }

    @JsonProperty("trailo_type")
    public String getcTrailoType() {
        return cTrailoType;
    }
    public void setcTrailoType(String cTrailoType) {
        this.cTrailoType = cTrailoType;
    }
    @JsonProperty("trailo_at")
    public String getcTrailoAt() {
        return cTrailoAt;
    }
    public void setcTrailoAt(String cTrailoAt) {
        this.cTrailoAt = cTrailoAt;
    }
    @JsonProperty("trailo_normal")
    public String getcTrailoNormal() {
        return cTrailoNormal;
    }
    public void setcTrailoNormal(String cTrailoNormal) {
        this.cTrailoNormal = cTrailoNormal;
    }
    @JsonProperty("trailo_group")
    public String getcTrailoGroup() {
        return cTrailoGroup;
    }
    public void setcTrailoGroup(String cTrailoGroup) {
        this.cTrailoGroup = cTrailoGroup;
    }
    @JsonProperty("totalization")
    public String getcTotalization() {
        return cTotalization;
    }
    public void setcTotalization(String cTotalization) {
        this.cTotalization = cTotalization;
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
        cStageDate = "";
        cStageZeroTime = "";
        bForce = false;
        cTrailoType = OTHER_VALUES;
        cTrailoAt = OTHER_VALUES;
        cTrailoNormal = "0";
        cTrailoGroup = "0";
        cTotalization = OTHER_VALUES;
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
        cStageDate = poSrc.getcStageDate();
        cStageZeroTime = poSrc.getcStageZeroTime();
        bForce = poSrc.isbForce();
        cTrailoType = poSrc.getcTrailoType();
        cTrailoAt = poSrc.getcTrailoAt();
        cTrailoNormal = poSrc.getcTrailoNormal();
        cTrailoGroup = poSrc.getcTrailoGroup();
        cTotalization = poSrc.getcTotalization();
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
        if (getcSource()!=null && 
            (getcSource().equals(ConverterToModel.SRC_OE2010) || getcSource().equals(ConverterToModel.SRC_OEV12))) {
            vbResul = true;
        }
        return vbResul;
    }    
    @JsonIgnore
    public boolean isRelay () {
        boolean vbResul = false;
        if (getcSource()!=null && 
            (getcSource().equals(ConverterToModel.SRC_OS2010) || getcSource().equals(ConverterToModel.SRC_OSV12))) {
            vbResul = true;
        }
        return vbResul;
    }    
    @JsonIgnore
    public boolean isScoring () {
        boolean vbResul = false;
        if (getcSource()!=null && 
            (getcSource().equals(ConverterToModel.SRC_OESCORE2010) ||
                    getcSource().equals(ConverterToModel.SRC_OESCOREV12) ||
                    isbIncludeScore())) {
            vbResul = true;
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
                cTotalization = OTHER_VALUES;
                if (vcTopValues.startsWith("OE0001_V12")) {
                    cContents = CONTENTS_START;
                    cSource = SRC_OEV12;
                } else if (vcTopValues.startsWith("OE0001")) {
                    cContents = CONTENTS_START;
                    cSource = SRC_OE2010;
                } else if (vcTopValues.startsWith("OESco0009_V12")) {
                    cContents = CONTENTS_START;
                    cSource = SRC_OESCOREV12;
                    bIncludeScore = true;
                } else if (vcTopValues.startsWith("OESco0001")) {
                    cContents = CONTENTS_START;
                    cSource = SRC_OESCORE2010;
                    bIncludeScore = true;
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
                    bIncludeScore = true;
                } else if (vcTopValues.startsWith("OESco0014_V12")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_BREAKDOWN;
                    cSource = SRC_OESCOREV12;
                    bIncludeScore = true;
                } else if (vcTopValues.startsWith("OESco0012")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_TOTALS;
                    cSource = SRC_OESCORE2010;
                    bIncludeScore = true;
                } else if (vcTopValues.startsWith("OESco0014")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_BREAKDOWN;
                    cSource = SRC_OESCORE2010;
                    bIncludeScore = true;
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
                    cTotalization = TOT_POINTS;
                } else if (vcTopValues.startsWith("OE0013_V12")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_TOTALS;
                    cSource = SRC_OEV12;
                    bOneStage = false;
                    cTotalization = TOT_TIME;
                } else if (vcTopValues.startsWith("OE0013a")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_TOTALS;
                    cSource = SRC_OE2010;
                    bOneStage = false;
                    cTotalization = TOT_POINTS;
                } else if (vcTopValues.startsWith("OE0013")) {
                    cContents = CONTENTS_RESULT;
                    cResultsType = RES_TOTALS;
                    cSource = SRC_OE2010;
                    bOneStage = false;
                    cTotalization = TOT_TIME;
                }
                //If there is a valid cContents value, then the result is true
                if (cContents.equals(CONTENTS_RESULT) || cContents.equals(CONTENTS_START)) {
                    vbResul = true;
                }
            } else if (cExtension.equals(EXT_XML)) {
                if (vcTopValues.contains("StartList") || vcTopValues.contains("ResultList") ||
                         vcTopValues.contains("EntryList")) {
                    //Contents
                    if (vcTopValues.contains("StartList")) {
                        cContents = CONTENTS_START;
                        cResultsType = OTHER_VALUES;
                    } else if (vcTopValues.contains("ResultList")) {
                        cContents = CONTENTS_RESULT;
                    } else if (vcTopValues.contains("EntryList")) {
                        cContents = CONTENTS_ENTRY;
                        cResultsType = OTHER_VALUES;
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
                        bIncludeScore = true;
                    } else if (vcTopValues.contains("SportSoftware OS2010")) {
                        cSource = SRC_OS2010;
                    } else if (vcTopValues.contains("SportSoftware OS12")) {
                        cSource = SRC_OSV12;
                    } else if (vcTopValues.contains("SiTiming")) {
                        cSource = SRC_SITIMING;
                    } else if (vcTopValues.contains("MeOS")) {
                        cSource = SRC_MEOS;
                    } else if (vcTopValues.contains("ControlOPrecision")) {
                        cSource = SRC_OPRE;
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
                    cTotalization = OTHER_VALUES;
                }               
            } else if (cExtension.equals(EXT_HTML)) {
                if (vcTopValues.contains("sitiming:eventid")) {
                    cSource = SRC_SITIMING;
                    cContents = OTHER_VALUES;
                    cResultsType = OTHER_VALUES;
                    cIofVersion = OTHER_VALUES;
                    cTotalization = OTHER_VALUES;
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
            //Make consecutive, incremental readings until all of the needed information is collected
            boolean vbContinue = true;
            int vnLoop = 0;
            int vnLimit = 10000;
            //Put value by default
            cResultsType = OTHER_VALUES;
            //Search by blocks of data. Putting a loop limit to avoid problems repeating excesive times
            while (vbContinue && vnLoop<500) {
                String vcTopValues = "";
                char[] vaChars = new char[vnLimit];
                int vnMaxRead = voBr.read(vaChars, 0, vnLimit);
                vcTopValues = new String(vaChars, 0, vnMaxRead);
                //Stop making loops if characters read are less than requested
                if (vnMaxRead < vnLimit) {
                    vbContinue = false;
                }
                //Removes all " characters
                vcTopValues = vcTopValues.replaceAll("\"", "");
                vcTopValues = vcTopValues.replaceAll("'", "");
                //Ask for some values if confirmed that is XML for results
                if (cExtension.equals(EXT_XML) && cContents.equals(CONTENTS_RESULT)) {
                    //If it's not a TrailO competition, check the existence of Splits tags
                    if (!cSource.equals(SRC_OPRE)) {
                        if (vcTopValues.contains("<SplitTime")) {
                            cResultsType = RES_BREAKDOWN;
                            if (vcTopValues.contains("SplitTimeControls:")) {
                                cResultsType = RES_RADIO;
                            }
                            //There is no need for further readings, because this comment appears before a split time when there is a radiocontrol result list
                            vbContinue = false;
                        } else {
                            cResultsType = RES_TOTALS;
                        }
                    } else {
                        //If it's TrailO, check at the header for the rest of parameters
                        int vnCreator = vcTopValues.indexOf("creator");
                        if (vnCreator>0) {
                            int vnEndTag = vcTopValues.indexOf(">", vnCreator);
                            if (vnEndTag>0) {
                                String vcTrailoParams = vcTopValues.substring(vnCreator+8, vnEndTag);
                                String[] vaTrailoParams = vcTrailoParams.split(";");
                                if (vaTrailoParams.length>2)
                                    cTrailoType = vaTrailoParams[2];
                                if (vaTrailoParams.length>3)
                                    cResultsType = vaTrailoParams[3];
                                if (vaTrailoParams.length>4)
                                    cTrailoAt = vaTrailoParams[4];
                                if (vaTrailoParams.length>5)
                                    cTrailoNormal = vaTrailoParams[5];
                                if (vaTrailoParams.length>6)
                                    cTrailoGroup = vaTrailoParams[6];
                            }
                        }
                        //If it's TrailO, there is no need for further readings
                        vbContinue = false;
                    }
                    //If XML contains Score tags, set the flag to true
                    if (vcTopValues.contains("<Score type=")) {
                        bIncludeScore = true;
                    }
                    vbResul = true;
                } else {
                    vbContinue = false;
                }
                //Increase Loop counter
                vnLoop++;
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
    public abstract eu.oreplay.db.Event convertEntryList (String pcFile);
    public abstract eu.oreplay.db.Event convertEntryList (File poFile);
}
