package com.nimble.dcfs;



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

@ApplicationPath("/")
@Path("/")
@Singleton
public class DcfsMainRest extends Application implements ServletContextListener {
    private final Logger logger = Logger.getLogger(DcfsMainRest.class);
    static DcfsInitializer dcfsInitializer = new DcfsInitializer();
    private static Properties props = PropertiesLoader.loadProperties();

    static {
        dcfsInitializer = new DcfsInitializer();
        boolean autoinitialize = Boolean.parseBoolean(props.getProperty("dcfs.admin.autoinitialize"));
        if (autoinitialize) {
            dcfsInitializer.restartDcfs();
        }
    }
    
    
    @GET
    @Path("/admin/restartSystem/{user}/{password}")
    public String restartSystem(@PathParam("user") String user, @PathParam("password") String password) {  //in future better a post
        AclManager aclManager = new AclManager();
        if (aclManager.verifyAdminDcfs(user, password)) {
            dcfsInitializer.restartDcfs();
            return "Dcf-Service restarted";
        }
        return "Dcf-Service not restarted";
    }

    @GET
    @Path("/admin/startCustomInitializer/{user}/{password}")
    public String startCostomInitializer(@PathParam("user") String user, @PathParam("password") String password) {  //in future better a post
        AclManager aclManager = new AclManager();
        if (aclManager.verifyAdminDcfs(user, password)) {
            dcfsInitializer.runCustomInitializer();
            return "Dcf-Service CustomInitializer started";
        }
        return "Dcf-Service CustomInitializer not started";
    }

    @GET
    public String hello() {
        return "Dcf-Service ( enabledTopics: "+dcfsInitializer.getEnabledTopics()+" enabledStreams"+dcfsInitializer.getEnabledStreams()+" )";
    }

    public DcfsMainRest() throws Exception {
        logger.info("DCFS in build main");
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("DCFS Running on created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("DCFS Shutting down");
    }

}
