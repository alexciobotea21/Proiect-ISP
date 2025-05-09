package com.vacationhomeexchange;

public class Evaluare {
    // Atribute
    private int id;
    private Client client;
    private Locuinta locuinta;
    private int scor;  // Scor de la 1 la 5
    private String comentariu;

    // Constructor
    public Evaluare(int id, Client client, Locuinta locuinta, int scor, String comentariu) {
        this.id = id;
        this.client = client;
        this.locuinta = locuinta;
        this.scor = scor;
        this.comentariu = comentariu;
    }

    // Getters și Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Locuinta getLocuinta() {
        return locuinta;
    }

    public void setLocuinta(Locuinta locuinta) {
        this.locuinta = locuinta;
    }

    public int getScor() {
        return scor;
    }

    public void setScor(int scor) {
        this.scor = scor;
    }

    public String getComentariu() {
        return comentariu;
    }

    public void setComentariu(String comentariu) {
        this.comentariu = comentariu;
    }

    // Metode specifice Evaluare
    public void adaugaEvaluare() {
        System.out.println("Evaluarea pentru locuință a fost adăugată.");
    }

    public void vizualizeazaEvaluare() {
        System.out.println("Evaluare: " + scor + " stele - " + comentariu);
    }
}
