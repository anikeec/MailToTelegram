package com.apu.mailtotelegram.settings;

import com.apu.mailtotelegram.storage.SettingsStorage;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private final static String EMAIL_HOST_PROPERTY = "email.host";
    private final static String EMAIL_PORT_PROPERTY = "email.port";
    private final static String EMAIL_USERNAME_PROPERTY = "email.username";
    private final static String EMAIL_PASSWORD_PROPERTY = "email.password";
    private final static String TELEGRAM_BOT_TOKEN_PROPERTY = "telegram.bot.token";
    private final static String TELEGRAM_CHAT_ID_PROPERTY = "telegram.chat.id";
    
    public static void loadEmailSettingsFromFile(
                    EmailSettings settings) throws IOException {
        //load settings
        Properties props = SettingsStorage.loadPropertiesFromFile();
        
        if(props.containsKey(EMAIL_HOST_PROPERTY)) 
            settings.emailHost = props.getProperty(EMAIL_HOST_PROPERTY);
        else
            throw new IllegalArgumentException(EMAIL_HOST_PROPERTY + " parametes is absent.");
        
        if(props.containsKey(EMAIL_PORT_PROPERTY)) 
            settings.emailPort = props.getProperty(EMAIL_PORT_PROPERTY);
        else
            throw new IllegalArgumentException(EMAIL_PORT_PROPERTY + " parametes is absent.");
        
        if(props.containsKey(EMAIL_USERNAME_PROPERTY)) 
            settings.emailUsername = props.getProperty(EMAIL_USERNAME_PROPERTY);
        else
            throw new IllegalArgumentException(EMAIL_USERNAME_PROPERTY + " parametes is absent.");
        
        if(props.containsKey(EMAIL_PASSWORD_PROPERTY)) 
            settings.emailPassword = props.getProperty(EMAIL_PASSWORD_PROPERTY);
        else
            throw new IllegalArgumentException(EMAIL_PASSWORD_PROPERTY + " parametes is absent.");
        
    }
    
    public static void loadTelegramSettingsFromFile(
                    TelegramSettings settings) throws IOException {
        //load settings
        Properties props = SettingsStorage.loadPropertiesFromFile();
        
        if(props.containsKey(TELEGRAM_BOT_TOKEN_PROPERTY)) 
            settings.telegramBotToken = props.getProperty(TELEGRAM_BOT_TOKEN_PROPERTY);
        else
            throw new IllegalArgumentException(TELEGRAM_BOT_TOKEN_PROPERTY + " parametes is absent.");
        
        if(props.containsKey(TELEGRAM_CHAT_ID_PROPERTY)) 
            settings.telegramChatId = props.getProperty(TELEGRAM_CHAT_ID_PROPERTY);
        else
            throw new IllegalArgumentException(TELEGRAM_CHAT_ID_PROPERTY + " parametes is absent.");
    }
    
}
