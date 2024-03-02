/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 *
 * @author javier.arufe
 */
@Entity
@Table(name = "users_federations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserFederation.findAll", query = "SELECT u FROM UserFederation u"),
    @NamedQuery(name = "UserFederation.findByUserId", query = "SELECT u FROM UserFederation u WHERE u.userFederationPK.userId = :userId"),
    @NamedQuery(name = "UserFederation.findByFederationId", query = "SELECT u FROM UserFederation u WHERE u.userFederationPK.federationId = :federationId"),
    @NamedQuery(name = "UserFederation.findByUuidValue", query = "SELECT u FROM UserFederation u WHERE u.uuidValue = :uuidValue"),
})
@JsonRootName(value = "users_federations")
@JsonInclude(Include.NON_NULL)
public class UserFederation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserFederationPK userFederationPK;
    @Column(name = "uuid_value")
    private String uuidValue;
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
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "federation_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Federation federation;

    public UserFederation() {
    }

    public UserFederation(UserFederationPK userFederationPK) {
        this.userFederationPK = userFederationPK;
    }

    public UserFederation(String userId, String federationId) {
        this.userFederationPK = new UserFederationPK(userId, federationId);
    }

    public UserFederationPK getUserFederationPK() {
        return userFederationPK;
    }

    public void setUserFederationPK(UserFederationPK userFederationPK) {
        this.userFederationPK = userFederationPK;
    }

    @JsonProperty("uuid_value")
    public String getUuidValue() {
        return uuidValue;
    }

    public void setUuidValue(String uuidValue) {
        this.uuidValue = uuidValue;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("federation")
    public Federation getFederation() {
        return federation;
    }

    public void setFederation(Federation federation) {
        this.federation = federation;
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
        hash += (userFederationPK != null ? userFederationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserFederation)) {
            return false;
        }
        UserFederation other = (UserFederation) object;
        if ((this.userFederationPK == null && other.userFederationPK != null) || (this.userFederationPK != null && !this.userFederationPK.equals(other.userFederationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.UserFederation[ userFederationPK=" + userFederationPK + " ]";
    }
    
}
