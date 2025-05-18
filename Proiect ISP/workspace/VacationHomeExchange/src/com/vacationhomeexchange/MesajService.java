package com.vacationhomeexchange;

import com.google.gson.reflect.TypeToken;
import java.util.*;

public class MesajService {
    private static final DataStore<Mesaj> mesajStore =
        new DataStore<>("mesaje.json", new TypeToken<List<Mesaj>>() {});
    private static final DataStore<Notificare> notificareStore =
        new DataStore<>("notificari.json", new TypeToken<List<Notificare>>() {});

    public static void trimiteMesaj(Utilizator expeditor, Utilizator destinatar, String continut) {
        List<Mesaj> mesaje = mesajStore.load();
        int newId = mesaje.size() + 1;

  
        Mesaj mesaj = new Mesaj(newId, expeditor.getEmail(), destinatar.getEmail(), continut, new Date());
        mesaje.add(mesaj);
        mesajStore.save(mesaje);

 
        Notificare notificare = new Notificare(
            "Mesaj nou de la " + expeditor.getNume(),
            new Date(),
            Notificare.TipNotificare.MESAJ_NOU,
            destinatar.getEmail()
        );

        List<Notificare> notificari = notificareStore.load();
        notificari.add(notificare);
        notificareStore.save(notificari);


        destinatar.adaugaNotificare(notificare);
        UserService.update(destinatar);

       
        System.out.println("✅ Mesaj trimis de la " + expeditor.getEmail() + " către " + destinatar.getEmail());
    }
}
