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
 * Class with methods to convert data in CSV OS format to OReplay data model; 
 * in this case, for relay competitions
 * @author javier.arufe
 */
public class ConverterCsvOSToModel extends ConverterToModel{
    private String cSeparator = ";";
    private String cEncoding = Utils.ENCODING_UTF_8;
    private eu.oreplay.db.Event oEve = null; //Event/Stage basic information to perform the calculations (UUID, base date, base time, description, etc)
    private final String[] FILE = {"OS0012", "OS0014", "OS0016a", "OS0012_V12", "OS0014_V12", "OS0016"};
    private final int[] COL_TEA_BIB = {1, 1, 1, 1, 1, 1};
    private final int[] COL_TEA_BIBALT = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_TEA_NAME = {3, 18, 18, 3, 19, 19};
    private final int[] COL_TEA_NC = {5, -1, -1, 5, -1, -1};
    private final int[] COL_TEA_START = {6, -1, -1, 6, -1, -1};
    private final int[] COL_TEA_TIME = {7, -1, 30, 7, -1, 31};
    private final int[] COL_TEA_STATUS = {8, -1, 31, 8, -1, 32};
    private final int[] COL_TEA_POSITION = {9, -1, 32, 9, -1, 33};
    private final int[] COL_CLU_ID = {13, -1, -1, 13, -1, -1};
    private final int[] COL_CLU_CITY = {14, -1, -1, 14, -1, -1};
    private final int[] COL_CLU_SHORT = {15, -1, -1, 15, -1, -1};
    private final int[] COL_CLU_LONG = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_CAT_ID = {19, 19, 19, 19, 20, 20};
    private final int[] COL_CAT_SHORT = {20, 20, 20, 20, 21, 21};
    private final int[] COL_CAT_LONG = {21, -1, -1, 21, -1, -1};
    private final int[] COL_LEGS = {22, -1, -1, 22, -1, -1};
    private final int[] COL_LEGINDEX = {31, 0, 0, 31, 0, 0};
    private final int[] COL_LEGFIELDS = {14, 256, 256, 15, 256, 256};
    private final int[] COL_LEG = {1, 4, 4, 1, 4, 4};
    private final int[] COL_LASTNAME = {3, 6, 6, 3, 6, 6};
    private final int[] COL_FIRSTNAME = {4, 7, 7, 4, 7, 7};
    private final int[] COL_SEX = {6, 9, 9, 6, 9, 9};
    private final int[] COL_START = {7, 10, 10, 7, 10, 10};
    private final int[] COL_FINISH = {8, 11, 11, 8, 11, 11};
    private final int[] COL_TIME = {9, 12, 12, 9, 12, 12};
    private final int[] COL_STATUS = {10, 13, 13, 10, 13, 13};
    private final int[] COL_SICARD = {11, 14, 14, 11, 14, 14};
    private final int[] COL_SICARDALT = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_DBID = {13, 16, 16, 13, 16, 16};
    private final int[] COL_IOFID = {-1, -1, -1, 14, 17, 17};
    private final int[] COL_POSITION = {-1, 27, -1, -1, 28, -1};
    private final int[] COL_COU_ID = {-1, 21, 24, -1, 22, 25};
    private final int[] COL_COU_SHORT = {-1, 22, 25, -1, 23, 26};
    private final int[] COL_COU_DIST = {-1, 23, 26, -1, 24, 27};
    private final int[] COL_COU_CLIMB = {-1, 24, 27, -1, 25, 28};
    private final int[] COL_COU_CONTROLS = {-1, 25, 28, -1, 26, 29};
    private final int[] COL_COU_COMBINATION = {-1, 26, 29, -1, 27, 30};
    private final int[] COL_SPLIT_STATION = {-1, 30, -1, -1, 31, -1};
    private final int[] COL_SPLIT_TIME = {-1, 31, -1, -1, 32, -1};
    private final int[] COL_SPLIT_INC = {-1, 2, -1, -1, 2, -1};
    private final int[] COL_RADIO_NUM = {-1, -1, 33, -1, -1, 34};
    private final int[] COL_RADIO_STATION = {-1, -1, 34, -1, -1, 35};
    private final int[] COL_RADIO_TIME = {-1, -1, 35, -1, -1, 36};
    private final int[] COL_RADIO_POSITION = {-1, -1, 36, -1, -1, 37};
    private final int[] COL_RADIO_INC = {-1, -1, 4, -1, -1, 4};
    //NOT USED FOR RELAYS
    private final int[] COL_IOFID_FEDO = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_BIRTH = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_BIRTH_FEDO = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_BEHIND = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_LIC_FEDO = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_DNI_FEDO = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_TEL1 = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_TEL2 = {-1, -1, -1, -1, -1, -1};
    private final int[] COL_MAIL = {-1, -1, -1, -1, -1, -1};
    

