package com.example.hz.notifcation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by mi on 16-10-19.
 */
public class CActivity extends Activity {
    private Button mToAactivityBtn;
    private String TAG = "CActivity--测试onNewIntent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_main);
        mToAactivityBtn= (Button) findViewById(R.id.to_a_activity_btn);

        mToAactivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainActivity();
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG,"onNewIntent()");
    }

    public void goMainActivity(){
        Log.d(TAG,"goMainActivity()");
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
    }

}