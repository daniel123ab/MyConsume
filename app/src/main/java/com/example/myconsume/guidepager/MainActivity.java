package com.example.myconsume.guidepager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myconsume.R;

public class MainActivity extends AppCompatActivity {
    private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity);

        TitanicTextView tv = (TitanicTextView) findViewById(R.id.my_text_view);

        // set fancy typeface
        tv.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));

        // start animation
        new Titanic().start(tv);
        thread=new Thread(new Runnable() {
            public volatile boolean exit=false;
            @Override
            public void run() {
                try {
                    Thread.sleep(5100);
                    skip();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    public void doClick(View v){
        thread.interrupt();
        skip();
    }

    private void skip(){
        startActivity(new Intent(MainActivity.this, com.example.myconsume.activity.login.MainActivity.class));
        finish();
    }
}
