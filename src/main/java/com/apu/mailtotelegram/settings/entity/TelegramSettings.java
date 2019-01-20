/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram.settings.entity;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author apu
 */
public class TelegramSettings {
    
    @Getter @Setter
    private String botToken;
    
    @Getter @Setter
    private String chatId;
    
}
