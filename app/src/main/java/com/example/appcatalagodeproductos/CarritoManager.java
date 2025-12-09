package com.example.appcatalagodeproductos;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;

public class CarritoManager {
    private static CarritoManager instance;
    private HashMap<Integer, ItemPedido> itemsCarrito;
    private CarritoPreferences preferences;
    private Context context;

    private CarritoManager(Context context) {
        this.context = context.getApplicationContext();
        this.preferences = new CarritoPreferences(this.context);
        // Cargar carrito guardado
        this.itemsCarrito = preferences.cargarCarrito();
    }

    public static CarritoManager getInstance(Context context) {
        if (instance == null) {
            instance = new CarritoManager(context);
        }
        return instance;
    }

    // Método de compatibilidad (sin Context)
    public static CarritoManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("CarritoManager debe inicializarse con Context primero");
        }
        return instance;
    }

    // Agregar producto al carrito
    public void agregarProducto(Producto producto) {
        if (itemsCarrito.containsKey(producto.getId())) {
            ItemPedido item = itemsCarrito.get(producto.getId());
            item.setCantidad(item.getCantidad() + 1);
        } else {
            itemsCarrito.put(producto.getId(), new ItemPedido(producto, 1));
        }
        guardarCarrito();
    }

    // Eliminar producto del carrito
    public void eliminarProducto(int productoId) {
        itemsCarrito.remove(productoId);
        guardarCarrito();
    }

    // Aumentar cantidad de un producto
    public void aumentarCantidad(int productoId) {
        if (itemsCarrito.containsKey(productoId)) {
            ItemPedido item = itemsCarrito.get(productoId);
            item.setCantidad(item.getCantidad() + 1);
            guardarCarrito();
        }
    }

    // Disminuir cantidad de un producto
    public void disminuirCantidad(int productoId) {
        if (itemsCarrito.containsKey(productoId)) {
            ItemPedido item = itemsCarrito.get(productoId);
            if (item.getCantidad() > 1) {
                item.setCantidad(item.getCantidad() - 1);
            } else {
                eliminarProducto(productoId);
            }
            guardarCarrito();
        }
    }

    // Obtener todos los items del carrito
    public ArrayList<ItemPedido> obtenerItems() {
        return new ArrayList<>(itemsCarrito.values());
    }

    // Calcular total del carrito
    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itemsCarrito.values()) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Obtener cantidad total de productos
    public int obtenerCantidadTotal() {
        int cantidad = 0;
        for (ItemPedido item : itemsCarrito.values()) {
            cantidad += item.getCantidad();
        }
        return cantidad;
    }

    // Vaciar carrito
    public void vaciarCarrito() {
        itemsCarrito.clear();
        guardarCarrito();
    }

    // Verificar si el carrito está vacío
    public boolean estaVacio() {
        return itemsCarrito.isEmpty();
    }

    // Guardar carrito en SharedPreferences
    private void guardarCarrito() {
        preferences.guardarCarrito(itemsCarrito);
    }
}