package com.example.plkvoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Launcher extends AppCompatActivity {

    protected boolean _active = true;
    protected int _splashTime = 1700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waitTime = 0;
                    while (_active && (waitTime < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waitTime += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {
                    startActivity(new Intent(Launcher.this, LoginActivity.class));
                    finish();
                }
            };
        };
        splashTread.start();

    }
}
