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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class for Trail-O answers
 * @author javier.arufe
 */
@Entity
@Table(name = "answers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Answer.findAll", query = "SELECT a FROM Answer a"),
    @NamedQuery(name = "Answer.findById", query = "SELECT a FROM Answer a WHERE a.id = :id"),
    @NamedQuery(name = "Answer.findByOrderNumber", query = "SELECT a FROM Answer a WHERE a.orderNumber = :orderNumber"),
    @NamedQuery(name = "Answer.findByGiven", query = "SELECT a FROM Answer a WHERE a.given = :given"),
    @NamedQuery(name = "Answer.findByCorrect", query = "SELECT a FROM Answer a WHERE a.correct = :correct"),
    @NamedQuery(name = "Answer.findByTimeSeconds", query = "SELECT a FROM Answer a WHERE a.timeSeconds = :timeSeconds"),
})
@JsonRootName(value = "answers")
@JsonInclude(Include.NON_NULL)
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private String id;
    @Column(name = "order_number")
    private Integer orderNumber;
    private String given;
    private String correct;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "time_seconds")
    private BigDecimal timeSeconds;
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
    @JoinColumn(name = "runner_result_id", referencedColumnName = "id")
    @ManyToOne
    private RunnerResult runnerResult;

    public Answer() {
    }

    public Answer(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("order_number")
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    @JsonProperty("time_seconds")
    public BigDecimal getTimeSeconds() {
        return timeSeconds;
    }

    public void setTimeSeconds(BigDecimal timeSeconds) {
        this.timeSeconds = timeSeconds;
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

    @JsonProperty("runner_result")
    public RunnerResult getRunnerResult() {
        return runnerResult;
    }

    public void setRunnerResult(RunnerResult runnerResult) {
        this.runnerResult = runnerResult;
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
        if (!(object instanceof Answer)) {
            return false;
        }
        Answer other = (Answer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Answer[ id=" + id + " ]";
    }
    
}
