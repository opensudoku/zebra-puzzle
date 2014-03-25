/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livehereandnow.sudoku.util;

import com.opensudoku.util.Sudoku;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author mark
 */
public class SudokuTest {

    public SudokuTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    static void show(String str) {
        System.out.println(str);
    }

    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test of getNewlyAdded method, of class Sudoku.
     */
//    @Test
//    public void testGetNewlyAdded() {
//        System.out.println("doing here...");
//        String str = ""
//                + "5, 3, 0, 0, 7, 0, 0, 0, 0,"
//                + "6, 0, 0, 1, 9, 5, 0, 0, 0,"
//                + "0, 9, 8, 0, 0, 0, 0, 6, 0,"
//                + "8, 0, 0, 0, 6, 0, 0, 0, 3,"
//                + "4, 0, 0, 8, 0, 3, 0, 0, 1,"
//                + "7, 0, 0, 0, 2, 0, 0, 0, 6,"
//                + "0, 6, 0, 0, 0, 0, 2, 8, 0,"
//                + "0, 0, 0, 4, 1, 9, 0, 0, 5,"
//                + "0, 0, 0, 0, 8, 0, 0, 7, 9,";
//        Sudoku instance = new Sudoku(str);
//
//        instance.show();
//    }

    @Test
    public void testIsBroken() {

        Sudoku s = new Sudoku();
        s.setData(1, 1, 2, 3, 4, 5, 6, 7, 8, 8);
        s.show();
        assertTrue(s.isBroken());
        s.setData(1, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        s.show();
        assertFalse(s.isBroken());
        String  str = "530070000_600195000_098000060_800060003_400803001_700020006_060000280_000419005_000080079";
        s.setData(str);
        s.show();
        str = "358942617_476183925_291567348_934275861_825416739_167839452_713628594_582394176_649751283";
        s.setData(str);
        s.show();
        

    }

}
