/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javier.arufe
 */
@Entity
@Table(name = "splits")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Split.findAll", query = "SELECT s FROM Split s"),
    @NamedQuery(name = "Split.findById", query = "SELECT s FROM Split s WHERE s.id = :id"),
    @NamedQuery(name = "Split.findByStageCounter", query = "SELECT s FROM Split s WHERE s.stageCounter = :stageCounter"),
    @NamedQuery(name = "Split.findBySicard", query = "SELECT s FROM Split s WHERE s.sicard = :sicard"),
    @NamedQuery(name = "Split.findByStation", query = "SELECT s FROM Split s WHERE s.station = :station"),
    @NamedQuery(name = "Split.findByReadingTime", query = "SELECT s FROM Split s WHERE s.readingTime = :readingTime"),
    @NamedQuery(name = "Split.findByReadingMilli", query = "SELECT s FROM Split s WHERE s.readingMilli = :readingMilli"),
    @NamedQuery(name = "Split.findByPoints", query = "SELECT s FROM Split s WHERE s.points = :points"),
    @NamedQuery(name = "Split.findByBibRunner", query = "SELECT s FROM Split s WHERE s.bibRunner = :bibRunner"),
    @NamedQuery(name = "Split.findByBibTeam", query = "SELECT s FROM Split s WHERE s.bibTeam = :bibTeam"),
    @NamedQuery(name = "Split.findByOrderNumber", query = "SELECT s FROM Split s WHERE s.orderNumber = :orderNumber"),
    @NamedQuery(name = "Split.findByBatteryPerc", query = "SELECT s FROM Split s WHERE s.batteryPerc = :batteryPerc"),
    @NamedQuery(name = "Split.findByBatteryTime", query = "SELECT s FROM Split s WHERE s.batteryTime = :batteryTime"),
    @NamedQuery(name = "Split.findByRawValue", query = "SELECT s FROM Split s WHERE s.rawValue = :rawValue"),
})
public class Split implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    @Column(name = "stage_order")
    private Integer stageOrder;
    private String sicard;
    private String station;
    @Column(name = "reading_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date readingTime;
    @Column(name = "reading_milli")
    private BigInteger readingMilli;
    private Integer points;
    @Column(name = "bib_runner")
    private String bibRunner;
    @Column(name = "bib_team")
    private String bibTeam;
    @Column(name = "order_number")
    private Integer orderNumber;
    @Column(name = "battery_perc")
    private Integer batteryPerc;
    @Column(name = "battery_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date batteryTime;
    @Column(name = "raw_value")
    private String rawValue;
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event event;
    @JoinColumn(name = "club_id", referencedColumnName = "id")
    @ManyToOne
    private Club club;
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stage stage;
    @JoinColumn(name = "runner_result_id", referencedColumnName = "id")
    @ManyToOne
    private RunnerResult runnerResult;
    @JoinColumn(name = "team_result_id", referencedColumnName = "id")
    @ManyToOne
    private TeamResult teamResult;
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @ManyToOne
    private Clazz clazz;
    @JoinColumn(name = "control_id", referencedColumnName = "id")
    @ManyToOne
    private Control control;
    @JoinColumns({
        @JoinColumn(name = "class_id", referencedColumnName = "class_id"),
        @JoinColumn(name = "control_id", referencedColumnName = "control_id"),
        @JoinColumn(name = "id_leg", referencedColumnName = "id_leg"),
        @JoinColumn(name = "id_revisit", referencedColumnName = "id_revisit")})
    @ManyToOne
    private ClazzControl clazzControl;
    @JoinColumn(name = "runner_id", referencedColumnName = "id")
    @ManyToOne
    private Runner runner;
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    @ManyToOne
    private Team team;

    public Split() {
    }

    public Split(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStageOrder() {
        return stageOrder;
    }

    public void setStageOrder(Integer stageOrder) {
        this.stageOrder = stageOrder;
    }

    public String getSicard() {
        return sicard;
    }

    public void setSicard(String sicard) {
        this.sicard = sicard;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Date getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(Date readingTime) {
        this.readingTime = readingTime;
    }

    public BigInteger getReadingMilli() {
        return readingMilli;
    }

    public void setReadingMilli(BigInteger readingMilli) {
        this.readingMilli = readingMilli;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getBibRunner() {
        return bibRunner;
    }

    public void setBibRunner(String bibRunner) {
        this.bibRunner = bibRunner;
    }

    public String getBibTeam() {
        return bibTeam;
    }

    public void setBibTeam(String bibTeam) {
        this.bibTeam = bibTeam;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getBatteryPerc() {
        return batteryPerc;
    }

    public void setBatteryPerc(Integer batteryPerc) {
        this.batteryPerc = batteryPerc;
    }

    public Date getBatteryTime() {
        return batteryTime;
    }

    public void setBatteryTime(Date batteryTime) {
        this.batteryTime = batteryTime;
    }

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public RunnerResult getRunnerResult() {
        return runnerResult;
    }

    public void setRunnerResult(RunnerResult runnerResult) {
        this.runnerResult = runnerResult;
    }

    public TeamResult getTeamResult() {
        return teamResult;
    }

    public void setTeamResult(TeamResult teamResult) {
        this.teamResult = teamResult;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public ClazzControl getClazzControl() {
        return clazzControl;
    }

    public void setClazzControl(ClazzControl clazzControl) {
        this.clazzControl = clazzControl;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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
        if (!(object instanceof Split)) {
            return false;
        }
        Split other = (Split) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Split[ id=" + id + " ]";
    }
    
}
