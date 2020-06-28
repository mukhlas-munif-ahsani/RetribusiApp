package com.munifahsan.retribusiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.munifahsan.retribusiapp.MainPedagang.MainPedagang;
import com.munifahsan.retribusiapp.MainPetugas.MainPetugas;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendToPedagang(){
        Intent intent = new Intent(this, MainPedagang.class);
        startActivity(intent);
        finish();
    }

    public void sendToPetugas(){
        Intent intent = new Intent(this, MainPetugas.class);
        startActivity(intent);
        finish();
    }
}
