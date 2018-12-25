/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram;

import com.apu.mailtotelegram.email.utils.EmailUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author apu
 */
public class EmailUtilsTest {
    
    public EmailUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of removePunctFromEmail method, of class EmailUtils.
     */
    @Test
    public void testRemovePunctFromEmail() {
        System.out.println("removePunctFromEmail");
        String str = "This$ is! a[ test~ text]";
        String expResult = "This is a test text";
        String result = EmailUtils.removePunctFromEmail(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDecodedStr method, of class EmailUtils.
     */
    @Test
    public void testGetDecodedStr() throws Exception {
        System.out.println("getDecodedStr");
        
        String expResult = "Это текст for encoding.";
        String str, result;         

        str = "=?windows-1251?B?3fLuIPLl6vHyIGZvciBlbmNvZGluZy4=?=";        
        result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);
        
        str = "=?windows-1251?Q?=DD=F2=EE_=F2=E5=EA=F1=F2_for_encoding.?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);
        
        str = "=?koi8-r?B?/NTPINTFy9PUIGZvciBlbmNvZGluZy4=?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);
        
        str = "=?koi8-r?Q?=FC=D4=CF_=D4=C5=CB=D3=D4_for_encoding.?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);
        
        str = "=?utf-8?B?0K3RgtC+INGC0LXQutGB0YIgZm9yIGVuY29kaW5nLg==?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);
        
        str = "=?utf-8?Q?=D0=AD=D1=82=D0=BE_=D1=82=D0=B5=D0=BA=D1=81=D1=82_for_encoding.?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);      
        
        str = "It is a text=?koi8-r?Q?=FC=D4=CF_=D4=C5=CB=D3=D4_for_encoding.?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals("It is a text" + expResult, result);
        
        str = "It is a text=?koi8-r?Q?=FC=D4=CF_=D4=C5=CB=D3=D4_for_encoding.?="
                + " add other part "
                + "=?koi8-r?Q?=FC=D4=CF_=D4=C5=CB=D3=D4_for_encoding.?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals("It is a text" + expResult + " add other part " + expResult, result);
        
    }

    /**
     * Test of removeStyleTags method, of class EmailUtils.
     */
    @Test
    public void testRemoveStyleTags() {
        System.out.println("removeStyleTags");
        String content = 
                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
                "<html><head><title></title>\n" +
                "<META http-equiv=Content-Type content=\"text/html; charset=koi8-r\">\n" +
                "<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n" +
                "<style type=\"text/css\"><!--\n" +
                "body {\n" +
                "  margin: 5px 5px 5px 5px;\n" +
                "  background-color: #ffffff;\n" +
                "}\n" + 
                "{\n" +
                " text-align: center;\n" +
                "}\n" +
                "--></style>\n" +
                "</head>";        
        String expResult = 
                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
                "<html><head><title></title>\n" +
                "<META http-equiv=Content-Type content=\"text/html; charset=koi8-r\">\n" +
                "<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n" +
                "\n" +
                "</head>";
        String result = EmailUtils.removeStyleTags(content);
        assertEquals(expResult, result);
        content = 
                "<style type=\"text/css\"><!--\n" +
                "body {\n" +
                "  margin: 5px 5px 5px 5px;\n" +
                "  background-color: #ffffff;\n" +
                "}\n" + 
                "{\n" +
                " text-align: center;\n" +
                "}\n" +
                "--></styl>";
        expResult = content;
        result = EmailUtils.removeStyleTags(content);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeHtmlTags method, of class EmailUtils.
     */
    @Test
    public void testRemoveHtmlTags() {
        System.out.println("removeHtmlTags");
        String content = "<p>text for testing.</p>Text";
        String expResult = "text for testing.Text";
        String result = EmailUtils.removeHtmlTags(content);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeEmptyStrs method, of class EmailUtils.
     */
    @Test
    public void testRemoveEmptyStrs() {
        System.out.println("removeEmptyStrs");
        String content = 
                "body {\n" +
                "  background-color: #ffffff;\r\n" +
                "\r\n" + 
                "\r\n" +
                "}\n";
        String expResult = 
                "body {\n" +
                "  background-color: #ffffff;\r\n" +
                "}\n";;
        String result = EmailUtils.removeEmptyStrs(content);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeSpecialSymbols method, of class EmailUtils.
     */
    @Test
    public void testRemoveSpecialSymbols() {
        System.out.println("removeSpecialSymbols");
        String content = "<span>Ltd.--&nbsp; &nbsp;&nbsp; &nbsp;</span>";
        String expResult = "<span>Ltd.</span>";
        String result = EmailUtils.removeSpecialSymbols(content);
        assertEquals(expResult, result);
    }
    
}
