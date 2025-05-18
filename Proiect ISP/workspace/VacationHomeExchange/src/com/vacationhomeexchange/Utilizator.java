package com.vacationhomeexchange;

import java.util.ArrayList;
import java.util.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilizator {
    // Atribute
    private int id;
    private String nume;
    private String email;
    private String parola;
    private String rol;
    private List<Notificare> notificari = new ArrayList<>();

    // Constructor
    public Utilizator(int id, String nume, String email, String parola, String rol) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.parola = parola;
        this.rol = rol;
        this.notificari = new ArrayList<>();
    }

    // Getters și Setters
    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getEmail() {
        return email;
    }

    public String getParola() {
        return parola;
    }

    public String getRol() {
        return rol;
    }

    public List<Notificare> getNotificari() {
        return notificari;
    }

    // Adăugare notificare în listă
    public void adaugaNotificare(Notificare notificare) {
        notificari.add(notificare);
        System.out.println("📬 Notificare adăugată pentru " + nume + ": " + notificare.getMesaj());
    }

    // Afișează toate notificările primite
    public void afiseazaNotificari() {
        System.out.println("🔔 Notificări pentru " + nume + ":");
        if (notificari.isEmpty()) {
            System.out.println("  - Nu ai notificări.");
        } else {
            for (Notificare n : notificari) {
                System.out.println("  [" + n.getTip() + "] " + n.getMesaj() + " | " + n.getData());
            }
        }
    }

    // Metoda extinsă de înregistrare
    public void inregistrare() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceti numele:");
        this.nume = scanner.nextLine();

        System.out.println("Introduceti email-ul:");
        this.email = scanner.nextLine();

        System.out.println("Introduceti parola:");
        this.parola = scanner.nextLine();

        System.out.println("Introduceti rolul (client/proprietar):");
        this.rol = scanner.nextLine();

        if (verificaEmail()) {
            if (verificaExistentUtilizator()) {
                System.out.println("Înregistrare eșuată! Utilizatorul cu acest email există deja.");
            } else {
                System.out.println("Înregistrare reușită! Bine ai venit, " + nume + "!");
            }
        } else {
            System.out.println("Eroare: Email invalid. Te rugăm să introduci un email valid.");
        }
    }

    public void autentificare() {
        Scanner scanner = new Scanner(System.in);

        if (this.email == null || this.parola == null || this.email.isEmpty() || this.parola.isEmpty()) {
            System.out.println("Te rugăm să te înregistrezi mai întâi!");
            return;
        }

        System.out.println("Introduceti email-ul pentru autentificare:");
        String emailIntroducere = scanner.nextLine();

        if (!verificaEmailFormat(emailIntroducere)) {
            System.out.println("Eroare: Email invalid. Te rugăm să introduci un email valid.");
            return;
        }

        System.out.println("Introduceti parola:");
        String parolaIntroducere = scanner.nextLine();

        if (emailIntroducere.equals(this.email) && parolaIntroducere.equals(this.parola)) {
            System.out.println("Autentificare reușită! Bun venit, " + nume + "!");
        } else {
            System.out.println("Eroare: Email sau parolă incorectă.");
        }
    }

    // Validare email (pentru câmpul din obiect)
    private boolean verificaEmail() {
        return verificaEmailFormat(this.email);
    }

    // Validare format email generic
    private boolean verificaEmailFormat(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Simulare verificare utilizatori existenți
    private boolean verificaExistentUtilizator() {
        String[] utilizatoriExistenti = {"ion@example.com", "maria@example.com"};
        for (String emailExistent : utilizatoriExistenti) {
            if (email.equals(emailExistent)) {
                return true;
            }
        }
        return false;
    }
}
