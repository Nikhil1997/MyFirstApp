package com.example.myfirstapp;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

public class GetFields extends AppCompatActivity {

    TextView content;
      int temp_id;
    List<TextView> allTxs = new ArrayList<TextView>();
    List<EditText> allEds = new ArrayList<EditText>();
   String userid;
    String[] FieldName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] FieldName;
        // setContentView(R.layout.activity_get_fields);
        // final LinearLayout lm = (LinearLayout) findViewById(R.id.activity_get_fields);
        //  LinearLayout.LayoutParams lparams=new LinearLayout.LayoutParams(
        //         ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        // LinearLayout linearLayout = new LinearLayout(this);
        // linearLayout.setOrientation(LinearLayout.VERTICAL);
        // create the layout params that will be used to define how your
        // button will be displayed
        //  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        // LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Bundle bun = getIntent().getExtras();
        String Response = bun.getString("Response");

        String userid =  bun.getString("userid");
        Log.d("out",userid);
        Log.d("Output", Response);


        try {
            ArrayList<String> list = new ArrayList<String>();

            JSONObject res = new JSONObject(Response);
            Boolean success = res.getBoolean("success");
            JSONArray fields = res.getJSONArray("leadTemplateFieldInfo");
            JSONObject d = fields.getJSONObject(0);
             temp_id = d.getInt("template_id");
            Log.d("temp",Integer.toString(temp_id));
            for (int j = 0; j < fields.length(); j++) {
                JSONObject fie = fields.getJSONObject(j);
                String b = fie.getString("field_name");
                Log.d("field_name", b);

            }
            for (int k = 0; k < fields.length(); k++) {
                JSONObject datatype = fields.getJSONObject(k);
                String c = datatype.getString("data_type");
                Log.d("data_type", c);

            }


            if (fields != null) {
                for (int m = 0; m < fields.length(); m++) {
                    list.add(fields.get(m).toString());
                    Log.d("List", list.get(m));
                }


            }
            ScrollView sv = new ScrollView(this);

            sv.setLayoutParams(new android.support.v7.app.ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            String[] stringname = new String[fields.length()];

            for (int j = 0; j < fields.length(); j++) {
                // Create LinearLayout

                JSONObject fie = fields.getJSONObject(j);
                String b = fie.getString("field_name");
                stringname[j]=b;
                JSONObject datatype = fields.getJSONObject(j);
                String c = datatype.getString("data_type");

                // Create TextView


                    TextView label = new TextView(this);

                    label.setHint(b);

                    ll.addView(label);

                    EditText lab = new EditText(this);

                    allEds.add(lab);

                    lab.setHint(c);
                    ll.addView(lab);






            }

            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            sv.addView(ll);
            //  this.setContentView(ll, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            this.setContentView(sv);


            // Create Button
            final Button btn = new Button(this);
                         // Give button an ID

            btn.setText("Save");
            btn.setBackgroundColor(Color.rgb(70, 120, 60));
            // set the layoutParams on the button



            // Set click listener for button


            //Add button to LinearLayout
            ll.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override


                public void onClick(View v) {

                    try {
                        Bundle bun = getIntent().getExtras();
                        String Response = bun.getString("Response");
                        JSONObject res = new JSONObject(Response);
                        Boolean success = res.getBoolean("success");
                        JSONArray fields = res.getJSONArray("leadTemplateFieldInfo");

                        String[] stringname = new String[fields.length()];

                        for (int j = 0; j < fields.length(); j++) {
                            // Create LinearLayout

                            JSONObject fie = fields.getJSONObject(j);
                            String b = fie.getString("field_name");
                            stringname[j]=b;









                        }

                        // CALL  method to make post method call

                        GetForm(stringname);


                    } catch (Exception ex) {

                        content.setText(" url exeption! ");//
                    }


                }
            });
            //Add button to LinearLayout defined in XML
            //            lm.addView(ll);


        } catch (JSONException e) {

            Log.d("data", "invalid");
        }
    }


    public void GetForm(String[] stringname) throws UnsupportedEncodingException {

//
        //   Login = email.getText().toString();
        //  Bundle bundle = getIntent().getExtras();
        //String Email = bundle.getString("Email");
        // Log.d("Email",Email);


        String[] string = new String[allEds.size()];
        String[] stringName = new String[allTxs.size()];
        //  Log.d("email",Login);
        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("http://www.mobizite.com/LeadCapture");//2


        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(allEds.size());
        Bundle bun = getIntent().getExtras();
        String userid =  bun.getString("userid");

        for(int i=0; i < allEds.size(); i++){
            string[i] = allEds.get(i).getText().toString();


            //add fieldnames
            nameValuePair.add(new BasicNameValuePair(stringname[i], string[i]));
           // nameValuePair.add(new BasicNameValuePair("first_name", ));

          }
        nameValuePair.add(new BasicNameValuePair("template_id", Integer.toString(temp_id)));
        nameValuePair.add(new BasicNameValuePair("user_id", userid));
        Log.d("senddata",nameValuePair.toString());
        // nameValuePair.add(new BasicNameValuePair("password", "123456789"));
        //Encoding POST data
     try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
         //print here  wht u r sending

        } catch (UnsupportedEncodingException e) {
            //log exception
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
            if (response.getStatusLine()
                    .getStatusCode() == 200) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                String a = "";
                a = result.toString();

                Log.d("template_id",Integer.toString(temp_id));
               // Log.d("user_id",userid);
                Log.d("Http Post Response:", a);


            }
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }

    }
}

