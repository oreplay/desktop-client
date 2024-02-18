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
@Table(name = "team_results")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeamResult.findAll", query = "SELECT t FROM TeamResult t"),
    @NamedQuery(name = "TeamResult.findById", query = "SELECT t FROM TeamResult t WHERE t.id = :id"),
    @NamedQuery(name = "TeamResult.findByCheckTime", query = "SELECT t FROM TeamResult t WHERE t.checkTime = :checkTime"),
    @NamedQuery(name = "TeamResult.findByStartTime", query = "SELECT t FROM TeamResult t WHERE t.startTime = :startTime"),
    @NamedQuery(name = "TeamResult.findByFinishTime", query = "SELECT t FROM TeamResult t WHERE t.finishTime = :finishTime"),
    @NamedQuery(name = "TeamResult.findByTimeSeconds", query = "SELECT t FROM TeamResult t WHERE t.timeSeconds = :timeSeconds"),
    @NamedQuery(name = "TeamResult.findByPosition", query = "SELECT t FROM TeamResult t WHERE t.position = :position"),
    @NamedQuery(name = "TeamResult.findByStatusCode", query = "SELECT t FROM TeamResult t WHERE t.statusCode = :statusCode"),
    @NamedQuery(name = "TeamResult.findByTimeBehind", query = "SELECT t FROM TeamResult t WHERE t.timeBehind = :timeBehind"),
    @NamedQuery(name = "TeamResult.findByTimeNeutralization", query = "SELECT t FROM TeamResult t WHERE t.timeNeutralization = :timeNeutralization"),
    @NamedQuery(name = "TeamResult.findByTimeAdjusted", query = "SELECT t FROM TeamResult t WHERE t.timeAdjusted = :timeAdjusted"),
    @NamedQuery(name = "TeamResult.findByTimePenalty", query = "SELECT t FROM TeamResult t WHERE t.timePenalty = :timePenalty"),
    @NamedQuery(name = "TeamResult.findByTimeBonus", query = "SELECT t FROM TeamResult t WHERE t.timeBonus = :timeBonus"),
    @NamedQuery(name = "TeamResult.findByPointsFinal", query = "SELECT t FROM TeamResult t WHERE t.pointsFinal = :pointsFinal"),
    @NamedQuery(name = "TeamResult.findByPointsAdjusted", query = "SELECT t FROM TeamResult t WHERE t.pointsAdjusted = :pointsAdjusted"),
    @NamedQuery(name = "TeamResult.findByPointsPenalty", query = "SELECT t FROM TeamResult t WHERE t.pointsPenalty = :pointsPenalty"),
    @NamedQuery(name = "TeamResult.findByPointsBonus", query = "SELECT t FROM TeamResult t WHERE t.pointsBonus = :pointsBonus"),
})
public class TeamResult implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private String id;
    @Column(name = "stage_order")
    private Integer stageOrder;
    @Column(name = "team_uuid")
    private String teamUuid;
    @Column(name = "class_uuid")
    private String classUuid;
    @Column(name = "check_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkTime;
    @Column(name = "start_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "finish_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishTime;
    @Column(name = "time_seconds")
    private Integer timeSeconds;
    private Integer position;
    @Column(name = "status_code")
    private Character statusCode;
    @Column(name = "time_behind")
    private Integer timeBehind;
    @Column(name = "time_neutralization")
    private Integer timeNeutralization;
    @Column(name = "time_adjusted")
    private Integer timeAdjusted;
    @Column(name = "time_penalty")
    private Integer timePenalty;
    @Column(name = "time_bonus")
    private Integer timeBonus;
    @Column(name = "points_final")
    private Integer pointsFinal;
    @Column(name = "points_adjusted")
    private Integer pointsAdjusted;
    @Column(name = "points_penalty")
    private Integer pointsPenalty;
    @Column(name = "points_bonus")
    private Integer pointsBonus;
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
    @OneToMany(mappedBy = "teamResult")
    private List<Split> splitList;
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event event;
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stage stage;
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Team team;
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @ManyToOne
    private Clazz clazz;
    @JoinColumn(name = "result_type_id", referencedColumnName = "id")
    @ManyToOne
    private ResultType resultType;

    public TeamResult() {
    }

    public TeamResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getTimeSeconds() {
        return timeSeconds;
    }

    public void setTimeSeconds(Integer timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Character getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Character statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getTimeBehind() {
        return timeBehind;
    }

    public void setTimeBehind(Integer timeBehind) {
        this.timeBehind = timeBehind;
    }

    public Integer getTimeNeutralization() {
        return timeNeutralization;
    }

    public void setTimeNeutralization(Integer timeNeutralization) {
        this.timeNeutralization = timeNeutralization;
    }

    public Integer getTimeAdjusted() {
        return timeAdjusted;
    }

    public void setTimeAdjustedy(Integer timeAdjusted) {
        this.timeAdjusted = timeAdjusted;
    }

    public Integer getTimePenalty() {
        return timePenalty;
    }

    public void setTimePenalty(Integer timePenalty) {
        this.timePenalty = timePenalty;
    }

    public Integer getTimeBonus() {
        return timeBonus;
    }

    public void setTimeBonus(Integer timeBonus) {
        this.timeBonus = timeBonus;
    }

    public Integer getPointsFinal() {
        return pointsFinal;
    }

    public void setPointsFinal(Integer pointsFinal) {
        this.pointsFinal = pointsFinal;
    }

    public Integer getPointsAdjusted() {
        return pointsAdjusted;
    }

    public void setPointsAdjusted(Integer pointsAdjusted) {
        this.pointsAdjusted = pointsAdjusted;
    }

    public Integer getPointsPenalty() {
        return pointsPenalty;
    }

    public void setPointsPenalty(Integer pointsPenalty) {
        this.pointsPenalty = pointsPenalty;
    }

    public Integer getPointsBonus() {
        return pointsBonus;
    }

    public void setPointsBonus(Integer pointsBonus) {
        this.pointsBonus = pointsBonus;
    }

    public Integer getStageOrder() {
        return stageOrder;
    }

    public void setStageOrder(Integer stageOrder) {
        this.stageOrder = stageOrder;
    }

    public String getTeamUuid() {
        return teamUuid;
    }

    public void setTeamUuid(String teamUuid) {
        this.teamUuid = teamUuid;
    }

    public String getClassUuid() {
        return classUuid;
    }

    public void setClassUuid(String classUuid) {
        this.classUuid = classUuid;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
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
        if (!(object instanceof TeamResult)) {
            return false;
        }
        TeamResult other = (TeamResult) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.TeamResult[ id=" + id + " ]";
    }
    
}
