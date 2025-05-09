package com.vacationhomeexchange;
import java.util.List;

public class Client extends Utilizator {
    // Atribute
    private List<Rezervare> istoricRezervari;

    // Constructor
    public Client(int id, String nume, String email, String parola, String rol, List<Rezervare> istoricRezervari) {
        super(id, nume, email, parola, rol);  // Apelăm constructorul părinte
        this.istoricRezervari = istoricRezervari;
    }

    // Metode specifice Client
    public void cautaLocuinte() {
        System.out.println("Căutăm locuințe disponibile...");
    }

    public void rezervaLocuinta() {
        System.out.println("Locuința a fost rezervată!");
    }

    public void evalueazaLocuinta() {
        System.out.println("Locuința a fost evaluată!");
    }

    public List<Rezervare> getIstoricRezervari() {
        return istoricRezervari;
    }

    public void setIstoricRezervari(List<Rezervare> istoricRezervari) {
        this.istoricRezervari = istoricRezervari;
    }
}
