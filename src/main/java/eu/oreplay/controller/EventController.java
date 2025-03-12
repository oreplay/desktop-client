/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.oreplay.controller;

import javax.persistence.EntityManager;
import eu.oreplay.db.Event;
/**
 *
 * @author javier.arufe
 */
public class EventController {
    private javax.persistence.EntityManager oEM;
    private java.util.List<Event> oList;
    private javax.persistence.Query oQuery;
    private java.util.Map<String, String> oPersMap = null;

    public EventController() {
        this.setoEM(null);
    }

    public EventController(EntityManager oEM) {
        this.setoEM(oEM);
    }
    public EventController(java.util.Map<String, String> poPersMap) {
        oPersMap = poPersMap;
        this.setoEM(oEM);
    }
    public EventController(EntityManager oEM, java.util.Map<String, String> poPersMap) {
        oPersMap = poPersMap;
        this.setoEM(oEM);
    }
    public EntityManager getoEM() {
        return oEM;
    }
    public void setoEM(EntityManager oEM) {
        if (oEM!=null)
            this.oEM = oEM;
        else {
            if (oPersMap==null)
                this.oEM = javax.persistence.Persistence.createEntityManagerFactory("OReplayPU").createEntityManager();
            else
                this.oEM = javax.persistence.Persistence.createEntityManagerFactory("OReplayPU", oPersMap).createEntityManager();
        }
    }

    public java.util.List<Event> getEvents () {
        this.setoEM(oEM);
        oQuery = oEM.createNamedQuery("Event.findAll");
        oList = oQuery.getResultList();
        return oList;
    }
}
