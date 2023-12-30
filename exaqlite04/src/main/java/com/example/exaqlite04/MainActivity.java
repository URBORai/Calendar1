package com.example.exaqlite04;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyDB db=null;

    Button bAppend,bEdit,bDelete,bClear;
    EditText eName,ePrice;
    ListView l1;
    Cursor c1;
    long myId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eName=findViewById(R.id.editTextTextName);
        ePrice=findViewById(R.id.editTextTextPrice);
        l1=findViewById(R.id.ListView1);
        bAppend=findViewById(R.id.buttonNew);
        bEdit=findViewById(R.id.buttonEdit);
        bDelete=findViewById(R.id.buttonDelete);
        bClear=findViewById(R.id.buttonClear);

        bAppend.setOnClickListener(MyLis);
        bEdit.setOnClickListener(MyLis);
        bDelete.setOnClickListener(MyLis);
        bClear.setOnClickListener(MyLis);

        l1.setOnItemClickListener(ListLis);

        db=new MyDB(this);
        db.open();
        c1=db.getAll();
        UPAdapter(c1);
    }
    private View.OnClickListener MyLis=new View.OnClickListener() {
        public void onClick(View v) {
            try {
                switch (v.getId()){
                    case R.id.buttonNew:
                        int price=Integer.parseInt(ePrice.getText().toString());
                        String name=eName.getText().toString();
                        if (db.append(name,price)>0){
                            c1=db.getAll();
                            UPAdapter(c1);
                            ClearEdit();
                        }
                        break;
                    case R.id.buttonEdit:
                        int price1=Integer.parseInt(ePrice.getText().toString());
                        String name1=eName.getText().toString();
                        if (db.update(myId,name1,price1)){
                            c1=db.getAll();
                            UPAdapter(c1);
                        }
                        break;
                    case R.id.buttonDelete:
                        if (c1!=null&&c1.getCount()>=0){
                            AlertDialog.Builder b1=new AlertDialog.Builder(MainActivity.this);
                            b1.setTitle("確定刪除");
                            b1.setMessage("確定要刪除"+eName.getText()+"這筆資料");
                            b1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            b1.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                               if(db.delete(myId)){
                                   c1=db.getAll();
                                   UPAdapter(c1);
                                   ClearEdit();
                               }
                                }
                            });
                            b1.show();
                        }
                        break;
                    case R.id.buttonClear:
                        ClearEdit();
                        break;
                }
            }
            catch (Exception err){
                Toast.makeText(getApplicationContext(),
                        "資料不正確!",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private AdapterView.OnItemClickListener ListLis=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ShowData(id);
            c1.moveToPosition(position);
        }
    };
    private void ShowData(long id){
        Cursor c=db.get(id);
        myId=id;
        eName.setText(c.getString(1));
        ePrice.setText(""+c.getInt(2));
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }
    public void UPAdapter(Cursor cur){
        if (cur != null && cur.getCount() >= 0){
            SimpleCursorAdapter adp=new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,cur,
                    new String[] {"name","price"},
                    new int[] {android.R.id.text1,android.R.id.text2},
                    0);
            l1.setAdapter(adp);
        }
    }
    public void ClearEdit(){
        eName.setText("");
        ePrice.setText("");
    }

}