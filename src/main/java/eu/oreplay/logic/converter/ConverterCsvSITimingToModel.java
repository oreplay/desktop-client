/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.logic.converter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.oreplay.db.*;
import eu.oreplay.utils.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class with methods to convert data in CSV SI-Timing format to OReplay data model;
 * in this case, for Scoring orienteering or Rogaine competitions
 * @author javier.arufe, pi.matherror
 */
public class ConverterCsvSITimingToModel extends ConverterToModel{
    private String cSeparator = ";";
    private String cEncoding = Utils.ENCODING_UTF_8;
    private Event oEve = null; //Event/Stage basic information to perform the calculations (UUID, base date, base time, description, etc)
    private final String[] FILE = {"RaceNumber"};
    private final int[] COL_BIB = {0}; //RaceNumber
    private final int[] COL_SICARD = {1};
    private final int[] COL_DBID = {2}; //MembershipNumbers
    private final int[] COL_NAME = {3}; // Team|Member1~Member2~...~MemberN
    private final int[] COL_NC = {11}; //NonCompetitive
    private final int[] COL_START = {8};
    private final int[] COL_FINISH = {9};
    private final int[] COL_TIME = {10};
    private final int[] COL_STATUS = {13}; //??
    private final int[] COL_CLU_ID = {0};
    private final int[] COL_CLU_CITY = {-1};
    private final int[] COL_CLU_SHORT = {-1};
    private final int[] COL_CAT_ID = {7};
    private final int[] COL_CAT_SHORT = {7}; //CourseClass
    private final int[] COL_CAT_LONG = {-1};
    private final int[] COL_COU_ID = {7};
    private final int[] COL_COU_SHORT = {7}; //CourseClass
    private final int[] COL_COU_DIST = {-1};
    private final int[] COL_COU_CLIMB = {-1};
    private final int[] COL_COU_CONTROLS = {31};
    private final int[] COL_COU_MAXPOINTS = {-1};
    private final int[] COL_COU_TIMELIMIT = {-1};
    private final int[] COL_POSITION = {12};
    private final int[] COL_FINALPOINTS = {17}; //FinalScore
    private final int[] COL_TOTALPOINTS = {-1};
    private final int[] COL_PENALTY = {15};
    private final int[] COL_BONUS = {16}; //ManualScoreAdjust
    private final int[] COL_COMMENTS = {-1};
    private final int[] COL_SPLIT_STATION = {32};
    private final int[] COL_SPLIT_POINTS = {34};
    private final int[] COL_SPLIT_TIME = {33};
    private final int[] COL_SPLIT_INC = {3};
    private final int[] COL_RADIO_NUM = {-1};
    private final int[] COL_RADIO_STATION = {-1};
    private final int[] COL_RADIO_TIME = {-1};
    private final int[] COL_RADIO_POSITION = {-1};
    private final int[] COL_RADIO_INC = {4};


    public ConverterCsvSITimingToModel() {
        super();
    }
    public ConverterCsvSITimingToModel(ConverterToModel poSrc) {
        super.copyValues(poSrc);
    }
    public ConverterCsvSITimingToModel(String pcSeparator, String pcEncoding) {
        cSeparator = pcSeparator;
        cEncoding = pcEncoding;
    }
    public ConverterCsvSITimingToModel(String pcSeparator, String pcEncoding, Event poEve) {
        cSeparator = pcSeparator;
        cEncoding = pcEncoding;
        oEve = poEve;
    }

    public void setSpecificProperties (String pcSeparator, String pcEncoding, Event poEve) {
        cSeparator = pcSeparator;
        cEncoding = pcEncoding;
        oEve = poEve;
    }
    
    public String getcSeparator() {
        return cSeparator;
    }

    public void setcSeparator(String cSeparator) {
        this.cSeparator = cSeparator;
    }

    public String getcEncoding() {
        return cEncoding;
    }

    public void setcEncoding(String cEncoding) {
        this.cEncoding = cEncoding;
    }

    @JsonIgnore
    public Event getoEve() {
        return oEve;
    }

    public void setoEve(Event oEve) {
        this.oEve = oEve;
    }

    @Override
    public Event convertEntryList (String pcFile) {
        File voFile = new File(pcFile);
        Event voResul = convertEntryList (voFile);
        voFile = null;
        return voResul;
    }
    @Override
    public Event convertEntryList (File poFile) {
        return null;
    }    

    @Override
    public Event convertStartList (String pcFile) {
        File voFile = new File(pcFile);
        Event voResul = convertStartList (voFile);
        voFile = null;
        return voResul;
    }
    @Override
    public Event convertStartList (File poFile) {
        Event voEve = null;
        if (oEve!=null) {
            List<String> plStart = new ArrayList<>();
            BufferedReader voBr = null;
            String vcLine = "";
            try {
                //Loops the file and filters line by line
                voBr = new BufferedReader(new InputStreamReader(new FileInputStream(poFile), cEncoding));
                while ((vcLine = voBr.readLine()) != null) {
                    plStart.add(vcLine);
                }
                voBr.close();
                // TODO: Manage Start List
                //voEve = convertStartListSingleStageOEScore (plStart);
                oLog.error("Start list uploads not supported yet");
            } catch (Exception e) {
                if (oLog!=null)
                    oLog.error("Exception reading start list lines", e);
                voEve = null;
            }        
        }
        return voEve;
    }
    
