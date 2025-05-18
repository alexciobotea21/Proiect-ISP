package com.vacationhomeexchange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EvaluareServiceTest {

    static class TestCase {
        Utilizator utilizator;
        Locuinta locuinta;
        int scor;
        String comentariu;
        boolean exceptieAsteptata;
        String descriere;

        TestCase(Utilizator utilizator, Locuinta locuinta, int scor, String comentariu, boolean exceptieAsteptata, String descriere) {
            this.utilizator = utilizator;
            this.locuinta = locuinta;
            this.scor = scor;
            this.comentariu = comentariu;
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
        Locuinta locuinta = new Locuinta(101, "Apartament", "Cluj", 300.0, "Central", "dan@email.com");

        String comentariuLung = "Foarte bun. ".repeat(100); // peste 1000 caractere

        return Stream.of(
            new TestCase(client, locuinta, 5, "Excelent!", false, "Client cu evaluare corectă"),
            new TestCase(new Client(999, "Dan", "dan@email.com", "pass", "client", new ArrayList<>()), locuinta, 3, "Acceptabil", false, "Client (fost proprietar) cu evaluare validă"),
            new TestCase(client, locuinta, 1, "Minim", false, "Scor minim permis"),
            new TestCase(client, locuinta, 5, "Maxim", false, "Scor maxim permis"),
            new TestCase(client, locuinta, 0, "Prea mic", true, "Scor sub limită - excepție"),
            new TestCase(client, locuinta, 6, "Prea mare", true, "Scor peste limită - excepție"),
            new TestCase(client, locuinta, 3, "", true, "Comentariu gol - excepție"),
            new TestCase(client, locuinta, 3, "   ", true, "Comentariu doar cu spații - excepție"),
            new TestCase(client, locuinta, 3, comentariuLung, false, "Comentariu foarte lung - valid"),
            new TestCase(null, locuinta, 3, "Valid", true, "Utilizator null - excepție"),
            new TestCase(client, null, 3, "Valid", true, "Locuință null - excepție"),
            new TestCase(client, locuinta, Integer.MIN_VALUE, "Bizar", true, "Scor Integer.MIN_VALUE - excepție"),
            new TestCase(client, locuinta, Integer.MAX_VALUE, "Bizar", true, "Scor Integer.MAX_VALUE - excepție")
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("provideTestCases")
    public void testCreareEvaluare(TestCase tc) {
        try {
            Evaluare evaluare = new Evaluare(0, (Client) tc.utilizator, tc.locuinta, tc.scor, tc.comentariu);
            EvaluareService.adaugaEvaluare(evaluare);
            assertFalse(tc.exceptieAsteptata, tc.descriere);
        } catch (Exception e) {
            assertTrue(tc.exceptieAsteptata, tc.descriere + " - NU era așteptată excepție, dar s-a produs: " + e.getMessage());
        }
    }
}
