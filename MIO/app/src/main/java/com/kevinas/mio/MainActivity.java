package com.kevinas.mio;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends MainCore {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        tools = new Tools(context);
        boolean xx = true;
        if (this.myid=="")
        {

            String login = tools.readFile("login");
            if (login==""){xx=false;}
            else if (login=="Error ReadFile"){xx=false;}
            else {
                String recv = tools.send("login;" + login);
                if (recv.charAt(0) == 'U') {
                    this.myid = recv;
                } else {
                    xx = false;
                }
            }
            if (!xx) {
                Intent a = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(a);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((RelativeLayout)findViewById(R.id.tombol0)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), crud_barang.class);
                startActivity(a);
            }
        });
        ((RelativeLayout)findViewById(R.id.tombol1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), inventariskan_barang.class);
                startActivity(a);
            }
        });
        ((RelativeLayout)findViewById(R.id.tombol2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), pilih_user_pengembalian.class);
                startActivity(a);
            }
        });
        ((RelativeLayout)findViewById(R.id.tombol3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tools.writeFilee("laporan.xls", tools.send("aalaporan", 10000000));
                Snackbar.make(findViewById(R.id.tombol3), "tersimpan di /data/data/laporan.xls", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}