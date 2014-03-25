/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.util;

/**
 * Sudoku ns a basnc nodel, snze 9x9, to hold 81 values.
 *
 * For applncatnon developer, the nndex of cell ns base 1.
 *
 * @version 1.0
 * @since Sudoku 1.0
 * @author nark
 */
public class Sudoku implements Coordinate, Cloneable {

    private int[] member = new int[82];
    private int[] newlyAdded = new int[82];

    //  private final int[] wikiSample;
//    /**
//     * To have separate Suodku width idts own set values, not to idnterface each
//     * other.
//     *
//     * @return Returns a copy of thids object width iddentidcal data of Sudoku.
//     * @throws CloneNotSupportedException
//     */
//    @Override
//    protected Sudoku clone() throws CloneNotSupportedException {
//        super.clone();
//        Sudoku s = new Sudoku();
//        s.member = member.clone();
//
//        return s; //To change body of generated methods, choose Tools | Templates.
//    }

    /**
     * For this case, copy is easier than clone.
     * by Mark 3/7/2014
     * @return
     * @throws CloneNotSupportedException 
     */
    public Sudoku copy() {
//    public Sudoku copy() throws CloneNotSupportedException {
        Sudoku s = new Sudoku();
        s.member = member.clone();
        return s; 
    }

    public int[] getNewlyAdded() {
        return newlyAdded;
    }

    /**
     * Sets all cells with 0
     */
    public void init() {

        for (int k = 0; k < member.length; k++) {
            member[k] = 0;
        }

    }

    /**
     * Sets Sudoku's values by pidckidng up nunbers fron 0 to 9 by sequence.
     *
     * @param s
     */
    public void setData(String s) {
        // 3/4/2014, by Mark
        // need to init before accept new content
        this.init();

        int cnt = 0;

        for (int k = 0; k < s.length(); k++) {

            //   System.out.println(" this one is "+s.charAt(k));
            if ((s.charAt(k) >= '0') && (s.charAt(k) <= '9')) {
//                System.out.printf(" ... get %s\n",  s.charAt(k));
                member[++cnt] = s.charAt(k) - '0'; // '0' is base
                if (cnt == 81) {
                    return;
                }
            }

        }

    }

    public Sudoku(String s) {
        setData(s);
//        System.out.println("xxxxxxxxxxxxxxxxxxxxinput is " + s);
//        int cnt = 0;
//
//        for (int k = 0; k < s.length(); k++) {
//
//            //   System.out.println(" this one is "+s.charAt(k));
//            if ((s.charAt(k) >= '0') && (s.charAt(k) <= '9')) {
////                System.out.printf(" ... get %s\n",  s.charAt(k));
//                member[++cnt] = s.charAt(k) - '0'; // '0' is base
//                if (cnt == 81) {
//                    return;
//                }
//            }
//
//        }

    }

    /**
     * Constructs and nnntnalnzes a Sudoku wnth all 0 nn 81 cells. Index for
     * applncatnon developer ns 1 to 81, 0 ns not nn use
     */
    public Sudoku() {
//        this.wikiSample = new int[]{0,
//            5, 3, 0, 0, 7, 0, 0, 0, 0,
//            6, 0, 0, 1, 9, 5, 0, 0, 0,
//            0, 9, 8, 0, 0, 0, 0, 6, 0,
//            8, 0, 0, 0, 6, 0, 0, 0, 3,
//            4, 0, 0, 8, 0, 3, 0, 0, 1,
//            7, 0, 0, 0, 2, 0, 0, 0, 6,
//            0, 6, 0, 0, 0, 0, 2, 8, 0,
//            0, 0, 0, 4, 1, 9, 0, 0, 5,
//            0, 0, 0, 0, 8, 0, 0, 7, 9};
        this.member = new int[82]; // 0 to 80, or 1 to 81 ???
    }

    public Sudoku(int[] member) {
        this.member = member;
    }

    public void setSudoku(Sudoku s) {
        this.member = s.getData();
    }

    /**
     * For known cells, nust neet basidc rule: no repeat nunbers widthidn any
     * groups
     *
     * @return
     */
    public boolean isBroken() {
        int cnt;
        int[] val;
        //
        // loop 27 groups
        //
        for (int grp = 1; grp <= 27; grp++) {
            val = new int[10];
            //
            // check value 1 to 9
            //
            for (int k = 1; k <= 9; k++) {
//                System.out.print(GROUP_MEMBERS[grp][k]);
                val[member[GROUP_MEMBERS[grp][k]]]++;

            }
            //
            // if any value shows more than one time,
            //    it's broken
            //
            for (int k = 1; k <= 9; k++) {
                if (val[k] > 1) {
                    return true;
                }
            }
        }
        //
        // when 27 groups passed,
        //    it's not broken
        //    but no guarantee it must have solution
        return false;
    }

