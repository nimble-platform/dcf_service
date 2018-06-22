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
@Table(name = "FilteredSubscriptionChannel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FilteredSubscriptionChannel.findAll", query = "SELECT f FROM FilteredSubscriptionChannel f")
    , @NamedQuery(name = "FilteredSubscriptionChannel.findById", query = "SELECT f FROM FilteredSubscriptionChannel f WHERE f.id = :id")
    , @NamedQuery(name = "FilteredSubscriptionChannel.findByIdDataChannel", query = "SELECT f FROM FilteredSubscriptionChannel f WHERE f.idDataChannel = :idDataChannel")
    , @NamedQuery(name = "FilteredSubscriptionChannel.findByIdConsumer", query = "SELECT f FROM FilteredSubscriptionChannel f WHERE f.idConsumer = :idConsumer")
    , @NamedQuery(name = "FilteredSubscriptionChannel.findByIdGroup", query = "SELECT f FROM FilteredSubscriptionChannel f WHERE f.idGroup = :idGroup")
    , @NamedQuery(name = "FilteredSubscriptionChannel.findByFieldList", query = "SELECT f FROM FilteredSubscriptionChannel f WHERE f.fieldList = :fieldList")
    , @NamedQuery(name = "FilteredSubscriptionChannel.findByFilterValue", query = "SELECT f FROM FilteredSubscriptionChannel f WHERE f.filterValue = :filterValue")})
public class FilteredSubscriptionChannel implements Serializable {

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
    @Column(name = "idConsumer")
    private int idConsumer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idGroup")
    private int idGroup;
    @Size(max = 5000)
    @Column(name = "fieldList")
    private String fieldList;
    @Size(max = 5000)
    @Column(name = "filterValue")
    private String filterValue;

    public FilteredSubscriptionChannel() {
    }

    public FilteredSubscriptionChannel(Integer id) {
        this.id = id;
    }

    public FilteredSubscriptionChannel(Integer id, int idDataChannel, int idConsumer, int idGroup) {
        this.id = id;
        this.idDataChannel = idDataChannel;
        this.idConsumer = idConsumer;
        this.idGroup = idGroup;
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

    public int getIdConsumer() {
        return idConsumer;
    }

    public void setIdConsumer(int idConsumer) {
        this.idConsumer = idConsumer;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
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
        if (!(object instanceof FilteredSubscriptionChannel)) {
            return false;
        }
        FilteredSubscriptionChannel other = (FilteredSubscriptionChannel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nimble.dcfs.db.FilteredSubscriptionChannel[ id=" + id + " ]";
    }
    
}
