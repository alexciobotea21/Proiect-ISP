package com.vacationhomeexchange;

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

    // Constructor
    public Utilizator(int id, String nume, String email, String parola, String rol) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.parola = parola;
        this.rol = rol;
    }

    // Getters și Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // Metoda extinsă de înregistrare
    public void inregistrare() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceti numele:");
        this.nume = scanner.nextLine();  // Citim numele

        System.out.println("Introduceti email-ul:");
        this.email = scanner.nextLine();  // Citim email-ul

        System.out.println("Introduceti parola:");
        this.parola = scanner.nextLine();  // Citim parola

        System.out.println("Introduceti rolul (client/proprietar):");
        this.rol = scanner.nextLine();  // Citim rolul

        // Verificăm dacă email-ul este valid
        if (verificaEmail()) {
            // Verificăm dacă utilizatorul există deja (simulăm acest lucru)
            if (verificaExistentUtilizator()) {
                System.out.println("Înregistrare eșuată! Utilizatorul cu acest email există deja.");
            } else {
                // Înregistrare completă
                System.out.println("Înregistrare reușită!");
                System.out.println("Bine ai venit, " + nume + "! Contul tău a fost creat cu succes.");
            }
        } else {
            System.out.println("Eroare: Email invalid. Te rugăm să introduci un email valid.");
        }
    }

    // Metoda extinsă de autentificare
    public void autentificare() {
        Scanner scanner = new Scanner(System.in);

        // Verificăm dacă utilizatorul a fost înregistrat corect înainte de autentificare
        if (this.email == null || this.parola == null || this.email.isEmpty() || this.parola.isEmpty()) {
            System.out.println("Te rugăm să te înregistrezi mai întâi!");
            return;
        }

        System.out.println("Introduceti email-ul pentru autentificare:");
        String emailIntroducere = scanner.nextLine();

        // Verificăm dacă email-ul introdus este valid
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

    // Metoda pentru a verifica formatul email-ului
    private boolean verificaEmailFormat(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    // Verifică dacă email-ul este valid
    private boolean verificaEmail() {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Simulăm verificarea existenței unui utilizator
    private boolean verificaExistentUtilizator() {
        // Într-o aplicație reală, ai interoga baza de date
        // Simulăm o listă de email-uri deja existente
        String[] utilizatoriExistenti = {"ion@example.com", "maria@example.com"};
        for (String emailExistent : utilizatoriExistenti) {
            if (email.equals(emailExistent)) {
                return true;  // Utilizatorul există deja
            }
        }
        return false;  // Utilizatorul nu există
    }
}