    public ConverterCsvOSToModel() {
        super();
    }
    public ConverterCsvOSToModel (ConverterToModel poSrc) {
        super.copyValues(poSrc);
    }
    public ConverterCsvOSToModel(String pcSeparator, String pcEncoding) {
        cSeparator = pcSeparator;
        cEncoding = pcEncoding;
    }
    public ConverterCsvOSToModel(String pcSeparator, String pcEncoding, eu.oreplay.db.Event poEve) {
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
                voEve = convertStartListSingleStageRelayOS (plStart);
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
     * came in the CSV file; this method is for a relay stage
     * The results can come from OS2010 or OSV12
     * @param plStart List<String> List with elements for each line of a CSV file for start list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, teams, teasmresults, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertStartListSingleStageRelayOS (List<String> plStart) {
        eu.oreplay.db.Event voEve = null;
        //Depending on the type of file, the columns of the fields are different
        int vnColIndex = this.getIndexFromContentsAndSource();       
        //HashMap to store Classes. For each class its teams. Teams should be ordered by class in the CSV but, what if not?...
        HashMap<String, eu.oreplay.db.Clazz> vlCla = new HashMap<>();
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
                if (vaRecord.length>=44) { //At least, teams with one runner
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
                    //Process the team and put it on the class
                    List<eu.oreplay.db.Team> vlTea = voCla.getTeamList();
                    if (vlTea==null)
                        vlTea = new ArrayList<>();
                    eu.oreplay.db.Team voTea = new eu.oreplay.db.Team();
                    voTea.setId("");
                    voTea.setUuid("");
                    voTea.setBibNumber(COL_TEA_BIB[vnColIndex]>=0?vaRecord[COL_TEA_BIB[vnColIndex]].trim().replaceAll("\"", ""):"");
                    voTea.setBibAlt(COL_TEA_BIBALT[vnColIndex]>=0?vaRecord[COL_TEA_BIBALT[vnColIndex]].trim().replaceAll("\"", ""):"");
                    voTea.setTeamName(COL_TEA_NAME[vnColIndex]>=0?vaRecord[COL_TEA_NAME[vnColIndex]].trim().replaceAll("\"", ""):"");
                    String vcLegs = COL_LEGS[vnColIndex]>=0?vaRecord[COL_LEGS[vnColIndex]].trim().replaceAll("\"", ""):"";
                    int vnLegs = 0;
                    try {
                        vnLegs = Integer.parseInt(vcLegs);
                    } catch (Exception eLegs) {
                        vnLegs = 0;
                    }
                    voTea.setLegs(vnLegs);
                    //Start Time is set in a first element of TeamResult List
                    ArrayList<eu.oreplay.db.TeamResult> vlRet = new ArrayList<>();
                    eu.oreplay.db.TeamResult voRet = new eu.oreplay.db.TeamResult();
                    voRet.setId("");
                    voRet.setStageOrder(voSta.getOrderNumber());
                    //Compose the type of result, which is a Stage Result
                    eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
                    voResType.setId(Utils.RESULT_STAGE_ID);
                    voResType.setDescription(Utils.RESULT_STAGE_DESC);
                    voRet.setResultType(voResType);
                    //Transform the start time value
                    try {
                        String vcTime = COL_TEA_START[vnColIndex]>=0?vaRecord[COL_TEA_START[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"";
                        java.util.Date vdTime = Utils.formatRelativeTimeFromBase(vcTime, 
                                voSta.getBaseDate(), voSta.getBaseTime());
                        voRet.setStartTime(vdTime);
                    }catch(Exception eStart) {
                        //Nothing to do
                    }
                    String vcNc = (COL_TEA_NC[vnColIndex]>=0?vaRecord[COL_TEA_NC[vnColIndex]].trim().replaceAll("\"", "").toUpperCase():"");
                    voRet.setStatusCode(Utils.STATUS_OK_ID);
                    if (vcNc.equals("X") || vcNc.equals("1"))
                        voTea.setIsNc(Boolean.TRUE);
                    else
                        voTea.setIsNc(Boolean.FALSE);
                    //Get the status
                    String vcStatus = COL_TEA_STATUS[vnColIndex]>=0?vaRecord[COL_TEA_STATUS[vnColIndex]].trim().replaceAll("\"", ""):"0";
                    if (vcStatus.equals(""))
                        vcStatus = "0";
                    voRet.setStatusCode(vcStatus.charAt(0));
                    //Add the result to the list
                    vlRet.add(voRet);
                    //Add the list to the team data
                    voTea.setTeamResultList(vlRet);                  
                    //Now, the club of the team (also for the runners of the team)
                    if (!(COL_CLU_ID[vnColIndex]>=0?vaRecord[COL_CLU_ID[vnColIndex]].trim().replaceAll("\"", ""):"").equals("")) {
                        eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                        voClu.setId("");
                        voClu.setUuid("");
                        voClu.setOeKey(COL_CLU_ID[vnColIndex]>=0?vaRecord[COL_CLU_ID[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voClu.setCity(COL_CLU_CITY[vnColIndex]>=0?vaRecord[COL_CLU_CITY[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voClu.setShortName(COL_CLU_SHORT[vnColIndex]>=0?vaRecord[COL_CLU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voClu.setLongName(COL_CLU_SHORT[vnColIndex]>=0?vaRecord[COL_CLU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        //Add the club to the runner
                        voTea.setClub(voClu);
                    }
                    //Process the runners, taking in account the number of legs
                    List<eu.oreplay.db.Runner> vlRun = new ArrayList<>();
                    for (int j=0; j<vnLegs; j++) {
                        //The index where the runner fields start
                        int vnColIni = COL_LEGINDEX[vnColIndex] + (COL_LEGFIELDS[vnColIndex] * j);
                        if (vnColIni>=0 && vnColIni<vaRecord.length) {
                            eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                            voRun.setId("");
                            voRun.setUuid("");
                            String vcLeg = COL_LEG[vnColIndex]>=0?vaRecord[COL_LEG[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"";
                            int vnLeg = 0;
                            try {
                                vnLeg = Integer.parseInt(vcLeg);
                            } catch (Exception eLeg) {
                                vnLeg = 0;
                            }
                            voRun.setLegNumber(vnLeg);
                            voRun.setBibNumber(voTea.getBibNumber() + "-" + vnLeg); //The bib number of the runner is the concatenation of the team and the leg
                            voRun.setSicard(COL_SICARD[vnColIndex]>=0?vaRecord[COL_SICARD[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                            voRun.setSicardAlt(COL_SICARDALT[vnColIndex]>=0?vaRecord[COL_SICARDALT[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                            voRun.setDbId(COL_DBID[vnColIndex]>=0?vaRecord[COL_DBID[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                            voRun.setIofId(COL_IOFID[vnColIndex]>=0?vaRecord[COL_IOFID[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                            voRun.setLastName(COL_LASTNAME[vnColIndex]>=0?vaRecord[COL_LASTNAME[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                            voRun.setFirstName(COL_FIRSTNAME[vnColIndex]>=0?vaRecord[COL_FIRSTNAME[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                            voRun.setSex((COL_SEX[vnColIndex]>=0?(vaRecord[COL_SEX[vnColIndex]+vnColIni].length()>0?vaRecord[COL_SEX[vnColIndex]+vnColIni].trim().replaceAll("\"", "").charAt(0):' '):' '));
                            //Start Time is set in a first element of RunnerResult List
                            ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<>();
                            eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                            voRes.setId("");
                            voRes.setStageOrder(voSta.getOrderNumber());
                            voRes.setLegNumber(vnLeg);
                            //Compose the type of result, which is a Stage Result
                            voRes.setResultType(voResType);
                            //Transform the start time value
                            try {
                                String vcTime = COL_START[vnColIndex]>=0?vaRecord[COL_START[vnColIndex]+vnColIni].trim().replaceAll("\"", "").replaceAll(",", "."):"";
                                java.util.Date vdTime = Utils.formatRelativeTimeFromBase(vcTime, 
                                        voSta.getBaseDate(), voSta.getBaseTime());
                                voRes.setStartTime(vdTime);
                            }catch(Exception eStart) {
                                //Nothing to do
                            }
                            voRes.setStatusCode(voRet.getStatusCode());
                            //Add the result to the list
                            vlRes.add(voRes);
                            //Add the list to the runner data
                            voRun.setRunnerResultList(vlRes);
                            //Now, the club of the runner, which is the same as the team
                            voRun.setClub(voTea.getClub());
                            //Add the runner to the list of runners
                            vlRun.add(voRun);                                                
                        }                                
                    }
                    //Set the list of runners to the team
                    voTea.setRunnerList(vlRun);
                    //Set the team to the list of teams
                    vlTea.add(voTea);
                    //Set the list of teams to the class again
                    voCla.setTeamList(vlTea);
                    //Remove the previous contents of the class from the HashMap and insert it again
                    vlCla.remove(vcClaId);
                    vlCla.put(vcClaId, voCla);
                } else {
                    if (oLog!=null)
                        oLog.warn("CSV File with not enough fields to process it ?");
                }
            }
            //Add the list of classes to the stage
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
                //CSV Totals are grouped by team; Breakdown and Radio are grouped by runner
                if (getcResultsType().equals(ConverterToModel.RES_TOTALS)) {
                    voEve = convertResultListSingleStageRelayOSTotals (plResult);
                } else if (getcResultsType().equals(ConverterToModel.RES_BREAKDOWN)) {
                    voEve = convertResultListSingleStageRelayOSBreakdown (plResult);
                } else if (getcResultsType().equals(ConverterToModel.RES_RADIO)) {
                    voEve = convertResultListSingleStageRelayOSRadio (plResult);
                }
            } catch (Exception e) {
                if (oLog!=null)
                    oLog.error("Exception reading result list lines", e);
                voEve = null;
            }        
        }
        return voEve;
    }    
    /**
     * Given a representation of CSV for totals result list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the CSV file; this method is for relay event and results are grouped by team
     * The results can come from OS2010 or OSV12
     * @param plResult List<String> List with elements for each line of a CSV file for result list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, teams, teamresults, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertResultListSingleStageRelayOSTotals (List<String> plResult) {
        eu.oreplay.db.Event voEve = null;
        //Depending on the type of file, the columns of the fields are different
        int vnColIndex = this.getIndexFromContentsAndSource();       
        //HashMap to store Classes. For each class its Teams. Teams should be ordered by class in the CSV but, what if not?...
        HashMap<String, eu.oreplay.db.Clazz> vlCla = new HashMap<>();
        try {
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
                    if (vaRecord.length>=44) { //At least, teams with one runner
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
                        //Process the team and put it on the class
                        List<eu.oreplay.db.Team> vlTea = voCla.getTeamList();
                        if (vlTea==null)
                            vlTea = new ArrayList<>();
                        eu.oreplay.db.Team voTea = new eu.oreplay.db.Team();
                        voTea.setId("");
                        voTea.setUuid("");
                        voTea.setBibNumber(COL_TEA_BIB[vnColIndex]>=0?vaRecord[COL_TEA_BIB[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voTea.setBibAlt(COL_TEA_BIBALT[vnColIndex]>=0?vaRecord[COL_TEA_BIBALT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voTea.setTeamName(COL_TEA_NAME[vnColIndex]>=0?vaRecord[COL_TEA_NAME[vnColIndex]].trim().replaceAll("\"", ""):"");
                        String vcLegs = COL_LEGS[vnColIndex]>=0?vaRecord[COL_LEGS[vnColIndex]].trim().replaceAll("\"", ""):"";
                        int vnLegs = 0;
                        try {
                            vnLegs = Integer.parseInt(vcLegs);
                        } catch (Exception eLegs) {
                            vnLegs = 0;
                        }
                        voTea.setLegs(vnLegs);
                        //Start Time is set in a first element of TeamResult List
                        ArrayList<eu.oreplay.db.TeamResult> vlRet = new ArrayList<>();
                        eu.oreplay.db.TeamResult voRet = new eu.oreplay.db.TeamResult();
                        voRet.setId("");
                        voRet.setStageOrder(voSta.getOrderNumber());
                        //Compose the type of result, which is a Stage Result
                        eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
                        voResType.setId(Utils.RESULT_STAGE_ID);
                        voResType.setDescription(Utils.RESULT_STAGE_DESC);
                        voRet.setResultType(voResType);
                        //Transform the start time value
                        try {
                            String vcTime = COL_TEA_START[vnColIndex]>=0?vaRecord[COL_TEA_START[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"";
                            java.util.Date vdTime = Utils.formatRelativeTimeFromBase(vcTime, 
                                    voSta.getBaseDate(), voSta.getBaseTime());
                            voRet.setStartTime(vdTime);
                        }catch(Exception eStart) {
                            //Nothing to do
                        }
                        String vcNc = (COL_TEA_NC[vnColIndex]>=0?vaRecord[COL_TEA_NC[vnColIndex]].trim().replaceAll("\"", "").toUpperCase():"");
                        voRet.setStatusCode(Utils.STATUS_OK_ID);
                        if (vcNc.equals("X") || vcNc.equals("1"))
                            voTea.setIsNc(Boolean.TRUE);
                        else
                            voTea.setIsNc(Boolean.FALSE);
                        //Converts time to a value in seconds
                        double vnTimeSecs = Utils.formatTimeInSeconds(COL_TEA_TIME[vnColIndex]>=0?vaRecord[COL_TEA_TIME[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"");
                        //Using String constructor to avoid problems with floating point approximations
                        BigDecimal vnTimeSeconds = new BigDecimal(vnTimeSecs + "");
                        //If it has no decimals, stores only the integer part
                        voRet.setTimeSeconds(Utils.isWhole(vnTimeSeconds)?new BigDecimal(vnTimeSeconds.longValue()+""):vnTimeSeconds);
                        //Get the status
                        String vcStatus = COL_TEA_STATUS[vnColIndex]>=0?vaRecord[COL_TEA_STATUS[vnColIndex]].trim().replaceAll("\"", ""):"0";
                        if (vcStatus.equals(""))
                            vcStatus = "0";
                        voRet.setStatusCode(vcStatus.charAt(0));
                        //Get the position
                        int vnPosition = 0;
                        try {
                            vnPosition = Integer.parseInt(COL_TEA_POSITION[vnColIndex]>=0?vaRecord[COL_TEA_POSITION[vnColIndex]].trim().replaceAll("\"", ""):"0");
                        }catch (Exception ePos){
                            //Nothing to do
                        };
                        voRet.setPosition(vnPosition);
                        //Calculates time behind the first of the class
                        BigDecimal vnTimeBehind = this.calculateTimeBehindTeam(vlTea, vnTimeSeconds);
                        //If it has no decimals, stores only the integer part
                        voRet.setTimeBehind(Utils.isWhole(vnTimeBehind)?new BigDecimal(vnTimeBehind.longValue()+""):vnTimeBehind);
                        //Add the result to the list
                        vlRet.add(voRet);
                        //Add the list to the team data
                        voTea.setTeamResultList(vlRet);                  
                        //Now, the club of the team (also for the runners of the team)
                        if (!(COL_CLU_ID[vnColIndex]>=0?vaRecord[COL_CLU_ID[vnColIndex]].trim().replaceAll("\"", ""):"").equals("")) {
                            eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                            voClu.setId("");
                            voClu.setUuid("");
                            voClu.setOeKey(COL_CLU_ID[vnColIndex]>=0?vaRecord[COL_CLU_ID[vnColIndex]].trim().replaceAll("\"", ""):"");
                            voClu.setCity(COL_CLU_CITY[vnColIndex]>=0?vaRecord[COL_CLU_CITY[vnColIndex]].trim().replaceAll("\"", ""):"");
                            voClu.setShortName(COL_CLU_SHORT[vnColIndex]>=0?vaRecord[COL_CLU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                            voClu.setLongName(COL_CLU_SHORT[vnColIndex]>=0?vaRecord[COL_CLU_SHORT[vnColIndex]].trim().replaceAll("\"", ""):"");
                            //Add the club to the runner
                            voTea.setClub(voClu);
                        }
                        //---------------------------------------------------------
                        //Process the runners, taking in account the number of legs
                        List<eu.oreplay.db.Runner> vlRun = new ArrayList<>();
                        for (int j=0; j<vnLegs; j++) {
                            //The index where the runner fields start
                            int vnColIni = COL_LEGINDEX[vnColIndex] + (COL_LEGFIELDS[vnColIndex] * j);
                            if (vnColIni>=0 && vnColIni<vaRecord.length) {
                                eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                                voRun.setId("");
                                voRun.setUuid("");
                                String vcLeg = COL_LEG[vnColIndex]>=0?vaRecord[COL_LEG[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"";
                                int vnLeg = 0;
                                try {
                                    vnLeg = Integer.parseInt(vcLeg);
                                } catch (Exception eLeg) {
                                    vnLeg = 0;
                                }
                                voRun.setLegNumber(vnLeg);
                                voRun.setBibNumber(voTea.getBibNumber() + "-" + vnLeg); //The bib number of the runner is the concatenation of the team and the leg
                                voRun.setSicard(COL_SICARD[vnColIndex]>=0?vaRecord[COL_SICARD[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                                voRun.setSicardAlt(COL_SICARDALT[vnColIndex]>=0?vaRecord[COL_SICARDALT[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                                voRun.setDbId(COL_DBID[vnColIndex]>=0?vaRecord[COL_DBID[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                                voRun.setIofId(COL_IOFID[vnColIndex]>=0?vaRecord[COL_IOFID[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                                voRun.setLastName(COL_LASTNAME[vnColIndex]>=0?vaRecord[COL_LASTNAME[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                                voRun.setFirstName(COL_FIRSTNAME[vnColIndex]>=0?vaRecord[COL_FIRSTNAME[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"");
                                voRun.setSex((COL_SEX[vnColIndex]>=0?(vaRecord[COL_SEX[vnColIndex]+vnColIni].length()>0?vaRecord[COL_SEX[vnColIndex]+vnColIni].trim().replaceAll("\"", "").charAt(0):' '):' '));
                                //Start Time is set in a first element of RunnerResult List
                                ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<>();
                                eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                                voRes.setId("");
                                voRes.setStageOrder(voSta.getOrderNumber());
                                voRes.setLegNumber(vnLeg);
                                //Compose the type of result, which is a Stage Result
                                voRes.setResultType(voResType);
                                //Transform the start time value
                                try {
                                    String vcTime = COL_START[vnColIndex]>=0?vaRecord[COL_START[vnColIndex]+vnColIni].trim().replaceAll("\"", "").replaceAll(",", "."):"";
                                    java.util.Date vdTime = Utils.formatRelativeTimeFromBase(vcTime, 
                                            voSta.getBaseDate(), voSta.getBaseTime());
                                    voRes.setStartTime(vdTime);
                                }catch(Exception eStart) {
                                    //Nothing to do
                                }
                                //Transform the finish time value
                                try {
                                    String vcTime = COL_FINISH[vnColIndex]>=0?vaRecord[COL_FINISH[vnColIndex]+vnColIni].trim().replaceAll("\"", "").replaceAll(",", "."):"";
                                    java.util.Date vdTime = Utils.formatRelativeTimeFromBase(vcTime, 
                                            voSta.getBaseDate(), voSta.getBaseTime());
                                    voRes.setFinishTime(vdTime);
                                }catch(Exception eFinish) {
                                    //Nothing to do
                                }      
                                //Converts time to a value in seconds
                                vnTimeSecs = Utils.formatTimeInSeconds(COL_TIME[vnColIndex]>=0?vaRecord[COL_TIME[vnColIndex]+vnColIni].trim().replaceAll("\"", "").replaceAll(",", "."):"");
                                //Using String constructor to avoid problems with floating point approximations
                                vnTimeSeconds = new BigDecimal(vnTimeSecs + "");
                                //If it has no decimals, stores only the integer part
                                voRes.setTimeSeconds(Utils.isWhole(vnTimeSeconds)?new BigDecimal(vnTimeSeconds.longValue()+""):vnTimeSeconds);
                                //Get the status
                                vcStatus = COL_STATUS[vnColIndex]>=0?vaRecord[COL_STATUS[vnColIndex]+vnColIni].trim().replaceAll("\"", ""):"0";
                                if (vcStatus.equals(""))
                                    vcStatus = "0";
                                voRes.setStatusCode(vcStatus.charAt(0));
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
                                //Add the result to the list
                                vlRes.add(voRes);
                                //Add the list to the runner data
                                voRun.setRunnerResultList(vlRes);
                                //Now, the club of the runner, which is the same as the team
                                voRun.setClub(voTea.getClub());
                                //Add the runner to the list of runners
                                vlRun.add(voRun);                                                
                            }                                
                        }
                        //Set the list of runners to the team
                        voTea.setRunnerList(vlRun);
                        //Set the team to the list of teams
                        vlTea.add(voTea);
                        //Set the list of teams to the class again
                        voCla.setTeamList(vlTea);
                        //Remove the previous contents of the class from the HashMap and insert it again
                        vlCla.remove(vcClaId);
                        vlCla.put(vcClaId, voCla);
                    } else {
                        if (oLog!=null)
                            oLog.warn("CSV File with not enough fields to process it ?");
                    }
                }
                //Add the list of classes to the stage
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
        } catch (Exception e) {
            if (oLog!=null)
                oLog.error("Exception converting OS Totals Results", e);
        }
        return voEve;
    }
    /**
     * Given a representation of CSV for a breakdown (splits) result list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the CSV file; this method is for relay event and results are grouped by runner
     * The results can come from OS2010 or OSV12
     * @param plResult List<String> List with elements for each line of a CSV file for result list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, teams, teamresults, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertResultListSingleStageRelayOSBreakdown (List<String> plResult) {
        eu.oreplay.db.Event voEve = null;
        //HashMap to store Classes. For each class its Teams and for each team its runners. Runners should be ordered by class in the CSV but, what if not?...
        HashMap<String, eu.oreplay.db.Clazz> vlCla = new HashMap<>();
        ArrayList<eu.oreplay.db.Control> vlCon = new ArrayList<>();
        HashMap<String, String> vlStations = new HashMap<>();
        String vcUuidEve = "";
        String vcUuidSta = "";
        boolean vbRadio = false;
        boolean vbSplit = false;
        //Flag to say whether the results include radiocontrols
        if (getcResultsType().equals(ConverterToModel.RES_RADIO))
            vbRadio = true;
        //Flag to say whether the results include breakdown of results (splits)
        if (getcResultsType().equals(ConverterToModel.RES_BREAKDOWN))
            vbSplit = true;
        if (oEve!=null) {
            vcUuidEve = oEve.getId();
            vcUuidSta = (oEve.getStageList()!=null?oEve.getStageList().get(0).getId():"");
        }
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
                    if (vaRecord.length>=29) {
                        //Get the class to process the team and runner inside it
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
                        //Get the team from the class to process the runner inside it
                        List<eu.oreplay.db.Team> vlTea = voCla.getTeamList();
                        if (vlTea==null)
                            vlTea = new ArrayList<>();
                        //Search for the team inside the list of teams of the class
                        String vcTeaBib = COL_TEA_BIB[vnColIndex]>=0?vaRecord[COL_TEA_BIB[vnColIndex]].trim().replaceAll("\"", ""):"";
                        int vnTeaInd = this.getIndexTeamInList(vlTea, vcTeaBib);
                        eu.oreplay.db.Team voTea = null;
                        //If the teams doesn't exist yet, create it; else get it
                        if (vnTeaInd==-1) {
                            voTea = new eu.oreplay.db.Team();
                        } else {
                            voTea = vlTea.get(vnTeaInd);
                        }
                        voTea.setId("");
                        voTea.setUuid("");
                        voTea.setBibNumber(vcTeaBib);
                        voTea.setBibAlt(COL_TEA_BIBALT[vnColIndex]>=0?vaRecord[COL_TEA_BIBALT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voTea.setTeamName(COL_TEA_NAME[vnColIndex]>=0?vaRecord[COL_TEA_NAME[vnColIndex]].trim().replaceAll("\"", ""):"");
                        //--------------------------------------------------------
                        //Get the list of results of the team, or create a new one
                        List<eu.oreplay.db.TeamResult> vlRet = voTea.getTeamResultList();
                        eu.oreplay.db.TeamResult voRet = null;
                        if (vlRet==null) {
                            vlRet = new ArrayList<eu.oreplay.db.TeamResult>();
                            voRet = new eu.oreplay.db.TeamResult();
                        } else {
                            if (vlRet.size()>0) {
                                voRet = vlRet.get(0);
                            } else {
                                voRet = new eu.oreplay.db.TeamResult();
                            }
                        }
                        voRet.setId("");
                        voRet.setStageOrder(voSta.getOrderNumber());
                        //Compose the type of result, which is a Stage Result
                        eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
                        voResType.setId(Utils.RESULT_STAGE_ID);
                        voResType.setDescription(Utils.RESULT_STAGE_DESC);
                        voRet.setResultType(voResType);
                        //Transform the start time value
                        try {
                            String vcTime = COL_TEA_START[vnColIndex]>=0?vaRecord[COL_TEA_START[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"";
                            java.util.Date vdTime = Utils.formatRelativeTimeFromBase(vcTime, 
                                    voSta.getBaseDate(), voSta.getBaseTime());
                            voRet.setStartTime(vdTime);
                        }catch(Exception eStart) {
                            //Nothing to do
                        }
                        String vcNc = (COL_TEA_NC[vnColIndex]>=0?vaRecord[COL_TEA_NC[vnColIndex]].trim().replaceAll("\"", "").toUpperCase():"");
                        voRet.setStatusCode(Utils.STATUS_OK_ID);
                        if (vcNc.equals("X") || vcNc.equals("1"))
                            voTea.setIsNc(Boolean.TRUE);
                        else
                            voTea.setIsNc(Boolean.FALSE);
                        //Converts time to a value in seconds
                        double vnTimeSecs = Utils.formatTimeInSeconds(COL_TEA_TIME[vnColIndex]>=0?vaRecord[COL_TEA_TIME[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"");
                        //Using String constructor to avoid problems with floating point approximations
                        BigDecimal vnTimeSeconds = new BigDecimal(vnTimeSecs + "");
                        //If it has no decimals, stores only the integer part
                        voRet.setTimeSeconds(Utils.isWhole(vnTimeSeconds)?new BigDecimal(vnTimeSeconds.longValue()+""):vnTimeSeconds);
                        //Get the status
                        String vcStatus = COL_TEA_STATUS[vnColIndex]>=0?vaRecord[COL_TEA_STATUS[vnColIndex]].trim().replaceAll("\"", ""):"0";
                        if (vcStatus.equals(""))
                            vcStatus = "0";
                        voRet.setStatusCode(vcStatus.charAt(0));
                        //Get the position
                        int vnPosition = 0;
                        try {
                            vnPosition = Integer.parseInt(COL_TEA_POSITION[vnColIndex]>=0?vaRecord[COL_TEA_POSITION[vnColIndex]].trim().replaceAll("\"", ""):"0");
                        }catch (Exception ePos){
                            //Nothing to do
                        };
                        voRet.setPosition(vnPosition);
                        //Calculates time behind the first of the class
                        BigDecimal vnTimeBehind = this.calculateTimeBehindTeam(vlTea, vnTimeSeconds);
                        //If it has no decimals, stores only the integer part
                        voRet.setTimeBehind(Utils.isWhole(vnTimeBehind)?new BigDecimal(vnTimeBehind.longValue()+""):vnTimeBehind);
                        //Add the result to the list
                        if (vlRet.size()==0) {
                            vlRet.add(voRet);
                        } else {
                            vlRet.set(0, voRet);
                        }
                        //Add the list to the team data
                        voTea.setTeamResultList(vlRet);                  
                        //----------------------------------------------------
                        //Get or create the list of radiocontrols of the class
                        ArrayList<eu.oreplay.db.ClazzControl> vlClaCon = (voCla.getClazzControlList()!=null?(ArrayList)voCla.getClazzControlList():new ArrayList<>());                    
                        //Process the runner and put it on the team
                        List<eu.oreplay.db.Runner> vlRun = voTea.getRunnerList();
                        if (vlRun==null)
                            vlRun = new ArrayList<>();
                        eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                        voRun.setId("");
                        voRun.setUuid("");
                        String vcLeg = COL_LEG[vnColIndex]>=0?vaRecord[COL_LEG[vnColIndex]].trim().replaceAll("\"", ""):"";
                        int vnLeg = 0;
                        try {
                            vnLeg = Integer.parseInt(vcLeg);
                        } catch (Exception eLeg) {
                            vnLeg = 0;
                        }
                        voRun.setLegNumber(vnLeg);
                        voRun.setBibNumber(voTea.getBibNumber() + "-" + vnLeg); //The bib number of the runner is the concatenation of the team and the leg
                        voRun.setBibAlt(voRun.getBibNumber());
                        voRun.setSicard(COL_SICARD[vnColIndex]>=0?vaRecord[COL_SICARD[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voRun.setSicardAlt(COL_SICARDALT[vnColIndex]>=0?vaRecord[COL_SICARDALT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voRun.setDbId(COL_DBID[vnColIndex]>=0?vaRecord[COL_DBID[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voRun.setLastName(COL_LASTNAME[vnColIndex]>=0?vaRecord[COL_LASTNAME[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voRun.setFirstName(COL_FIRSTNAME[vnColIndex]>=0?vaRecord[COL_FIRSTNAME[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voRun.setSex((COL_SEX[vnColIndex]>=0?(vaRecord[COL_SEX[vnColIndex]].length()>0?vaRecord[COL_SEX[vnColIndex]].trim().replaceAll("\"", "").charAt(0):' '):' '));
                        //Now, the course of the runner
                        if (voRun.getCourse()==null && !vaRecord[COL_COU_ID[vnColIndex]].trim().replaceAll("\"", "").equals("")) {
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
                            //---------------------------------------
                            //OS has a field for the runner's course combination (COL_COU_COMBINATION), but our model not
                            //---------------------------------------
                            //Add the course to the runner
                            voRun.setCourse(voCou);
                        }
                        //---------------------------------------------------------
                        //Start Time is set in a first element of RunnerResult List
                        ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<>();
                        eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                        voRes.setId("");
                        voRes.setStageOrder(voSta.getOrderNumber());
                        voRes.setLegNumber(vnLeg);
                        //Compose the type of result, which is a Stage Result
                        voResType = new eu.oreplay.db.ResultType();
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
                        vnTimeSecs = Utils.formatTimeInSeconds(COL_TIME[vnColIndex]>=0?vaRecord[COL_TIME[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"");
                        //Using String constructor to avoid problems with floating point approximations
                        vnTimeSeconds = new BigDecimal(vnTimeSecs + "");
                        //If it has no decimals, stores only the integer part
                        voRes.setTimeSeconds(Utils.isWhole(vnTimeSeconds)?new BigDecimal(vnTimeSeconds.longValue()+""):vnTimeSeconds);
                        //Get the status
                        vcStatus = COL_STATUS[vnColIndex]>=0?vaRecord[COL_STATUS[vnColIndex]].trim().replaceAll("\"", ""):"0";
                        if (vcStatus.equals(""))
                            vcStatus = "0";
                        voRes.setStatusCode(vcStatus.charAt(0));
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
                                            if (vnSplOrder>voRun.getCourse().getControls()) {
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
                                        vnCol += (COL_RADIO_INC[vnColIndex]>0?COL_RADIO_INC[vnColIndex]:4);
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
                        //Set the list of runners to the team
                        voTea.setRunnerList(vlRun);
                        //Set or replace the team to the list of teams
                        if (vnTeaInd==-1) {
                            vlTea.add(voTea);
                        } else {
                            vlTea.set(vnTeaInd, voTea);
                        }
                        //Set the list of teams to the class again
                        voCla.setTeamList(vlTea);                   
                        //If it's a result with radiocontrols, add the list of class-controls to the class
                        if (vbRadio && !vlClaCon.isEmpty()) {
                            voCla.setClazzControlList(vlClaCon);
                        }
                        //Remove the previous contents of the class from the HashMap and insert it again
                        vlCla.remove(vcClaId);
                        vlCla.put(vcClaId, voCla);
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
                oLog.error("Exception converting OS Breakdown Results", e);
        }
        return voEve;
    }
    /**
     * Given a representation of CSV for a radiocontrol (intermediate) result list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the CSV file; this method is for relay event and results are grouped by runner
     * The results can come from OS2010 or OSV12
     * @param plResult List<String> List with elements for each line of a CSV file for result list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, teams, teamresults, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertResultListSingleStageRelayOSRadio (List<String> plResult) {
        eu.oreplay.db.Event voEve = null;
        voEve = convertResultListSingleStageRelayOSBreakdown(plResult);
        return voEve;
    }
    /**
     * When processing CSV from OS, depending on OS version and type of contents,
     * this method returns an index, meaning the place things like column positions, etc,
     * is located
     * OS2010, STARTS or GLOBAL RESULTS = 0
     * OS2010, BREAKDOWN (SPLITS) = 1
     * OS2010, RADIOCONTROL (INTERMEDIATE) = 2
     * OSV12, STARTS or GLOBAL RESULTS = 3
     * OSV12, BREAKDOWN (SPLITS) = 4
     * OSV12, RADIOCONTROL (INTERMEDIATE) = 5
     * @return int Index of the values
     */
    @JsonIgnore
    public int getIndexFromContentsAndSource() {
        int vnResul = 0;
        try {
            if (getcSource().equals(ConverterToModel.SRC_OS2010)) {
                if ((getcContents().equals(ConverterToModel.CONTENTS_RESULT) && getcResultsType().equals(ConverterToModel.RES_TOTALS)) ||
                       (getcContents().equals(ConverterToModel.CONTENTS_START)))
                    vnResul = 0;
                else if (getcResultsType().equals(ConverterToModel.RES_BREAKDOWN))
                    vnResul = 1;
                else if (getcResultsType().equals(ConverterToModel.RES_RADIO))
                    vnResul = 2;
            } else if (getcSource().equals(ConverterToModel.SRC_OSV12)) {
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
    /**
     * Takes a list of teams and their times, looks for the team in the first
     * position and substract the given time to calculate the difference between
     * the current team and the first one
     * @param plTea List<eu.oreplay.db.Team> List of teams
     * @param pnCurrent BigDecimal Time(seconds) of the current team
     * @return BigDecimal The difference or zero
     */
    public BigDecimal calculateTimeBehindTeam (List<eu.oreplay.db.Team> plTea, BigDecimal pnCurrent) {
        BigDecimal vnResul = BigDecimal.ZERO;
        boolean vbFound = false;
        try {
            //If the current value is greater than zero
            if (plTea!=null && pnCurrent.compareTo(BigDecimal.ZERO)==1) {
                int i = 0;
                while (i<plTea.size() && !vbFound) {
                    eu.oreplay.db.Team voTea = plTea.get(i);
                    if (voTea!=null && voTea.getTeamResultList()!=null && !voTea.getTeamResultList().isEmpty()) {
                        eu.oreplay.db.TeamResult voRet = voTea.getTeamResultList().get(0);
                        if (voRet.getPosition()==1) {
                            vnResul = pnCurrent.subtract(voRet.getTimeSeconds());
                            vbFound = true;
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
    /**
     * Given a list of teams, search for a team by its Bib# and returns the index in the list
     * @param plTea List<eu.oreplay.db.Team> List of Teams
     * @return int Index of the team in the list, or -1
     */
    private int getIndexTeamInList(List<eu.oreplay.db.Team> plTea, String pcBib) {
        int vnResul = -1;
        boolean vbFound = false;
        if (plTea!=null) {
            if (!plTea.isEmpty()) {
                int i = 0;
                while (i<plTea.size() && !vbFound) {
                    eu.oreplay.db.Team voTea = plTea.get(i);
                    if (voTea!=null) {
                        String vcBib = (voTea.getBibNumber()!=null?voTea.getBibNumber():"");
                        if (!vcBib.equals("") && vcBib.equals(pcBib)) {
                            vnResul = i;
                            vbFound = true;
                        }
                    }
                    if (!vbFound) {
                        i++;
                    }
                }
            }
        }
        return vnResul;
    }
}