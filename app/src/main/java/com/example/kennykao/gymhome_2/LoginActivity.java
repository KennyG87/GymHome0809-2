package com.example.kennykao.gymhome_2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import StudentsLogin.StudentsVO;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private final static String ACTION = "getAll";
    private AsyncTask StudentsLoginTask;
    private ProgressDialog progressDialog;
    private RadioGroup rgMembers;
    private RadioButton rbStudents;
    private RadioButton rbCoaches;
    private Button btLogin;
    private EditText etEmail;
    private EditText etPassword;


    class StudentsLoginTask extends AsyncTask<String, Object, List<StudentsVO>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected List<StudentsVO> doInBackground(String... params) {
            String url = params[0].toString();
            String jsonIn;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", ACTION);
            try {
                jsonIn = getRemoteData(url, jsonObject.toString());
            } catch (IOException e) {
                Log.e(TAG, e.toString());
                return null;
            }

            Gson gson = new Gson();
            Type listType = new TypeToken<List<StudentsVO>>() {
            }.getType();
            return gson.fromJson(jsonIn, listType);
        }

        private String getRemoteData(String url, String jsonOut) throws IOException {
            StringBuilder jsonIn = new StringBuilder();
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true); // allow inputs
            connection.setDoOutput(true); // allow outputs
            connection.setUseCaches(false); // do not use a cached copy
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "UTF-8");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            bw.write(jsonOut);
            Log.d(TAG, "jsonOut: " + jsonOut);
            bw.close();

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    jsonIn.append(line);
                }
            } else {
                Log.d(TAG, "response code: " + responseCode);
            }
            connection.disconnect();
            Log.d(TAG, "jsonIn: " + jsonIn);
            return jsonIn.toString();
        }


//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_login);
//            rbCoaches = (RadioButton) findViewById(R.id.rbCoaches);
//            rbStudents = (RadioButton) findViewById(R.id.rbStudents);
//            btLogin = (Button) findViewById(R.id.btLogin);
//            etEmail = (EditText) findViewById(R.id.etEmail);
//            etPassword = (EditText) findViewById(R.id.etPassword);
//
//            btLogin.setOnClickListener(new Button.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String email = String.valueOf(etEmail.getText());
//                    Log.d(TAG, email);
//                    String passwd = String.valueOf(etPassword.getText());
//                    Log.d(TAG, passwd);
//
//                    if (Common.networkConnected()) {
//                        retrieveLoginTask = new RetrieveLoginTask().execute(Common.URL, email, passwd);
//                    } else {
//                        showToast(this, R.string.msg_NoNetwork);
//                    }
//
//
//                    Intent intent = new Intent();
//                    intent.setClass(LoginActivity.this, HomepageActivity.class);
//                    startActivity(intent);
//                    LoginActivity.this.finish();
//                }
//            });
//        }
//    }
}


