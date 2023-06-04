package com.example.yeslife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;


public class Statistiques extends AppCompatActivity {
    LinearLayout monLayout;
    Vector<Habitude> Vhab = new Vector<Habitude>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiques);
        monLayout=findViewById(R.id.monLayout);
        Vhab = MainActivity.lesHabitudes.getHabIterator();
    //création de textviews correspondant chacun à une activité
        for (int i = 0; i < Vhab.size(); i++) {
            TextView tv= new TextView(this);
            tv.setText(Vhab.get(i).getNom().toString() + ":" +Vhab.get(i).getDates() );
            monLayout.addView(tv);
        }
    }
}