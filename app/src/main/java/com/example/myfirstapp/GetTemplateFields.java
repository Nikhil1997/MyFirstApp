package com.example.myfirstapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GetTemplateFields extends AppCompatActivity {

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_template_fields);

        LinearLayout layout=(LinearLayout)findViewById(R.id.activity_get_template_fields);
        BoxView box=new BoxView(context);
        layout.addView(box.getView());



    }

    public class BoxView {
        private Context context;
        private TextView tv;
        private EditText edt;
        private Button btn;
        private View v;

        public BoxView(Context context) {

            // TODO Auto-generated constructor stub
            this.context= context;
            init();
        }

        private void init() {
            // TODO Auto-generated method stub
           LayoutInflater inflator= LayoutInflater.from(context);
            this.v= inflator.inflate(R.layout.activity_get_template_fields, null);

            this.tv=(TextView)v.findViewById(R.id.textView1);
            this.edt=(EditText)v.findViewById(R.id.editText1);
            this.btn=(Button)v.findViewById(R.id.button1);

        }

        public View getView(){
            return v;
        }
        public void setTv(TextView tv) {
            this.tv = tv;
        }
        public TextView getTv() {
            return tv;
        }
        public void setEdt(EditText edt) {
            this.edt = edt;
        }
        public EditText getEdt() {
            return edt;
        }
        public void setBtn(Button btn) {
            this.btn = btn;
        }
        public Button getBtn() {
            return btn;
        }



    }


}
