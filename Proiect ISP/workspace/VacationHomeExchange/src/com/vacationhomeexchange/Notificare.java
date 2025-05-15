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
    private Utilizator destinatar;

    public Notificare(int id, String mesaj, Date data, TipNotificare tip, Utilizator destinatar) {
        if (mesaj == null || mesaj.trim().isEmpty()) {
            throw new IllegalArgumentException("Mesajul notificării nu poate fi gol.");
        }

        this.id = id;
        this.mesaj = mesaj;
        this.data = data;
        this.tip = tip;
        this.destinatar = destinatar;
    }

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

    public Utilizator getDestinatar() {
        return destinatar;
    }

    public void trimiteNotificare() {
        System.out.println("🔔 Notificare pentru " + destinatar.getNume() + ": [" + tip + "] " + mesaj);
    }

    public void programeazaNotificare(Date dataProgramata) {
        System.out.println("📅 Notificare programată pentru " + destinatar.getNume() + " la " + dataProgramata);
    }
}
