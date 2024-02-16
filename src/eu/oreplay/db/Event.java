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
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    private String description;
    @Column(name = "initial_date", nullable=true)
    @Temporal(TemporalType.DATE)
    private Date initialDate;
    @Column(name = "final_date", nullable=true)
    @Temporal(TemporalType.DATE)
    private Date finalDate;
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

    public Event(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public Federation getFederation() {
        return federation;
    }

    public void setFederation(Federation federation) {
        this.federation = federation;
    }

    @XmlTransient
    public List<Split> getSplitList() {
        return splitList;
    }

    public void setSplitList(List<Split> splitList) {
        this.splitList = splitList;
    }

    @XmlTransient
    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    @XmlTransient
    public List<Control> getControlList() {
        return controlList;
    }

    public void setControlList(List<Control> controlList) {
        this.controlList = controlList;
    }

    @XmlTransient
    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    @XmlTransient
    public List<Club> getClubList() {
        return clubList;
    }

    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }

    @XmlTransient
    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    @XmlTransient
    public List<Clazz> getClazzList() {
        return clazzList;
    }

    public void setClazzList(List<Clazz> clazzList) {
        this.clazzList = clazzList;
    }

    @XmlTransient
    public List<TeamResult> getTeamResultList() {
        return teamResultList;
    }

    public void setTeamResultList(List<TeamResult> teamResultList) {
        this.teamResultList = teamResultList;
    }

    @XmlTransient
    public List<ClazzControl> getClazzControlList() {
        return clazzControlList;
    }

    public void setClazzControlList(List<ClazzControl> clazzControlList) {
        this.clazzControlList = clazzControlList;
    }

    @XmlTransient
    public List<UserEvent> getUserEventList() {
        return userEventList;
    }

    public void setUserEventList(List<UserEvent> userEventList) {
        this.userEventList = userEventList;
    }

    @XmlTransient
    public List<RunnerResult> getRunnerResultList() {
        return runnerResultList;
    }

    public void setRunnerResultList(List<RunnerResult> runnerResultList) {
        this.runnerResultList = runnerResultList;
    }

    @XmlTransient
    public List<Stage> getStageList() {
        return stageList;
    }

    public void setStageList(List<Stage> stageList) {
        this.stageList = stageList;
    }

    @XmlTransient
    public List<Runner> getRunnerList() {
        return runnerList;
    }

    public void setRunnerList(List<Runner> runnerList) {
        this.runnerList = runnerList;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Event[ id=" + id + " ]";
    }
    
}
