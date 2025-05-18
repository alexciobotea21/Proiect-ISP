package com.vacationhomeexchange;

import java.util.Date;

public class Rezervare {
    private int id;
    private Date dataInceput;
    private Date dataSfarsit;
    private Client utilizatorClient;
    private Locuinta locuinta;
    private boolean confirmata; // ✅ noua variabilă

    public Rezervare(int id, Date dataInceput, Date dataSfarsit, Client utilizatorClient, Locuinta locuinta) {
        this.id = id;
        this.dataInceput = dataInceput;
        this.dataSfarsit = dataSfarsit;
        this.utilizatorClient = utilizatorClient;
        this.locuinta = locuinta;
        this.confirmata = false; // implicit, rezervarea nu este confirmată
    }

    // Getters și Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataInceput() {
        return dataInceput;
    }

    public void setDataInceput(Date dataInceput) {
        this.dataInceput = dataInceput;
    }

    public Date getDataSfarsit() {
        return dataSfarsit;
    }

    public void setDataSfarsit(Date dataSfarsit) {
        this.dataSfarsit = dataSfarsit;
    }

    public Client getUtilizatorClient() {
        return utilizatorClient;
    }

    public void setUtilizatorClient(Client utilizatorClient) {
        this.utilizatorClient = utilizatorClient;
    }

    public Locuinta getLocuinta() {
        return locuinta;
    }

    public void setLocuinta(Locuinta locuinta) {
        this.locuinta = locuinta;
    }

    public boolean isConfirmata() {
    	return confirmata;
    }

    public void setConfirmata(boolean confirmata) {
    	this.confirmata = confirmata;
    }

    // Metode
    public void confirmareRezervare() {
        this.confirmata = true;
        System.out.println("Rezervarea a fost confirmată.");
    }

    public void anulareRezervare() {
        this.confirmata = false;
        System.out.println("Rezervarea a fost anulată.");
    }
    
    
}