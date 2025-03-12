/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
@Table(name = "teams")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
    @NamedQuery(name = "Team.findById", query = "SELECT t FROM Team t WHERE t.id = :id"),
    @NamedQuery(name = "Team.findByUuid", query = "SELECT t FROM Team t WHERE t.uuid = :uuid"),
    @NamedQuery(name = "Team.findByBibNumber", query = "SELECT t FROM Team t WHERE t.bibNumber = :bibNumber"),
    @NamedQuery(name = "Team.findByBibAlt", query = "SELECT t FROM Team t WHERE t.bibAlt = :bibAlt"),
    @NamedQuery(name = "Team.findByTeamName", query = "SELECT t FROM Team t WHERE t.teamName = :teamName"),
    @NamedQuery(name = "Team.findBySicard", query = "SELECT t FROM Team t WHERE t.sicard = :sicard"),
    @NamedQuery(name = "Team.findBySicardAlt", query = "SELECT t FROM Team t WHERE t.sicardAlt = :sicardAlt"),
    @NamedQuery(name = "Team.findByLegs", query = "SELECT t FROM Team t WHERE t.legs = :legs"),
})
@JsonRootName(value = "teams")
@JsonInclude(Include.NON_NULL)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private String id;
    private String uuid;
    @Column(name = "bib_number")
    private String bibNumber;
    @Column(name = "bib_alt")
    private String bibAlt;
    @Column(name = "team_name")
    private String teamName;
    private String sicard;
    @Column(name = "sicard_alt")
    private String sicardAlt;
    @Column(name = "class_uuid")
    private String classUuid;
    private Integer legs;
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
    @OneToMany(mappedBy = "team")
    private List<Split> splitList;
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event event;
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stage stage;
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @ManyToOne
    private Clazz clazz;
    @JoinColumn(name = "club_id", referencedColumnName = "id")
    @ManyToOne
    private Club club;
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @ManyToOne
    private Course course;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    private List<TeamResult> teamResultList;
    @OneToMany(mappedBy = "team")
    private List<Runner> runnerList;

    public Team() {
    }

    public Team(String id) {
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

    @JsonProperty("bib_number")
    public String getBibNumber() {
        return bibNumber;
    }

    public void setBibNumber(String bibNumber) {
        this.bibNumber = bibNumber;
    }

    @JsonProperty("bib_alt")
    public String getBibAlt() {
        return bibAlt;
    }

    public void setBibAlt(String bibAlt) {
        this.bibAlt = bibAlt;
    }

    @JsonProperty("team_name")
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getSicard() {
        return sicard;
    }

    public void setSicard(String sicard) {
        this.sicard = sicard;
    }

    @JsonProperty("sicard_alt")
    public String getSicardAlt() {
        return sicardAlt;
    }

    public void setSicardAlt(String sicardAlt) {
        this.sicardAlt = sicardAlt;
    }

    public Integer getLegs() {
        return legs;
    }

    public void setLegs(Integer legs) {
        this.legs = legs;
    }

    @JsonProperty("class_uuid")
    public String getClassUuid() {
        return classUuid;
    }

    public void setClassUuid(String classUuid) {
        this.classUuid = classUuid;
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

    @JsonProperty("class")
    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    @JsonProperty("club")
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @JsonProperty("course")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @JsonProperty("team_results")
    @XmlTransient
    public List<TeamResult> getTeamResultList() {
        return teamResultList;
    }

    public void setTeamResultList(List<TeamResult> teamResultList) {
        this.teamResultList = teamResultList;
    }

    @JsonProperty("runners")
    @XmlTransient
    public List<Runner> getRunnerList() {
        return runnerList;
    }

    public void setRunnerList(List<Runner> runnerList) {
        this.runnerList = runnerList;
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
        boolean vbResul = true;
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            vbResul = false;
        } else {
            Team other = (Team) object;
            if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
                vbResul = false;
            }
        }
        return vbResul;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Team[ id=" + id + " ]";
    }
    
}
