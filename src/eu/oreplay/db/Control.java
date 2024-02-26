/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 *
 * @author javier.arufe
 */
@Entity
@Table(name = "controls")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Control.findAll", query = "SELECT c FROM Control c"),
    @NamedQuery(name = "Control.findById", query = "SELECT c FROM Control c WHERE c.id = :id"),
    @NamedQuery(name = "Control.findByControlName", query = "SELECT c FROM Control c WHERE c.controlName = :controlName"),
    @NamedQuery(name = "Control.findByStation", query = "SELECT c FROM Control c WHERE c.station = :station"),
    @NamedQuery(name = "Control.findByCoordSystem", query = "SELECT c FROM Control c WHERE c.coordSystem = :coordSystem"),
    @NamedQuery(name = "Control.findByDatum", query = "SELECT c FROM Control c WHERE c.datum = :datum"),
    @NamedQuery(name = "Control.findByUtmZone", query = "SELECT c FROM Control c WHERE c.utmZone = :utmZone"),
    @NamedQuery(name = "Control.findByHemisphere", query = "SELECT c FROM Control c WHERE c.hemisphere = :hemisphere"),
    @NamedQuery(name = "Control.findByLatitude", query = "SELECT c FROM Control c WHERE c.latitude = :latitude"),
    @NamedQuery(name = "Control.findByLongitude", query = "SELECT c FROM Control c WHERE c.longitude = :longitude"),
    @NamedQuery(name = "Control.findByBatteryPerc", query = "SELECT c FROM Control c WHERE c.batteryPerc = :batteryPerc"),
    @NamedQuery(name = "Control.findByLastReading", query = "SELECT c FROM Control c WHERE c.lastReading = :lastReading"),
})
@JsonRootName(value = "controls")
@JsonInclude(Include.NON_NULL)
public class Control implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private String id;
    @Column(name = "control_name")
    private String controlName;
    private String station;
    @Column(name = "coord_system")
    private Character coordSystem;
    private String datum;
    @Column(name = "utm_zone")
    private Integer utmZone;
    private Character hemisphere;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private BigDecimal latitude;
    private BigDecimal longitude;
    @Column(name = "battery_perc")
    private Integer batteryPerc;
    @Column(name = "last_reading", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastReading;
    //Dates for creation, modification and deletion
    @Column(name = "created", nullable=true)
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "modified", nullable=true)
    @Temporal(TemporalType.DATE)
    private Date modified;
    @Column(name = "deleted", nullable=true)
    @Temporal(TemporalType.DATE)
    private Date deleted;
    //
    @OneToMany(mappedBy = "control")
    private List<Split> splitList;
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event event;
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stage stage;
    @JoinColumn(name = "control_type_id", referencedColumnName = "id")
    @ManyToOne
    private ControlType controlType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "control")
    private List<ClazzControl> clazzControlList;

    public Control() {
    }

    public Control(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("control_name")
    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @JsonProperty("coord_system")
    public Character getCoordSystem() {
        return coordSystem;
    }

    public void setCoordSystem(Character coordSystem) {
        this.coordSystem = coordSystem;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    @JsonProperty("utm_zone")
    public Integer getUtmZone() {
        return utmZone;
    }

    public void setUtmZone(Integer utmZone) {
        this.utmZone = utmZone;
    }

    public Character getHemisphere() {
        return hemisphere;
    }

    public void setHemisphere(Character hemisphere) {
        this.hemisphere = hemisphere;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("battery_perc")
    public Integer getBatteryPerc() {
        return batteryPerc;
    }

    public void setBatteryPerc(Integer batteryPerc) {
        this.batteryPerc = batteryPerc;
    }

    @JsonProperty("last_reading")
    public Date getLastReading() {
        return lastReading;
    }

    public void setLastReading(Date lastReading) {
        this.lastReading = lastReading;
    }

    @JsonProperty("splits")
    @XmlTransient
    public List<Split> getSplitList() {
        return splitList;
    }

    public void setSplitList(List<Split> splitList) {
        this.splitList = splitList;
    }

    @JsonProperty("event")
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @JsonProperty("stage")
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @JsonProperty("control_type")
    public ControlType getControlType() {
        return controlType;
    }

    public void setControlType(ControlType controlType) {
        this.controlType = controlType;
    }

    @JsonProperty("classes_controls")
    @XmlTransient
    public List<ClazzControl> getClazzControlList() {
        return clazzControlList;
    }

    public void setClazzControlList(List<ClazzControl> clazzControlList) {
        this.clazzControlList = clazzControlList;
    }

    @JsonIgnore
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @JsonIgnore
    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @JsonIgnore
    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Control)) {
            return false;
        }
        Control other = (Control) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Control[ id=" + id + " ]";
    }
    
}
