package com.vacationhomeexchange;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final String FILE = "users.json";
    private static final DataStore<Utilizator> store =
        new DataStore<>(FILE, new TypeToken<List<Utilizator>>() {});

    public static List<Utilizator> getAll() {
        return store.load();
    }

    public static void add(Utilizator u) {
        List<Utilizator> users = store.load();
        users.add(u);
        store.save(users);
        System.out.println("✅ Utilizator adăugat cu succes.");
    }

    public static void removeByEmail(String email) {
        List<Utilizator> users = store.load();
        boolean removed = users.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
        store.save(users);
        if (removed) {
            System.out.println("✅ Utilizator șters.");
        } else {
            System.out.println("⚠️ Nu s-a găsit niciun utilizator cu emailul: " + email);
        }
    }

    public static Utilizator autentifica(String email, String parola) {
        Utilizator u = store.load().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email) && user.getParola().equals(parola))
                .findFirst()
                .orElse(null);
        if (u == null) return null;

        // Transformare în Client sau Proprietar pe baza rolului
        if ("client".equalsIgnoreCase(u.getRol())) {
            return new Client(u.getId(), u.getNume(), u.getEmail(), u.getParola(), u.getRol(), new ArrayList<>());
        } else if ("proprietar".equalsIgnoreCase(u.getRol())) {
            return new Proprietar(u.getId(), u.getNume(), u.getEmail(), u.getParola(), u.getRol(), new ArrayList<>());
        } else {
            return u; // rol necunoscut, întoarce ca Utilizator simplu
        }
    }

    public static void afiseazaToti() {
        List<Utilizator> users = store.load();
        if (users.isEmpty()) {
            System.out.println("⚠️ Nu există utilizatori înregistrați.");
        } else {
            users.forEach(u ->
                System.out.println("• " + u.getNume() + " (" + u.getEmail() + ", " + u.getRol() + ")"));
        }
    }

    // ✅ Metodă nouă pentru actualizarea unui utilizator existent (ex: notificări noi)
    public static void update(Utilizator utilizator) {
        List<Utilizator> users = store.load();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equalsIgnoreCase(utilizator.getEmail())) {
                users.set(i, utilizator);
                store.save(users);
                System.out.println("🔁 Utilizator actualizat.");
                return;
            }
        }
        System.out.println("⚠️ Utilizatorul nu a fost găsit pentru actualizare.");
    }
}
