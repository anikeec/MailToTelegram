/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram.utils.log;

import java.io.PrintStream;
import org.apache.log4j.LogManager;

/**
 *
 * @author apu
 */
public class Logging {
    
    public static PrintStream createLoggingProxy(PrintStream inPrintStream) {
        return new PrintStream(inPrintStream) {
            @Override
            public void print(String s) {
                //inPrintStream.print(s);
                LogManager.getLogger("outLog").info(s);
            }            
        };
    }
    
}
