/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author javier.arufe
 */
@Embeddable
public class UserFederationPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;
    @Basic(optional = false)
    @Column(name = "federation_id")
    private String federationId;

    public UserFederationPK() {
    }

    public UserFederationPK(String userId, String federationId) {
        this.userId = userId;
        this.federationId = federationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFederationId() {
        return federationId;
    }

    public void setFederationId(String federationId) {
        this.federationId = federationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        hash += (federationId != null ? federationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean vbResul = true;
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserFederationPK)) {
            vbResul = false;
        } else {
            UserFederationPK other = (UserFederationPK) object;
            if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
                vbResul = false;
            }
            if ((this.federationId == null && other.federationId != null) || (this.federationId != null && !this.federationId.equals(other.federationId))) {
                vbResul = false;
            }
        }
        return vbResul;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.UserFederationPK[ userId=" + userId + ", federationId=" + federationId + " ]";
    }
    
}
