package com.vacationhomeexchange;

import java.util.Date;

public class Mesaj {
    // Atribute
    private int id;
    private Utilizator expeditor;
    private Utilizator destinatar;
    private String continut;
    private Date data;

    // Constructor
    public Mesaj(int id, Utilizator expeditor, Utilizator destinatar, String continut, Date data) {
        this.id = id;
        this.expeditor = expeditor;
        this.destinatar = destinatar;
        this.continut = continut;
        this.data = data;
    }

    // Getters și Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilizator getExpeditor() {
        return expeditor;
    }

    public void setExpeditor(Utilizator expeditor) {
        this.expeditor = expeditor;
    }

    public Utilizator getDestinatar() {
        return destinatar;
    }

    public void setDestinatar(Utilizator destinatar) {
        this.destinatar = destinatar;
    }

    public String getContinut() {
        return continut;
    }

    public void setContinut(String continut) {
        this.continut = continut;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    // Metode specifice Mesaj
    public void trimiteMesaj() {
        System.out.println("Mesajul a fost trimis de la " + expeditor.getNume() + " la " + destinatar.getNume());
    }

    public void citesteMesaj() {
        System.out.println("Mesajul citit de " + destinatar.getNume() + ": " + continut);
    }

    public void stergeMesaj() {
        System.out.println("Mesajul a fost șters.");
    }
}