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
 * Class with methods to convert data in CSV OE format to OReplay data model;
 * in this case, for classic orienteering competitions
 * @author javier.arufe
 */
public class ConverterCsvOEToModel extends ConverterToModel{
    private String cSeparator = ";";
    private String cEncoding = Utils.ENCODING_UTF_8;
    private eu.oreplay.db.Event oEve = null; //Event/Stage basic information to perform the calculations (UUID, base date, base time, description, etc)
    private final String[] FILE = {"OE0012", "OE0014", "OE0016", "OE0012_V12", "OE0014_V12", "OE0016_V12"};
    private final int[] COL_BIB = {1, 1, 1, 2, 2, 2};
    private final int[] COL_BIBALT = {2, 2, 2, 3, 3, 3};
    private final int[] COL_SICARD = {3, 3, 3, 4, 4, 4};
    private final int[] COL_SICARDALT = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_DBID = {4, 4, 4, 5, 5, 5};
    private final int[] COL_IOFID = {-1, -1, -1, 6, 6, 6};
    private final int[] COL_IOFID_FEDO = {33, 33, 33, 36, 36, 36};
    private final int[] COL_LASTNAME = {5, 5, 5, 7, 7, 7};
    private final int[] COL_FIRSTNAME = {6, 6, 6, 8, 8, 8};
    private final int[] COL_BIRTH = {-1, -1, -1, 9, 9, 9};
    private final int[] COL_BIRTH_FEDO = {37, 37, 37, 9, 9, 9};
    private final int[] COL_SEX = {8, 8, 8, 11, 11, 11};
    private final int[] COL_NC = {10, 10, 10, 13, 13, 13};
    private final int[] COL_START = {11, 11, 11, 14, 14, 14};
    private final int[] COL_FINISH = {12, 12, 12, 15, 15, 15};
    private final int[] COL_TIME = {13, 13, 13, 16, 16, 16};
    private final int[] COL_STATUS = {14, 14, 14, 17, 17, 17};
    private final int[] COL_BEHIND = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_CLU_ID = {18, 18, 18, 21, 21, 21};
    private final int[] COL_CLU_CITY = {19, 19, 19, 22, 22, 22};
    private final int[] COL_CLU_SHORT = {20, 20, 20, 23, 23, 23};
    private final int[] COL_CLU_LONG = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_CAT_ID = {24, 24, 24, 27, 27, 27};
    private final int[] COL_CAT_SHORT = {25, 25, 25, 28, 28, 28};
    private final int[] COL_CAT_LONG = {26, 26, 26, 29, 29, 29};
    private final int[] COL_LIC_FEDO = {32, 32, 32, 35, 35, 35};
    private final int[] COL_DNI_FEDO = {35, 35, 35, 38, 38, 38};
    private final int[] COL_TEL1 = {44, 44, 44, 47, 47, 47};
    private final int[] COL_TEL2 = {45, 45, 45, 48, 48, 48};
    private final int[] COL_MAIL = {47, 47, 47, 50, 50, 50};
    private final int[] COL_COU_ID = {52, 52, 52, 57, 57, 57};
    private final int[] COL_COU_SHORT = {53, 53, 53, 58, 58, 58};
    private final int[] COL_COU_DIST = {54, 54, 54, 59, 59, 59};
    private final int[] COL_COU_CLIMB = {55, 55, 55, 60, 60, 60};
    private final int[] COL_COU_CONTROLS = {56, 56, 56, 61, 61, 61};
    private final int[] COL_POSITION = {57, 57, 57, 62, 62, 62};
    private final int[] COL_SPLIT_STATION = {-1, 60, -1, -1, 65, -1};
    private final int[] COL_SPLIT_TIME = {-1, 61, -1, -1, 66, -1};
    private final int[] COL_SPLIT_INC = {-1, 2, -1, -1, 2, -1};
    private final int[] COL_RADIO_NUM = {-1, -1, 59, -1, -1, 64};
    private final int[] COL_RADIO_STATION = {-1, -1, 60, -1, -1, 65};
    private final int[] COL_RADIO_TIME = {-1, -1, 61, -1, -1, 66};
    private final int[] COL_RADIO_POSITION = {-1, -1, 62, -1, -1, 67};
    private final int[] COL_RADIO_INC = {-1, -1, 4, -1, -1, 4};
    

    public ConverterCsvOEToModel() {
        super();
    }
    public ConverterCsvOEToModel (ConverterToModel poSrc) {
        super.copyValues(poSrc);
    }
    public ConverterCsvOEToModel(String pcSeparator, String pcEncoding) {
        cSeparator = pcSeparator;
        cEncoding = pcEncoding;
    }
    public ConverterCsvOEToModel(String pcSeparator, String pcEncoding, eu.oreplay.db.Event poEve) {
        cSeparator = pcSeparator;
        cEncoding = pcEncoding;
        oEve = poEve;
    }

