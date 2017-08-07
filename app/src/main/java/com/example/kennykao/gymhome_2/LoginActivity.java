package com.example.kennykao.gymhome_2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.concurrent.atomic.AtomicReference;

import StudentsLogin.StudentsVO;

import static android.app.PendingIntent.getActivity;

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
    };


    @Override
    public void onStart() {
        super.onStart();
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "StudentsServlet";
            List<StudentsVO> studentsList = null;

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                studentsList = new LoginActivity().execute(url).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (studentsList == null || studentsList.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoNewsFound);
            } else {
                rvNews.setAdapter(new NewsRecyclerViewAdapter(getActivity(), newsList));
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }

    private AtomicReference execute(Object url) {
        return null;
    }

    private LoginActivity getActivity() {
    }


}


//        btLogin.setOnClickListener(new Button.OnClickListener() {
//
//           @Override
//           public void onClick(View v) {
//               setContentView(R.layout.fakehomepage);
//           }
//               String email = etEmail.getText().toString();
//               final String password = etPassword.getText().toString();
//
//               if (TextUtils.isEmpty(email)) {
//                   Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
//                   return;
//               }
//
//               if (TextUtils.isEmpty(password)) {
//                   Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
//                   return;
//               }
//
//
//
//    }
//});




////               //authenticate user
////               auth.signInWithEmailAndPassword(email, password)
////           .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
////               @Override
////               public void onComplete(@NonNull Task<AuthResult> task) {
////                   // If sign in fails, display a message to the user. If sign in succeeds
////                   // the auth state listener will be notified and logic to handle the
////                   // signed in user can be handled in the listener.
////                   progressBar.setVisibility(View.GONE);
////                   if (!task.isSuccessful()) {
////                       // there was an error
////                       if (password.length() < 6) {
////                           inputPassword.setError(getString(R.string.minimum_password));
////                       } else {
////                           Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
////                       }
//                   } else {
//                       Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
//                       startActivity(intent);
//                       finish();
//                   }
//               }
//           });
//}
//       }











//                    String email = String.valueOf(etEmail.getText());
//                    Log.d(TAG, email);
//                    String passwd = String.valueOf(etPassword.getText());
//                    Log.d(TAG, passwd);

//                    if (Common.networkConnected(getActivity())) {
//                        studentsLoginTask = new StudentsLoginTask().execute(Common.URL);
//                    } else {
//                        showToast(this, R.string.msg_NoNetwork);
//                    }






