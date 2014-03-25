/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.util;

import static com.opensudoku.util.Solver.show;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Solver can determine question is solvable or unsolvable, and only provide
 * first available answer only. SolverExt is designed to determine solvable
 * question has how many answers. We will focus on: (0)no answer (1) one answer
 * (2) two answers (3) more than 3 answers
 *
 * @author mark
 */
public class SolverExt extends Solver {

    private final int MAX_ANSWER_COUNT = 12;
    private int answerCnt;
    private final Sudoku[] max3Answers = new Sudoku[MAX_ANSWER_COUNT + 1];

    public Sudoku getAnswer(int k) {
        return max3Answers[k];
    }

    public int getAnswerCnt() {
        return answerCnt;
    }

    public void setAnswerCnt(int answerCnt) {
        this.answerCnt = answerCnt;
    }

    /**
     * autorun is rewritten, able to provide first 3 answers
     *
     * @param cmd
     * @return
     *
     */
    @Override
    public boolean runCommand(String cmd) {
        String str = "009036040008070310007000060000000050090642030070000000020000400081090600040580900";
//http://www.dailysudoku.com/sudoku//pdf/2014/01/2014-01-5_S2_N1_X.pdf
        String str1 = "000120000#710000920#003005008#109006080#060207040#040900603#400700800#031000064#000083000";

        // change row h from 031 to be 035, then it's broken
        String str2 = "000120000#710000920#003005008#109006080#060207040#040900603#400700800#035000064#000083000";
        String str3 = "500304000306020040100000000003100080008506400070002300000000004020050709000701000";

        switch (cmd) {
            case "help": {
                show(" ans:show command reference as follows,");
                show(" *====================================*");
                show(" sample: to assign question with default sample");
                show(" question: to show question ");
                show(" answer: to show answer ");
                show(" possible : to show possible ");
                show(" answer=possible : to assign asnwer with possible ");
                show(" possible=answer : to assign possible with answer ");
                show(" a1=2 : to assign cell a1 with value 2 ");
                show(" a=123456789: to assign row a with 123456789");
                show(" *====================================*\n");

                return true;
            }

            case "quit": {
                show(" ans:to end this application.");
                show(" *====================================*");
                show(" *   Thank You! See You Next Time!    *");
                show(" *====================================*\n");

                System.exit(0);
                return true;
            }

            case "status": {
                show(" ans:" + core.getStatus());

                return true;
            }

            case "sample": {
                show(" ans:sample question is set, as follows");
                getCore().getQuestion().setData(str);
                getCore().getQuestion().show();
                return true;
            }
            case "sample1": {
                show(" ans:sample1 question is set, as follows");
                getCore().getQuestion().setData(str1);
                getCore().getQuestion().show();
                getCore().getAnswer().init();
                getCore().getPossible().init();
                return true;
            }
            case "sample2": {
                show(" ans:sample2 question is set, as follows");
                getCore().getQuestion().setData(str2);
                getCore().getQuestion().show();
                getCore().getAnswer().init();
                getCore().getPossible().init();
                return true;
            }
            case "sample3": {
                show(" ans:sample3 question is set, as follows, QUESTION IS EMPTY FOR DEBUG...");
                getCore().getQuestion().setData(str3);
                getCore().getQuestion().show();
                getCore().getAnswer().init();
                getCore().getPossible().init();
                return true;
            }

            case "question": {
                show(" ans:show question, as follows");
                //   getCore().getQuestion().setData(str);
                getCore().getQuestion().show();
                return true;
            }
            case "answer": {
                show(" ans:show answer, as follows");
                //   getCore().getQuestion().setData(str);
                getCore().getAnswer().show();
                return true;
            }
            case "clean": {
                show(" ans:clean working pad is done");
                getCore().getAnswer().setSudoku(getCore().getQuestion().copy());
                getCore().getPossible().init();
                return true;
            }

            case "run": {
                show(" ans:run answer as it can, done!");
                getCore().run();
//                getCore().getAnswer().show();
                return true;
            }
            case "autorun": {
                show(" ans:autorun, done!   DEBUG... BY SOLVEREXT");
                run();
                switch (getAnswerCnt()) {
//                    case 0:
//                        show("   this question has no answer!");
//                        break;
//                    case 1:
//                        show("   this question has 1 answer!");
//                        getAnswer(0).show();
//                        break;
//                    case 2:
//                        show("   this question has 2 answers!");
//                        getAnswer(0).show();
//                        getAnswer(1).show();
//                        break;
//                    case 3:
//                        show("   this question has 3 or more answers!");
//                        getAnswer(0).show();
//                        getAnswer(1).show();
//                        getAnswer(2).show();
//                        break;
                    default:
                        if (getAnswerCnt() == MAX_ANSWER_COUNT) {
                            show("ans:this question has " + getAnswerCnt() + " or more answers!");

                        } else {
                            show("   this question has " + getAnswerCnt() + " answers!");
                        }
                        for (int k = 0; k < getAnswerCnt(); k++) {
                            show("\n   #" + (1 + k) + " answer:");

                            getAnswer(k).show();

                        }

                }
                return true;

            }

            case "possible": {
                show(" ans:show possible, as follows");
//                    solver.getCore();
                getCore().getPossible().show();
                return true;
            }
            case "question=answer": {
                show(" ans:assign question with answer, done!");
//                    solver.getCore();
                getCore().getQuestion().setSudoku(getCore().getAnswer().copy());
                getCore().getPossible().init();

                return true;
            }
            case "answer=question": {
                show(" ans:assign answer with question, done!");
//                    solver.getCore();
                getCore().getAnswer().setSudoku(getCore().getQuestion().copy());
                getCore().getPossible().init();

                return true;
            }

            default:
                // A1=2
                if (cmd.length() == 4) {
                    if (cmd.charAt(0) < 'a') {
                        break;
                    }
                    if (cmd.charAt(0) > 'i') {
                        break;
                    }
                    if (cmd.charAt(1) < '0') {
                        break;
                    }
                    if (cmd.charAt(1) > '9') {
                        break;
                    }
                    if (cmd.charAt(2) != '=') {
                        break;
                    }
                    if (cmd.charAt(3) < '0') {
                        break;
                    }
                    if (cmd.charAt(3) > '9') {
                        break;
                    }
                    //
                    // A1=2
                    //
                    char c0 = cmd.charAt(0);
                    int row = ABCEDFGHI.indexOf(c0);
                    int seq = Integer.parseInt(cmd.substring(1, 2));
                    int val = Integer.parseInt(cmd.substring(3, 4));

//                    show("...debug here," + cmd + " row#" + row + ", seq=" + seq + ", val=" + val);
                    //   show(seq+" ans:Set cell("  + ")=" + val);
                    show(" ans:assign value " + val + " to answer's cell " + ABCEDFGHI.charAt(row) + seq + " , done!");

                    getCore().getAnswer().setData(row, seq, val);
                    //     getCore().getQuestion().setSudoku(getCore().getAnswer().clone());
                    //   getCore().getPossible().init();

//                    show("...we assume question is ");
                    //   getCore().getQuestion().show();
//                    
//                    
//                    getCore().run();
//                    show("...and get answer ");
//                    if (getCore().isBroken()) {
//                        show("...it's broken ");
//                        getCore().getAnswer().show();
//                        getCore().getPossible().show();
//                        return true;
//                    }
//
//                    if (getCore().getAnswer().isSolved()) {
//                        show("ans:auto-run result, as follows ");
//                        getCore().getAnswer().show();
//                        show("ans:This Sudoku is solved!");
//                        return true;
//                    }
//                    show("...still ok, continue ");
//                    getCore().getAnswer().show();
                    return true;
                }

                // A=123456789, 
                if (cmd.length() == 11) {
                    Pattern p = Pattern.compile("[a-i]=\\d\\d\\d\\d\\d\\d\\d\\d\\d");
                    Matcher m = p.matcher(cmd);
                    if (m.matches()) {
                        int row = ABCEDFGHI.indexOf(cmd.charAt(0));
                        int v1 = cmd.charAt(2) - '0';
                        int v2 = cmd.charAt(3) - '0';
                        int v3 = cmd.charAt(4) - '0';
                        int v4 = cmd.charAt(5) - '0';
                        int v5 = cmd.charAt(6) - '0';
                        int v6 = cmd.charAt(7) - '0';
                        int v7 = cmd.charAt(8) - '0';
                        int v8 = cmd.charAt(9) - '0';
                        int v9 = cmd.charAt(10) - '0';
                        core.getAnswer().setData(row, v1, v2, v3, v4, v5, v6, v7, v8, v9);
                    }
                    show(" ans:assign " + cmd.substring(2) + " to answer's row " + cmd.charAt(0) + ", done!");
                    return true;
                }
                if (cmd.startsWith("set question=")) {
                    getCore().getQuestion().setData(cmd);
                    //show(" ans:set question="+getCore.getQuestion(). done! ");
                    show(" ans:set question=" + getCore().getQuestion().toString() + ", done!");
                    show(" ans:show question, as follows");
                    //   getCore().getQuestion().setData(str);
                    getCore().getQuestion().show();
                    return true;

                }
        }
        return false;
    }

