package com.vacationhomeexchange;

import com.google.gson.reflect.TypeToken;
import java.util.List;

public class LocuintaService {
    private static final String FILE = "locuinte.json";
    private static final DataStore<Locuinta> store =
        new DataStore<>(FILE, new TypeToken<List<Locuinta>>() {});

    public static List<Locuinta> getAll() {
        return store.load();
    }

    public static void add(Locuinta l) {
        List<Locuinta> locuinte = store.load();
        locuinte.add(l);
        store.save(locuinte);
        System.out.println("✅ Locuință adăugată.");
    }

    public static void afiseazaToate() {
        List<Locuinta> locuinte = store.load();
        if (locuinte.isEmpty()) {
            System.out.println("⚠️ Nu există locuințe înregistrate.");
        } else {
            int index = 1;
            for (Locuinta l : locuinte) {
                System.out.println(index++ + ". [" + l.getTip() + "] " +
                    l.getLocatie() + " – " + l.getPret() + " EUR/noapte\n    " +
                    l.getDescriere());
            }
        }
    }

    public static Locuinta getByIndex(int index) {
        List<Locuinta> locuinte = store.load();
        if (index >= 1 && index <= locuinte.size()) {
            return locuinte.get(index - 1);
        }
        return null;
    }
}
