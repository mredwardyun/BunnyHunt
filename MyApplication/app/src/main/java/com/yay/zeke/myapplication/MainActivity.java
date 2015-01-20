package com.yay.zeke.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "in onCreate");

        final Button b1 = (Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "in onClick");
                final TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText("Clicked");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "in onStart");
    }

    protected void onResume() {
        super.onStart();
        Log.i(TAG, "in onResume");
    }

    protected void onPause() {
        super.onStart();
        Log.i(TAG, "in onPause");
    }

    protected void onRestart() {
        super.onStart();
        Log.i(TAG, "in onRestart");
    }

    protected void onStop() {
        super.onStart();
        Log.i(TAG, "in onStop");
    }

    protected void onDestroy() {
        super.onStart();
        Log.i(TAG, "in onDestroy");
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
