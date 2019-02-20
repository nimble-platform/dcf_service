/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.integration.IdentityService;

import java.math.BigInteger;

/**
 *
 * @author a.musumeci
 */
public class NimbleUser {

    private String username;
    private String firstname;
    private String lastname, familyName;
    private String email;
    private BigInteger userID, id;
    private String accessToken;
    private boolean showWelcomeInfo;
    private String[] role;

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getUserID() {
        return userID;
    }

    public void setUserID(BigInteger userID) {
        this.userID = userID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isShowWelcomeInfo() {
        return showWelcomeInfo;
    }

    public void setShowWelcomeInfo(boolean showWelcomeInfo) {
        this.showWelcomeInfo = showWelcomeInfo;
    }

    
    public boolean isValidUser() {
        return (id != null || userID != null);
    }
    
}
