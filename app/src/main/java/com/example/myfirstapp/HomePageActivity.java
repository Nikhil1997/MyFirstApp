package com.example.myfirstapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;


public class HomePageActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {


    Button sgnup ;
    SignInButton google;
    LoginButton fb;
    TextView name,user;
   ImageView image;
    CallbackManager callbackManager;
    GoogleApiClient googleApiClient;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private static final String HOME_ACTIVITIES = "com.example.MyFirstApp.GetTemplate";
    private static final int fb_REQUEST_CODE = 0;
    private static final int REQ_CODE = 900 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_home_page);
        name = (TextView)findViewById(R.id.feedz);
        fb = (LoginButton) findViewById(R.id.fblogin);
       google = (SignInButton) findViewById(R.id.googlelogin);
        sgnup = (Button)findViewById(R.id.sigup);
        user = (TextView) findViewById(R.id.log);
        image = (ImageView) findViewById(R.id.imageView1);
        google.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        callbackManager = CallbackManager.Factory.create();
        fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                            //    String name = "";
                            //    String email = "";
                             //   String id = "";
                                final JSONObject jsonObject = response.getJSONObject();
                                String nombre = "";
                                String Email = "";
                                String id = "";
                                try {
                                    nombre = jsonObject.getString("name");
                                    Email =  jsonObject.getString("email");
                                    Toast.makeText(getApplicationContext(),"Hi, " + nombre , Toast.LENGTH_LONG).show();
                                    Log.d("email",Email);
                              //      name = object.getString("name");
                              //      email =  object.getString("email");
                             //      Log.d("email",email);
                             //       Log.d("name",name);
                                }

                                catch(JSONException ex) {

                                    ex.printStackTrace();
                                }
                            }
                        });
             //   Intent intent = new Intent(getApplicationContext(),GetTemplate.class);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

             //   startActivity(intent);


         //     fb.setVisibility(View.INVISIBLE); //<- IMPORTANT

          //      Intent intent = new Intent(getApplicationContext(),GetTemplate.class);
          //      startActivity(intent);
           //     finish();//<- IMPORTANT
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Login Cancelled",Toast.LENGTH_LONG);
            }

            @Override
            public void onError(FacebookException error) {

            }
        });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
    // callbackManager.onActivityResult(requestCode,resultCode,data);

        if(requestCode == REQ_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            HandleResult(result);

        }






      //  if (requestCode == REQUEST_FB) {
       //     if (resultCode == RESULT_OK) {
       // if (requestCode == fb_REQUEST_CODE) {
     //      this.finish();
     //   }
//
                // By default we just finish the Activity and log them in automatically
             // this.finish();
//            }
  //      }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.googlelogin:
                signin();
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signin()
    {
        Intent in = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(in,REQ_CODE);

    }

    private void HandleResult(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();


        }

    }



}
