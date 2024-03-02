/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.logic.converter;

import eu.oreplay.logic.iof.*;
import java.util.ArrayList;

/**
 * Class with methods to convert data in IOF XML format to OReplay data model
 * @author javier.arufe
 */
public class ConverterIofToModel {
    /**
     * Given a representation of IOF XML for start list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the XML file; this method is for 1-stage, classic Foot-O event
     * @param poStart StartList Object that represents an IOF XML file for start list
     * @param pcUuidEve String Event's UUID used in OReplay DB to identify the Event
     * @param pcUuidSta String Stage's UUID used in OReplay DB to identify the Stage
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertStartListSingleStageClassic (eu.oreplay.logic.iof.StartList poStart, String pcUuidEve, String pcUuidSta) {
        eu.oreplay.db.Event voEve = null;
        if (poStart!=null) {
            //Event's data
            voEve = new eu.oreplay.db.Event();
            voEve.setId(pcUuidEve);
            voEve.setDescription(poStart.getEvent()!=null?poStart.getEvent().getName():"");
            //Stage's data
            eu.oreplay.db.Stage voSta = new eu.oreplay.db.Stage();
            voSta.setId(pcUuidSta);
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
                                            //Compose the type of result, which is a Stage Result
                                            eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
                                            voResType.setId("e4ddfa9d-3347-47e4-9d32-c6c119aeac0e");
                                            voResType.setDescription("Stage");
                                            voRes.setResultType(voResType);
                                            //Transform the date value
                                            voRes.setStartTime((voPrs.getStartTime()!=null?voPrs.getStartTime().toGregorianCalendar().getTime():null));
                                            voRes.setStatusCode('0');
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
}