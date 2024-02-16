/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javier.arufe
 */
@Entity
@Table(name = "users_events")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserEvent.findAll", query = "SELECT u FROM UserEvent u"),
    @NamedQuery(name = "UserEvent.findByUserId", query = "SELECT u FROM UserEvent u WHERE u.userEventPK.userId = :userId"),
    @NamedQuery(name = "UserEvent.findByEventId", query = "SELECT u FROM UserEvent u WHERE u.userEventPK.eventId = :eventId"),
    @NamedQuery(name = "UserEvent.findByIsAdmin", query = "SELECT u FROM UserEvent u WHERE u.isAdmin = :isAdmin"),
})
public class UserEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserEventPK userEventPK;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "event_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Event event;

    public UserEvent() {
    }

    public UserEvent(UserEventPK userEventPK) {
        this.userEventPK = userEventPK;
    }

    public UserEvent(String userId, int eventId) {
        this.userEventPK = new UserEventPK(userId, eventId);
    }

    public UserEventPK getUserEventPK() {
        return userEventPK;
    }

    public void setUserEventPK(UserEventPK userEventPK) {
        this.userEventPK = userEventPK;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userEventPK != null ? userEventPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEvent)) {
            return false;
        }
        UserEvent other = (UserEvent) object;
        if ((this.userEventPK == null && other.userEventPK != null) || (this.userEventPK != null && !this.userEventPK.equals(other.userEventPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.UserEvent[ userEventPK=" + userEventPK + " ]";
    }
    
}