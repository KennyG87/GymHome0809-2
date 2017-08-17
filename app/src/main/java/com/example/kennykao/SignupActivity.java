package com.example.kennykao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final int REQUEST_SIGNUP = 1;
    private AsyncTask SignupTask;
    private ProgressDialog progressDialog;
    private RadioGroup rgMembers;
    private RadioButton rbStudents;
    private RadioButton rbCoaches;
    private Integer userGender = 0, userStatus = 0;
    private Button btSignup;
    private EditText etUser;
    private TextView tvMessage;
    private StudentsVO stus;
    private CoachesVO coas;
    private MembersVO mems;
    private NewMembers newMembers;
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
    private Boolean flag;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAA: ");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        setResult(RESULT_CANCELED);
        newMembers = new NewMembers();
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

        rgMembers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedID){
            rbStudents = (RadioButton) group.findViewById(checkedID);
                Common.showToast(getApplicationContext(), (String) rbStudents.getText());
            userStatus = Integer.valueOf((String) rbStudents.getHint());
        }
    });

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedID){
                rbMale = (RadioButton) group.findViewById(checkedID);
                Common.showToast(getApplicationContext(), (String) rbMale.getText());
                userGender = Integer.valueOf((String) rbMale.getHint());
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
                Authentication();

                newMembers = new NewMembers();
                if (networkConnected()) {
                    try {
                        String cool = new SignupTask.execute(Common.URL + "StudentsServlet").get();
                        System.out.print(cool);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Common.showToast(v.getContext(), R.string.NoNetwork);
                }

            }


            private boolean networkConnected() {
                ConnectivityManager conManager =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }

            private void Authentication() {
                flag = false;
                ArrayList<Boolean> valids = new ArrayList(Arrays.asList(false,false,false,false,false,false,false,false,false,true));
                Log.d("flag","flag::::::"+valids.contains(false));
                //名字驗證
                if (!valids.get(0)){
                    String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{3,15}$";
                    if (name == null || name.length() == 0) {
                        etName.setError("姓名: 請勿空白");
                        return;
                    } else if(!name.trim().matches(enameReg)) {
                        etName.setError("姓名: 只能是中文、英文字母、數字 , 且長度必需在3到15之間");
                        return;
                    }
                    valids.add(0,true);
                //暱稱驗證
                if (!valids.get(1)){
                    String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{3,15}$";
                    if (nickname == null || nickname.length() == 0) {
                        etNickname.setError("暱稱: 請勿空白");
                        return;
                    } else if(!nickname.trim().matches(enameReg)) {
                        etNickname.setError("暱稱: 只能是中文、英文字母、數字 , 且長度必需在3到15之間");
                        return;
                    }
                    valids.add(1,true);
                }

                }
                //帳號驗證
                if (!valids.get(2)){
                    String enameReg = "^[(a-zA-Z0-9_)]{3,10}$";
                    if (username == null || username.length() == 0) {
                        etUser.setError("帳號: 請勿空白");
                        return;
                    } else if(!username.trim().matches(enameReg)) {
                        etUser.setError("帳號: 英文字母、數字和_ , 且長度必需在3到10之間");
                        return;
                    }
                    valids.add(2,true);
                }
                //密碼驗證
                if (!valids.get(3)){
                    String enameReg = "^[(a-zA-Z0-9)]{6,15}$";
                    if (password == null || password.length() == 0) {
                        etPassword.setError("密碼: 請勿空白");
                        return;
                    } else if(!password.trim().matches(enameReg)) {
                        etPassword.setError("密碼: 只能是英文字母、數字 , 且長度必需在6到15之間");
                        return;
                    }
                    valids.add(3,true);
                }


                //身分證驗證
                if (!valids.get(4)){
                    PID(id,valids);
                }

                //email驗證
                if (!valids.get(5)){
                    String enameReg = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
                    if (email == null || email.length() == 0) {
                        etEmail.setError("Email: 請勿空白");
                        return;
                    } else if(!email.trim().matches(enameReg)) {
                        etEmail.setError("Email: 格式錯誤");
                        return;
                    }
                    valids.add(5,true);
                }
                //自介驗證
                if (!valids.get(6)){
                    String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)(,.;')]{10,300}$";
                    if (intro == null || intro.length() == 0) {
                        etIntro.setError("自介: 請勿空白");
                        return;
                    } else if(!intro.trim().matches(enameReg)) {
                        etIntro.setError("自介: 長度必需在2到300之間");
                        return;
                    }
                    valids.add(6,true);

                }

                flag=true;


                Log.d("flag","flag:"+flag);

            }

        });




