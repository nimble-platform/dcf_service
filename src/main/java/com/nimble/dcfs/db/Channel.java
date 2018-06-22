/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author a.musumeci
 */
@Entity
@Table(name = "Channel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Channel.findAll", query = "SELECT c FROM Channel c")
    , @NamedQuery(name = "Channel.findById", query = "SELECT c FROM Channel c WHERE c.id = :id")
    , @NamedQuery(name = "Channel.findByIdProducer", query = "SELECT c FROM Channel c WHERE c.idProducer = :idProducer")
    })
public class Channel implements Serializable {

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
    @Column(name = "topicname")
    private String topicname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDataFormat")
    private int idDataFormat;
    @Size(max = 5000)
    @Column(name = "fieldTypeList")
    private String fieldTypeList;
    @Size(max = 45)
    @Column(name = "key")
    private String key;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1200)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "openSince")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openSince;

    public Channel() {
    }

    public Channel(Integer id) {
        this.id = id;
    }

    public Channel(Integer id, int idProducer, String topicname, int idDataFormat, String description, Date openSince) {
        this.id = id;
        this.idProducer = idProducer;
        this.topicname = topicname;
        this.idDataFormat = idDataFormat;
        this.description = description;
        this.openSince = openSince;
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

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public int getIdDataFormat() {
        return idDataFormat;
    }

    public void setIdDataFormat(int idDataFormat) {
        this.idDataFormat = idDataFormat;
    }

    public String getFieldTypeList() {
        return fieldTypeList;
    }

    public void setFieldTypeList(String fieldTypeList) {
        this.fieldTypeList = fieldTypeList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOpenSince() {
        return openSince;
    }

    public void setOpenSince(Date openSince) {
        this.openSince = openSince;
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
        if (!(object instanceof Channel)) {
            return false;
        }
        Channel other = (Channel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nimble.dcfs.db.Channel[ id=" + id + "" + topicname + "" + description + " ]";
    }
    
}
