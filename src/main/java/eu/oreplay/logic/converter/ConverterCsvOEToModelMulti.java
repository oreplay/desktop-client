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
 * in this case, for multistage totalization of results for classic orienteering competitions
 * @author javier.arufe
 */
public class ConverterCsvOEToModelMulti extends ConverterToModel{
    private String cSeparator = ";";
    private String cEncoding = Utils.ENCODING_UTF_8;
    private eu.oreplay.db.Event oEve = null; //Event/Stage basic information to perform the calculations (UUID, base date, base time, description, etc)
    private final String[] FILE = {"OE0013", "OE0013a", "OE0013_V12", "OE0013a_V12"};
    private final int[] COL_STAGE_INC = {6, 6, 6, 6};
    private final int[] COL_BIB = {1, 1, 2, 2};
    private final int[] COL_BIBALT = {2, 2, 3, 3};
    private final int[] COL_STAGE_SICARD = {3, 3, 4, 4};
    private final int[] COL_SICARDALT = {-1, -1, -1, -1};
    private final int[] COL_DBID = {9, 9, 10, 10};
    private final int[] COL_IOFID = {-1, -1, 11, 11};
    private final int[] COL_IOFID_FEDO = {-1, -1, 91, 91};
    private final int[] COL_LASTNAME = {10, 10, 12, 12};
    private final int[] COL_FIRSTNAME = {11, 11, 13, 13};
    private final int[] COL_BIRTH = {12, 12, 14, 14};
    private final int[] COL_BIRTH_FEDO = {-1, -1, -1, -1};
    private final int[] COL_SEX = {13, 13, 16, 16};
    private final int[] COL_STAGE_TOOKPART = {20, 20, 23, 23};
    private final int[] COL_TIMES_INC = {8, 8, 8, 8};
    private final int[] COL_STAGE_NC = {26, 26, 29, 29};
    private final int[] COL_STAGE_START = {27, 27, 30, 30};
    private final int[] COL_STAGE_FINISH = {28, 28, 31, 31};
    private final int[] COL_STAGE_TIME = {29, 29, 32, 32};
    private final int[] COL_STAGE_STATUS = {30, 30, 33, 33};
    private final int[] COL_STAGE_BONUS = {31, 31, 34, 34};
    private final int[] COL_STAGE_PENALTY = {32, 32, 35, 35};
    private final int[] COL_CLU_ID = {74, 74, 77, 77};
    private final int[] COL_CLU_CITY = {75, 75, 78, 78};
    private final int[] COL_CLU_SHORT = {76, 76, 79, 79};
    private final int[] COL_CLU_LONG = {-1, -1, -1, -1};
    private final int[] COL_CAT_ID = {80, 80, 83, 83};
    private final int[] COL_CAT_SHORT = {81, 81, 84, 84};
    private final int[] COL_CAT_LONG = {82, 82, 85, 85};
    private final int[] COL_LIC_FEDO = {88, 88, 91, 91};
    private final int[] COL_DNI_FEDO = {91, 91, 94, 94};
    private final int[] COL_TEL1 = {100, 100, 103, 103};
    private final int[] COL_TEL2 = {101, 101, 104, 104};
    private final int[] COL_MAIL = {103, 103, 106, 106};
    private final int[] COL_TEA_ID = {107, 107, 110, 110};
    private final int[] COL_TEA_SHORT = {-1, -1, 111, 111};
    private final int[] COL_STAGE_POSITION = {108, 108, 113, 113};
    private final int[] COL_TOTAL_TIME = {114, 114, 119, 119};
    private final int[] COL_TOTAL_POSITION = {115, 115, 120, 120};
    private final int[] COL_POINTS_INC = {-1, 2, -1, 2};
    private final int[] COL_POINTS = {-1, 116, -1, 121};
    private final int[] COL_OK = {-1, 117, -1, 122};
    private final int[] COL_TOTAL_POINTS = {-1, 128, -1, 133};
    

