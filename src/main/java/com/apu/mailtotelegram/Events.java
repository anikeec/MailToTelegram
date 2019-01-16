package com.apu.mailtotelegram;

import org.apache.camel.main.MainListenerSupport;
import org.apache.camel.main.MainSupport;

public class Events extends MainListenerSupport {

    @Override
    public void afterStart(MainSupport main) {
        System.out.println("Email2Telegram with Camel is now started!");
    }

    @Override
    public void beforeStop(MainSupport main) {
        System.out.println("Email2Telegram with Camel is now being stopped!");
    }
}
