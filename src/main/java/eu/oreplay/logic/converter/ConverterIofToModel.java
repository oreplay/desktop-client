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
import java.util.List;
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
    public eu.oreplay.db.Event convertEntryList (String pcFile) {
        File voFile = new File(pcFile);
        eu.oreplay.db.Event voResul = convertEntryList (voFile);
        voFile = null;
        return voResul;
    }
    @Override
    public eu.oreplay.db.Event convertEntryList (File poFile) {
        eu.oreplay.db.Event voEve = null;
        eu.oreplay.logic.iof.EntryList voEntry = null;
        JAXBContext voContext = null;
        InputStream voIs = null;
        try {
            if (isbUtf())
                voIs = new BOMInputStream(new FileInputStream(poFile));
            else
                voIs = new FileInputStream(poFile);
            voContext = JAXBContext.newInstance(EntryList.class);
            voEntry = (EntryList) voContext.createUnmarshaller()
                    .unmarshal(voIs);
            voIs.close();
            if (getcSource().equals(ConverterToModel.SRC_OE2010) ||
                    getcSource().equals(ConverterToModel.SRC_OEV12) ||
                    getcSource().equals(ConverterToModel.SRC_GENERICXML) || 
                    getcSource().equals(ConverterToModel.SRC_MEOS)) {
                voEve = convertEntryListSingleStageClassic (voEntry);
            } else if (getcSource().equals(ConverterToModel.SRC_OS2010) ||
                    getcSource().equals(ConverterToModel.SRC_OSV12)) {
                voEve = convertEntryListSingleStageRelay (voEntry);
            } else if (getcSource().equals(ConverterToModel.SRC_OESCOREV12) ||
                    getcSource().equals(ConverterToModel.SRC_OESCORE2010) ||
                    getcSource().equals(ConverterToModel.SRC_SITIMING)) {
                voEve = convertEntryListSingleStageRogaine (voEntry);
            }
        }catch(Exception e) { 
            voEve = null;
        }
        return voEve;
    }
    /**
     * Given a representation of IOF XML for entry list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the XML file; this method is for 1-stage, classic Foot-O event
     * @param poEntry EntryList Object that represents an IOF XML file for entry list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners, teams)
     */
    public eu.oreplay.db.Event convertEntryListSingleStageClassic (eu.oreplay.logic.iof.EntryList poEntry) {
        eu.oreplay.db.Event voEve = null;
        //HashMap to store Classes. For each class its runners or teams.
        HashMap<String, eu.oreplay.db.Clazz> vlCla = new HashMap<>();
        try {
            if (poEntry!=null) {
                //Try to set the date and time of the stage from the XML
                try {
                    if (!isbForce()) {
                        oEve.getStageList().get(0).setBaseDate(poEntry.getEvent().getStartTime().getDate().toGregorianCalendar().getTime());
                        oEve.getStageList().get(0).setBaseTime(poEntry.getEvent().getStartTime().getTime().toGregorianCalendar().getTime());
                    } else {
                        oEve.getStageList().get(0).setBaseDate(Utils.parse(getcStageDate(), getcDateFormat()));
                        oEve.getStageList().get(0).setBaseTime(Utils.convertGregorianDateFromXmlOrForced(poEntry.getEvent().getStartTime().getTime(), isbForce(), getcStageDate(), getcDateFormat()));
                    }
                }catch(Exception eDateTime) {
                }
                //Event's data
                voEve = Utils.copyBasicEventData(oEve);
                //Stage's data
                eu.oreplay.db.Stage voSta = Utils.copyBasicOneStageData(oEve);
                //---------------------------------------------------------------------------
                //Some files would only have individual persons. Some other would have also teams and then teammembers
                //Start processing individuals
                //---------------------------------------------------------------------------
                if (poEntry.getPersonEntry()!=null) {
                    for (eu.oreplay.logic.iof.PersonEntry voPersonEntry : poEntry.getPersonEntry()) {
                        eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                        voRun.setId("");
                        voRun.setUuid("");
                        eu.oreplay.logic.iof.Person voPerson = voPersonEntry.getPerson();
                        if (voPerson!=null) {
                            try {
                                voRun.setDbId(voPerson.getId().get(0).getValue());
                            }catch(Exception eEntryId) {
                            }
                            voRun.setFirstName((voPerson.getName()!=null?voPerson.getName().getGiven():""));
                            voRun.setLastName((voPerson.getName()!=null?voPerson.getName().getFamily():""));
                            Character vcSex = (voPerson.getSex()!=null?(voPerson.getSex().length()>0?voPerson.getSex().charAt(0):'M'):'M');
                            voRun.setSex(vcSex);
                        }
                        //SiTiming treats teams as one person with serveral SiCards. I get only the first one
                        voRun.setSicard(voPersonEntry.getControlCard()!=null?(!voPersonEntry.getControlCard().isEmpty()?(voPersonEntry.getControlCard().get(0)!=null?voPersonEntry.getControlCard().get(0).getValue():""):""):"");
                        //Check whether there is an alternative SiCard
                        voRun.setSicardAlt(voPersonEntry.getControlCard()!=null?(voPersonEntry.getControlCard().size()>1?(voPersonEntry.getControlCard().get(1)!=null?voPersonEntry.getControlCard().get(1).getValue():""):""):"");
                        //Get Club info
                        if (voPersonEntry.getOrganisation()!=null) {
                            eu.oreplay.logic.iof.Organisation voOrg = voPersonEntry.getOrganisation();
                            eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                            voClu.setId("");
                            voClu.setUuid("");
                            voClu.setOeKey(voOrg.getId()!=null?voOrg.getId().getValue():"");
                            voClu.setShortName(voOrg.getShortName());
                            voClu.setLongName(voOrg.getName());
                            //Add the club to the runner
                            voRun.setClub(voClu);
                        }
                        //Get Class info
                        eu.oreplay.db.Clazz voCla = new eu.oreplay.db.Clazz();
                        voCla.setId("");
                        voCla.setUuid("");
                        if (voPersonEntry.getClazz()!=null && !voPersonEntry.getClazz().isEmpty()) {
                            eu.oreplay.logic.iof.Class voClass = voPersonEntry.getClazz().get(0);
                            if (voClass!=null) {
                                voCla.setOeKey((voClass.getId()!=null?voClass.getId().getValue():""));
                                voCla.setShortName(voClass.getShortName());
                                voCla.setLongName(voClass.getName());
                            }
                        }                        
                        //Search for the class in the HashMap
                        if (vlCla.containsKey(voCla.getOeKey())) {
                            voCla = vlCla.get(voCla.getOeKey());
                        }
                        //Process the runner and put it on the class
                        List<eu.oreplay.db.Runner> vlRun = voCla.getRunnerList();
                        if (vlRun==null)
                            vlRun = new ArrayList<>();                      
                        //Add the runner to the list of runners
                        vlRun.add(voRun);
                        //Set the list of runners to the class again
                        voCla.setRunnerList(vlRun);
                        //Remove the previous contents of the class from the HashMap and insert it again
                        vlCla.remove(voCla.getOeKey());
                        vlCla.put(voCla.getOeKey(), voCla);
                    }
                }
                //---------------------------------------------------------------------------
                //Now, process the teams if the file has teams instead of individuals
                //---------------------------------------------------------------------------
                if (poEntry.getTeamEntry()!=null) {
                    for (eu.oreplay.logic.iof.TeamEntry voTeamEntry : poEntry.getTeamEntry()) {
                        eu.oreplay.db.Team voTea = new eu.oreplay.db.Team();
                        voTea.setId("");
                        voTea.setUuid("");
                        voTea.setTeamName((voTeamEntry.getName()!=null?voTeamEntry.getName():""));
                        //Get Club info
                        if (voTeamEntry.getOrganisation()!=null && !voTeamEntry.getOrganisation().isEmpty()) {
                            eu.oreplay.logic.iof.Organisation voOrg = voTeamEntry.getOrganisation().get(0);
                            eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                            voClu.setId("");
                            voClu.setUuid("");
                            voClu.setOeKey(voOrg.getId()!=null?voOrg.getId().getValue():"");
                            voClu.setShortName(voOrg.getShortName());
                            voClu.setLongName(voOrg.getName());
                            //Add the club to the team
                            voTea.setClub(voClu);
                        }
                        //Process the team's runners
                        if (voTeamEntry.getTeamEntryPerson()!=null) {
                            //Process each runner of the team
                            ArrayList<eu.oreplay.db.Runner> vlRun = new ArrayList<>();
                            for (eu.oreplay.logic.iof.TeamEntryPerson voTeamEntryPerson : voTeamEntry.getTeamEntryPerson()) {
                                eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                                voRun.setId("");
                                voRun.setUuid("");
                                eu.oreplay.logic.iof.Person voPerson = voTeamEntryPerson.getPerson();
                                if (voPerson!=null) {
                                    voRun.setFirstName((voPerson.getName()!=null?voPerson.getName().getGiven():""));
                                    voRun.setLastName((voPerson.getName()!=null?voPerson.getName().getFamily():""));
                                    Character vcSex = (voPerson.getSex()!=null?(voPerson.getSex().length()>0?voPerson.getSex().charAt(0):'M'):'M');
                                    voRun.setSex(vcSex);
                                }
                                //Add the club to the runner, copying the same of the team
                                voRun.setClub(voTea.getClub());
                                //SiCard
                                voRun.setSicard(voTeamEntryPerson.getControlCard()!=null?(!voTeamEntryPerson.getControlCard().isEmpty()?(voTeamEntryPerson.getControlCard().get(0)!=null?voTeamEntryPerson.getControlCard().get(0).getValue():""):""):"");
                                //Check whether there is an alternative SiCard
                                voRun.setSicardAlt(voTeamEntryPerson.getControlCard()!=null?(voTeamEntryPerson.getControlCard().size()>1?(voTeamEntryPerson.getControlCard().get(1)!=null?voTeamEntryPerson.getControlCard().get(1).getValue():""):""):"");
                                //Leg
                                if (voTeamEntryPerson.getLeg()!=null)
                                    voRun.setLegNumber(voTeamEntryPerson.getLeg().intValue());
                                //Add the runner to the list of runners
                                vlRun.add(voRun);                                        
                            }
                            //Set the list of runners to the team
                            voTea.setRunnerList(vlRun);
                        }
                        //Get Class info
                        eu.oreplay.db.Clazz voCla = new eu.oreplay.db.Clazz();
                        voCla.setId("");
                        voCla.setUuid("");
                        if (voTeamEntry.getClazz()!=null && !voTeamEntry.getClazz().isEmpty()) {
                            eu.oreplay.logic.iof.Class voClass = voTeamEntry.getClazz().get(0);
                            if (voClass!=null) {
                                voCla.setOeKey((voClass.getId()!=null?voClass.getId().getValue():""));
                                voCla.setShortName(voClass.getShortName());
                                voCla.setLongName(voClass.getName());
                            }
                        }                        
                        //Search for the class in the HashMap
                        if (vlCla.containsKey(voCla.getOeKey())) {
                            voCla = vlCla.get(voCla.getOeKey());
                        }
                        //Process the team and put it on the class
                        List<eu.oreplay.db.Team> vlTea = voCla.getTeamList();
                        if (vlTea==null)
                            vlTea = new ArrayList<>();                      
                        //Add the team to the list of teams of the class
                        vlTea.add(voTea);
                        //Set the list of teams to the class again
                        voCla.setTeamList(vlTea);
                        //Remove the previous contents of the class from the HashMap and insert it again
                        vlCla.remove(voCla.getOeKey());
                        vlCla.put(voCla.getOeKey(), voCla);
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
        }catch(Exception e) {
        }
        return voEve;
    }

    /**
     * Given a representation of IOF XML for entry list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the XML file; this method is for 1-stage, Relay event
     * @param poEntry EntryList Object that represents an IOF XML file for entry list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners, teams)
     */
    public eu.oreplay.db.Event convertEntryListSingleStageRelay (eu.oreplay.logic.iof.EntryList poEntry) {
        eu.oreplay.db.Event voEve = convertEntryListSingleStageClassic (poEntry);
        return voEve;
    }
    /**
     * Given a representation of IOF XML for entry list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the XML file; this method is for 1-stage, Rogaine event
     * @param poEntry EntryList Object that represents an IOF XML file for entry list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners, teams)
     */
    public eu.oreplay.db.Event convertEntryListSingleStageRogaine (eu.oreplay.logic.iof.EntryList poEntry) {
        eu.oreplay.db.Event voEve = convertEntryListSingleStageClassic (poEntry);
        return voEve;
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
                    getcSource().equals(ConverterToModel.SRC_GENERICXML) || 
                    getcSource().equals(ConverterToModel.SRC_MEOS)) {
                voEve = convertStartListSingleStageClassic (voStart);
            } else if (getcSource().equals(ConverterToModel.SRC_OS2010) ||
                    getcSource().equals(ConverterToModel.SRC_OSV12)) {
                voEve = convertStartListSingleStageRelay (voStart);
            } else if (getcSource().equals(ConverterToModel.SRC_OESCOREV12) ||
                    getcSource().equals(ConverterToModel.SRC_OESCORE2010) ||
                    getcSource().equals(ConverterToModel.SRC_SITIMING)) {
                voEve = convertStartListSingleStageRogaine (voStart);
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
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults, teams and teamresults)
     */
    public eu.oreplay.db.Event convertStartListSingleStageClassic (eu.oreplay.logic.iof.StartList poStart) {
        eu.oreplay.db.Event voEve = null;
        try {
            if (poStart!=null) {
                //Try to set the date and time of the stage from the XML
                try {
                    if (!isbForce()) {
                        oEve.getStageList().get(0).setBaseDate(poStart.getEvent().getStartTime().getDate().toGregorianCalendar().getTime());
                        oEve.getStageList().get(0).setBaseTime(poStart.getEvent().getStartTime().getTime().toGregorianCalendar().getTime());
                    } else {
                        oEve.getStageList().get(0).setBaseDate(Utils.parse(getcStageDate(), getcDateFormat()));
                        oEve.getStageList().get(0).setBaseTime(Utils.convertGregorianDateFromXmlOrForced(poStart.getEvent().getStartTime().getTime(), isbForce(), getcStageDate(), getcDateFormat()));
                    }
                }catch(Exception eDateTime) {
                }
                //Event's data
                voEve = Utils.copyBasicEventData(oEve);
                //Stage's data
                eu.oreplay.db.Stage voSta = Utils.copyBasicOneStageData(oEve);
                //Classes' data
                if (poStart.getClassStart()!=null) {
                    //Process each class in the StartList
                    ArrayList<eu.oreplay.db.Clazz> vlCla = new ArrayList<>();
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
                            if (voClassStart.getCourse()!=null && !voClassStart.getCourse().isEmpty()) {
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
                            //---------------------------------------------------------------------------
                            //Some classes would only have individual persons. Some other would have also teams and then teammembers
                            //Start processing individuals
                            //---------------------------------------------------------------------------
                            if (voClassStart.getPersonStart()!=null) {
                                //Process each runner of the class
                                ArrayList<eu.oreplay.db.Runner> vlRun = new ArrayList<>();
                                for (eu.oreplay.logic.iof.PersonStart voPersonStart : voClassStart.getPersonStart()) {
                                    eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                                    voRun.setId("");
                                    voRun.setUuid("");
                                    eu.oreplay.logic.iof.Person voPerson = voPersonStart.getPerson();
                                    if (voPerson!=null) {
                                        voRun.setFirstName((voPerson.getName()!=null?voPerson.getName().getGiven():""));
                                        voRun.setLastName((voPerson.getName()!=null?voPerson.getName().getFamily():""));
                                        Character vcSex = (voPerson.getSex()!=null?(voPerson.getSex().length()>0?voPerson.getSex().charAt(0):'M'):'M');
                                        voRun.setSex(vcSex);
                                    }
                                    //Start Time, Bib# and SiCard are in another place
                                    if (voPersonStart.getStart()!=null && !voPersonStart.getStart().isEmpty()) {
                                        eu.oreplay.logic.iof.PersonRaceStart voPrs = voPersonStart.getStart().get(0);
                                        if (voPrs!=null) {
                                            voRun.setBibNumber(voPrs.getBibNumber());
                                            //SiTiming treats teams as one person with serveral SiCards. I get only the first one
                                            voRun.setSicard(voPrs.getControlCard()!=null?(!voPrs.getControlCard().isEmpty()?(voPrs.getControlCard().get(0)!=null?voPrs.getControlCard().get(0).getValue():""):""):"");
                                            //Check whether there is an alternative SiCard
                                            voRun.setSicardAlt(voPrs.getControlCard()!=null?(voPrs.getControlCard().size()>1?(voPrs.getControlCard().get(1)!=null?voPrs.getControlCard().get(1).getValue():""):""):"");
                                            //Start Time is set in a first element of RunnerResult List
                                            ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<>();
                                            eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                                            voRes.setId("");
                                            voRes.setStageOrder(voSta.getOrderNumber());
                                            voRes.setLegNumber(1);
                                            //The type of result is null as it is processing only start times
                                            voRes.setResultType(null);
                                            //Transform the date value
                                            //voRes.setStartTime((voPrs.getStartTime()!=null?voPrs.getStartTime().toGregorianCalendar().getTime():null));
                                            voRes.setStartTime(Utils.convertGregorianDateFromXmlOrForced(voPrs.getStartTime(), 
                                                    isbForce(), getcStageDate(), getcDateFormat()));
                                            //There is only Status info at Result tag
                                            voRes.setStatusCode(Utils.STATUS_OK_ID);
                                            //Add the result to the list
                                            vlRes.add(voRes);
                                            //Add the list to the runner data
                                            voRun.setRunnerResultList(vlRes);
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
                            //---------------------------------------------------------------------------
                            //Now, process the teams if the class has teams instead of individuals
                            //---------------------------------------------------------------------------
                            if (voClassStart.getTeamStart()!=null) {
                                //Process each team of the class
                                ArrayList<eu.oreplay.db.Team> vlTea = new ArrayList<>();
                                for (eu.oreplay.logic.iof.TeamStart voTeamStart : voClassStart.getTeamStart()) {
                                    eu.oreplay.db.Team voTea = new eu.oreplay.db.Team();
                                    voTea.setId("");
                                    voTea.setUuid("");
                                    voTea.setBibNumber((voTeamStart.getBibNumber()!=null?voTeamStart.getBibNumber():""));
                                    voTea.setTeamName((voTeamStart.getName()!=null?voTeamStart.getName():""));
                                    //Get Club info
                                    if (voTeamStart.getOrganisation()!=null && !voTeamStart.getOrganisation().isEmpty()) {
                                        eu.oreplay.logic.iof.Organisation voOrg = voTeamStart.getOrganisation().get(0);
                                        eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                                        voClu.setId("");
                                        voClu.setUuid("");
                                        voClu.setOeKey(voOrg.getId()!=null?voOrg.getId().getValue():"");
                                        voClu.setShortName(voOrg.getShortName());
                                        voClu.setLongName(voOrg.getName());
                                        //Add the club to the team
                                        voTea.setClub(voClu);
                                    }
                                    //Process the team's runners
                                    if (voTeamStart.getTeamMemberStart()!=null) {
                                        //Process each runner of the team
                                        ArrayList<eu.oreplay.db.Runner> vlRun = new ArrayList<>();
                                        for (eu.oreplay.logic.iof.TeamMemberStart voTeamMemberStart : voTeamStart.getTeamMemberStart()) {
                                            eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                                            voRun.setId("");
                                            voRun.setUuid("");
                                            eu.oreplay.logic.iof.Person voPerson = voTeamMemberStart.getPerson();
                                            if (voPerson!=null) {
                                                voRun.setFirstName((voPerson.getName()!=null?voPerson.getName().getGiven():""));
                                                voRun.setLastName((voPerson.getName()!=null?voPerson.getName().getFamily():""));
                                                Character vcSex = (voPerson.getSex()!=null?(voPerson.getSex().length()>0?voPerson.getSex().charAt(0):'M'):'M');
                                                voRun.setSex(vcSex);
                                            }
                                            //Get Club info
                                            if (voTeamMemberStart.getOrganisation()!=null) {
                                                eu.oreplay.logic.iof.Organisation voOrg = voTeamMemberStart.getOrganisation();
                                                eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                                                voClu.setId("");
                                                voClu.setUuid("");
                                                voClu.setOeKey(voOrg.getId()!=null?voOrg.getId().getValue():"");
                                                voClu.setShortName(voOrg.getShortName());
                                                voClu.setLongName(voOrg.getName());
                                                //Add the club to the runner
                                                voRun.setClub(voClu);
                                                //Add the club to the team if the team doesn't have one yet
                                                if (voTea.getClub()==null)
                                                    voTea.setClub(voClu);
                                            } else {
                                                //Add the club to the runner, copying the same of the team
                                                voRun.setClub(voTea.getClub());
                                            }
                                            //Course, Leg, Start Time, Bib# and SiCard are in another place
                                            if (voTeamMemberStart.getStart()!=null && !voTeamMemberStart.getStart().isEmpty()) {
                                                eu.oreplay.logic.iof.TeamMemberRaceStart voPrs = voTeamMemberStart.getStart().get(0);
                                                if (voPrs!=null) {
                                                    voRun.setBibNumber(voPrs.getBibNumber());
                                                    voRun.setSicard(voPrs.getControlCard()!=null?(!voPrs.getControlCard().isEmpty()?(voPrs.getControlCard().get(0)!=null?voPrs.getControlCard().get(0).getValue():""):""):"");
                                                    //Check whether there is an alternative SiCard
                                                    voRun.setSicardAlt(voPrs.getControlCard()!=null?(voPrs.getControlCard().size()>1?(voPrs.getControlCard().get(1)!=null?voPrs.getControlCard().get(1).getValue():""):""):"");
                                                    //Now, the specific course of the runner
                                                    if (voPrs.getCourse()!=null) {
                                                        eu.oreplay.logic.iof.SimpleCourse voSimple = voPrs.getCourse();
                                                        if (voSimple!=null) {
                                                            eu.oreplay.db.Course voCou = new eu.oreplay.db.Course();
                                                            voCou.setId("");
                                                            voCou.setUuid("");
                                                            voCou.setOeKey((voSimple.getId()!=null?voSimple.getId().getValue():""));
                                                            voCou.setShortName(voSimple.getName());
                                                            voCou.setClimb((voSimple.getClimb()!=null?voSimple.getClimb()+"":""));
                                                            voCou.setDistance((voSimple.getLength()!=null?voSimple.getLength()+"":""));
                                                            voCou.setControls((voSimple.getNumberOfControls()!=null?voSimple.getNumberOfControls().intValue():0));
                                                            //Add the course to the runner
                                                            voRun.setCourse(voCou);
                                                            //If the course of the class is null, add this course to the class too
                                                            if (voCla.getCourse()==null)
                                                                voCla.setCourse(voCou);
                                                        }
                                                    }
                                                    //Start Time is set in a first element of RunnerResult List
                                                    ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<>();
                                                    eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                                                    voRes.setId("");
                                                    voRes.setStageOrder(voSta.getOrderNumber());
                                                    if (voPrs.getLeg()!=null)
                                                        voRes.setLegNumber(voPrs.getLeg().intValue());
                                                    //The type of result is null as it is processing only start times
                                                    voRes.setResultType(null);
                                                    //Transform the date value
                                                    //voRes.setStartTime((voPrs.getStartTime()!=null?voPrs.getStartTime().toGregorianCalendar().getTime():null));
                                                    voRes.setStartTime(Utils.convertGregorianDateFromXmlOrForced(voPrs.getStartTime(), isbForce(), getcStageDate(), getcDateFormat()));
                                                    //There is only Status info at Result tag
                                                    voRes.setStatusCode(Utils.STATUS_OK_ID);
                                                    //Add the result to the list
                                                    vlRes.add(voRes);
                                                    //Add the list to the runner data
                                                    voRun.setRunnerResultList(vlRes);
                                                    //If it's the first runner of the team, set a TeamResult with the same values of the RunnerResult
                                                    if (vlRun.isEmpty()) {
                                                        ArrayList<eu.oreplay.db.TeamResult> vlTes = new ArrayList<>();
                                                        eu.oreplay.db.TeamResult voTes = new eu.oreplay.db.TeamResult();
                                                        voTes.setId(voRes.getId());
                                                        voTes.setStageOrder(voRes.getStageOrder());
                                                        voTes.setResultType(voRes.getResultType());
                                                        voTes.setStartTime(voRes.getStartTime());
                                                        //There is only Status info at Result tag
                                                        voTes.setStatusCode(voRes.getStatusCode());
                                                        //Add the result to the list
                                                        vlTes.add(voTes);
                                                        //Add the list to the team data
                                                        voTea.setTeamResultList(vlTes);
                                                    }
                                                }
                                            }
                                            //Add the runner to the list of runners
                                            vlRun.add(voRun);                                        
                                        }
                                        //Set the list of runners to the team
                                        voTea.setRunnerList(vlRun);
                                    }
                                    //Add the team to the list of teams of the class
                                    vlTea.add(voTea);
                                }
                                voCla.setTeamList(vlTea);
                            }
                        }
                        //Add the class to the list of classes
                        vlCla.add(voCla);
                    }
                    //Set the list of classes to the stage
                    voSta.setClazzList(vlCla);
                }
                //Add the stage to the event
                ArrayList<eu.oreplay.db.Stage> vlSta = new ArrayList<>();
                vlSta.add(voSta);
                voEve.setStageList(vlSta);
            }
        }catch(Exception e) {
        }
        return voEve;
    }

    /**
     * Given a representation of IOF XML for start list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the XML file; this method is for 1-stage, Relay event
     * @param poStart StartList Object that represents an IOF XML file for start list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults, teams and teamresults)
     */
    public eu.oreplay.db.Event convertStartListSingleStageRelay (eu.oreplay.logic.iof.StartList poStart) {
        eu.oreplay.db.Event voEve = convertStartListSingleStageClassic (poStart);
        return voEve;
    }
    /**
     * Given a representation of IOF XML for start list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the XML file; this method is for 1-stage, Rogaine event
     * @param poStart StartList Object that represents an IOF XML file for start list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults, teams and teamresults)
     */
    public eu.oreplay.db.Event convertStartListSingleStageRogaine (eu.oreplay.logic.iof.StartList poStart) {
        eu.oreplay.db.Event voEve = convertStartListSingleStageClassic (poStart);
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
            if (getcSource().equals(ConverterToModel.SRC_OPRE)) {
                voEve = convertResultListSingleStageTrailo (voResult);
            } else if (getcSource().equals(ConverterToModel.SRC_OE2010) ||
                    getcSource().equals(ConverterToModel.SRC_OEV12) ||
                    getcSource().equals(ConverterToModel.SRC_GENERICXML) || 
                    (getcSource().equals(ConverterToModel.SRC_MEOS) && !isScoring())) {
                voEve = convertResultListSingleStageClassic (voResult);
            } else if (getcSource().equals(ConverterToModel.SRC_OS2010) ||
                    getcSource().equals(ConverterToModel.SRC_OSV12)) {
                voEve = convertResultListSingleStageRelay (voResult);
            } else if ((getcSource().equals(ConverterToModel.SRC_MEOS) ||
                    getcSource().equals(ConverterToModel.SRC_OESCOREV12) ||
                    getcSource().equals(ConverterToModel.SRC_SITIMING)) &&
                    isScoring()) {
                voEve = convertResultListSingleStageRogaine (voResult);
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
        ArrayList<eu.oreplay.db.Control> vlCon = new ArrayList<>();
        HashMap<String, String> vlStations = new HashMap<>();
        boolean vbRadio = false;
        int vnPenFactor = (getcSource().equals(ConverterToModel.SRC_MEOS)?-1:1); //MeOS uses positive values for penalty. This factor converts to negative
        try {
            //Flag to say whether the results include radiocontrols
            if (getcResultsType().equals(ConverterToModel.RES_RADIO))
                vbRadio = true;
            if (poResult!=null) {
                //Try to set the date and time of the stage from the XML
                try {
                    if (!isbForce()) {
                        oEve.getStageList().get(0).setBaseDate(poResult.getEvent().getStartTime().getDate().toGregorianCalendar().getTime());
                        oEve.getStageList().get(0).setBaseTime(poResult.getEvent().getStartTime().getTime().toGregorianCalendar().getTime());
                    } else {
                        oEve.getStageList().get(0).setBaseDate(Utils.parse(getcStageDate(), getcDateFormat()));
                        oEve.getStageList().get(0).setBaseTime(Utils.convertGregorianDateFromXmlOrForced(poResult.getEvent().getStartTime().getTime(), isbForce(), getcStageDate(), getcDateFormat()));
                    }
                }catch(Exception eDateTime) {
                }
                //Event's data
                voEve = Utils.copyBasicEventData(oEve);
                //Stage's data
                eu.oreplay.db.Stage voSta = Utils.copyBasicOneStageData(oEve);
                //Classes' data
                if (poResult.getClassResult()!=null) {
                    //Process each class in the ResultList
                    ArrayList<eu.oreplay.db.Clazz> vlCla = new ArrayList<>();
                    for (eu.oreplay.logic.iof.ClassResult voClassResult : poResult.getClassResult()) {
                        //List to store the radiocontrols of the class
                        ArrayList<eu.oreplay.db.ClazzControl> vlClaCon = new ArrayList<>();
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
                            if (voClassResult.getCourse()!=null && !voClassResult.getCourse().isEmpty()) {
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
                            //Some classes would only have individual persons. Some other would have also teams and then teammembers
                            //Start processing individuals
                            //---------------------------------------------------------------------------
                            if (voClassResult.getPersonResult()!=null) {
                                //Process each runner of the class
                                ArrayList<eu.oreplay.db.Runner> vlRun = new ArrayList<>();
                                int vnContRun = 0;
                                for (eu.oreplay.logic.iof.PersonResult voPersonResult : voClassResult.getPersonResult()) {
                                    eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                                    voRun.setId("");
                                    voRun.setUuid("");
                                    try {
                                        voRun.setDbId(voPersonResult.getEntryId().getValue());
                                    }catch(Exception eEntryId) {
                                    }
                                    eu.oreplay.logic.iof.Person voPerson = voPersonResult.getPerson();
                                    if (voPerson!=null) {
                                        voRun.setFirstName((voPerson.getName()!=null?voPerson.getName().getGiven():""));
                                        voRun.setLastName((voPerson.getName()!=null?voPerson.getName().getFamily():""));
                                        Character vcSex = (voPerson.getSex()!=null?(voPerson.getSex().length()>0?voPerson.getSex().charAt(0):'M'):'M');
                                        voRun.setSex(vcSex);
                                    }
                                    //Start Time, Bib#, SiCard and results are in another place
                                    if (voPersonResult.getResult()!=null && !voPersonResult.getResult().isEmpty()) {
                                        //Counter to know which of the RaceResults is treated in each loop for the same runner
                                        int vnIndResult = 0;
                                        ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<>();
                                        //Normally, there will be only one RaceResult for each Person. 
                                        //Trail-O, instead, will have several results for each kind of data (totals, normal controls and each timed group)
                                        for (eu.oreplay.logic.iof.PersonRaceResult voPrs : voPersonResult.getResult()) {
                                            if (voPrs!=null) {
                                                //Only write runner info from the first element of the Race Results
                                                if (vnIndResult==0) {
                                                    voRun.setBibNumber(voPrs.getBibNumber());
                                                    //SiTiming treats teams as one person with serveral SiCards. I get only the first one
                                                    voRun.setSicard(voPrs.getControlCard()!=null?(!voPrs.getControlCard().isEmpty()?(voPrs.getControlCard().get(0)!=null?voPrs.getControlCard().get(0).getValue():""):""):"");
                                                    //Check whether there is an alternative SiCard
                                                    voRun.setSicardAlt(voPrs.getControlCard()!=null?(voPrs.getControlCard().size()>1?(voPrs.getControlCard().get(1)!=null?voPrs.getControlCard().get(1).getValue():""):""):"");
                                                }
                                                //Start Time, Finish Time, are set in a first element of RunnerResult List
                                                //In Trail-O, other RunnerResult elements for normal controls and timed groups
                                                eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                                                voRes.setId("");
                                                voRes.setStageOrder(voSta.getOrderNumber());
                                                voRes.setLegNumber(1);
                                                //--------------------------------------------------------------------------------------------------
                                                //Compose the type of result, which is a Stage Result for normal competitions, or others for Trail-O
                                                eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
                                                if (!getcSource().equals(ConverterToModel.SRC_OPRE)) {
                                                    voResType.setId(Utils.RESULT_STAGE_ID);
                                                    voResType.setDescription(Utils.RESULT_STAGE_DESC);
                                                } else {
                                                    //In Trail-O, first Race Result is for totals
                                                    //In PreO and Sprint, the second one is for normal controls; the rest for timed groups
                                                    //In TempO, the second and beyond are for timed groups
                                                    if (vnIndResult==0) {
                                                        //Totals
                                                        voResType.setId(Utils.RESULT_STAGE_ID);
                                                        voResType.setDescription(Utils.RESULT_STAGE_DESC);
                                                    } else if (vnIndResult==1 && !getcTrailoType().equals(ConverterToModel.TRAILO_TYPE_TEMPO)) {
                                                        //Normal controls
                                                        voResType.setId(Utils.RESULT_TRAILO_NORMAL_ID);
                                                        voResType.setDescription(Utils.RESULT_TRAILO_NORMAL_DESC);
                                                    } else {
                                                        //Timed groups
                                                        voResType.setId(Utils.RESULT_TRAILO_TIMED_ID);
                                                        voResType.setDescription(Utils.RESULT_TRAILO_TIMED_DESC);
                                                    }
                                                }
                                                voRes.setResultType(voResType);
                                                //--------------------------------------------------------------------------------------------------
                                                //Transform date values
                                                //voRes.setStartTime((voPrs.getStartTime()!=null?voPrs.getStartTime().toGregorianCalendar().getTime():null));
                                                //voRes.setFinishTime((voPrs.getFinishTime()!=null?voPrs.getFinishTime().toGregorianCalendar().getTime():null));
                                                voRes.setStartTime(Utils.convertGregorianDateFromXmlOrForced(voPrs.getStartTime(), isbForce(), getcStageDate(), getcDateFormat()));
                                                voRes.setFinishTime(Utils.convertGregorianDateFromXmlOrForced(voPrs.getFinishTime(), isbForce(), getcStageDate(), getcDateFormat()));
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
                                                voRes.setTimeAdjusted(BigDecimal.ZERO);
                                                voRes.setTimeBonus(BigDecimal.ZERO);
                                                voRes.setTimeNeutralization(BigDecimal.ZERO);
                                                voRes.setTimePenalty(BigDecimal.ZERO);
                                                //Process the Score tags
                                                if (voPrs.getScore()!=null) {
                                                    for (eu.oreplay.logic.iof.Score voSco : voPrs.getScore()) {
                                                        if (voSco!=null) {
                                                            try {
                                                                int vnPoints = (int)voSco.getValue();
                                                                switch (voSco.getType()) {
                                                                    case "Score":
                                                                        voRes.setPointsFinal(vnPoints);
                                                                        break;
                                                                    case "FinalScore":
                                                                        voRes.setPointsFinal(vnPoints);
                                                                        break;
                                                                    case "Penalty":
                                                                        voRes.setPointsPenalty(vnPoints*vnPenFactor);
                                                                        break;
                                                                    case "PenaltyScore":
                                                                        voRes.setPointsPenalty(vnPoints*vnPenFactor);
                                                                        break;
                                                                    case "ScorePenalty":
                                                                        voRes.setPointsPenalty(vnPoints*vnPenFactor);
                                                                        break;
                                                                    case "ManualScoreAdjust":
                                                                        voRes.setPointsAdjusted(vnPoints);
                                                                        break;
                                                                    case "XtraPoints":
                                                                        voRes.setPointsBonus(vnPoints);
                                                                        break;
                                                                    case "ScoreBonus":
                                                                        voRes.setPointsBonus(vnPoints);
                                                                        break;
                                                                    case "Time":
                                                                        voRes.setTimeSeconds(new java.math.BigDecimal(voSco.getValue()));
                                                                        break;
                                                                    case "TimePenalty":
                                                                        voRes.setTimePenalty(new java.math.BigDecimal(voSco.getValue()));
                                                                        break;
                                                                    default:
                                                                        break;
                                                                }
                                                            }catch(Exception eConvScore) {
                                                            }
                                                        }
                                                    }
                                                }
                                                //Now, process Splits, if present
                                                if (voPrs.getSplitTime()!=null && !voPrs.getSplitTime().isEmpty()) {
                                                    int vnSplOrder = 1;
                                                    ArrayList<eu.oreplay.db.Split> vlSpl = new ArrayList<>();
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
                                                //Now, process Answers, if present (Trail-O)
                                                if (voPrs.getControlAnswer()!=null && !voPrs.getControlAnswer().isEmpty()) {
                                                    int vnAnsOrder = 1;
                                                    ArrayList<eu.oreplay.db.Answer> vlAns = new ArrayList<>();
                                                    for (eu.oreplay.logic.iof.ControlAnswer voControlAnswer : voPrs.getControlAnswer()) {
                                                        eu.oreplay.db.Answer voAns = new eu.oreplay.db.Answer();
                                                        voAns.setGiven(voControlAnswer.getAnswer());
                                                        voAns.setCorrect(voControlAnswer.getCorrectAnswer());
                                                        voAns.setOrderNumber(vnAnsOrder);                                                              
                                                        //Add the object to the list
                                                        vlAns.add(voAns);
                                                        //Increase the counter of the answer order
                                                        vnAnsOrder++;
                                                    }
                                                    //Add the list of answers to the results
                                                    voRes.setAnswerList(vlAns);
                                                }
                                                //Add the result to the list
                                                vlRes.add(voRes);
                                                //Increase the counter of RaceResult for the same runner
                                                vnIndResult++;
                                            }
                                        }
                                        //Add the list to the runner data
                                        voRun.setRunnerResultList(vlRes);                                            
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
                                        try {
                                            voClu.setLogo(voOrg.getLogotype().get(0).getValue());
                                        }catch(Exception eLogo) {
                                        }
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
                            //---------------------------------------------------------------------------
                            //Now, process the teams if the class has teams instead of individuals
                            //HashMap to store Teams. For each team, its runners. In some XML, the runners of a team come in one TeamResult tag. 
                            //But, in some other, each runner comes in a separate TeamResult tag (even when it's for the same team)
                            HashMap<String, eu.oreplay.db.Team> vlTea = new HashMap<>();
                            //Process each team
                            if (voClassResult.getTeamResult()!=null) {
                                //Counter of runners for the class, in order to add controls only when it is the first runner
                                int vnContRun = 0;
                                for (eu.oreplay.logic.iof.TeamResult voTeamResult : voClassResult.getTeamResult()) {
                                    //Prepares the structure for the overall results of the team
                                    List<eu.oreplay.db.TeamResult> vlTes = new ArrayList<>();
                                    //First, tries to get a good identifier for the team
                                    String vcTeaId = "";
                                    String vcTeaBib = (voTeamResult.getBibNumber()!=null?voTeamResult.getBibNumber():"");
                                    String vcTeaName = (voTeamResult.getName()!=null?voTeamResult.getName():"");
                                    String vcTeaEntry = (voTeamResult.getEntryId()!=null?voTeamResult.getEntryId().getValue():"");
                                    if (!vcTeaBib.equals(""))
                                        vcTeaId = vcTeaBib;
                                    else if (!vcTeaEntry.equals(""))
                                        vcTeaId = vcTeaEntry;
                                    else if (!vcTeaName.equals(""))
                                        vcTeaId = vcTeaName;
                                    //Search for the team in the HashMap
                                    eu.oreplay.db.Team voTea = new eu.oreplay.db.Team();
                                    if (vlTea.containsKey(vcTeaId)) {
                                        voTea = vlTea.get(vcTeaId);
                                    } else {
                                        voTea.setId("");
                                        voTea.setUuid("");
                                        voTea.setBibNumber(vcTeaBib);
                                        voTea.setTeamName(vcTeaName);
                                    }                                
                                    //Get Club info
                                    if (voTeamResult.getOrganisation()!=null && !voTeamResult.getOrganisation().isEmpty()) {
                                        eu.oreplay.logic.iof.Organisation voOrg = voTeamResult.getOrganisation().get(0);
                                        eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                                        voClu.setId("");
                                        voClu.setUuid("");
                                        voClu.setOeKey(voOrg.getId()!=null?voOrg.getId().getValue():"");
                                        voClu.setShortName(voOrg.getShortName());
                                        voClu.setLongName(voOrg.getName());
                                        //Add the club to the team
                                        voTea.setClub(voClu);
                                    }
                                    //Process the team's runners
                                    if (voTeamResult.getTeamMemberResult()!=null) {
                                        //Process each runner of the team
                                        List<eu.oreplay.db.Runner> vlRun = null;
                                        vlRun = voTea.getRunnerList();
                                        if (vlRun==null)
                                            vlRun = new ArrayList<>();
                                        //Loop for each member
                                        for (eu.oreplay.logic.iof.TeamMemberResult voTeamMemberResult : voTeamResult.getTeamMemberResult()) {
                                            eu.oreplay.db.Runner voRun = new eu.oreplay.db.Runner();
                                            voRun.setId("");
                                            voRun.setUuid("");
                                            eu.oreplay.logic.iof.Person voPerson = voTeamMemberResult.getPerson();
                                            if (voPerson!=null) {
                                                voRun.setFirstName((voPerson.getName()!=null?voPerson.getName().getGiven():""));
                                                voRun.setLastName((voPerson.getName()!=null?voPerson.getName().getFamily():""));
                                                Character vcSex = (voPerson.getSex()!=null?(voPerson.getSex().length()>0?voPerson.getSex().charAt(0):'M'):'M');
                                                voRun.setSex(vcSex);
                                            }
                                            //Get Club info
                                            if (voTeamMemberResult.getOrganisation()!=null) {
                                                eu.oreplay.logic.iof.Organisation voOrg = voTeamMemberResult.getOrganisation();
                                                eu.oreplay.db.Club voClu = new eu.oreplay.db.Club();
                                                voClu.setId("");
                                                voClu.setUuid("");
                                                voClu.setOeKey(voOrg.getId()!=null?voOrg.getId().getValue():"");
                                                voClu.setShortName(voOrg.getShortName());
                                                voClu.setLongName(voOrg.getName());
                                                //Add the club to the runner
                                                voRun.setClub(voClu);
                                                //Add the club to the team if the team doesn't have one yet
                                                if (voTea.getClub()==null)
                                                    voTea.setClub(voClu);
                                            } else {
                                                //Add the club to the runner, copying the same of the team
                                                voRun.setClub(voTea.getClub());
                                            }
                                            //Course, Leg, Start Time, Bib# and SiCard are in another place
                                            if (voTeamMemberResult.getResult()!=null && !voTeamMemberResult.getResult().isEmpty()) {
                                                eu.oreplay.logic.iof.TeamMemberRaceResult voPrs = voTeamMemberResult.getResult().get(0);
                                                if (voPrs!=null) {
                                                    voRun.setBibNumber(voPrs.getBibNumber());
                                                    voRun.setSicard(voPrs.getControlCard()!=null?(!voPrs.getControlCard().isEmpty()?(voPrs.getControlCard().get(0)!=null?voPrs.getControlCard().get(0).getValue():""):""):"");
                                                    //Check whether there is an alternative SiCard
                                                    voRun.setSicardAlt(voPrs.getControlCard()!=null?(voPrs.getControlCard().size()>1?(voPrs.getControlCard().get(1)!=null?voPrs.getControlCard().get(1).getValue():""):""):"");
                                                    //Now, the specific course of the runner
                                                    if (voPrs.getCourse()!=null) {
                                                        eu.oreplay.logic.iof.SimpleCourse voSimple = voPrs.getCourse();
                                                        if (voSimple!=null) {
                                                            eu.oreplay.db.Course voCou = new eu.oreplay.db.Course();
                                                            voCou.setId("");
                                                            voCou.setUuid("");
                                                            voCou.setOeKey((voSimple.getId()!=null?voSimple.getId().getValue():""));
                                                            voCou.setShortName(voSimple.getName());
                                                            voCou.setClimb((voSimple.getClimb()!=null?voSimple.getClimb()+"":""));
                                                            voCou.setDistance((voSimple.getLength()!=null?voSimple.getLength()+"":""));
                                                            voCou.setControls((voSimple.getNumberOfControls()!=null?voSimple.getNumberOfControls().intValue():0));
                                                            //Add the course to the runner
                                                            voRun.setCourse(voCou);
                                                            //If the course of the class is null, add this course to the class too
                                                            if (voCla.getCourse()==null)
                                                                voCla.setCourse(voCou);
                                                        }
                                                    }
                                                    //Start Time is set in a first element of RunnerResult List
                                                    ArrayList<eu.oreplay.db.RunnerResult> vlRes = new ArrayList<>();
                                                    eu.oreplay.db.RunnerResult voRes = new eu.oreplay.db.RunnerResult();
                                                    voRes.setId("");
                                                    voRes.setStageOrder(voSta.getOrderNumber());
                                                    //Leg number of the runner by default. It's used for runnerresult and also for teamresult
                                                    int vnLegNumber = 1;
                                                    if (voPrs.getLeg()!=null) {
                                                        vnLegNumber = voPrs.getLeg().intValue();
                                                        voRes.setLegNumber(vnLegNumber);
                                                    }
                                                    //Compose the type of result, which is a Stage Result
                                                    eu.oreplay.db.ResultType voResType = new eu.oreplay.db.ResultType();
                                                    voResType.setId(Utils.RESULT_STAGE_ID);
                                                    voResType.setDescription(Utils.RESULT_STAGE_DESC);
                                                    voRes.setResultType(voResType);
                                                    //Transform date values
                                                    //voRes.setStartTime((voPrs.getStartTime()!=null?voPrs.getStartTime().toGregorianCalendar().getTime():null));
                                                    //voRes.setFinishTime((voPrs.getFinishTime()!=null?voPrs.getFinishTime().toGregorianCalendar().getTime():null));
                                                    voRes.setStartTime(Utils.convertGregorianDateFromXmlOrForced(voPrs.getStartTime(), isbForce(), getcStageDate(), getcDateFormat()));
                                                    voRes.setFinishTime(Utils.convertGregorianDateFromXmlOrForced(voPrs.getFinishTime(), isbForce(), getcStageDate(), getcDateFormat()));
                                                    //Get the status, from IOF enumeration to OReplay Ids
                                                    voRes.setStatusCode(Utils.convertIofStatusValue(voPrs.getStatus().value()));
                                                    //Get times and position
                                                    if (voPrs.getTime()!=null)
                                                        voRes.setTimeSeconds(new java.math.BigDecimal(voPrs.getTime()));
                                                    if (voPrs.getTimeBehind()!=null && !voPrs.getTimeBehind().isEmpty()) {
                                                        voRes.setTimeBehind(new java.math.BigDecimal(voPrs.getTimeBehind().get(0).getValue()));
                                                    }
                                                    if (voPrs.getPosition()!=null && !voPrs.getPosition().isEmpty() &&
                                                            voPrs.getPosition().get(0).getValue()!=null) {
                                                        voRes.setPosition(voPrs.getPosition().get(0).getValue().intValue());
                                                    }
                                                    //Set remainder fields for points and times
                                                    //Process the Score tags
                                                    if (voPrs.getScore()!=null) {
                                                        for (eu.oreplay.logic.iof.Score voSco : voPrs.getScore()) {
                                                            if (voSco!=null) {
                                                                try {
                                                                    int vnPoints = (int)voSco.getValue();
                                                                    switch (voSco.getType()) {
                                                                        case "Score":
                                                                            voRes.setPointsFinal(vnPoints);
                                                                            break;
                                                                        case "FinalScore":
                                                                            voRes.setPointsFinal(vnPoints);
                                                                            break;
                                                                        case "Penalty":
                                                                            voRes.setPointsPenalty(vnPoints*vnPenFactor);
                                                                            break;
                                                                        case "PenaltyScore":
                                                                            voRes.setPointsPenalty(vnPoints*vnPenFactor);
                                                                            break;
                                                                        case "ManualScoreAdjust":
                                                                            voRes.setPointsAdjusted(vnPoints);
                                                                            break;
                                                                        case "XtraPoints":
                                                                            voRes.setPointsBonus(vnPoints);
                                                                            break;
                                                                        default:
                                                                            break;
                                                                    }
                                                                }catch(Exception eConvScore) {
                                                                }
                                                            }
                                                        }
                                                    }
                                                    voRes.setTimeAdjusted(BigDecimal.ZERO);
                                                    voRes.setTimeBonus(BigDecimal.ZERO);
                                                    voRes.setTimeNeutralization(BigDecimal.ZERO);
                                                    voRes.setTimePenalty(BigDecimal.ZERO);
                                                    //Now, process Splits, if present
                                                    if (voPrs.getSplitTime()!=null && !voPrs.getSplitTime().isEmpty()) {
                                                        int vnSplOrder = 1;
                                                        ArrayList<eu.oreplay.db.Split> vlSpl = new ArrayList<>();
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
                                                    //Create or update the team's result from the OverallResult tag
                                                    //The next sentence is moved up to create one array for several TeamResult elements (the accumulated overall of each leg)
                                                    //List<eu.oreplay.db.TeamResult> vlTes = new ArrayList<>();
                                                    eu.oreplay.db.TeamResult voTes = new eu.oreplay.db.TeamResult();
                                                    if (voPrs.getOverallResult()!=null) {
                                                        OverallResult voOve = voPrs.getOverallResult();
                                                        voTes.setId(voRes.getId());
                                                        voTes.setStageOrder(voRes.getStageOrder());
                                                        voTes.setResultType(voRes.getResultType());
                                                        voTes.setLegNumber(vnLegNumber);
                                                        //Get the status, from IOF enumeration to OReplay Ids
                                                        voTes.setStatusCode(Utils.convertIofStatusValue(voOve.getStatus().value()));
                                                        //Get times and position
                                                        if (voOve.getTime()!=null)
                                                            voTes.setTimeSeconds(new java.math.BigDecimal(voOve.getTime()));
                                                        if (voOve.getTimeBehind()!=null) {
                                                            voTes.setTimeBehind(new java.math.BigDecimal(voOve.getTimeBehind()));
                                                        }
                                                        if (voOve.getPosition()!=null) {
                                                            voTes.setPosition(voOve.getPosition().intValue());
                                                        }                                                        
                                                        //Set remainder fields for points and times
                                                        //Process the Score tags
                                                        if (voOve.getScore()!=null) {
                                                            for (eu.oreplay.logic.iof.Score voSco : voOve.getScore()) {
                                                                if (voSco!=null) {
                                                                    try {
                                                                        int vnPoints = (int)voSco.getValue();
                                                                        switch (voSco.getType()) {
                                                                            case "Score":
                                                                                voTes.setPointsFinal(vnPoints);
                                                                                break;
                                                                            case "FinalScore":
                                                                                voTes.setPointsFinal(vnPoints);
                                                                                break;
                                                                            case "Penalty":
                                                                                voTes.setPointsPenalty(vnPoints*vnPenFactor);
                                                                                break;
                                                                            case "PenaltyScore":
                                                                                voTes.setPointsPenalty(vnPoints*vnPenFactor);
                                                                                break;
                                                                            case "ManualScoreAdjust":
                                                                                voTes.setPointsAdjusted(vnPoints);
                                                                                break;
                                                                            case "XtraPoints":
                                                                                voTes.setPointsBonus(vnPoints);
                                                                                break;
                                                                            default:
                                                                                break;
                                                                        }
                                                                    }catch(Exception eConvScore) {
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        voTes.setTimeAdjusted(BigDecimal.ZERO);
                                                        voTes.setTimeBonus(BigDecimal.ZERO);
                                                        voTes.setTimeNeutralization(BigDecimal.ZERO);
                                                        voTes.setTimePenalty(BigDecimal.ZERO);
                                                        //Some XML files have the results of the runner inside the Overall tag
                                                        //So, let's write the runner values from this Overall
                                                        //I'm going to comment this because the overall is for the team, sometimes accumulating values from leg to leg
                                                        /*
                                                        voRes.setStatusCode(voTes.getStatusCode());
                                                        voRes.setTimeSeconds(voTes.getTimeSeconds());
                                                        voRes.setTimeBehind(voTes.getTimeBehind());
                                                        voRes.setPosition(voTes.getPosition());
                                                        voRes.setPointsFinal(voTes.getPointsFinal());
                                                        voRes.setPointsPenalty(voTes.getPointsPenalty());
                                                        voRes.setPointsAdjusted(voTes.getPointsAdjusted());
                                                        voRes.setPointsBonus(voTes.getPointsBonus());
                                                        voRes.setTimeAdjusted(BigDecimal.ZERO);
                                                        voRes.setTimeBonus(BigDecimal.ZERO);
                                                        voRes.setTimeNeutralization(BigDecimal.ZERO);
                                                        voRes.setTimePenalty(BigDecimal.ZERO);                                                        
                                                        */
                                                    } else {
                                                        //If there is no Overall tag, put the value of the team as of the runner
                                                        voTes.setId(voRes.getId());
                                                        voTes.setStageOrder(voRes.getStageOrder());
                                                        voTes.setResultType(voRes.getResultType());
                                                        voTes.setStatusCode(voRes.getStatusCode());
                                                        voTes.setTimeSeconds(voRes.getTimeSeconds());
                                                        voTes.setTimeBehind(voRes.getTimeBehind());
                                                        voTes.setPosition(voRes.getPosition());
                                                        voTes.setPointsFinal(voRes.getPointsFinal());
                                                        voTes.setPointsPenalty(voRes.getPointsPenalty());
                                                        voTes.setPointsAdjusted(voRes.getPointsAdjusted());
                                                        voTes.setPointsBonus(voRes.getPointsBonus());
                                                        voTes.setTimeAdjusted(BigDecimal.ZERO);
                                                        voTes.setTimeBonus(BigDecimal.ZERO);
                                                        voTes.setTimeNeutralization(BigDecimal.ZERO);
                                                        voTes.setTimePenalty(BigDecimal.ZERO);
                                                    }
                                                    //Add the result to the list
                                                    vlRes.add(voRes);
                                                    //Add the list to the runner data
                                                    voRun.setRunnerResultList(vlRes);
                                                    //Add the result to the list
                                                    vlTes.add(voTes);
                                                    //Add the list to the team data
                                                    voTea.setTeamResultList(vlTes);
                                                }
                                            }
                                            //Add the runner to the list of runners
                                            vlRun.add(voRun);                                        
                                            //Increase the counter of runners
                                            vnContRun++;
                                        }
                                        //Set the list of runners to the team
                                        voTea.setRunnerList(vlRun);
                                    }
                                    //Remove the previous contents of the team from the HashMap and insert it again
                                    vlTea.remove(vcTeaId);
                                    vlTea.put(vcTeaId, voTea);
                                }
                                //Add the list of teams to the class
                                List<eu.oreplay.db.Team> vlTeaNew = new ArrayList<>(vlTea.values());
                                voCla.setTeamList(vlTeaNew);
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
                ArrayList<eu.oreplay.db.Stage> vlSta = new ArrayList<>();
                vlSta.add(voSta);
                voEve.setStageList(vlSta);
            }
        }catch(Exception e) {
            //e.printStackTrace();
        }
        return voEve;
    }
    /**
     * Given a representation of IOF XML for result list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the XML file; this method is for a relay event
     * @param poResult ResultList Object that represents an IOF XML file for result list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, teams, teamresults, runners and runnerresults)
     */
    public eu.oreplay.db.Event convertResultListSingleStageRelay (eu.oreplay.logic.iof.ResultList poResult) {
        //At this moment, calculations for Relays are the same as for classic events
        //The difference is that here there will be no individual persons' results, only teams
        eu.oreplay.db.Event voEve = this.convertResultListSingleStageClassic(poResult);
        return voEve;
    }
    /**
     * Given a representation of IOF XML for result list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the XML file; this method is for 1-stage, Rogaine event
     * @param poResult ResultList Object that represents an IOF XML file for result list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults, teams and teamresults)
     */
    public eu.oreplay.db.Event convertResultListSingleStageRogaine (eu.oreplay.logic.iof.ResultList poResult) {
        //At this moment, calculations for Rogaine are the same as for classic events
        eu.oreplay.db.Event voEve = this.convertResultListSingleStageClassic(poResult);
        return voEve;
    }
    /**
     * Given a representation of IOF XML for result list, this method creates a 
     * structure following the OReplay data model and feeds with the data that
     * came in the XML file; this method is for 1-stage, Trail-O event
     * @param poResult ResultList Object that represents an IOF XML file for result list
     * @return eu.oreplay.db.Event Event and all the related subclasses in there (stage, classes, runners and runnerresults, teams and teamresults)
     */
    public eu.oreplay.db.Event convertResultListSingleStageTrailo (eu.oreplay.logic.iof.ResultList poResult) {
        //At this moment, calculations for Trail-O are the same as for classic events
        eu.oreplay.db.Event voEve = this.convertResultListSingleStageClassic(poResult);
        return voEve;
    }

}