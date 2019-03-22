package com.kevinas.mio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class pengembalian extends MainCore {
    ViewGroup root;
    ArrayList<ViewGroup> data = new ArrayList<ViewGroup>();
    ArrayList<String> pinjam_key = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengembalian);
        root = findViewById(R.id.pinjaman_content);
        String peminjam = getIntent().getStringExtra("peminjam");

        String pinjaman[] = tools.splitstr(tools.send(String.format("run;select * from inventaris where id_peminjam='%s'", peminjam)), ';');

        ViewGroup rownya;

        //hitung barang yang blm dikembalikan
        HashMap<String, HashMap<String, ArrayList<String>>> inv_ib  = new HashMap<String, HashMap<String, ArrayList<String>>>();

        for (String i : pinjaman)
        {
            if (Pattern.matches("^[\\s]?$", i)|i=="")
            {
                continue;
            }
            String inventaris[] = tools.splitstr(i, ',');
            ArrayList<String> toadd = new ArrayList<>();
            String barang_terpinjam[] = tools.splitstr(tools.send(String.format("run;select * from pinjaman_barang where id_inventaris='%s'", inventaris[0])), ';');

            for (String a : barang_terpinjam)
            {
                if (Pattern.matches("^[\\s]?$", a))
                {
                    continue;
                }
                rownya = (ViewGroup) (getLayoutInflater().inflate(R.layout.row_pengembalian, root, false));
                String detail_pinjam[] = tools.splitstr(a, ',');
                if (detail_pinjam.length<=1){continue;}
                String q = tools.send(String.format("run;select * from barang where id_barang='%s'", detail_pinjam[2]));
                String pegawai = tools.splitstr(tools.splitstr(tools.send(String.format("run;select * from seluruh_user where id='%s'", inventaris[3])), ';')[0], ',')[2];
                Log.d("hasil", q);
                String barang[] = tools.splitstr(tools.splitstr(q, ';')[0], ',');
                ((TextView)rownya.findViewById(R.id.id_barang)).setText(detail_pinjam[2]);
                ((TextView)rownya.findViewById(R.id.nama_barang)).setText(barang[1]);
                ((TextView)rownya.findViewById(R.id.jumlah_barang)).setText(detail_pinjam[3]);
                String tgl_petugas = tools.splitstr(inventaris[1], '.')[0] + " - " + tools.splitstr(pegawai, ' ')[0];
                ((TextView)rownya.findViewById(R.id.tgl_petugas)).setText(tgl_petugas);
                root.addView(rownya);
                data.add(rownya);
                pinjam_key.add(detail_pinjam[0]);
            }
        }
    }
    @Override
    public void onBackPressed()
    {
            for (int a = 0; a < data.size(); a++)
            {
                ViewGroup x = data.get(a);
                String stok = ((EditText)x.findViewById(R.id.jumlah_barang)).getText().toString();
                String id = ((TextView)x.findViewById(R.id.id_barang)).getText().toString();
                tools.send(String.format("pengembalian;%s;%s;%s;%s", pinjam_key.get(a), getIntent().getStringExtra("peminjam"), stok, id));
            }
        super.onBackPressed();
    }
}
