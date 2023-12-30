package com.example.exinputdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private EditText eID,ePW;
    private TextView eContent;
    private Button bAppend,bClear,bEnd;
    private static final String FileName="login.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eID=findViewById(R.id.editTextText1);
        ePW=findViewById(R.id.editTextText2);
        eContent=findViewById(R.id.editTextText3);
        bAppend=findViewById(R.id.button1);
        bClear=findViewById(R.id.button2);
        bEnd=findViewById(R.id.button3);

        bAppend.setOnClickListener(listener);
        bClear.setOnClickListener(listener);
        bEnd.setOnClickListener(listener);

        DisplayFile(FileName);
    }
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button1:
                    if(eID.getText().toString().equals("")||
                    ePW.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"帳號密碼都必須輸入!",Toast.LENGTH_LONG).show();
                        break;
                    }
                    FileOutputStream Fout=null;
                    BufferedOutputStream BuffOut=null;
                    try {
                        Fout=openFileOutput(FileName,MODE_APPEND);
                        BuffOut=new BufferedOutputStream(Fout);

                        BuffOut.write(eID.getText().toString().getBytes());
                        BuffOut.write("\n".getBytes());
                        BuffOut.write(ePW.getText().toString().getBytes());
                        BuffOut.write("\n".getBytes());
                        BuffOut.close();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    DisplayFile(FileName);
                    eID.setText("");
                    ePW.setText("");
                    break;
                case R.id.button2:
                    try {
                        Fout=openFileOutput(FileName,MODE_PRIVATE);
                        Fout.close();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    DisplayFile(FileName);
                    break;
                case R.id.button3:
                    finish();
            }
        }
    };
    private void DisplayFile(String Fname){
        FileInputStream Fin=null;
        BufferedInputStream Buffin=null;
        try {
            Fin=openFileInput(Fname);
            Buffin=new BufferedInputStream(Fin);
            byte BuffByte []=new byte[20];
            eContent.setText("");

            do {
                int Flag=Buffin.read(BuffByte);
                if (Flag==-1){
                    break;
                }
                else {
                    eContent.append(new String(BuffByte),0,Flag);
                }
            }while (true);
            Buffin.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}