    @Override
    public void run() {
        // ver 1.7
        answerCnt=0;
        
        Sudoku temp = new Sudoku();
        temp = core.getQuestion().copy();
        core.run();
//        core.show();

        // only one answer
        if (core.getAnswer().isSolved()) {
            setAnswerCnt(ANSWER_COUNT_IS_1);
            return;
        }

        // broken on appearance
        if (core.getAnswer().isBroken()) {
            setAnswerCnt(ANSWER_COUNT_IS_0);
            return;
        }

        //
        // new Stack
        //
        stack = new Stack<Sudoku>();
        List<Integer> branch = core.getBranch();
        int id = core.getBranchCellId();
        //      show(" ...after Core's run \n show List: " + branch.toString());
        for (Integer val : branch) {
            Sudoku newQuestion = new Sudoku();
            newQuestion = core.getAnswer().copy();
//            show(" ... cell#" + id + " with assigned value " + val.toString());
            newQuestion.setData(id, val);
//            newQuestion.show();
            stack.push(newQuestion);
//            core.getQuestion().setSudoku(newQuestion);
//            core.run();
//            core.show();

        }
        run2();

        // ver 1.5
        core.getQuestion().setSudoku(temp);
        // ver 1.6
        // when no answer, clean answer
        if (getAnswerCnt()==0){
            core.getAnswer().init();
        }
        

    }

