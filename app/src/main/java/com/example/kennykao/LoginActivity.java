package com.example.kennykao;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.example.kennykao.gymhome_2.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


import static com.example.kennykao.Common.showToast;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private LoginTask LoginTask;
    private ProgressDialog progressDialog;
    private RadioGroup rgMembers;
//    private RadioButton rbStudents;
//    private RadioButton rbCoaches;
    private Button btLogin;
    private EditText etUser;
    private EditText etPassword;
    private TextView tvMessage;
    private StudentsVO stus;
    private CoachesVO coas;
    private AllMembers allMembers;
    private TextView linktoSignup;
    private TextView tvNopassword;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "yyyyyyyyyyyyyyyyyyyyyyyyyyy: ");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setResult(RESULT_CANCELED);
        allMembers = new AllMembers();
        rgMembers = (RadioGroup) findViewById(R.id.rgMembers);
//        rbCoaches = (RadioButton) findViewById(R.id.rbCoaches);
//        rbStudents = (RadioButton) findViewById(R.id.rbStudents);
        btLogin = (Button) findViewById(R.id.btLogin);
        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        linktoSignup = (TextView) findViewById(R.id.linktoSignup);

        linktoSignup.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        rgMembers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedID){
                RadioButton radioButton = (RadioButton) group.findViewById(checkedID);

                role = String.valueOf(radioButton.getText());



            }
        });


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUser.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.length() <= 0 || password.length() <= 0) {
                    showMessage(R.string.InvalidUserOrPassword);
                    return;
                }





                //Object obj = null;
                try {
                    allMembers = isUserValid(role, username, password);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
//                stus = allMembers.getStudentsVO();
//                coas = allMembers.getCoachesVO();

                if(allMembers == null ) {
                    Common.showToast(getBaseContext(), "WRONG");

                }else {
                    Common.showToast(getBaseContext(), "OK");

                }

           }
        });




    }



    class LoginTask extends AsyncTask<String, Object, AllMembers> {


        @Override
        protected AllMembers doInBackground(String... params) {

            String url = params[0];
            String role = params[1];
            String username = params[2];
            String password = params[3];
            String jsonIn;

            if(role.equals("健身者")){
                role = "0";
            }else {
                role="1";
            }


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("role", role);
            jsonObject.addProperty("username", username);
            jsonObject.addProperty("password", password);

            try {
                jsonIn = getRemoteData(url, jsonObject.toString());
            } catch (IOException e) {
                Log.e(TAG, e.toString());
                return null;
            }

            Gson gson = new Gson();
//            Type listType = new TypeToken<AllMembers>() {
//            }.getType();
//            Object obj = gson.fromJson(jsonIn,Object.class);
            return  gson.fromJson(jsonIn, AllMembers.class);

        }

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
        jsonIn = new StringBuilder();
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



    private void showMessage(int msgResId) {
        tvMessage.setText(msgResId);
    }

    private AllMembers isUserValid(String role, String username, String password) throws ExecutionException, InterruptedException {
        //Object obj = null;
        if (Common.networkConnected(this)) {
            if (LoginTask == null){
                LoginTask = new LoginTask();
            }
            allMembers = LoginTask.execute(Common.URL+"CoachesServlet", role, username, password).get();
//            allMembers =LoginTask.execute(Common.URL+"StudentsServlet", role, username, password).get();
        } else {

            Common.showToast(this, R.string.tryagain);
        }

        return allMembers;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (LoginTask != null){
            LoginTask.cancel(true);
            LoginTask = null;
        }
    }


}

















