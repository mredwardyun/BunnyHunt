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

    ImageButton bathtub = null;
    ImageButton shampoo = null;
    ImageButton sink = null;
    ImageButton towel = null;
    ImageButton duck = null;

    ImageView heart1 = null;
    ImageView heart2 = null;
    ImageView heart3 = null;

    PopupWindow promptGuess = null;
    PopupWindow lifeWindow = null;
    TextView clueDisplay = null;

    TextView msgDisplay = null;
    Button submit = null;

    public static int curClueNum = 0;
    public static int numLives = 3;

    public enum objName {
        DUCK,SHAMPOO,BATHTUB,TOWEL,SINK
    }

    public static Boolean[] clueState = {false,false,false,false,false};

//    public static objName[] myObj = new objName[5];
//    public static objName[] objArray = {objName.DUCK,objName.SHAMPOO,
//            objName.BATHTUB,objName.TOWEL,objName.SINK};

    public static String[] myObj = new String[5];
    public static String[] objArray = {"duck", "shampoo","bathtub","towel","sink"};


    public static String[] myClue = new String[5];
    public static String[] clueArray = {
            "My best friend and my buddy,\n" +
            "My lovely rubber _ _ _ _ _",

            "Lather up and scrub between\n" +
            "Until my fur is sparkly clean",

            "Splash! Splash! Splash!\n" +
            "Time for a good scrub!\n" +
            "It’s my favourite time of the day,\n" +
            "Relaxing in the _ _ _ _ _ _ _.",

            "It dries me up\n" +
            "and wipes me down.\n" +
            "It’s my lovely \n" +
            "fluffy _ _ _ _ _",

            "It’s where I brush my teeth,\n" +
            "It’s where I clean my face,\n" +
            "It’s where I wash my hands,\n" +
            "The perfect hygiene place\n" +
            "Where is this?"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bathtub = (ImageButton) this.findViewById(R.id.bathtub);
        shampoo = (ImageButton) this.findViewById(R.id.shampoo);
        sink = (ImageButton) this.findViewById(R.id.sink);
        towel = (ImageButton) this.findViewById(R.id.vase);
        duck = (ImageButton) this.findViewById(R.id.ball);

        heart1 = (ImageView) this.findViewById(R.id.heart1);
        heart2 = (ImageView) this.findViewById(R.id.heart2);
        heart3 = (ImageView) this.findViewById(R.id.heart3);

        clueDisplay = (TextView) findViewById(R.id.clueDisp);

        Log.i("MainActivity", "Initialise arrays");

        initializeArrays();

        Log.i("MainActivity", "Initialise clues");
        initializeClueAndObject(curClueNum);





        bathtub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("bathtub");
            }

        });

        shampoo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("shampoo");
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

        duck.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                objClick("duck");
            }
        });


    }

    public void shampooClick() {
        startActivity(new Intent(MainActivity.this, newClue.class));
    }

    public void objClick(String obj) {
        int clueNum=0;

        for (int i=0;i<5;i++) {
            if (myObj[i].equals(obj))
                clueNum = i;
        }

        if (clueState[clueNum]) {
            popupWindow();
        }
        else {
           loseLife();
           lifePopupWindow();
        }
    }


    private void popupWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.input_prompt,(ViewGroup)findViewById(R.id.popUpEl));
            promptGuess = new PopupWindow(layout,350,400,true);
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
                    verifyAnswer(input.getText().toString());
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
            lifeWindow = new PopupWindow(layout, 350, 400, true);
            lifeWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);


            TextView lifeDisp = (TextView) layout.findViewById(R.id.lifeDisp);
            Button closeLife = (Button) layout.findViewById(R.id.closeLife);
            if (numLives==0) {
                closeLife.setText("Reset");
                lifeDisp.setText("You're out of lives!");
            }

            closeLife.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (numLives==0)
                        resetLevel();
                    lifeWindow.dismiss();
                }
            });


        }
        catch (Exception e) {
            e.printStackTrace();
        }


        }

    public void initializeArrays() {
        int[] usedNum = {7,7,7,7,7};
        for (int i=0;i<5;i++) {
            int r = randomNumber();
            while (hasBeenUsed(r, usedNum)) {
                r = randomNumber();
            }
            usedNum[i] = r;
            myObj[i] = objArray[r];
            myClue[i] = clueArray[r];
        }

    }

    public boolean hasBeenUsed(int n, int[] usedNum) {
        for (int i=0;i<5;i++) {
            if (usedNum[i]==n)
                return true;
        }
        return false;
    }

    public void nextLevel() {
        startActivity(new Intent(MainActivity.this, newClue.class));
    }

    void verifyAnswer (String ans) {
        String ansNeat = ans.toLowerCase().trim();

        if (ansNeat.equals(myObj[curClueNum])) {

            submit.setClickable(false);
            msgDisplay.setText("Congratulations!");
            initializeClueAndObject(++curClueNum);
        }
        else {
            //loseLife(); // This is too troublesome
            msgDisplay.setText("Sorry, wrong answer. Try again!");
        }
    }
    
    void initializeClueAndObject(int clueNum) {
        if (clueNum>5) {
            nextLevel();
        }

        displayClue(clueNum);
        setObject(clueNum);
    }

    void displayClue(int clueNum){
        clueDisplay.setText(myClue[clueNum]);
    }

    void setObject(int clueNum) {
        clueState[clueNum]=true;
        if (clueNum>0) {
            clueState[clueNum-1]=false;
        }
    }

    void hideUserInput() {
        //layout.findViewById(R.id.userInput).setVisibility(View.VISIBLE);
    }

    void loseLife() {
        numLives--;

        if (numLives==2) {
            heart3.setVisibility(View.GONE);
        }
        else if (numLives==1) {
            heart2.setVisibility(View.GONE);
        }
        else if (numLives==0) {
            heart1.setVisibility(View.GONE);
            // RestartGame
        }
    }

    void resetLevel() {
        curClueNum=0;
        resetClueStates();
        initializeArrays();
        initializeClueAndObject(curClueNum);
        resetHearts();
    }

    void resetHearts() {
        numLives = 3;
        heart3.setVisibility(View.VISIBLE);
        heart2.setVisibility(View.VISIBLE);
        heart1.setVisibility(View.VISIBLE);
    }

    void resetClueStates() {
        for (int i=0;i<5;i++) {
            clueState[i]=false;
        }
    }





    int randomNumber() {
        Random rand = new Random();
        return rand.nextInt(5);
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
