/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram.email;

import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author apu
 */
public class EmailRouteBuilder extends RouteBuilder {
    
    private final static Logger LOGGER = 
            LogManager.getLogger(EmailRouteBuilder.class.getName());
    
    private final Processor fromRouteProcessor;
    private final Processor errorProcessor;
    private final Processor toRouteProcessor;
    private final String fromUri;
    private final String toUri;

    public EmailRouteBuilder(String fromUri,
                                Processor fromRouteProcessor,                           
                                Processor toRouteProcessor,
                                String toUri,
                                Processor errorProcessor) {
        this.fromRouteProcessor = fromRouteProcessor;
        this.errorProcessor = errorProcessor;
        this.toRouteProcessor = toRouteProcessor;
        this.fromUri = fromUri;
        this.toUri = toUri;
    }    

    @Override
    public void configure() throws Exception {
        
        onException(Exception.class)
                .process(errorProcessor)
                .log(LoggingLevel.ERROR, "Received body - ${body}")
                .log(LoggingLevel.ERROR, "Exception message - ${exception.message}")
                .log(LoggingLevel.ERROR, "Exception stacktrace - ${exception.stacktrace}")
                .handled(true);
        
        from(fromUri) 
        .log("Received a request") 
        .process(fromRouteProcessor)
        .process(toRouteProcessor)
        .inOnly(toUri);
    }
    
}
