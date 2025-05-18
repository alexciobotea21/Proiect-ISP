package com.vacationhomeexchange;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class DataStore<T> {
    private final String filePath;
    private final Gson gson = new Gson();
    private final TypeToken<List<T>> typeToken;

    public DataStore(String filePath, TypeToken<List<T>> typeToken) {
        this.filePath = filePath;
        this.typeToken = typeToken;
    }

    public List<T> load() {
        try (Reader reader = new FileReader(filePath)) {
            List<T> lista = gson.fromJson(reader, typeToken.getType());
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }


    public void save(List<T> data) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("❌ Eroare la salvare: " + e.getMessage());
        }
    }
}
