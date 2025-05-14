package com.vacationhomeexchange;

import java.util.ArrayList;

public class TestProprietar {
    public static void main(String[] args) {
        // Creează o locuință
        Locuinta locuinta1 = new Locuinta(1, "Apartament", "București, România", 150.0, "Apartament modern, aproape de centru.");
        Locuinta locuinta2 = new Locuinta(2, "Casă", "Cluj, România", 250.0, "Casă spațioasă, ideală pentru familii.");

        // Creează un proprietar cu lista de locuințe
        ArrayList<Locuinta> locuinte = new ArrayList<>();
        locuinte.add(locuinta1);
        locuinte.add(locuinta2);

        Proprietar proprietar = new Proprietar(1, "Mihai Ionescu", "mihai@example.com", "parola1", "proprietar", locuinte);

        // Testează getters
        System.out.println("Proprietar: " + proprietar.getNume());
        System.out.println("Email: " + proprietar.getEmail());
        System.out.println("Rol: " + proprietar.getRol());

        // Testează metodele
        proprietar.getListaLocuinte();
        proprietar.raspundeMesaj();
        proprietar.confirmaRezervare();
    }
}
