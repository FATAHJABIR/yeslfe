package com.example.yeslife;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CalendarView;


public class MainActivity extends AppCompatActivity {


    int jour;
    int mois;
    int année;
    CalendarView cv;
    private Button bt;
    public static Habitudes lesHabitudes = new Habitudes();
    ChoixHabitudesActivity objet;
    Button btStat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChoixHabitudesActivity obj = new ChoixHabitudesActivity();

        cv = (CalendarView) findViewById(R.id.calendarView);
        TextView tv = (TextView) findViewById(R.id.textView);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            //récupération de la date
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

                jour = dayOfMonth;
                mois = month + 1;
                année = year;
                Toast.makeText(getApplicationContext(), "Vous avez sélectionné la date : " + dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();


            }
        });
        bt = findViewById(R.id.buttonOK);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActiviteChoix();
            }
        });
        btStat=findViewById(R.id.buttonStats);
        btStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lien vers activité Statistique
                Intent it = new Intent(getApplicationContext(), Statistiques.class);
                it.setClass(getApplicationContext(), Statistiques.class);
                startActivity(it);

            }
        });
    }

    public void openActiviteChoix(){
        //lien vers activité choix habitudes
        Intent it = new Intent(getApplicationContext(), ChoixHabitudesActivity.class);
        it.setClass(this, ChoixHabitudesActivity.class);
        it.putExtra("Jour", Integer.toString(jour) );
        it.putExtra("Mois", Integer.toString(mois));
        it.putExtra("Année", Integer.toString(année));
        startActivity(it);
    }


}

