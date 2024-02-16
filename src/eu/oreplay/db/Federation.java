/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "federations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Federation.findAll", query = "SELECT f FROM Federation f"),
    @NamedQuery(name = "Federation.findById", query = "SELECT f FROM Federation f WHERE f.id = :id"),
    @NamedQuery(name = "Federation.findByDescription", query = "SELECT f FROM Federation f WHERE f.description = :description"),
})
public class Federation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private String id;
    private String description;
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

    @XmlTransient
    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    @XmlTransient
    public List<UserFederation> getUserFederationList() {
        return userFederationList;
    }

    public void setUserFederationList(List<UserFederation> userFederationList) {
        this.userFederationList = userFederationList;
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
        if (!(object instanceof Federation)) {
            return false;
        }
        Federation other = (Federation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.Federation[ id=" + id + " ]";
    }
    
}