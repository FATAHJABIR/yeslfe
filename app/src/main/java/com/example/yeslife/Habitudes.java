package com.example.yeslife;

import java.io.Serializable;
import java.util.Vector;

public class Habitudes implements Serializable {
    // liste des habitudes
    private Vector<Habitude> lesHabitudes;

    /**
     * Crée une nouvelle liste comportant 1 habitude
     */
    public Habitudes() {
        lesHabitudes = new Vector<Habitude>();

        lesHabitudes.add(new Habitude("Fitness", "Activité où on fait du sport", new Vector<String>()));
        lesHabitudes.add(new Habitude("Hydratation", "1L par jour au moins", new Vector<String>()));


    }

    /**
     * Fournit un itérateur sur les questions du quiz
     *
     * @return itérateur sur les questions du quiz
     */
    public Vector<Habitude> getHabIterator() {
        return lesHabitudes;
    }

    public void add(Habitude h) {
        lesHabitudes.add(h);
    }

    public void setLesHabitudes(Vector<Habitude> list) {
        lesHabitudes = list;
    }

    //retourn l'habitude dont le nom est text
    public Habitude getTheOneNamed(String text) {
        Vector<Habitude> V =MainActivity.lesHabitudes.getHabIterator();
        for (int i = 0; i < MainActivity.lesHabitudes.getHabIterator().size(); i++) {
            if (V.get(i).getNom().toString().equals(text)) {
                return V.get(i);
            }
        }
        return null;
    }
}