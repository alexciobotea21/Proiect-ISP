package com.vacationhomeexchange;

import java.util.Date;

public class TestUtilizator {
    public static void main(String[] args) {
        // Creăm un utilizator
        Utilizator utilizator = new Utilizator(1, "Alex Ciobotea", "alex@example.com", "parola123", "client");

        // Simulăm trimiterea unei notificări către utilizator
        Notificare notificare = new Notificare(
            1,
            "Rezervarea ta a fost confirmată!",
            new Date(),
            Notificare.TipNotificare.REZERVARE_CONFIRMATA,
            utilizator
        );

        // Adăugăm notificarea și o afișăm
        utilizator.adaugaNotificare(notificare);
        utilizator.afiseazaNotificari();

        // Testăm autentificarea (reușită)
        System.out.println("\n🔐 Test autentificare cu date corecte:");
        utilizator.autentificare();

        // Testăm înregistrarea (opțional, dacă vrem input manual)
        // utilizator.inregistrare();
    }
}
