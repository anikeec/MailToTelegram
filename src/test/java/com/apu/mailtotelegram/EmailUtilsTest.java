/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram;

import com.apu.mailtotelegram.utils.EmailUtils;
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
     * Test of checkEmailSubject method, of class EmailUtils.
     */
    @Test
    @Ignore
    public void testCheckEmailSubject() {
        System.out.println("checkEmailSubject");
        String subject = "";
        String expResult = "";
        String result = EmailUtils.checkEmailSubject(subject);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkEmailDirectory method, of class EmailUtils.
     */
    @Test
    @Ignore
    public void testCheckEmailDirectory() {
        System.out.println("checkEmailDirectory");
        String str = "";
        String expResult = "";
        String result = EmailUtils.checkEmailDirectory(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of chechFileNameCoding method, of class EmailUtils.
     */
    @Test
    @Ignore
    public void testChechFileNameCoding() throws Exception {
        System.out.println("chechFileNameCoding");
        String fileName = "";
        String expResult = "";
        String result = EmailUtils.chechFileNameCoding(fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
    @Ignore
    public void testGetDecodedStr() throws Exception {
        System.out.println("getDecodedStr");
        String str = "";
        String expResult = "";
        String result = EmailUtils.getDecodedStr(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNextEncodedPart method, of class EmailUtils.
     */
    @Test
    @Ignore
    public void testGetNextEncodedPart() {
        System.out.println("getNextEncodedPart");
        String str = "";
        String startStr = "";
        String endStr = "";
        EmailUtils.RowPart expResult = null;
        EmailUtils.RowPart result = EmailUtils.getNextEncodedPart(str, startStr, endStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decodePart method, of class EmailUtils.
     */
    @Test
    @Ignore
    public void testDecodePart() {
        System.out.println("decodePart");
        String str = "";
        String expResult = "";
        String result = EmailUtils.decodePart(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of replaceEncodedPart method, of class EmailUtils.
     */
    @Test
    @Ignore
    public void testReplaceEncodedPart() {
        System.out.println("replaceEncodedPart");
        String strSrc = "";
        EmailUtils.RowPart rowPart = null;
        String expResult = "";
        String result = EmailUtils.replaceEncodedPart(strSrc, rowPart);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
