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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "federations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Federation.findAll", query = "SELECT f FROM Federation f"),
    @NamedQuery(name = "Federation.findById", query = "SELECT f FROM Federation f WHERE f.id = :id"),
    @NamedQuery(name = "Federation.findByDescription", query = "SELECT f FROM Federation f WHERE f.description = :description"),
})
@JsonRootName(value = "federations")
@JsonInclude(Include.NON_NULL)
public class Federation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private String id;
    private String description;
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
    @OneToMany(mappedBy = "federation")
    private List<Event> eventList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "federation")
    private List<UserFederation> userFederationList;

    public Federation() {
    }

    public Federation(String id) {
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

    @JsonProperty("events")
    @XmlTransient
    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    @JsonProperty("users_federations")
    @XmlTransient
    public List<UserFederation> getUserFederationList() {
        return userFederationList;
    }

    public void setUserFederationList(List<UserFederation> userFederationList) {
        this.userFederationList = userFederationList;
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
        if (!(object instanceof Federation)) {
            vbResul = false;
        }
        Federation other = (Federation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            vbResul = false;
        }
        return vbResul;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Federation[ id=" + id + " ]";
    }
    
}
