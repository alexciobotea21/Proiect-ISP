package com.vacationhomeexchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class main
 {
    public static void main(String[] args) {
        // Liste de nume și locații predefinite
        List<String> numeProprietari = Arrays.asList(
            "Ionescu Maria", "Popescu Andrei", "Dumitrescu Elena", 
            "Stanciu George", "Florescu Ana"
        );
        
        List<String> numeClienti = Arrays.asList(
            "Radu Mihai", "Munteanu Ioana", "Dobre Alex", 
            "Marin Carmen", "Popovici Vlad"
        );
        
        List<String> locatii = Arrays.asList(
            "București", "Brașov", "Cluj-Napoca", "Constanța", "Sibiu", 
            "Timișoara", "Iași", "Sinaia", "Bran", "Vama Veche"
        );
        
        List<String> tipuriLocuinte = Arrays.asList(
            "Apartament", "Casă", "Vilă", "Studio", "Cabană"
        );

        // Listele pentru stocare
        List<Proprietar> proprietari = new ArrayList<>();
        List<Client> clienti = new ArrayList<>();
        List<Locuinta> toateLocuintele = new ArrayList<>();
        Random random = new Random();

        // Generare proprietari (5) cu câte 10 locuințe
        for (int i = 0; i < 5; i++) {
            List<Locuinta> locuinteProprietar = new ArrayList<>();
            
            for (int j = 0; j < 10; j++) {
                String tipAleator = tipuriLocuinte.get(random.nextInt(tipuriLocuinte.size()));
                String locatieAleatoare = locatii.get(random.nextInt(locatii.size()));
                double pret = 80 + random.nextInt(500); // Pret între 80 și 580 RON/noapte
                
                Locuinta locuinta = new Locuinta(
                    (i + 1) * 100 + j + 1, // ID unic
                    tipAleator,
                    locatieAleatoare,
                    pret,
                    "Frumos " + tipAleator.toLowerCase() + " în " + locatieAleatoare + 
                    " cu vedere la " + (random.nextBoolean() ? "munți" : "mare") + "."
                );
                locuinteProprietar.add(locuinta);
                toateLocuintele.add(locuinta);
            }

            Proprietar proprietar = new Proprietar(
                i + 1,
                numeProprietari.get(i),
                numeProprietari.get(i).toLowerCase().replace(" ", "") + "@example.com",
                "parola" + (i + 1),
                "proprietar",
                locuinteProprietar
            );
            proprietari.add(proprietar);
        }

        // Generare clienți (5)
        for (int i = 0; i < 5; i++) {
            Client client = new Client(
                i + 101, // ID-uri începând de la 101
                numeClienti.get(i),
                numeClienti.get(i).toLowerCase().replace(" ", "") + "@example.com",
                "parola" + (i + 1),
                "client",
                new ArrayList<>() // Fără rezervări inițiale
            );
            clienti.add(client);
        }

        // Afișare detaliată
        System.out.println("=== PROPRIETARI ===");
        for (Proprietar p : proprietari) {
            System.out.printf("%s (%s) are %d locuințe:\n", 
                p.getNume(), p.getEmail(), p.getListaLocuinte().size());
            for (Locuinta l : p.getListaLocuinte()) {
                System.out.printf("  - %s în %s (Preț: %.2f RON/noapte)\n", 
                    l.getTip(), l.getLocatie(), l.getPret());
            }
            System.out.println();
        }

        System.out.println("=== CLIENTI ===");
        for (Client c : clienti) {
            System.out.printf("%s (%s)\n", c.getNume(), c.getEmail());
        }
        
        System.out.println("\n=== TEST FUNCȚIE CAUTARE ===");
        Client clientDeTest = clienti.get(0); // Primul client

        // Caută "Apartamente" în "Brașov" sub 300 RON
        clientDeTest.cautaLocuinte(toateLocuintele, "Apartament", "Brașov", 300.0);
        
        
     // ... EXISTING CODE ...

        System.out.println("\n=== TEST FUNCȚIE CAUTARE ===");
        Client clientDeTest = clienti.get(0); // Primul client

        // Caută "Apartamente" în "Brașov" sub 300 RON
        clientDeTest.cautaLocuinte(toateLocuintele, "Apartament", "Brașov", 300.0);

        // === TESTARE FUNCȚIE REZERVARE ===
        System.out.println("\n=== TEST FUNCȚIE REZERVARE ===");

        Locuinta locuintaDeTest = toateLocuintele.get(0); // prima locuință
        Date dataInceput = new Date(2025 - 1900, 4, 20); // 20 mai 2025 (luna începe de la 0)
        Date dataSfarsit = new Date(2025 - 1900, 4, 25);  // 25 mai 2025

        clientDeTest.rezervaLocuinta(locuintaDeTest, dataInceput, dataSfarsit);

        System.out.println("\n=== ISTORIC REZERVĂRI PENTRU " + clientDeTest.getNume() + " ===");
        for (Rezervare rez : clientDeTest.getIstoricRezervari()) {
            System.out.println("Rezervare ID: " + rez.getId());
            System.out.println("Locuință: " + rez.getLocuinta().getTip() + " în " + rez.getLocuinta().getLocatie());
            System.out.println("Perioadă: " + rez.getDataInceput() + " - " + rez.getDataSfarsit());
            System.out.println("Confirmare: " + rez.isConfirmare());
            System.out.println("-------------------------");
        }

    }
}