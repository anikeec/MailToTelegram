/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram.email;

import com.apu.mailtotelegram.storage.FileStorage;
import com.apu.mailtotelegram.storage.Storage;
import com.apu.mailtotelegram.storage.StorageException;

import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;



/**
 *
 * @author apu
 */
public class EmailProcessor implements Processor {

    private final static Logger LOGGER = 
            LogManager.getLogger(EmailProcessor.class.getName());
    
    private final Storage<String> storage;

    public EmailProcessor() {
        this.storage = new FileStorage<>();     //init storage
    }    
    
    @Override
    public void process(Exchange exchange) throws StorageException {
        Map<String, Object> headers = exchange.getIn().getHeaders();

        Object messageUid = headers.get("Message-ID");
        Object headTo = headers.get("To");
        Object headFrom = headers.get("From");
        Object headCopy = headers.get("CC");
        Object headDate = headers.get("Date");
        Object headSubject = headers.get("Subject");

        LOGGER.info(
                headTo + "\r\n" + 
                headFrom + "\r\n" + 
                headCopy + "\r\n" + 
                headDate + "\r\n" + 
                headSubject + "\r\n");
        
        DecodedEmail decodedEmail = new DecodedEmail();
        decodedEmail.putEncodedMessageUid(messageUid);
        decodedEmail.putEncodedHeadTo(headTo);
        decodedEmail.putEncodedHeadFrom(headFrom);
        decodedEmail.putEncodedHeadCopy(headCopy);
        decodedEmail.putEncodedHeadDate(headDate);
        decodedEmail.putEncodedHeadSubject(headSubject);
        decodedEmail.putEncodedContentType(headers.get("content-Type"));
        
        String messageUidStr = decodedEmail.getMessageUid();
        // check if this message has already received
        if (storage.find(messageUidStr)) {
            LOGGER.info("MESSAGE with id " + messageUidStr + " exist.");
            exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE); 
            return;
        }
        storage.add(messageUidStr);
        
        decodedEmail.putEncodedBody(exchange.getIn().getBody());       
        
        //send decoded email message 
        exchange.getIn().setBody(decodedEmail.toString());
        
    }
    
}
