package com.example.exlogin01;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText EID,EPW;
    private Button BOK,BReset;
    private String[] Login;
    private File FileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EID=findViewById(R.id.editTextTextID);
        EPW=findViewById(R.id.editTextTextPW);
        BOK=findViewById(R.id.buttonCheck);
        BReset=findViewById(R.id.buttonReDo);

        BOK.setOnClickListener(Listener);
        BReset.setOnClickListener(Listener);

        requestStoragePermissions();
    }
    private void requestStoragePermissions(){
        if (Build.VERSION.SDK_INT>=23){
            int hasPermission=checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasPermission!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                return;
            }
        }
        readFile();
    }
    //@Override
    public void onRequestPermissionnsResult(int requestCode,String[] permissions,int[] grantResults){
        if (requestCode==1){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                readFile();
            }
            else {
                Toast.makeText(this,"未取得權限!",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    private void readFile(){
        FileName=new File("sdcard/Login.txt");
        try {
            FileInputStream FIn=new FileInputStream(FileName);
            BufferedReader Reader=new BufferedReader(new InputStreamReader(FIn));
            String Line="",WholeData="";
            int i=0;
            while ((Line=Reader.readLine())!=null){
                WholeData=WholeData+Line+"\n";
                i++;
            }
            Login=WholeData.split("\n");
            Reader.close();
            FIn.close();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"error!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    private View.OnClickListener Listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonCheck:
                    if (EID.getText().toString().equals("")||
                    EPW.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"帳號密碼都要輸入!",Toast.LENGTH_LONG).show();
                        break;
                    }
                    boolean Flag=false;
                    for (int i=0;i<Login.length;i+=2){
                        if (EID.getText().toString().equals(Login[i])){
                            Flag=true;
                            if (EPW.getText().toString().equals(Login[i+1])){
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("登入")
                                        .setMessage("登入成功!\n歡迎使用本應用程式")
                                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"密碼不正確",Toast.LENGTH_LONG).show();
                                EPW.setText("");
                                break;
                            }
                        }
                    }
                    if (!Flag){
                        Toast.makeText(getApplicationContext(),"帳號不正確!",Toast.LENGTH_LONG).show();
                        EID.setText("");
                        EPW.setText("");
                    }
                    break;
                case R.id.buttonReDo:
                    EID.setText("");
                    EPW.setText("");
                    break;
            }
        }
    };
}