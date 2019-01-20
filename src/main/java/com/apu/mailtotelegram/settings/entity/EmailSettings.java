/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram.settings.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author apu
 */
@NoArgsConstructor
public class EmailSettings {
    
    @Getter @Setter
    private String host;
    
    @Getter @Setter
    private String port;
    
    @Getter @Setter
    private String username;
    
    @Getter @Setter
    private String password;
    
}
