package com.example.yeslife;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;

import java.util.Arrays;
import java.util.Vector;

public class CreationNouvelleHabitude extends AppCompatActivity {
    EditText etNomNouvelleHabitude;
    EditText etDescriptionNouvelleHabitude;
    Switch switchComptable;
    EditText etObjectif;
    Button bt;


    public Habitudes lesHabitudes;


    //Attributs Habitude à renvoyer
    String nom, desc, obj;
    Vector<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_des_habitudes);

        etNomNouvelleHabitude = findViewById(R.id.nomNouvelleHabitude);
        etDescriptionNouvelleHabitude = findViewById(R.id.descriptionNouvelleHabitude);
        etObjectif = findViewById(R.id.objectif);
        switchComptable = (Switch) findViewById(R.id.switchComptable);
        bt = (Button) findViewById(R.id.boutonOKnouvellehabitude);


        findViewById(R.id.objectif).setVisibility(View.INVISIBLE);

        //réglage vivibilité editText objectif

        switchComptable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!switchComptable.isChecked()) {
                    findViewById(R.id.objectif).setVisibility(View.INVISIBLE);
                } else
                    findViewById(R.id.objectif).setVisibility(View.VISIBLE);
            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //récupération des entrées de l'utilisateur
                lesHabitudes = MainActivity.lesHabitudes;
                nom = etNomNouvelleHabitude.getText().toString();
                desc = etDescriptionNouvelleHabitude.getText().toString();
                obj = etObjectif.getText().toString();
                dates = new Vector<String>();

                Vector<String> allNames = new Vector<>();
            //vérification que l'habitude n'est pas déjà existante
                for(int i = 0; i < MainActivity.lesHabitudes.getHabIterator().size(); i++){
                    allNames.add(MainActivity.lesHabitudes.getHabIterator().get(i).getNom());
                }
                if (!allNames.contains(nom)) {
                    MainActivity.lesHabitudes.add(new Habitude(nom, desc, dates));
                }
                else
                    Toast.makeText(getApplicationContext(), "Vous avez déjà créé cette habitude", Toast.LENGTH_SHORT).show();


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