package com.example.myfirstapp;

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
    TextView content;
    EditText email, password;
    String Email,Password;
Button Blogin;


    Boolean validuser , correctPassword,validpass;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Blogin = (Button)findViewById(R.id.Blogin);
        content = (TextView)findViewById(R.id.content);
        email = (EditText) findViewById(R.id.TFusername);
        validuser=false;
        validpass=false;
       correctPassword=false;

        password = (EditText) findViewById(R.id.editText4);



      // final String Email = email.getText().toString().trim();

       final String regEx =  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

   //     boolean isEmailValid(CharSequence email) {
    //       return android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches();
  //  }




        Log.d("pass",correctPassword.toString());

        email.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){

                if(email.getText().length()<5  ){
                    Toast.makeText(getApplicationContext(), "Invalid Username", Toast.LENGTH_SHORT).show();
                    //  email.setError("Invalid");
                    validuser =false;
                }else
                {validuser=true;}
                String Email = email.getText().toString().trim();



                if(Email.matches(regEx)) {
                    validuser=true;
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                                      validuser=false;
                }

            }

        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus){

                if(password.getText().length()<6){
                    password.setError("Enter minimum 6 Characters");
                    correctPassword = false;
                }
                else if(password.getText().length() >= 6){
                    correctPassword = true;
                }

              //  if(validuser && correctPassword){
               //     Blogin.setEnabled(true);
               // }else{
                 //   Blogin.setEnabled(false);
               // }


            }

        });

        Log.d("val",validuser.toString());
        Log.d("val",validpass.toString());




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


             try {
               //    if(Email.matches(regEx))
                 //  {
                // if(valid){

                 GetTextNew();

                 //  }
                 //else
                  // {oToast.makeText(getApplicationContext(), "Form is invalid", Toast.LENGTH_SHORT).show();
                  // }

              }
              catch (Exception ex)
              {

              }
            }
        });}



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
                       EditText email = (EditText) findViewById(R.id.TFusername);
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





}




