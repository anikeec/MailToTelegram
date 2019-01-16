package com.apu.mailtotelegram.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SettingsStorage {

    private static Logger LOGGER = LogManager.getLogger(SettingsStorage.class.getName());
    
    private static final String PROPERTIES_FILE_PATH = "global.properties";
    
    public static Properties loadPropertiesFromFile() throws IOException {
        Properties properties = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(PROPERTIES_FILE_PATH);
            properties.load(in);
        } finally {
            if(in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("Error closing property file \r\n" + 
                                    ExceptionUtils.getStackTrace(e));
                }
        }
        return properties;
    }
    
    public static void savePropertiesToFile(Properties properties) throws IOException {
        FileOutputStream propFile = null;
        try {
            propFile = new FileOutputStream(PROPERTIES_FILE_PATH);
            properties.store(propFile, null);
        } finally {
            if(propFile != null)
                try {
                    propFile.close();
                } catch (IOException e) {
                    LOGGER.error("Error closing property file \r\n" + 
                                    ExceptionUtils.getStackTrace(e));
                }
        }       
    }
    
}
