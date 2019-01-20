/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.apu.mailtotelegram.email.EmailProcessor;
import com.apu.mailtotelegram.email.EmailRouteBuilder;
import com.apu.mailtotelegram.email.TelegramProcessor;
import com.apu.mailtotelegram.error.ErrorProcessor;
import com.apu.mailtotelegram.settings.entity.EmailSettings;
import com.apu.mailtotelegram.settings.Settings;
import com.apu.mailtotelegram.settings.entity.TelegramSettings;
import com.apu.mailtotelegram.utils.log.Logging;

/**
 *
 * @author apu
 */
public class Email2Telegram {
    
    private static final Logger LOGGER = 
                    LogManager.getLogger(Email2Telegram.class.getName());
    private static final long APP_RESTART_DELAY = 10000;
    private static final int RESTART_MAX_AMOUNT = 10;
    
    public static void main(String[] args) {
        Email2Telegram application = new Email2Telegram();
        int restartMaxAmount = 0;
        while(restartMaxAmount < RESTART_MAX_AMOUNT) {
            try {
                application.start();
            } catch (Exception e) {
                LOGGER.error(ExceptionUtils.getStackTrace(e));
                LOGGER.debug("Delay before restart application");
                try {
                    Thread.sleep(APP_RESTART_DELAY);
                } catch (InterruptedException e1) {
                    LOGGER.debug("Restart delay was interrupted.");
                }
                restartMaxAmount++;
                LOGGER.debug("Try to restart application. Attempt " + restartMaxAmount);
            }
        }
        LOGGER.error("Application has been restarted " + restartMaxAmount +  " times. "
                + "So the application will be shutting down.");
    }
    
    public void start() throws Exception {
        
        System.setOut(Logging.createLoggingProxy(System.out));
        
        System.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");

        //load settings from file
        EmailSettings emailSettings = new EmailSettings();
        TelegramSettings telegramSettings = new TelegramSettings();
        Settings.loadEmailSettingsFromFile(emailSettings);
        Settings.loadTelegramSettingsFromFile(telegramSettings);    
        
        
        Main main = new Main();
              
        Processor emailRouteProcessor = 
                new EmailProcessor();
        Processor telegramRouteProcessor = 
                    new TelegramProcessor(telegramSettings);
            
        Processor errorProcessor = new ErrorProcessor();
            
        String fromUri = 
                    "pop3://" 
                    + emailSettings.getHost() 
                    + ":" 
                    + emailSettings.getPort()
                    + "?"
                    + "username=" + emailSettings.getUsername()
                    + "&password=" + emailSettings.getPassword()
                    + "&unseen=true"
                    + "&fetchSize=5"
                    + "&searchTerm.fromSentDate=now-24h"
                    + "&disconnect=true"
                    + "&connectionTimeout=20000"
                    + "&consumer.initialDelay=1000"
                    + "&consumer.delay=600000"
                    + "&debugMode=true";
            
        String toUri = "telegram:bots/" + telegramSettings.getBotToken();

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
