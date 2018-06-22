/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author a.musumeci
 */
public class DcfsEntityManagerFactory {

    static EntityManager em = null;

    public static EntityManager createEntityManager() {
            if (em == null) {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.nimble.dcfs.PERSISTENCE");
                em = emf.createEntityManager();
            }
            return em;
    }
   
}
