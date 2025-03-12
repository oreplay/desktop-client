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
public class UserEventPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;
    @Basic(optional = false)
    @Column(name = "event_id")
    private String eventId;

    public UserEventPK() {
    }

    public UserEventPK(String userId, String eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean vbResul = true;
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEventPK)) {
            vbResul = false;
        }
        UserEventPK other = (UserEventPK) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            vbResul = false;
        }
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            vbResul = false;
        }
        return vbResul;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.UserEventPK[ userId=" + userId + ", eventId=" + eventId + " ]";
    }
    
}
