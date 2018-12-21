/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram;

import com.apu.mailtotelegram.storage.SettingsStorage;
import java.util.Properties;
import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author apu
 */
public class Main {
    
    private static String EMAIL_HOST_PROPERTY = "email.host";
    private static String EMAIL_PORT_PROPERTY = "email.port";
    private static String EMAIL_USERNAME_PROPERTY = "email.username";
    private static String EMAIL_PASSWORD_PROPERTY = "email.password";
    private static String TELEGRAM_BOT_TOKEN_PROPERTY = "telegram.bot.token";
    private static String TELEGRAM_CHAT_ID_PROPERTY = "telegram.chat.id";
    
    private static Logger LOGGER = LogManager.getLogger(Main.class.getName());
    
    public static void main(String[] args) throws Exception {
        
        System.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
        
        //load settings
        Properties emailProperties = SettingsStorage.loadPropertiesFromFile();
        final String emailHost;
        final String emailPort;
        final String emailUsername;
        final String emailPassword;
        final String telegramBotToken;
        final String telegramChatId;
        
        if(emailProperties.containsKey(EMAIL_HOST_PROPERTY)) 
            emailHost = emailProperties.getProperty(EMAIL_HOST_PROPERTY);
        else
            throw new IllegalArgumentException("Needed parametes is absent.");
        
        if(emailProperties.containsKey(EMAIL_PORT_PROPERTY)) 
            emailPort = emailProperties.getProperty(EMAIL_PORT_PROPERTY);
        else
            throw new IllegalArgumentException("Needed parametes is absent.");
        
        if(emailProperties.containsKey(EMAIL_USERNAME_PROPERTY)) 
            emailUsername = emailProperties.getProperty(EMAIL_USERNAME_PROPERTY);
        else
            throw new IllegalArgumentException("Needed parametes is absent.");
        
        if(emailProperties.containsKey(EMAIL_PASSWORD_PROPERTY)) 
            emailPassword = emailProperties.getProperty(EMAIL_PASSWORD_PROPERTY);
        else
            throw new IllegalArgumentException("Needed parametes is absent.");
        
        if(emailProperties.containsKey(TELEGRAM_BOT_TOKEN_PROPERTY)) 
            telegramBotToken = emailProperties.getProperty(TELEGRAM_BOT_TOKEN_PROPERTY);
        else
            throw new IllegalArgumentException("Needed parametes is absent.");
        
        if(emailProperties.containsKey(TELEGRAM_CHAT_ID_PROPERTY)) 
            telegramChatId = emailProperties.getProperty(TELEGRAM_CHAT_ID_PROPERTY);
        else
            throw new IllegalArgumentException("Needed parametes is absent.");
        
        CamelContext context = new DefaultCamelContext();        
        try {  
            Processor emailRouteProcessor = 
                    new EmailProcessor(telegramBotToken, telegramChatId);

            RouteBuilder emailRouteBuilder = new RouteBuilder() {
                public void configure() {
                    from("pop3://" 
                            + emailHost + ":" + emailPort 
                            + "?"
                            + "username=" + emailUsername 
                            + "&password=" + emailPassword
                            + "&unseen=true"
                            + "&fetchSize=10"
                            + "&searchTerm.fromSentDate=now-24h"
//                            + "&consumer.useFixedDelay=true"
                            + "&disconnect=true"
                            + "&connectionTimeout=20000"
                            + "&consumer.initialDelay=1000"
                            + "&consumer.delay=60000"
                            + "&debugMode=true"
                    ) 
                    //.threads()
                    .log("Received a request")
//                            .to("log:emails?showAll=true&multiline=true"); 
                    .process(emailRouteProcessor);
                  }
              }; 

            context.addRoutes(emailRouteBuilder);               
            context.start();        
            while(true) {}       
        } finally {        
            context.stop();        
        }
    }
    
}
