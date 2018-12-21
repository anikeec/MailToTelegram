package com.apu.mailtotelegram.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    public final String GLOBAL_PROPERTIES_FILE_PATH = "src/main/resources/global.properties";
    public final String LAST_MESSAGE_ID = "email.lastMessageId";
    public final String LAST_MESSAGE_DATE_TIME = "email.lastMessageDateTime";
    
    public Properties loadPropertiesFromFile(String fileName) throws IOException {
        Properties properties = new Properties();
        FileInputStream in;
        in = new FileInputStream(fileName);
        properties.load(in);
        in.close();    
        return properties;
    }
    
    public void savePropertiesToFile(String fileName, Properties properties) throws IOException {
        FileOutputStream propFile = new FileOutputStream(fileName);
        properties.store(propFile, null);
        propFile.close();
    }
    
}
