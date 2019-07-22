package com.example.queue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MainActivity extends AppCompatActivity {
    EditText edit;
    Button btn;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.edittest);
        btn = findViewById(R.id.button);
        text = findViewById(R.id.textview);

        final BlockingQueue<String> queue = new LinkedBlockingDeque<>(1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    queue.put(edit.getText().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        new Consumer(queue).start();

    }

    class Consumer extends Thread{
        BlockingQueue<String> queue;
        public Consumer(BlockingQueue<String> q) {
            queue = q;
        }

        @Override
        public void run() {
            while(true){
                Log.i("gejun","state = "+Thread.currentThread().getState());
                try {
                    String s = queue.take();
                    Log.i("gejun",""+s+", thread = " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
