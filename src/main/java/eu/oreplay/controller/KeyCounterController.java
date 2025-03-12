/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.oreplay.controller;

import java.util.List;
import eu.oreplay.db.*;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
/**
 *
 * @author javier.arufe
 */
public class KeyCounterController {
    private javax.persistence.EntityManager oEM;
    private java.util.Map<String, String> oPersMap = null;
    private String cManager = "OReplayPU";
    private static final String PARAM_1 = "idEntity";
    private static final String PARAM_2 = "idKey";
    private List<KeyCounter> oList;
    private javax.persistence.Query oQuery;

    public KeyCounterController() {
    }
    public KeyCounterController(String pcManager) {
        cManager = pcManager;
    }
    public KeyCounterController(EntityManager oEM) {
        this.setoEM(oEM);
    }
    public KeyCounterController(String pcManager, java.util.Map<String, String> poPersMap) {
        oPersMap = poPersMap;
        cManager = pcManager;
        this.setoEM(oEM);
    }

    public KeyCounterController(java.util.Map<String, String> poPersMap) {
        oPersMap = poPersMap;
        this.setoEM(oEM);
    }
    public KeyCounterController(EntityManager oEM, java.util.Map<String, String> poPersMap) {
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
                this.oEM = javax.persistence.Persistence.createEntityManagerFactory(cManager).createEntityManager();
            else
                this.oEM = javax.persistence.Persistence.createEntityManagerFactory(cManager, oPersMap).createEntityManager();
        }
    }
    public String getcManager() {
        return cManager;
    }
    public void setcManager(String cManager) {
        this.cManager = cManager;
        this.setoEM(oEM);
    }

    /**
     * Given a table name and the key value you want to break,
     * it returns the key counter record in order to check the current value
     * @param pcTable String Table name
     * @param pcKey String Value of the key to perform the search
     * @return KeyCounter Object with the data of the counter, or null
     */
    public KeyCounter findKeyCounterByKey (String pcTable, String pcKey) {
        KeyCounter voResul = null;
        this.setoEM(oEM);
        oQuery = oEM.createNamedQuery("KeyCounter.ByKey");
        oQuery.setParameter(PARAM_1,pcTable.toUpperCase());
        oQuery.setParameter(PARAM_2, pcKey);
        oList = oQuery.getResultList();
        if (oList!=null && !oList.isEmpty()) {
            voResul = oList.get(0);
        }
        return voResul;
    }

    /**
     * Given a table name and the key value, it assigns a new value to the
     * counter by increasing 1 to the current value
     * @param pcTable String Table name
     * @param pcKey String Value of the key to perform the search
     * @return int If Ok, value of the counter that can be assigned to the target record
     */
    public int assignKeyCounter(String pcTable, String pcKey) {
        return assignKeyCounter (pcTable, pcKey, 1);
    }
    
    /**
     * Given a table name, a key value and a number of desired records,
     * it assigns a new value to the counter by increasing in that desired number of records;
     * For example, it the stored value is 5 and 3 new records are being requested,
     * the returned value is 6 and the new value of the counter is 8, so you can use 6, 7 and 8 as values for the new records
     * @param pcTable String Table name
     * @param pcKey String Value of the key to perform the search
     * @param pnNumKeys int Number of values requested
     * @return int If Ok, first value of the counter that can be assigned to the target records
     */
    public int assignKeyCounter(String pcTable, String pcKey, int pnNumKeys) {
        int vnResul = 1;

        this.setoEM(oEM);
        oEM.getTransaction().begin();
        try {
            oQuery = oEM.createNamedQuery("KeyCounter.ByKey");
            oQuery.setParameter(PARAM_1,pcTable.toUpperCase());
            oQuery.setParameter(PARAM_2, pcKey);
            oQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            oList = oQuery.getResultList();
            KeyCounter voKeyCounter = null;
            if (oList!=null && !oList.isEmpty()) {
                //Take the first record as it should be only 1
                voKeyCounter = oList.get(0);
            }
            if (voKeyCounter==null) {
                voKeyCounter = new KeyCounter();
                KeyCounterPK voClaveCont = new KeyCounterPK(pcTable.toUpperCase(), pcKey);
                voKeyCounter.setKeyCounterPK(voClaveCont);
                voKeyCounter.setCounter(pnNumKeys);
                voKeyCounter.setBoundaryValue(1999999999);
                oEM.persist(voKeyCounter);
            } else {
                vnResul = voKeyCounter.getCounter()+ 1;
                voKeyCounter.setCounter(voKeyCounter.getCounter()+pnNumKeys);
                oEM.merge(voKeyCounter);
            }        
            oEM.getTransaction().commit();
        } catch (Exception e) {
            vnResul = -1;
        } finally {
            if (oEM.getTransaction().isActive())
                oEM.getTransaction().rollback();
        }
        //If you assign multiple keys at once, it returns the lowest index so that the caller starts assigning to the records from there
        return vnResul;
    }
    
    /**
     * Given a table name and a key value, it assigns a new value to the counter, setting it in 1;
     * This is only to reassign records from scratch, if necessary.
     * @param pcTable String Table name
     * @param pcKey String Value of the key to perform the search
     * @return int If all is Ok, returns 1
     */
    public int assignKeyCounterNew(String pcTable, String pcKey) {
        return assignKeyCounterNew (pcTable, pcKey, 1);
    }
    
    /**
     * Given a table name, a key value and a number of desired records,
     * it assigns a new value to the counter, setting it to that number;
     * This is only to reassign records from scratch, if necessary.
     * For example, if 3 new records are requested,
     * the returned value is 1 and the new value of the counter is 3, so you can use 1, 2 and 3 for the new target records
     * @param pcTable String Table name
     * @param pcKey String Value of the key to perform the search
     * @param pnNumKeys int Number of values requested
     * @return int If Ok, first value of the counter that can be assigned to the target records
     */
    public int assignKeyCounterNew(String pcTable, String pcKey, int pnNumKeys) {
        int vnResul = 1;

        this.setoEM(oEM);
        oEM.getTransaction().begin();
        try {
            oQuery = oEM.createNamedQuery("KeyCounter.PorClave");
            oQuery.setParameter(PARAM_1,pcTable.toUpperCase());
            oQuery.setParameter(PARAM_2, pcKey);
            oQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            oList = oQuery.getResultList();
            KeyCounter voKeyCounter = null;
            if (oList!=null && !oList.isEmpty()) {
                //Take the first record as it should be only 1
                voKeyCounter = oList.get(0);
            }
            if (voKeyCounter==null) {
                voKeyCounter = new KeyCounter();
                KeyCounterPK voClaveCont = new KeyCounterPK(pcTable.toUpperCase(), pcKey);
                voKeyCounter.setKeyCounterPK(voClaveCont);
                voKeyCounter.setCounter(pnNumKeys);
                voKeyCounter.setBoundaryValue(1999999999);
                oEM.persist(voKeyCounter);
            } else {
                voKeyCounter.setCounter(pnNumKeys);
                oEM.merge(voKeyCounter);
            }        
            oEM.getTransaction().commit();
        } catch (Exception e) {
            vnResul = -1;
        } finally {
            if (oEM.getTransaction().isActive())
                oEM.getTransaction().rollback();
        }        
        //If you assign multiple keys at once, it returns the lowest index so that the caller starts assigning to the records from there
        return vnResul;
    }
    
}
