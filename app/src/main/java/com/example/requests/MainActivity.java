package com.example.requests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private EditText editTextResult;
    private EditText editText1;
    private EditText editText2;
    private Context context;
    ClearableCookieJar cookieJar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        context = this;
        editTextResult = findViewById(R.id.editTextResult);
        Button button = findViewById(R.id.button21);
        cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpHandler client = new OkHttpHandler();
                client.execute();
            }
        });

    }

    public class OkHttpHandler extends AsyncTask<Void, Void, Void> {


        String message;
        JSONArray persons;
        int index;
        String key;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url("http://192.168.1.43:8000/persons/")
                    .build();
            OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieJar).build();

            try {
                Response response = client.newCall(request).execute();
                message = response.body().string();
                persons = new JSONArray(message);
            } catch (Exception e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            index = Integer.parseInt(editText1.getText().toString());
            key = editText2.getText().toString();
            try {
                editTextResult.setText(
                        persons.getJSONObject(index).
                                getString(key)
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
