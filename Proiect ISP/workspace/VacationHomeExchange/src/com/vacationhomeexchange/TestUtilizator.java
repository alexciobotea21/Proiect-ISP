package com.vacationhomeexchange;

public class TestUtilizator {
    public static void main(String[] args) {
        // Creăm un utilizator nou cu date goale
        Utilizator utilizator = new Utilizator(1, "", "", "", "");

        // Testăm înregistrarea (datele vor fi introduse de la tastatură)
        utilizator.inregistrare();

        // Testăm autentificarea (datele vor fi introduse de la tastatură)
        utilizator.autentificare();
    }
}
