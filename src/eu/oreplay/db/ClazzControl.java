/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author javier.arufe
 */
@Entity
@Table(name = "classes_controls")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClazzControl.findAll", query = "SELECT c FROM ClazzControl c"),
    @NamedQuery(name = "ClazzControl.findByClassId", query = "SELECT c FROM ClazzControl c WHERE c.clazzControlPK.classId = :classId"),
    @NamedQuery(name = "ClazzControl.findByControlId", query = "SELECT c FROM ClazzControl c WHERE c.clazzControlPK.controlId = :controlId"),
    @NamedQuery(name = "ClazzControl.findByIdLeg", query = "SELECT c FROM ClazzControl c WHERE c.clazzControlPK.idLeg = :idLeg"),
    @NamedQuery(name = "ClazzControl.findByIdRevisit", query = "SELECT c FROM ClazzControl c WHERE c.clazzControlPK.idRevisit = :idRevisit"),
    @NamedQuery(name = "ClazzControl.findByDescription", query = "SELECT c FROM ClazzControl c WHERE c.description = :description"),
    @NamedQuery(name = "ClazzControl.findByOrderNumber", query = "SELECT c FROM ClazzControl c WHERE c.orderNumber = :orderNumber"),
    @NamedQuery(name = "ClazzControl.findByKilometer", query = "SELECT c FROM ClazzControl c WHERE c.kilometer = :kilometer"),
    @NamedQuery(name = "ClazzControl.findByRelativePosition", query = "SELECT c FROM ClazzControl c WHERE c.relativePosition = :relativePosition"),
    @NamedQuery(name = "ClazzControl.findByControls", query = "SELECT c FROM ClazzControl c WHERE c.controls = :controls"),
})
public class ClazzControl implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClazzControlPK clazzControlPK;
    private String description;
    @Column(name = "order_number")
    private Integer orderNumber;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private BigDecimal kilometer;
    @Column(name = "relative_position")
    private Integer relativePosition;
    private Integer controls;
    @OneToMany(mappedBy = "clazzControl")
    private List<Split> splitList;
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event event;
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stage stage;
    @JoinColumn(name = "class_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clazz clazz;
    @JoinColumn(name = "control_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Control control;

    public ClazzControl() {
    }

    public ClazzControl(ClazzControlPK clazzControlPK) {
        this.clazzControlPK = clazzControlPK;
    }

    public ClazzControl(long clazzId, long controlId, int idLeg, int idRevisit) {
        this.clazzControlPK = new ClazzControlPK(clazzId, controlId, idLeg, idRevisit);
    }

    public ClazzControlPK getClazzControlPK() {
        return clazzControlPK;
    }

    public void setClazzControlPK(ClazzControlPK clazzControlPK) {
        this.clazzControlPK = clazzControlPK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getKilometer() {
        return kilometer;
    }

    public void setKilometer(BigDecimal kilometer) {
        this.kilometer = kilometer;
    }

    public Integer getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(Integer relativePosition) {
        this.relativePosition = relativePosition;
    }

    public Integer getControls() {
        return controls;
    }

    public void setControls(Integer controls) {
        this.controls = controls;
    }

    @XmlTransient
    public List<Split> getSplitList() {
        return splitList;
    }

    public void setSplitList(List<Split> splitList) {
        this.splitList = splitList;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clazzControlPK != null ? clazzControlPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClazzControl)) {
            return false;
        }
        ClazzControl other = (ClazzControl) object;
        if ((this.clazzControlPK == null && other.clazzControlPK != null) || (this.clazzControlPK != null && !this.clazzControlPK.equals(other.clazzControlPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.ClazzControl[ clazzControlPK=" + clazzControlPK + " ]";
    }
    
}
