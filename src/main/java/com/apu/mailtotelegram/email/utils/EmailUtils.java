package com.apu.mailtotelegram.email.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeUtility;

public class EmailUtils {
  
    private static final int MAX_TEXT_MESS_LENGTH = 400;    
    private static final String PUNCT_SUBJECT_EMAIL = "!\"#$%&'()*+,/:;?[\\]^`{|}~";
    
    public static String handleTextPlain(Object content) 
                            throws UnsupportedEncodingException {
        String bodyWithoutHtml = 
                EmailUtils.removeHtmlTags((String)content);
        String bodyWithoutEmptyStr = 
                EmailUtils.removeEmptyStrs(bodyWithoutHtml);
        String bodyWithoutSpecialSymbols = 
                EmailUtils.removeSpecialSymbols(bodyWithoutEmptyStr);
        String decodedStr = EmailUtils.getDecodedStr(bodyWithoutSpecialSymbols);
        if(decodedStr.length() > MAX_TEXT_MESS_LENGTH) 
                decodedStr = decodedStr.substring(0,MAX_TEXT_MESS_LENGTH);
        return decodedStr;
    }
    
    public static String handleTextHtml(Object content) 
                            throws UnsupportedEncodingException {
        String bodyWithoutEmptyStr = 
                EmailUtils.removeEmptyStrs((String)content);
        String bodyWithoutSpecialSymbols = 
                EmailUtils.removeSpecialSymbols(bodyWithoutEmptyStr);
        String decodedStr = EmailUtils.getDecodedStr(bodyWithoutSpecialSymbols);
        if(decodedStr.length() > MAX_TEXT_MESS_LENGTH) 
            decodedStr = decodedStr.substring(0,MAX_TEXT_MESS_LENGTH);
        return decodedStr;
    }
    
    public static String extractAddressesFromEmail(String str) {
        Pattern p = Pattern.compile(
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
            + "|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]"
            + "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")"
            + "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
            + "(?:[a-z0-9-]*[a-z0-9])?"
            + "|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}"
            + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?"
            + "|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]"
            + "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"); 
        Matcher m = p.matcher(str);
        StringBuilder sb = new StringBuilder();
        while(m.find()){
            int st = m.start();
            int end = m.end();
            String matchedStr =  m.group();
            sb.append("\r\n").append(matchedStr);          
        }
        return sb.toString();
    }
    
    public static String getDecodedStr(String str) throws UnsupportedEncodingException {
        Pattern p = 
            Pattern.compile("\\=\\?"
                    + "(utf-8|UTF-8|windows-1251|WINDOWS-1251|koi8-r|KOI8-R)\\?"
                    + "(Q|B)\\?"
                    + ".+?\\?\\="); //used lazy mode ("x.+?x" //it is a lazy mode)
        Matcher m = p.matcher(str);
        StringBuilder sbDecoded = new StringBuilder();
        int tempPosition = 0;
        while(m.find()){
            int st = m.start();
            if(st > (tempPosition + 1)) {
                sbDecoded.append(str.substring(tempPosition, st));
            }
            int end = m.end();
            String matchedStr =  m.group();
            String decodedText = MimeUtility.decodeText(matchedStr);
            sbDecoded.append(decodedText);          
            tempPosition = end;
        }  
        if((str.length() - 1) > tempPosition) {
            sbDecoded.append(str.substring(tempPosition, str.length()));
        }
        return sbDecoded.toString();
    }    
    
    public static String removePunctFromEmail(String str) {
        String result = str.replaceAll("(\\r\\n)+", "");
        return StringUtils.removePunct(result, PUNCT_SUBJECT_EMAIL);
    }
    
    public static String removeStyleTags(String content) {
        if(content == null) return null;
        return 
            content.replaceAll("(<style type=\"text/css\">)[\\s\\S]*(</style>)","");
    
    }
    
    public static String removeHtmlTags(String content) {
        if(content == null) return null;
        content = removeStyleTags(content);
        return content.replaceAll("\\<[^>]{1,100}?>","")
                .replaceAll("class=\"[^\"]{1,20}?\"","")
                .replaceAll("data-id=\"[^\"]{1,10}?\"","")
                .replaceAll("data-raw=\"[^\"]{1,20}?\"","");
    }
    
    public static String removeEmptyStrs(String content) {
        if(content == null) return null;
        return content.replaceAll("(\\r\\n)+","\r\n");
    }
    
    public static String removeSpecialSymbols(String content) {
        if(content == null) return null;
        return content.replaceAll("( &nbsp;)+","")
                .replaceAll("(--&nbsp;)+","")
                .replaceAll("(&nbsp;)+","")
                .replaceAll("(&lt;)+"," ")
                .replaceAll("(&gt;)+"," ");
    }
    
    public static String removeCSS(String content) {
        if(content == null) return null;
        return content.replaceAll("[ &nbsp;]+","")
                .replaceAll("[--&nbsp;]+","")
                .replaceAll("[&nbsp;]+","");
    }

}
