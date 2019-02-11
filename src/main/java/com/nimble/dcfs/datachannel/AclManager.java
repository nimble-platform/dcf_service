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
 
package com.nimble.dcfs.datachannel;

import com.nimble.dcfs.db.*;
import com.nimble.dcfs.util.PropertiesLoader;
import java.util.Properties;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Manger which controls access to message Stream using Db persisted rules
 * @author a.musumeci
 */
public class AclManager {
    Properties props = PropertiesLoader.loadProperties();
    
    /**
     *
     * @param user
     * @param password
     * @return
     */
    public boolean verifyAdminDcfs(String user, String password) {
        String adminUser = props.getProperty("dcfs.admin.login");
        String adminPassword = props.getProperty("dcfs.admin.password");
        if ( adminUser==null || adminPassword==null) return true;
        
        return ( adminUser.equals(user) && adminPassword.equals(password) );
    }
    
    /**
     *
     * @param login
     * @param password
     * @return
     */
    public User getProducer(String login, String password) {
        EntityManager em = DcfsEntityManagerFactory.createEntityManager();
        List<User> users = em.createNamedQuery("User.findByLoginPasswordProducer").setParameter("login", login).setParameter("password", password).getResultList();
        em.close();
        if (users.size()>0)
        return users.get(0);
        else return null;
    }

    /**
     *
     * @param login
     * @param password
     * @return
     */
    public List<User> getProducerForConsumer(Integer idConsumer) {
        EntityManager em = DcfsEntityManagerFactory.createEntityManager();
        List<User> users = em.createNamedQuery("User.findByIdConsumer").setParameter("idconsumer", idConsumer).getResultList();
        em.close();
        return users;
    }

    /**
     *
     * @param producerNamespace
     * @return
     */
    public User getProducer(String producerNamespace) {
        EntityManager em = DcfsEntityManagerFactory.createEntityManager();
        List<User> users = em.createNamedQuery("User.findByProducerNamespace").setParameter("producerNamespace", producerNamespace).getResultList();
        em.close();
        if (users.size()>0)
        return users.get(0);
        else return null;
    }

    /**
     *
     * @return
     */
    public List<User> getProducerList() {
            EntityManager em = DcfsEntityManagerFactory.createEntityManager();
            List<User> producerList = em.createNamedQuery("User.findAllProducer").getResultList();
            em.close();
            return producerList;
        }

    /**
     *
     * @param idUser
     * @return
     */
    User getUser(Integer idUser) {
            EntityManager em = DcfsEntityManagerFactory.createEntityManager();
        List<User> users = em.createNamedQuery("User.findById").setParameter("id", idUser).getResultList();
            em.close();
        if (users.size()>0)
        return users.get(0);
        else return null;
    }

    /**
     *
     * @param login
     * @param password
     * @return
     */
    public User getConsumer(String login, String password) {
        User user=null;
        EntityManager em = DcfsEntityManagerFactory.createEntityManager();
        List<User> users = em.createNamedQuery("User.findByLoginPasswordConsumer").setParameter("login", login).setParameter("password", password).getResultList();
        if (users.size()>0) {
            user = users.get(0);
            List<GroupConsumer> groupConsumer = em.createNamedQuery("GroupConsumer.findByIdUserInSubscription").setParameter("idConsumer", user.getId()).getResultList();
            user.setGroupConsumer(groupConsumer);
        } 
        em.close();
        return user;
        
    }

}
