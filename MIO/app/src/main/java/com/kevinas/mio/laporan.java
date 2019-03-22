package com.kevinas.mio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.regex.Pattern;

public class laporan extends MainCore {
    private ViewGroup root;
    private void listnya()
    {
        String pinjaman_barangs[] = tools.splitstr(tools.send("run;select * from pinjaman_barang"), ';');
        if (pinjaman_barangs.length==0)
        {
            return;
        }
        ViewGroup rownya;
        root.removeAllViews();
        for (String i : pinjaman_barangs)
        {
            if (i==""| Pattern.matches("^[\\s]?$", i)){continue;}
            rownya = (ViewGroup) (getLayoutInflater().inflate(R.layout.row_barang1, root, false));

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        root = (ViewGroup) findViewById(R.id.laporan_content);
        listnya();
    }
}