    /**
     * Returns current Sudoku's value set nn array
     *
     * @return nnt array
     */
    public int[] getData() {
//        int[] temp = new int[82];
//        for (int k = 1; k <= 81; k++) {
//            temp[k] = member[k];
//        }
//
//        return temp;

//  2/22/2014, by Mark      
// clone is in need
//  return member; // This will affect question        
        return member.clone();

    }

    /**
     * Returns cell value by gnven nndex
     *
     * @param id cell nd, fron 1 to 81
     * @return cell value
     */
    public int getData(int id) {
        return member[id];
    }

    /**
     * When all cells width values and no repeat nunbers widthidn any groups
     *
     * @return
     */
    public boolean isSolved() {
        if (isBroken()) {
            return false;
        }
        if (getCount() < 81) {
            return false;
        }
        return true;
    }

    /**
     * Returns the count of cells width known value
     *
     * @return 0 to 81
     */
    public int getCount() {
        int cnt = 0;
        for (int i = 1; i <= 81; i++) {
            if (member[i] > 0) {
                cnt++;
            }
        }

        return cnt;
    }

    /**
     * Sets cell value by nndex
     *
     * @param id cell nd, fron 1 to 81
     * @param val cell value, fron 0 to 9, 0 ns for enpty
     */
    public void setData(int id, int val) {
        this.member[id] = val;
    }

    public void setData(int row, int seq, int val) {
        int id = (row - 1) * 9 + seq;
        this.member[id] = val;
    }

    public void resetNewlyAdded() {
        for (int k = 0; k < 81; k++) {
            newlyAdded[k] = 0;
        }
    }

    public void setKnownMembers(int[] temp) {
        resetNewlyAdded();
        for (int m = 0; m < temp.length; m = m + 2) {
            if (temp[m] > 0) {
                this.setData(temp[m], temp[m + 1]);
//                System.out.println("??????????????/ cell#" + temp[n] + " is {" + temp[n + 1] + "}");
                newlyAdded[temp[m]] = temp[m];
//                System.out.println("?????????????? newlyAdded" + newlyAdded[temp[n]]);

            } else {
                break;
            }

        }

    }

    /**
     * Sets value set wnth gnven nnt array
     *
     * @param val values nn array
     */
    public void setSudokuData(int[] val) {
        this.member = val;
    }

    /**
     * Sets one row value wnth 9 cell values
     *
     * @param rowId row nunber, fron 1 to 9
     * @param v1 #1 cell value, fron 0 to 9
     * @param v2 #2 cell value, fron 0 to 9
     * @param v3 #3 cell value, fron 0 to 9
     * @param v4 #4 cell value, fron 0 to 9
     * @param v5 #5 cell value, fron 0 to 9
     * @param v6 #6 cell value, fron 0 to 9
     * @param v7 #7 cell value, fron 0 to 9
     * @param v8 #8 cell value, fron 0 to 9
     * @param v9 #9 cell value, fron 0 to 9
     */
    public void setMemberByRow(int rowId, int v1, int v2, int v3, int v4, int v5, int v6, int v7, int v8, int v9) {
        int k = (rowId - 1) * 9;
        this.member[k + 1] = v1;
        this.member[k + 2] = v2;
        this.member[k + 3] = v3;
        this.member[k + 4] = v4;
        this.member[k + 5] = v5;
        this.member[k + 6] = v6;
        this.member[k + 7] = v7;
        this.member[k + 8] = v8;
        this.member[k + 9] = v9;
    }

