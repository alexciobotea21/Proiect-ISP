package com.vacationhomeexchange;

import com.google.gson.reflect.TypeToken;
import java.util.*;

public class EvaluareService {
    private static final String FILE = "evaluari.json";
    private static final DataStore<Evaluare> store = new DataStore<>(FILE, new TypeToken<List<Evaluare>>() {});
    private static int idCounter = 1;

    public static void adaugaEvaluare(Evaluare evaluare) {
        if (evaluare == null) throw new IllegalArgumentException("Evaluarea nu poate fi null.");

        // Validări suplimentare
        if (evaluare.getClient() == null)
            throw new IllegalArgumentException("Clientul nu poate fi null.");
        if (evaluare.getLocuinta() == null)
            throw new IllegalArgumentException("Locuința nu poate fi null.");
        if (evaluare.getScor() < 1 || evaluare.getScor() > 5)
            throw new IllegalArgumentException("Scorul trebuie să fie între 1 și 5.");
        if (evaluare.getComentariu() == null || evaluare.getComentariu().trim().isEmpty())
            throw new IllegalArgumentException("Comentariul nu poate fi gol.");

        List<Evaluare> toate = store.load();
        if (toate == null) toate = new ArrayList<>(); 

        toate.add(evaluare);
        store.save(toate);

        System.out.println("✅ Evaluare adăugată cu succes.");
    }

    public static List<Evaluare> getToateEvaluarile() {
        return store.load();
    }

    public static void afiseazaEvaluari() {
        List<Evaluare> toate = store.load();
        if (toate.isEmpty()) {
            System.out.println("📭 Nu există evaluări salvate.");
        } else {
            for (Evaluare e : toate) {
                System.out.println("⭐ " + e.getScor() + " - " + e.getComentariu() + " (" + e.getClient().getEmail() + ")");
            }
        }
    }
} 
