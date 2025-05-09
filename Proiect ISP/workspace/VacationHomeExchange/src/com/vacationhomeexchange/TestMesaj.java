package com.vacationhomeexchange;

import java.util.Date;

public class TestMesaj {
    public static void main(String[] args) {
        // Creează un utilizator valid
        Utilizator expeditor = new Utilizator(1, "Ion Popescu", "ion@example.com", "parola123", "client");
        Utilizator destinatar = new Utilizator(2, "Mihai Ionescu", "mihai@example.com", "parola456", "proprietar");

        // Creează un mesaj
        Mesaj mesaj = new Mesaj(1, expeditor, destinatar, "Bună, am o întrebare despre locuință.", new Date());

        // Testează metodele
        mesaj.trimiteMesaj();
        mesaj.citesteMesaj();
        mesaj.stergeMesaj();
    }
}
