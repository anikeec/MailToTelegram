/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram.error;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author apu
 */
public class ErrorProcessor implements Processor {
    
    private static Logger LOGGER = LogManager.getLogger(ErrorProcessor.class.getName());

    @Override
    public void process(Exchange exchange) throws Exception {  
        Exception cause = 
                exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        
        if(cause != null){
            LOGGER.error("Error has occurred: ", cause);
            // Sending Error message to client
            //exchange.getOut().setBody("Error");
        } else {
            // Sending response message to client
            //exchange.getOut().setBody("Good");
        }
    }
    
}
