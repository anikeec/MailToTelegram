package com.apu.mailtotelegram.settings;

import com.apu.mailtotelegram.settings.entity.TelegramSettings;
import com.apu.mailtotelegram.settings.entity.EmailSettings;
import com.apu.mailtotelegram.storage.SettingsStorage;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    public static void loadEmailSettingsFromFile(
                    EmailSettings emailSet) throws IOException {
        //load settings
        Properties props = SettingsStorage.loadPropertiesFromFile();
        
        if(props.containsKey(EmailProps.HOST_PROP)) 
            emailSet.setHost(props.getProperty(EmailProps.HOST_PROP));
        else
            throw new IllegalArgumentException(EmailProps.HOST_PROP + " parametes is absent.");
        
        if(props.containsKey(EmailProps.PORT_PROP)) 
            emailSet.setPort(props.getProperty(EmailProps.PORT_PROP));
        else
            throw new IllegalArgumentException(EmailProps.PORT_PROP + " parametes is absent.");
        
        if(props.containsKey(EmailProps.USERNAME_PROP)) 
            emailSet.setUsername(props.getProperty(EmailProps.USERNAME_PROP));
        else
            throw new IllegalArgumentException(EmailProps.USERNAME_PROP + " parametes is absent.");
        
        if(props.containsKey(EmailProps.PASSWORD_PROP)) 
            emailSet.setPassword(props.getProperty(EmailProps.PASSWORD_PROP));
        else
            throw new IllegalArgumentException(EmailProps.PASSWORD_PROP + " parametes is absent.");
        
    }
    
    public static void loadTelegramSettingsFromFile(
                    TelegramSettings telegramSet) throws IOException {
        //load settings
        Properties props = SettingsStorage.loadPropertiesFromFile();
        
        if(props.containsKey(TelegramProps.BOT_TOKEN_PROP)) 
            telegramSet.setBotToken(props.getProperty(TelegramProps.BOT_TOKEN_PROP));
        else
            throw new IllegalArgumentException(TelegramProps.BOT_TOKEN_PROP + " parametes is absent.");
        
        if(props.containsKey(TelegramProps.CHAT_ID_PROP)) 
            telegramSet.setChatId(props.getProperty(TelegramProps.CHAT_ID_PROP));
        else
            throw new IllegalArgumentException(TelegramProps.CHAT_ID_PROP + " parametes is absent.");
    }
    
}
