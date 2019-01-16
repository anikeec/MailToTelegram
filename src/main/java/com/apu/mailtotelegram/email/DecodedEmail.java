package com.apu.mailtotelegram.email;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.apu.mailtotelegram.email.utils.EmailUtils;
import com.apu.mailtotelegram.email.utils.StringUtils;

import lombok.Getter;
import lombok.Setter;


public class DecodedEmail {

    private final static Logger LOGGER = LogManager.getLogger(DecodedEmail.class.getName());

    @Getter @Setter
    private String messageUid;
    
    @Getter @Setter
    private String headTo;
    
    @Getter @Setter
    private String headFrom;
    
    @Getter @Setter
    private String headCopy;
    
    @Getter @Setter
    private String headDate;
    
    @Getter @Setter
    private String headSubject;
    
    @Getter @Setter
    private String contentType;
    
    @Getter @Setter
    private String body;

    public void putEncodedMessageUid(Object messageUidObj) {
        if (messageUidObj != null)
            this.setMessageUid((String) messageUidObj);
    }

    public void putEncodedHeadTo(Object headTo) {
        if (headTo != null) {
            String decodedAddrTo;
            try {
                decodedAddrTo = EmailUtils.getDecodedStr((String) headTo);
                this.setHeadTo(EmailUtils.extractAddressesFromEmail(decodedAddrTo));
            } catch (UnsupportedEncodingException e) {
                LOGGER.debug("MESSAGE id: " + this.getMessageUid() 
                        + "." + "Header To error. Received: " + headTo);
            }
        }
    }

    public void putEncodedHeadFrom(Object headFrom) {
        if (headFrom != null) {
            String decodedAddrFrom;
            try {
                decodedAddrFrom = EmailUtils.getDecodedStr((String) headFrom);
                this.setHeadFrom(EmailUtils.extractAddressesFromEmail(decodedAddrFrom));
            } catch (UnsupportedEncodingException e) {
                LOGGER.debug("MESSAGE id: " + this.getMessageUid() 
                        + "." + "Header From error. Received: " + headFrom);
            }
        }
    }

    public void putEncodedHeadCopy(Object headCopy) {
        if (headCopy != null) {
            String decodedHeadCopy;
            try {
                decodedHeadCopy = EmailUtils.getDecodedStr((String) headCopy);
                this.setHeadCopy(EmailUtils.extractAddressesFromEmail(decodedHeadCopy));
            } catch (UnsupportedEncodingException e) {
                LOGGER.debug("MESSAGE id: " + this.getMessageUid() 
                        + "." + "Header Copy error. Received: " + headCopy);
            }
        }
    }

    public void putEncodedHeadDate(Object headDate) {
        if (headDate != null) {
            Date date;
            // try {
            date = StringUtils.dateFormat((String) headDate);
            this.setHeadDate(StringUtils.dateFormat(date));
            // } catch (ParseException e) {
            //
            // }
        }
    }

    public void putEncodedHeadSubject(Object headSubject) {
        if (headSubject != null) {
            String decodedHeadSubject;
            try {
                decodedHeadSubject = EmailUtils.getDecodedStr((String) headSubject);
                this.setHeadCopy(decodedHeadSubject);
            } catch (UnsupportedEncodingException e) {
                LOGGER.debug("MESSAGE id: " + this.getMessageUid() 
                        + "." + "Header Subject error. Received: " + headSubject);
            }
        }
    }

    public void putEncodedContentType(Object contentType) {
        if (contentType != null) {
            String decodedContentType;
            try {
                decodedContentType = EmailUtils.getDecodedStr((String) contentType);
                this.setContentType(decodedContentType);
            } catch (UnsupportedEncodingException e) {
                LOGGER.debug("MESSAGE id: " + this.getMessageUid() + "."
                        + "Header content type error. Received: " + contentType);
            }
        }
    }

    public void putEncodedBody(Object body) {
        String content = this.getContentType();
        try {
            if ((body != null) && (body instanceof MimeMultipart)) {
                MimeMultipart bodyMultipart = (MimeMultipart) body;
                int amount = bodyMultipart.getCount();

                BodyPart bp;
                String contentType;
                Object contentObj;
                for (int i = 0; i < amount; i++) {
                    bp = bodyMultipart.getBodyPart(i);
                    contentType = bp.getContentType();
                    contentObj = bp.getContent();
                    if (contentType.contains("text/plain") && (contentObj != null)) {
                        this.setBody(EmailUtils.handleTextPlain(contentObj));
                    } else if (contentType.contains("text/html") && (contentObj != null)) {
                        this.setBody(EmailUtils.handleTextHtml(contentObj));
                    }
                }
            } else {
                if (content.contains("text/html") && (body != null)) {
                    this.setBody(EmailUtils.handleTextHtml(body));
                } else if (content.contains("text/plain") && (body != null)) {
                    this.setBody(EmailUtils.handleTextPlain(body));
                }
            }
        } catch (MessagingException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        } catch (IOException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Message.").append("\r\n");
        if (this.getHeadTo() != null)
            sb.append("To: ").append(this.getHeadTo()).append("\r\n");
        if (this.getHeadFrom() != null)
            sb.append("From: ").append(this.getHeadFrom()).append("\r\n");
        if (this.getHeadCopy() != null)
            sb.append("Copy: ").append(this.getHeadCopy()).append("\r\n");
        if (this.getHeadDate() != null)
            sb.append("Date: ").append(StringUtils.dateFormat(this.getHeadDate())).append("\r\n");
        if (this.getHeadSubject() != null)
            sb.append("Subject: ").append(this.getHeadSubject()).append("\r\n");
        if (this.getBody() != null)
            sb.append("Body: \r\n").append(this.getBody()).append("\r\n");
        return sb.toString();
    }

}
