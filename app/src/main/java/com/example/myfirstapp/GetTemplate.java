package com.example.myfirstapp;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GetTemplate extends AppCompatActivity {

    TextView content, List , numbers;

    String Email,userid;
    Button btn1;
    int id_ , p;
   final ArrayList<Integer> arrlist = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_template);

        Button Get_User_Templates = (Button) findViewById(R.id.BgetTemplate);


        List = (TextView)findViewById(R.id.list);
        numbers = (TextView) findViewById(R.id.numbers);

        Get_User_Templates.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                try {

                    // CALL  method to make post method call

                    GetTemp();


                } catch (Exception ex) {

                    content.setText(" url exeption! ");//
                }
            }
        });








    }


    public void GetTemp() throws UnsupportedEncodingException {

//
     //   Login = email.getText().toString();
        Bundle bundle = getIntent().getExtras();
        String Email = bundle.getString("Email");
        //Bundle bundle = getIntent().getExtras();

        Log.d("Email",Email);

      //  Log.d("email",Login);
        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("http://www.mobizite.com/LeadTemplateInformation");//2


        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);

        nameValuePair.add(new BasicNameValuePair("email", Email));


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
                Log.d("Http Post Response:", a);
                try {
                    JSONObject json = new JSONObject(a);
                    Boolean success = json.getBoolean("success");
                    Log.v("success:", "" + success);
                    JSONArray info = json.getJSONArray("leadTemplateInfo");
                    ArrayList<Integer> arrlist = new ArrayList<Integer>(info.length());
                     ArrayList<String> templist = new ArrayList<String>(info.length());
                    for (int i = 0; i < info.length(); i++) {
                        JSONObject d = info.getJSONObject(i);


                        arrlist.add(d.getInt("id"));
                        templist.add(d.getString("name"));

                        // String b= d.getString("name");
                    }

                    Log.d("ids", arrlist.toString());
                    Log.d("names", templist.toString());

                    //   Log.d("id", "" + id_arab);
                    //  Log.d("Name", "" + b);

                    // JSONObject e = info.getJSONObject(1);
                    //String f = e.getString("name");
                    //  id_oracle = e.getInt("id");
                    //Log.d("id", "" + id_oracle);
                    // Log.d("Name", "" + f);
                    ScrollView sv = new ScrollView(this);

                    sv.setLayoutParams(new android.support.v7.app.ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
                    LinearLayout ll = new LinearLayout(this);
                    ll.setOrientation(LinearLayout.VERTICAL);

                    final int arraySize = info.length();

                    Log.d("arrlist.size()=" , Integer.toString(arrlist.size()));
                    Log.d("arraysize", Integer.toString(arraySize));
                    for (int i = 0; i < arraySize; i++) {
                        JSONObject data = info.getJSONObject(i);
                        String name = data.getString("name");
                       // JSONObject number = info.getJSONObject(i);
                        //int no = number.getInt("id");
                      //  List.append(name);
                        //List.append("\n");
                        TextView label = new TextView(this);

                        label.setHint(name);

                        ll.addView(label);
                       // numbers.append(Integer.toString(no));
                       // numbers.append("\n");
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        Button btn = new Button(this);
                        int p = arrlist.get(i);
                       Log.d("p",Integer.toString(p));
                        btn.setId(p);
                       //btn.setOnClickListener(getOnClickDoSomething(button));
                       final int id_ = btn.getId();
                        btn.setText("button " + id_);
                        btn.setBackgroundColor(Color.rgb(60, 80, 100));
                         ll.addView(btn, params);

                        btn1 = ((Button) findViewById(id_));


                        //    View.OnClickListener getOnClickDoSomething(final Button button)  {
                        //      return new View.OnClickListener() {
                        //        public void onClick(View v) {
                        //        button.setText("text now set.. ");
                        //      }
                        //};
                        //}




                        btn.setOnClickListener(new Button.OnClickListener() {

                            public void onClick(View v) {

                                try {

                                 Log.d("data","valid");
                                    int id_ = v.getId();
                                    Button button = (Button) findViewById(id_);
                                    String message = button.getText().toString();
                                    Log.d("ID", Integer.toString(id_));
                                   Log.d("message", message);
                                    // CALL GetText method to make post method call
                                    //  for(int j=0;j<arraySize;j++) {


                                    //    if (id_ == p) {


//                                    ArrayList<Integer> arrlist = new ArrayList<Integer>(arraySize);
//                                   int p = arrlist.get(id_);
//                                   Log.d("no",Integer.toString(p));
                                    Get_Fields(id_);
                                    //  }
                                    //}
                                } catch (Exception ex) {

                                    content.setText(" url exeption! ");//
                                }
                            }
                        });








                    }

                   ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                   // this.setContentView(ll, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    sv.addView(ll);
                    this.setContentView(sv);













                    //  btn1.setOnClickListener(new View.OnClickListener() {
                    //    public void onClick(View view) {


//                            }
                    //                      });
                    //                }


                    //
                    //here
                }
                catch (JSONException e) {
                    Log.d("data","invalid");
                    //some exception handler code.
                }

            }
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }

    }

    public void Get_Fields(int p) throws UnsupportedEncodingException {

//
        //   Login = email.getText().toString();
        //   Bundle bundle = getIntent().getExtras();
        // String Email = bundle.getString("Email");
        // Log.d("Email",Email);


        //  Log.d("email",Login);
        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("http://www.mobizite.com/LeadTemplateFieldInformation");//2


        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);

        nameValuePair.add(new BasicNameValuePair("template_id", Integer.toString(p)));


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
                Log.d("Http Post Response:", a);
                Intent i = new Intent(GetTemplate.this, GetFields.class);
                Bundle bun = new Bundle();
                bun.putString("Response", a);
                //here
              //  Log.d("userid",userid); this????yes
                Bundle bundle = getIntent().getExtras();
                String userid = bundle.getString("userid");
                Log.d("user",userid);
                bun.putString("userid",userid);
                i.putExtras(bun);
                startActivity(i);

                try {
                    ArrayList<String> list = new ArrayList<String>();

                    JSONObject res = new JSONObject(a);
                    Boolean success = res.getBoolean("success");
                    JSONArray fields = res.getJSONArray("leadTemplateFieldInfo");

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





                } catch (JSONException e) {

                    Log.d("data", "invalid");
                }


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




