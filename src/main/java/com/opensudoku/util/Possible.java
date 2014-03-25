/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides data storage of possible values for total 81 cells in Sudoku.
 *
 * @author mark
 */
public final class Possible implements Coordinate {

    private int[][] possible = new int[82][10];

    //  private int[] member;
    /**
     * Ready to use with default possible values from 1 to 9
     */
    public Possible() {
        init();
    }

    /**
     * Removes single possible value on specified cell
     *
     * @param cellId
     * @param val
     */
    public void removePossibleValue(int cellId, int val) {
//  
        possible[cellId][val] = 0;
    }

    public void setPossibleValue(int cellId, int val) {
//      
        resetPossible(cellId);
        possible[cellId][val] = val;
    }

    /**
     * to remove possible value on 3 groups, based on given cell id and assigned
     * value
     *
     * @param cellId
     * @param val
     */
    public void sweepGroups(int cellId, int val) {
//        System.out.println("id=" + cellId + ", val=" + val);
        //   int val=member[cellId];
        int grp1 = CELL_GROUPS[cellId][1];
        int grp2 = CELL_GROUPS[cellId][2];
        int grp3 = CELL_GROUPS[cellId][3];

        for (int n = 1; n <= 9; n++) {
            // given cell
            possible[cellId][n] = 0;
            // same row 
            possible[GROUP_MEMBERS[grp1][n]][val] = 0;
            // same col
            possible[GROUP_MEMBERS[grp2][n]][val] = 0;
            // same box
            possible[GROUP_MEMBERS[grp3][n]][val] = 0;
        }
    }

    /**
     * Returns summary of every cell's possible count
     *
     * @return
     */
    public int getCount() {
        int cnt = 0;
        for (int id = 1; id <= 81; id++) {
            cnt += getCount(id);
        }
        return cnt;
    }

