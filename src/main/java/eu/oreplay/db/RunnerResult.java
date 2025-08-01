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
import eu.oreplay.utils.Utils;
import java.math.BigDecimal;

/**
 *
 * @author javier.arufe
 */
@Entity
@Table(name = "runner_results")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RunnerResult.findAll", query = "SELECT r FROM RunnerResult r"),
    @NamedQuery(name = "RunnerResult.findById", query = "SELECT r FROM RunnerResult r WHERE r.id = :id"),
    @NamedQuery(name = "RunnerResult.findByCheckTime", query = "SELECT r FROM RunnerResult r WHERE r.checkTime = :checkTime"),
    @NamedQuery(name = "RunnerResult.findByStartTime", query = "SELECT r FROM RunnerResult r WHERE r.startTime = :startTime"),
    @NamedQuery(name = "RunnerResult.findByFinishTime", query = "SELECT r FROM RunnerResult r WHERE r.finishTime = :finishTime"),
    @NamedQuery(name = "RunnerResult.findByTimeSeconds", query = "SELECT r FROM RunnerResult r WHERE r.timeSeconds = :timeSeconds"),
    @NamedQuery(name = "RunnerResult.findByPosition", query = "SELECT r FROM RunnerResult r WHERE r.position = :position"),
    @NamedQuery(name = "RunnerResult.findByStatusCode", query = "SELECT r FROM RunnerResult r WHERE r.statusCode = :statusCode"),
    @NamedQuery(name = "RunnerResult.findByTimeBehind", query = "SELECT r FROM RunnerResult r WHERE r.timeBehind = :timeBehind"),
    @NamedQuery(name = "RunnerResult.findByTimeNeutralization", query = "SELECT r FROM RunnerResult r WHERE r.timeNeutralization = :timeNeutralization"),
    @NamedQuery(name = "RunnerResult.findByTimeAdjusted", query = "SELECT r FROM RunnerResult r WHERE r.timeAdjusted = :timeAdjusted"),
    @NamedQuery(name = "RunnerResult.findByTimePenalty", query = "SELECT r FROM RunnerResult r WHERE r.timePenalty = :timePenalty"),
    @NamedQuery(name = "RunnerResult.findByTimeBonus", query = "SELECT r FROM RunnerResult r WHERE r.timeBonus = :timeBonus"),
    @NamedQuery(name = "RunnerResult.findByPointsFinal", query = "SELECT r FROM RunnerResult r WHERE r.pointsFinal = :pointsFinal"),
    @NamedQuery(name = "RunnerResult.findByPointsAdjusted", query = "SELECT r FROM RunnerResult r WHERE r.pointsAdjusted = :pointsAdjusted"),
    @NamedQuery(name = "RunnerResult.findByPointsPenalty", query = "SELECT r FROM RunnerResult r WHERE r.pointsPenalty = :pointsPenalty"),
    @NamedQuery(name = "RunnerResult.findByPointsBonus", query = "SELECT r FROM RunnerResult r WHERE r.pointsBonus = :pointsBonus"),
    @NamedQuery(name = "RunnerResult.findByLegNumber", query = "SELECT r FROM RunnerResult r WHERE r.legNumber = :legNumber"),
})
@JsonRootName(value = "runner_results")
@JsonInclude(Include.NON_NULL)
public class RunnerResult implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private String id;
    @Column(name = "stage_order")
    private Integer stageOrder;
    @Column(name = "runner_uuid")
    private String runnerUuid;
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
    @OneToMany(mappedBy = "runnerResult")
    private List<Split> splitList;
    @OneToMany(mappedBy = "runnerResult")
    private List<Answer> answerList;
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event event;
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stage stage;
    @JoinColumn(name = "runner_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Runner runner;
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @ManyToOne
    private Clazz clazz;
    @JoinColumn(name = "result_type_id", referencedColumnName = "id")
    @ManyToOne
    private ResultType resultType;

    public RunnerResult() {
    }

    public RunnerResult(String id) {
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

    @JsonProperty("runner_uuid")
    public String getRunnerUuid() {
        return runnerUuid;
    }

    public void setRunnerUuid(String runnerUuid) {
        this.runnerUuid = runnerUuid;
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

    @JsonProperty("answers")
    @XmlTransient
    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
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

    @JsonProperty("runner")
    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
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

    /**
     * Sets the value of the status code from the NotCompeting status and the 
     * value of certain fields; this is used when results come from XML
     * @param pcStatusValue String The status value of the runner
     * @param pbIsNc Boolean Flag that indicates whether the runner is competing or not
     */
    public void setStatusCodeFromNc (String pcStatusValue, Boolean pbIsNc) {
        //Value of Status depending on NC and other rules
        if (pbIsNc) {
            //If there is a position, then the Status is OK
            if (getPosition()!=null && getPosition().intValue()>0) {
                setStatusCode(Utils.STATUS_OK_ID);
            } else {
                //If not, there is no chance to exactly know the status
                //If there are StartTime and FinishTime, let's assume MissingPunch
                if (getStartTime()!=null && getFinishTime()!=null) {
                    setStatusCode(Utils.STATUS_MISSING_ID);
                //If there is no StartTime and no FinishTime, let's assume DidNotStart
                } else if (getStartTime()==null && getFinishTime()==null) {
                    setStatusCode(Utils.STATUS_DNS_ID);
                //If there is StartTime and no FinishTime, let's assume DidNotFinish
                } else if (getStartTime()!=null && getFinishTime()==null) {
                    setStatusCode(Utils.STATUS_DNF_ID);
                }
            }
        } else {
            //If the runner is competing, get the status from IOF enumeration to OReplay Ids
            setStatusCode(Utils.convertIofStatusValue(pcStatusValue));
        }
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
        if (!(object instanceof RunnerResult other)) {
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
        return "eu.oreplay.db.RunnerResult[ id=" + id + " ]";
    }
    
}
