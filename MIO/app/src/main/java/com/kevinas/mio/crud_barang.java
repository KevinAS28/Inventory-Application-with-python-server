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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.Pattern;
public class crud_barang extends MainCore {
    private ViewGroup root;
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
            rownya = (ViewGroup) (getLayoutInflater().inflate(R.layout.row_barang, root, false));
            allrows.add(rownya);
            final TextView id = rownya.findViewById(R.id.id_barang);
            final TextView nama = rownya.findViewById(R.id.nama_barang);
            final EditText jumlah = rownya.findViewById(R.id.jumlah_barang);
            jumlah.setOnEditorActionListener(
                    new EditText.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                    actionId == EditorInfo.IME_ACTION_DONE ||
                                    event != null &&
                                            event.getAction() == KeyEvent.ACTION_DOWN &&
                                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                                if (event == null || !event.isShiftPressed()) {
                                    // the user is done typing.
                                    return true; // consume.
                                }
                            }
                            return false; // pass on to other listeners.
                        }
                    }
            );
            ImageButton x = rownya.findViewById(R.id.hapus);
            final String a[] = tools.splitstr(i, ',');
            x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tools.send("run;delete from barang where id_barang='"+a[0]+"'");
                    listnya();
                }
            });
            if (a.length==1){continue;}
            id.setText(a[0]);
            nama.setText(a[1]);
            jumlah.setText(a[2]);
            root.addView(rownya);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_barang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        root = (ViewGroup) findViewById(R.id.crud_barang_content);
        ImageButton tambah = findViewById(R.id.tambah_barang);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), tambah_barangg.class);
                startActivity(a);
            }
        });
        listnya();
    }
    @Override
    public void onBackPressed()
    {
        for (ViewGroup x:allrows)
        {
            String stok = ((EditText)x.findViewById(R.id.jumlah_barang)).getText().toString();
            String id = ((TextView)x.findViewById(R.id.id_barang)).getText().toString();
            tools.send(String.format("run;update barang set stok='%s' where id_barang='%s';", stok, id));
        }
        Intent a = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(a);
    }
}