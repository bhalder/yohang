package com.example.bhalder.yohang_4;

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
public class MyProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile);

        db_util db = new db_util(this);
        db_user user = db.getUser("stud");
        TextView name = (TextView) findViewById(R.id.playerName);
        TextView points = (TextView) findViewById(R.id.Score);
        if( user != null ) {
            name.setText(user.fullName);
            points.setText(String.valueOf(user.points));
        }
    }

}
