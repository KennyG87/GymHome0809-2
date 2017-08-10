package com.example.kennykao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.kennykao.gymhome_2.R;

public class HomepageActivity extends AppCompatActivity {
    private Button btLinktoLogin;
    private Button btLinktoSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        btLinktoLogin = (Button)findViewById(R.id.btLinktoLogin);
        btLinktoSignup = (Button)findViewById(R.id.btLintoSignup);

        btLinktoLogin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(HomepageActivity.this,LoginActivity.class);
                startActivity(intent);
                HomepageActivity.this.finish();
            }
        });
        btLinktoSignup.setOnClickListener(new Button.OnClickListener(){
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
