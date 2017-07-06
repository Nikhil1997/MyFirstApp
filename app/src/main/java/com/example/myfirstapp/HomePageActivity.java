package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {


    Button fb,google,sgnup ;
    TextView name,user;
   ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        name = (TextView)findViewById(R.id.feedz);
        fb = (Button)findViewById(R.id.fblogin);
        google = (Button)findViewById(R.id.googlelogin);
        sgnup = (Button)findViewById(R.id.sigup);
        user = (TextView) findViewById(R.id.log);
        image = (ImageView) findViewById(R.id.imageView1);

        sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(in);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(d);
            }
        });



    }
}
