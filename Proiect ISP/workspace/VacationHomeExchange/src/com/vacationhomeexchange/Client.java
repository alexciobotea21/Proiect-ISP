package com.vacationhomeexchange;
import java.util.Date;
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
    public void cautaLocuinte(List<Locuinta> toateLocuintele, String tip, String locatie, double pretMaxim) {
        System.out.println("Rezultatele căutării pentru tip: " + tip + ", locație: " + locatie + ", preț maxim: " + pretMaxim);

        boolean gasit = false;

        for (Locuinta locuinta : toateLocuintele) {
            if (locuinta.getTip().equalsIgnoreCase(tip) &&
                locuinta.getLocatie().equalsIgnoreCase(locatie) &&
                locuinta.getPret() <= pretMaxim) {

                gasit = true;
                System.out.println("-----------------------------");
                System.out.println("Tip: " + locuinta.getTip());
                System.out.println("Locație: " + locuinta.getLocatie());
                System.out.println("Preț: " + locuinta.getPret());
                System.out.println("Descriere: " + locuinta.getDescriere());
            }
        }

        if (!gasit) {
            System.out.println("Nu s-au găsit locuințe care să corespundă criteriilor.");
        }
    }

    public void rezervaLocuinta(Locuinta locuinta, Date dataInceput, Date dataSfarsit) {
        // Verificăm dacă perioada este validă
        if (dataInceput.after(dataSfarsit)) {
            System.out.println("Data de început nu poate fi mai mare decât data de sfârșit.");
            return;
        }

        // Creăm o rezervare
        Rezervare rezervare = new Rezervare(
            istoricRezervari.size() + 1, // ID unic pentru rezervare
            dataInceput,
            dataSfarsit,
            this, // Clientul care face rezervarea
            locuinta
        );

        // Adăugăm rezervarea la istoricul clientului
        istoricRezervari.add(rezervare);
        System.out.println("Rezervarea a fost realizată pentru locuința " + locuinta.getTip() + " în " + locuinta.getLocatie() + " de la " + dataInceput + " la " + dataSfarsit + ".");
    }


    public void evalueazaLocuinta(int idEvaluare, Locuinta locuinta, int scor, String comentariu) {
        // Verificăm dacă această locuință a fost rezervată de client
        boolean rezervareGasita = false;
        for (Rezervare rez : istoricRezervari) {
            if (rez.getLocuinta().getId() == locuinta.getId() && rez.isConfirmare()) {
                rezervareGasita = true;
                break;
            }
        }

        if (!rezervareGasita) {
            System.out.println("Nu puteți evalua această locuință deoarece nu aveți o rezervare confirmată pentru ea.");
            return;
        }

        // Verificăm validitatea scorului
        if (scor < 1 || scor > 5) {
            System.out.println("Scorul trebuie să fie între 1 și 5.");
            return;
        }

        // Creăm evaluarea
        Evaluare evaluare = new Evaluare(idEvaluare, this, locuinta, scor, comentariu);
        evaluare.adaugaEvaluare();
        evaluare.vizualizeazaEvaluare();
    }


    public List<Rezervare> getIstoricRezervari() {
        return istoricRezervari;
    }

    public void setIstoricRezervari(List<Rezervare> istoricRezervari) {
        this.istoricRezervari = istoricRezervari;
    }
}