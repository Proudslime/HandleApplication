package com.example.handleapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button btn_start,btn_pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        btn_start = findViewById(R.id.start);
        btn_pause = findViewById(R.id.pause);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                textView.setText(msg.arg1+"");
            }
        };

        final Runnable myWorker = new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while(progress <= 100){
                    Message msg = new Message();
                    msg.arg1 = progress;
                    handler.sendMessage(msg);
                    progress += 10;

                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                Message msg = handler.obtainMessage();
                msg.arg1 = -1;
                handler.sendMessage(msg);
            }
        };

        final Thread workThread = new Thread(null,myWorker,"WorkThread");

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workThread.start();
            }
        });


    }
}