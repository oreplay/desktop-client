/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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

/**
 *
 * @author javier.arufe
 */
@Entity
@Table(name = "courses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c"),
    @NamedQuery(name = "Course.findById", query = "SELECT c FROM Course c WHERE c.id = :id"),
    @NamedQuery(name = "Course.findByUuid", query = "SELECT c FROM Course c WHERE c.uuid = :uuid"),
    @NamedQuery(name = "Course.findByOeKey", query = "SELECT c FROM Course c WHERE c.oeKey = :oeKey"),
    @NamedQuery(name = "Course.findByShortName", query = "SELECT c FROM Course c WHERE c.shortName = :shortName"),
    @NamedQuery(name = "Course.findByLongName", query = "SELECT c FROM Course c WHERE c.longName = :longName"),
    @NamedQuery(name = "Course.findByDistance", query = "SELECT c FROM Course c WHERE c.distance = :distance"),
    @NamedQuery(name = "Course.findByClimb", query = "SELECT c FROM Course c WHERE c.climb = :climb"),
    @NamedQuery(name = "Course.findByControls", query = "SELECT c FROM Course c WHERE c.controls = :controls"),
    @NamedQuery(name = "Course.findByCoordSystem", query = "SELECT c FROM Course c WHERE c.coordSystem = :coordSystem"),
    @NamedQuery(name = "Course.findByDatum", query = "SELECT c FROM Course c WHERE c.datum = :datum"),
    @NamedQuery(name = "Course.findByUtmZone", query = "SELECT c FROM Course c WHERE c.utmZone = :utmZone"),
    @NamedQuery(name = "Course.findByHemisphere", query = "SELECT c FROM Course c WHERE c.hemisphere = :hemisphere"),
    @NamedQuery(name = "Course.findByLatitude", query = "SELECT c FROM Course c WHERE c.latitude = :latitude"),
    @NamedQuery(name = "Course.findByLongitude", query = "SELECT c FROM Course c WHERE c.longitude = :longitude"),
    @NamedQuery(name = "Course.findByZoom", query = "SELECT c FROM Course c WHERE c.zoom = :zoom"),
})
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private String id;
    private String uuid;
    @Column(name = "oe_key")
    private String oeKey;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "long_name")
    private String longName;
    private String distance;
    private String climb;
    private Integer controls;
    @Column(name = "coord_system")
    private Character coordSystem;
    private String datum;
    @Column(name = "utm_zone")
    private Integer utmZone;
    private Character hemisphere;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer zoom;
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
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event event;
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stage stage;
    @OneToMany(mappedBy = "course")
    private List<Clazz> clazzList;

    public Course() {
    }

    public Course(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOeKey() {
        return oeKey;
    }

    public void setOeKey(String oeKey) {
        this.oeKey = oeKey;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getClimb() {
        return climb;
    }

    public void setClimb(String climb) {
        this.climb = climb;
    }

    public Integer getControls() {
        return controls;
    }

    public void setControls(Integer controls) {
        this.controls = controls;
    }

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

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
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

    @XmlTransient
    public List<Clazz> getClazzList() {
        return clazzList;
    }

    public void setClazzList(List<Clazz> clazzList) {
        this.clazzList = clazzList;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

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
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Course[ id=" + id + " ]";
    }
    
}
