package com.kevinas.mio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class tambah_barangg extends MainCore {
    String jenis = "IT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_barang);
        ((Button)findViewById(R.id.jenis)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenis = (jenis=="IT")?"ATK":"IT";
                ((Button)findViewById(R.id.jenis)).setText(jenis);
            }
        });
        ((Button)findViewById(R.id.tambahkan)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nama = findViewById(R.id.nama_barang);
                EditText jmlh = findViewById(R.id.jumlah_barang);
                tools.send(String.format("tambah_barang;%s;%s;%s", nama.getText(), jmlh.getText(), jenis=="IT"?"J_0":"J_1"));
                Intent a = new Intent(getApplicationContext(), crud_barang.class);
                startActivity(a);
            }
        });
    }


}
