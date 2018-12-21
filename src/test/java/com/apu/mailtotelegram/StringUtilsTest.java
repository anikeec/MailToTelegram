/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apu.mailtotelegram;

import com.apu.mailtotelegram.utils.StringUtils;
import java.util.Date;
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
public class StringUtilsTest {
    
    public StringUtilsTest() {
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
     * Test of removePunct method, of class StringUtils.
     */
    @Test
    @Ignore
    public void testRemovePunct_String() {
        System.out.println("removePunct");
        String str = "";
        String expResult = "";
        String result = StringUtils.removePunct(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removePunct method, of class StringUtils.
     */
    @Test
    @Ignore
    public void testRemovePunct_String_String() {
        System.out.println("removePunct");
        String str = "";
        String pattern = "";
        String expResult = "";
        String result = StringUtils.removePunct(str, pattern);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lengthRestriction method, of class StringUtils.
     */
    @Test
    @Ignore
    public void testLengthRestriction() {
        System.out.println("lengthRestriction");
        String str = "";
        int maxLength = 0;
        String expResult = "";
        String result = StringUtils.lengthRestriction(str, maxLength);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dateFormat method, of class StringUtils.
     */
    @Test
    public void testDateFormat_Date() {
        System.out.println("dateFormat");
        Date date = new Date(1538392661000l);
        String expResult = "2018.10.01-14.17.41";
        String result = StringUtils.dateFormat(date);
        assertEquals(expResult, result);
    }

    /**
     * Test of dateFormat method, of class StringUtils.
     */
    @Test
    public void testDateFormat_String() {
        System.out.println("dateFormat");
        String date = "Mon, 1 Oct 2018 14:17:41 +0300";
        Date expResult = new Date(1538392661000l);
        Date result = StringUtils.dateFormat(date);
        assertEquals(expResult, result);
    }
    
}
