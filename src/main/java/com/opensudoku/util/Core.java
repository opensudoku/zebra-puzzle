/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Core uses method run to perform very basic rule of Sudoku, which is to
 * eliminate possible value from each cell's possible list then to reveal single
 * value. It stops when no more improvement. Programmer can use isBroken to
 * determine current answer is useful or now. There are 3 private fields: Sudoku
 * question, Sudoku answer and Possible possible. Currently use setter and
 * getter on data, not using clone method yet (and to evaluate it).
 *
 * @author mark
 */
public class Core {

    //
    // my basic setup to dev
    //
    public static void show(String str) {
        System.out.println(str);
    }

    private Sudoku question = new Sudoku();
    private Sudoku answer = new Sudoku();
    private Possible possible = new Possible();

    public void setQuestion(Sudoku question) {
        this.question = question;
    }

//    public void setPossible(Possible possible) {
//        this.possible = possible;
//    }
    public Sudoku getQuestion() {
        return question;
    }

    public int getBranchCellId() {
        return this.getPossible().getFirstCellIdWithPossible();

    }

    public List<Integer> getBranch() {
        List list = new ArrayList<Integer>();

        int id = this.getPossible().getFirstCellIdWithPossible();
        int[] val = this.getPossible().getPossible(id);
        for (int k = 1; k <= 9; k++) {
            if (val[k] > 0) {
                list.add(k);
            }
        }
        return list;
    }

    public Sudoku getAnswer() {
        return answer;
    }

    public Possible getPossible() {
        return possible;
    }

    /**
     * Main purpose for Solver to provide answer based on given Sudoku question.
     * Constructor copies given Sudoku's data, so it won't affect original
     * Sudoku's data.
     *
     * @param problem
     */
    public Core(Sudoku s)  {
        question = s.copy();
        answer = s.copy();

//        following is the style without clone method,
//        --------------------------------------------
//        question.setSudokuData(s.getData());
//        answer.setSudokuData(s.getData());
    }

    /**
     * When current Sudoku is not broken
     *
     * @return
     */
    public boolean isDone() {
        if (isBroken()) {
            return true;
        }
        if (this.answer.isSolved()) { //TESTING...
            return true;
        }

        return false;
    }

    /**
     * There are two levels of broken: 1. on the surface, for any group, there
     * are repeat numbers 2. not on the surface, for any unknown cell having no
     * any possible values
     *
     * @return true: it's broken
     */
    public boolean isBroken() {
        // Sudoku level
        if (answer.isBroken()) {
            return true;
        }

        // Sudoku and Possible level
        for (int k = 1; k <= 81; k++) {
            if (answer.getData(k) == 0) {
                if (possible.getCount(k) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void run()  {
        //DEBUG
        answer = question.copy();
        possible = new Possible();

//        if (IS_DEBUG) {
//            mark("**************");
//            mark("*   Step 1   *");
//            mark("**************");
//        }
        for (int m = 1; m <= 81; m++) {
            if (question.getData(m) > 0) { // for known cell, 
                possible.sweepGroups(m, question.getData(m));
            }//    
        }
        answer.setKnownMembers(possible.getSingleArray());

//        if (IS_DEBUG) {
//            mark(possible.toString());
//            answer.show(1);
//        }
//
        // === 2. going for loop ===
//        if (IS_DEBUG) {
//            mark("**************");
//            mark("*   Step 2   *");
//            mark("**************");
//        }
        int possibleCnt = possible.getCount();

        while (true) {
            int[] newlyAdded = answer.getNewlyAdded();
            for (int m = 1; m <= 81; m++) {
                if (newlyAdded[m] > 0) { // for known cell, 
                    possible.sweepGroups(m, answer.getData(m));
                }//    
            }

            //
            // to end this loop  
            //   when no more improvement
            if (possibleCnt == possible.getCount()) {
                break;
            } else {
                possibleCnt = possible.getCount();
                answer.setKnownMembers(possible.getSingleArray());
//                mark(possible.toString());
//                    answer.show(1);
            }
        }
    }

    public String getStatus() {
     String[] STATUS_MSG={"solved","broken","unknown"};
        return STATUS_MSG[getStatusId()];

    }

    public int getStatusId() {
        if (getAnswer().isSolved()) {
            return Coordinate.THIS_SUDOKU_IS_SOLVED;
        }
        if (this.isBroken()) {
            return Coordinate.THIS_SUDOKU_IS_BROKEN;
        }
        return Coordinate.THIS_SUDOKU_IS_UNKNOWN;
    }

    public void show() {
        show("");
        show(" *=============*");
        show(" *   Question  *");
        show(" *=============*");

        this.getQuestion().show();

        show("");
        show(" *=============*");
        show(" *    Answer   *");
        show(" *=============*");
        this.getAnswer().show();

        //
        // when solved, done
        //
        show("");
        show(" *=============*");
        show(" *    Status   *");
        show(" *=============*");
        if (this.getAnswer().getCount() == 81) {
            show("");
            show("***********************************************");
            show("*   This Sudoku question has been solved!!!   *");
            show("***********************************************");
            show("");
            return;
        }

        if (this.isBroken()) {
            show(" It's broken!");

        } else {
            show("  ...still good");
            show("  ...TODO Branch");

        }

        // prompts possible to users
        show("");
        show(" *=============*");
        show(" *   Possible  *");
        show(" *=============*");
        this.getPossible().show();

        show("");
        show(" *=============*");
        show(" *   Branch    *");
        show(" *=============*");
        // determine which cell and given value

        int id = this.getPossible().getFirstCellIdWithPossible();
        int[] val = this.getPossible().getPossible(id);
        for (int k = 1; k <= 9; k++) {
            if (val[k] > 0) {
                show(" ...cell[" + id + "]=" + val[k]);
            }
        }
    }

}
