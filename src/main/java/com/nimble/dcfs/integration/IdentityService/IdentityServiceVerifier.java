/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.integration.IdentityService;

import com.nimble.dcfs.util.PropertiesLoader;
import java.io.IOException;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
/**
 *
 * @author a.musumeci
 */
public class IdentityServiceVerifier {

    private Properties systemProps = PropertiesLoader.loadProperties();
    private String IdentityServiceLogin;
    private String IdentityServicePersonByToken;

    public IdentityServiceVerifier() {
        IdentityServiceLogin=  systemProps.getProperty("dcfs.IdentityServiceLogin");
        IdentityServicePersonByToken= systemProps.getProperty("dcfs.IdentityServicePersonByToken");

    }
    
    /**
     * Extracts the identity from an OpenID Connect token and fetches the associated company from the Identity Service.
     * @param accessToken OpenID Connect token storing identity.
     * @return Identifier of associated company
     * @throws UnirestException Error while communicating with the Identity Service.
     */
    public NimbleUser verifyNimbleUser(String accessToken)  {
        NimbleUser nimbleUser = null;
        // obtain extended user information
        
        try {
            String jsonUser = Unirest.get(IdentityServicePersonByToken)
                .header("Authorization", accessToken).asJson().getBody().toString();
            
            Gson gson = new Gson();
            nimbleUser = (NimbleUser) gson.fromJson( jsonUser, NimbleUser.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
            
        return nimbleUser;
    }    
    
    
}