    /**
     * Given a representation of CSV for start list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the CSV file; this method is for 1-stage, Scoring or Rogaine event
     * The results can come from OEScore2010 or OEScoreV12
     * //@param plStart List<String> List with elements for each line of a CSV file for start list
     * //@return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults)
     */
    // TODO: Manage Start Lists
    /*public Event convertStartListSingleStageOEScore (List<String> plStart) {
        Event voEve = null;
        //Depending on the type of file, the columns of the fields are different
        int vnColIndex = this.getIndexFromContentsAndSource();       
        //HashMap to store Classes. For each class its runners. Runners shoulb be ordered by class in the CSV but, what if not?...
        HashMap<String, Clazz> vlCla = new HashMap<>();
        if (plStart!=null) {
            //Event's data
            voEve = Utils.copyBasicEventData(oEve);
            //Stage's data
            Stage voSta = Utils.copyBasicOneStageData(oEve);
            //First line contains the name of the columns
            String vcLine = "";
            //Loop starts at second line
            for (int i=1; i<plStart.size(); i++) {
                vcLine = plStart.get(i);
                String[] vaRecord = vcLine.split(cSeparator);
                if (vaRecord.length>=57) {
                    String vcClaId = (COL_CAT_ID[vnColIndex]>=0?vaRecord[COL_CAT_ID[vnColIndex]].trim().replaceAll("\"", ""):"");
                    String vcClaShort = (COL_CAT_SHORT[vnColIndex]>=0?vaRecord[COL_CAT_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                    String vcClaLong = (COL_CAT_LONG[vnColIndex]>=0?vaRecord[COL_CAT_LONG[vnColIndex]].trim().replaceAll("\"", ""):"");
                    //Search for the class in the HashMap
                    Clazz voCla = new Clazz();
                    if (vlCla.containsKey(vcClaId)) {
                        voCla = vlCla.get(vcClaId);
                    } else {
                        voCla.setId("");
                        voCla.setUuid("");
                        voCla.setOeKey(vcClaId);
                        voCla.setShortName(vcClaShort);
                        voCla.setLongName(vcClaLong);
                    }
                    //Now, the course of the class
                    if (voCla.getCourse()==null && !vaRecord[COL_COU_ID[vnColIndex]].trim().replaceAll("\"", "").equals("")) {
                        Course voCou = new Course();
                        voCou.setId("");
                        voCou.setUuid("");
                        voCou.setOeKey(COL_COU_ID[vnColIndex]>=0?vaRecord[COL_COU_ID[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voCou.setShortName(COL_COU_SHORT[vnColIndex]>=0?vaRecord[COL_COU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voCou.setDistance(COL_COU_DIST[vnColIndex]>=0?vaRecord[COL_COU_DIST[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voCou.setClimb(COL_COU_CLIMB[vnColIndex]>=0?vaRecord[COL_COU_CLIMB[vnColIndex]].trim().replaceAll("\"", ""):"");
                        int vnControls = 0;
                        try {
                            vnControls = Integer.parseInt(COL_COU_CONTROLS[vnColIndex]>=0?vaRecord[COL_COU_CONTROLS[vnColIndex]].trim().replaceAll("\"", ""):"0");
                        }catch (Exception eNumCon){
                            //Nothing to do
                        };
                        voCou.setControls(vnControls);
                        //Add the course to the class
                        voCla.setCourse(voCou);
                    }
                    //Process the runner and put it on the class
                    List<Runner> vlRun = voCla.getRunnerList();
                    if (vlRun==null)
                        vlRun = new ArrayList<>();
                    Runner voRun = new Runner();
                    voRun.setId("");
                    voRun.setUuid("");
                    voRun.setBibNumber(COL_BIB[vnColIndex]>=0?vaRecord[COL_BIB[vnColIndex]].trim().replaceAll("\"", ""):"");
                    voRun.setBibAlt(COL_BIBALT[vnColIndex]>=0?vaRecord[COL_BIBALT[vnColIndex]].trim().replaceAll("\"", ""):"");
                    voRun.setSicard(COL_SICARD[vnColIndex]>=0?vaRecord[COL_SICARD[vnColIndex]].trim().replaceAll("\"", ""):"");
                    voRun.setSicardAlt(COL_SICARDALT[vnColIndex]>=0?vaRecord[COL_SICARDALT[vnColIndex]].trim().replaceAll("\"", ""):"");
                    voRun.setDbId(COL_DBID[vnColIndex]>=0?vaRecord[COL_DBID[vnColIndex]].trim().replaceAll("\"", ""):"");
                    voRun.setIofId(COL_IOFID[vnColIndex]>=0?vaRecord[COL_IOFID[vnColIndex]].trim().replaceAll("\"", ""):"");
                    try {
                        String vcBirth = (COL_BIRTH[vnColIndex]>=0?vaRecord[COL_BIRTH[vnColIndex]].trim().replaceAll("\"", ""):"");
                        java.util.Date vdBirth = Utils.parse(vcBirth, "dd/MM/yyyy");
                        if (vdBirth==null) {
                            vdBirth = Utils.parse(vcBirth, "MM/dd/yyyy");
                        }
                        voRun.setBirthDate(vdBirth);
                    }catch(Exception eDate) {
                        //Nothing to do
                    }
                    //If the event belongs to FEDO, some fields come in Num or Text columns
                    if (oEve.getFederation()!=null) {
                        if (oEve.getFederation().getId().equals("FEDO")) {
                            //IOF ID
                            voRun.setIofId(COL_IOFID_FEDO[vnColIndex]>=0?vaRecord[COL_IOFID_FEDO[vnColIndex]].trim().replaceAll("\"", ""):"");
                            //Birthdate
                            try {
                                String vcBirth = (COL_BIRTH_FEDO[vnColIndex]>=0?vaRecord[COL_BIRTH_FEDO[vnColIndex]].trim().replaceAll("\"", ""):"");
                                java.util.Date vdBirth = Utils.parse(vcBirth, "dd/MM/yyyy");
                                if (vdBirth==null) {
                                    vdBirth = Utils.parse(vcBirth, "MM/dd/yyyy");
                                }
                                voRun.setBirthDate(vdBirth);
                            }catch(Exception eDate) {
                                //Nothing to do
                            }
                            //License
                            voRun.setLicense(COL_LIC_FEDO[vnColIndex]>=0?vaRecord[COL_LIC_FEDO[vnColIndex]].trim().replaceAll("\"", ""):"");
                            //DNI
                            voRun.setNationalId(COL_DNI_FEDO[vnColIndex]>=0?vaRecord[COL_DNI_FEDO[vnColIndex]].trim().replaceAll("\"", ""):"");
                        }
                    }
                    voRun.setLastName(COL_LASTNAME[vnColIndex]>=0?vaRecord[COL_LASTNAME[vnColIndex]].trim().replaceAll("\"", ""):"");
                    voRun.setFirstName(COL_FIRSTNAME[vnColIndex]>=0?vaRecord[COL_FIRSTNAME[vnColIndex]].trim().replaceAll("\"", ""):"");
                    voRun.setSex((COL_SEX[vnColIndex]>=0?(vaRecord[COL_SEX[vnColIndex]].length()>0?vaRecord[COL_SEX[vnColIndex]].trim().replaceAll("\"", "").charAt(0):' '):' '));
                    voRun.setTelephone1(COL_TEL1[vnColIndex]>=0?vaRecord[COL_TEL1[vnColIndex]].trim().replaceAll("\"", ""):"");
                    voRun.setTelephone2(COL_TEL2[vnColIndex]>=0?vaRecord[COL_TEL2[vnColIndex]].trim().replaceAll("\"", ""):"");
                    voRun.setEmail(COL_MAIL[vnColIndex]>=0?vaRecord[COL_MAIL[vnColIndex]].trim().replaceAll("\"", ""):"");
                    //Start Time is set in a first element of RunnerResult List
                    ArrayList<RunnerResult> vlRes = new ArrayList<>();
                    RunnerResult voRes = new RunnerResult();
                    voRes.setId("");
                    voRes.setStageOrder(voSta.getOrderNumber());
                    voRes.setLegNumber(1);
                    //Compose the type of result, which is a Stage Result
                    ResultType voResType = new ResultType();
                    voResType.setId(Utils.RESULT_STAGE_ID);
                    voResType.setDescription(Utils.RESULT_STAGE_DESC);
                    voRes.setResultType(voResType);
                    //Transform the start time value
                    try {
                        String vcTime = COL_START[vnColIndex]>=0?vaRecord[COL_START[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"";
                        java.util.Date vdTime = Utils.formatRelativeTimeFromBase(vcTime, 
                                voSta.getBaseDate(), voSta.getBaseTime());
                        voRes.setStartTime(vdTime);
                    }catch(Exception eStart) {
                        //Nothing to do
                    }
                    String vcNc = (COL_NC[vnColIndex]>=0?vaRecord[COL_NC[vnColIndex]].trim().replaceAll("\"", "").toUpperCase():"");
                    voRes.setStatusCode(Utils.STATUS_OK_ID);
                    if (vcNc.equals("X") || vcNc.equals("1"))
                        voRun.setIsNc(Boolean.TRUE);
                    else
                        voRun.setIsNc(Boolean.FALSE);
                    //Set value for points, penalty and bonus
                    voRes.setPointsFinal(BigDecimal.ZERO);
                    voRes.setPointsPenalty(BigDecimal.ZERO);
                    voRes.setPointsAdjusted(BigDecimal.ZERO);
                    voRes.setPointsBonus(BigDecimal.ZERO);
                    voRes.setTimeAdjusted(BigDecimal.ZERO);
                    voRes.setTimeBonus(BigDecimal.ZERO);
                    voRes.setTimeNeutralization(BigDecimal.ZERO);
                    voRes.setTimePenalty(BigDecimal.ZERO);
                    //Add the result to the list
                    vlRes.add(voRes);
                    //Add the list to the runner data
                    voRun.setRunnerResultList(vlRes);
                    //Now, the club of the runner
                    if (!(COL_CLU_ID[vnColIndex]>=0?vaRecord[COL_CLU_ID[vnColIndex]].trim().replaceAll("\"", ""):"").equals("")) {
                        Club voClu = new Club();
                        voClu.setId("");
                        voClu.setUuid("");
                        voClu.setOeKey(COL_CLU_ID[vnColIndex]>=0?vaRecord[COL_CLU_ID[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voClu.setCity(COL_CLU_CITY[vnColIndex]>=0?vaRecord[COL_CLU_CITY[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voClu.setShortName(COL_CLU_SHORT[vnColIndex]>=0?vaRecord[COL_CLU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voClu.setLongName(COL_CLU_SHORT[vnColIndex]>=0?vaRecord[COL_CLU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        //Add the club to the runner
                        voRun.setClub(voClu);
                    }
                    //Add the runner to the list of runners
                    vlRun.add(voRun);
                    //Set the list of runners to the class again
                    voCla.setRunnerList(vlRun);
                    //Remove the previous contents of the class from the HashMap and insert it again
                    vlCla.remove(vcClaId);
                    vlCla.put(vcClaId, voCla);
                } else {
                    if (oLog!=null)
                        oLog.warn("CSV File with not enough fields to process it ?");
                }
            }
            //Add the lis of classes to the stage
            List<Clazz> vlClaNew = new ArrayList<>(vlCla.values());
            voSta.setClazzList(vlClaNew);
            //Add the stage to the event
            ArrayList<Stage> vlSta = new ArrayList<>();
            vlSta.add(voSta);
            voEve.setStageList(vlSta);
        } else {
            if (oLog!=null)
                oLog.warn("File with no lines to process");
        }
        return voEve;
    }*/

