package com.example.kennykao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final int REQUEST_SIGNUP = 1;
    private AsyncTask SignupTask;
    private ProgressDialog progressDialog;
    private RadioGroup rgMembers;
    private RadioButton rbStudents;
    private RadioButton rbCoaches;
    private Button btSignup;
    private EditText etUser;
    private TextView tvMessage;
    private StudentsVO stus;
    private CoachesVO coas;
    private MembersVO mems;
    private AllMembers allMembers;
    private TextView linktoLogin;
    private EditText etName;
    private EditText etNickname;
    private EditText etPassword;
    private EditText etConfirmPW;
    private RadioGroup rgSex;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private EditText etID;
    private EditText etEmail;
    private EditText etIntro;
    private Button btTakeNow;
    private Button btUpload;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAA: ");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        setResult(RESULT_CANCELED);
        allMembers = new AllMembers();
        rgMembers = (RadioGroup) findViewById(R.id.rgMembers);
        rbCoaches = (RadioButton) findViewById(R.id.rbCoaches);
        rbStudents = (RadioButton) findViewById(R.id.rbStudents);
        btSignup = (Button) findViewById(R.id.btSignup);
        etUser = (EditText) findViewById(R.id.etUser);
        linktoLogin = (TextView) findViewById(R.id.linktoLogin);
        etName = (EditText) findViewById(R.id.etName);
        etNickname = (EditText) findViewById(R.id.etNickname);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPW = (EditText) findViewById(R.id.etConfirmPW);
        rgSex = (RadioGroup) findViewById(R.id.rgSex);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);
        etID = (EditText) findViewById(R.id.etID);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etIntro = (EditText) findViewById(R.id.etIntro);

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedID){
                RadioButton radioButton = (RadioButton) group.findViewById(checkedID);

            }
        });

        memGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButtonGender = (RadioButton) group.findViewById(checkedId);
                Common.showToast(getApplicationContext(), (String) RadioButtonGender.getText());
                user_memGender = Integer.valueOf((String) RadioButtonGender.getHint());
            }
        });
        memRelation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButtonRelation = (RadioButton) group.findViewById(checkedId);
                Common.showToast(getApplicationContext(), (String) RadioButtonRelation.getHint());
                user_memRelation = Integer.valueOf((String) RadioButtonRelation.getHint());

            }
        });

        linktoLogin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String nickname = etNickname.getText().toString().trim();
                String username = etUser.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                Integer sex = rgSex.getCheckedRadioButtonId();
                String id = etID.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String intro = etIntro.getText().toString().trim();


                if (username.length() <= 0 || password.length() <= 0 && name.length() <= 0 && nickname.length() <= 0
                        && id.length() <= 0 && email.length() <= 0 && intro.length() <= 0) {
                    showMessage(R.string.CannotNull);
                    return;
                }
                String status = "0"; // from radio button 0=Students 1=Coaches

                Object obj = null;
                try {
                    allMembers = isUserNew(status, name, nickname, username, password, sex , id, email, intro);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stus = allMembers.getStudentsVO();
                coas = allMembers.getCoachesVO();

                if(allMembers == null ) {
                    Common.showToast(getBaseContext(), "WRONG");
                    return;
                }else {
                    Common.showToast(getBaseContext(), "OK");

                }

                if (networkConnected()) {
                    try {
                        allMembers= (AllMembers) SignupTask().execute(Common.URL+"StudentsServlet").get();
                        Log.d(TAG, "BBBBBBBBBBBBBBBBBBBBBBBBBBBBB: ");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Common.showToast(v.getContext(), R.string.msg_NoNetwork);
                }

            }

            public static boolean networkConnected(Activity activity) {
                ConnectivityManager conManager =
                        (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }


        });




