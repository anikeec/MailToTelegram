/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.apu.mailtotelegram.email.EmailProcessor;
import com.apu.mailtotelegram.email.EmailRouteBuilder;
import com.apu.mailtotelegram.email.TelegramProcessor;
import com.apu.mailtotelegram.error.ErrorProcessor;
import com.apu.mailtotelegram.settings.EmailSettings;
import com.apu.mailtotelegram.settings.Settings;
import com.apu.mailtotelegram.settings.TelegramSettings;
import com.apu.mailtotelegram.utils.log.Logging;

/**
 *
 * @author apu
 */
public class Email2Telegram {
    
    private static Logger LOGGER = LogManager.getLogger(Email2Telegram.class.getName());
    private Main main;
    
    public static void main(String[] args) throws Exception {
        Email2Telegram application = new Email2Telegram();
        application.start();
    }
    
    public void start() throws Exception {
        
        System.setOut(Logging.createLoggingProxy(System.out));
        
        System.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");

        //load settings from file
        EmailSettings emailSettings = new EmailSettings();
        Settings.loadEmailSettingsFromFile(emailSettings);
        
        TelegramSettings telegramSettings = new TelegramSettings();
        Settings.loadTelegramSettingsFromFile(telegramSettings);
        
        main = new Main();
        
//        CamelContext context = new DefaultCamelContext();         
        Processor emailRouteProcessor = 
                new EmailProcessor();
        Processor telegramRouteProcessor = 
                    new TelegramProcessor(telegramSettings);
            
        Processor errorProcessor = new ErrorProcessor();
            
        String fromUri = 
                    "pop3://" 
                    + emailSettings.emailHost + ":" + emailSettings.emailPort 
                    + "?"
                    + "username=" + emailSettings.emailUsername 
                    + "&password=" + emailSettings.emailPassword
                    + "&unseen=true"
                    + "&fetchSize=5"
                    + "&searchTerm.fromSentDate=now-24h"
                    + "&disconnect=true"
                    + "&connectionTimeout=20000"
                    + "&consumer.initialDelay=1000"
                    + "&consumer.delay=600000"
                    + "&debugMode=true";
            
        String toUri = "telegram:bots/" + telegramSettings.telegramBotToken;

        RouteBuilder emailRouteBuilder = 
                    new EmailRouteBuilder(fromUri,
                                            emailRouteProcessor,  
                                            telegramRouteProcessor,
                                            toUri,
                                            errorProcessor); 

        main.addRouteBuilder(emailRouteBuilder);
        main.addMainListener(new Events());              
        main.run();                   
    }
    
}