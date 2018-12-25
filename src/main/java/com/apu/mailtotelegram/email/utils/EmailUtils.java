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
    
    public static String getDecodedStr(String str) throws UnsupportedEncodingException {
        Pattern p = 
            Pattern.compile("\\=\\?"
                    + "(utf-8|UTF-8|windows-1251|WINDOWS-1251|koi8-r|KOI8-R)\\?"
                    + "(Q|B)\\?"
                    + ".+?"         //used lazy mode ("x.+?x" //it is a lazy mode)
                    + "\\?\\="); 
        Matcher m = p.matcher(str);
        StringBuilder sbDecoded = new StringBuilder();
        while(m.find()){
            int st = m.start();
            int end = m.end();
            String matchedStr =  m.group();
            String decodedText = MimeUtility.decodeText(matchedStr);
            sbDecoded.append(decodedText);          
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
