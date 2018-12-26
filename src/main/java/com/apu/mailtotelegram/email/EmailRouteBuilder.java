/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram.email;

import com.apu.mailtotelegram.error.ErrorProcessor;
import com.apu.mailtotelegram.settings.EmailSettings;
import com.apu.mailtotelegram.settings.TelegramSettings;
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
    
    private final Processor emailRouteProcessor;
    private final EmailSettings emailSettings;
    private final TelegramSettings telegramSettings;

    public EmailRouteBuilder(Processor emailRouteProcessor, 
                                EmailSettings emailSettings,
                                TelegramSettings telegramSettings) {
        this.emailRouteProcessor = emailRouteProcessor;
        this.emailSettings = emailSettings;
        this.telegramSettings = telegramSettings;
    }    

    @Override
    public void configure() throws Exception {
        
        onException(Exception.class)
                .process(new ErrorProcessor())
                .handled(true);
        
        from("pop3://" 
                + emailSettings.emailHost + ":" + emailSettings.emailPort 
                + "?"
                + "username=" + emailSettings.emailUsername 
                + "&password=" + emailSettings.emailPassword
                + "&unseen=true"
                + "&fetchSize=100"
                + "&searchTerm.fromSentDate=now-24h"
                + "&disconnect=true"
                + "&connectionTimeout=20000"
                + "&consumer.initialDelay=1000"
                + "&consumer.delay=600000"
                + "&debugMode=true"
        ) 
        .log("Received a request") 
        .process(emailRouteProcessor)
        .setHeader("CamelTelegramChatId", constant(telegramSettings.telegramChatId))
        .inOnly("telegram:bots/" + telegramSettings.telegramBotToken);
    }
    
}
