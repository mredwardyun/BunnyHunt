package com.bye.hi.testgame;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Random;

import static android.view.View.OnClickListener;


public class bathroom extends ActionBarActivity {

    private static int totalNum = 5;
    private boolean levelClear = false;
    private static int hintNum= 0;

    public static ImageButton duck = null;
    public static ImageButton shampoo = null;
    public static ImageButton bathtub = null;
    public static ImageButton towel = null;
    public static ImageButton sink = null;
    public static ImageButton rug = null;

    public static Button hintButton = null;
    public static ImageButton levelUp = null;


    PopupWindow promptGuess = null;
    PopupWindow lifeWindow = null;

    TextView clueDisplay = null;
    public static TextView hintDisplay = null;
    TextView msgDisplay = null;
    Button submit = null;

    public static int curClueNum = 0;


    public static Boolean[] clueState = {false,false,false,false,false};

    public static String[] myObj = new String[totalNum];
    public static String[] objArray = {"ducky","shampoo","bathtub","towel","sink"};

    public static String[] myClue = new String[totalNum];
    public static String[] clueArray = {
            "My best friend and my buddy,\n" +
                    "My lovely rubber _ _ _ _ _",

            "At school today we played with goo,\n" +
                    "It's in my fur, so grab the _ _ _ _ _ _ _",

            "Splash! Splash! Time for a good scrub!\n" +
                    "I love to play around in my  _ _ _ _ _ _ _.",

            "I take a shower when I smell foul\n" +
                    "Then dry off with my fluffy warm  _ _ _ _ _",

            "I brush my teeth, and watch myself blink,\n" +
                    "I wash my hands in my sparkly  _ _ _ _"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom);

        duck = (ImageButton) this.findViewById(R.id.duck);
        shampoo = (ImageButton) this.findViewById(R.id.shampoo);
        bathtub = (ImageButton) this.findViewById(R.id.bathtub);
        towel = (ImageButton) this.findViewById(R.id.towel);
        sink = (ImageButton) this.findViewById(R.id.sink);
        rug = (ImageButton) this.findViewById(R.id.rug);

        hintButton = (Button) this.findViewById(R.id.hintButtonBath);
        levelUp = (ImageButton) findViewById(R.id.levelUpBath);


        Activity.heart1 = (ImageView) this.findViewById(R.id.heart1bath);
        Activity.heart2 = (ImageView) this.findViewById(R.id.heart2bath);
        Activity.heart3 = (ImageView) this.findViewById(R.id.heart3bath);

        Activity.halfheart1 = (ImageView) this.findViewById(R.id.halfheart1bath);
        Activity.halfheart2 = (ImageView) this.findViewById(R.id.halfheart2bath);
        Activity.halfheart3 = (ImageView) this.findViewById(R.id.halfheart3bath);

        clueDisplay = (TextView) findViewById(R.id.hintTextBath);
        hintDisplay = (TextView) findViewById(R.id.dispHintBath);

        initializeArrays();
        Activity.resetHearts();
        initializeClueAndObject(curClueNum);

