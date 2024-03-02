/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author javier.arufe
 */
@Embeddable
public class KeyCounterPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_entity")
    private String idEntity;
    @Basic(optional = false)
    @Column(name = "id_key")
    private String idKey;

    public KeyCounterPK() {
    }

    public KeyCounterPK(String idEntity, String idKey) {
        this.idEntity = idEntity;
        this.idKey = idKey;
    }

    public String getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(String idEntity) {
        this.idEntity = idEntity;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntity != null ? idEntity.hashCode() : 0);
        hash += (idKey != null ? idKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeyCounterPK)) {
            return false;
        }
        KeyCounterPK other = (KeyCounterPK) object;
        if ((this.idEntity == null && other.idEntity != null) || (this.idEntity != null && !this.idEntity.equals(other.idEntity))) {
            return false;
        }
        if ((this.idKey == null && other.idKey != null) || (this.idKey != null && !this.idKey.equals(other.idKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.KeyCounterPK[ idEntity=" + idEntity + ", idKey=" + idKey + " ]";
    }
    
}
