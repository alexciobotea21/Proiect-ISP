package com.vacationhomeexchange;

import com.google.gson.reflect.TypeToken;
import java.util.*;

public class NotificareService {
    private static final DataStore<Notificare> store =
        new DataStore<>("notificari.json", new TypeToken<List<Notificare>>() {});

    private static int idCounter = 1;

    public static boolean trimiteNotificare(Utilizator utilizator, Notificare.TipNotificare tip, String mesaj) {
        if (utilizator == null) {
            throw new IllegalArgumentException("Utilizatorul nu poate fi null.");
        }

        if (utilizator.getEmail() == null || utilizator.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Emailul utilizatorului nu poate fi null sau gol.");
        }

        String email = utilizator.getEmail().trim();
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Emailul utilizatorului are un format invalid.");
        }

        if (tip == null) {
            throw new IllegalArgumentException("Tipul notificării nu poate fi null.");
        }

        if (mesaj == null || mesaj.trim().isEmpty()) {
            throw new IllegalArgumentException("Mesajul notificării nu poate fi gol.");
        }

        Notificare notificare = new Notificare(
            new Random().nextInt(10_000), mesaj, new Date(), tip, email
        );

        salvare(notificare);
        return true;
    }



    public static void salvare(Notificare n) {
        List<Notificare> toate = store.load();

        if (toate == null) {
            toate = new ArrayList<>();
        }

        toate.add(n);
        store.save(toate);
    }

    public static void afiseazaGenerale(Utilizator u) {
        List<Notificare> toate = store.load();
        List<Notificare> generale = toate.stream()
            .filter(n -> n.getEmailDestinatar() != null && n.getEmailDestinatar().equalsIgnoreCase(u.getEmail()))
            .filter(n -> n.getTip() == Notificare.TipNotificare.INFORMATIVA ||
                         n.getTip() == Notificare.TipNotificare.MESAJ_NOU ||
                         n.getTip() == Notificare.TipNotificare.REZERVARE_CONFIRMATA)
            .toList();

        if (generale.isEmpty()) {
            System.out.println("📭 Nu ai notificări generale.");
        } else {
            System.out.println("🔔 Notificări generale:");
            generale.forEach(n -> System.out.println("- [" + n.getTip() + "] " + n.getMesaj()));
        }
    }

    public static void afiseazaAlerteImportante(Utilizator u) {
        List<Notificare> toate = store.load();
        List<Notificare> alerte = toate.stream()
            .filter(n -> n.getEmailDestinatar() != null && n.getEmailDestinatar().equalsIgnoreCase(u.getEmail()))
            .filter(n -> n.getTip() == Notificare.TipNotificare.ALERTA_IMPORTANTA)
            .toList();

        if (alerte.isEmpty()) {
            System.out.println("🚫 Nu ai alerte importante.");
        } else {
            System.out.println("🚨 Alerte importante:");
            alerte.forEach(n -> System.out.println("- " + n.getMesaj()));
        }
    }
}
