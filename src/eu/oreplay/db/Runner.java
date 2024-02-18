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
@Table(name = "runners")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Runner.findAll", query = "SELECT r FROM Runner r"),
    @NamedQuery(name = "Runner.findById", query = "SELECT r FROM Runner r WHERE r.id = :id"),
    @NamedQuery(name = "Runner.findByUuid", query = "SELECT r FROM Runner r WHERE r.uuid = :uuid"),
    @NamedQuery(name = "Runner.findByFirstName", query = "SELECT r FROM Runner r WHERE r.firstName = :firstName"),
    @NamedQuery(name = "Runner.findByLastName", query = "SELECT r FROM Runner r WHERE r.lastName = :lastName"),
    @NamedQuery(name = "Runner.findByDbId", query = "SELECT r FROM Runner r WHERE r.dbId = :dbId"),
    @NamedQuery(name = "Runner.findByIofId", query = "SELECT r FROM Runner r WHERE r.iofId = :iofId"),
    @NamedQuery(name = "Runner.findByBibNumber", query = "SELECT r FROM Runner r WHERE r.bibNumber = :bibNumber"),
    @NamedQuery(name = "Runner.findByBibAlt", query = "SELECT r FROM Runner r WHERE r.bibAlt = :bibAlt"),
    @NamedQuery(name = "Runner.findBySicard", query = "SELECT r FROM Runner r WHERE r.sicard = :sicard"),
    @NamedQuery(name = "Runner.findBySicardAlt", query = "SELECT r FROM Runner r WHERE r.sicardAlt = :sicardAlt"),
    @NamedQuery(name = "Runner.findByLicense", query = "SELECT r FROM Runner r WHERE r.license = :license"),
    @NamedQuery(name = "Runner.findByNationalId", query = "SELECT r FROM Runner r WHERE r.nationalId = :nationalId"),
    @NamedQuery(name = "Runner.findByBirthDate", query = "SELECT r FROM Runner r WHERE r.birthDate = :birthDate"),
    @NamedQuery(name = "Runner.findBySex", query = "SELECT r FROM Runner r WHERE r.sex = :sex"),
    @NamedQuery(name = "Runner.findByTelephone1", query = "SELECT r FROM Runner r WHERE r.telephone1 = :telephone1"),
    @NamedQuery(name = "Runner.findByTelephone2", query = "SELECT r FROM Runner r WHERE r.telephone2 = :telephone2"),
    @NamedQuery(name = "Runner.findByEmail", query = "SELECT r FROM Runner r WHERE r.email = :email"),
    @NamedQuery(name = "Runner.findByLegNumber", query = "SELECT r FROM Runner r WHERE r.legNumber = :legNumber"),
})
public class Runner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private String id;
    private String uuid;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "db_id")
    private String dbId;
    @Column(name = "iof_id")
    private String iofId;
    @Column(name = "bib_number")
    private String bibNumber;
    @Column(name = "bib_alt")
    private String bibAlt;
    private String sicard;
    @Column(name = "sicard_alt")
    private String sicardAlt;
    private String license;
    @Column(name = "national_id")
    private String nationalId;
    @Column(name = "birth_date", nullable=true)
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private Character sex;
    private String telephone1;
    private String telephone2;
    private String email;
    @Column(name = "class_uuid")
    private String classUuid;
    @Column(name = "leg_number")
    private Integer legNumber;
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
    @OneToMany(mappedBy = "runner")
    private List<Split> splitList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "runner")
    private List<RunnerResult> runnerResultList;
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
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    @ManyToOne
    private Team team;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User user;

    public Runner() {
    }

    public Runner(String id) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getIofId() {
        return iofId;
    }

    public void setIofId(String iofId) {
        this.iofId = iofId;
    }

    public String getBibNumber() {
        return bibNumber;
    }

    public void setBibNumber(String bibNumber) {
        this.bibNumber = bibNumber;
    }

    public String getBibAlt() {
        return bibAlt;
    }

    public void setBibAlt(String bibAlt) {
        this.bibAlt = bibAlt;
    }

    public String getSicard() {
        return sicard;
    }

    public void setSicard(String sicard) {
        this.sicard = sicard;
    }

    public String getSicardAlt() {
        return sicardAlt;
    }

    public void setSicardAlt(String sicardAlt) {
        this.sicardAlt = sicardAlt;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLegNumber() {
        return legNumber;
    }

    public void setLegNumber(Integer legNumber) {
        this.legNumber = legNumber;
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

    @XmlTransient
    public List<RunnerResult> getRunnerResultList() {
        return runnerResultList;
    }

    public void setRunnerResultList(List<RunnerResult> runnerResultList) {
        this.runnerResultList = runnerResultList;
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

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof Runner)) {
            return false;
        }
        Runner other = (Runner) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Runner[ id=" + id + " ]";
    }
    
}
