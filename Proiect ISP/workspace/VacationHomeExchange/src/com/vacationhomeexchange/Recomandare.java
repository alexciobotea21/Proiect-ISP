package com.vacationhomeexchange;

public class Recomandare {
    // Atribute
    private int id;
    private Client client;
    private Locuinta locuintaRecomandata;
    private String motiv;

    // Constructor
    public Recomandare(int id, Client client, Locuinta locuintaRecomandata, String motiv) {
        this.id = id;
        this.client = client;
        this.locuintaRecomandata = locuintaRecomandata;
        this.motiv = motiv;
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

    public Locuinta getLocuintaRecomandata() {
        return locuintaRecomandata;
    }

    public void setLocuintaRecomandata(Locuinta locuintaRecomandata) {
        this.locuintaRecomandata = locuintaRecomandata;
    }

    public String getMotiv() {
        return motiv;
    }

    public void setMotiv(String motiv) {
        this.motiv = motiv;
    }

    // Metode specifice Recomandare
    public void genereazaRecomandare() {
        System.out.println("Recomandare generată: " + client.getNume() + " recomandă locuința " + locuintaRecomandata.getTip() + " din " + locuintaRecomandata.getLocatie() + " pentru motivul: " + motiv);
    }
}
