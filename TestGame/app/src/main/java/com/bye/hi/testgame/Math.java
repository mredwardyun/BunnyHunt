package com.bye.hi.testgame;
import android.util.Log;

import java.util.Random;

public class Math {
    private static int answer;
    private static int first;
    private static int second;
    private static Boolean b1;
    private static Boolean b2;

    // precondition: difficulty is 1-5
    public static String mathRedemption (int difficulty) {
        Random r = new Random();
        randomizeMath(r, difficulty);
        Log.i("Math", generate(r, b1, b2));
        Log.i("Math", "answer is " + answer);
        return "";
    }

    public static String answerMe() {
        return "" + answer;

    }

    // returns a math equation as a string and stores the answer globally
    // Boolean op1, op2 determine the mathematical operation
    // 00 - add     01 - sub     10 - mul     11 - div
    public static String generate(Random r, Boolean op1, Boolean op2) {
        if (!op1) {
            first = r.nextInt(100) + 1;
            second = r.nextInt(100) + 1;
            if (!op2) {
                answer = first + second;
                return first + " + " + second;
            } else {
                if (first < second) {
                    int temp = first;
                    first = second;
                    second = first;
                }
                answer = first - second;
                return first + " - " + second;
            }
        }
        else {
            first = r.nextInt(10) + 1;
            second = r.nextInt(10) + 1;
            if (!op2) {
                answer = first * second;
                return first + " * " + second;
            }
            else {
                answer = second;
                return (first*second) + " / " + first;
            }
        }
    }

    public static void randomizeMath(Random r, int difficulty) {
        int operation = r.nextInt(difficulty) + 1;
        if (operation == 1) {
            b1 = false;
            b2 = false;
        } else if (operation == 2) {
            b1 = true;
            b2 = false;
        } else if (operation == 3) {
            b1 = false;
            b2 = true;
        } else if (operation == 4 || operation == 5) {
            b1 = true;
            b2 = true;
        }
        else {
            Log.i("Math", "difficulty level was not accepted");
        }
    }
}
