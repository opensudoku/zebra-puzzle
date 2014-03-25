/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livehereandnow.sudoku.util;

import com.opensudoku.util.Possible;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mark
 */
public class PossibleTest {

    public PossibleTest() {
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
     * Test of show method, of class Possible.
     */
    @Test
    public void testToPrint() {
        System.out.println("toPrint");
        Possible instance = new Possible();
        instance.show();
        instance.init();
        instance.show();
        
        // TODO review the generated test code and remove the default call to fail.
        //       fail("The test case is a prototype.");
    }

}
