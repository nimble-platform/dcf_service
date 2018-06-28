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
@Table(name = "FilterGroupChannel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FilterGroupChannel.findAll", query = "SELECT f FROM FilterGroupChannel f")
    , @NamedQuery(name = "FilterGroupChannel.findById", query = "SELECT f FROM FilterGroupChannel f WHERE f.id = :id")
    , @NamedQuery(name = "FilterGroupChannel.findByIdDataChannel", query = "SELECT f FROM FilterGroupChannel f WHERE f.idDataChannel = :idDataChannel")
    , @NamedQuery(name = "FilterGroupChannel.findByIdGroupConsumer", query = "SELECT f FROM FilterGroupChannel f WHERE f.idGroupConsumer = :idGroupConsumer")
    , @NamedQuery(name = "FilterGroupChannel.findByIdGroupConsumerAndIdDataChannel", query = "SELECT f FROM FilterGroupChannel f WHERE f.idGroupConsumer = :idGroupConsumer AND f.idDataChannel = :idDataChannel")
    , @NamedQuery(name = "FilterGroupChannel.findByFieldList", query = "SELECT f FROM FilterGroupChannel f WHERE f.fieldList = :fieldList")
    , @NamedQuery(name = "FilterGroupChannel.findByFilterValue", query = "SELECT f FROM FilterGroupChannel f WHERE f.filterValue = :filterValue")})
public class FilterGroupChannel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDataChannel")
    private int idDataChannel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idGroupConsumer")
    private int idGroupConsumer;
    @Size(max = 5000)
    @Column(name = "fieldList")
    private String fieldList;
    @Size(max = 5000)
    @Column(name = "filterValue")
    private String filterValue;

    public FilterGroupChannel() {
    }

    public FilterGroupChannel(Integer id) {
        this.id = id;
    }

    public FilterGroupChannel(Integer id, int idDataChannel, int idGroupConsumer) {
        this.id = id;
        this.idDataChannel = idDataChannel;
        this.idGroupConsumer = idGroupConsumer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdDataChannel() {
        return idDataChannel;
    }

    public void setIdDataChannel(int idDataChannel) {
        this.idDataChannel = idDataChannel;
    }

    public int getIdGroupConsumer() {
        return idGroupConsumer;
    }

    public void setIdGroupConsumer(int idGroupConsumer) {
        this.idGroupConsumer = idGroupConsumer;
    }

    public String getFieldList() {
        return fieldList;
    }

    public void setFieldList(String fieldList) {
        this.fieldList = fieldList;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
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
        if (!(object instanceof FilterGroupChannel)) {
            return false;
        }
        FilterGroupChannel other = (FilterGroupChannel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nimble.dcfs.db.FilterGroupChannel[ id=" + id + " ]";
    }
    
}
