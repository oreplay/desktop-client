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
    private String classId;
    @Basic(optional = false)
    @Column(name = "control_id")
    private String controlId;
    @Basic(optional = false)
    @Column(name = "id_leg")
    private int idLeg;
    @Basic(optional = false)
    @Column(name = "id_revisit")
    private int idRevisit;

    public ClazzControlPK() {
    }

    public ClazzControlPK(String classId, String controlId, int idLeg, int idRevisit) {
        this.classId = classId;
        this.controlId = controlId;
        this.idLeg = idLeg;
        this.idRevisit = idRevisit;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getControlId() {
        return controlId;
    }

    public void setControlId(String controlId) {
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
        hash += (classId != null ? classId.hashCode() : 0);
        hash += (controlId != null ? controlId.hashCode() : 0);
        hash += idLeg;
        hash += idRevisit;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean vbResul = true;
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClazzControlPK)) {
            vbResul = false;
        } else {
            ClazzControlPK other = (ClazzControlPK) object;
            if ((this.classId == null && other.classId != null) || (this.classId != null && !this.classId.equals(other.classId))) {
                vbResul = false;
            }
            if ((this.controlId == null && other.controlId != null) || (this.controlId != null && !this.controlId.equals(other.controlId))) {
                vbResul = false;
            }
            if (this.idLeg != other.idLeg) {
                vbResul = false;
            }
            if (this.idRevisit != other.idRevisit) {
                vbResul = false;
            }
        }
        return vbResul;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.ClazzControlPK[ classId=" + classId + ", controlId=" + controlId + ", idLeg=" + idLeg + ", idRevisit=" + idRevisit + " ]";
    }
    
}
