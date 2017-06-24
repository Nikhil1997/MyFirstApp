package com.example.myfirstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.UnsupportedSchemeException;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    EditText firstname,lastname,emailtext,passwordtext,companyname,mobilenumber;
    TextView linklogin;
    Button sign_up;
    String FirstName, LastName , EmailText , PasswordText , CompanyName, MobileNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstname= (EditText)findViewById(R.id.input_firstname);
        lastname= (EditText)findViewById(R.id.input_lastname);
        emailtext= (EditText)findViewById(R.id.input_email);
        passwordtext= (EditText)findViewById(R.id.input_password);
        companyname= (EditText)findViewById(R.id.input_companyname);
        mobilenumber= (EditText)findViewById(R.id.input_Mobilenumber);
        sign_up = (Button)findViewById(R.id.btn_signup);
        linklogin = (TextView)findViewById(R.id.link_login) ;

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     signup();
            }
        });
       linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                //finish();
                Intent t = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(t);

            }
        });


    }

    public void signup() {


        if (!validate()) {
            onSignupFailed();
            return;
        }

        sign_up.setEnabled(false);

        final ProgressDialog progress_Dialog = new ProgressDialog(this,
                R.style.AppTheme);
        progress_Dialog.setIndeterminate(true);
        progress_Dialog.setMessage("Creating Account...");
        progress_Dialog.show();
        try {
            Accountdetails();
        } catch (Exception e) {

        }
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progress_Dialog.dismiss();
                    }
                }, 3000);


    }





    public void onSignupSuccess() {
        sign_up.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(this,"New Account Created Successfully",Toast.LENGTH_LONG).show();
        finish();
    }

    public void onSignupFailed() {
       Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();

       sign_up.setEnabled(true);
    }


public void Accountdetails() throws UnsupportedEncodingException {
    FirstName = firstname.getText().toString();
    LastName = lastname.getText().toString();
    EmailText = emailtext.getText().toString();
    PasswordText = passwordtext.getText().toString();
    CompanyName = companyname.getText().toString();
    MobileNumber = mobilenumber.getText().toString();

    HttpClient httpClient = new DefaultHttpClient();
    // replace with your url
    HttpPost httpPost = new HttpPost("http://www.mobizite.com/UserSignupInformation");
    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(6);
    nameValuePair.add(new BasicNameValuePair("first_name", FirstName));
    nameValuePair.add(new BasicNameValuePair("last_name", LastName));
    nameValuePair.add(new BasicNameValuePair("email", EmailText));
    nameValuePair.add(new BasicNameValuePair("password", PasswordText));
    nameValuePair.add(new BasicNameValuePair("company_name", CompanyName));
    nameValuePair.add(new BasicNameValuePair("mobile_number", MobileNumber));

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
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        String a = "";
        a = result.toString();
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
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                //  Intent in = new Intent(MainActivity.this , GetFields.class) ;
                EditText email = (EditText) findViewById(R.id.input_email);
                String getrec = email.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("Email", getrec);
                //  Bundle bun = new Bundle();
                bundle.putString("userid", Integer.toString(id));
                //in.putExtras(bun) ;

                i.putExtras(bundle);
                startActivity(i);


            }
        } catch (JSONException e) {
            Log.d("data", "invalid");
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
    public boolean validate() {
        boolean valid = true;

        String name = firstname.getText().toString();
        String email = emailtext.getText().toString();
        String password = passwordtext.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            firstname.setError("at least 3 characters");
            valid = false;
        } else {
           firstname.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
          emailtext.setError("enter a valid email address");
            valid = false;
        } else {
           emailtext.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
           passwordtext.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordtext.setError(null);
        }

        return valid;
    }













}
