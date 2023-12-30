package com.example.mycalendar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static int id=0;
    public int yea,mont,da,hou,min;
    public String th;
    Cursor c1;
    private database db=null;
    public Bundle bd;
    private CalendarView cv;
    private ListView lv;
    private Button btadd1;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cv=findViewById(R.id.CalendarView1);
        lv=findViewById(R.id.listv);
        btadd1=findViewById(R.id.addsomething);
        et=findViewById(R.id.editTextText3);

        btadd1.setOnClickListener(btaddlis);
        lv.setOnItemClickListener(lvlis);
        cv.setOnDateChangeListener(cvlis);

        bd=new Bundle();
        Calendar c=Calendar.getInstance();
        yea=c.get(Calendar.YEAR);mont=c.get(Calendar.MONTH)+1;da=c.get(Calendar.DAY_OF_MONTH);hou=c.get(Calendar.HOUR_OF_DAY);min=c.get(Calendar.MINUTE);
        bd.putInt("ye",yea);
        bd.putInt("mon",mont);
        bd.putInt("day",da);
        db=new database(this);
        db.open();
        c1=db.getAll();
        //UPAdapter(c1);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }
   protected View.OnClickListener btaddlis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            if(et.getText().toString().equals("")){
                    Intent it=new Intent();
                    it.setClass(MainActivity.this, second.class);
                    it.putExtras(bd);
                    startActivity(it);
            }
            else {
                try{
                    String thing=et.getText().toString();
                    th=thing;
                    db.append(id,hou,min,yea,mont,da,0,th);
                    id++;
                    /*if (db.append(id,hou,min,yea,mont,da,0,thing)>0){
                        id++;
                        c1=db.getAll();
                        UPAdapter(c1);
                        ClearEdit();
                    }*/
                }catch (Exception err){
                    Toast.makeText(getApplicationContext(),
                            "資料不正確!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    protected AdapterView.OnItemClickListener lvlis=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };
    protected CalendarView.OnDateChangeListener cvlis =new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            yea=year;mont=month;da=dayOfMonth;
            Intent it=new Intent();
            it.setClass(MainActivity.this,second.class);
            bd.putInt("ye",year);
            bd.putInt("mon",month);
            bd.putInt("day",dayOfMonth);
        }
    };
    public void ClearEdit(){
        et.setText("");
    }
    public void UPAdapter(Cursor cur){
        if (cur != null && cur.getCount() >= 0){
            String[] fromColumns = new String[] {"HOUR","MIN","WHAT"};
            int[] toViews = new int[] {android.R.id.text1, android.R.id.text2};

            SimpleCursorAdapter adp = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2, cur,
                    fromColumns, toViews,
                    0);
            lv.setAdapter(adp);
        }
    }
}