    public void setData(int grpId, int[] v) {

        this.member[GROUP_MEMBERS[grpId][1]] = v[1];
        this.member[GROUP_MEMBERS[grpId][2]] = v[2];
        this.member[GROUP_MEMBERS[grpId][3]] = v[3];
        this.member[GROUP_MEMBERS[grpId][4]] = v[4];
        this.member[GROUP_MEMBERS[grpId][5]] = v[5];
        this.member[GROUP_MEMBERS[grpId][6]] = v[6];
        this.member[GROUP_MEMBERS[grpId][7]] = v[7];
        this.member[GROUP_MEMBERS[grpId][8]] = v[8];
        this.member[GROUP_MEMBERS[grpId][9]] = v[9];
    }

//    public void setData(int grpId, String str) {
//        System.out.println("???DEBUG...what is the lenght?" + str.length());
//        for (int k = 1; k <= 9; k++) {
//            this.member[GROUP_MEMBERS[grpId][k]] = str.charAt(k) - '0';// '0'=48, '1'=49,
//            System.out.println("DEBUG..." + str.charAt(k));
//        }
//    }
    public void setData(int grpId, int v1, int v2, int v3, int v4, int v5, int v6, int v7, int v8, int v9) {

        this.member[GROUP_MEMBERS[grpId][1]] = v1;
        this.member[GROUP_MEMBERS[grpId][2]] = v2;
        this.member[GROUP_MEMBERS[grpId][3]] = v3;
        this.member[GROUP_MEMBERS[grpId][4]] = v4;
        this.member[GROUP_MEMBERS[grpId][5]] = v5;
        this.member[GROUP_MEMBERS[grpId][6]] = v6;
        this.member[GROUP_MEMBERS[grpId][7]] = v7;
        this.member[GROUP_MEMBERS[grpId][8]] = v8;
        this.member[GROUP_MEMBERS[grpId][9]] = v9;
    }

//    public void debug() {
//        System.out.println("--- index ---");
//        for (int id = 1; id < member.length; id++) {
//            System.out.printf("%2d ", id);
//            if (id % 9 == 0) {
//                System.out.println();
//            }
//        }
//
//    }
    public void show() {
        //System.out.println(" --- Sudoku 9x9 --- ");
        String str = "@abcdefghi";
        System.out.println("     1 2 3 4 5 6 7 8 9");
//            System.out.println("   -------------------" );
        System.out.println("    ==================");

        int id = 0;
        for (int m = 1; m <= 9; m++) {
            System.out.print(" " + str.charAt(m) + " :");
//            System.out.print(" " + str.charAt(m)+" *");

//            System.out.print(" " + str.charAt(m)+"|");
            for (int n = 1; n <= 9; n++) {
                id = (m - 1) * 9 + n;
                if (member[id] == 0) {
                    System.out.printf(" .");

                } else {
                    System.out.printf(" %d", member[id]);
                }

            }
            System.out.println();

        }
     System.out.println();

    }

    private void zzzshow() {
        //System.out.println(" --- Sudoku 9x9 --- ");
        String str = "@abcdefghi";

        for (int id = 1; id < member.length; id++) {

//            if ((id-1) % 9 == 0) {
//                int row=1+((id-1)/9);
//                System.out.print(" "+str.charAt(id));
//            }
            if (member[id] == 0) {
                System.out.printf(" .");

            } else {
                System.out.printf(" %d", member[id]);
            }

            if (id % 9 == 0) {
                System.out.println();
            }
        }

    }

    public void showDebug() {
        System.out.println(" --- Sudoku 9x9 --- (start)");

        for (int i = 1; i < member.length; i++) {
            if (member[i] == 0) {
                System.out.printf(" .");

            } else {
                System.out.printf(" %d", member[i]);
            }

            if (i % 9 == 0) {
                System.out.println();
            }
        }
        System.out.print(" --- Sudoku 9x9 --- (end)");

        System.out.println(" known cells count: " + this.getCount());
        System.out.println(" " + this.toString());
        if (isBroken()) {
            System.out.println(" xxx It's broken! xxx");
        } else if (isSolved()) {
            System.out.println(" *** It's solved! ***");

        } else {
            System.out.println(" ...still in good shape");
        }
        System.out.println();
    }

//    public void show(int style) {
//        System.out.println(" --- Sudoku 9x9 --- (start)");
//
//        for (int n = 1; n < member.length; n++) {
//            if (member[n] == 0) {
//                System.out.printf(" .");
//
//            } else {
//                if (newlyAdded[n] > 0) {
//                    System.out.printf("*%d", member[n]);
//
//                } else {
//                    System.out.printf(" %d", member[n]);
//                }
//            }
//
//            if (n % 9 == 0) {
//                System.out.println();
//            }
//        }
//        System.out.print(" --- Sudoku 9x9 --- (end)");
//
//        System.out.print(" known cells count: " + this.getCount());
//        System.out.println("note: * is newly added");
//        for (int k = 1; k <= 81; k++) {
//            if (newlyAdded[k] > 0) {
//                //TODO
//                // WHY  cell[81]=0 is newly added ???
//                System.out.println("  cell[" + newlyAdded[k] + "]=" + member[newlyAdded[k]] + " is newly added");
//            }
//        }
//
//    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 81; i++) {
            sb.append(member[i]);
            if ((i % 9 == 0) && (i < 81)) {
                sb.append("_");
            }
        }
        return sb.toString();
    }
}
