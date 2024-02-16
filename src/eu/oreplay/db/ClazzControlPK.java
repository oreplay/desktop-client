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
public class ClazzControlPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "class_id")
    private long classId;
    @Basic(optional = false)
    @Column(name = "control_id")
    private long controlId;
    @Basic(optional = false)
    @Column(name = "id_leg")
    private int idLeg;
    @Basic(optional = false)
    @Column(name = "id_revisit")
    private int idRevisit;

    public ClazzControlPK() {
    }

    public ClazzControlPK(long classId, long controlId, int idLeg, int idRevisit) {
        this.classId = classId;
        this.controlId = controlId;
        this.idLeg = idLeg;
        this.idRevisit = idRevisit;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getControlId() {
        return controlId;
    }

    public void setControlId(long controlId) {
        this.controlId = controlId;
    }

    public int getIdLeg() {
        return idLeg;
    }

    public void setIdLeg(int idLeg) {
        this.idLeg = idLeg;
    }

    public int getIdRevisit() {
        return idRevisit;
    }

    public void setIdRevisit(int idRevisit) {
        this.idRevisit = idRevisit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) classId;
        hash += (int) controlId;
        hash += (int) idLeg;
        hash += (int) idRevisit;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClazzControlPK)) {
            return false;
        }
        ClazzControlPK other = (ClazzControlPK) object;
        if (this.classId != other.classId) {
            return false;
        }
        if (this.controlId != other.controlId) {
            return false;
        }
        if (this.idLeg != other.idLeg) {
            return false;
        }
        if (this.idRevisit != other.idRevisit) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.ClazzControlPK[ classId=" + classId + ", controlId=" + controlId + ", idLeg=" + idLeg + ", idRevisit=" + idRevisit + " ]";
    }
    
}
