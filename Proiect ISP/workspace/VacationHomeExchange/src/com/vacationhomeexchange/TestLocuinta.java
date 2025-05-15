package com.vacationhomeexchange;

public class TestLocuinta {
    public static void main(String[] args) {
        // Creează o locuință
        Locuinta locuinta = new Locuinta(1, "Apartament", "București, România", 150.0, "Apartament cu 2 camere, situat în centrul orașului.");

        // Testează getters
        System.out.println("Locuință ID: " + locuinta.getId());
        System.out.println("Tip: " + locuinta.getTip());
        System.out.println("Locație: " + locuinta.getLocatie());
        System.out.println("Preț: " + locuinta.getPret());
        System.out.println("Descriere: " + locuinta.getDescriere());

        // Testează metodele
        locuinta.adaugaLocuinta();
        locuinta.editeazaLocuinta();
        locuinta.stergeLocuinta();
    }
}