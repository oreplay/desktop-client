/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.oreplay.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "result_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResultType.findAll", query = "SELECT r FROM ResultType r"),
    @NamedQuery(name = "ResultType.findById", query = "SELECT r FROM ResultType r WHERE r.id = :id"),
    @NamedQuery(name = "ResultType.findByDescription", query = "SELECT r FROM ResultType r WHERE r.description = :description"),
})
public class ResultType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer id;
    private String description;
    @OneToMany(mappedBy = "resultType")
    private List<TeamResult> teamResultList;
    @OneToMany(mappedBy = "resultType")
    private List<RunnerResult> runnerResultList;

    public ResultType() {
    }

    public ResultType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<TeamResult> getTeamResultList() {
        return teamResultList;
    }

    public void setTeamResultList(List<TeamResult> teamResultList) {
        this.teamResultList = teamResultList;
    }

    @XmlTransient
    public List<RunnerResult> getRunnerResultList() {
        return runnerResultList;
    }

    public void setRunnerResultList(List<RunnerResult> runnerResultList) {
        this.runnerResultList = runnerResultList;
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
        if (!(object instanceof ResultType)) {
            return false;
        }
        ResultType other = (ResultType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.oreplay.db.ResultType[ id=" + id + " ]";
    }
    
}