    /**
     * Purpose is to use run2 to get max 3 answers
     *
     * @return (1:got answer , 0:given question is broken ??? TODO )-1:out of
     * stack
     *
     */
    private int run2() {
        int result = 0;
        Sudoku s = new Sudoku();
        while (!stack.isEmpty()) {
            s = stack.pop();
            core.getQuestion().setSudoku(s);
            core.run();
//            core.show();

            if (core.getAnswer().isSolved()) {
//                show(" *** Game Over  ***");
//                show(" *** Solved!!!  ***");
                show ("DEBUG... answerCnt"+answerCnt);
                max3Answers[answerCnt++] = core.getAnswer().copy();
                if (answerCnt == MAX_ANSWER_COUNT) {
                    return MAX_ANSWER_COUNT;
                }
                continue;
//                return 1;
            }
            if (core.getAnswer().isBroken()) {
//                show(" *** Game Over  ***");
//                show(" *** xxx broken xxx  ***");
                continue;
            }

            //
            // next level branch
            //
            List<Integer> branch = core.getBranch();
            int id = core.getBranchCellId();
            //      show(" ...after Core's run \n show List: " + branch.toString());
            for (Integer val : branch) {
                Sudoku newQuestion = new Sudoku();
                newQuestion = core.getAnswer().copy();
                newQuestion.setData(id, val);

                // since going to get max3 answers only, no concern of StackOverflow
                stack.push(newQuestion);
            }

            // return result;
        }
        return -1;// no solution, out of stack
    }

}
