package com.example.bhalder.yohang_4;

/**
 * Created by bhalder on 7/16/16.
 */

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by bhalder on 7/16/16.
 */
public class WinActivity extends AppCompatActivity  implements OnClickListener  {

    Button btnStartAnotherActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win);

        btnStartAnotherActivity = (Button) findViewById(R.id.btnStartNewActivity);
        btnStartAnotherActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}