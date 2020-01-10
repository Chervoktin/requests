package com.example.requests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpHandler client = new OkHttpHandler();
        client.execute();
        textView = (TextView) findViewById(R.id.textViewMsg);
    }

    public class OkHttpHandler extends AsyncTask<Void, Void, Void> {

        OkHttpClient client = new OkHttpClient();
        String message;

        @Override
        protected Void doInBackground(Void... voids) {

            Request.Builder builder = new Request.Builder();
            builder.url("http://192.168.1.43:8000/");
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                message = response.body().string();
            } catch (Exception e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView.setText(message);
        }
    }
}
