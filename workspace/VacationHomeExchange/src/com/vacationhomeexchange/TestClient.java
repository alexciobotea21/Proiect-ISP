package com.vacationhomeexchange;

import java.util.ArrayList;
import java.util.Date;

public class TestClient {
    public static void main(String[] args) {
        // Creează un client cu un istoric de rezervări gol
        Client client = new Client(1, "Ion Popescu", "ion@example.com", "parola69", "client", new ArrayList<>());
        
        // Testează getterele
        System.out.println("Client: " + client.getNume());
        System.out.println("Email: " + client.getEmail());
        System.out.println("Rol: " + client.getRol());

        // Testează metodele
        client.cautaLocuinte();
        client.rezervaLocuinta();
        client.evalueazaLocuinta();
        
        // Testează adăugarea unei rezervări în istoricul clientului
        Rezervare rezervare = new Rezervare(1, new Date(), new Date(), client, new Locuinta(1, "Apartament", "București", 150.0, "Locuință confortabilă"));
        client.getIstoricRezervari().add(rezervare); // Adăugăm rezervarea în istoricul clientului
        System.out.println("Istoric rezervări: " + client.getIstoricRezervari().size() + " rezervări.");
    }
}
