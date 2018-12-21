/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram;

import org.apache.camel.ProducerTemplate;

/**
 *
 * @author apu
 */
public class TelegramBot {
    
    public static void send(ProducerTemplate template, 
                            String telegramBotToken,
                            String telegramChatId,
                            String message) {
        Object response = 
                template.requestBodyAndHeader(
                        "telegram:bots/" + telegramBotToken, 
                        message,
                        "CamelTelegramChatId",
                        telegramChatId);
    }
    
}
