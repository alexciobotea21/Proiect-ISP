package com.vacationhomeexchange;

import java.util.List;

public class Proprietar extends Utilizator {
    // Atribute
    private List<Locuinta> listaLocuinte;

    // Constructor
    public Proprietar(int id, String nume, String email, String parola, String rol, List<Locuinta> listaLocuinte) {
        super(id, nume, email, parola, rol);  // Apelăm constructorul părinte
        this.listaLocuinte = listaLocuinte;
    }

    // Metode specifice Proprietar
    public void raspundeMesaj() {
        System.out.println("Mesajul a fost răspuns.");
    }

    public void confirmaRezervare() {
        System.out.println("Rezervarea a fost confirmată.");
    }

    public List<Locuinta> getListaLocuinte() {
        return listaLocuinte;
    }

    public void setListaLocuinte(List<Locuinta> listaLocuinte) {
        this.listaLocuinte = listaLocuinte;
    }
    
    public void afiseazaLocuinte() {
        if (listaLocuinte == null || listaLocuinte.isEmpty()) {
            System.out.println("Nu există locuințe disponibile.");
            return;
        }

        System.out.println("Lista locuințelor deținute:");
        for (Locuinta locuinta : listaLocuinte) {
            System.out.println("-----------------------------");
            System.out.println("Tip: " + locuinta.getTip());
            System.out.println("Locație: " + locuinta.getLocatie());
            System.out.println("Preț: " + locuinta.getPret());
            System.out.println("Descriere: " + locuinta.getDescriere());
        }
    }
    
    public void confirmaRezervare(Rezervare rezervare) {
        if (rezervare == null || rezervare.getLocuinta() == null) {
            System.out.println("Rezervarea este invalidă.");
            return;
        }

        // Verificăm dacă locuința rezervării aparține acestui proprietar
        boolean esteLocuintaProprie = false;
        for (Locuinta l : listaLocuinte) {
            if (l.getId() == rezervare.getLocuinta().getId()) {
                esteLocuintaProprie = true;
                break;
            }
        }

        if (esteLocuintaProprie) {
            rezervare.confirmareRezervare();
            System.out.println("Rezervarea #" + rezervare.getId() + " a fost confirmată de către proprietar.");
        } else {
            System.out.println("Eroare: această locuință nu aparține proprietarului curent.");
        }
    }

}
