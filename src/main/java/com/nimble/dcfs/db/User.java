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
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findAllProducer", query = "SELECT u FROM User u where u.isProducer=1")
    , @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id")
    , @NamedQuery(name = "User.findByIdNimbleUser", query = "SELECT u FROM User u WHERE u.idNimbleUser = :idNimbleUser")
    , @NamedQuery(name = "User.findByBrand", query = "SELECT u FROM User u WHERE u.brand = :brand")
    , @NamedQuery(name = "User.findByLoginPasswordProducer", query = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password AND u.isProducer=1")
    , @NamedQuery(name = "User.findByLoginPasswordConsumer", query = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password AND u.isConsumer=1")
    , @NamedQuery(name = "User.findByIsProducer", query = "SELECT u FROM User u WHERE u.isProducer = :isProducer")
    , @NamedQuery(name = "User.findByIsConsumer", query = "SELECT u FROM User u WHERE u.isConsumer = :isConsumer")
    , @NamedQuery(name = "User.findByProducerNamespace", query = "SELECT u FROM User u WHERE u.producerNamespace = :producerNamespace")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idNimbleUser")
    private int idNimbleUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "brand")
    private String brand;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isProducer")
    private int isProducer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isConsumer")
    private int isConsumer;
    @Size(max = 250)
    @Column(name = "producerNamespace")
    private String producerNamespace;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, int idNimbleUser, String brand, String login, String password, int isProducer, int isConsumer) {
        this.id = id;
        this.idNimbleUser = idNimbleUser;
        this.brand = brand;
        this.login = login;
        this.password = password;
        this.isProducer = isProducer;
        this.isConsumer = isConsumer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdNimbleUser() {
        return idNimbleUser;
    }

    public void setIdNimbleUser(int idNimbleUser) {
        this.idNimbleUser = idNimbleUser;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsProducer() {
        return isProducer;
    }

    public void setIsProducer(int isProducer) {
        this.isProducer = isProducer;
    }

    public int getIsConsumer() {
        return isConsumer;
    }

    public void setIsConsumer(int isConsumer) {
        this.isConsumer = isConsumer;
    }

    public String getProducerNamespace() {
        return producerNamespace;
    }

    public void setProducerNamespace(String producerNamespace) {
        this.producerNamespace = producerNamespace;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nimble.dcfs.db.User[ id=" + id + " ]";
    }
    
}
