package com.vacationhomeexchange;

import java.util.Date;

public class Notificare {

    public enum TipNotificare {
        INFORMATIVA,
        REZERVARE_CONFIRMATA,
        MESAJ_NOU,
        ALERTA_IMPORTANTA
    }

    private int id;
    private String mesaj;
    private Date data;
    private TipNotificare tip;
    private String emailDestinatar;

    // Constructor complet
    public Notificare(int id, String mesaj, Date data, TipNotificare tip, String emailDestinatar) {
        if (mesaj == null || mesaj.trim().isEmpty()) {
            throw new IllegalArgumentException("Mesajul notificării nu poate fi gol.");
        }

        this.id = id;
        this.mesaj = mesaj;
        this.data = data;
        this.tip = tip;
        this.emailDestinatar = emailDestinatar;
    }

    // Constructor fără ID
    public Notificare(String mesaj, Date data, TipNotificare tip, String emailDestinatar) {
        if (mesaj == null || mesaj.trim().isEmpty()) {
            throw new IllegalArgumentException("Mesajul notificării nu poate fi gol.");
        }

        this.mesaj = mesaj;
        this.data = data;
        this.tip = tip;
        this.emailDestinatar = emailDestinatar;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getMesaj() {
        return mesaj;
    }

    public Date getData() {
        return data;
    }

    public TipNotificare getTip() {
        return tip;
    }

    public String getEmailDestinatar() {
        return emailDestinatar;
    }

    // Metode specifice
    public void trimiteNotificare() {
        System.out.println("🔔 Notificare pentru " + emailDestinatar + ": [" + tip + "] " + mesaj);
    }

    public void programeazaNotificare(Date dataProgramata) {
        System.out.println("📅 Notificare programată pentru " + emailDestinatar + " la " + dataProgramata);
    }
}
