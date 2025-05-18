package com.vacationhomeexchange;

import java.util.Date;

public class Mesaj {
    // Atribute
    private int id;
    private String emailExpeditor;
    private String emailDestinatar;
    private String continut;
    private Date data;

    // Constructor complet
    public Mesaj(int id, String emailExpeditor, String emailDestinatar, String continut, Date data) {
        this.id = id;
        this.emailExpeditor = emailExpeditor;
        this.emailDestinatar = emailDestinatar;
        this.continut = continut;
        this.data = data;
    }

    // Constructor fără ID
    public Mesaj(String emailExpeditor, String emailDestinatar, String continut, Date data) {
        this.emailExpeditor = emailExpeditor;
        this.emailDestinatar = emailDestinatar;
        this.continut = continut;
        this.data = data;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getEmailExpeditor() {
        return emailExpeditor;
    }

    public String getEmailDestinatar() {
        return emailDestinatar;
    }

    public String getContinut() {
        return continut;
    }

    public Date getData() {
        return data;
    }

    // Metode specifice Mesaj
    public void trimiteMesaj() {
        System.out.println("Mesajul a fost trimis de la " + emailExpeditor + " la " + emailDestinatar);
    }

    public void citesteMesaj() {
        System.out.println("Mesajul de la " + emailExpeditor + ": " + continut);
    }

    public void stergeMesaj() {
        System.out.println("Mesajul a fost șters.");
    }
}