    public ConverterCsvOEToModelMulti() {
        super();
    }
    public ConverterCsvOEToModelMulti (ConverterToModel poSrc) {
        super.copyValues(poSrc);
    }
    public ConverterCsvOEToModelMulti(String pcSeparator, String pcEncoding) {
        cSeparator = pcSeparator;
        cEncoding = pcEncoding;
    }
    public ConverterCsvOEToModelMulti(String pcSeparator, String pcEncoding, eu.oreplay.db.Event poEve) {
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
        return null;
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
                voEve = convertResultListMultiStageClassicOE (plResult);
                //Second loop to final values in order to calculate correct time behind values
                voEve = recalculateTimeBehind (voEve);
            } catch (Exception e) {
                voEve = null;
            }        
        }
        return voEve;
    }    
    /**
     * Given a representation of CSV for result list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the CSV file; this method is for multi-stage, classic Foot-O event
     * The results can be totalizations by time or by time and points
     * The results can come from OE2010 or OEV12
     * @param plResult List<String> List with elements for each line of a CSV file for result list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertResultListMultiStageClassicOE (List<String> plResult) {
        eu.oreplay.db.Event voEve = null;
        //HashMap to store Classes. For each class its runners. Runners should be ordered by class in the CSV but, what if not?...
        HashMap<String, eu.oreplay.db.Clazz> vlCla = new HashMap<>();
        //Depending on the type of file, the columns of the fields are different
        int vnColIndex = this.getIndexFromContentsAndSource();       
        try {
            //Maximum number of stages supported by the CSV format
            int vnMaxStages = COL_STAGE_INC[vnColIndex];
            //Process the contents to search for the number of stages
            int vnNumberStages = getNumberOfStagesFromCsv(plResult);
            //Process the contents
            if (plResult!=null && vnMaxStages>0 && vnNumberStages>0) {
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
                    if (vaRecord.length>=121) {
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
                        //Process the runner and put it on the class
                        List<eu.oreplay.db.Runner> vlRun = voCla.getRunnerList();
                        if (vlRun==null)
                            vlRun = new ArrayList<>();
                        eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                        voRun.setId("");
                        voRun.setUuid("");
                        voRun.setBibNumber(COL_BIB[vnColIndex]>=0?vaRecord[COL_BIB[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voRun.setBibAlt(COL_BIBALT[vnColIndex]>=0?vaRecord[COL_BIBALT[vnColIndex]].trim().replaceAll("\"", ""):"");
                        voRun.setSicard(COL_STAGE_SICARD[vnColIndex]>=0?vaRecord[COL_STAGE_SICARD[vnColIndex]].trim().replaceAll("\"", ""):"");
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
                        //RunnerResult List to save the results of each stage and an overall
                        ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<>();
                        //----------------------------------------------------------------------------------
                        //First, the overall
                        //----------------------------------------------------------------------------------
                        eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                        voRes.setId("");
                        voRes.setStageOrder(1); //Links the overall to the first stage
                        voRes.setLegNumber(1);
                        //Compose the type of result, which is an Overall Result
                        eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
                        voResType.setId(Utils.RESULT_OVERALL_ID);
                        voResType.setDescription(Utils.RESULT_OVERALL_DESC);
                        voRes.setResultType(voResType);
                        //Converts total time to a value in seconds
                        double vnTimeSecs = Utils.formatTimeInSeconds(COL_TOTAL_TIME[vnColIndex]>=0?vaRecord[COL_TOTAL_TIME[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"");
                        //Using String constructor to avoid problems with floating point approximations
                        BigDecimal vnTimeSeconds = new BigDecimal(vnTimeSecs + "");
                        //If it has no decimals, stores only the integer part
                        voRes.setTimeSeconds(Utils.isWhole(vnTimeSeconds)?new BigDecimal(vnTimeSeconds.longValue()+""):vnTimeSeconds);
                        //Get the global position
                        int vnPosition = 0;
                        try {
                            vnPosition = Integer.parseInt(COL_TOTAL_POSITION[vnColIndex]>=0?vaRecord[COL_TOTAL_POSITION[vnColIndex]].trim().replaceAll("\"", ""):"0");
                        }catch (Exception ePos){
                            //Nothing to do
                        };
                        voRes.setPosition(vnPosition);
                        //-------------------------------------------------------------------
                        //Time Behind is going to be completely recalculated in a second processing
                        //-------------------------------------------------------------------
                        //Calculates time behind the first of the class
                        //BigDecimal vnTimeBehind = this.calculateTimeBehind(vlRun, vnTimeSeconds, 0);
                        //If it has no decimals, stores only the integer part
                        //voRes.setTimeBehind(Utils.isWhole(vnTimeBehind)?new BigDecimal(vnTimeBehind.longValue()+""):vnTimeBehind);
                        //-------------------------------------------------------------------
                        //Set remainder fields for points and times
                        voRes.setPointsAdjusted(BigDecimal.ZERO);
                        voRes.setPointsBonus(BigDecimal.ZERO);
                        //If it's a results file that makes a points totalization
                        if (getcTotalization().equals(ConverterToModel.TOT_POINTS)) {
                            //Get the total points
                            double vnPoints = 0.0;
                            try {
                                vnPoints = Double.parseDouble(COL_TOTAL_POINTS[vnColIndex]>=0?vaRecord[COL_TOTAL_POINTS[vnColIndex]].trim().replaceAll("\"", "").replaceAll(",", "."):"0.0");
                            }catch (Exception ePoints){
                                //Nothing to do
                            };
                            voRes.setPointsFinal(new java.math.BigDecimal(vnPoints).setScale(2, java.math.RoundingMode.HALF_UP));
                        } else {
                            voRes.setPointsFinal(BigDecimal.ZERO);
                        }
                        voRes.setPointsPenalty(BigDecimal.ZERO);
                        voRes.setTimeAdjusted(BigDecimal.ZERO);
                        voRes.setTimeBonus(BigDecimal.ZERO);
                        voRes.setTimeNeutralization(BigDecimal.ZERO);
                        voRes.setTimePenalty(BigDecimal.ZERO);
                        //Add the result to the list
                        vlRes.add(voRes);
                        //----------------------------------------------------------------------------------
                        //Starting index of the 'took part' columns
                        int vnTookPart = COL_STAGE_TOOKPART[vnColIndex];
                        //Now, each of the stages
                        for (int j=0; j<vnNumberStages; j++) {
                            voRes = new eu.oreplay.db.RunnerResult();
                            voRes.setId("");
                            voRes.setStageOrder(j+1);
                            voRes.setLegNumber(1);
                            //Compose the type of result, which is a Stage Result
                            voResType = new eu.oreplay.db.ResultType();
                            voResType.setId(Utils.RESULT_STAGE_ID);
                            voResType.setDescription(Utils.RESULT_STAGE_DESC);
                            voRes.setResultType(voResType);
                            //Some values that don't depend on participation of the runner
                            voRes.setStartTime(null);
                            voRes.setFinishTime(null);
                            voRes.setTimeSeconds(null);
                            voRes.setTimeBehind(null);
                            //Set remainder fields for points and times
                            voRes.setPointsFinal(BigDecimal.ZERO);
                            voRes.setPointsAdjusted(BigDecimal.ZERO);
                            voRes.setPointsBonus(BigDecimal.ZERO);
                            voRes.setPointsPenalty(BigDecimal.ZERO);
                            voRes.setTimeAdjusted(BigDecimal.ZERO);
                            voRes.setTimeBonus(BigDecimal.ZERO);
                            voRes.setTimeNeutralization(BigDecimal.ZERO);
                            voRes.setTimePenalty(BigDecimal.ZERO);
                            //Some values depend whether the runner took part or not
                            String vcTookPart = vaRecord[vnTookPart+j].trim().replaceAll("\"", "").toLowerCase();
                            //If the field is checked and the current number of stages is less than the stage the runner took part, update the value
                            if (vcTookPart.equals("x") || vcTookPart.equals("1")) {
                                //The index of the column for the total time on the stage
                                int vnColTime = COL_STAGE_TIME[vnColIndex] + (COL_TIMES_INC[vnColIndex]*j);
                                if (vnColTime>0) {
                                    vnTimeSecs = Utils.formatTimeInSeconds(vaRecord[vnColTime].trim().replaceAll("\"", "").replaceAll(",", "."));
                                    //Using String constructor to avoid problems with floating point approximations
                                    vnTimeSeconds = new BigDecimal(vnTimeSecs + "");
                                    //If it has no decimals, stores only the integer part
                                    voRes.setTimeSeconds(Utils.isWhole(vnTimeSeconds)?new BigDecimal(vnTimeSeconds.longValue()+""):vnTimeSeconds);
                                }
                                //-------------------------------------------------------------------
                                //Time Behind is going to be completely recalculated in a second processing
                                //-------------------------------------------------------------------
                                //Calculates time behind the first of the class
                                //vnTimeBehind = this.calculateTimeBehind(vlRun, vnTimeSeconds, (j+1));
                                //If it has no decimals, stores only the integer part
                                //voRes.setTimeBehind(Utils.isWhole(vnTimeBehind)?new BigDecimal(vnTimeBehind.longValue()+""):vnTimeBehind);
                                //-------------------------------------------------------------------
                                //The index of the column for the status on the stage
                                int vnColStatus = COL_STAGE_STATUS[vnColIndex] + (COL_TIMES_INC[vnColIndex]*j);
                                if (vnColStatus>0) {
                                    //Get the status
                                    String vcStatus = vaRecord[vnColStatus].trim().replaceAll("\"", "");
                                    if (vcStatus.equals(""))
                                        vcStatus = "0";
                                    voRes.setStatusCode(vcStatus.charAt(0));
                                }
                                //The index of the column for the NotCompeting on the stage
                                int vnColNc = COL_STAGE_NC[vnColIndex] + (COL_TIMES_INC[vnColIndex]*j);
                                if (vnColNc>0) {
                                    //Check if it's a NotCompeting Runner. If so, change the status code
                                    String vcNc = vaRecord[vnColNc].trim().replaceAll("\"", "").toUpperCase();
                                    if (vcNc.equals("X") || vcNc.equals("1"))
                                        voRun.setIsNc(Boolean.TRUE);
                                    else
                                        voRun.setIsNc(Boolean.FALSE);
                                }
                                //The index of the column for the position on the stage
                                int vnColPos = COL_STAGE_POSITION[vnColIndex] + j;
                                if (vnColPos>0) {
                                    //Get the position
                                    vnPosition = 0;
                                    try {
                                        vnPosition = Integer.parseInt(vaRecord[vnColPos].trim().replaceAll("\"", ""));
                                    }catch (Exception ePos){
                                        //Nothing to do
                                    };
                                    voRes.setPosition(vnPosition);
                                }
                                //The index of the column for the position on the stage
                                int vnColPoints = COL_POINTS[vnColIndex] + (COL_POINTS_INC[vnColIndex]*j);
                                int vnColOk = COL_OK[vnColIndex] + (COL_POINTS_INC[vnColIndex]*j);
                                //If it's a results file that makes a points totalization
                                if (getcTotalization().equals(ConverterToModel.TOT_POINTS) && vnColPoints>0) {
                                    //Get the total points
                                    double vnPoints = 0.0;
                                    try {
                                        vnPoints = Double.parseDouble(vaRecord[vnColPoints].trim().replaceAll("\"", "").replaceAll(",", "."));
                                    }catch (Exception ePoints){
                                        //Nothing to do
                                    };
                                    voRes.setPointsFinal(new java.math.BigDecimal(vnPoints).setScale(2, java.math.RoundingMode.HALF_UP));
                                    //Sometimes only some of the best stages are taken into account 
                                    String vcOneOfBest = vaRecord[vnColOk].trim().replaceAll("\"", "").toLowerCase();
                                    //If the field is checked
                                    if (vcOneOfBest.equals("x") || vcOneOfBest.equals("1")) {
                                        voRes.setIsBest(true);
                                    } else {
                                        voRes.setIsBest(false);
                                    }
                                }
                            }
                            //Add the result to the list
                            vlRes.add(voRes);                            
                        }                        
                        //Add the list to the runner data
                        voRun.setRunnerResultList(vlRes);
                        //Add the runner to the list of runners
                        vlRun.add(voRun);
                        //Set the list of runners to the class again
                        voCla.setRunnerList(vlRun);
                        //Remove the previous contents of the class from the HashMap and insert it again
                        vlCla.remove(vcClaId);
                        vlCla.put(vcClaId, voCla);
                    }
                }
                //Add the lis of classes to the stage
                List<eu.oreplay.db.Clazz> vlClaNew = new ArrayList<>(vlCla.values());
                voSta.setClazzList(vlClaNew);
                //Add the stage to the event
                ArrayList<eu.oreplay.db.Stage> vlSta = new ArrayList<>();
                vlSta.add(voSta);
                voEve.setStageList(vlSta);
            }
        }catch (Exception e) {
            //Nothing to do
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
                if (getcTotalization().equals(ConverterToModel.TOT_TIME))
                    vnResul = 0;
                else
                    vnResul = 1;
            } else if (getcSource().equals(ConverterToModel.SRC_OEV12)) {
                if (getcTotalization().equals(ConverterToModel.TOT_TIME))
                    vnResul = 2;
                else
                    vnResul = 3;
            }        
        }catch (Exception e) {
            vnResul = 3;
        }
        return vnResul;
    }
    /**
     * Takes a list of runners and their times, looks for the runner in the first
     * position and substract the given time to calculate the difference between
     * the current runner and the first one
     * @param plRun List<eu.oreplay.db.Runner> List of runners
     * @param pnCurrent BigDecimal Time(seconds) of the current runner
     * @param pnStage int Number of the stage to look for, or 0 for the Overall result
     * @return BigDecimal The difference or zero
     */
    public BigDecimal calculateTimeBehind (List<eu.oreplay.db.Runner> plRun, BigDecimal pnCurrent, int pnStage) {
        BigDecimal vnResul = BigDecimal.ZERO;
        BigDecimal vnMin = BigDecimal.ZERO;
        try {
            //If the current value is greater than zero
            if (plRun!=null && pnCurrent!=null && pnCurrent.compareTo(BigDecimal.ZERO)==1) {
                //Loop to get the minimum time
                for (int i=0; i<plRun.size(); i++) {
                    eu.oreplay.db.Runner voRun = plRun.get(i);
                    if (voRun!=null && voRun.getRunnerResultList()!=null) {
                        if (!voRun.getRunnerResultList().isEmpty() && pnStage<voRun.getRunnerResultList().size()) {
                            eu.oreplay.db.RunnerResult voRes = voRun.getRunnerResultList().get(pnStage);
                            //If runner has a position and a time, and his time is less than current minimum or current minimum equals 0, update the minimum
                            if ((voRes.getPosition()!=null && voRes.getPosition()>0) && 
                                    voRes.getTimeSeconds()!=null &&
                                    voRes.getTimeSeconds().compareTo(BigDecimal.ZERO)==1 && 
                                    (voRes.getTimeSeconds().compareTo(vnMin)==-1 || vnMin.compareTo(BigDecimal.ZERO)==0)) {
                                vnMin = voRes.getTimeSeconds();
                            }
                        }
                    }                    
                }
                if (vnMin.compareTo(BigDecimal.ZERO)==1) {
                    vnResul = pnCurrent.subtract(vnMin);
                }
            }
        }catch(Exception e) {
            vnResul = BigDecimal.ZERO;
        }
        return vnResul;
    }
    /**
     * Takes a list of runners and their times, looks for the runner in the first
     * position and substract the given time to calculate the difference between
     * the current runner and the first one
     * @param plRun List<eu.oreplay.db.Runner> List of runners
     * @param pnCurrent BigDecimal Time(seconds) of the current runner
     * @param pnStage int Number of the stage to look for, or 0 for the Overall result
     * @return BigDecimal The difference or zero
     */
    public List<BigDecimal> calculateMinTimes (List<eu.oreplay.db.Runner> plRun) {
        List<BigDecimal> vlResul = null;
        try {
            int vnStages = this.getNumberOfStagesFromResultList(plRun);
            //If the current value is greater than zero
            if (plRun!=null && vnStages>0) {
                //Initialise the list of minimum values
                vlResul = new ArrayList<>();
                for (int i=0; i<vnStages; i++) {
                    vlResul.add(BigDecimal.ZERO);
                }
                //Loop to get the minimum time
                for (int i=0; i<plRun.size(); i++) {
                    eu.oreplay.db.Runner voRun = plRun.get(i);
                    if (voRun!=null && voRun.getRunnerResultList()!=null) {
                        if (!voRun.getRunnerResultList().isEmpty()) {
                            for (int j=0; j<voRun.getRunnerResultList().size(); j++) {
                                eu.oreplay.db.RunnerResult voRes = voRun.getRunnerResultList().get(j);
                                //If runner has a position and a time, and his time is less than current minimum or current minimum equals 0, update the minimum
                                if ((voRes.getPosition()!=null && voRes.getPosition()>0) && 
                                        voRes.getTimeSeconds()!=null &&
                                        voRes.getTimeSeconds().compareTo(BigDecimal.ZERO)==1 && 
                                        (voRes.getTimeSeconds().compareTo(vlResul.get(j))==-1 || vlResul.get(j).compareTo(BigDecimal.ZERO)==0)) {
                                    vlResul.set(j, voRes.getTimeSeconds());
                                }
                            }
                        }
                    }                    
                }
            }
        }catch(Exception e) {
            //Nothing to do
        }
        return vlResul;
    }

    /**
     * Given a list of runners, each of them with a list of results, 
     * this method loops the list to find the greatest number of results
     * (it should be the same for all of the runners)
     * @param plRun List<eu.oreplay.db.Runner> List of runners with results
     * @return int Maximum number of results (overall and each of the stages)
     */
    public int getNumberOfStagesFromResultList (List<eu.oreplay.db.Runner> plRun) {
        int vnResul = 0;
        try {
            if (plRun!=null && !plRun.isEmpty()) {
                //Loop to get the maximum number of stages
                for (int i=0; i<plRun.size(); i++) {
                    eu.oreplay.db.Runner voRun = plRun.get(i);
                    if (voRun!=null && voRun.getRunnerResultList()!=null) {
                        if (vnResul<voRun.getRunnerResultList().size()) {
                            vnResul = voRun.getRunnerResultList().size();
                        }
                    }                    
                }

            }
        }catch(Exception e) {
            //Nothing to do
        }
        return vnResul;
    }
    /**
     * Inspect the contents of the CSV to search for the number of stages;
     * some runners take part in some stages, some others take part in all;
     * this method gets the maximum checked 'took part' columns (I1, I2, I3, I4, I5, I6)
     * @param plResult List<String> List with elements for each line of a CSV file for result list
     * @return int the number of the stages from the took part columns
     */
    public int getNumberOfStagesFromCsv (List<String> plResult) {
        int vnResul = 0;
        //Depending on the type of file, the columns of the fields are different
        int vnColIndex = this.getIndexFromContentsAndSource();       
        try {
            //Maximum number of stages supported by the CSV format
            int vnMaxStages = COL_STAGE_INC[vnColIndex];
            //Starting index of the 'took part' columns
            int vnTookPart = COL_STAGE_TOOKPART[vnColIndex];
            //Process the contents
            if (plResult!=null && vnMaxStages>0 && vnTookPart>0) {
                //First line contains the name of the columns
                String vcLine = "";
                //Loop starts at second line
                for (int i=1; i<plResult.size(); i++) {
                    vcLine = plResult.get(i);
                    String[] vaRecord = vcLine.split(cSeparator);
                    if (vaRecord.length>=(vnTookPart+vnMaxStages)) {
                        //Loop to look at the participation of the runner
                        for (int j=0; j<vnMaxStages; j++) {
                            String vcTookPart = vaRecord[vnTookPart+j].trim().replaceAll("\"", "").toLowerCase();
                            //If the field is checked and the current number of stages is less than the stage the runner took part, update the value
                            if ((vcTookPart.equals("x") || vcTookPart.equals("1")) &&
                                    (vnResul<(j+1))) {
                                vnResul = j+1;
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            //Nothing to do
        }
        return vnResul;
    }
    /**
     * After processing a file, some time behind values could not be calculated
     * because there are several stages and total values and not all the runners
     * are after the first runner in all cases
     * @param poEve eu.oreplay.db.Event Event with current values
     * @return eu.oreplay.db.Event Event after second calculation
     */
    public eu.oreplay.db.Event recalculateTimeBehind (eu.oreplay.db.Event poEve) {
        try {
            if (poEve!=null && poEve.getStageList()!=null && !poEve.getStageList().isEmpty()) {
                //Loop the event stages
                for (int i=0; i<poEve.getStageList().size(); i++) {
                    eu.oreplay.db.Stage voSta = poEve.getStageList().get(i);
                    if (voSta!=null && voSta.getClazzList()!=null && !voSta.getClazzList().isEmpty()) {
                        //Loop the stage classes
                        for (int j=0; j<voSta.getClazzList().size(); j++) {
                            eu.oreplay.db.Clazz voCla = voSta.getClazzList().get(j);
                            if (voCla!=null && voCla.getRunnerList()!=null && !voCla.getRunnerList().isEmpty()) {
                                //Calculate only once the minimum time of the Overall and each stage
                                List<BigDecimal> vlMin = this.calculateMinTimes (voCla.getRunnerList());
                                //Loop the class runners
                                for (int k=0; k<voCla.getRunnerList().size(); k++) {
                                    eu.oreplay.db.Runner voRun = voCla.getRunnerList().get(k);
                                    if (voRun!=null && voRun.getRunnerResultList()!=null && !voRun.getRunnerResultList().isEmpty()) {
                                        //Loop the runner results (overall + each stage)
                                        int l=0;
                                        while (l<voRun.getRunnerResultList().size()) {
                                            eu.oreplay.db.RunnerResult voRes = voRun.getRunnerResultList().get(l);
                                            BigDecimal vnTimeBehind = BigDecimal.ZERO;
                                            if (voRes.getTimeSeconds()!=null && voRes.getTimeSeconds().compareTo(BigDecimal.ZERO)==1) {
                                                //Calculates time behind the first of the class
                                                //vnTimeBehind = this.calculateTimeBehind(voCla.getRunnerList(), voRes.getTimeSeconds(), l);
                                                //Calculates time behind the first of the class and stage, using the previously calculated minimums
                                                vnTimeBehind = voRes.getTimeSeconds().subtract(vlMin.get(l));
                                                //If it has no decimals, stores only the integer part
                                            }
                                            voRes.setTimeBehind(Utils.isWhole(vnTimeBehind)?new BigDecimal(vnTimeBehind.longValue()+""):vnTimeBehind);
                                            //Increase loop counter
                                            l++;
                                        }
                                    }
                                }
                            }
                        }
                    }                    
                }
            } 
        }catch (Exception e) {
            //Nothing to do
        }
        return poEve;
    }

}