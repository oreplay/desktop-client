/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javier.arufe
 */
@Entity
@Table(name = "key_counters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeyCounter.findAll", query = "SELECT k FROM KeyCounter k"),
    @NamedQuery(name = "KeyCounter.findByIdEntity", query = "SELECT k FROM KeyCounter k WHERE k.keyCounterPK.idEntity = :idEntity"),
    @NamedQuery(name = "KeyCounter.findByIdKey", query = "SELECT k FROM KeyCounter k WHERE k.keyCounterPK.idKey = :idKey"),
    @NamedQuery(name = "KeyCounter.findByCounter", query = "SELECT k FROM KeyCounter k WHERE k.counter = :counter"),
    @NamedQuery(name = "KeyCounter.findByBoundaryValue", query = "SELECT k FROM KeyCounter k WHERE k.boundaryValue = :boundaryValue"),
    @NamedQuery(name = "KeyCounter.ByKey", query = "SELECT k FROM KeyCounter k WHERE k.keyCounterPK.idEntity = :idEntity AND k.keyCounterPK.idKey = :idKey"),
})
public class KeyCounter implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KeyCounterPK keyCounterPK;
    @Column(name = "counter")
    private Integer counter;
    @Column(name = "boundary_value")
    private Integer boundaryValue;

    public KeyCounter() {
    }

    public KeyCounter(KeyCounterPK keyCounterPK) {
        this.keyCounterPK = keyCounterPK;
    }

    public KeyCounter(String idEntity, String idKey) {
        this.keyCounterPK = new KeyCounterPK(idEntity, idKey);
    }

    public KeyCounterPK getKeyCounterPK() {
        return keyCounterPK;
    }

    public void setKeyCounterPK(KeyCounterPK keyCounterPK) {
        this.keyCounterPK = keyCounterPK;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Integer getBoundaryValue() {
        return boundaryValue;
    }

    public void setBoundaryValue(Integer boundaryValue) {
        this.boundaryValue = boundaryValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (keyCounterPK != null ? keyCounterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeyCounter)) {
            return false;
        }
        KeyCounter other = (KeyCounter) object;
        if ((this.keyCounterPK == null && other.keyCounterPK != null) || (this.keyCounterPK != null && !this.keyCounterPK.equals(other.keyCounterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.KeyCounter[ keyCounterPK=" + keyCounterPK + " ]";
    }
    
}
