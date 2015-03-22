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


public class kitchen extends ActionBarActivity {

    private static int totalNum = 4;
    private boolean levelClear = false;
    private static int hintNum= 0;

    public static ImageButton stove = null;
    public static ImageButton fridge = null;
    public static ImageButton table = null;
    public static ImageButton pot = null;

    public static ImageButton chair = null;
    public static ImageButton levelUp = null;

    public static Button hintButton = null;


    PopupWindow promptGuess = null;
    PopupWindow lifeWindow = null;
    TextView clueDisplay = null;
    public static TextView hintDisplay = null;
    TextView msgDisplay = null;
    Button submit = null;

    public static int curClueNum = 0;


    public static Boolean[] clueState = {false,false,false,false};

    public static String[] myObj = new String[totalNum];
    public static String[] objArray = {"stove", "fridge","table", "pot"};


    public static String[] myClue = new String[totalNum];
    public static String[] clueArray = {
            "Look at these carrots, what a treasure trove.\n" +
                    "Let’s cut them up and cook them on the _ _ _ _ _ ",

            "On my way to school I cross the bridge,\n" +
                    "But oh no! I left my lunch in the _ _ _ _ _ _",

            "Table",

            "I put my veggies here and wait until they’re hot\n" +
                    "Once they’re ready, take them out of the _ _ _ "
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        stove = (ImageButton) this.findViewById(R.id.stove);
        fridge = (ImageButton) this.findViewById(R.id.fridge);
        table = (ImageButton) this.findViewById(R.id.table);
        chair = (ImageButton) this.findViewById(R.id.chair);
        chair.setClickable(false);
        pot = (ImageButton) this.findViewById(R.id.pot);

        hintButton = (Button) this.findViewById(R.id.hintButtonKitch);
        levelUp = (ImageButton) findViewById(R.id.levelUpKitch);


        Activity.heart1 = (ImageView) this.findViewById(R.id.heart1kitch);
        Activity.heart2 = (ImageView) this.findViewById(R.id.heart2kitch);
        Activity.heart3 = (ImageView) this.findViewById(R.id.heart3kitch);

        Activity.halfheart1 = (ImageView) this.findViewById(R.id.halfheart1kitch);
        Activity.halfheart2 = (ImageView) this.findViewById(R.id.halfheart2kitch);
        Activity.halfheart3 = (ImageView) this.findViewById(R.id.halfheart3kitch);

        clueDisplay = (TextView) findViewById(R.id.hintTextKitch);
        hintDisplay = (TextView) findViewById(R.id.hintDispKitch);

        initializeArrays();
        initializeClueAndObject(curClueNum);
        Activity.resetHearts();

        stove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("stove");
            }

        });

        fridge.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("fridge");
            }
        });

        table.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("table");
            }
        });

        pot.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("pot");
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
            LayoutInflater inflater = (LayoutInflater)kitchen.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            LayoutInflater inflater = (LayoutInflater) kitchen.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        startActivity(new Intent(kitchen.this, livingroom.class));
    }

    public void prepareNextLevel() {
        setUnclickable();
        levelUp.setVisibility(View.VISIBLE);
        clueDisplay.setText("");
        hintDisplay.setText("");
        levelClear = true;
    }

    public static void setUnclickable() {
        stove.setClickable(false);
        fridge.setClickable(false);
        table.setClickable(false);
        pot.setClickable(false);
        hintButton.setVisibility(View.INVISIBLE);
    }

    public static void setClickable() {
        stove.setClickable(true);
        fridge.setClickable(true);
        table.setClickable(true);
        pot.setClickable(true);
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
        String hint = myString.charAt(1) + "";
        for (int i = 1;i<hintNum;i++) {
            hint = hint + myString.charAt(i);
        }
        hintDisplay.setText(hint);
        if (hintNum<myString.length()) {
            hintNum++;
        }
    }

    void reedemLife() {
        startActivity(new Intent(kitchen.this, math_redem.class));
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
