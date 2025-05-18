package com.vacationhomeexchange;

import com.google.gson.Gson;

public class GsonTest {
    public static void main(String[] args) {
        Gson gson = new Gson();

        String json = gson.toJson("Salut lume!");
        System.out.println("JSON generat: " + json);

        String back = gson.fromJson(json, String.class);
        System.out.println("JSON convertit înapoi: " + back);
    }
}
