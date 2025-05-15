package com.vacationhomeexchange;

import java.util.Date;

public class TestRezervare {
    public static void main(String[] args) {
        // Creează un client și o locuință
        Client client = new Client(1, "Ion Popescu", "ion@example.com", "parola1", "client", null);
        Locuinta locuinta = new Locuinta(1, "Apartament", "București, România", 150.0, "Apartament modern");

        // Creează o rezervare
        Rezervare rezervare = new Rezervare(1, new Date(), new Date(), client, locuinta);

        // Testează metodele
        rezervare.confirmareRezervare();
        rezervare.anulareRezervare();
        
        // Afișează detalii despre rezervare
        System.out.println("Detalii rezervare:");
        System.out.println("Locuință: " + locuinta.getTip() + " în " + locuinta.getLocatie());
        System.out.println("Client: " + client.getNume());
    }
}