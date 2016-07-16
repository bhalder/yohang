package com.example.bhalder.yohang_4;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class YoHang_4 extends Activity implements OnClickListener {

    Button btnStartAnotherActivity;
    Button btnStartMissionActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yo_hang_4);

        btnStartAnotherActivity = (Button) findViewById(R.id.btnStartAnotherActivity);
        btnStartAnotherActivity.setOnClickListener(this);

        btnStartMissionActivity = (Button) findViewById(R.id.btnStartNewMission);
        btnStartMissionActivity.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.btnStartAnotherActivity:
                Intent intent = new Intent(this, MyProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.btnStartNewMission:
                Intent intent2 = new Intent(this, MapsActivity.class);
                startActivity(intent2);
                break;
        }
    }

}