    /**
     * Returns specified cell's possible count
     *
     * @param id
     * @return
     */
    public int getCount(int id) {
        int cnt = 0;
        for (int n = 1; n <= 9; n++) {
            if (possible[id][n] > 0) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * Returns first possible value on specified cell.
     *
     * @param id
     * @return
     */
    public int getFirstValue(int id) {
        //  int val = 0;
        for (int n = 1; n <= 9; n++) {
            if (possible[id][n] > 0) {
                return n;
            }
        }
        return 0;
    }

    /**
     * Returns possible value array of specified cell.
     *
     * @param id
     * @return
     */
    public int[] getValues(int id) {
//        List<Integer> list = new ArrayList<Integer>();
//        for (int n = 1; n <= 9; n++) {
//            if (possible[id][n] > 0) {
//                list.add(n);
//            }
//        }

        int[] list = new int[10];
        //    int cnt=0;
        for (int n = 1; n <= 9; n++) {
            if (possible[id][n] > 0) {
                list[n] = n;
            }
        }
        return list;
    }

    public int getFirstCellIdWithPossible() {
        //  int val = 0;
        for (int n = 1; n <= 81; n++) {
            if (getCount(n) > 0) {
                return n;
            }
        }
        return 0;
    }

    public List<Integer> getSinglePossibleArray() {
        List<Integer> single = new ArrayList<Integer>();

        for (int m = 1; m <= 81; m++) {
            // given ce
            if (this.getCount(m) == 1) {
                single.add(m);
            }
        }

        return single;
    }

    public int[][] getPossibleData() {
        return possible.clone(); // get clone

    }

    public void setPossibleData(int[][] data) {
        possible = data; // use given copy directly

    }

    public int[] getSingleArray() {
        int[] single = new int[162]; //***BUG??
//        int[] single = new int[164]; //***BUG??
        int cnt = 0;

        for (int m = 1; m <= 81; m++) {
            // given ce
            if (this.getCount(m) == 1) {
//                    System.out.println("cell#" + m + " has only one possible value "+getFirstValue(m));

                single[cnt++] = m;// id
                single[cnt++] = getFirstValue(m);// val

            }
        }

        return single;
    }

    public int[] getPossible(int id) {
//      returns clone array is safer 
//      by Mark , 2/14/2014
        return possible[id].clone();
    }

    public void setPossible(int id, int[] val) {
        this.possible[id] = val;
    }

    public void resetPossible(int id) {
        int[] val = new int[10];
        this.possible[id] = val;
    }

    /**
     * Initializes each cell's possible array to be {0,1,2,3,4,5,6,7,8,9}
     */
    public void init() {
        for (int id = 1; id <= 81; id++) {
            // System.out.printf("%2d ", id);

            for (int i = 0; i <= 9; i++) {
                possible[id][i] = i;
            }

        }
    }

    public void removeValueByGroup(int val, int grpId) {

    }

    public void xxxshow() {
        for (int id = 0; id < 81; id++) {
            System.out.printf("%2d ", id);

            for (int i = 1; i <= 9; i++) {
                System.out.printf("%d", possible[id][i]);

            }
            System.out.printf("\n");

        }
    }

    public String toString(int k) {
        if (k == 1) {
            StringBuilder sb = new StringBuilder();

            for (int id = 1; id <= 81; id++) {
                sb.append("cell#").append(id).append("{");
                for (int i = 1; i <= 9; i++) {
                    if (possible[id][i] > 0) {
                        sb.append(possible[id][i]);
                    }
                }

                if (id % 9 != 0) {
                    sb.append("}, ");
                } else {
                    sb.append("}\n");
                }
            }
            sb.append("possible value count:").append(this.getCount()).append("\n");
            return sb.toString();
        }

        return "--- not yet define ---";
    }

    /**
     * Shows designed output for command line process.
     */
    public void showCmd() {
        String rowHead = "0ABCDEFGHI";
        StringBuilder sb = new StringBuilder();
        sb.append(" --- Sudoku 9x9 possible --- (start)\n");
        int id = 0;
        int cellCnt = 0;
        for (int m = 1; m <= 9; m++) {
            sb.append(" row#").append(m).append(":");

            for (int n = 1; n <= 9; n++) {
                id = (m - 1) * 9 + n;
                boolean anyPossible = false;
                sb.append(rowHead.charAt(m)).append(n).append("=");
                sb.append("{");
                for (int i = 1; i <= 9; i++) {
                    if (possible[id][i] > 0) {
                        sb.append(possible[id][i]);
                        anyPossible = true;
                    }
                }
                sb.append("}");
                if (anyPossible) {
                    cellCnt++;
                }

                if (n < 9) {
                    sb.append(",");
                }
            }// end of id
            sb.append("\n");

        }// end of m

//
//          sb.append("single possible value: ");
//
//        int[] temp = getSingleArray();
//        for (int m = 0; m < 81; m = m + 2) {
//            if (temp[m] > 0) {
//                //answer.setMember(temp[m], temp[m + 1]);
//                //  System.out.println("cell#" + temp[m] + " is {" + temp[m + 1] + "}");
//                sb.append("\n  cell[").append(temp[m]).append("]=").append(temp[m + 1]).append(" " );
//
//            } else {
//                break;
//            }
//
//        }
        sb.append(" --- Sudoku 9x9 possible --- (end)");
        sb.append("  possible count:").append(this.getCount()).append("\n");
        sb.append("                                       cells count:").append(cellCnt).append("\n");
        System.out.println(sb.toString());
    }

    public void show() {

        StringBuilder sb = new StringBuilder();
        sb.append(" --- Sudoku 9x9 possible --- (start)\n");
        int id = 0;
        int cellCnt = 0;
        String rowHead = "0abcdefghi";

        for (int m = 1; m <= 9; m++) {
//            sb.append(" row#").append(m).append(":");
    sb.append(" (").append(m).append(")").append(rowHead.charAt(m)).append(":");

            for (int n = 1; n <= 9; n++) {
                id = (m - 1) * 9 + n;
                boolean anyPossible = false;
                sb.append("{");
                for (int i = 1; i <= 9; i++) {
                    if (possible[id][i] > 0) {
                        sb.append(possible[id][i]);
                        anyPossible = true;
                    }
                }
                sb.append("}");
                if (anyPossible) {
                    cellCnt++;
                }

                if (n < 9) {
                    sb.append(",");
                }
            }// end of id
            sb.append("\n");

        }// end of m

//
//          sb.append("single possible value: ");
//
//        int[] temp = getSingleArray();
//        for (int m = 0; m < 81; m = m + 2) {
//            if (temp[m] > 0) {
//                //answer.setMember(temp[m], temp[m + 1]);
//                //  System.out.println("cell#" + temp[m] + " is {" + temp[m + 1] + "}");
//                sb.append("\n  cell[").append(temp[m]).append("]=").append(temp[m + 1]).append(" " );
//
//            } else {
//                break;
//            }
//
//        }
        sb.append(" --- Sudoku 9x9 possible --- (end)");
        sb.append("  possible count:").append(this.getCount()).append("\n");
        sb.append("                                       cells count:").append(cellCnt).append("\n");
        System.out.println(sb.toString());
    }

    public void showSingle() {
        StringBuilder sb = new StringBuilder();
        sb.append("single possible value: ");

        int[] temp = getSingleArray();
        for (int m = 0; m < 81; m = m + 2) {
            if (temp[m] > 0) {
                //answer.setMember(temp[m], temp[m + 1]);
                //  System.out.println("cell#" + temp[m] + " is {" + temp[m + 1] + "}");
                sb.append("\n  cell[").append(temp[m]).append("]=").append(temp[m + 1]).append(" ");

            } else {
                break;
            }

        }

        //sb.append("\n--- Sudoku 9x9 possible --- (end)\n");
        sb.append("\n");
        System.out.println(sb.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Sudoku 9x9 possible --- (start)\n");
        int id = 0;
        for (int m = 1; m <= 9; m++) {
            sb.append("row#").append(m).append(":");

            for (int n = 1; n <= 9; n++) {
                id = (m - 1) * 9 + n;
                sb.append("{");
                for (int i = 1; i <= 9; i++) {
                    if (possible[id][i] > 0) {
                        sb.append(possible[id][i]);
                    }
                }
                sb.append("}");

                if (n < 9) {
                    sb.append(",");
                }
            }// end of id
            sb.append("\n");

        }// end of m

        int[] temp = getSingleArray();
        for (int m = 0; m < 81; m = m + 2) {
            if (temp[m] > 0) {
                //answer.setMember(temp[m], temp[m + 1]);
                //  System.out.println("cell#" + temp[m] + " is {" + temp[m + 1] + "}");
                sb.append("\n  cell[").append(temp[m]).append("]=").append(temp[m + 1]).append(" ");

            } else {
                break;
            }

        }

        sb.append("\n--- Sudoku 9x9 possible --- (end)");
        sb.append("possible value count:").append(this.getCount()).append(" ");
        sb.append("single possible value: ");

        return sb.toString();
    }
}
