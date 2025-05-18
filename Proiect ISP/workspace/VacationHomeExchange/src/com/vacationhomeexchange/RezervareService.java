package com.vacationhomeexchange;

import com.google.gson.reflect.TypeToken;
import java.util.*;

public class RezervareService {
    private static final String FILE = "rezervari.json";
    private static final DataStore<Rezervare> store =
        new DataStore<>(FILE, new TypeToken<List<Rezervare>>() {});

    public static List<Rezervare> getAll() {
        return store.load();
    }

    public static void adaugaRezervare(Rezervare r) {
        List<Rezervare> rezervari = store.load();
        r.setId(rezervari.size() + 1);
        rezervari.add(r);
        store.save(rezervari);
        System.out.println("✅ Rezervare înregistrată.");

        // Notificare pentru proprietar
        Notificare notificare = new Notificare(
            "Rezervare nouă pentru locuința din " + r.getLocuinta().getLocatie(),
            new Date(),
            Notificare.TipNotificare.INFORMATIVA,
            r.getLocuinta().getEmailProprietar()
        );
        NotificareService.salvare(notificare);
        Utilizator proprietar = UserService.getAll().stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(r.getLocuinta().getEmailProprietar()))
            .findFirst().orElse(null);
        if (proprietar != null) {
            proprietar.adaugaNotificare(notificare);
            UserService.update(proprietar);
        }
     // Notificare către client
        Notificare selfNotif = new Notificare(
            "Ai efectuat o rezervare pentru " + r.getLocuinta().getLocatie(),
            new Date(),
            Notificare.TipNotificare.INFORMATIVA,
            r.getUtilizatorClient().getEmail()
        );
        NotificareService.salvare(selfNotif);
        UserService.update(r.getUtilizatorClient());
    }

    public static List<Rezervare> getByClientEmail(String email) {
        List<Rezervare> toate = store.load();
        List<Rezervare> filtrate = new ArrayList<>();
        for (Rezervare r : toate) {
            if (r.getUtilizatorClient().getEmail().equalsIgnoreCase(email)) {
                filtrate.add(r);
            }
        }
        return filtrate;
    }

    public static void afiseazaRezervarileClientului(String emailClient) {
        List<Rezervare> lista = getByClientEmail(emailClient);
        if (lista.isEmpty()) {
            System.out.println("📭 Nu ai rezervări salvate.");
        } else {
            int i = 1;
            for (Rezervare r : lista) {
                Locuinta l = r.getLocuinta();
                System.out.println(i++ + ". " + l.getTip() + " – " + l.getLocatie() +
                        " | " + l.getPret() + " EUR/noapte" +
                        "\n   ↪️ Perioadă: " + r.getDataInceput() + " → " + r.getDataSfarsit() +
                        "\n   ✅ Confirmată: " + r.isConfirmata());
            }
        }
    }

    public static List<Rezervare> getRezervariNeconfirmatePentruProprietar(Proprietar proprietar) {
        List<Rezervare> toate = store.load();
        List<Rezervare> filtrate = new ArrayList<>();

        for (Rezervare r : toate) {
            if (!r.isConfirmata()) {
                for (Locuinta l : proprietar.getListaLocuinte()) {
                    if (l.getId() == r.getLocuinta().getId()) {
                        filtrate.add(r);
                        break;
                    }
                }
            }
        }

        return filtrate;
    }

    public static void confirmaRezervare(Proprietar proprietar, Rezervare rezervare) {
        boolean apartine = false;
        for (Locuinta l : proprietar.getListaLocuinte()) {
            if (l.getId() == rezervare.getLocuinta().getId()) {
                apartine = true;
                break;
            }
        }

        if (apartine) {
            rezervare.confirmareRezervare();
            List<Rezervare> toate = store.load();
            for (int i = 0; i < toate.size(); i++) {
                if (toate.get(i).getId() == rezervare.getId()) {
                    toate.set(i, rezervare);
                    break;
                }
            }
            store.save(toate);
            System.out.println("✅ Rezervarea a fost confirmată.");

            Notificare notificare = new Notificare(
                "Rezervarea ta pentru " + rezervare.getLocuinta().getLocatie() + " a fost confirmată",
                new Date(),
                Notificare.TipNotificare.INFORMATIVA,
                rezervare.getUtilizatorClient().getEmail()
            );
            NotificareService.salvare(notificare);
            UserService.getAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(notificare.getEmailDestinatar()))
                .findFirst()
                .ifPresent(u -> {
                    u.adaugaNotificare(notificare);
                    UserService.update(u);
                });
        } else {
            System.out.println("⚠️ Această rezervare nu este pentru locuințele tale.");
        }
    }

    public static void afiseazaNeconfirmate(Proprietar p) {
        List<Rezervare> toate = getAll();
        List<Rezervare> neconfirmate = toate.stream()
            .filter(r -> r.getLocuinta().getEmailProprietar().equalsIgnoreCase(p.getEmail()))
            .filter(r -> !r.isConfirmata())
            .toList();

        if (neconfirmate.isEmpty()) {
            System.out.println("📭 Nu ai rezervări neconfirmate.");
        } else {
            int i = 1;
            for (Rezervare r : neconfirmate) {
                System.out.println(i++ + ". " + r.getUtilizatorClient().getNume() + " - " +
                        r.getLocuinta().getLocatie() + " [" + r.getDataInceput() + " - " + r.getDataSfarsit() + "]");
            }
        }
    }

    public static void confirmareInteractiv(Proprietar p, Scanner in) {
        List<Rezervare> toate = getAll();
        List<Rezervare> neconfirmate = toate.stream()
            .filter(r -> r.getLocuinta().getEmailProprietar().equalsIgnoreCase(p.getEmail()))
            .filter(r -> !r.isConfirmata())
            .toList();

        if (neconfirmate.isEmpty()) {
            System.out.println("📭 Nu ai rezervări neconfirmate.");
            return;
        }

        for (int i = 0; i < neconfirmate.size(); i++) {
            Rezervare r = neconfirmate.get(i);
            System.out.println((i + 1) + ". " + r.getUtilizatorClient().getEmail() + " → " +
                    r.getLocuinta().getLocatie() + " [" + r.getDataInceput() + " - " + r.getDataSfarsit() + "]");
        }

        System.out.print("Selectează indexul de confirmat: ");
        int index = Integer.parseInt(in.nextLine());
        if (index < 1 || index > neconfirmate.size()) {
            System.out.println("❌ Index invalid.");
            return;
        }

        Rezervare rezervare = neconfirmate.get(index - 1);
        rezervare.setConfirmata(true);
        saveAll(toate);
        System.out.println("✅ Rezervare confirmată.");
    }

    public static void saveAll(List<Rezervare> lista) {
        store.save(lista);
    }

    public static void anulareInteractiv(Client client, Scanner in) {
        List<Rezervare> toate = store.load();
        List<Rezervare> aleClientului = toate.stream()
            .filter(r -> r.getUtilizatorClient().getEmail().equalsIgnoreCase(client.getEmail()))
            .filter(Rezervare::isConfirmata)
            .toList();

        if (aleClientului.isEmpty()) {
            System.out.println("📭 Nu ai rezervări confirmate de anulat.");
            return;
        }

        for (int i = 0; i < aleClientului.size(); i++) {
            Rezervare r = aleClientului.get(i);
            System.out.println((i + 1) + ". " + r.getLocuinta().getLocatie() +
                " [" + r.getDataInceput() + " - " + r.getDataSfarsit() + "]");
        }

        System.out.print("Selectează indexul rezervării de anulat: ");
        try {
            int index = Integer.parseInt(in.nextLine());
            if (index < 1 || index > aleClientului.size()) {
                System.out.println("❌ Index invalid.");
                return;
            }

            Rezervare selectata = aleClientului.get(index - 1);
            selectata.setConfirmata(false);

            for (int i = 0; i < toate.size(); i++) {
                if (toate.get(i).getId() == selectata.getId()) {
                    toate.set(i, selectata);
                    break;
                }
            }

            store.save(toate);
            System.out.println("❌ Rezervare anulată.");

            Notificare notificare = new Notificare(
                "Clientul a anulat rezervarea pentru " + selectata.getLocuinta().getLocatie(),
                new Date(),
                Notificare.TipNotificare.INFORMATIVA,
                selectata.getLocuinta().getEmailProprietar()
            );
         // Notificare pentru client
            Notificare selfNotif = new Notificare(
                "Ai anulat rezervarea pentru " + selectata.getLocuinta().getLocatie(),
                new Date(),
                Notificare.TipNotificare.INFORMATIVA,
                client.getEmail()
            );
            NotificareService.salvare(selfNotif);
            client.adaugaNotificare(selfNotif);
            UserService.update(client);

            NotificareService.salvare(notificare);
            UserService.getAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(notificare.getEmailDestinatar()))
                .findFirst()
                .ifPresent(u -> {
                    u.adaugaNotificare(notificare);
                    UserService.update(u);
                });
        } catch (Exception e) {
            System.out.println("⚠️ Eroare: " + e.getMessage());
        }
    }
}