        duck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("duckie");
            }

        });

        shampoo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("shampoo");
            }

        });

        bathtub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("bathtub");
            }
        });

        towel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("towel");
            }
        });

        sink.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("sink");
            }
        });

        rug.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Activity.loseLife();
                lifePopupWindow();
            }
        });

        hintButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                    displayHint();
                }


        });

        levelUp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                nextLevel();
            }
        });


    }

    public void objClick(String obj) {
        int clueNum=0;

        for (int i=0;i<totalNum;i++) {
            if (myObj[i].equals(obj))
                clueNum = i;
        }

        if (clueState[clueNum]) {
            popupWindow();
        }
        else {
            Activity.loseLife();
            lifePopupWindow();
        }
    }


    private void popupWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater)bathroom.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.input_prompt,(ViewGroup)findViewById(R.id.popUpEl));
            promptGuess = new PopupWindow(layout,370, 500,true);
            promptGuess.showAtLocation(layout, Gravity.CENTER,0,0);

            final EditText input = (EditText) layout.findViewById(R.id.userInput);
            submit = (Button) layout.findViewById(R.id.submitButton);
            Button close = (Button) layout.findViewById(R.id.closeButton);
            msgDisplay = (TextView) layout.findViewById(R.id.msgDisp);


            close.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    promptGuess.dismiss();
                }
            });

            submit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (verifyAnswer(input.getText().toString())) {
                        InputMethodManager imm =
                                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                        promptGuess.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void lifePopupWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater) bathroom.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.lose_life, (ViewGroup) findViewById(R.id.loseLifeE));
            lifeWindow = new PopupWindow(layout, 370, 500, true);
            lifeWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);


            TextView lifeDisp = (TextView) layout.findViewById(R.id.lifeDisp);
            Button closeLife = (Button) layout.findViewById(R.id.closeLife);
            if (Activity.numLives==0) {
                closeLife.setText("Reset");
                lifeDisp.setText("You're out of lives!");
            }

            closeLife.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    lifeWindow.dismiss();
                    if (Activity.numLives==0) {
                        resetLevel();
                        //reedemLife();
                        // NEED TO WORK ON THIS!
                    }

                }
            });


        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void initializeArrays() {
        int[] usedNum = {23,23,23,23,23};
        for (int i=0;i<totalNum;i++) {
            int r = Activity.randomNumber(totalNum);
            while (hasBeenUsed(r, usedNum)) {
                r = Activity.randomNumber(totalNum);
            }
            usedNum[i] = r;
            myObj[i] = objArray[r];
            myClue[i] = clueArray[r];
        }

    }

    public static boolean hasBeenUsed(int n, int[] usedNum) {
        for (int i=0;i<totalNum;i++) {
            if (usedNum[i]==n)
                return true;
        }
        return false;
    }

    public void nextLevel() {
        startActivity(new Intent(bathroom.this, kitchen.class));
    }

    public void prepareNextLevel() {
        setUnclickable();
        levelUp.setVisibility(View.VISIBLE);
        clueDisplay.setText("");
        hintDisplay.setText("");
        levelClear = true;

    }

    public static void setUnclickable() {
        shampoo.setClickable(false);
        towel.setClickable(false);
        sink.setClickable(false);
        bathtub.setClickable(false);
        rug.setClickable(false);
        hintButton.setVisibility(View.INVISIBLE);
    }


    public static void setClickable() {
        shampoo.setClickable(true);
        towel.setClickable(true);
        sink.setClickable(true);
        bathtub.setClickable(true);
        rug.setClickable(true);
        hintButton.setClickable(true);
    }

    boolean verifyAnswer (String ans) {
        String ansNeat = ans.toLowerCase().trim();

        if (ansNeat.equals(myObj[curClueNum])) {

            submit.setClickable(false);
            msgDisplay.setText("Congratulations!");
            initializeClueAndObject(++curClueNum);
            return true;
        }
        else {
            //loseLife(); // This is too troublesome
            msgDisplay.setText("Sorry, wrong answer. Try again!");
            return false;
        }
    }

    void initializeClueAndObject(int clueNum) {
        if (clueNum>totalNum-1) {
            prepareNextLevel();
            Log.i("MainActivity","here");
        }
        else {
            displayClue(clueNum);
            setObject(clueNum);
            hintNum=0;
        }
    }

    void displayClue(int clueNum){
        clueDisplay.setText(myClue[clueNum]);
        hintDisplay.setText("");
    }

    void setObject(int clueNum) {
        clueState[clueNum]=true;
        if (clueNum>0) {
            clueState[clueNum-1]=false;
        }
    }


    void displayHint() {
        String myString = myObj[curClueNum];
        String hint = "";
        for (int i = 0;i<hintNum;i++) {
            hint = hint + myString.charAt(i);
        }
        hintDisplay.setText(hint);
        if (hintNum<myString.length()) {
            hintNum++;
        }
    }

    void reedemLife() {
        startActivity(new Intent(bathroom.this, math_redem.class));
    }

    void resetLevel() {
        curClueNum=0;
        resetClueStates();
        initializeArrays();
        initializeClueAndObject(curClueNum);
        Activity.resetHearts();
    }


    void resetClueStates() {
        for (int i=0;i<totalNum;i++) {
            clueState[i]=false;
        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
