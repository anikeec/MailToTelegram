/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram.telegram;

import com.apu.mailtotelegram.settings.TelegramSettings;
import org.apache.camel.ProducerTemplate;

/**
 *
 * @author apu
 */
public class TelegramBot {
    
    public static void send(ProducerTemplate template, 
                            TelegramSettings settings,
                            String message) {
        Object response = 
                template.requestBodyAndHeader(
                        "telegram:bots/" + settings.telegramBotToken, 
                        message,
                        "CamelTelegramChatId",
                        settings.telegramChatId);
    }
    
}
