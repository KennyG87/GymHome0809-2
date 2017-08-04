package com.example.kennykao.gymhome_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomepageActivity extends AppCompatActivity {
    private Button btLogin;
    private Button btSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        btLogin = (Button)findViewById(R.id.btLogin);
        btSignup = (Button)findViewById(R.id.btSignup);
        btLogin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(HomepageActivity.this,LoginActivity.class);
                startActivity(intent);
                HomepageActivity.this.finish();
            }
        });
        btSignup.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(HomepageActivity.this,SignupActivity.class);
                startActivity(intent);
                HomepageActivity.this.finish();
            }
        });
    }


}
