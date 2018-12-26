/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram.email;

import com.apu.mailtotelegram.settings.TelegramSettings;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import static org.apache.camel.builder.Builder.constant;

/**
 *
 * @author apu
 */
public class TelegramProcessor implements Processor {
    
    TelegramSettings telegramSettings;

    public TelegramProcessor(TelegramSettings telegramSettings) {
        this.telegramSettings = telegramSettings;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeader("CamelTelegramChatId", 
                        constant(telegramSettings.telegramChatId));
    }
    
}
