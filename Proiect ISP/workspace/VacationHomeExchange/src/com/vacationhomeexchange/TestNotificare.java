package com.vacationhomeexchange;

import java.util.Date;

public class TestNotificare {
    public static void main(String[] args) {
        // Creează o notificare
        Notificare notificare = new Notificare(1, "Rezervarea a fost confirmată.", new Date(), "Rezervare");

        // Testează metoda de trimitere notificare
        notificare.trimiteNotificare();
    }
}
