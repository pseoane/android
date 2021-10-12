package com.example.cameramaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import com.example.cameramaps.client.repository.GardensRepository;
import com.example.cameramaps.client.model.Gardens;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getGardens();
    }

    private void getGardens() {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                int statusCode = msg.getData().getInt("statusCode");
                Gardens gardens = (Gardens) msg.obj;
                return;
            }
        };
        new GardensRepository().executeRequest(handler);
    }
}