package com.bye.hi.testgame;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class Activity {
    public static ImageView heart1 = null;
    public static ImageView heart2 = null;
    public static ImageView heart3 = null;

    public static ImageView halfheart1 = null;
    public static ImageView halfheart2 = null;
    public static ImageView halfheart3 = null;



    public static int numLives = 3;

    public static void loseLife() {
        numLives--;

        if (numLives==2) {
            heart3.setVisibility(View.INVISIBLE);
            halfheart3.setVisibility(View.VISIBLE);
        }
        else if (numLives==1) {
            heart2.setVisibility(View.INVISIBLE);
            halfheart2.setVisibility(View.VISIBLE);
        }
        else if (numLives==0) {
            heart1.setVisibility(View.INVISIBLE);
            halfheart1.setVisibility(View.VISIBLE);
        }
    }


    public static void gainLife() {
        numLives++;

        if (numLives==1) {
            heart1.setVisibility(View.VISIBLE);
            halfheart1.setVisibility(View.GONE);
        }
        else if (numLives==2) {
            heart2.setVisibility(View.VISIBLE);
            halfheart2.setVisibility(View.GONE);
        }
        else if (numLives==3) {
            heart3.setVisibility(View.VISIBLE);
            halfheart3.setVisibility(View.GONE);
        }
    }
    public static void resetHearts() {
        numLives = 3;
        heart3.setVisibility(View.VISIBLE);
        heart2.setVisibility(View.VISIBLE);
        heart1.setVisibility(View.VISIBLE);

        halfheart3.setVisibility(View.GONE);
        halfheart2.setVisibility(View.GONE);
        halfheart1.setVisibility(View.GONE);
    }



    public static int randomNumber(int num) {
        Random rand = new Random();
        return rand.nextInt(num);
    }





}
