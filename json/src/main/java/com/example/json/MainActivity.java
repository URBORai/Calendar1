package com.example.json;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    protected Button bJson;
    protected TextView tJson;
    protected String strObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bJson=findViewById(R.id.buttonJSON);
        tJson=findViewById(R.id.textViewData);

        strObj=getString(R.string.jsondata);

        bJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonobj=null;
                try {
                    jsonobj=new JSONObject(strObj);
                    tJson.setText("USER ID :"+jsonobj.getString("userId")+"\n"
                    +"Password :"+jsonobj.getString("pwd")+"\n"
                    +"Book1 :"+jsonobj.getJSONArray("booklist").getString(0)+"\n"
                    +"Book2 :"+jsonobj.getJSONArray("booklist").getString(1)+"\n"
                    +"Book3 :"+jsonobj.getJSONArray("booklist").getString(2));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}