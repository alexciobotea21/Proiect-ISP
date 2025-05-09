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
}
