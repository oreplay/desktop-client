/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.logic.converter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.oreplay.logic.iof.*;
import eu.oreplay.utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import org.apache.commons.io.input.BOMInputStream;

/**
 * Class with methods to convert data in IOF XML format to OReplay data model
 * @author javier.arufe
 */
public class ConverterIofToModel extends ConverterToModel {
    private eu.oreplay.db.Event oEve = null; //Event/Stage basic information to perform the calculations (UUID, base date, base time, description, etc)

    public ConverterIofToModel() {
        super();
    }
    public ConverterIofToModel (ConverterToModel poSrc) {
        super.copyValues(poSrc);
    }
    public ConverterIofToModel(eu.oreplay.db.Event poEve) {
        oEve = poEve;
    }
    
    public void setSpecificProperties (eu.oreplay.db.Event poEve) {
        oEve = poEve;
    }

    @JsonIgnore
    public eu.oreplay.db.Event getoEve() {
        return oEve;
    }

    public void setoEve(eu.oreplay.db.Event oEve) {
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
        eu.oreplay.logic.iof.StartList voStart = null;
        JAXBContext voContext = null;
        InputStream voIs = null;
        try {
            if (isbUtf())
                voIs = new BOMInputStream(new FileInputStream(poFile));
            else
                voIs = new FileInputStream(poFile);
            voContext = JAXBContext.newInstance(StartList.class);
            voStart = (StartList) voContext.createUnmarshaller()
                    .unmarshal(voIs);
            voIs.close();
            if (getcSource().equals(ConverterToModel.SRC_OE2010) ||
                    getcSource().equals(ConverterToModel.SRC_OEV12) ||
                    getcSource().equals(ConverterToModel.SRC_GENERICXML)) {
                voEve = convertStartListSingleStageClassic (voStart);
            }
        }catch(Exception e) { 
            voEve = null;
        }
        return voEve;
    }
    /**
     * Given a representation of IOF XML for start list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the XML file; this method is for 1-stage, classic Foot-O event
     * @param poStart StartList Object that represents an IOF XML file for start list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertStartListSingleStageClassic (eu.oreplay.logic.iof.StartList poStart) {
        eu.oreplay.db.Event voEve = null;
        String vcUuidEve = "";
        String vcUuidSta = "";
        if (oEve!=null) {
            vcUuidEve = oEve.getId();
            vcUuidSta = (oEve.getStageList()!=null?oEve.getStageList().get(0).getId():"");
        }
        if (poStart!=null) {
            //Event's data
            voEve = new eu.oreplay.db.Event();
            voEve.setId(vcUuidEve);
            voEve.setDescription(poStart.getEvent()!=null?poStart.getEvent().getName():"");
            //Stage's data
            eu.oreplay.db.Stage voSta = new eu.oreplay.db.Stage();
            voSta.setId(vcUuidSta);
            if (poStart.getEvent()!=null) {
                if (poStart.getEvent().getRace()!=null) {
                    if (poStart.getEvent().getRace().size()>0) {
                        voSta.setOrderNumber((poStart.getEvent().getRace().get(0).getRaceNumber()!=null?poStart.getEvent().getRace().get(0).getRaceNumber().intValue():1));
                        voSta.setDescription((poStart.getEvent().getRace().get(0).getName()!=null?poStart.getEvent().getRace().get(0).getName():""));
                    } else {
                        voSta.setOrderNumber(1);
                        voSta.setDescription(voEve.getDescription());
                    }
                } else {
                    voSta.setOrderNumber(1);
                    voSta.setDescription(voEve.getDescription());
                }
            }
            //Classes' data
            if (poStart.getClassStart()!=null) {
                //Process each class in the StartList
                ArrayList<eu.oreplay.db.Clazz> vlCla = new ArrayList<eu.oreplay.db.Clazz>();
                for (eu.oreplay.logic.iof.ClassStart voClassStart : poStart.getClassStart()) {
                    eu.oreplay.db.Clazz voCla = new eu.oreplay.db.Clazz();
                    voCla.setId("");
                    voCla.setUuid("");
                    eu.oreplay.logic.iof.Class voClass = voClassStart.getClazz();
                    if (voClass!=null) {
                        voCla.setOeKey((voClass.getId()!=null?voClass.getId().getValue():""));
                        voCla.setShortName(voClass.getShortName());
                        voCla.setLongName(voClass.getName());
                        //Now, the course of the class
                        if (voClassStart.getCourse()!=null) {
                            if (voClassStart.getCourse().size()>0) {
                                eu.oreplay.logic.iof.SimpleRaceCourse voSimple = voClassStart.getCourse().get(0);
                                if (voSimple!=null) {
                                    eu.oreplay.db.Course voCou = new eu.oreplay.db.Course();
                                    voCou.setId("");
                                    voCou.setUuid("");
                                    voCou.setOeKey((voSimple.getId()!=null?voSimple.getId().getValue():""));
                                    voCou.setShortName(voSimple.getName());
                                    voCou.setClimb((voSimple.getClimb()!=null?voSimple.getClimb()+"":""));
                                    voCou.setDistance((voSimple.getLength()!=null?voSimple.getLength()+"":""));
                                    voCou.setControls((voSimple.getNumberOfControls()!=null?voSimple.getNumberOfControls().intValue():0));
                                    //Add the course to the class
                                    voCla.setCourse(voCou);
                                }
                            }
                        }
                        if (voClassStart.getPersonStart()!=null) {
                            //Process each runner of the class
                            ArrayList<eu.oreplay.db.Runner> vlRun = new ArrayList<eu.oreplay.db.Runner>();
                            for (eu.oreplay.logic.iof.PersonStart voPersonStart : voClassStart.getPersonStart()) {
                                eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                                voRun.setId("");
                                voRun.setUuid("");
                                eu.oreplay.logic.iof.Person voPerson = voPersonStart.getPerson();
                                if (voPerson!=null) {
                                    voRun.setFirstName((voPerson.getName()!=null?voPerson.getName().getGiven():""));
                                    voRun.setLastName((voPerson.getName()!=null?voPerson.getName().getFamily():""));
                                }
                                //Start Time, Bib# and SiCard are in another place
                                if (voPersonStart.getStart()!=null) {
                                    if (voPersonStart.getStart().size()>0) {
                                        eu.oreplay.logic.iof.PersonRaceStart voPrs = voPersonStart.getStart().get(0);
                                        if (voPrs!=null) {
                                            voRun.setBibNumber(voPrs.getBibNumber());
                                            voRun.setSicard(voPrs.getControlCard()!=null?(voPrs.getControlCard().size()>0?(voPrs.getControlCard().get(0)!=null?voPrs.getControlCard().get(0).getValue():""):""):"");
                                            //Start Time is set in a first element of RunnerResult List
                                            ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<eu.oreplay.db.RunnerResult>();
                                            eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                                            voRes.setId("");
                                            voRes.setStageOrder(voSta.getOrderNumber());
                                            voRes.setLegNumber(1);
                                            //The type of result is null as it is processing only start times
                                            voRes.setResultType(null);
                                            //Transform the date value
                                            voRes.setStartTime((voPrs.getStartTime()!=null?voPrs.getStartTime().toGregorianCalendar().getTime():null));
                                            voRes.setStatusCode(Utils.STATUS_OK_ID);
                                            //Add the result to the list
                                            vlRes.add(voRes);
                                            //Add the list to the runner data
                                            voRun.setRunnerResultList(vlRes);
                                        }
                                    }
                                }
                                //Get Club info
                                if (voPersonStart.getOrganisation()!=null) {
                                    eu.oreplay.logic.iof.Organisation voOrg = voPersonStart.getOrganisation();
                                    eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                                    voClu.setId("");
                                    voClu.setUuid("");
                                    voClu.setOeKey(voOrg.getId()!=null?voOrg.getId().getValue():"");
                                    voClu.setShortName(voOrg.getShortName());
                                    voClu.setLongName(voOrg.getName());
                                    //Add the club to the runner
                                    voRun.setClub(voClu);
                                }
                                //Add the runner to the list of runners
                                vlRun.add(voRun);
                            }
                            //Set the list of runners to the class
                            voCla.setRunnerList(vlRun);
                        }
                    }
                    //Add the class to the list of classes
                    vlCla.add(voCla);
                }
                //Set the list of classes to the stage
                voSta.setClazzList(vlCla);
            }
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
        eu.oreplay.logic.iof.ResultList voResult = null;
        JAXBContext voContext = null;
        InputStream voIs = null;
        try {
            if (isbUtf())
                voIs = new BOMInputStream(new FileInputStream(poFile));
            else
                voIs = new FileInputStream(poFile);
            voContext = JAXBContext.newInstance(StartList.class);
            voResult = (ResultList) voContext.createUnmarshaller()
                    .unmarshal(voIs);
            voIs.close();
            if (getcSource().equals(ConverterToModel.SRC_OE2010) ||
                    getcSource().equals(ConverterToModel.SRC_OEV12) ||
                    getcSource().equals(ConverterToModel.SRC_GENERICXML)) {
                voEve = convertResultListSingleStageClassic (voResult);
            }
        }catch(Exception e) {            
            voEve = null;
        }
        return voEve;
    }
    /**
     * Given a representation of IOF XML for result list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the XML file; this method is for 1-stage, classic Foot-O event
     * @param poResult ResultList Object that represents an IOF XML file for result list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertResultListSingleStageClassic (eu.oreplay.logic.iof.ResultList poResult) {
        eu.oreplay.db.Event voEve = null;
        ArrayList<eu.oreplay.db.Control> vlCon = new ArrayList<eu.oreplay.db.Control>();
        HashMap<String, String> vlStations = new HashMap<String, String>();
        String vcUuidEve = "";
        String vcUuidSta = "";
        boolean vbRadio = false;
        //Flag to say whether the results include radiocontrols
        if (getcResultsType().equals(ConverterToModel.RES_RADIO))
            vbRadio = true;
        if (oEve!=null) {
            vcUuidEve = oEve.getId();
            vcUuidSta = (oEve.getStageList()!=null?oEve.getStageList().get(0).getId():"");
        }
        if (poResult!=null) {
            //Event's data
            voEve = new eu.oreplay.db.Event();
            voEve.setId(vcUuidEve);
            voEve.setDescription(poResult.getEvent()!=null?poResult.getEvent().getName():"");
            //Stage's data
            eu.oreplay.db.Stage voSta = new eu.oreplay.db.Stage();
            voSta.setId(vcUuidSta);
            if (poResult.getEvent()!=null) {
                if (poResult.getEvent().getRace()!=null) {
                    if (poResult.getEvent().getRace().size()>0) {
                        voSta.setOrderNumber((poResult.getEvent().getRace().get(0).getRaceNumber()!=null?poResult.getEvent().getRace().get(0).getRaceNumber().intValue():1));
                        voSta.setDescription((poResult.getEvent().getRace().get(0).getName()!=null?poResult.getEvent().getRace().get(0).getName():""));
                    } else {
                        voSta.setOrderNumber(1);
                        voSta.setDescription(voEve.getDescription());
                    }
                } else {
                    voSta.setOrderNumber(1);
                    voSta.setDescription(voEve.getDescription());
                }
            }
            //Classes' data
            if (poResult.getClassResult()!=null) {
                //Process each class in the ResultList
                ArrayList<eu.oreplay.db.Clazz> vlCla = new ArrayList<eu.oreplay.db.Clazz>();
                for (eu.oreplay.logic.iof.ClassResult voClassResult : poResult.getClassResult()) {
                    //List to store the radiocontrols of the class
                    ArrayList<eu.oreplay.db.ClazzControl> vlClaCon = new ArrayList<eu.oreplay.db.ClazzControl>();
                    //Creates a class
                    eu.oreplay.db.Clazz voCla = new eu.oreplay.db.Clazz();
                    voCla.setId("");
                    voCla.setUuid("");
                    eu.oreplay.logic.iof.Class voClass = voClassResult.getClazz();
                    if (voClass!=null) {
                        voCla.setOeKey((voClass.getId()!=null?voClass.getId().getValue():""));
                        voCla.setShortName(voClass.getShortName());
                        voCla.setLongName(voClass.getName());
                        //Now, the course of the class
                        if (voClassResult.getCourse()!=null) {
                            if (voClassResult.getCourse().size()>0) {
                                eu.oreplay.logic.iof.SimpleRaceCourse voSimple = voClassResult.getCourse().get(0);
                                if (voSimple!=null) {
                                    eu.oreplay.db.Course voCou = new eu.oreplay.db.Course();
                                    voCou.setId("");
                                    voCou.setUuid("");
                                    voCou.setOeKey((voSimple.getId()!=null?voSimple.getId().getValue():""));
                                    voCou.setShortName(voSimple.getName());
                                    voCou.setClimb((voSimple.getClimb()!=null?voSimple.getClimb()+"":""));
                                    voCou.setDistance((voSimple.getLength()!=null?voSimple.getLength()+"":""));
                                    voCou.setControls((voSimple.getNumberOfControls()!=null?voSimple.getNumberOfControls().intValue():0));
                                    //Add the course to the class
                                    voCla.setCourse(voCou);
                                }
                            }
                        }
                        if (voClassResult.getPersonResult()!=null) {
                            //Process each runner of the class
                            ArrayList<eu.oreplay.db.Runner> vlRun = new ArrayList<eu.oreplay.db.Runner>();
                            int vnContRun = 0;
                            for (eu.oreplay.logic.iof.PersonResult voPersonResult : voClassResult.getPersonResult()) {
                                eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                                voRun.setId("");
                                voRun.setUuid("");
                                eu.oreplay.logic.iof.Person voPerson = voPersonResult.getPerson();
                                if (voPerson!=null) {
                                    voRun.setFirstName((voPerson.getName()!=null?voPerson.getName().getGiven():""));
                                    voRun.setLastName((voPerson.getName()!=null?voPerson.getName().getFamily():""));
                                }
                                //Start Time, Bib#, SiCard and results are in another place
                                if (voPersonResult.getResult()!=null) {
                                    if (voPersonResult.getResult().size()>0) {
                                        eu.oreplay.logic.iof.PersonRaceResult voPrs = voPersonResult.getResult().get(0);
                                        if (voPrs!=null) {
                                            voRun.setBibNumber(voPrs.getBibNumber());
                                            voRun.setSicard(voPrs.getControlCard()!=null?(voPrs.getControlCard().size()>0?(voPrs.getControlCard().get(0)!=null?voPrs.getControlCard().get(0).getValue():""):""):"");
                                            //Start Time is set in a first element of RunnerResult List
                                            ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<eu.oreplay.db.RunnerResult>();
                                            eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                                            voRes.setId("");
                                            voRes.setStageOrder(voSta.getOrderNumber());
                                            voRes.setLegNumber(1);
                                            //Compose the type of result, which is a Stage Result
                                            eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
                                            voResType.setId(Utils.RESULT_STAGE_ID);
                                            voResType.setDescription(Utils.RESULT_STAGE_DESC);
                                            voRes.setResultType(voResType);
                                            //Transform date values
                                            voRes.setStartTime((voPrs.getStartTime()!=null?voPrs.getStartTime().toGregorianCalendar().getTime():null));
                                            voRes.setFinishTime((voPrs.getFinishTime()!=null?voPrs.getFinishTime().toGregorianCalendar().getTime():null));
                                            //Get the status, from IOF enumeration to OReplay Ids
                                            voRes.setStatusCode(Utils.convertIofStatusValue(voPrs.getStatus().value()));
                                            //Get times and position
                                            if (voPrs.getTime()!=null)
                                                voRes.setTimeSeconds(new java.math.BigDecimal(voPrs.getTime()));
                                            if (voPrs.getTimeBehind()!=null)
                                                voRes.setTimeBehind(new java.math.BigDecimal(voPrs.getTimeBehind()));
                                            if (voPrs.getPosition()!=null)
                                                voRes.setPosition(voPrs.getPosition().intValue());
                                            //Set remainder fields for points and times
                                            voRes.setPointsAdjusted(0);
                                            voRes.setPointsBonus(0);
                                            voRes.setPointsFinal(0);
                                            voRes.setPointsPenalty(0);
                                            voRes.setTimeAdjusted(BigDecimal.ZERO);
                                            voRes.setTimeBonus(BigDecimal.ZERO);
                                            voRes.setTimeNeutralization(BigDecimal.ZERO);
                                            voRes.setTimePenalty(BigDecimal.ZERO);
                                            //Now, process Splits, if present
                                            if (voPrs.getSplitTime()!=null) {
                                                if (voPrs.getSplitTime().size()>0) {
                                                    int vnSplOrder = 1;
                                                    ArrayList<eu.oreplay.db.Split> vlSpl = new ArrayList<eu.oreplay.db.Split>();
                                                    for (eu.oreplay.logic.iof.SplitTime voSplitTime : voPrs.getSplitTime()) {
                                                        eu.oreplay.db.Split voSpl = new eu.oreplay.db.Split();
                                                        voSpl.setStation(voSplitTime.getControlCode());
                                                        //Get the time, but only if the status is not missing
                                                        if (!voSplitTime.getStatus().equals(Utils.SPLIT_STATUS_MISSING)) {
                                                            try {
                                                                Date vdSplitTime = new Date((long)voRes.getStartTime().getTime() + (long)(voSplitTime.getTime() * 1000.0));
                                                                voSpl.setReadingMilli(new BigInteger(vdSplitTime.getTime()+""));
                                                                voSpl.setReadingTime(vdSplitTime);
                                                                BigDecimal vnTimeSeconds = BigDecimal.valueOf(voSplitTime.getTime());
                                                                //If it has no decimals, stores only the integer part
                                                                voSpl.setTimeSeconds(Utils.isWhole(vnTimeSeconds)?new BigDecimal(vnTimeSeconds.longValue()+""):vnTimeSeconds);
                                                            }catch (Exception eMilli) {
                                                                voSpl.setReadingMilli(null);
                                                                voSpl.setTimeSeconds(null);
                                                            }
                                                        } else {
                                                            voSpl.setReadingMilli(null);
                                                            voSpl.setTimeSeconds(null);
                                                        }
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
                                                            voCon.setStation(voSplitTime.getControlCode());
                                                            if (!vlStations.containsKey(voSplitTime.getControlCode())) {
                                                                vlStations.put(voSplitTime.getControlCode(), voSplitTime.getControlCode());
                                                                vlCon.add(voCon);
                                                            }
                                                            //If it's the first runner of the class, add the control to the list of controls of the class
                                                            if (vnContRun==0) {
                                                                eu.oreplay.db.ClazzControl voClaCon = new eu.oreplay.db.ClazzControl();
                                                                voClaCon.setControl(voCon);
                                                                vlClaCon.add(voClaCon);
                                                            }
                                                        }
                                                        //Increase the counter of the split order
                                                        vnSplOrder++;
                                                    }
                                                    //Add the list of splits to the results
                                                    voRes.setSplitList(vlSpl);
                                                }
                                            }
                                            //Add the result to the list
                                            vlRes.add(voRes);
                                            //Add the list to the runner data
                                            voRun.setRunnerResultList(vlRes);
                                        }
                                    }
                                }
                                //Get Club info
                                if (voPersonResult.getOrganisation()!=null) {
                                    eu.oreplay.logic.iof.Organisation voOrg = voPersonResult.getOrganisation();
                                    eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                                    voClu.setId("");
                                    voClu.setUuid("");
                                    voClu.setOeKey(voOrg.getId()!=null?voOrg.getId().getValue():"");
                                    voClu.setShortName(voOrg.getShortName());
                                    voClu.setLongName(voOrg.getName());
                                    //Add the club to the runner
                                    voRun.setClub(voClu);
                                }
                                //Add the runner to the list of runners
                                vlRun.add(voRun);
                                //Increase the counter of runners
                                vnContRun++;
                            }
                            //Set the list of runners to the class
                            voCla.setRunnerList(vlRun);
                        }
                        //If it's a result with radiocontrols, add the list of class-controls to the class
                        if (vbRadio && !vlClaCon.isEmpty()) {
                            voCla.setClazzControlList(vlClaCon);
                        }
                    }
                    //Add the class to the list of classes
                    vlCla.add(voCla);
                }
                //Set the list of classes to the stage
                voSta.setClazzList(vlCla);
                //If it's a result with radiocontrols, add the list of controls to the stage
                if (vbRadio && !vlCon.isEmpty()) {
                    voSta.setControlList(vlCon);
                }
            }
            //Add the stage to the event
            ArrayList<eu.oreplay.db.Stage> vlSta = new ArrayList<eu.oreplay.db.Stage>();
            vlSta.add(voSta);
            voEve.setStageList(vlSta);
        }
        return voEve;
    }

}