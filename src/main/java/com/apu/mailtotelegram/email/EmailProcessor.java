/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram.email;

import com.apu.mailtotelegram.settings.TelegramSettings;
import com.apu.mailtotelegram.telegram.TelegramBot;
import com.apu.mailtotelegram.email.utils.EmailUtils;
import com.apu.mailtotelegram.email.utils.StringUtils;
import com.apu.mailtotelegram.storage.FileStorage;
import com.apu.mailtotelegram.storage.Storage;
import java.util.Date;
import java.util.Map;
import javax.mail.BodyPart;
import javax.mail.internet.MimeMultipart;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;



/**
 *
 * @author apu
 */
public class EmailProcessor implements Processor {

    private final static Logger LOGGER = 
            LogManager.getLogger(EmailProcessor.class.getName());
    
    private final TelegramSettings telegramSettings;    
    private final Storage<String> storage;

    public EmailProcessor(TelegramSettings telegramSettings) {
        this.telegramSettings = telegramSettings;
        this.storage = new FileStorage<>();     //init storage
    }    
    
    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> headers = exchange.getIn().getHeaders();

        Object messageUid = headers.get("Message-ID");
        Object headTo = headers.get("To");
        Object headFrom = headers.get("From");
        Object headCopy = headers.get("CC");
        Object headDate = headers.get("Date");
        Object headSubject = headers.get("Subject");

        System.out.println(
                headTo + "\r\n" + 
                headFrom + "\r\n" + 
                headCopy + "\r\n" + 
                headDate + "\r\n" + 
                headSubject + "\r\n");

        if(messageUid != null) {
            String messageUidStr = (String)messageUid;
            // check if this message has already received
            if (storage.find(messageUidStr)) {
                LOGGER.info("MESSAGE with id " + messageUidStr + " exist.");
                return;
            }
            storage.add(messageUidStr);
        }

        Date date = null;
        if(headDate != null) {
            date = StringUtils.dateFormat((String)headDate);
            Date nowaday = 
                StringUtils.dateFormat("Wed, 19 Dec 2018 15:00:00 +0300");
            if(date.compareTo(nowaday) < 0) {
                System.out.println("Old message");
                return;
            }
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Message.").append("\r\n");
        
        if(headTo != null) {
            String decodedAddrTo = 
                    EmailUtils.getDecodedStr((String)headTo);
            sb.append("To: ")
                .append(EmailUtils.extractAddressesFromEmail(decodedAddrTo))
                .append("\r\n");
        }
        
        if(headFrom != null) {
            String decodedAddrFrom = 
                    EmailUtils.getDecodedStr((String)headFrom);
            sb.append("From: ")
                .append(EmailUtils.extractAddressesFromEmail(decodedAddrFrom))
                .append("\r\n");
        }
        
        if(headCopy != null) {
            String decodedAddrCopy = 
                    EmailUtils.getDecodedStr((String)headCopy);
            sb.append("Copy: ")
                .append(EmailUtils.extractAddressesFromEmail(decodedAddrCopy))
                .append("\r\n");
        }
        
        sb.append("Date: ")
                .append(StringUtils.dateFormat(date))
                .append("\r\n");
        
        if(headSubject != null)
            sb.append("Subject: ")
                .append("\r\n")
                .append(EmailUtils.getDecodedStr((String)headSubject))
                .append("\r\n");

        String content = (String)headers.get("content-Type");        
        
        sb.append("Body: ");
        Object body = exchange.getIn().getBody();
        
        if((body != null) && (body instanceof MimeMultipart)) {
            MimeMultipart bodyMultipart = (MimeMultipart)body;
            int amount = bodyMultipart.getCount();
            BodyPart bp;
            String contentType;
            Object contentObj;
            for(int i=0; i<amount; i++) {
                bp = bodyMultipart.getBodyPart(i);
                contentType = bp.getContentType();
                contentObj = bp.getContent();
                if(contentType.contains("text/plain") && (contentObj != null)) {
                    sb.append(EmailUtils.handleTextPlain(contentObj));
                } else if(content.contains("text/html") && (contentObj != null)) {
                    sb.append(EmailUtils.handleTextHtml(contentObj));
                }                       
            }
        } else {
            if(content.contains("text/html") && (body != null)) {           
                sb.append(EmailUtils.handleTextHtml(body));
            } else if(content.contains("text/plain") && (body != null)) {
                sb.append(EmailUtils.handleTextPlain(body));
            }
        }

        sb.append("\r\n");

        ProducerTemplate template = 
                exchange.getContext().createProducerTemplate();

        TelegramBot.send(template, 
                        telegramSettings, 
                        sb.toString());
        /*
        Object response = template.requestBodyAndHeader(
              "telegram:bots/" + telegramBotToken, 
              sb.toString(),
              "CamelTelegramChatId",
              telegramChatId);
        */
//                    exchange.getOut().setBody(pktdata + "\r\n" 
//                        + name + "\r\n"
//                        + response
//                        );
        template.stop();
    }
    
}
