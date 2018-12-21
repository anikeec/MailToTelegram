/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram;

import com.apu.mailtotelegram.email.EmailRouteBuilder;
import com.apu.mailtotelegram.email.EmailProcessor;
import com.apu.mailtotelegram.settings.EmailSettings;
import com.apu.mailtotelegram.settings.Settings;
import com.apu.mailtotelegram.settings.TelegramSettings;
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
    
    
    
    private static Logger LOGGER = LogManager.getLogger(Main.class.getName());
    
    public static void main(String[] args) throws Exception {
        
        System.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");

        //load settings from file
        EmailSettings emailSettings = new EmailSettings();
        Settings.loadEmailSettingsFromFile(emailSettings);
        
        TelegramSettings telegramSettings = new TelegramSettings();
        Settings.loadTelegramSettingsFromFile(telegramSettings);
        
        CamelContext context = new DefaultCamelContext();        
        try {  
            Processor emailRouteProcessor = 
                    new EmailProcessor(telegramSettings);

            RouteBuilder emailRouteBuilder = 
                    new EmailRouteBuilder(emailRouteProcessor, 
                                            emailSettings); 

            context.addRoutes(emailRouteBuilder);               
            context.start();        
            while(true) {}       
        } finally {        
            context.stop();        
        }
    }
    
}
