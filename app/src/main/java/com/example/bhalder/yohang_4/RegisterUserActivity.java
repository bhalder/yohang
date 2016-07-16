package com.example.bhalder.yohang_4;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import android.view.View.OnClickListener;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedOutputStream;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bhalder on 7/16/16.
 */
public class RegisterUserActivity extends AppCompatActivity implements OnClickListener {

    private Button registerButton;
    ProgressBar progressBar;
    EditText loginId;
    EditText Name;
    EditText Email;

    static final String API_URL = "http://10.16.22.225:8080/register";
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeruser);

        registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        loginId = (EditText) findViewById(R.id.registerID);
        Name = (EditText) findViewById(R.id.registerName);
        Email = (EditText) findViewById(R.id.registerEmail);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);

        new RetrieveFeedTask(this).execute();
    }

    class RetrieveFeedTask  extends AsyncTask<Void, Void, String> {

        private Exception exception;
        public RegisterUserActivity registerUserActivity;
        public  RetrieveFeedTask(RegisterUserActivity registerUserActivity) {
            this.registerUserActivity = registerUserActivity;
        }


        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {
            String loginIdStr = loginId.getText().toString();
            String NameStr = Name.getText().toString();
            String EmailStr = Email.getText().toString();

            // Do some validation here

            try {
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("userId", loginIdStr)
                        .appendQueryParameter("email", EmailStr)
                        .appendQueryParameter("fullName", NameStr);

                String query = builder.build().getEncodedQuery();
                URL url = new URL(API_URL+"?"+query);
                Log.i("INFO", url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                try {
                    urlConnection.connect();
                    int response = urlConnection.getResponseCode();
                    if( response == 200 || response == 201 ) {

                        Intent intent = new Intent(this.registerUserActivity, FirstPageActivity.class);
                        startActivity(intent);

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
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            // TODO: check this.exception
            // TODO: do something with the feed

        }
    }
}

