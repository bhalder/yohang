package com.example.bhalder.yohang_4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bhalder on 7/16/16.
 */
public class MissionBriefActivity extends Activity implements View.OnClickListener {

    Button btnStartNewMission;
    TextView missionbrief;
    static final String API_URL = "http://10.16.22.225:8080/mission?_id=578a15421aebf84475fecf51";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missionbrief);

        missionbrief = (TextView) findViewById(R.id.missionbrief);

        new RetrieveFeedTask(this).execute();

        btnStartNewMission = (Button) findViewById(R.id.btnStartNewMission);
        btnStartNewMission.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, AnotherActivity.class);
        startActivity(intent);
    }

    class RetrieveFeedTask  extends AsyncTask<Void, Void, String> {

        private Exception exception;
        public MissionBriefActivity missionBriefActivity;

        public RetrieveFeedTask(MissionBriefActivity missionBriefActivity) {
            this.missionBriefActivity = missionBriefActivity;
        }

        protected void onPreExecute() {

               missionbrief.setText("");
        }

        protected String doInBackground(Void... urls) {

            String loginIdStr = "stud";
            // Do some validation here

            try {
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("userId", loginIdStr);

                String query = builder.build().getEncodedQuery();
                URL url = new URL(API_URL+"?"+query);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("loginId", loginIdStr);

                try {
                    urlConnection.connect();
                    int response = urlConnection.getResponseCode();
                    if( response == 200 ) {
                        db_util db = new db_util(this.missionBriefActivity);
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        int ch;
                        StringBuilder sb = new StringBuilder();
                        while((ch = in.read()) != -1)
                            sb.append((char)ch);


                        return sb.toString();

                    }
                    return Integer.toString(response);
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }

            Log.i("INFO", response);

            // TODO: check this.exception
            // TODO: do something with the feed

            try {
                JSONObject object = (JSONObject) new JSONObject(response);

                String msg = object.getString("mission");
                missionbrief.setText(msg);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
