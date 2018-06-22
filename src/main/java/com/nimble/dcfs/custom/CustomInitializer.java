package com.nimble.dcfs.custom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.nimble.dcfs.producer.DcfsProducer;
import com.nimble.dcfs.producer.DcfsProducer;

/**
 *
 * @author a.musumeci
 */
public abstract class CustomInitializer {

    public abstract String getLoginProducer();
    public abstract String getPasswordProducer();

    public abstract boolean afterStartTopic(DcfsProducer producer);
    
    
}
