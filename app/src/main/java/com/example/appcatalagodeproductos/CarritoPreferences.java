package com.example.appcatalagodeproductos;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;

public class CarritoPreferences {
    private static final String PREFS_NAME = "CarritoPrefs";
    private static final String KEY_CARRITO = "carrito";
    private static final String KEY_PRIMERA_VEZ = "primera_vez";

    private SharedPreferences prefs;
    private Gson gson;

    public CarritoPreferences(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Guardar carrito
    public void guardarCarrito(HashMap<Integer, ItemPedido> carrito) {
        SharedPreferences.Editor editor = prefs.edit();
        String carritoJson = gson.toJson(carrito);
        editor.putString(KEY_CARRITO, carritoJson);
        editor.apply();
    }

    // Cargar carrito
    public HashMap<Integer, ItemPedido> cargarCarrito() {
        String carritoJson = prefs.getString(KEY_CARRITO, null);
        if (carritoJson != null) {
            Type type = new TypeToken<HashMap<Integer, ItemPedido>>(){}.getType();
            return gson.fromJson(carritoJson, type);
        }
        return new HashMap<>();
    }

    // Verificar si es la primera vez
    public boolean esPrimeraVez() {
        return prefs.getBoolean(KEY_PRIMERA_VEZ, true);
    }

    // Marcar que ya no es la primera vez
    public void marcarNoEsPrimeraVez() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_PRIMERA_VEZ, false);
        editor.apply();
    }
}