package com.example.yeslife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Vector;

public class EditHabitudes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habitudes);



        Button btsuppr = findViewById(R.id.edit_boutonSuppr);

        Vector<Habitude> V= new Vector<>();

        btsuppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //récupération données depuis choixHabitudesActivity
                Intent recuperation = getIntent();
                String s = recuperation.getStringExtra("nomSwitch");
                Habitude h = MainActivity.lesHabitudes.getTheOneNamed(s);
                //retour vers ChoixHabitudesActivity
                Intent it = new Intent();
                it.setClass(getApplicationContext(), ChoixHabitudesActivity.class);
                startActivity(it);
            }
        });

        btsuppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recuperation = getIntent();
                String s = recuperation.getStringExtra("nomSwitch");
                Habitude h = MainActivity.lesHabitudes.getTheOneNamed(s);
                MainActivity.lesHabitudes.getHabIterator().remove(h);
                saveData();
                Intent it = new Intent();
                it.setClass(getApplicationContext(), ChoixHabitudesActivity.class);
                startActivity(it);
            }
        });
    }
    //enregistrement
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.lesHabitudes.getHabIterator().toArray());
        Log.d("new_list", Arrays.toString(MainActivity.lesHabitudes.getHabIterator().toArray()));
        Log.d("save_array", json);
        editor.putString("habitudes", json);
        editor.apply();
    }
}