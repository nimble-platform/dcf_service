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
 
package com.nimble.dcfs.admin;



import org.apache.log4j.Logger;

import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import com.nimble.dcfs.datachannel.AclManager;
import com.nimble.dcfs.util.PropertiesLoader;
import java.util.Properties;
import javax.ws.rs.PathParam;

/**
 * Admin services: credentials are defined in dcfs.properties file
 * @author a.musumeci
 */
@Path("/admin")
@Singleton
public class DcfsAdminRest extends Application implements ServletContextListener {
    private final Logger logger = Logger.getLogger(DcfsAdminRest.class);
    static DcfsInitializer dcfsInitializer = new DcfsInitializer();
    private static Properties props = PropertiesLoader.loadProperties();

    static {
        dcfsInitializer = new DcfsInitializer();
        boolean autoinitialize = Boolean.parseBoolean(props.getProperty("dcfs.admin.autoinitialize"));
        if (autoinitialize) {
            dcfsInitializer.restartDcfs();
        }
    }
    
    /**
     * Restart the service when something has changed in persistent configuration on DB
     * 
     * @param userAdmin
     * @param passwordAdmin
     * @return
     */
    @GET
    @Path("/restartSystem/{userAdmin}/{passwordAdmin}")
    public String restartSystem(@PathParam("userAdmin") String userAdmin, @PathParam("passwordAdmin") String passwordAdmin) {
        AclManager aclManager = new AclManager();
        if (aclManager.verifyAdminDcfs(userAdmin, passwordAdmin)) {
            dcfsInitializer.restartDcfs();
            return "Dcf-Service restarted";
        }
        return "Dcf-Service not restarted";
    }

    /**
     *
     * @throws Exception
     */
    public DcfsAdminRest() throws Exception {
        logger.info("DCFS in build main");
    }

    /**
     *
     * @param servletContextEvent
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("DCFS Running on created");
    }

    /**
     *
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("DCFS Shutting down");
    }

}
