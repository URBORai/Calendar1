package com.example.json_volley;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ListView Lis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Lis=findViewById(R.id.ListView1);

        String url="https://data.taipei/api/v1/dataset/74cfc01d-242f-428d-bc2f-caf5edd6e404?scope=resourceAquire";
        getData(url);
    }
    private String getData(String Urlstr){
        String result= "";
        JsonObjectRequest JOR=
                new JsonObjectRequest(Urlstr,null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject Res){
                        try {
                            parseJSON(Res);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                    }
                });
        Volley.newRequestQueue(this).add(JOR);
        return result;
    }
    private void parseJSON(JSONObject JO) throws JSONException{
        ArrayList<String> List=new ArrayList<>();
        JSONArray data = JO.getJSONObject("result").getJSONArray("results");
        for (int i=0;i<data.length();i++){
            JSONObject o=data.getJSONObject(i);
            String str ="_id:"+o.getString("_id")+"\n"
                    +"項次:"+o.getString("項次")+"\n"
                    +"停車場名稱:"+o.getString("停車場名稱")+"\n"
                    +"經度:"+o.getString("經度")+"\n"
                    +"緯度:"+o.getString("緯度")+"\n"
                    +"機車:"+o.getString("機車");
            List.add(str);
        }
        Lis.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,List));
    }
}