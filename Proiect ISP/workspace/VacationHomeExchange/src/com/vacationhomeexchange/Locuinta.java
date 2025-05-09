package com.vacationhomeexchange;

public class Locuinta {
    // Atribute
    private int id;
    private String tip;
    private String locatie;
    private double pret;
    private String descriere;

    // Constructor
    public Locuinta(int id, String tip, String locatie, double pret, String descriere) {
        this.id = id;
        this.tip = tip;
        this.locatie = locatie;
        this.pret = pret;
        this.descriere = descriere;
    }

    // Getters și Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    // Metode specifice Locuință
    public void adaugaLocuinta() {
        System.out.println("Locuința a fost adăugată.");
    }

    public void editeazaLocuinta() {
        System.out.println("Locuința a fost editată.");
    }

    public void stergeLocuinta() {
        System.out.println("Locuința a fost ștearsă.");
    }
}
