package com.bye.hi.testgame;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class math_redem extends ActionBarActivity {
    TextView eqnDisp = null;
    boolean isAnswer[] = {false, false, false, false};
    Button ans[] = new Button[4];
    String dispOptions[] = new String[4];
    Button ans2 = null;
    Button ans3 = null;
    Button ans4 = null;
    Random rand = new Random(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_redem);


        ans[0] = (Button) findViewById(R.id.ans1);
        ans[1] = (Button) findViewById(R.id.ans2);
        ans[2] = (Button) findViewById(R.id.ans3);
        ans[3] = (Button) findViewById(R.id.ans4);

        eqnDisp = (TextView) findViewById(R.id.eqnDisp);

        //String eqn = Math.mathRedemption(homescreen.difficultyLevel);
        String eqn = Math.mathRedemption(3);
        eqnDisp.setText(eqn);

        dispOptions[0] = "THE ANSWER";
        dispOptions[1] = "3";
        dispOptions[2] = "4";
        dispOptions[3] = "5";

        int[] usedNum = {7, 7, 7, 7, 7};
        for (int i = 0; i < 5; i++) {
            int r = rand.nextInt(4);
            while (hasBeenUsed(r, usedNum)) {
                r = rand.nextInt(4);
            }
            usedNum[i] = r;
            ans[i].setText(dispOptions[r]);
            if (r == 0)
                isAnswer[i] = true;
        }

/*
        View.OnClickListener boardClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int x = 0, y = 0;
                switch (v.getId()) {
                    case R.id.ans1:
                        evaluateAnswer(0);
                        break;
                    case R.id.ans2:
                        evaluateAnswer(1);
                        break;
                    case R.id.ans3:
                        evaluateAnswer(2);
                        break;
                    case R.id.ans4:
                        evaluateAnswer(3);
                        break;
                }
            }
        };
*/
    }



    public boolean hasBeenUsed(int n, int[] usedNum) {
        for (int i=0;i<5;i++) {
            if (usedNum[i]==n)
                return true;
        }
        return false;
    }

    public void evaluateAnswer(int n) {
        if (isAnswer[n]) {
            Activity.gainLife();
            finish();
        }
        else {
            //?
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_math_redem, menu);
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
