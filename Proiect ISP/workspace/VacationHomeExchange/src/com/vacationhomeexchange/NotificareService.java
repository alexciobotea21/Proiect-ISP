package com.vacationhomeexchange;

import com.google.gson.reflect.TypeToken;
import java.util.*;

public class NotificareService {
    private static final DataStore<Notificare> store =
        new DataStore<>("notificari.json", new TypeToken<List<Notificare>>() {});

    public static void salvare(Notificare n) {
        List<Notificare> toate = store.load();
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
