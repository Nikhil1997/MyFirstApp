package com.example.myfirstapp;

import android.app.ProgressDialog;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    TextView signuplink;
    EditText email, password;
    String Email,Password;
Button Blogin;
    private static final int REQUEST_SIGNUP = 0;


    Boolean validuser , correctPassword,validpass;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Blogin = (Button)findViewById(R.id.btn_login);

        email = (EditText) findViewById(R.id.input_email);


        password = (EditText) findViewById(R.id.input_password);
        signuplink = (TextView) findViewById(R.id.link_signup);


      // final String Email = email.getText().toString().trim();



   //     boolean isEmailValid(CharSequence email) {
    //       return android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches();
  //  }




    //    Log.d("pass",correctPassword.toString());



     //   Log.d("val",validuser.toString());
      //  Log.d("val",validpass.toString());




        // email.addTextChangedListener(new TextWatcher() {


           //  @Override
            // public void afterTextChanged(Editable s) {
            //     if (Email.matches(emailPattern) && s.length() > 0) {
                    // Toast.makeText(getApplicationContext(),"Valid Email Address",Toast.LENGTH_SHORT);
          //           content.setText("valid Email");
            //     }
        //         else
      //           {
                    // Toast.makeText(getApplicationContext(),"Enter Valid Email Address",Toast.LENGTH_SHORT);
    //                 content.setText("Invalid Email");
  //               }
//
      //       }
    //         @Override
  //           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//             }

    //         @Override
  //           public void onTextChanged(CharSequence s, int start, int before, int count) {

         //    }
       //  });







        //button

        // Log.d("CLASS","abc");

        Blogin.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v)
            {

               login();

            }
        });



        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivityForResult(inte,REQUEST_SIGNUP);
            }
        });




    }


    public void login(){

        if (!validate()) {
            onLoginFailed();
            return;
        }
        Blogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        try {

            GetTextNew();

        }
        catch (Exception ex)
        {

        }

          new android.os.Handler().postDelayed(new Runnable() {

              public void run() {
                  onLoginsuccess();

                 progressDialog.dismiss();

              }
          },3000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {


                // By default we just finish the Activity and log them in automatically
               //this.finish();
            }
        }
    }



    //   @Override
 //   public void onBackPressed() {
        // disable going back to the MainActivity
  //      moveTaskToBack(true);
  //  }

    public void onLoginsuccess() {
        Blogin.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        Blogin.setEnabled(true);
    }







    public  void  GetTextNew()  throws UnsupportedEncodingException
    {


       Email   = email.getText().toString();
        Password   = password.getText().toString();
        Log.d("Email",Email);
        Log.d("Password",Password);

//        String data =  URLEncoder.encode("email", "UTF-8") + "="
//                + URLEncoder.encode(Email, "UTF-8");
//
//        data += "&" + URLEncoder.encode("password", "UTF-8")
//                + "=" + URLEncoder.encode(Password, "UTF-8");
//        Log.d("data",data);

        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("http://www.mobizite.com/UserInformation");//2


        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("email", Email));
        nameValuePair.add(new BasicNameValuePair("password", Password));

        // nameValuePair.add(new BasicNameValuePair("password", "123456789"));
        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log

            Log.v("response code", response.getStatusLine()
                    .getStatusCode() + "");
                  BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer result =new StringBuffer();
                String line="";
                while((line=rd.readLine())!=null){
                    result.append(line);
                }
                String a="";
                a=result.toString();
                Log.d("Http Post Response:", a);
                try {
                    JSONObject json = new JSONObject(a);
                    Boolean success = json.getBoolean("success");
                    Log.v("success:", "" + success);
                    JSONArray info = json.getJSONArray("userInfo");
                    JSONObject d = info.getJSONObject(0);
                    int id = d.getInt("id");
                    Log.d("id", "" + id);


                   if (success) {
                      Intent i = new Intent(MainActivity.this, GetTemplate.class);
                     //  Intent in = new Intent(MainActivity.this , GetFields.class) ;
                       EditText email = (EditText) findViewById(R.id.input_email);
                       String getrec=email.getText().toString();
                       Bundle bundle = new Bundle();
                       bundle.putString("Email", getrec);
                     //  Bundle bun = new Bundle();
                       bundle.putString("userid",Integer.toString(id));
                      //in.putExtras(bun) ;

                       i.putExtras(bundle);
                        startActivity(i);






                  }
                }
                catch (JSONException e) {
                    Log.d("data","invalid");
                    //some exception handler code.
                }


        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }

    }




        //here
//        String endPoint= "https://safe-citadel-91138.herokuapp.com/questions";
//
//        try {
//
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpPost post = new HttpPost(endPoint);
//            post.addHeader("Content-Type", "application/json");
//            JSONObject obj = new JSONObject();
//
//            obj.put("firstName", "TESTF");
//            obj.put("lastName", "TESTL");
//            obj.put("email", "support@mlab.com");
//
//            StringEntity entity = new StringEntity(obj.toString());
//            post.setEntity(entity);
//            HttpResponse response = httpClient.execute(post);
//            Log.d("click",response.toString());//response
//        }
//        catch (Exception e){
//
//        }


    public boolean validate() {
        boolean valid = true;

        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if (Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (Password.isEmpty() || Password.length() < 4 || Password.length() > 10) {
          password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
           password.setError(null);
        }

        return valid;
    }





}




