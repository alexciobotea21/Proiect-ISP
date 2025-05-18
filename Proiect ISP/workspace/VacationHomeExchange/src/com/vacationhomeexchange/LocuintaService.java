package com.vacationhomeexchange;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

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
    
    public static void saveAll(List<Locuinta> lista) {
    	store.save(lista);
    }

    public static void adaugaInteractiv(Proprietar p, Scanner in) {
        System.out.print("Tip locuință: ");
        String tip = in.nextLine();
        System.out.print("Locație: ");
        String locatie = in.nextLine();
        System.out.print("Preț pe noapte: ");
        double pret = Double.parseDouble(in.nextLine());
        System.out.print("Descriere: ");
        String descriere = in.nextLine();

        List<Locuinta> locuinte = getAll();
        int id = locuinte.size() + 1;

        Locuinta noua = new Locuinta(id, tip, locatie, pret, descriere, p.getEmail());
        locuinte.add(noua);
        store.save(locuinte);
        p.getListaLocuinte().add(noua);
        UserService.update(p);
        Notificare n = new Notificare(
        	    "Ai adăugat o nouă locuință în " + locatie,
        	    new Date(),
        	    Notificare.TipNotificare.INFORMATIVA,
        	    p.getEmail()
        	);
        	NotificareService.salvare(n);
        	p.adaugaNotificare(n);
        	UserService.update(p);

        System.out.println("✅ Locuință adăugată.");
    }

    public static void stergeInteractiv(Proprietar p, Scanner in) {
        List<Locuinta> locuinte = getAll().stream()
            .filter(l -> l.getEmailProprietar().equalsIgnoreCase(p.getEmail()))
            .toList();

        if (locuinte.isEmpty()) {
            System.out.println("⚠️ Nu ai locuințe înregistrate.");
            return;
        }

        for (int i = 0; i < locuinte.size(); i++) {
            Locuinta l = locuinte.get(i);
            System.out.println((i + 1) + ". " + l.getTip() + " – " + l.getLocatie());
        }

        System.out.print("Selectează indexul de șters: ");
        int index = Integer.parseInt(in.nextLine());
        if (index < 1 || index > locuinte.size()) {
            System.out.println("❌ Index invalid.");
            return;
        }

        Locuinta deSters = locuinte.get(index - 1);
        List<Locuinta> toate = getAll();
        toate.removeIf(l -> l.getId() == deSters.getId());
        store.save(toate);

        p.getListaLocuinte().removeIf(l -> l.getId() == deSters.getId());
        UserService.update(p);
        Notificare n = new Notificare(
        	    "Ai șters o locuință din " + deSters.getLocatie(),
        	    new Date(),
        	    Notificare.TipNotificare.INFORMATIVA,
        	    p.getEmail()
        	);
        	NotificareService.salvare(n);
        	p.adaugaNotificare(n);
        	UserService.update(p);

        System.out.println("🗑️ Locuință ștearsă.");
    }

    public static void afiseazaLocuinteProprietar(Proprietar p) {
        List<Locuinta> toate = getAll();
        List<Locuinta> aleLui = toate.stream()
            .filter(l -> l.getEmailProprietar().equalsIgnoreCase(p.getEmail()))
            .toList();

        if (aleLui.isEmpty()) {
            System.out.println("📭 Nu ai locuințe înregistrate.");
        } else {
            System.out.println("🏡 Locuințele tale:");
            for (Locuinta l : aleLui) {
                System.out.println("- [" + l.getTip() + "] " + l.getLocatie() + ", " +
                    l.getPret() + " EUR/noapte\n    " + l.getDescriere());
            }
        }
    }

}