    public void setSpecificProperties (String pcSeparator, String pcEncoding, eu.oreplay.db.Event poEve) {
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
    public eu.oreplay.db.Event convertEntryList (String pcFile) {
        File voFile = new File(pcFile);
        eu.oreplay.db.Event voResul = convertEntryList (voFile);
        voFile = null;
        return voResul;
    }
    @Override
    public eu.oreplay.db.Event convertEntryList (File poFile) {
        return null;
    }    

    @Override
    public eu.oreplay.db.Event convertStartList (String pcFile) {
        File voFile = new File(pcFile);
        eu.oreplay.db.Event voResul = convertStartList (voFile);
        voFile = null;
        return voResul;
    }
    @Override
    public eu.oreplay.db.Event convertStartList (File poFile) {
        eu.oreplay.db.Event voEve = null;
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
                voEve = convertStartListSingleStageClassicOE (plStart);
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
     * came in the CSV file; this method is for 1-stage, classic Foot-O event
     * The results can come from OE2010 or OEV12
     * @param plStart List<String> List with elements for each line of a CSV file for start list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertStartListSingleStageClassicOE (List<String> plStart) {
        eu.oreplay.db.Event voEve = null;
        //Depending on the type of file, the columns of the fields are different
        int vnColIndex = this.getIndexFromContentsAndSource();       
        //HashMap to store Classes. For each class its runners. Runners shoulb be ordered by class in the CSV but, what if not?...
        HashMap<String, eu.oreplay.db.Clazz> vlCla = new HashMap<>();
        try {
            if (plStart!=null) {
                //Event's data
                voEve = Utils.copyBasicEventData(oEve);
                //Stage's data
                eu.oreplay.db.Stage voSta = Utils.copyBasicOneStageData(oEve);
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
                        eu.oreplay.db.Clazz voCla = new eu.oreplay.db.Clazz();
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
                            eu.oreplay.db.Course voCou = new eu.oreplay.db.Course();
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
                        List<eu.oreplay.db.Runner> vlRun = voCla.getRunnerList();
                        if (vlRun==null)
                            vlRun = new ArrayList<>();
                        eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
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
                        ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<>();
                        eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                        voRes.setId("");
                        voRes.setStageOrder(voSta.getOrderNumber());
                        voRes.setLegNumber(1);
                        //Compose the type of result, which is a Stage Result
                        eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
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
                        //Add the result to the list
                        vlRes.add(voRes);
                        //Add the list to the runner data
                        voRun.setRunnerResultList(vlRes);
                        //Now, the club of the runner
                        if (!(COL_CLU_ID[vnColIndex]>=0?vaRecord[COL_CLU_ID[vnColIndex]].trim().replaceAll("\"", ""):"").equals("")) {
                            eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
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
                if (oLog!=null)
                    oLog.info("Number of processed lines: " + plStart.size());
                //Add the lis of classes to the stage
                List<eu.oreplay.db.Clazz> vlClaNew = new ArrayList<>(vlCla.values());
                voSta.setClazzList(vlClaNew);
                //Add the stage to the event
                ArrayList<eu.oreplay.db.Stage> vlSta = new ArrayList<>();
                vlSta.add(voSta);
                voEve.setStageList(vlSta);
            } else {
                if (oLog!=null)
                    oLog.warn("File with no lines to process");
            }
        }catch (Exception e) {
            if (oLog!=null)
                oLog.error("Exception converting start list", e);
        }
        return voEve;
    }

    @Override
    public eu.oreplay.db.Event convertResultList (String pcFile) {
        File voFile = new File(pcFile);
        eu.oreplay.db.Event voResul = convertResultList (voFile);
        voFile = null;
        return voResul;
    }
    @Override
    public eu.oreplay.db.Event convertResultList (File poFile) {
        eu.oreplay.db.Event voEve = null;
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
                voEve = convertResultListSingleStageClassicOE (plResult);
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
     * The results can be global, breakdown (splits) or radiocontrol (intermediate)
     * The results can come from OE2010 or OEV12
     * @param plResult List<String> List with elements for each line of a CSV file for result list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertResultListSingleStageClassicOE (List<String> plResult) {
        eu.oreplay.db.Event voEve = null;
        //HashMap to store Classes. For each class its runners. Runners should be ordered by class in the CSV but, what if not?...
        HashMap<String, eu.oreplay.db.Clazz> vlCla = new HashMap<>();
        ArrayList<eu.oreplay.db.Control> vlCon = new ArrayList<>();
        HashMap<String, String> vlStations = new HashMap<>();
        boolean vbRadio = false;
        boolean vbSplit = false;
        //Flag to say whether the results include radiocontrols
        if (getcResultsType().equals(ConverterToModel.RES_RADIO))
            vbRadio = true;
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
                eu.oreplay.db.Stage voSta = Utils.copyBasicOneStageData(oEve);
                //First line contains the name of the columns
                String vcLine = "";
                //Loop starts at second line
                for (int i=1; i<plResult.size(); i++) {
                    vcLine = plResult.get(i);
                    String[] vaRecord = vcLine.split(cSeparator);
                    if (vaRecord.length>=57) {
                        String vcClaId = (COL_CAT_ID[vnColIndex]>=0?vaRecord[COL_CAT_ID[vnColIndex]].trim().replaceAll("\"", ""):"");
                        String vcClaShort = (COL_CAT_SHORT[vnColIndex]>=0?vaRecord[COL_CAT_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        String vcClaLong = (COL_CAT_LONG[vnColIndex]>=0?vaRecord[COL_CAT_LONG[vnColIndex]].trim().replaceAll("\"", ""):"");
                        //Search for the class in the HashMap
                        eu.oreplay.db.Clazz voCla = new eu.oreplay.db.Clazz();
                        if (vlCla.containsKey(vcClaId)) {
                            voCla = vlCla.get(vcClaId);
                        } else {
                            voCla.setId("");
                            voCla.setUuid("");
                            voCla.setOeKey(vcClaId);
                            voCla.setShortName(vcClaShort);
                            voCla.setLongName(vcClaLong);
                        }
                        //Get or create the list of radiocontrols of the class
                        ArrayList<eu.oreplay.db.ClazzControl> vlClaCon = (voCla.getClazzControlList()!=null?(ArrayList)voCla.getClazzControlList():new ArrayList<>());                    
                        //Now, the course of the class
                        if (voCla.getCourse()==null && !vaRecord[COL_COU_ID[vnColIndex]].trim().replaceAll("\"", "").equals("")) {
                            eu.oreplay.db.Course voCou = new eu.oreplay.db.Course();
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
                        List<eu.oreplay.db.Runner> vlRun = voCla.getRunnerList();
                        if (vlRun==null)
                            vlRun = new ArrayList<>();
                        eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
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
                        ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<>();
                        eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                        voRes.setId("");
                        voRes.setStageOrder(voSta.getOrderNumber());
                        voRes.setLegNumber(1);
                        //Compose the type of result, which is a Stage Result
                        eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
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
                        if (vcNc.equals("X") || vcNc.equals("1"))
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
                        voRes.setPointsBonus(BigDecimal.ZERO);
                        voRes.setPointsFinal(BigDecimal.ZERO);
                        voRes.setPointsPenalty(BigDecimal.ZERO);
                        voRes.setTimeAdjusted(BigDecimal.ZERO);
                        voRes.setTimeBonus(BigDecimal.ZERO);
                        voRes.setTimeNeutralization(BigDecimal.ZERO);
                        voRes.setTimePenalty(BigDecimal.ZERO);
                        //Sets contributory to true by default but this is only for several days totalization
                        voRes.setContributory(true);
                        //Now, the club of the runner
                        if (!(COL_CLU_ID[vnColIndex]>=0?vaRecord[COL_CLU_ID[vnColIndex]].trim().replaceAll("\"", ""):"").equals("")) {
                            eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                            voClu.setId("");
                            voClu.setUuid("");
                            voClu.setOeKey(COL_CLU_ID[vnColIndex]>=0?vaRecord[COL_CLU_ID[vnColIndex]].trim().replaceAll("\"", ""):"");
                            voClu.setCity(COL_CLU_CITY[vnColIndex]>=0?vaRecord[COL_CLU_CITY[vnColIndex]].trim().replaceAll("\"", ""):"");
                            voClu.setShortName(COL_CLU_SHORT[vnColIndex]>=0?vaRecord[COL_CLU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                            voClu.setLongName(COL_CLU_SHORT[vnColIndex]>=0?vaRecord[COL_CLU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                            //Add the club to the runner
                            voRun.setClub(voClu);
                        }
                        //Now, process Splits or radiocontrols, if present
                        if ((vbSplit && COL_SPLIT_STATION[vnColIndex]>=0) ||
                                (vbRadio && COL_RADIO_NUM[vnColIndex]>=0)) {
                            int vnSplOrder = 1;
                            ArrayList<eu.oreplay.db.Split> vlSpl = new ArrayList<>();
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
                                    eu.oreplay.db.Split voSpl = new eu.oreplay.db.Split();
                                    voSpl.setStation(vcStation);
                                    try {
                                        String vcTime = "";
                                        if (vbSplit)
                                            vcTime = (vaRecord[vnCol+1].trim().replaceAll("\"", ""));
                                        else
                                            vcTime = (vaRecord[vnCol+2].trim().replaceAll("\"", ""));
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
                                            //If the order number is greater than the number of controls, then it's an additional split
                                            if (vnSplOrder>voCla.getCourse().getControls()) {
                                                voSpl.setStatus(Utils.SPLIT_STATUS_ADDITIONAL);
                                            } else {
                                                voSpl.setStatus("");
                                            }
                                        } else {
                                            //If there is no time, then it's missing
                                            voSpl.setStatus(Utils.SPLIT_STATUS_MISSING);
                                        }
                                    }catch(Exception eSplit) {
                                        voSpl.setReadingMilli(null);
                                        voSpl.setReadingTime(null);
                                        voSpl.setTimeSeconds(null);
                                        voSpl.setStatus(Utils.SPLIT_STATUS_MISSING);
                                    }
                                    voSpl.setPosition(vnSplPos);
                                    voSpl.setBibRunner(voRun.getBibNumber());
                                    voSpl.setOrderNumber(vnSplOrder);
                                    voSpl.setPoints(0);
                                    voSpl.setSicard(voRun.getSicard());
                                    voSpl.setStageOrder(voSta.getOrderNumber());
                                    //Add the object to the list
                                    vlSpl.add(voSpl);
                                    //If it's a result with radiocontrols, compose the whole list of controls and the list of controls of the class
                                    if (vbRadio) {
                                        eu.oreplay.db.Control voCon = new eu.oreplay.db.Control();
                                        voCon.setStation(vcStation);
                                        if (!vlStations.containsKey(vcStation)) {
                                            vlStations.put(vcStation, vcStation);
                                            vlCon.add(voCon);
                                        }
                                        //If it's the first runner of the class, add the control to the list of controls of the class
                                        if (vlCla.isEmpty()) {
                                            eu.oreplay.db.ClazzControl voClaCon = new eu.oreplay.db.ClazzControl();
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
                        vlCla.remove(vcClaId);
                        vlCla.put(vcClaId, voCla);
                    } else {
                        if (oLog!=null)
                            oLog.warn("CSV File with not enough fields to process it ?");
                    }
                }
                //Add the lis of classes to the stage
                List<eu.oreplay.db.Clazz> vlClaNew = new ArrayList<>(vlCla.values());
                voSta.setClazzList(vlClaNew);
                //If it's a result with radiocontrols, add the list of controls to the stage
                if (vbRadio && !vlCon.isEmpty()) {
                    voSta.setControlList(vlCon);
                }
                //Add the stage to the event
                ArrayList<eu.oreplay.db.Stage> vlSta = new ArrayList<>();
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
     * When processing CSV from OE, depending on OE version and type of contents,
     * this method returns an index, meaning the place things like column positions, etc,
     * is located
     * OE2010, STARTS or GLOBAL RESULTS = 0
     * OE2010, BREAKDOWN (SPLITS) = 1
     * OE2010, RADIOCONTROL (INTERMEDIATE) = 2
     * OEV12, STARTS or GLOBAL RESULTS = 3
     * OEV12, BREAKDOWN (SPLITS) = 4
     * OEV12, RADIOCONTROL (INTERMEDIATE) = 5
     * @return int Index of the values
     */
    @JsonIgnore
    public int getIndexFromContentsAndSource() {
        int vnResul = 0;
        try {
            if (getcSource().equals(ConverterToModel.SRC_OE2010)) {
                if ((getcContents().equals(ConverterToModel.CONTENTS_RESULT) && getcResultsType().equals(ConverterToModel.RES_TOTALS)) ||
                       (getcContents().equals(ConverterToModel.CONTENTS_START)))
                    vnResul = 0;
                else if (getcResultsType().equals(ConverterToModel.RES_BREAKDOWN))
                    vnResul = 1;
                else if (getcResultsType().equals(ConverterToModel.RES_RADIO))
                    vnResul = 2;
            } else if (getcSource().equals(ConverterToModel.SRC_OEV12)) {
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
    public BigDecimal calculateTimeBehind (List<eu.oreplay.db.Runner> plRun, BigDecimal pnCurrent) {
        BigDecimal vnResul = BigDecimal.ZERO;
        boolean vbFound = false;
        try {
            //If the current value is greater than zero
            if (plRun!=null && pnCurrent.compareTo(BigDecimal.ZERO)==1) {
                int i = 0;
                while (i<plRun.size() && !vbFound) {
                    eu.oreplay.db.Runner voRun = plRun.get(i);
                    if (voRun!=null && voRun.getRunnerResultList()!=null) {
                        if (!voRun.getRunnerResultList().isEmpty()) {
                            eu.oreplay.db.RunnerResult voRes = voRun.getRunnerResultList().get(0);
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