//                String status = "0"; // from radio button 0=Students 1=Coaches
//
//                Object obj = null;
//                try {
//                    newMembers = isUserNew(status, name, nickname, username, password, sex , id, email, intro);
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                stus = newMembers.getStudentsVO();
//                coas = newMembers.getCoachesVO();
//
//                if(newMembers == null ) {
//                    Common.showToast(getBaseContext(), "WRONG");
//                    return;
//                }else {
//                    Common.showToast(getBaseContext(), "OK");
//
//                }

                if (networkConnected()) {
                    try {
                        newMembers = (NewMembers) SignupTask().execute(Common.URL+"StudentsServlet").get();

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
 protected NewMembers doInBackground(Object... params) {
 String url = params[0].toString();
 allmember = (Allmember)params[1];
 String str="";
 String jsonIn;
 Gson gson=  new Gson();
 outStr = gson.toJson(allmember);


                                 //sevlet用
                                 Gson gson = new Gson();
                                 //            Type listType = new TypeToken<NewMembers>() {
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



    protected void onPostExecute(NewMembers all) {
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

private class SignupTask extends AsyncTask<Object, Integer, NewMembers> {
                Log.d(TAG, "BBBBBBBBBBBBBBBBBBBBBBBBBBBBB: ");
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
    protected NewMembers doInBackground(Object... params) {
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
//            Type listType = new TypeToken<NewMembers>() {
//            }.getType();
//            Object obj = gson.fromJson(jsonIn,Object.class);
        return  gson.fromJson(jsonIn, NewMembers.class);
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
    protected void onPostExecute(NewMembers all) {
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

    private NewMembers isUserNew(String role, String username, String nickname, String s, String password, Integer sex, String id, String email, String intro) throws ExecutionException, InterruptedException {
        Object obj = null;
        if (Common.networkConnected(this)) {
            if (SignupTask == null){
                SignupTask = new SignupTask();
            }
            NewMembers = (NewMembers) SignupTask.execute(Common.URL+"StudentsServlet", role, username);
        } else {
            Common.showToast(this, R.string.tryagain);
        }
        return (NewMembers) newmembers;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (SignupTask != null){
            SignupTask.cancel(true);
            SignupTask = null;
        }
    }

    public void PID(String id,ArrayList<Boolean> valids){
        int[] num=new int[10];
        int[] rdd={10,11,12,13,14,15,16,17,34,18,19,20,21,22,35,23,24,25,26,27,28,29,32,30,31,33};
        id=id.toUpperCase();
        if(!((id.trim().length()) ==10)){
            etID.setError("身分證字號總長度為10!!");return;
        }
        if(id.charAt(0)<'A'||id.charAt(0)>'Z'){
            etID.setError("第一個字錯誤!!");return;
        }
        if(id.charAt(1)!='1' && id.charAt(1)!='2'){
            etID.setError("第二個字錯誤!!");return;
        }
        for(int i=1;i<10;i++){
            if(id.charAt(i)<'0'||id.charAt(i)>'9'){
                etID.setError("輸入錯誤");return;
            }
        }
        for(int i=1;i<10;i++){
            num[i]=(id.charAt(i)-'0');
        }
        num[0]=rdd[id.charAt(0)-'A'];
        int sum=((int)num[0]/10+(num[0]%10)*9);
        for(int i=0;i<8;i++){
            sum+=num[i+1]*(8-i);
        }
        if(!(10-sum%10==num[9])) {
            etID.setError("身分證號錯誤");}
        valids.add(4,true);
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