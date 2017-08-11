package com.example.kennykao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kennykao.gymhome_2.R;

public class SignupActivity extends AppCompatActivity {
    private Button btSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btSignup = (Button)findViewById(R.id.btSignup);
        btSignup.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(SignupActivity.this, HomepageActivity.class);
                startActivity(intent);
                SignupActivity.this.finish();
            }

//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent();
//                intent.setClass(SignupActivity.this, fakeMainpageActivity.class);
//                startActivity(intent);
//                SignupActivity.this.finish();
//            }
        });
    }


}
