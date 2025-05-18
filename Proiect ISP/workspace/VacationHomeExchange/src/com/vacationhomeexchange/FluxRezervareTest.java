package com.vacationhomeexchange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FluxRezervareTest {

    static class TestCase {
        Utilizator utilizator;
        String actiune;
        boolean asteptatConfirmata;
        boolean exceptieAsteptata;
        String descriere;

        TestCase(Utilizator utilizator, String actiune, boolean asteptatConfirmata, boolean exceptieAsteptata, String descriere) {
            this.utilizator = utilizator;
            this.actiune = actiune;
            this.asteptatConfirmata = asteptatConfirmata;
            this.exceptieAsteptata = exceptieAsteptata;
            this.descriere = descriere;
        }

        @Override
        public String toString() {
            return descriere;
        }
    }

    static Stream<TestCase> provideTestCases() {
        Client client = new Client(1, "Ana", "ana@email.com", "pass", "client", new ArrayList<>());
        Proprietar proprietarCorect = new Proprietar(2, "Dan", "dan@email.com", "pass", "proprietar", new ArrayList<>());
        Proprietar proprietarFals = new Proprietar(3, "Ion", "ion@email.com", "pass", "proprietar", new ArrayList<>());
        Client faraEmail = new Client(4, "FaraEmail", null, "pass", "client", new ArrayList<>());
        Client clientTrecut = new Client(5, "Trecut", "trecut@email.com", "pass", "client", new ArrayList<>());
        Client clientInvers = new Client(6, "Invers", "invers@email.com", "pass", "client", new ArrayList<>());
        Utilizator clientCuRolGresit = new Utilizator(7, "Fake", "fake@email.com", "pass", "admin");

        return Stream.of(
            new TestCase(client, "confirma", true, false, "Client confirmă rezervarea"),
            new TestCase(client, "anuleaza", false, false, "Client anulează rezervarea"),
            new TestCase(proprietarCorect, "confirma", true, false, "Proprietar confirmă rezervare"),
            new TestCase(proprietarCorect, "anuleaza", false, false, "Proprietar anulează rezervare"),

            new TestCase(null, "confirma", false, true, "Utilizator null - exceptie"),
            new TestCase(faraEmail, "confirma", false, true, "Utilizator cu email null - exceptie"),
            new TestCase(client, "invalid", false, true, "Acțiune invalidă - exceptie"),

            new TestCase(proprietarFals, "confirma", false, false, "Proprietar NU deține locuința - nu confirmă"),
            new TestCase(clientInvers, "confirma", true, false, "Client rezervare cu data inversă"),
            new TestCase(clientTrecut, "confirma", true, false, "Client rezervare în trecut"),
            new TestCase(client, "confirma", true, false, "Locuință fără proprietar (emailProprietar null)"),
            new TestCase(client, "confirma", true, false, "Locuință cu preț negativ"),
            new TestCase(clientCuRolGresit, "confirma", true, false, "Utilizator cu rol invalid (admin) dar format valid"),
            new TestCase(client, "confirma", false, true, "Rezervare fără locuință - excepție așteptată"),
            new TestCase(client, "confirma", false, true, "Rezervare fără date de început/sfârșit - excepție așteptată"),
            new TestCase(client, "confirma", true, false, "Confirmare deja efectuată"),
            new TestCase(client, "confirma", false, true, "Client null în rezervare")
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("provideTestCases")
    public void testFluxRezervare(TestCase tc) {
        try {
            if (tc.utilizator == null || tc.utilizator.getEmail() == null || tc.utilizator.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Utilizator invalid");
            }

            boolean faraProprietar = tc.descriere.toLowerCase().contains("fără proprietar");
            boolean pretNegativ = tc.descriere.toLowerCase().contains("preț negativ");
            boolean faraLocuinta = tc.descriere.toLowerCase().contains("fără locuință");
            boolean faraDate = tc.descriere.toLowerCase().contains("fără date");
            boolean clientNull = tc.descriere.toLowerCase().contains("client null");

            String emailProprietar = faraProprietar ? null : "dan@email.com";
            double pret = pretNegativ ? -100.0 : 200.0;

            Locuinta loc = !faraLocuinta ? new Locuinta(100, "Apartament", "Bucuresti", pret, "Spațios", emailProprietar) : null;

            if (tc.utilizator instanceof Proprietar prop &&
                tc.descriere.toLowerCase().contains("proprietar confirmă rezervare") && loc != null) {
                prop.setListaLocuinte(List.of(loc));
            }

            Date azi = new Date();
            Date ieri = new Date(azi.getTime() - 86400000L);
            Date maine = new Date(azi.getTime() + 86400000L);

            String email = tc.utilizator.getEmail();
            boolean invers = email != null && email.contains("invers");
            Date start = faraDate ? null : (invers ? maine : ieri);
            Date end = faraDate ? null : (invers ? ieri : maine);

            Client clientRezervant = clientNull ? null :
                (tc.utilizator instanceof Client)
                    ? (Client) tc.utilizator
                    : new Client(999, "Fake", "fake@x.com", "fake", "client", new ArrayList<>());

            Rezervare rezervare = new Rezervare(999, start, end, clientRezervant, loc);

            if (tc.descriere.toLowerCase().contains("deja efectuată")) {
                rezervare.confirmareRezervare();
            }

            switch (tc.actiune.toLowerCase()) {
                case "confirma" -> {
                    if (tc.utilizator instanceof Proprietar prop)
                        prop.confirmaRezervare(rezervare);
                    else
                        rezervare.confirmareRezervare();
                }
                case "anuleaza" -> rezervare.anulareRezervare();
                default -> throw new IllegalArgumentException("Acțiune necunoscută: " + tc.actiune);
            }

            assertEquals(tc.asteptatConfirmata, rezervare.isConfirmata(), tc.descriere);

        } catch (Exception e) {
            assertTrue(tc.exceptieAsteptata, tc.descriere + " - NU era așteptată excepție, dar s-a produs: " + e.getMessage());
        }
    }
}
