/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.oreplay.logic.converter.IsoDateSerializer;

/**
 *
 * @author javier.arufe
 */
@Entity
@Table(name = "events")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findById", query = "SELECT e FROM Event e WHERE e.id = :id"),
    @NamedQuery(name = "Event.findByDescription", query = "SELECT e FROM Event e WHERE e.description = :description"),
    @NamedQuery(name = "Event.findByInitialDate", query = "SELECT e FROM Event e WHERE e.initialDate = :initialDate"),
    @NamedQuery(name = "Event.findByFinalDate", query = "SELECT e FROM Event e WHERE e.finalDate = :finalDate"),
})
@JsonRootName(value = "event")
@JsonInclude(Include.NON_NULL)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private String id;
    private String description;
    @Column(name = "initial_date", nullable=true)
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = IsoDateSerializer.class)
    private Date initialDate;
    @Column(name = "final_date", nullable=true)
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = IsoDateSerializer.class)
    private Date finalDate;
    //Dummy needed for not breaking the communication between Back and Client
    private String federation_id = "";
    private Link _links;
    @Basic(optional = false)
    @Column(name = "is_hidden")
    private boolean isHidden;
    private String website;
    private String picture;
    private String scope;
    private String location;
    @Column(name = "country_code")
    private String countryCode;
    
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
    @JoinColumn(name = "federation_id", referencedColumnName = "id")
    @ManyToOne
    private Federation federation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<Split> splitList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<Answer> answerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<Control> controlList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<Course> courseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<Club> clubList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<Team> teamList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<Clazz> clazzList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<TeamResult> teamResultList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<ClazzControl> clazzControlList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<UserEvent> userEventList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<RunnerResult> runnerResultList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<Stage> stageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<Runner> runnerList;

    public Event() {
    }

    public Event(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("initial_date")
    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    @JsonProperty("final_date")
    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    @JsonProperty("federation")
    public Federation getFederation() {
        return federation;
    }

    public void setFederation(Federation federation) {
        this.federation = federation;
    }

    @JsonProperty("splits")
    @XmlTransient
    public List<Split> getSplitList() {
        return splitList;
    }

    public void setSplitList(List<Split> splitList) {
        this.splitList = splitList;
    }

    @JsonProperty("answers")
    @XmlTransient
    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    @JsonProperty("controls")
    @XmlTransient
    public List<Control> getControlList() {
        return controlList;
    }

    public void setControlList(List<Control> controlList) {
        this.controlList = controlList;
    }

    @JsonProperty("courses")
    @XmlTransient
    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    @JsonProperty("clubs")
    @XmlTransient
    public List<Club> getClubList() {
        return clubList;
    }

    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }

    @JsonProperty("teams")
    @XmlTransient
    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    @JsonProperty("classes")
    @XmlTransient
    public List<Clazz> getClazzList() {
        return clazzList;
    }

    public void setClazzList(List<Clazz> clazzList) {
        this.clazzList = clazzList;
    }

    @JsonProperty("team_results")
    @XmlTransient
    public List<TeamResult> getTeamResultList() {
        return teamResultList;
    }

    public void setTeamResultList(List<TeamResult> teamResultList) {
        this.teamResultList = teamResultList;
    }

    @JsonProperty("classes_controls")
    @XmlTransient
    public List<ClazzControl> getClazzControlList() {
        return clazzControlList;
    }

    public void setClazzControlList(List<ClazzControl> clazzControlList) {
        this.clazzControlList = clazzControlList;
    }

    @JsonProperty("users_events")
    @XmlTransient
    public List<UserEvent> getUserEventList() {
        return userEventList;
    }

    public void setUserEventList(List<UserEvent> userEventList) {
        this.userEventList = userEventList;
    }

    @JsonProperty("runner_results")
    @XmlTransient
    public List<RunnerResult> getRunnerResultList() {
        return runnerResultList;
    }

    public void setRunnerResultList(List<RunnerResult> runnerResultList) {
        this.runnerResultList = runnerResultList;
    }

    @JsonProperty("stages")
    @XmlTransient
    public List<Stage> getStageList() {
        return stageList;
    }

    public void setStageList(List<Stage> stageList) {
        this.stageList = stageList;
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

    //Dummy needed
    @JsonIgnore
    @JsonProperty("federation_id")
    public String getFederation_id() {
        return federation_id;
    }
    public void setFederation_id(String federation_id) {
        this.federation_id = federation_id;
    }
    @JsonProperty("_links")
    public Link getLinks() {
        return _links;
    }
    public void setLinks(Link _links) {
        this._links = _links;
    }
    @JsonProperty("is_hidden")
    public boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
        if (!(object instanceof Event)) {
            vbResul = false;
        } else {
            Event other = (Event) object;
            if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
                vbResul = false;
            }
        }
        return vbResul;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Event[ id=" + id + " ]";
    }
    
}
