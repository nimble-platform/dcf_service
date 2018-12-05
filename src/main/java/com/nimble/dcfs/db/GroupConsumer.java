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
    , @NamedQuery(name = "GroupConsumer.findByIdUserInSubscription", query = "SELECT g FROM GroupConsumer g WHERE g.id in ( SELECT fsc.id from FilteredSubscriptionChannel fsc where fsc.idConsumer = :idConsumer ) ")
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