/**IF (取到的值="0"){
 *       membwe = new Membwe(xxxxxxx);
 *       student = new Student(xxxxxx);
 *       coach = null;
 *       }else{
 *       membwe = nwe Membwe(xxxxx);
 *       student = null;
 *       coach = new Coach(xxxxxx);
 *       }
 *       allmember = new Allmember(member,student,coach);
 *       new SignupTask.execute(Common.URL+"upload", allmember);
 *
 protected AllMembers doInBackground(Object... params) {
 String url = params[0].toString();
 allmember = (Allmember)params[1];
 String str="";
 String jsonIn;
 Gson gson=  new Gson();
 outStr = gson.toJson(allmember);


                                 //sevlet用
                                 Gson gson = new Gson();
                                 //            Type listType = new TypeToken<AllMembers>() {
                                 //            }.getType();
                                 allmember = gson.from(jsonIn,listype);


 try {
 jsonIn = getRemoteData(url, outStr);
 } catch (IOException e) {
 Log.e(TAG, e.toString());
 return null;
 }


                return  allmember;
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
 @Override
 /*
  * invoked on the UI thread after the background computation finishes.
  * The result of the background computation is passed to this step as a
  * parameter.
 */



    protected void onPostExecute(AllMembers all) {
//            Integer status = user.getStatus();
//            String name = user.getName();
//            String nickname = user.getNickname();
//            String username = user.getUsername();
//            String password = user.getPassword();
//            Integer sex = user.getSex();
//            String id = user.getId();
//            String email = user.getEmail();
//            String intro = user.getIntro();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0,
//                    user.getImage().length);
        progressDialog.cancel();
    }
}




 **/




//        // 點擊TAKE ONE NOW會拍照
//        public void onTakeNowClick(View view) {
//            takePicture();
//        }
//
//        private void UploadPicture() {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            // 指定存檔路徑
//            file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//            file = new File(file, "picture.jpg");
//            // targeting Android 7.0 (API level 24) and higher,
//            // storing images using a FileProvider.
//            // passing a file:// URI across a package boundary causes a FileUriExposedException.
//            Uri contentUri = FileProvider.getUriForFile(
//                    this, getPackageName() + ".provider", file);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
//
//            if (isIntentAvailable(this, intent)) {
//                startActivityForResult(intent, REQ_TAKE_PICTURE);
//            } else {
//                showToast(this, R.string.msg_NoCameraApp);
//            }
//        }


    }

private class SignupTask extends AsyncTask<Object, Integer, AllMembers> {

//        @Override
//        // invoked on the UI thread immediately after the task is executed.
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(SignupActivity.this);
//            progressDialog.setMessage("Loading...");
//            progressDialog.show();
//        }

    @Override
    // invoked on the background thread immediately after onPreExecute()
    protected AllMembers doInBackground(Object... params) {
        String url = params[0].toString();
        Integer status = Integer.valueOf(params[1].toString());
        String name = params[2].toString();
        String nickname = params[3].toString();
        String username = params[4].toString();
        String password = params[5].toString();
        Integer sex = Integer.valueOf(params[6].toString());
        String id = params[7].toString();
        String email = params[8].toString();
        String intro = params[9].toString();
        byte[] image = (byte[]) params[10];
        String jsonIn;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", status);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("nickname", nickname);
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("sex", sex);
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("intro", intro);
        jsonObject.addProperty("imageBase64", Base64.encodeToString(image, Base64.DEFAULT));
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
    @Override
        /*
         * invoked on the UI thread after the background computation finishes.
		 * The result of the background computation is passed to this step as a
		 * parameter.
		 */
    protected void onPostExecute(AllMembers all) {
//            Integer status = user.getStatus();
//            String name = user.getName();
//            String nickname = user.getNickname();
//            String username = user.getUsername();
//            String password = user.getPassword();
//            Integer sex = user.getSex();
//            String id = user.getId();
//            String email = user.getEmail();
//            String intro = user.getIntro();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0,
//                    user.getImage().length);
        progressDialog.cancel();
    }
}




    private void showMessage(int msgResId) {
        tvMessage.setText(msgResId);
    }

    private AllMembers isUserNew(String role, String username, String nickname, String s, String password, Integer sex, String id, String email, String intro) throws ExecutionException, InterruptedException {
        Object obj = null;
        if (Common.networkConnected(this)) {
            if (SignupTask == null){
                SignupTask = new SignupTask();
            }
            newUser = (NewUser) SignupTask.execute(Common.URL+"StudentsServlet", role, username);
        } else {
            Common.showToast(this, R.string.tryagain);
        }
        return (AllMembers) newUser;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (SignupTask != null){
            SignupTask.cancel(true);
            SignupTask = null;
        }
    }


//public void Autofill(View view) {
//        memId.setText("haappyy");
//        memPwd.setText("123");
//        memName.setText("掌管");
//        memSname.setText("補教名師");
//        memIdNo.setText("A165265055");
//        memPhone.setText("0912345672");
//        memAddress.setText("Myhome");
//        memEmail.setText("haappyy@gmail.com");
//        memSelfintro.setText("我叫梁陳星,我會教國文");
//
//
//        }

}