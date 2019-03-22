package com.kevinas.mio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.HashMap;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.Pattern;
public class inventariskan_barang extends MainCore {
    private ViewGroup root;
    private ImageButton tambah;
    private boolean next = false;
    private ArrayList<ViewGroup> allrows = new ArrayList<ViewGroup>();
    private void listnya()
    {

        String barangs[] = tools.splitstr(tools.send("run;select * from barang"), ';');
        if (barangs.length==0)
        {
            return;
        }
        ViewGroup rownya;
        root.removeAllViews();

        for (String i : barangs)
        {
            if (i==""|Pattern.matches("^[\\s]?$", i)){continue;}
            rownya = (ViewGroup) (getLayoutInflater().inflate(R.layout.row_barang1, root, false));
            allrows.add(rownya);
            final TextView id = rownya.findViewById(R.id.id_barang);
            final TextView nama = rownya.findViewById(R.id.nama_barang);
            final EditText jumlah = rownya.findViewById(R.id.jumlah_barang);
            final String a[] = tools.splitstr(i, ',');
            CheckBox terpilih = rownya.findViewById(R.id.terpilih);
            terpilih.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!next)
                    {
                        next = true;
                        tambah.setEnabled(true);
                        tambah.setAlpha((float)1);
                    }
                }
            });
            if (a.length==1){continue;}
            id.setText(a[0]);
            nama.setText(a[1]);
            jumlah.setText(a[2]);
            root.addView(rownya);
        }
    }

    HashMap<String, String> list = new HashMap<>();
    ArrayList<String> barangnya = new ArrayList<>(), jumlah= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventariskan_barang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        root = (ViewGroup) findViewById(R.id.crud_barang_content);
        tambah = findViewById(R.id.inventariskan);
        tambah.setEnabled(false);
        tambah.setAlpha((float)0);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (ViewGroup x:allrows)
                {
                    if (((CheckBox)x.findViewById(R.id.terpilih)).isChecked())
                    {
                        barangnya.add (String.valueOf( (((TextView)x.findViewById(R.id.id_barang)).getText()))) ;
                        jumlah.add(String.valueOf( (((TextView)x.findViewById(R.id.jumlah_barang)).getText())));
                    }
                }
                Intent a = new Intent(getApplicationContext(), pilih_user.class);
                a.putStringArrayListExtra("barangnya", barangnya);
                a.putStringArrayListExtra("jumlahnya", jumlah);
                startActivity(a);
            }
        });
        listnya();
    }
    @Override
    public void onBackPressed()
    {
        barangnya.clear();
        jumlah.clear();
        super.onBackPressed();
    }
}
