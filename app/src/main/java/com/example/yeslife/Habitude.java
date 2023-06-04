package com.example.yeslife;

import java.io.Serializable;
import java.util.Vector;

public class Habitude implements Serializable {
    private String nom;

    private String description;

    private boolean Comptable;

    private int objectif;

    private Vector<String> dates;

    public Habitude(String nom, String description, Vector <String> dates){
        this.nom = nom;
        this.description = description;
        this.dates = dates;
    }

    public String getNom(){
        return nom;
    }

    public String getDescription(){
        return description;
    }
    public Vector<String> getDates(){
        return dates;
    }

    public void addDate(String finalDate){
         dates.add(finalDate);
    }
}
