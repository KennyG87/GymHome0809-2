package com.example.kennykao.gymhome0808;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;



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
    private AsyncTask studentsLoginTask;
    private ProgressDialog progressDialog;
    private RadioGroup rgMembers;
    private RadioButton rbStudents;
    private RadioButton rbCoaches;
    private Button btLogin;
    private EditText etEmail;
    private EditText etPassword;



    private class StudentsLoginTask extends AsyncTask<String, Object, List<StudentsVO>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(LoginActivity.this);
//            progressDialog.setMessage("Loading...");
//            progressDialog.show();
            Log.d(TAG, "ZZZZZZZZZZZZZZZZZZZZZZZZZZz: ");

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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rgMembers = (RadioGroup) findViewById(R.id.rgMembers);
        rbCoaches = (RadioButton) findViewById(R.id.rbCoaches);
        rbStudents = (RadioButton) findViewById(R.id.rbStudents);
        btLogin = (Button) findViewById(R.id.btLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        Log.d(TAG, "yyyyyyyyyyyyyyyyyyyyyyyyyyy: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//最基本的click回應
//        btLogin.setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent();
//                intent.setClass(LoginActivity.this, HomepageActivity.class);
//                startActivity(intent);
//                LoginActivity.this.finish();
//            }
//        });


//        btLogin.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                String email = etEmail.getText().toString().trim();
//                String password = etPassword.getText().toString().trim();
//                if (email.length() <= 0 || password.length() <= 0) {
//                    showMessage(R.string.msg_InvalidEmailOrPassword);
//                    return;
//                }
//
//                if (isLoginValid(email, password)) {
//                    SharedPreferences pref = getSharedPreferences(Common.PREF_FILE,
//                            MODE_PRIVATE);
//                    pref.edit()
//                            .putBoolean("login", true)
//                            .putString("email", email)
//                            .putString("password", password)
//                            .apply();
//                    setResult(RESULT_OK);
//                    finish();
//                } else {
//                    showMessage(R.string.msg_InvalidEmailOrPassword);
//                }
//            }
//
//
//        });
//    }
//
//    private void showMessage(int msg_invalidEmailOrPassword) {
//        return;
//    }
//
//
//
//    private boolean isLoginValid(String email, String password) {
//        return Boolean.parseBoolean(null);
//    }
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        SharedPreferences pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
//        boolean login = pref.getBoolean("login", false);
//        if (login) {
//            String email = pref.getString("email", "");
//            String password = pref.getString("password", "");
//            if (isLoginValid(email, password)) {
//                setResult(RESULT_OK);
//                finish();
//            } else {
//                showMessage(R.string.msg_InvalidEmailOrPassword);
//            }
//        }
//    }
//
//
//
//    private boolean isLoginValid(String email, String password) {
//        // 應該連線至server端檢查帳號密碼是否正確
//        return email.equals("a");
//    }


}}









