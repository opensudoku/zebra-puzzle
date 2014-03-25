/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opensudoku.util;

//import static com.livehereandnow.sudoku.app.Main.show;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Solver has a Core to perform basic solving technique. Solver provides
 * branching features to automatically create game tree.
 *
 * @author mark
 */
public class Solver implements Coordinate {

    protected Stack<Sudoku> stack;
    protected final Core core;
    protected final String ABCEDFGHI = "@abcdefghi";

    /**
     *
     */
    public Solver()  {
        Sudoku question = new Sudoku();

        core = new Core(question);
    }

//
//    public Solver(Core core) {
//        this.core = core;
//    }
    static void show(String str) {
        System.out.println(str);
    }

    public Core getCore() {
        return core;
    }

    public boolean runCommand(String cmd){
        String str = "009036040008070310007000060000000050090642030070000000020000400081090600040580900";
//http://www.dailysudoku.com/sudoku//pdf/2014/01/2014-01-5_S2_N1_X.pdf
        String str1 = "000120000#710000920#003005008#109006080#060207040#040900603#400700800#031000064#000083000";

        // change row h from 031 to be 035, then it's broken
        String str2 = "000120000#710000920#003005008#109006080#060207040#040900603#400700800#035000064#000083000";
        String str3 = "";

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
                show(" ans:autorun, done!");
                run();
                show(" ans:" + core.getStatus());
                if (core.getStatusId() == Coordinate.THIS_SUDOKU_IS_SOLVED) {
                    getCore().getAnswer().show();
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
        }
        return false;
    }

    public void run()  {
        Sudoku temp=new Sudoku();
        temp=core.getQuestion().copy();
        core.run();
//        core.show();

        // only one answer
        if (core.getAnswer().isSolved()) {
//            show(" *** Game Over  ***");
//            show(" *** Solved!!!  ***");
            return;
        }

        // broken on appearance
        if (core.getAnswer().isBroken()) {
//            show(" *** Game Over  ***");
//            show(" *** xxx broken xxx  ***");
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

        //3/8/2014
        core.getQuestion().setSudoku(temp);
//        show(" just show last try");
//        core.getAnswer().show();
    }

    private int run2()  {
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
                return 1;
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
//                show(" ... cell#" + id + " with assigned value " + val.toString());
                newQuestion.setData(id, val);
//            newQuestion.show();

                stack.push(newQuestion);
            }

            // return result;
        }
        return -1;// no solution
    }

    public void show() {
        // core.run();
//        show(" ...DOING show");
    }
}
