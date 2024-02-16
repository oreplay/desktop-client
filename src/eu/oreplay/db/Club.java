/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author javier.arufe
 */
@Entity
@Table(name = "clubs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Club.findAll", query = "SELECT c FROM Club c"),
    @NamedQuery(name = "Club.findById", query = "SELECT c FROM Club c WHERE c.id = :id"),
    @NamedQuery(name = "Club.findByUuid", query = "SELECT c FROM Club c WHERE c.uuid = :uuid"),
    @NamedQuery(name = "Club.findByOeKey", query = "SELECT c FROM Club c WHERE c.oeKey = :oeKey"),
    @NamedQuery(name = "Club.findByShortName", query = "SELECT c FROM Club c WHERE c.shortName = :shortName"),
    @NamedQuery(name = "Club.findByLongName", query = "SELECT c FROM Club c WHERE c.longName = :longName"),
    @NamedQuery(name = "Club.findByCity", query = "SELECT c FROM Club c WHERE c.city = :city"),
})
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    private String uuid;
    @Column(name = "oe_key")
    private String oeKey;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "long_name")
    private String longName;
    private String city;
    @Lob
    private byte[] logo;
    @OneToMany(mappedBy = "club")
    private List<Split> splitList;
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Event event;
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stage stage;
    @OneToMany(mappedBy = "club")
    private List<Team> teamList;
    @OneToMany(mappedBy = "club")
    private List<Runner> runnerList;

    public Club() {
    }

    public Club(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOeKey() {
        return oeKey;
    }

    public void setOeKey(String oeKey) {
        this.oeKey = oeKey;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
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

    @XmlTransient
    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
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
        if (!(object instanceof Club)) {
            return false;
        }
        Club other = (Club) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Club[ id=" + id + " ]";
    }
    
}
