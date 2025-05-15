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
            System.out.println("✅ Utilizator adăugat cu succes.");
        } else if (rol.equalsIgnoreCase("proprietar")) {
            Utilizator u = new Proprietar(0, nume, email, parola, rol, new ArrayList<>());
            UserService.add(u);
            System.out.println("✅ Utilizator adăugat cu succes.");
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
            // Folosim sintaxa clasica instanceof cu cast explicit
            if (user instanceof Client) {
                Client client = (Client) user;
                submeniuClient(client);
            } else if (user instanceof Proprietar) {
                Proprietar proprietar = (Proprietar) user;
                submeniuProprietar(proprietar);
            } else {
                System.out.println("⚠️ Submeniu neimplementat pentru acest rol.");
            }
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
            System.out.println("0. Delogare");

            System.out.print("Alege: ");
            String opt = in.nextLine();

            switch (opt) {
                case "1" -> LocuintaService.afiseazaToate();
                case "2" -> rezervareNoua(client);
                case "3" -> RezervareService.afiseazaRezervarileClientului(client.getEmail());
                case "0" -> {
                    System.out.println("🔒 Delogat.");
                    activ = false;
                }
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
        System.out.print("Selectează numărul locuinței: ");
        try {
            int index = Integer.parseInt(in.nextLine());
            Locuinta loc = LocuintaService.getByIndex(index);
            if (loc == null) {
                System.out.println("⚠️ Locuință invalidă.");
                return;
            }

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

    private static void submeniuProprietar(Proprietar p) {
        boolean activ = true;

        while (activ) {
            System.out.println("\n=== MENIU PROPRIETAR ===");
            System.out.println("1. Afișează rezervări neconfirmate");
            System.out.println("2. Confirmă o rezervare");
            System.out.println("0. Delogare");

            System.out.print("Alege: ");
            String opt = in.nextLine();

            switch (opt) {
                case "1" -> {
                    List<Rezervare> neconfirmate = RezervareService.getRezervariNeconfirmatePentruProprietar(p);
                    if (neconfirmate.isEmpty()) {
                        System.out.println("📭 Nu ai rezervări neconfirmate.");
                    } else {
                        int i = 1;
                        for (Rezervare r : neconfirmate) {
                            System.out.println(i++ + ". " + r.getUtilizatorClient().getNume() + " a rezervat " +
                                    r.getLocuinta().getTip() + " în " + r.getLocuinta().getLocatie() +
                                    " [" + r.getDataInceput() + " → " + r.getDataSfarsit() + "]");
                        }
                    }
                }

                case "2" -> {
                    List<Rezervare> neconfirmate = RezervareService.getRezervariNeconfirmatePentruProprietar(p);
                    if (neconfirmate.isEmpty()) {
                        System.out.println("Nu ai rezervări de confirmat.");
                        break;
                    }
                    System.out.print("Selectează rezervarea de confirmat: ");
                    int nr = Integer.parseInt(in.nextLine());
                    if (nr >= 1 && nr <= neconfirmate.size()) {
                        Rezervare rezervare = neconfirmate.get(nr - 1);
                        RezervareService.confirmaRezervare(p, rezervare);
                    } else {
                        System.out.println("Număr invalid.");
                    }
                }

                case "0" -> {
                    System.out.println("🔒 Delogat.");
                    activ = false;
                }

                default -> System.out.println("⚠️ Opțiune invalidă.");
            }
        }
    }
}
