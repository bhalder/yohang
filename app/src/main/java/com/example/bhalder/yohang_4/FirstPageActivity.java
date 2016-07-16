package com.example.bhalder.yohang_4;

/**
 * Created by bhalder on 7/16/16.
 */
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.StringWriter;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class FirstPageActivity extends AppCompatActivity implements View.OnClickListener {

    EditText loginId;
    TextView responseView;
    ProgressBar progressBar;
    //static final String API_URL = "http://yohang.herokuapp.com/login";
    static final String API_URL = "http://10.16.22.225:8080/login";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.btnlogin:
                new RetrieveFeedTask(this).execute();

                break;
            case R.id.btnregister:
                Intent intent2 = new Intent(this, RegisterUserActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        responseView = (TextView) findViewById(R.id.responseView);
        loginId = (EditText) findViewById(R.id.loginId);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Button queryButton = (Button) findViewById(R.id.btnlogin);
        queryButton.setOnClickListener(this);
        Button registerButton = (Button) findViewById(R.id.btnregister);
        registerButton.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FirstPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.bhalder.yohang_4/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FirstPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.bhalder.yohang_4/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class RetrieveFeedTask  extends AsyncTask<Void, Void, String> {

        private Exception exception;
        public FirstPageActivity firstActivity;

        public RetrieveFeedTask(FirstPageActivity firstActivity) {
            this.firstActivity = firstActivity;
        }

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            responseView.setText("");
        }

        protected String doInBackground(Void... urls) {
            String loginIdStr = loginId.getText().toString();
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
                        db_util db = new db_util(this.firstActivity);
                        db_user user = db.getUser(loginIdStr);
                        if( user == null ) {
                            user = new db_user();
                            user.userId = loginIdStr;
                            user.points = 0;
                            user.email = "test@gmail.com";
                            user._id = "1234";
                            db.addUser(user);

                        } else {
                            Log.i("Oye Hoye", user.fullName);
                        }
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        Intent intent = new Intent(this.firstActivity, YoHang_4.class);
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
            responseView.setText(response);
            // TODO: check this.exception
            // TODO: do something with the feed

            try {
                JSONObject object = (JSONObject) new JSONObject(response);

                String requestID = object.getString("_id");

                Intent intent = new Intent(new FirstPageActivity(), YoHang_4.class);
                startActivity(intent);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
