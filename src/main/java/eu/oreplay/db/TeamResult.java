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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.oreplay.logic.converter.IsoTimestampSerializer;
import java.math.BigDecimal;

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
@JsonRootName(value = "team_results")
@JsonInclude(Include.NON_NULL)
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
    @JsonSerialize(using = IsoTimestampSerializer.class)
    private Date checkTime;
    @Column(name = "start_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = IsoTimestampSerializer.class)
    private Date startTime;
    @Column(name = "finish_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = IsoTimestampSerializer.class)
    private Date finishTime;
    @Column(name = "time_seconds")
    private BigDecimal timeSeconds;
    private Integer position;
    @Column(name = "status_code")
    private Character statusCode;
    @Column(name = "time_behind")
    private BigDecimal timeBehind;
    @Column(name = "time_neutralization")
    private BigDecimal timeNeutralization;
    @Column(name = "time_adjusted")
    private BigDecimal timeAdjusted;
    @Column(name = "time_penalty")
    private BigDecimal timePenalty;
    @Column(name = "time_bonus")
    private BigDecimal timeBonus;
    @Column(name = "points_final")
    private BigDecimal pointsFinal;
    @Column(name = "points_adjusted")
    private BigDecimal pointsAdjusted;
    @Column(name = "points_penalty")
    private BigDecimal pointsPenalty;
    @Column(name = "points_bonus")
    private BigDecimal pointsBonus;
    @Column(name = "leg_number")
    private Integer legNumber;
    @Basic(optional = true)
    private boolean contributory;
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

    @JsonProperty("check_time")
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    @JsonProperty("start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("finish_time")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @JsonProperty("time_seconds")
    public BigDecimal getTimeSeconds() {
        return timeSeconds;
    }

    public void setTimeSeconds(BigDecimal timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @JsonProperty("status_code")
    public Character getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Character statusCode) {
        this.statusCode = statusCode;
    }

    @JsonProperty("time_behind")
    public BigDecimal getTimeBehind() {
        return timeBehind;
    }

    public void setTimeBehind(BigDecimal timeBehind) {
        this.timeBehind = timeBehind;
    }

    @JsonProperty("time_neutralization")
    public BigDecimal getTimeNeutralization() {
        return timeNeutralization;
    }

    public void setTimeNeutralization(BigDecimal timeNeutralization) {
        this.timeNeutralization = timeNeutralization;
    }

    @JsonProperty("time_adjusted")
    public BigDecimal getTimeAdjusted() {
        return timeAdjusted;
    }

    public void setTimeAdjusted(BigDecimal timeAdjusted) {
        this.timeAdjusted = timeAdjusted;
    }

    @JsonProperty("time_penalty")
    public BigDecimal getTimePenalty() {
        return timePenalty;
    }

    public void setTimePenalty(BigDecimal timePenalty) {
        this.timePenalty = timePenalty;
    }

    @JsonProperty("time_bonus")
    public BigDecimal getTimeBonus() {
        return timeBonus;
    }

    public void setTimeBonus(BigDecimal timeBonus) {
        this.timeBonus = timeBonus;
    }

    @JsonProperty("points_final")
    public BigDecimal getPointsFinal() {
        return pointsFinal;
    }

    public void setPointsFinal(BigDecimal pointsFinal) {
        this.pointsFinal = pointsFinal;
    }

    @JsonProperty("points_adjusted")
    public BigDecimal getPointsAdjusted() {
        return pointsAdjusted;
    }

    public void setPointsAdjusted(BigDecimal pointsAdjusted) {
        this.pointsAdjusted = pointsAdjusted;
    }

    @JsonProperty("points_penalty")
    public BigDecimal getPointsPenalty() {
        return pointsPenalty;
    }

    public void setPointsPenalty(BigDecimal pointsPenalty) {
        this.pointsPenalty = pointsPenalty;
    }

    @JsonProperty("points_bonus")
    public BigDecimal getPointsBonus() {
        return pointsBonus;
    }

    public void setPointsBonus(BigDecimal pointsBonus) {
        this.pointsBonus = pointsBonus;
    }

    @JsonProperty("leg_number")
    public Integer getLegNumber() {
        return legNumber;
    }

    public void setLegNumber(Integer legNumber) {
        this.legNumber = legNumber;
    }

    public boolean getContributory() {
        return contributory;
    }

    public void setContributory(boolean contributory) {
        this.contributory = contributory;
    }

    @JsonProperty("stage_order")
    public Integer getStageOrder() {
        return stageOrder;
    }

    public void setStageOrder(Integer stageOrder) {
        this.stageOrder = stageOrder;
    }

    @JsonProperty("team_uuid")
    public String getTeamUuid() {
        return teamUuid;
    }

    public void setTeamUuid(String teamUuid) {
        this.teamUuid = teamUuid;
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

    @JsonProperty("team")
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @JsonProperty("class")
    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    @JsonProperty("result_type")
    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
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
        if (!(object instanceof TeamResult other)) {
            vbResul = false;
        } else {
            if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
                vbResul = false;
            }
        }
        return vbResul;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.TeamResult[ id=" + id + " ]";
    }
    
}
