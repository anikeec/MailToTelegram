package com.apu.mailtotelegram.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {

	//private static final String PUNCT = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

	private static final String PUNCT_SUBJECT = "!\"#$%&'()*+,/:;<>?@[\\]^`{|}~";
	
    public static String removePunct(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (PUNCT_SUBJECT.indexOf(c) < 0) {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    public static String removePunct(String str, String pattern) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (pattern.indexOf(c) < 0) {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    public static String lengthRestriction(String str, int maxLength) {
        if(str.length() >= maxLength) {
            str = str.substring(0, maxLength);
        }
        return str;
    }
    
    public static String dateFormat(Date date) {        
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        return dateFormat.format(date);
    }
    
    public static Date dateFormat(String date) {   
        //Mon, 1 Oct 2018 14:17:41 +0300
        Locale locale = new Locale("UA");
        SimpleDateFormat dateFormat = 
                new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", locale);
//                new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return new Date(0);
        }
    }
	
}
