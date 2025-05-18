package com.vacationhomeexchange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class NotificareServiceTest {

    static class TestCase {
        Utilizator utilizator;
        Notificare.TipNotificare tip;
        String mesaj;
        boolean asteptat;
        boolean exceptieAsteptata;
        String descriere;

        TestCase(Utilizator utilizator, Notificare.TipNotificare tip, String mesaj, boolean asteptat, boolean exceptieAsteptata, String descriere) {
            this.utilizator = utilizator;
            this.tip = tip;
            this.mesaj = mesaj;
            this.asteptat = asteptat;
            this.exceptieAsteptata = exceptieAsteptata;
            this.descriere = descriere;
        }

        @Override
        public String toString() {
            return descriere;
        }
    }

    static Stream<TestCase> provideTestCases() {
        Client client = new Client(1, "Gigel", "gigel@email.com", "1234", "client", new ArrayList<>());
        Proprietar proprietar = new Proprietar(2, "Maria", "maria@email.com", "abcd", "proprietar", new ArrayList<>());
        Client faraEmail = new Client(3, "Anonim", null, "xyz", "client", new ArrayList<>());
        String mesajLung = "A".repeat(1000);
        String mesajEmoji = "Salut 👋! Noutăți ✔️🔥🎉 Știri importante în zonă.";

        return Stream.of(
            // Cazuri funcționale de bază:
            new TestCase(client, Notificare.TipNotificare.REZERVARE_CONFIRMATA, "Rezervarea ta a fost aprobată!", true, false, "Client rezervare confirmată"),
            new TestCase(proprietar, Notificare.TipNotificare.MESAJ_NOU, "Ai un mesaj nou!", true, false, "Proprietar mesaj nou"),
            new TestCase(client, Notificare.TipNotificare.ALERTA_IMPORTANTA, "Cutremur în zonă!", true, false, "Client alertă importantă"),
            new TestCase(proprietar, Notificare.TipNotificare.INFORMATIVA, "Update platformă completat.", true, false, "Proprietar informativă"),

            // Validări de input:
            new TestCase(null, Notificare.TipNotificare.MESAJ_NOU, "Fără utilizator", false, true, "Utilizator null - exceptie"),
            new TestCase(client, null, "Fără tip notificare", false, true, "Tip notificare null - exceptie"),
            new TestCase(client, Notificare.TipNotificare.REZERVARE_CONFIRMATA, "", false, true, "Mesaj gol - exceptie"),
            new TestCase(client, Notificare.TipNotificare.REZERVARE_CONFIRMATA, " ", false, true, "Mesaj doar cu spații - exceptie"),
            new TestCase(client, Notificare.TipNotificare.REZERVARE_CONFIRMATA, null, false, true, "Mesaj null - exceptie"),

            // Limite și caractere speciale:
            new TestCase(client, Notificare.TipNotificare.INFORMATIVA, mesajLung, true, false, "Mesaj foarte lung"),
            new TestCase(client, Notificare.TipNotificare.MESAJ_NOU, mesajEmoji, true, false, "Mesaj cu caractere speciale / emoji"),

            // Emailuri invalide:
            new TestCase(faraEmail, Notificare.TipNotificare.INFORMATIVA, "Salut fără email", false, true, "Utilizator cu email null"),
            new TestCase(new Client(4, "EmptyEmail", "", "pass", "client", new ArrayList<>()),
                Notificare.TipNotificare.INFORMATIVA, "Email gol", false, true, "Email gol - exceptie"),
            new TestCase(new Client(5, "WhiteSpaceEmail", "   ", "pass", "client", new ArrayList<>()),
                Notificare.TipNotificare.INFORMATIVA, "Email cu spații", false, true, "Email cu spații - exceptie"),
            new TestCase(new Client(6, "InvalidEmail", "noatsign.com", "pass", "client", new ArrayList<>()),
                Notificare.TipNotificare.INFORMATIVA, "Email fără @", false, true, "Email fără caracter @ - exceptie"),
            new TestCase(new Client(7, "NoDomain", "user@", "pass", "client", new ArrayList<>()),
                Notificare.TipNotificare.INFORMATIVA, "Email fără domeniu", false, true, "Email fără domeniu - exceptie")
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("provideTestCases")
    public void testCreareNotificare(TestCase tc) {
        if (tc.exceptieAsteptata) {
            assertThrows(IllegalArgumentException.class, () -> {
                NotificareService.trimiteNotificare(tc.utilizator, tc.tip, tc.mesaj);
            }, tc.descriere);
        } else {
            boolean rezultat = NotificareService.trimiteNotificare(tc.utilizator, tc.tip, tc.mesaj);
            assertEquals(tc.asteptat, rezultat, tc.descriere);
        }
    }
}
