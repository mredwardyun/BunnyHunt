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
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Random;

import static android.view.View.OnClickListener;


public class MainActivity extends ActionBarActivity {
    // hello from Zeke!!
    // goodbye



    private static int totalNum = 3;
    private boolean levelClear = false;
    private static int hintNum= 1;

    public static ImageButton vase = null;
    public static ImageButton ball = null;
    public static ImageButton portrait = null;
    public static Button hintButton = null;
    public static ImageButton levelUp = null;

    public static Button mathClose = null;
    public static Button mathSubmit = null;
    public static EditText mathAns = null;
    public static TextView mathEqn = null;
    public static TextView mathPrompt = null;
    public static String mathAnswer = null;

    PopupWindow promptGuess = null;
    PopupWindow lifeWindow = null;
    PopupWindow mathWindow = null;
    PopupWindow wdWindow = null;
    TextView clueDisplay = null;
    public static TextView hintDisplay = null;
    TextView msgDisplay = null;
    Button submit = null;

    public static int curClueNum = 0;


    public static Boolean[] clueState = {false,false,false};

    public static String[] myObj = new String[totalNum];
    public static String[] objArray = {"vase", "ball","painting"};


    public static String[] myClue = new String[totalNum];
    public static String[] clueArray = {
            "\nDaddy’s a lawyer, and he just won a big case;\n" +
                    "So he bought me flowers, sitting in the _ _ _ _ ",

            "\nMy sister loves to play with her doll;\n" +
                    "Me, I like to throw my _ _ _ _",


            "\nOutside, it's about start raining \n" +
                    "but I'm warm next to Grammy's  _ _ _ _ _ _ _ _"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vase = (ImageButton) this.findViewById(R.id.vase);
        ball = (ImageButton) this.findViewById(R.id.ball);
        portrait = (ImageButton) this.findViewById(R.id.portrait);
        hintButton = (Button) this.findViewById(R.id.hintButtonStairs);


        Activity.heart1 = (ImageView) this.findViewById(R.id.heart1stairs);
        Activity.heart2 = (ImageView) this.findViewById(R.id.heart2stairs);
        Activity.heart3 = (ImageView) this.findViewById(R.id.heart3stairs);

        Activity.halfheart1 = (ImageView) this.findViewById(R.id.halfheart1stairs);
        Activity.halfheart2 = (ImageView) this.findViewById(R.id.halfheart2stairs);
        Activity.halfheart3 = (ImageView) this.findViewById(R.id.halfheart3stairs);

        levelUp = (ImageButton) findViewById(R.id.levelUpStairs);

        clueDisplay = (TextView) findViewById(R.id.hintTextStairs);
        hintDisplay = (TextView) findViewById(R.id.hintDispStairs);

        initializeArrays();
        initializeClueAndObject(curClueNum);
        Activity.resetHearts();

        vase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("vase");
            }

        });

        ball.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("ball");
            }
        });

        portrait.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("painting");
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

    public void shampooClick() {
        startActivity(new Intent(MainActivity.this, newClue.class));
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
            LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.input_prompt,(ViewGroup)findViewById(R.id.popUpEl));
            promptGuess = new PopupWindow(layout,370,500,true);
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
                        wellDoneWindow();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void lifePopupWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.lose_life, (ViewGroup) findViewById(R.id.loseLifeE));
            lifeWindow = new PopupWindow(layout, 370, 400, true);
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
                    if (Activity.numLives==0)
                        resetLevel();
                    lifeWindow.dismiss();
                }
            });


        }
        catch (Exception e) {
            e.printStackTrace();
        }


        }

    private void mathPopupWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.math_redemption, (ViewGroup) findViewById(R.id.mathWindow));
            mathWindow = new PopupWindow(layout, 370, 400, true);
            mathWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            mathAns = (EditText) findViewById(R.id.mathAns);
            mathSubmit = (Button) findViewById(R.id.mathSubmit);
            mathClose = (Button) findViewById(R.id.mathClose);
            mathEqn = (TextView) findViewById(R.id.mathEqn);
            mathPrompt = (TextView) findViewById(R.id.textView2);


            String question = Math.mathRedemption(homescreen.difficultyLevel);
            mathAnswer = Math.answerMe();

            mathEqn.setText("hello world");

            mathSubmit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String input = mathAns.getText().toString();
                    if (input.equals(mathAnswer)) {
                        mathWindow.dismiss();
                        Activity.resetHearts();
                    }
                    else {
                        mathPrompt.setText("Incorrect. Please try again:");
                    }

                }
            });
            mathClose.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mathWindow.dismiss();
                }
            });


        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void wellDoneWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.welldone, (ViewGroup) findViewById(R.id.welldoneE));
            wdWindow = new PopupWindow(layout, 370, 200, true);
            wdWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            Button nextButton = (Button) findViewById(R.id.nextButton);

            nextButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    wdWindow.dismiss();
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
        startActivity(new Intent(MainActivity.this, bedroom.class));
    }

    public void prepareNextLevel() {
        setUnclickable();
        resetHint();
        clueDisplay.setText("");
        levelUp.setVisibility(View.VISIBLE);
        levelClear = true;
    }

    public static void setUnclickable() {
        vase.setClickable(false);
        ball.setClickable(false);
        portrait.setClickable(false);
        hintButton.setVisibility(View.INVISIBLE);
    }

    public static void setClickable() {
        vase.setClickable(false);
        ball.setClickable(false);
        portrait.setClickable(false);
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
        resetHint();
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
        for (int i = 0; i<hintNum; i++) {
            hint = hint + myString.charAt(i);
        }

        hintDisplay.setText(hint);
        if (hintNum<myString.length()) {
            hintNum++;
        }
    }

    void resetHint() {
        hintDisplay.setText("");
    }

    void reedemLife() {
        startActivity(new Intent(MainActivity.this, math_redem.class));
    }

    void resetLevel() {
        //mathPopupWindow();
        curClueNum=0;
        resetClueStates();
        initializeArrays();
        initializeClueAndObject(curClueNum);
        Activity.resetHearts();
    }

    void restartGame() {
        resetLevel();
        startActivity(new Intent(MainActivity.this, homescreen.class));
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
