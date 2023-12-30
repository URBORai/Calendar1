package com.example.ch12_ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private EditText EIn,EResult;
    private Button BIn,BDelete,BEnd;
    private static final String FilePhoneNumber="PhoneNumber.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EIn=findViewById(R.id.editTextTextIn);
        EResult=findViewById(R.id.editTextTextResult);
        BIn=findViewById(R.id.buttonIn);
        BDelete=findViewById(R.id.buttonDelete);
        BEnd=findViewById(R.id.buttonEnd);

        BIn.setOnClickListener(Listener);
        BDelete.setOnClickListener(Listener);
        BEnd.setOnClickListener(Listener);

        DisplayFile(FilePhoneNumber);

        EResult.setKeyListener(null);
    }
    private View.OnClickListener Listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonIn:
                    if(EIn.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"一定要輸入",Toast.LENGTH_LONG).show();
                        break;
                    }
                    FileOutputStream FOut=null;
                    BufferedOutputStream BuffOut=null;
                    try {
                        FOut=openFileOutput(FilePhoneNumber,MODE_APPEND);
                        BuffOut=new BufferedOutputStream(FOut);;

                        BuffOut.write(EIn.getText().toString().getBytes());
                        BuffOut.write("\n".getBytes());
                        BuffOut.close();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    DisplayFile(FilePhoneNumber);
                    EIn.setText("");
                    break;
                case R.id.buttonDelete:
                    try {
                        FOut=openFileOutput(FilePhoneNumber,MODE_PRIVATE);
                        FOut.close();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    DisplayFile(FilePhoneNumber);
                    break;
                case R.id.buttonEnd:
                    finish();
            }
        }
    };
    private void DisplayFile(String filePhoneNumber){
        FileInputStream FIn=null;
        BufferedInputStream BuffIn=null;
        try {
            FIn=openFileInput(filePhoneNumber);
            BuffIn=new BufferedInputStream(FIn);
            byte BuffByte []=new byte[20];
            EResult.setText("");

            do{
                int Flag=BuffIn.read(BuffByte);
                if (Flag==-1){
                    break;
                }
                else {
                    EResult.append(new String(BuffByte),0,Flag);
                }
            }while (true);
            BuffIn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}