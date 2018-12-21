package com.apu.mailtotelegram.utils;

import com.apu.mailtotelegram.utils.StringUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.MimeUtility;

import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;

public class EmailUtils {
    
    private static String START_ENCODED_SYMBOLS = "=?";
    private static String END_ENCODED_SYMBOLS = "?=";
    private static String KOI8R_ENCODING = "KOI8-R";
    private static String KOI8R_START_ROW = "KOI8-R?B?";
    private static String UTF8_ENCODING = "UTF-8";
    private static String UTF8B_START_ROW = "UTF-8?B?";
    private static String UTF8Q_START_ROW = "UTF-8?Q?";

    private static int EMAIL_SUBJECT_MAX_LENGTH = 80;

    public static String checkEmailSubject(String subject) {
        if (subject == null)
                subject = "";
        try {
            subject = EmailUtils.getDecodedStr(subject);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EmailUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        subject = StringUtils.removePunct(subject);
        return subject;
    }

    public static String checkEmailDirectory(String str) {
        return StringUtils.lengthRestriction(str, EMAIL_SUBJECT_MAX_LENGTH);
    }
    
    private static String checkForErrorDecoding(String str) throws UnsupportedEncodingException {               
        
//        byte bytesF[] = str.getBytes("cp1251");
//        String charsetDetectedFirst = CharsetDetect.findCharset(bytesF);
//        byte bytesFirst[] = str.getBytes(charsetDetectedFirst);
//        //charsetDetectedFirst = CharsetDetect.findCharset(bytesFirst);
//        //String retStrFirst = new String(bytesFirst, charsetDetectedFirst);
//        
//        CharsetDetector detector1 = new CharsetDetector();
//        detector1.setText(bytesFirst);
//        CharsetMatch charset1 = detector1.detect();
//        String charsetName1 = charset1.getName();
//        String retStrFirst = new String(bytesFirst, charsetName1);
        
        String text = MimeUtility.decodeText(str);
        
        byte bytes[] = str.getBytes("cp1251");
        CharsetDetector detector = new CharsetDetector();
        detector.setText(bytes);
        CharsetMatch charset = detector.detect();
        CharsetMatch charsets[] = detector.detectAll();
        String charsetName = charset.getName();
        if(charsetName.equalsIgnoreCase("windows-1251")) 
            return str;
        bytes = str.getBytes(charsetName);//"ISO-8859-1"
        
        detector.setText(bytes);
        charset = detector.detect();
        charsetName = charset.getName();        
        String retStr = new String(bytes, charsetName);
        
        return retStr;         
    }
    
    public static String chechFileNameCoding(String fileName) throws IOException {
        if(fileName == null)
            return null;
        String str = getDecodedStr(fileName);        
        return StringUtils.removePunct(str);     
    }
    
    private static final String PUNCT_SUBJECT_EMAIL = "!\"#$%&'()*+,/:;?[\\]^`{|}~";
    
    public static String removePunctFromEmail(String str) {
        String result = str.replaceAll("(\\r\\n)+", "");
        return StringUtils.removePunct(result, PUNCT_SUBJECT_EMAIL);
    }
    
    public static String getDecodedStr(String str) throws UnsupportedEncodingException {
        RowPart findedPart;
        do {
            findedPart = getNextEncodedPart(str, START_ENCODED_SYMBOLS, END_ENCODED_SYMBOLS);
            if((findedPart != null)&&(findedPart.encoded == true)) {
                findedPart.strDecoded = decodePart(findedPart.str);
                str = replaceEncodedPart(str, findedPart);
            }
        } while(findedPart != null);
        //str = checkForErrorDecoding(str);
        return str;
    }
    
    public static RowPart getNextEncodedPart(String str, String startStr, String endStr) {
        int startIndex = str.indexOf(startStr);
        if (startIndex == -1)
            return null;
        int endIndex = str.indexOf(endStr, startIndex + 1);
        if (endIndex == -1)
            return null;
        String subStr = str.substring(startIndex + startStr.length(), endIndex);        
        RowPart retPart = new RowPart(subStr, true, startIndex, endIndex + endStr.length());        
        return retPart;
    }
    
    public static String decodePart(String str) {
        if (str.length() < 5)
                return str;
        String substrCoding;
        if (str.toUpperCase().contains(KOI8R_START_ROW)) {        
            String substring = str.substring(KOI8R_START_ROW.length());
            byte[] decoded = Base64.getDecoder().decode(substring);
            try {
                String koiStr = new String(decoded, KOI8R_ENCODING);
                decoded = koiStr.getBytes(UTF8_ENCODING);
                return new String(decoded, UTF8_ENCODING);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EmailUtils.class.getName()).log(Level.SEVERE, null, ex);
            }                                  
        } else if (str.toUpperCase().contains(UTF8B_START_ROW)) { 
            String substring = str.substring(UTF8B_START_ROW.length());
            byte[] decoded = Base64.getDecoder().decode(substring);
            try {
                return new String(decoded, UTF8_ENCODING);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EmailUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (str.toUpperCase().contains(UTF8Q_START_ROW)) { 
            String substring = str.substring(UTF8Q_START_ROW.length());
            return substring;
        } else { 
            return str;
        }        
        return null;
    }
    
    public static String replaceEncodedPart(String strSrc, RowPart rowPart) {
        StringBuilder sb = new StringBuilder();
        sb.append(strSrc.substring(0, rowPart.startPosition));
        sb.append(rowPart.strDecoded);
        sb.append(strSrc.substring(rowPart.endPosition, strSrc.length()));
        return sb.toString();
    }
    
    public static String removeStyleTags(String content) {
        if(content == null) return null;
        //String ret = Jsoup.parse(content).text();
        return content
        //        .replaceAll("<style type=\"text/css\">(?:.|\n)*</style>","");
        //.replaceAll("<(?:style){1}.*/?(?:(?:.|\n)*</(?:style){1}>)?","");
        //.replaceAll("^(<style type=\"text/css\">){1}(.|'\\u0085'|'\\u2028'|'\\u2029'|\r|\r\n|\n)*(</style>){1}","");
        //.replaceAll("[\\s\\S]*<style type=\"text/css\">[\\s\\S]*</style>","");
        .replaceAll("(<style type=\"text/css\">)[\\s\\S]*(</style>)","");
    
    }
    
    public static String removeHtmlTags(String content) {
        if(content == null) return null;
        content = removeStyleTags(content);
        //String ret = Jsoup.parse(content).text();
        return content.replaceAll("\\<[^>]{1,100}?>","")
                .replaceAll("class=\"[^\"]{1,20}?\"","")
                .replaceAll("data-id=\"[^\"]{1,10}?\"","")
                .replaceAll("data-raw=\"[^\"]{1,20}?\"","");
    }
    
    public static String removeEmptyStrs(String content) {
        if(content == null) return null;
        //String ret = Jsoup.parse(content).text();
        return content.replaceAll("(\\r\\n)+","\r\n");
    }
    
    public static String removeSpecialSymbols(String content) {
        if(content == null) return null;
        //String ret = Jsoup.parse(content).text();
        return content.replaceAll("( &nbsp;)+","")
                .replaceAll("(--&nbsp;)+","")
                .replaceAll("(&nbsp;)+","")
                .replaceAll("(&lt;)+"," ")
                .replaceAll("(&gt;)+"," ");
    }
    
    public static String removeCSS(String content) {
        if(content == null) return null;
        //String ret = Jsoup.parse(content).text();
        return content.replaceAll("[ &nbsp;]+","")
                .replaceAll("[--&nbsp;]+","")
                .replaceAll("[&nbsp;]+","");
    }
    
    public static class RowPart {
        String str;
        boolean encoded;
        String strDecoded;
        int startPosition;
        int endPosition;
        
        public RowPart(String str, boolean encoded, int startPosition, int endPosition) {
            this.str = str;
            this.encoded = encoded;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }        
    }
    
    public static void main(String[] args) {
        String str = "Óâåäîìëåíèå î íîâîé òåìå â ïîäïèñàííîì ôîðóìå";
        String result;
        try {
            result = EmailUtils.checkForErrorDecoding(str);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        str = "Óâåäîìëåíèå î íîâîé òåìå â ïîäïèñàííîì ôîðóìå ( From VRTP )";
        try {
            result = EmailUtils.checkForErrorDecoding(str);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //MailUtils.extractStandardHeaders()
        
    }

}
