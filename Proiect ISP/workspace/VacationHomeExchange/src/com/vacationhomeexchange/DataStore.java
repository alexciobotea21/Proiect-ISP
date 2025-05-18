package com.vacationhomeexchange;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.List;

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
            return gson.fromJson(reader, typeToken.getType());
        } catch (IOException e) {
            return new java.util.ArrayList<>();
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
