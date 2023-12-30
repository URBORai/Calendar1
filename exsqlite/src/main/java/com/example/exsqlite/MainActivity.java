package com.example.exsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase DB=null;
    private final static String CREATE_TABLE="create table table01(_id integer primary key,num integer,data text)";

    EditText E1;
    Button B1;
    ListView L1;
    String Str,ItemData;
    int n=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        E1=findViewById(R.id.editTextText1);
        B1=findViewById(R.id.button1);
        L1=findViewById(R.id.ListView1);

        B1.setOnClickListener(Lis);
        E1.setKeyListener(null);

        ItemData="資料項目"+n;
        Str="insert into table01 (num,data) values ("+n+",'"+ItemData+"')";
        E1.setText(Str);

        DB=openOrCreateDatabase("db1.db",MODE_PRIVATE,null);
        try {
            DB.execSQL(CREATE_TABLE);
        }
        catch (Exception e){
            UpdateAdapter();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        DB.execSQL("DROP TABLE table01");
        DB.close();
    }
    private View.OnClickListener Lis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                DB.execSQL(E1.getText().toString());
                UpdateAdapter();
                n++;
                ItemData="資料項目"+n;
                Str="insert into table01 (num,data) values ("+n+",'"+ItemData+"')";
                E1.setText(Str);
                setTitle("資料新增完畢!");
            }
            catch (Exception e){
                setTitle("SQL語法錯誤!");
            }
        }
    };
    public void UpdateAdapter(){
        Cursor cur=DB.rawQuery("select * from table01",null);
        if (cur!=null&&cur.getCount()>=0){
            SimpleCursorAdapter A1=new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,
                    cur,new String[]{"num","data"},
                    new int[]{android.R.id.text1,android.R.id.text2},
                    0);
            L1.setAdapter(A1);
        }
    }
}