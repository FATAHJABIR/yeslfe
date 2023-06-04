package com.example.yeslife;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Vector;
import java.time.LocalDate;



public class ChoixHabitudesActivity extends AppCompatActivity {
    String jour;
    String mois;
    String annee;
    String nomNouvelleHabitude;
    String descriptionNouvelleHabitude;

    EditText ethabitudeRealisee;
    int nombreHabitudes;
    Vector<Switch> VSwitch = new Vector<Switch>();
    Vector<Habitude> Vhab = new Vector<Habitude>();


    LinearLayout layout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_habitudes);
        loadData();


        FloatingActionButton fab = new FloatingActionButton(this);
        fab = findViewById(R.id.floatingActionButton2);

        //Listener du bouton plus
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(getApplicationContext(), CreationNouvelleHabitude.class);
                startActivity(it);
            }
        });


        TextView tv = findViewById(R.id.choixHabitude);
        layout = findViewById(R.id.monLayout3);
        String date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = String.valueOf(LocalDate.now());
        }
        String curDate[] = date.split("-");

        //Bouton OK
        Button btok = findViewById(R.id.button);
        String finalDate = date;
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enregistrement de la nouvelle habitude
                String finalDate1 = jour + "/" + mois + "/" + annee;
                Toast.makeText(getApplicationContext(), "Enregistrement", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < VSwitch.size(); i++) {
                    if (VSwitch.get(i).isChecked()) {
                        MainActivity.lesHabitudes.getTheOneNamed(VSwitch.get(i).getText().toString()).addDate(finalDate1);
                        saveData();
                    }
                }
                //retour vers mainActivity
                Intent it = new Intent();
                it.setClass(getApplicationContext(), MainActivity.class);
                startActivity(it);
            }
        });

    //Récupération de la date
        Intent recuperation = getIntent();
        jour = recuperation.getStringExtra("Jour");
        mois = recuperation.getStringExtra("Mois");
        annee = recuperation.getStringExtra("Année");
        //on choisit la date du jour par défaut si l'utilisateur n'en a pas sélectionné
        if ((jour != null) && (mois != null) && (annee != null)) {
            if ((jour.equals("0")) && (mois.equals("0")) && (annee.equals("0"))) {
                jour = curDate[2];
                mois = curDate[1];
                annee = curDate[0];
            }
        } else {//PB
            jour = curDate[2];
            mois = curDate[1];
            annee = curDate[0];
        }

        finalDate = jour + "/" + mois + "/" + annee;

        //Texte du haut
        tv.setText("Habitudes pour la date du " + finalDate);

        //Recuperation nouvelle habitude
        nomNouvelleHabitude = recuperation.getStringExtra("NomNouvelleHabitude");
        descriptionNouvelleHabitude = recuperation.getStringExtra("DescriptionNouvelleHabitude");

        //Création des switch
        Vhab = MainActivity.lesHabitudes.getHabIterator();
        nombreHabitudes = Vhab.size();
        for (int i = 0; i < nombreHabitudes; i++) {
            Switch s = new Switch(this);
            s.setText(Vhab.get(i).getNom());
            layout.addView(s);
            VSwitch.add(s);
            //Ceux qui dont la date du moment est dans leur fichier date on les coche
            if(Vcontient(MainActivity.lesHabitudes.getTheOneNamed(s.getText().toString()).getDates(),finalDate) == true){//check la valeur plutôt
                s.setChecked(true);

            }

            s.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                //Lien vers activité EditHabitudes
                public boolean onLongClick(View view) {
                    Intent it = new Intent();
                    it.setClass(getApplicationContext(), EditHabitudes.class);
                    it.putExtra("nomSwitch", s.getText() );
                    startActivity(it);
                    return false;
                }
            });
        }



    }

    //test si une chaine de caractère s est bien présente dans le vecteur V
    public boolean Vcontient(Vector<String> V, String s){
        for(int i = 0; i < V.size(); i++){
            if(V.get(i).equals(s)){
                return true;
            }
        }
        return false;
    }
    //processus de mémoire
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

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("habitudes", null);
        Type type = new TypeToken<ArrayList<Habitude>>() {
        }.getType();
        ArrayList<Habitude> list = gson.fromJson(json, type);
        if (list != null) {
            Vector<Habitude> newLesHabitudes = new Vector<>(list);
            MainActivity.lesHabitudes.setLesHabitudes(newLesHabitudes);
        }
        else{
            MainActivity.lesHabitudes = new Habitudes();


        }

    }
}