package com.example.kennykao;

import android.app.ProgressDialog;
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

import CoachesLogin.CoachesVO;
import StudentsLogin.StudentsVO;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private StudentsSignupTask studentsSignupTask;
    private ProgressDialog progressDialog;
    private RadioGroup rgMembers;
    private RadioButton rbStudents;
    private RadioButton rbCoaches;
    private Button btSignup;
    private EditText etUser;
    private TextView tvMessage;
    private StudentsVO stus;
    private CoachesVO coas;
    private MemberCoach memberCoach;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAA: ");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        setResult(RESULT_CANCELED);
        memberCoach = new MemberCoach();
        rgMembers = (RadioGroup) findViewById(R.id.rgMembers);
        rbCoaches = (RadioButton) findViewById(R.id.rbCoaches);
        rbStudents = (RadioButton) findViewById(R.id.rbStudents);
        btSignup = (Button) findViewById(R.id.btSignup);
        etUser = (EditText) findViewById(R.id.etUser);

        tvMessage = (TextView) findViewById(R.id.tvMessage);
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUser.getText().toString().trim();

                if (username.length() <= 0) {
                    showMessage(R.string.UserCannotNull);
                    return;
                }
                String role = "0"; // from radio button 0=Students 1=Coaches

                Object obj = null;
                try {
                    memberCoach = isUserNew(role, username);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stus = memberCoach.getStudentsVO();
                coas = memberCoach.getCoachesVO();

                if(memberCoach == null ) {
                    Common.showToast(getBaseContext(), "WRONG");
                    return;
                }else {
                    Common.showToast(getBaseContext(), "OK");

                }

           }
        });




    }


    class StudentsSignupTask extends AsyncTask<String, Object, MemberCoach> {



        @Override
        protected MemberCoach doInBackground(String... params) {
            String role = params[1];
            String url = params[0];
            String username = params[2];
            String jsonIn;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("role", role);
            jsonObject.addProperty("username", username);

            try {
                jsonIn = getRemoteData(url, jsonObject.toString());
            } catch (IOException e) {
                Log.e(TAG, e.toString());
                return null;
            }

            Gson gson = new Gson();
//            Type listType = new TypeToken<MemberCoach>() {
//            }.getType();
//            Object obj = gson.fromJson(jsonIn,Object.class);
            return  gson.fromJson(jsonIn, MemberCoach.class);

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

    private MemberCoach isUserNew(String role, String username) throws ExecutionException, InterruptedException {
        Object obj = null;
        if (Common.networkConnected(this)) {
            if (studentsSignupTask == null){
                studentsSignupTask = new StudentsSignupTask();
            }
            memberCoach =studentsSignupTask.execute(Common.URL+"MembersServlet", role, username).get();
        } else {
            Common.showToast(this, R.string.tryagain);
        }
        return memberCoach;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (studentsSignupTask != null){
            studentsSignupTask.cancel(true);
            studentsSignupTask = null;
        }
    }


}

















