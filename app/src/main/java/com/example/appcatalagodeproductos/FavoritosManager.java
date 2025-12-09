package com.example.appcatalagodeproductos;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public class FavoritosManager {
    private static FavoritosManager instance;
    private static final String PREFS_NAME = "FavoritosPrefs";
    private static final String KEY_FAVORITOS = "favoritos_ids";

    private SharedPreferences prefs;
    private HashSet<Integer> favoritosIds;
    private Gson gson;

    private FavoritosManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        cargarFavoritos();
    }

    public static FavoritosManager getInstance(Context context) {
        if (instance == null) {
            instance = new FavoritosManager(context);
        }
        return instance;
    }

    public static FavoritosManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("FavoritosManager debe inicializarse con Context primero");
        }
        return instance;
    }

    private void cargarFavoritos() {
        String favoritosJson = prefs.getString(KEY_FAVORITOS, null);
        if (favoritosJson != null) {
            Type type = new TypeToken<HashSet<Integer>>(){}.getType();
            favoritosIds = gson.fromJson(favoritosJson, type);
        } else {
            favoritosIds = new HashSet<>();
        }
    }

    private void guardarFavoritos() {
        SharedPreferences.Editor editor = prefs.edit();
        String favoritosJson = gson.toJson(favoritosIds);
        editor.putString(KEY_FAVORITOS, favoritosJson);
        editor.apply();
    }

    public void agregarFavorito(int productoId) {
        favoritosIds.add(productoId);
        guardarFavoritos();
    }

    public void eliminarFavorito(int productoId) {
        favoritosIds.remove(productoId);
        guardarFavoritos();
    }

    public boolean esFavorito(int productoId) {
        return favoritosIds.contains(productoId);
    }

    public void toggleFavorito(int productoId) {
        if (esFavorito(productoId)) {
            eliminarFavorito(productoId);
        } else {
            agregarFavorito(productoId);
        }
    }

    public HashSet<Integer> obtenerFavoritosIds() {
        return new HashSet<>(favoritosIds);
    }

    public int obtenerCantidadFavoritos() {
        return favoritosIds.size();
    }
}