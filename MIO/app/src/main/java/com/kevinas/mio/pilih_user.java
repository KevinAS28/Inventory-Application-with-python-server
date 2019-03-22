package com.kevinas.mio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pilih_user extends MainCore {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_user);
        ViewGroup root = (ViewGroup) findViewById(R.id.pilih_user_content);
        root.removeAllViews();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String allusers = tools.send("run;select * from seluruh_user where id_type='JU_0'");
        String alltype = tools.send("run;select * from tipe_akun");
        String dat0[] = tools.splitstr(allusers, ';');
        String dat1[] = tools.splitstr(alltype, ';');
        HashMap<String, String> alltype1 = new HashMap<>();
        for (String d: dat1) {
            if (d==""| Pattern.matches("^[\\s]?$", d))
            {
                continue;
            }
            String dat[] = tools.splitstr(d, ',');
            alltype1.put(dat[0], dat[1]);
        }
        ViewGroup baris;

        for (int z =0; z < dat0.length;z++)
        {
            String i = dat0[z];
            if (i==""| Pattern.matches("^[\\s]?$", i))
            {
                continue;
            }
            baris = (ViewGroup) (getLayoutInflater().inflate(R.layout.row_user, root, false));
            TextView id = (TextView)baris.findViewById(R.id.id_user);
            TextView tipe = (TextView)baris.findViewById(R.id.tipe_user);
            TextView nama = (TextView)baris.findViewById(R.id.nama_user);
            TextView alamat = (TextView)baris.findViewById(R.id.alamat_user);
            TextView telp = (TextView)baris.findViewById(R.id.telp_user);
            final String a[] = tools.splitstr(i, ',');
            nama.setText(a[2]);

            id.setText(a[0]);
            tipe.setText(alltype1.get(a[1]));

            alamat.setText(a[3]);
            telp.setText(a[4]);

            baris.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> barangs = getIntent().getStringArrayListExtra("barangnya");
                    ArrayList<String> counts = getIntent().getStringArrayListExtra("jumlahnya");
                    String id_peminjam = a[0];
                    String id_petugas = myid;
                    String tosend = String.format("inventariskan;%s|%s|;", id_peminjam, id_petugas);
                    for (int z = barangs.size()-1; z > -1; z--)
                    {
                        tosend+=barangs.get(z)+"|"+counts.get(z)+"|;";
                    }
                    tools.send(tosend);
                    Intent k = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(k);
                }
            });
            root.addView(baris);
        }
    }
}