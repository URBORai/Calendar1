package com.example.mycalendar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class second extends AppCompatActivity {
    public int min,hour,year,month,day;
    private EditText tip,settime,wake,nationtime;
    private Button bt;
    private TimePicker tp;
    private RadioGroup RGc;
    private RadioButton RBst;
    private MediaPlayer mediaPlayer;
    private Calendar targetTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_issue);

        tp=findViewById(R.id.tp);
        tip=findViewById(R.id.editTextText);
        settime=findViewById(R.id.editTextText2);
        bt=findViewById(R.id.sure);
        RGc=findViewById(R.id.RadioGroup1);
        RBst=findViewById(R.id.radioButton3);
///sdcard/yt1s.com - Classic Alarm Clock  Sound Effect for Editing_320kbps.mp3
        RGc.setOnCheckedChangeListener(RGcListener);
        mediaPlayer=new MediaPlayer();
        String filePath= Environment.getExternalStorageDirectory().getPath()+
                "/sdcard/mu.mp3";
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepareAsync();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        tp.setIs24HourView(true);
        Intent it=this.getIntent();
        Bundle bd=it.getExtras();
        if (bd != null) {
            year = bd.getInt("ye");
            month = bd.getInt("mon");
            day = bd.getInt("day");
            settime.setText(year + "/" + month + "/" + day);
        } else {

        }

        tp.setOnTimeChangedListener(tplis);
        bt.setOnClickListener(btlis);
    }
    protected TimePicker.OnTimeChangedListener tplis=new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            min=minute;
            hour=hourOfDay;
            targetTime = Calendar.getInstance();
            targetTime.add(Calendar.DAY_OF_MONTH, day);   // 明天
            targetTime.set(Calendar.HOUR_OF_DAY, hour);   //時間
            targetTime.set(Calendar.MINUTE, min);
            targetTime.set(Calendar.SECOND, 0);
        }
    };
    private RadioGroup.OnCheckedChangeListener RGcListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
// 检查选中的 RadioButton


            if (checkedId == RBst.getId()) {
                // RBst 被选中，计算距离触发时间的差距
                long timeDifference = targetTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();

                // 在未来的时间触发音效
                if (timeDifference > 0) {
                    scheduleSoundEffect(timeDifference);
                }
            }/* else if (checkedId == RBcl.getId()) {

                showAlertDialog("提示", "已选择不提醒");
            }*/
        }
    };
    private void scheduleSoundEffect(long delayMillis) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                // 在这里触发音效
                playSound();

                // 弹出提醒对话框
                showAlertDialog("鬧鐘提醒","時間到了!");
            }
        }, delayMillis);  //—>void run
    }

    private void playSound() {
        // 检查音效是否正在播放，如果正在播放，停止并重新开始
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }


        mediaPlayer.start();

        // 监听音效播放完成事件
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {


                // 在音效播放完成后执行的操作，看有沒有要加什麼
            }
        });
    }

    private void showAlertDialog(String title, String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(second.this);
                alertDialogBuilder.setTitle(title);
                alertDialogBuilder.setMessage(message);
                alertDialogBuilder.setPositiveButton("确定", null);
                alertDialogBuilder.show();
            }
        });
    }
    protected View.OnClickListener btlis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    };
}
