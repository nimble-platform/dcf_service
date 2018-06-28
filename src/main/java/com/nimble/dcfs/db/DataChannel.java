/*
 * Copyright 2018 a.musumeci.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
@Table(name = "DataChannel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataChannel.findAll", query = "SELECT d FROM DataChannel d")
    , @NamedQuery(name = "DataChannel.findById", query = "SELECT d FROM DataChannel d WHERE d.id = :id")
    , @NamedQuery(name = "DataChannel.findByIdChannel", query = "SELECT d FROM DataChannel d WHERE d.idChannel = :idChannel")
    , @NamedQuery(name = "DataChannel.findByIdProducerAndDataChannelName", query = "SELECT d FROM DataChannel d WHERE d.name = :dataChannelName and idChannel in (SELECT c.id FROM Channel c WHERE c.idProducer = :idProducer)")
    })

public class DataChannel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idChannel")
    private int idChannel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 5000)
    @Column(name = "fieldList")
    private String fieldList;
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

    public DataChannel() {
    }

    public DataChannel(Integer id) {
        this.id = id;
    }

    public DataChannel(Integer id, int idChannel, String name, String description, Date openSince) {
        this.id = id;
        this.idChannel = idChannel;
        this.name = name;
        this.description = description;
        this.openSince = openSince;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(int idChannel) {
        this.idChannel = idChannel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldList() {
        return fieldList;
    }

    public void setFieldList(String fieldList) {
        this.fieldList = fieldList;
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
        if (!(object instanceof DataChannel)) {
            return false;
        }
        DataChannel other = (DataChannel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nimble.dcfs.db.DataChannel[ id=" + id + " ]";
    }
    
}
