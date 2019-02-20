/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.integration.identityService;

import com.nimble.dcfs.integration.IdentityService.IdentityServiceVerifier;
import com.nimble.dcfs.integration.IdentityService.NimbleUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 *
 * @author a.musumeci
 */
public class IdentityServiceVerifierTest {
    
    
    public IdentityServiceVerifierTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of recreateDcfsTopics method, of class TopicManager.
     */
    @Test
    public void testVerifyNimbleUser() {
        String autorization="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ6cFp2LVlNTlJfRURaeEJDN0tRTmw0VEVqektpcUJnNzJhY0JHZjlCbVFVIn0.eyJqdGkiOiJkMDliNTFlYS1hOTI0LTRmMzctOWZiYi00ZDAxNjc0MjQ1ZWYiLCJleHAiOjE1NTAyMzMxNzIsIm5iZiI6MCwiaWF0IjoxNTUwMjI5NTcyLCJpc3MiOiJodHRwOi8va2V5Y2xvYWs6ODA4MC9hdXRoL3JlYWxtcy9tYXN0ZXIiLCJhdWQiOiJuaW1ibGVfY2xpZW50Iiwic3ViIjoiOGI0NDBhNGUtNTZhOC00MmU3LTk3ZDMtYTJkNWNmNjk0MzIzIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoibmltYmxlX2NsaWVudCIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6ImY1NmI0YjY5LTIyOGItNDM2OC1hNWI1LTBiMjM5YTQ3ODYxNCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOltdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsibmltYmxlX3VzZXIiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sIm5hbWUiOiJhbmRyZWEgcGVyc29uYSBtdXN1bWVjaSIsInByZWZlcnJlZF91c2VybmFtZSI6Im11c3VtZWNpLmhvbG9uaXgrMUBnbWFpbC5jb20iLCJnaXZlbl9uYW1lIjoiYW5kcmVhIHBlcnNvbmEiLCJmYW1pbHlfbmFtZSI6Im11c3VtZWNpIiwiZW1haWwiOiJtdXN1bWVjaS5ob2xvbml4KzFAZ21haWwuY29tIn0.gBnPrfbtVmgwSDIZcOZpOlNglesktHwtTf3dw1oGjnVOCWivIX-phWcU6pbr3fBrMql3AgPEFGHRDhDXXW0Ag1sY09ht6nyZQWtQE9abjARPwO6By69kbZ0WC8R5nK3TkaAJOnFn4Fiutm7x2nn2bQDxjaKZf7QzHRic7V129eSo1IgfsqNAlF2UGLtuVbB-H65uRamZl5xFXi8bgQ6uzohcs4LWd9eaQobiOH2J21toHJ35QqUy8lfQyEiMEIf2ZiW_gCxn2ZPr9n4mWR71P_Ihh6cKA6SLZPLyi0TJK3pj0V0ujHhrdLjssJ2pmrO3qZFPz7bPhvDY_uxZ4raItA";
        //String autorization="errrr";
        NimbleUser user = null;
        
        IdentityServiceVerifier isv = new IdentityServiceVerifier();
        user = isv.verifyNimbleUser(autorization);
        //assertNotNull(user);
        //assertNotEquals(new Boolean(user.isValidUser()),Boolean.FALSE);
        
    }

    
}
