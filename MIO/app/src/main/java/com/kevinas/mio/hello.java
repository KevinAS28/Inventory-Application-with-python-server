package com.kevinas.mio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class hello extends MainCore {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        TextView xx = findViewById(R.id.textnya);
        String temp = "";
        ArrayList<String> x = getIntent().getStringArrayListExtra("barangnya");
        ArrayList<String> y = getIntent().getStringArrayListExtra("jumlahnya");
        temp+=x.get(0) + " " + y.get(0);
        xx.setText(temp);
    }
}
