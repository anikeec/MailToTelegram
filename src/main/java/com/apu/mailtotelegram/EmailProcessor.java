/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram;

import com.apu.mailtotelegram.utils.EmailUtils;
import com.apu.mailtotelegram.utils.StringUtils;
import com.apu.mailtotelegram.storage.FileStorage;
import com.apu.mailtotelegram.storage.Storage;
import java.util.Date;
import java.util.Map;
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
    
    private final String telegramBotToken;
    private final String telegramChatId;    
    private final Storage<String> storage;

    public EmailProcessor(String telegramBotToken, String telegramChatId) {
        this.telegramBotToken = telegramBotToken;
        this.telegramChatId = telegramChatId;
        this.storage = new FileStorage<>();     //init storage
    }    
    
    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> headers = exchange.getIn().getHeaders();
                    Object body = exchange.getIn().getBody();

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
                    if(headTo != null)
                        sb.append("To: ")
                            .append(EmailUtils.getDecodedStr((String)headTo))
                            .append("\r\n");
                    if(headFrom != null)
                        sb.append("From: ")
                            .append(EmailUtils.getDecodedStr((String)headFrom))
                            .append("\r\n");
                    if(headCopy != null)
                        sb.append("Copy: ")
                            .append(EmailUtils.getDecodedStr((String)headCopy))
                            .append("\r\n"); 
                    sb.append("Date: ")
                            .append(StringUtils.dateFormat(date))
                            .append("\r\n");
                    if(headSubject != null)
                        sb.append("Subject: ")
                            .append(EmailUtils.checkEmailSubject((String)headSubject))
                            .append("\r\n");
                    //sb.append("Text: ").append(headers.get("Subject")).append("\r\n");

                    String content = (String)headers.get("content-Type");
                    if(content.contains("text/html")) {
                                                                                //check for multipart!!!
                        //remove html tags from message
                        if(body != null) {
                            String bodyWithoutHtml = 
                                    EmailUtils.removeHtmlTags((String)body);
                            String bodyWithoutEmptyStr = 
                                    EmailUtils.removeEmptyStrs(bodyWithoutHtml);
                            String bodyWithoutSpecialSymbols = 
                                    EmailUtils.removeSpecialSymbols(bodyWithoutEmptyStr);
                            String decodedStr = EmailUtils.getDecodedStr(bodyWithoutSpecialSymbols);
                            sb.append("Body: ")
                                .append(decodedStr.substring(0,400))
                                .append("\r\n");
                        } else if(content.contains("text/plain")) {
                            String bodyWithoutEmptyStr = 
                                    EmailUtils.removeEmptyStrs((String)body);
                            String bodyWithoutSpecialSymbols = 
                                    EmailUtils.removeSpecialSymbols(bodyWithoutEmptyStr);
                            String decodedStr = EmailUtils.getDecodedStr(bodyWithoutSpecialSymbols);
                            sb.append("Body: ")
                                .append(decodedStr.substring(0,400))
                                .append("\r\n");
                        }
                        
                    }
                    
                    ProducerTemplate template = 
                            exchange.getContext().createProducerTemplate();

                    TelegramBot.send(template, 
                                    telegramBotToken, 
                                    telegramChatId, 
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
