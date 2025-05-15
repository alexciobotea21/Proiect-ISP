package com.vacationhomeexchange;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class RezervareService {
    private static final String FILE = "rezervari.json";
    private static final DataStore<Rezervare> store =
        new DataStore<>(FILE, new TypeToken<List<Rezervare>>() {});

    public static List<Rezervare> getAll() {
        return store.load();
    }

    public static void adaugaRezervare(Rezervare r) {
        List<Rezervare> rezervari = store.load();
        r.setId(rezervari.size() + 1); // atribuire automată id
        rezervari.add(r);
        store.save(rezervari);
        System.out.println("✅ Rezervare înregistrată.");
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
                        "\n   ✅ Confirmată: " + r.isConfirmare());
            }
        }
    }

    public static List<Rezervare> getRezervariNeconfirmatePentruProprietar(Proprietar proprietar) {
        List<Rezervare> toate = store.load();
        List<Rezervare> filtrate = new ArrayList<>();

        for (Rezervare r : toate) {
            if (!r.isConfirmare()) {
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
        } else {
            System.out.println("⚠️ Această rezervare nu este pentru locuințele tale.");
        }
    }
}