    @Override
    public Event convertResultList (String pcFile) {
        File voFile = new File(pcFile);
        Event voResul = convertResultList (voFile);
        voFile = null;
        return voResul;
    }
    @Override
    public Event convertResultList (File poFile) {
        Event voEve = null;
        if (oEve!=null) {
            List<String> plResult = new ArrayList<>();
            BufferedReader voBr = null;
            String vcLine = "";
            try {
                //Loops the file and filters line by line
                voBr = new BufferedReader(new InputStreamReader(new FileInputStream(poFile), cEncoding));
                while ((vcLine = voBr.readLine()) != null) {
                    plResult.add(vcLine);
                }
                voBr.close();
                voEve = convertResultListSingleStageSITiming (plResult);
            } catch (Exception e) {
                if (oLog!=null)
                    oLog.error("Exception reading result list lines", e);
                voEve = null;
            }        
        }
        return voEve;
    }    
    /**
     * Given a representation of CSV for result list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the CSV file; this method is for 1-stage, classic Foot-O event
     * came in the CSV file; this method is for 1-stage, Scoring or Rogaine event
     * The results can come from OEScore2010 or OEScoreV12
     * @param plResult List<String> List with elements for each line of a CSV file for result list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults)
     */
    // TODO: Manage Result List. In progress...
    public Event convertResultListSingleStageSITiming (List<String> plResult) {
        Event voEve = null;
        //HashMap to store Classes. For each class its runners. Runners should be ordered by class in the CSV but, what if not?...
        HashMap<String, Clazz> vlCla = new HashMap<>();
        ArrayList<Control> vlCon = new ArrayList<>();
        HashMap<String, String> vlStations = new HashMap<>();
        boolean vbRadio = false;
        boolean vbSplit = false;
        //Flag to say whether the results include radiocontrols
        // TODO: Not implemented YET
//        if (getcResultsType().equals(ConverterToModel.RES_RADIO))
//            vbRadio = true;
        //Flag to say whether the results include breakdown of results (splits)
        if (getcResultsType().equals(ConverterToModel.RES_BREAKDOWN))
            vbSplit = true;
        //Depending on the type of file, the columns of the fields are different
        int vnColIndex = this.getIndexFromContentsAndSource();       
        try {
            //Process the contents
            if (plResult!=null) {
                //Event's data
                voEve = Utils.copyBasicEventData(oEve);
                //Stage's data
                Stage voSta = Utils.copyBasicOneStageData(oEve);
                //First line contains the name of the columns
                String vcLine = "";
                //Loop starts at second line
                for (int i=1; i<plResult.size(); i++) {
                    vcLine = plResult.get(i);
                    String[] vaRecord = vcLine.split(cSeparator);
                    if (vaRecord.length>=34) {
                        String teamInfo = COL_NAME[vnColIndex]>=0?vaRecord[COL_NAME[vnColIndex]].trim().replaceAll("\"", ""):"";
                        String[] teamInfo_arr = teamInfo.split("\\|", 2);
                        String teamName = teamInfo_arr[0].trim().replaceAll("\"", "");
                        String membersNames = teamInfo_arr.length > 1 ? teamInfo_arr[1].trim().replaceAll("~", " / ") : "";

                        String vcClaId = (COL_CAT_ID[vnColIndex]>=0?vaRecord[COL_CAT_ID[vnColIndex]].trim().replaceAll("\"", ""):"");
                        String vcClaShort = (COL_CAT_SHORT[vnColIndex]>=0?vaRecord[COL_CAT_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        String vcClaLong = (COL_CAT_LONG[vnColIndex]>=0?vaRecord[COL_CAT_LONG[vnColIndex]].trim().replaceAll("\"", ""):"");
                        //Search for the class in the HashMap
                        Clazz voCla = new Clazz();
                        if (vlCla.containsKey(vcClaId)) { //vcClaId
                            voCla = vlCla.get(vcClaId); //vcClaId
                        } else {
                            voCla.setId("");
                            voCla.setUuid("");
                            voCla.setOeKey(vcClaId); //vcClaId
                            voCla.setShortName(vcClaShort); //vcClaShort
                            voCla.setLongName(vcClaLong); //vcClaLong
                        }
                        //Get or create the list of radiocontrols of the class
                        ArrayList<ClazzControl> vlClaCon = (voCla.getClazzControlList()!=null?(ArrayList)voCla.getClazzControlList():new ArrayList<>());
                        //Now, the course of the class
                        if (voCla.getCourse()==null && !vaRecord[COL_COU_ID[vnColIndex]].trim().replaceAll("\"", "").equals("")) {
                        //if (voCla.getCourse()==null && !teamName.equals("")) {
                            Course voCou = new Course();
                            voCou.setId("");
                            voCou.setUuid("");
                            voCou.setOeKey(COL_COU_ID[vnColIndex]>=0?vaRecord[COL_COU_ID[vnColIndex]].trim().replaceAll("\"", ""):"");
                            voCou.setShortName(COL_COU_SHORT[vnColIndex]>=0?vaRecord[COL_COU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                            //voCou.setOeKey(teamName); //vcClaShort
                            //voCou.setShortName(teamName);
                            voCou.setDistance(COL_COU_DIST[vnColIndex]>=0?vaRecord[COL_COU_DIST[vnColIndex]].trim().replaceAll("\"", ""):"");
                            voCou.setClimb(COL_COU_CLIMB[vnColIndex]>=0?vaRecord[COL_COU_CLIMB[vnColIndex]].trim().replaceAll("\"", ""):"");
                            int vnControls = 0;
                            try {
                                vnControls = Integer.parseInt(COL_COU_CONTROLS[vnColIndex]>=0?vaRecord[COL_COU_CONTROLS[vnColIndex]].trim().replaceAll("\"", ""):"0");
                            }catch (Exception eNumCon){
                                //Nothing to do
                            };
                            voCou.setControls(vnControls);
                            //Add the course to the class
                            voCla.setCourse(voCou);
                        }
                        //Process the runner and put it on the class
                        List<Runner> vlRun = voCla.getRunnerList();
                        if (vlRun==null)
                            vlRun = new ArrayList<>();
                        Runner voRun = new Runner();
                        voRun.setId("");
                        voRun.setUuid("");
                        voRun.setBibNumber(COL_BIB[vnColIndex]>=0?vaRecord[COL_BIB[vnColIndex]].trim().replaceAll("\"", ""):"");
                        //voRun.setBibAlt(COL_BIBALT[vnColIndex]>=0?vaRecord[COL_BIBALT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voRun.setBibAlt("");
                        String siCard = COL_SICARD[vnColIndex]>=0?vaRecord[COL_SICARD[vnColIndex]].trim().replaceAll("\"", ""):"";
                        voRun.setSicard(siCard.split("~")[0]);
                        //voRun.setSicardAlt(COL_SICARDALT[vnColIndex]>=0?vaRecord[COL_SICARDALT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voRun.setSicardAlt("");
                        voRun.setDbId(COL_DBID[vnColIndex]>=0?vaRecord[COL_DBID[vnColIndex]].trim().replaceAll("\"", ""):"");
                        //voRun.setIofId(COL_IOFID[vnColIndex]>=0?vaRecord[COL_IOFID[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voRun.setIofId("");
                        /*try { //SI-Timing has no birth date
                            String vcBirth = (COL_BIRTH[vnColIndex]>=0?vaRecord[COL_BIRTH[vnColIndex]].trim().replaceAll("\"", ""):"");
                            java.util.Date vdBirth = Utils.parse(vcBirth, "dd/MM/yyyy");
                            if (vdBirth==null) {
                                vdBirth = Utils.parse(vcBirth, "MM/dd/yyyy");
                            }
                            voRun.setBirthDate(vdBirth);
                        }catch(Exception eDate) {
                            //Nothing to do
                        }*/
                        //If the event belongs to FEDO, some fields come in Num or Text columns
                        // Not implemented
                        /*if (oEve.getFederation()!=null) {
                            if (oEve.getFederation().getId().equals("FEDO")) {
                                //IOF ID
                                voRun.setIofId(COL_IOFID_FEDO[vnColIndex]>=0?vaRecord[COL_IOFID_FEDO[vnColIndex]].trim().replaceAll("\"", ""):"");
                                //Birthdate
                                try {
                                    String vcBirth = (COL_BIRTH_FEDO[vnColIndex]>=0?vaRecord[COL_BIRTH_FEDO[vnColIndex]].trim().replaceAll("\"", ""):"");
                                    java.util.Date vdBirth = Utils.parse(vcBirth, "dd/MM/yyyy");
                                    if (vdBirth==null) {
                                        vdBirth = Utils.parse(vcBirth, "MM/dd/yyyy");
                                    }
                                    voRun.setBirthDate(vdBirth);
                                }catch(Exception eDate) {
                                    //Nothing to do
                                }
                                //License
                                voRun.setLicense(COL_LIC_FEDO[vnColIndex]>=0?vaRecord[COL_LIC_FEDO[vnColIndex]].trim().replaceAll("\"", ""):"");
                                //DNI
                                voRun.setNationalId(COL_DNI_FEDO[vnColIndex]>=0?vaRecord[COL_DNI_FEDO[vnColIndex]].trim().replaceAll("\"", ""):"");
                            }
                        }*/
                        voRun.setLastName(membersNames);
                        //voRun.setLastName(COL_LASTNAME[vnColIndex]>=0?vaRecord[COL_LASTNAME[vnColIndex]].trim().replaceAll("\"", ""):"");
                        //voRun.setFirstName(COL_FIRSTNAME[vnColIndex]>=0?vaRecord[COL_FIRSTNAME[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voRun.setFirstName("");
                        //voRun.setSex((COL_SEX[vnColIndex]>=0?(vaRecord[COL_SEX[vnColIndex]].length()>0?vaRecord[COL_SEX[vnColIndex]].trim().replaceAll("\"", "").charAt(0):' '):' '));
                        //voRun.setTelephone1(COL_TEL1[vnColIndex]>=0?vaRecord[COL_TEL1[vnColIndex]].trim().replaceAll("\"", ""):"");
                        //voRun.setTelephone2(COL_TEL2[vnColIndex]>=0?vaRecord[COL_TEL2[vnColIndex]].trim().replaceAll("\"", ""):"");
                        //voRun.setEmail(COL_MAIL[vnColIndex]>=0?vaRecord[COL_MAIL[vnColIndex]].trim().replaceAll("\"", ""):"");
                        //Start Time is set in a first element of RunnerResult List
                        ArrayList<RunnerResult> vlRes = new ArrayList<>();
                        RunnerResult voRes = new RunnerResult();
                        voRes.setId("");
                        voRes.setStageOrder(voSta.getOrderNumber());
                        voRes.setLegNumber(1);
                        //Compose the type of result, which is a Stage Result
                        ResultType voResType = new ResultType();
                        voResType.setId(Utils.RESULT_STAGE_ID);
                        voResType.setDescription(Utils.RESULT_STAGE_DESC);
                        voRes.setResultType(voResType);
                        //Transform the start time value
                        try {
                            String vcTime = COL_START[vnColIndex]>=0?vaRecord[COL_START[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"";
                            java.util.Date vdTime = Utils.formatRelativeTimeFromBase(vcTime, 
                                    voSta.getBaseDate(), voSta.getBaseTime());
                            voRes.setStartTime(vdTime);
                        }catch(Exception eStart) {
                            //Nothing to do
                        }
                        //Transform the finish time value
                        try {
                            String vcTime = COL_FINISH[vnColIndex]>=0?vaRecord[COL_FINISH[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"";
                            java.util.Date vdTime = Utils.formatRelativeTimeFromBase(vcTime, 
                                    voSta.getBaseDate(), voSta.getBaseTime());
                            voRes.setFinishTime(vdTime);
                        }catch(Exception eFinish) {
                            //Nothing to do
                        }      
                        //Converts time to a value in seconds
                        double vnTimeSecs = Utils.formatTimeInSeconds(COL_TIME[vnColIndex]>=0?vaRecord[COL_TIME[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"");
                        //Using String constructor to avoid problems with floating point approximations
                        BigDecimal vnTimeSeconds = new BigDecimal(vnTimeSecs + "");
                        //If it has no decimals, stores only the integer part
                        voRes.setTimeSeconds(Utils.isWhole(vnTimeSeconds)?new BigDecimal(vnTimeSeconds.longValue()+""):vnTimeSeconds);
                        //Get the status
                        String vcStatus = COL_STATUS[vnColIndex]>=0?vaRecord[COL_STATUS[vnColIndex]].trim().replaceAll("\"", ""):"0";
                        if (vcStatus.equals(""))
                            vcStatus = "0";
                        voRes.setStatusCode(vcStatus.charAt(0));
                        //Check if it's a NotCompeting Runner. If so, change the status code
                        String vcNc = (COL_NC[vnColIndex]>=0?vaRecord[COL_NC[vnColIndex]].trim().replaceAll("\"", "").toUpperCase():"");
                        if (vcNc.equals("Y"))
                            voRun.setIsNc(Boolean.TRUE);
                        else
                            voRun.setIsNc(Boolean.FALSE);
                        //Get the position
                        int vnPosition = 0;
                        try {
                            vnPosition = Integer.parseInt(COL_POSITION[vnColIndex]>=0?vaRecord[COL_POSITION[vnColIndex]].trim().replaceAll("\"", ""):"0");
                        }catch (Exception ePos){
                            //Nothing to do
                        };
                        voRes.setPosition(vnPosition);
                        //Calculates time behind the first of the class
                        BigDecimal vnTimeBehind = this.calculateTimeBehind(vlRun, vnTimeSeconds);
                        //If it has no decimals, stores only the integer part
                        voRes.setTimeBehind(Utils.isWhole(vnTimeBehind)?new BigDecimal(vnTimeBehind.longValue()+""):vnTimeBehind);
                        //Set remainder fields for points and times
                        voRes.setPointsAdjusted(BigDecimal.ZERO);
                        int vnPoints = 0;
                        try {
                            vnPoints = Integer.parseInt(COL_FINALPOINTS[vnColIndex]>=0?vaRecord[COL_FINALPOINTS[vnColIndex]].trim().replaceAll("\"", ""):"0");
                        }catch(Exception ePoints1) {
                            vnPoints = 0;
                        }
                        voRes.setPointsFinal(new BigDecimal(vnPoints).setScale(2, java.math.RoundingMode.HALF_UP));
                        try {
                            vnPoints = Integer.parseInt(COL_BONUS[vnColIndex]>=0?vaRecord[COL_BONUS[vnColIndex]].trim().replaceAll("\"", ""):"0");
                        }catch(Exception ePoints1) {
                            vnPoints = 0;
                        }
                        voRes.setPointsBonus(new BigDecimal(vnPoints).setScale(2, java.math.RoundingMode.HALF_UP));
                        try {
                            vnPoints = Integer.parseInt(COL_PENALTY[vnColIndex]>=0?vaRecord[COL_PENALTY[vnColIndex]].trim().replaceAll("\"", ""):"0");
                        }catch(Exception ePoints1) {
                            vnPoints = 0;
                        }
                        voRes.setPointsPenalty(new BigDecimal(vnPoints).setScale(2, java.math.RoundingMode.HALF_UP));
                        voRes.setTimeAdjusted(BigDecimal.ZERO);
                        voRes.setTimeBonus(BigDecimal.ZERO);
                        voRes.setTimeNeutralization(BigDecimal.ZERO);
                        voRes.setTimePenalty(BigDecimal.ZERO);
                        //Sets contributory to true by default but this is only for several days totalization
                        voRes.setContributory(true);
                        //Now, the club of the runner
                        if (!(COL_CLU_ID[vnColIndex]>=0?vaRecord[COL_CLU_ID[vnColIndex]].trim().replaceAll("\"", ""):"").equals("")) {
                            Club voClu = new Club();
                            voClu.setId("");
                            voClu.setUuid("");
                            voClu.setOeKey(COL_CLU_ID[vnColIndex]>=0?vaRecord[COL_CLU_ID[vnColIndex]].trim().replaceAll("\"", ""):"");
                            voClu.setCity(COL_CLU_CITY[vnColIndex]>=0?vaRecord[COL_CLU_CITY[vnColIndex]].trim().replaceAll("\"", ""):"");
                            //voClu.setShortName(COL_CLU_SHORT[vnColIndex]>=0?vaRecord[COL_CLU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                            //voClu.setLongName(COL_CLU_SHORT[vnColIndex]>=0?vaRecord[COL_CLU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                            voClu.setShortName(teamName);
                            voClu.setLongName(teamName);
                            //Add the club to the runner
                            voRun.setClub(voClu);
                        }
                        //Now, process Splits or radiocontrols, if present
                        if ((vbSplit && COL_SPLIT_STATION[vnColIndex]>=0) ||
                                (vbRadio && COL_RADIO_NUM[vnColIndex]>=0)) {
                            int vnSplOrder = 1;
                            ArrayList<Split> vlSpl = new ArrayList<>();
                            int vnCol = (vbSplit?COL_SPLIT_STATION[vnColIndex]:COL_RADIO_NUM[vnColIndex]);
                            boolean vbStop = false;
                            while (vnCol<vaRecord.length && !vbStop) {
                                int vnSplPos = 0;
                                String vcStation = vaRecord[vnCol].trim().replaceAll("\"", "");
                                if (vbRadio) {
                                    vcStation = vaRecord[vnCol+1].trim().replaceAll("\"", "");
                                    try {
                                        vnSplOrder = Integer.parseInt(vaRecord[vnCol].trim().replaceAll("\"", "0"));
                                    }catch(Exception eOrder) {
                                        vnSplOrder = 0;
                                    }
                                    try {
                                        vnSplPos = Integer.parseInt(vaRecord[vnCol+3].trim().replaceAll("\"", "0"));
                                    }catch (Exception ePos) {
                                        vnSplPos = 0;
                                    }
                                }
                                //Continue processing while there are stations 
                                if (!vcStation.equals("")) {
                                    Split voSpl = new Split();
                                    voSpl.setStation(vcStation);
                                    try {
                                        int vnSplPoints = 0;
                                        String vcTime = "";
                                        boolean vbEmptyPoints = false;
                                        if (vbSplit) {
                                            try {
                                                if (vaRecord[vnCol+2].trim().equals("")) {
                                                    vbEmptyPoints = true;
                                                }
                                                vnSplPoints = Integer.parseInt(vaRecord[vnCol+2].trim().replaceAll("\"", "0"));
                                            }catch(Exception eSplPoints) {
                                                vnSplPoints = 0;
                                            }
                                            vcTime = (vaRecord[vnCol+1].trim().replaceAll("\"", ""));
                                        } else {
                                            try {
                                                if (vaRecord[vnCol+2].trim().equals("")) {
                                                    vbEmptyPoints = true;
                                                }
                                                vnSplPoints = Integer.parseInt(vaRecord[vnCol+2].trim().replaceAll("\"", "0"));
                                            }catch(Exception eSplPoints) {
                                                vnSplPoints = 0;
                                            }
                                            vcTime = (vaRecord[vnCol+3].trim().replaceAll("\"", ""));
                                        }
                                        voSpl.setPoints(vnSplPoints);
                                        if (!vcTime.equals("")) {
                                            java.util.Date voSplitTime = Utils.formatRelativeTimeFromBase(vcTime, 
                                                    voRes.getStartTime());
                                            voSpl.setReadingMilli(new BigInteger(voSplitTime.getTime()+""));
                                            voSpl.setReadingTime(voSplitTime);
                                            //Converts time to a value in seconds
                                            vnTimeSecs = Utils.formatTimeInSeconds(vcTime);
                                            //Using String constructor to avoid problems with floating point approximations
                                            vnTimeSeconds = new BigDecimal(vnTimeSecs + "");
                                            //If it has no decimals, stores only the integer part
                                            voSpl.setTimeSeconds(Utils.isWhole(vnTimeSeconds)?new BigDecimal(vnTimeSeconds.longValue()+""):vnTimeSeconds);
                                            //-----------------------------------------------------------------------------------------------------
                                            //Same code as in OE. I'm not going to apply it because OEScore exports all of the punched controls ordered by time; it doesn't write non-course controls at the end
                                            //If the order number is greater than the number of controls, then it's an additional split
                                            //if (vnSplOrder>voCla.getCourse().getControls()) {
                                            //    voSpl.setStatus(Utils.SPLIT_STATUS_ADDITIONAL);
                                            //} else {
                                            //    voSpl.setStatus("");
                                            //}
                                        } else {
                                            //If there is no time, then it's missing
                                            //voSpl.setStatus(Utils.SPLIT_STATUS_MISSING);
                                        }
                                        //If some control is revisited, in the CSV it's located at the end of the splits with points column empty. Then, set as Additional
                                        if (vbEmptyPoints) {
                                            voSpl.setStatus(Utils.SPLIT_STATUS_ADDITIONAL);
                                        } else {
                                            voSpl.setStatus("");
                                        }
                                    }catch(Exception eSplit) {
                                        voSpl.setReadingMilli(null);
                                        voSpl.setReadingTime(null);
                                        voSpl.setTimeSeconds(null);
                                        voSpl.setPoints(0);
                                        //voSpl.setStatus(Utils.SPLIT_STATUS_MISSING);
                                    }
                                    voSpl.setPosition(vnSplPos);
                                    voSpl.setBibRunner(voRun.getBibNumber());
                                    voSpl.setOrderNumber(vnSplOrder);
                                    voSpl.setSicard(voRun.getSicard());
                                    voSpl.setStageOrder(voSta.getOrderNumber());
                                    //Add the object to the list
                                    vlSpl.add(voSpl);
                                    //If it's a result with radiocontrols, compose the whole list of controls and the list of controls of the class
                                    if (vbRadio) {
                                        Control voCon = new Control();
                                        voCon.setStation(vcStation);
                                        if (!vlStations.containsKey(vcStation)) {
                                            vlStations.put(vcStation, vcStation);
                                            vlCon.add(voCon);
                                        }
                                        //If it's the first runner of the class, add the control to the list of controls of the class
                                        if (vlCla.isEmpty()) {
                                            ClazzControl voClaCon = new ClazzControl();
                                            voClaCon.setControl(voCon);
                                            vlClaCon.add(voClaCon);
                                        }
                                    }
                                    //Increase the counter of the split order
                                    vnSplOrder++;
                                    //Increase the column counter
                                    if (vbSplit)
                                        vnCol += (COL_SPLIT_INC[vnColIndex]>0?COL_SPLIT_INC[vnColIndex]:2);
                                    else
                                        vnCol += (COL_RADIO_INC[vnColIndex]>0?COL_RADIO_INC[vnColIndex]:2);
                                } else {
                                    vbStop = true;
                                }
                            }
                            //Add the list of splits to the results
                            voRes.setSplitList(vlSpl);
                        }
                        //Add the result to the list
                        vlRes.add(voRes);
                        //Add the list to the runner data
                        voRun.setRunnerResultList(vlRes);
                        //Add the runner to the list of runners
                        vlRun.add(voRun);
                        //Set the list of runners to the class again
                        voCla.setRunnerList(vlRun);
                        //If it's a result with radiocontrols, add the list of class-controls to the class
                        if (vbRadio && !vlClaCon.isEmpty()) {
                            voCla.setClazzControlList(vlClaCon);
                        }
                        //Remove the previous contents of the class from the HashMap and insert it again
                        vlCla.remove(vcClaId); //vcClaId
                        vlCla.put(vcClaId, voCla); //vcClaId
                    } else {
                        if (oLog!=null)
                            oLog.warn("CSV File with not enough fields to process it ?");
                    }
                }
                //Add the lis of classes to the stage
                List<Clazz> vlClaNew = new ArrayList<>(vlCla.values());
                voSta.setClazzList(vlClaNew);
                //If it's a result with radiocontrols, add the list of controls to the stage
                if (vbRadio && !vlCon.isEmpty()) {
                    voSta.setControlList(vlCon);
                }
                //Add the stage to the event
                ArrayList<Stage> vlSta = new ArrayList<>();
                vlSta.add(voSta);
                voEve.setStageList(vlSta);
            } else {
                if (oLog!=null)
                    oLog.warn("File with no lines to process");
            }
        }catch (Exception e) {
            if (oLog!=null)
                oLog.error("Exception converting results", e);
        }
        return voEve;
    }
    /**
     * When processing CSV from OEScore, depending on OEScore version and type of contents,
     * this method returns an index, meaning the place things like column positions, etc,
     * is located
     * OEScore2010, STARTS or GLOBAL RESULTS = 0
     * OEScore2010, BREAKDOWN (SPLITS) = 1
     * OEScore2010, RADIOCONTROL (INTERMEDIATE) = 2
     * OEScoreV12, STARTS or GLOBAL RESULTS = 3
     * OEScoreV12, BREAKDOWN (SPLITS) = 4
     * OEScoreV12, RADIOCONTROL (INTERMEDIATE) = 5
     * @return int Index of the values
     */
    @JsonIgnore
    public int getIndexFromContentsAndSource() {
        int vnResul = 0;
        try {
            if (getcSource().equals(ConverterToModel.SRC_OESCORE2010)) {
                if ((getcContents().equals(ConverterToModel.CONTENTS_RESULT) && getcResultsType().equals(ConverterToModel.RES_TOTALS)) ||
                       (getcContents().equals(ConverterToModel.CONTENTS_START)))
                    vnResul = 0;
                else if (getcResultsType().equals(ConverterToModel.RES_BREAKDOWN))
                    vnResul = 1;
                else if (getcResultsType().equals(ConverterToModel.RES_RADIO))
                    vnResul = 2;
            } else if (getcSource().equals(ConverterToModel.SRC_OESCOREV12)) {
                if ((getcContents().equals(ConverterToModel.CONTENTS_RESULT) && getcResultsType().equals(ConverterToModel.RES_TOTALS)) ||
                       (getcContents().equals(ConverterToModel.CONTENTS_START)))
                    vnResul = 3;
                else if (getcResultsType().equals(ConverterToModel.RES_BREAKDOWN))
                    vnResul = 4;
                else if (getcResultsType().equals(ConverterToModel.RES_RADIO))
                    vnResul = 5;
            }        
        }catch (Exception e) {
            vnResul = 0;
        }
        return vnResul;
    }
    /**
     * Takes a list of runners and their times, looks for the runner in the first
     * position and substract the given time to calculate the difference between
     * the current runner and the first one
     * @param plRun List<eu.oreplay.db.Runner> List of runners
     * @param pnCurrent BigDecimal Time(seconds) of the current runner
     * @return BigDecimal The difference or zero
     */
    public BigDecimal calculateTimeBehind (List<Runner> plRun, BigDecimal pnCurrent) {
        BigDecimal vnResul = BigDecimal.ZERO;
        boolean vbFound = false;
        try {
            //If the current value is greater than zero
            if (plRun!=null && pnCurrent.compareTo(BigDecimal.ZERO)==1) {
                int i = 0;
                while (i<plRun.size() && !vbFound) {
                    Runner voRun = plRun.get(i);
                    if (voRun!=null && voRun.getRunnerResultList()!=null) {
                        if (!voRun.getRunnerResultList().isEmpty()) {
                            RunnerResult voRes = voRun.getRunnerResultList().get(0);
                            if (voRes.getPosition()==1) {
                                vnResul = pnCurrent.subtract(voRes.getTimeSeconds());
                                vbFound = true;
                            }
                        }
                    }
                    i++;
                }
            }
        }catch(Exception e) {
            vnResul = BigDecimal.ZERO;
        }
        return vnResul;
    }
}