package com.vacationhomeexchange;

public class TestEvaluare {
    public static void main(String[] args) {
        // Creează un client și o locuință
        Client client = new Client(1, "Ion Popescu", "ion@example.com", "parola1", "client", null);
        Locuinta locuinta = new Locuinta(1, "Apartament", "București, România", 150.0, "Apartament modern");

        // Creează o evaluare
        Evaluare evaluare = new Evaluare(1, client, locuinta, 5, "Locuința a fost excelentă, recomand cu încredere!");

        // Testează metodele
        evaluare.adaugaEvaluare();
        evaluare.vizualizeazaEvaluare();
    }
}