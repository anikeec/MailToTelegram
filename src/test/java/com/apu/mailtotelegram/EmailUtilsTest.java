/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram;

import com.apu.mailtotelegram.email.utils.EmailUtils;
import java.io.UnsupportedEncodingException;
import javax.mail.internet.MimeUtility;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

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
    @Ignore
    public void testRemovePunctFromEmail() {
        System.out.println("removePunctFromEmail");
        String str = "";
        String expResult = "";
        String result = EmailUtils.removePunctFromEmail(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDecodedStr method, of class EmailUtils.
     */
    @Test
    public void testGetDecodedStr() throws Exception {
        System.out.println("getDecodedStr");
        
        String expResult = "Строка для кодирования.";
        
        String str = "=?windows-1251?B?0fLw7urgIOTr/yDq7uTo8O7i4O3o/y4=?=";        
        String result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);
        
        str = "=?windows-1251?Q?=D1=F2=F0=EE=EA=E0_=E4=EB=FF_?= "
                + "=?windows-1251?Q?=EA=EE=E4=E8=F0=EE=E2=E0=ED=E8=FF.?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);
        
        str = "=?koi8-r?B?89TSz8vBIMTM0SDLz8TJ0s/Xwc7J0S4=?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);
        
        str = "=?koi8-r?Q?=F3=D4=D2=CF=CB=C1_=C4=CC=D1_?= =?koi8-r?Q?=CB=CF=C4=C9=D2=CF=D7=C1=CE=C9=D1.?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);
        
        str = "=?utf-8?B?0KHRgtGA0L7QutCwINC00LvRjyDQutC+0LTQuNGA0L7QstCw0L3QuNGPLg==?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);
        
        str = "=?utf-8?Q?=D0=A1=D1=82=D1=80=D0=BE=D0=BA=D0=B0_=D0=B4=D0=BB=D1=8F_?= "
            + "=?utf-8?Q?=D0=BA=D0=BE=D0=B4=D0=B8=D1=80=D0=BE?= =?utf-8?Q?=D0=B2=D0=B0=D0=BD=D0=B8=D1=8F.?=";
        result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);      
                
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
        String content = "";
        String expResult = "";
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
