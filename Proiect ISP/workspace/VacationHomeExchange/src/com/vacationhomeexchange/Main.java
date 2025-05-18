package com.vacationhomeexchange;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) {
        boolean ruleaza = true;

        while (ruleaza) {
            System.out.println("\n=== MENIU PRINCIPAL ===");
            System.out.println("1. Adaugă utilizator");
            System.out.println("2. Afișează toți utilizatorii");
            System.out.println("3. Șterge utilizator după email");
            System.out.println("4. Autentificare");
            System.out.println("5. Introdu alertă importantă pentru utilizator");
            System.out.println("0. Ieșire");
            System.out.print("Alege o opțiune: ");

            String opt = in.nextLine();

            switch (opt) {
                case "1" -> adaugaUtilizator();
                case "2" -> UserService.afiseazaToti();
                case "3" -> {
                    System.out.print("Emailul de șters: ");
                    UserService.removeByEmail(in.nextLine());
                }
                case "4" -> autentificare();
                case "5" -> introduAlertaAdmin();
                case "0" -> {
                    ruleaza = false;
                    System.out.println("👋 La revedere!");
                }
                default -> System.out.println("⚠️ Opțiune invalidă.");
            }
        }

        in.close();
    }

    private static void adaugaUtilizator() {
        System.out.print("Nume: ");
        String nume = in.nextLine();
        System.out.print("Email: ");
        String email = in.nextLine();
        System.out.print("Parolă: ");
        String parola = in.nextLine();
        System.out.print("Rol (client/proprietar): ");
        String rol = in.nextLine();

        if (rol.equalsIgnoreCase("client")) {
            Utilizator u = new Client(0, nume, email, parola, rol, new ArrayList<>());
            UserService.add(u);
        } else if (rol.equalsIgnoreCase("proprietar")) {
            Utilizator u = new Proprietar(0, nume, email, parola, rol, new ArrayList<>());
            UserService.add(u);
        } else {
            System.out.println("⚠️ Rol necunoscut, utilizatorul nu a fost adăugat.");
        }
    }

    private static void autentificare() {
        System.out.print("Email: ");
        String email = in.nextLine();
        System.out.print("Parolă: ");
        String parola = in.nextLine();

        Utilizator user = UserService.autentifica(email, parola);
        if (user != null) {
            System.out.println("🎉 Autentificare reușită: " + user.getNume() + " (" + user.getRol() + ")");
            if (user instanceof Client client) submeniuClient(client);
            else if (user instanceof Proprietar p) submeniuProprietar(p);
        } else {
            System.out.println("❌ Email sau parolă incorectă.");
        }
    }

    private static void submeniuClient(Client client) {
        boolean activ = true;
        while (activ) {
            System.out.println("\n=== MENIU CLIENT ===");
            System.out.println("1. Afișează locuințe");
            System.out.println("2. Rezervă locuință");
            System.out.println("3. Vezi rezervările mele");
            System.out.println("4. Trimite mesaj către alt utilizator");
            System.out.println("5. Vezi mesajele primite");
            System.out.println("6. Vezi notificări generale");
            System.out.println("7. Vezi alertele importante");
            System.out.println("8. Anulează o rezervare");
            System.out.println("0. Delogare");
            System.out.print("Alege: ");
            String opt = in.nextLine();

            switch (opt) {
                case "1" -> LocuintaService.afiseazaToate();
                case "2" -> rezervareNoua(client);
                case "3" -> RezervareService.afiseazaRezervarileClientului(client.getEmail());
                case "4" -> trimiteMesaj(client);
                case "5" -> afiseazaMesajePrimite(client);
                case "6" -> NotificareService.afiseazaGenerale(client);
                case "7" -> NotificareService.afiseazaAlerteImportante(client);
                case "8" -> RezervareService.anulareInteractiv(client, in);
                case "0" -> activ = false;
                default -> System.out.println("⚠️ Opțiune invalidă.");
            }
        }
    }

    private static void submeniuProprietar(Proprietar p) {
        boolean activ = true;
        while (activ) {
            System.out.println("\n=== MENIU PROPRIETAR ===");
            System.out.println("1. Afișează rezervări neconfirmate");
            System.out.println("2. Confirmă o rezervare");
            System.out.println("3. Trimite mesaj către alt utilizator");
            System.out.println("4. Vezi mesajele primite");
            System.out.println("5. Adaugă locuință");
            System.out.println("6. Șterge locuință");
            System.out.println("7. Vezi locuințele tale");
            System.out.println("8. Vezi notificări generale");
            System.out.println("9. Vezi alertele importante");
            System.out.println("0. Delogare");
            System.out.print("Alege: ");
            String opt = in.nextLine();

            switch (opt) {
                case "1" -> RezervareService.afiseazaNeconfirmate(p);
                case "2" -> RezervareService.confirmareInteractiv(p, in);
                case "3" -> trimiteMesaj(p);
                case "4" -> afiseazaMesajePrimite(p);
                case "5" -> LocuintaService.adaugaInteractiv(p, in);
                case "6" -> LocuintaService.stergeInteractiv(p, in);
                case "7" -> LocuintaService.afiseazaLocuinteProprietar(p);
                case "8" -> NotificareService.afiseazaGenerale(p);
                case "9" -> NotificareService.afiseazaAlerteImportante(p);
                case "0" -> activ = false;
                default -> System.out.println("⚠️ Opțiune invalidă.");
            }
        }
    }

    private static void rezervareNoua(Client client) {
        List<Locuinta> locuinte = LocuintaService.getAll();
        if (locuinte.isEmpty()) {
            System.out.println("⚠️ Nu există locuințe disponibile.");
            return;
        }
        LocuintaService.afiseazaToate();
        try {
            System.out.print("Selectează locuință (index): ");
            int index = Integer.parseInt(in.nextLine());
            Locuinta loc = LocuintaService.getByIndex(index);
            System.out.print("Data început (dd-MM-yyyy): ");
            Date inceput = sdf.parse(in.nextLine());
            System.out.print("Data sfârșit (dd-MM-yyyy): ");
            Date sfarsit = sdf.parse(in.nextLine());
            Rezervare rez = new Rezervare(0, inceput, sfarsit, client, loc);
            RezervareService.adaugaRezervare(rez);
        } catch (Exception e) {
            System.out.println("❌ Eroare: " + e.getMessage());
        }
    }

    private static void trimiteMesaj(Utilizator expeditor) {
        System.out.print("Email destinatar: ");
        String email = in.nextLine();
        Utilizator destinatar = UserService.getAll().stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
        if (destinatar == null) {
            System.out.println("❌ Utilizator inexistent.");
            return;
        }
        System.out.print("Mesaj: ");
        String continut = in.nextLine();
        MesajService.trimiteMesaj(expeditor, destinatar, continut);
    }

    private static void afiseazaMesajePrimite(Utilizator u) {
        List<Mesaj> toate = new DataStore<>("mesaje.json", new com.google.gson.reflect.TypeToken<List<Mesaj>>() {}).load();
        List<Mesaj> primite = toate.stream()
            .filter(m -> m.getEmailDestinatar() != null && m.getEmailDestinatar().equalsIgnoreCase(u.getEmail()))
            .toList();
        if (primite.isEmpty()) System.out.println("📭 Niciun mesaj primit.");
        else {
            System.out.println("📨 Mesaje primite:");
            for (Mesaj m : primite) {
                System.out.println("- De la " + m.getEmailExpeditor() + ": " + m.getContinut() + " [" + m.getData() + "]");
            }
        }
    }

    private static void introduAlertaAdmin() {
        System.out.print("Email utilizator: ");
        String email = in.nextLine();
        Utilizator user = UserService.getAll().stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email))
            .findFirst()
            .orElse(null);
        if (user == null) {
            System.out.println("❌ Utilizator inexistent.");
            return;
        }
        System.out.print("Mesaj alertă: ");
        String mesaj = in.nextLine();
        Notificare alerta = new Notificare(mesaj, new Date(), Notificare.TipNotificare.ALERTA_IMPORTANTA, email);
        NotificareService.salvare(alerta);
        user.adaugaNotificare(alerta);
        UserService.update(user);
        System.out.println("🚨 Alertă importantă salvată.");
    }
}
