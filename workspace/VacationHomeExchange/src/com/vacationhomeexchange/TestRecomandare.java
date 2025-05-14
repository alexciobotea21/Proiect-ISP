package com.vacationhomeexchange;

public class TestRecomandare {
    public static void main(String[] args) {
        // Creează un client și o locuință
        Client client = new Client(1, "Ion Popescu", "ion@example.com", "parola1", "client", null);
        Locuinta locuinta = new Locuinta(1, "Apartament", "București, România", 150.0, "Apartament modern și confortabil.");

        // Creează o recomandare
        Recomandare recomandare = new Recomandare(1, client, locuinta, "Locuința este aproape de centru și are facilități excelente.");

        // Testează metoda de generare recomandare
        recomandare.genereazaRecomandare();
    }
}
