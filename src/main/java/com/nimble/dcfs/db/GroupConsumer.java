/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author a.musumeci
 */
@Entity
@Table(name = "GroupConsumer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupConsumer.findAll", query = "SELECT g FROM GroupConsumer g")
    , @NamedQuery(name = "GroupConsumer.findById", query = "SELECT g FROM GroupConsumer g WHERE g.id = :id")
    , @NamedQuery(name = "GroupConsumer.findByIdProducer", query = "SELECT g FROM GroupConsumer g WHERE g.idProducer = :idProducer")
    , @NamedQuery(name = "GroupConsumer.findByDescription", query = "SELECT g FROM GroupConsumer g WHERE g.description = :description")})
public class GroupConsumer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idProducer")
    private int idProducer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "description")
    private String description;

    public GroupConsumer() {
    }

    public GroupConsumer(Integer id) {
        this.id = id;
    }

    public GroupConsumer(Integer id, int idProducer, String description) {
        this.id = id;
        this.idProducer = idProducer;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(int idProducer) {
        this.idProducer = idProducer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof GroupConsumer)) {
            return false;
        }
        GroupConsumer other = (GroupConsumer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nimble.dcfs.db.GroupConsumer[ id=" + id + " ]";
    }
    
}
