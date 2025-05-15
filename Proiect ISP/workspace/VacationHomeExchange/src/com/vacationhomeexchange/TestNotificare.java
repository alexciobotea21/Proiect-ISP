package com.vacationhomeexchange;

import java.util.Date;

public class TestNotificare {
    public static void main(String[] args) {
        // Creăm un destinatar
        Utilizator destinatar = new Utilizator(2, "Maria Ionescu", "maria@example.com", "secure456", "proprietar");

        // Creăm o notificare informativă
        Notificare notificare = new Notificare(
            2,
            "Ai un nou mesaj de la un client.",
            new Date(),
            Notificare.TipNotificare.MESAJ_NOU,
            destinatar
        );

        // Trimitem notificarea
        notificare.trimiteNotificare();

        // Programăm o notificare viitoare
        Date dataViitoare = new Date(System.currentTimeMillis() + 86400000); // +1 zi
        notificare.programeazaNotificare(dataViitoare);

        // Adăugăm notificarea în lista utilizatorului
        destinatar.adaugaNotificare(notificare);

        // Afișăm notificările
        destinatar.afiseazaNotificari();
    }
}
