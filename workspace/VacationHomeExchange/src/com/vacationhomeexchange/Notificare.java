package com.vacationhomeexchange;

import java.util.Date;

public class Notificare {
    // Atribute
    private int id;
    private String mesaj;
    private Date data;
    private String tipNotificare;

    // Constructor
    public Notificare(int id, String mesaj, Date data, String tipNotificare) {
        this.id = id;
        this.mesaj = mesaj;
        this.data = data;
        this.tipNotificare = tipNotificare;
    }

    // Getters și Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipNotificare() {
        return tipNotificare;
    }

    public void setTipNotificare(String tipNotificare) {
        this.tipNotificare = tipNotificare;
    }

    // Metoda pentru trimiterea notificării
    public void trimiteNotificare() {
        System.out.println("Notificare trimisă: " + mesaj + " Tip: " + tipNotificare);
    }
}
