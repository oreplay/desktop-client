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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 *
 * @author javier.arufe
 */
@Entity
@Table(name = "stages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stage.findAll", query = "SELECT s FROM Stage s"),
    @NamedQuery(name = "Stage.findById", query = "SELECT s FROM Stage s WHERE s.id = :id"),
    @NamedQuery(name = "Stage.findByDescription", query = "SELECT s FROM Stage s WHERE s.description = :description"),
    @NamedQuery(name = "Stage.findByBaseDate", query = "SELECT s FROM Stage s WHERE s.baseDate = :baseDate"),
    @NamedQuery(name = "Stage.findByBaseTime", query = "SELECT s FROM Stage s WHERE s.baseTime = :baseTime"),
    @NamedQuery(name = "Stage.findByOrderNumber", query = "SELECT s FROM Stage s WHERE s.orderNumber = :orderNumber"),
    @NamedQuery(name = "Stage.findByServerOffset", query = "SELECT s FROM Stage s WHERE s.serverOffset = :serverOffset"),
    @NamedQuery(name = "Stage.findByUtcValue", query = "SELECT s FROM Stage s WHERE s.utcValue = :utcValue"),
})
@JsonRootName(value = "stages")
@JsonInclude(Include.NON_NULL)
public class Stage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private String id;
    private String description;
    @Column(name = "base_date", nullable=true)
    @Temporal(TemporalType.DATE)
    private Date baseDate;
    @Column(name = "base_time", nullable=true)
    @Temporal(TemporalType.TIME)
    private Date baseTime;
    @Column(name = "order_number")
    private Integer orderNumber;
    @Column(name = "server_offset")
    private Integer serverOffset;
    @Column(name = "utc_value")
    private String utcValue;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    private List<Split> splitList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    private List<Answer> answerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    private List<Control> controlList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    private List<Course> courseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    private List<Club> clubList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    private List<Team> teamList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    private List<Clazz> clazzList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    private List<TeamResult> teamResultList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    private List<ClazzControl> clazzControlList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    private List<RunnerResult> runnerResultList;
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event event;
    @JoinColumn(name = "stage_type_id", referencedColumnName = "id")
    @ManyToOne
    private StageType stageType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    private List<Runner> runnerList;

    public Stage() {
    }

    public Stage(String id) {
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

    @JsonProperty("base_date")
    public Date getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    @JsonProperty("base_time")
    public Date getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(Date baseTime) {
        this.baseTime = baseTime;
    }

    @JsonProperty("server_offset")
    public Integer getServerOffset() {
        return serverOffset;
    }

    public void setServerOffset(Integer serverOffset) {
        this.serverOffset = serverOffset;
    }

    @JsonProperty("order_number")
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @JsonProperty("utc_value")
    public String getUtcValue() {
        return utcValue;
    }

    public void setUtcValue(String utcValue) {
        this.utcValue = utcValue;
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

    @JsonProperty("runner_results")
    @XmlTransient
    public List<RunnerResult> getRunnerResultList() {
        return runnerResultList;
    }

    public void setRunnerResultList(List<RunnerResult> runnerResultList) {
        this.runnerResultList = runnerResultList;
    }

    @JsonProperty("event")
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @JsonProperty("stage_type")
    public StageType getStageType() {
        return stageType;
    }

    public void setStageType(StageType stageType) {
        this.stageType = stageType;
    }

    @JsonProperty("runners")
    @XmlTransient
    public List<Runner> getRunnerList() {
        return runnerList;
    }

    public void setRunnerList(List<Runner> runnerList) {
        this.runnerList = runnerList;
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
        if (!(object instanceof Stage)) {
            return false;
        }
        Stage other = (Stage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Stage[ id=" + id + " ]";
    }
    
}
