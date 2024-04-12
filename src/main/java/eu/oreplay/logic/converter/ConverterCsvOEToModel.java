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
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class with methods to convert data in CSV OE format to OReplay data model
 * @author javier.arufe
 */
public class ConverterCsvOEToModel extends ConverterToModel{
    private String cSeparator = ";";
    private String cEncoding = Utils.ENCODING_UTF_8;
    private eu.oreplay.db.Event oEve = null; //Event/Stage basic information to perform the calculations (UUID, base date, base time, description, etc)

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
            List<String> plStart = new ArrayList<String>();
            BufferedReader voBr = null;
            String vcLine = "";
            try {
                //Loops the file and filters line by line
                voBr = new BufferedReader(new InputStreamReader(new FileInputStream(poFile), cEncoding));
                while ((vcLine = voBr.readLine()) != null) {
                    plStart.add(vcLine);
                }
                voBr.close();
                if (getcSource().equals(ConverterToModel.SRC_OE2010)) {
                    voEve = convertStartListSingleStageClassicOE2010 (plStart);
                } else if (getcSource().equals(ConverterToModel.SRC_OEV12)) {
                    voEve = convertStartListSingleStageClassicOEv12 (plStart);
                }
            } catch (Exception e) {
                voEve = null;
            }        
        }
        return voEve;
    }
    
    /**
     * Given a representation of OE2010 CSV for start list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the CSV file; this method is for 1-stage, classic Foot-O event
     * @param plStart List<String> List with elements for each line of a CSV file for start list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertStartListSingleStageClassicOE2010 (List<String> plStart) {
        eu.oreplay.db.Event voEve = null;
        //HashMap to store Classes. For each class its runners. Runners shoulb be ordered by class in the CSV but, what if not?...
        HashMap<String, eu.oreplay.db.Clazz> vlCla = new HashMap<String, eu.oreplay.db.Clazz>();
        if (plStart!=null) {
            //Event's data
            voEve = new eu.oreplay.db.Event();
            voEve.setId(oEve.getId());
            voEve.setDescription(oEve.getDescription());
            //Stage's data
            eu.oreplay.db.Stage voSta = new eu.oreplay.db.Stage();
            if (oEve.getStageList()!=null) {
                if (oEve.getStageList().size()>0) {
                    voSta.setId(oEve.getStageList().get(0).getId());
                    voSta.setOrderNumber(oEve.getStageList().get(0).getOrderNumber());
                    voSta.setDescription(oEve.getStageList().get(0).getDescription());
                    voSta.setBaseDate(oEve.getStageList().get(0).getBaseDate());
                    voSta.setBaseTime(oEve.getStageList().get(0).getBaseTime());
                }
            }
            //First line contains the name of the columns
            String vcLine = "";
            //Loop starts at second line
            for (int i=1; i<plStart.size(); i++) {
                vcLine = plStart.get(i);
                String[] vaRecord = vcLine.split(cSeparator);
                if (vaRecord.length>=57) {
                    String vcClaId = vaRecord[24].trim().replaceAll("\"", "");
                    String vcClaShort = vaRecord[25].trim().replaceAll("\"", "");
                    String vcClaLong = vaRecord[26].trim().replaceAll("\"", "");
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
                    if (voCla.getCourse()==null && !vaRecord[52].trim().replaceAll("\"", "").equals("")) {
                        eu.oreplay.db.Course voCou = new eu.oreplay.db.Course();
                        voCou.setId("");
                        voCou.setUuid("");
                        voCou.setOeKey(vaRecord[52].trim().replaceAll("\"", ""));
                        voCou.setShortName(vaRecord[53].trim().replaceAll("\"", ""));
                        voCou.setDistance(vaRecord[54].trim().replaceAll("\"", ""));
                        voCou.setClimb(vaRecord[55].trim().replaceAll("\"", ""));
                        int vnControls = 0;
                        try {
                            vnControls = Integer.parseInt(vaRecord[56].trim().replaceAll("\"", ""));
                        }catch (Exception eNumCon){};
                        voCou.setControls(vnControls);
                        //Add the course to the class
                        voCla.setCourse(voCou);
                    }
                    //Process the runner and put it on the class
                    List<eu.oreplay.db.Runner> vlRun = voCla.getRunnerList();
                    if (vlRun==null)
                        vlRun = new ArrayList<eu.oreplay.db.Runner>();
                    eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                    voRun.setId("");
                    voRun.setUuid("");
                    voRun.setBibNumber(vaRecord[1].trim().replaceAll("\"", ""));
                    voRun.setBibAlt(vaRecord[2].trim().replaceAll("\"", ""));
                    voRun.setSicard(vaRecord[3].trim().replaceAll("\"", ""));
                    voRun.setDbId(vaRecord[4].trim().replaceAll("\"", ""));
                    voRun.setLastName(vaRecord[5].trim().replaceAll("\"", ""));
                    voRun.setFirstName(vaRecord[6].trim().replaceAll("\"", ""));
                    voRun.setSex((vaRecord[7].length()>0?vaRecord[7].trim().replaceAll("\"", "").charAt(0):' '));
                    //Start Time is set in a first element of RunnerResult List
                    ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<eu.oreplay.db.RunnerResult>();
                    eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                    voRes.setId("");
                    voRes.setStageOrder(voSta.getOrderNumber());
                    voRes.setLegNumber(1);
                    //Compose the type of result, which is a Stage Result
                    eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
                    voResType.setId("e4ddfa9d-3347-47e4-9d32-c6c119aeac0e");
                    voResType.setDescription("Stage");
                    voRes.setResultType(voResType);
                    //Transform the date value
                    try {
                        java.util.Date vdStart = Utils.formatRelativeTimeFromBase(vaRecord[11].trim().replaceAll("\"", ""), 
                                voSta.getBaseDate(), voSta.getBaseTime());
                        voRes.setStartTime(vdStart);
                    }catch(Exception e5) {
                    }
                    voRes.setStatusCode('0');
                    //Add the result to the list
                    vlRes.add(voRes);
                    //Add the list to the runner data
                    voRun.setRunnerResultList(vlRes);
                    //Now, the club of the runner
                    if (!vaRecord[18].trim().replaceAll("\"", "").equals("")) {
                        eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                        voClu.setId("");
                        voClu.setUuid("");
                        voClu.setOeKey(vaRecord[18].trim().replaceAll("\"", ""));
                        voClu.setCity(vaRecord[19].trim().replaceAll("\"", ""));
                        voClu.setShortName(vaRecord[20].trim().replaceAll("\"", ""));
                        voClu.setLongName(vaRecord[20].trim().replaceAll("\"", ""));
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
                }
            }
            //Add the lis of classes to the stage
            List<eu.oreplay.db.Clazz> vlClaNew = new ArrayList<eu.oreplay.db.Clazz>(vlCla.values());
            voSta.setClazzList(vlClaNew);
            //Add the stage to the event
            ArrayList<eu.oreplay.db.Stage> vlSta = new ArrayList<eu.oreplay.db.Stage>();
            vlSta.add(voSta);
            voEve.setStageList(vlSta);
        }
        return voEve;
    }
    /**
     * Given a representation of OEv12 CSV for start list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the CSV file; this method is for 1-stage, classic Foot-O event
     * @param plStart List<String> List with elements for each line of a CSV file for start list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertStartListSingleStageClassicOEv12 (List<String> plStart) {
        eu.oreplay.db.Event voEve = null;
        //HashMap to store Classes. For each class its runners. Runners shoulb be ordered by class in the CSV but, what if not?...
        HashMap<String, eu.oreplay.db.Clazz> vlCla = new HashMap<String, eu.oreplay.db.Clazz>();
        if (plStart!=null) {
            //Event's data
            voEve = new eu.oreplay.db.Event();
            voEve.setId(oEve.getId());
            voEve.setDescription(oEve.getDescription());
            //Stage's data
            eu.oreplay.db.Stage voSta = new eu.oreplay.db.Stage();
            if (oEve.getStageList()!=null) {
                if (oEve.getStageList().size()>0) {
                    voSta.setId(oEve.getStageList().get(0).getId());
                    voSta.setOrderNumber(oEve.getStageList().get(0).getOrderNumber());
                    voSta.setDescription(oEve.getStageList().get(0).getDescription());
                    voSta.setBaseDate(oEve.getStageList().get(0).getBaseDate());
                    voSta.setBaseTime(oEve.getStageList().get(0).getBaseTime());
                }
            }
            //First line contains the name of the columns
            if (plStart != null) {
                String vcLine = "";
                //Loop starts at second line
                for (int i=1; i<plStart.size(); i++) {
                    vcLine = plStart.get(i);
                    String[] vaRecord = vcLine.split(cSeparator);
                    if (vaRecord.length>=61) {
                        String vcClaId = vaRecord[27].trim().replaceAll("\"", "");
                        String vcClaShort = vaRecord[28].trim().replaceAll("\"", "");
                        String vcClaLong = vaRecord[29].trim().replaceAll("\"", "");
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
                        if (voCla.getCourse()==null && !vaRecord[56].trim().replaceAll("\"", "").equals("")) {
                            eu.oreplay.db.Course voCou = new eu.oreplay.db.Course();
                            voCou.setId("");
                            voCou.setUuid("");
                            voCou.setOeKey(vaRecord[56].trim().replaceAll("\"", ""));
                            voCou.setShortName(vaRecord[57].trim().replaceAll("\"", ""));
                            voCou.setDistance(vaRecord[58].trim().replaceAll("\"", ""));
                            voCou.setClimb(vaRecord[59].trim().replaceAll("\"", ""));
                            int vnControls = 0;
                            try {
                                vnControls = Integer.parseInt(vaRecord[60].trim().replaceAll("\"", ""));
                            }catch (Exception eNumCon){};
                            voCou.setControls(vnControls);
                            //Add the course to the class
                            voCla.setCourse(voCou);
                        }
                        //Process the runner and put it on the class
                        List<eu.oreplay.db.Runner> vlRun = voCla.getRunnerList();
                        if (vlRun==null)
                            vlRun = new ArrayList<eu.oreplay.db.Runner>();
                        eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                        voRun.setId("");
                        voRun.setUuid("");
                        voRun.setBibNumber(vaRecord[2].trim().replaceAll("\"", ""));
                        voRun.setBibAlt(vaRecord[3].trim().replaceAll("\"", ""));
                        voRun.setSicard(vaRecord[4].trim().replaceAll("\"", ""));
                        voRun.setDbId(vaRecord[5].trim().replaceAll("\"", ""));
                        voRun.setIofId(vaRecord[6].trim().replaceAll("\"", ""));
                        voRun.setLastName(vaRecord[7].trim().replaceAll("\"", ""));
                        voRun.setFirstName(vaRecord[8].trim().replaceAll("\"", ""));
                        //Next, it's the BirthDate and would need conversion from text to Date. But, OE doesn't fill that value
                        //voRun.setBirthDate(vaRecord[9].trim().replaceAll("\"", ""));
                        voRun.setSex((vaRecord[11].length()>0?vaRecord[11].trim().replaceAll("\"", "").charAt(0):' '));
                        //Start Time is set in a first element of RunnerResult List
                        ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<eu.oreplay.db.RunnerResult>();
                        eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                        voRes.setId("");
                        voRes.setStageOrder(voSta.getOrderNumber());
                        voRes.setLegNumber(1);
                        //Compose the type of result, which is a Stage Result
                        eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
                        voResType.setId("e4ddfa9d-3347-47e4-9d32-c6c119aeac0e");
                        voResType.setDescription("Stage");
                        voRes.setResultType(voResType);
                        //Transform the date value
                        try {
                            java.util.Date vdStart = Utils.formatRelativeTimeFromBase(vaRecord[14].trim().replaceAll("\"", ""), 
                                    voSta.getBaseDate(), voSta.getBaseTime());
                            voRes.setStartTime(vdStart);
                        }catch(Exception e5) {
                        }
                        voRes.setStatusCode('0');
                        //Add the result to the list
                        vlRes.add(voRes);
                        //Add the list to the runner data
                        voRun.setRunnerResultList(vlRes);
                        //Now, the club of the runner
                        if (!vaRecord[18].trim().replaceAll("\"", "").equals("")) {
                            eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                            voClu.setId("");
                            voClu.setUuid("");
                            voClu.setOeKey(vaRecord[21].trim().replaceAll("\"", ""));
                            voClu.setCity(vaRecord[22].trim().replaceAll("\"", ""));
                            voClu.setShortName(vaRecord[23].trim().replaceAll("\"", ""));
                            voClu.setLongName(vaRecord[23].trim().replaceAll("\"", ""));
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
                    }
                }
            }
            //Add the lis of classes to the stage
            List<eu.oreplay.db.Clazz> vlClaNew = new ArrayList<eu.oreplay.db.Clazz>(vlCla.values());
            voSta.setClazzList(vlClaNew);
            //Add the stage to the event
            ArrayList<eu.oreplay.db.Stage> vlSta = new ArrayList<eu.oreplay.db.Stage>();
            vlSta.add(voSta);
            voEve.setStageList(vlSta);
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
        }
        return voEve;
    }
    
}