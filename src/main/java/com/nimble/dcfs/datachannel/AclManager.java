/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.datachannel;

import com.nimble.dcfs.db.Channel;
import com.nimble.dcfs.datachannel.DataChannelManager;
import com.nimble.dcfs.db.User;
import com.nimble.dcfs.db.*;
import com.nimble.dcfs.util.PropertiesLoader;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author a.musumeci
 */
public class AclManager {
    Properties props = PropertiesLoader.loadProperties();
    DataChannelManager dataChannelManager = new DataChannelManager();
    
    
    public boolean verifyAdminDcfs(String user, String password) {
        String adminUser = props.getProperty("dcfs.admin.login");
        String adminPassword = props.getProperty("dcfs.admin.password");
        if ( adminUser==null || adminPassword==null) return true;
        
        return ( adminUser.equals(user) && adminPassword.equals(password) );
    }
    
    public User getProducer(String login, String password) {
        List<User> users = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("User.findByLoginPasswordProducer").setParameter("login", login).setParameter("password", password).getResultList();
        if (users.size()>0)
        return users.get(0);
        else return null;
    }

    public User getProducer(String producerNamespace) {
        List<User> users = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("User.findByProducerNamespace").setParameter("producerNamespace", producerNamespace).getResultList();
        if (users.size()>0)
        return users.get(0);
        else return null;
    }

    public List<User> getProducerList() {
            List<User> producerList = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("User.findAllProducer").getResultList();
            return producerList;
        }

    
     public User getUser(Integer idUser) {
        List<User> users = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("User.findById").setParameter("id", idUser).getResultList();
        if (users.size()>0)
        return users.get(0);
        else return null;
    }

    public User getConsumer(String login, String password) {
        List<User> users = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("User.findByLoginPasswordConsumer").setParameter("login", login).setParameter("password", password).getResultList();
        if (users.size()>0)
        return users.get(0);
        else return null;
    }

    public ArrayList<String> getAvaiableTopics( Integer userId ) {
                    ArrayList<String> avaiableTopics = new ArrayList();
                    User user =  getUser(userId);
                    if ( user!=null && user.getIsProducer()==1) {
                        List<Channel> channels = dataChannelManager.getProducerChannel(userId);
                        Iterator<Channel> i = channels.iterator();
                        while (i.hasNext()) {
                            avaiableTopics.add(
                               user.getProducerNamespace().toUpperCase()+"_"+i.next().getTopicname()
                            );
                        }

                    }
                    return avaiableTopics;
    }
    
    
}
