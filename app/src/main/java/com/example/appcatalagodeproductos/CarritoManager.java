package com.example.appcatalagodeproductos;

import java.util.ArrayList;
import java.util.HashMap;

public class CarritoManager {
    private static CarritoManager instance;
    private HashMap<Integer, ItemPedido> itemsCarrito;

    private CarritoManager() {
        itemsCarrito = new HashMap<>();
    }

    public static CarritoManager getInstance() {
        if (instance == null) {
            instance = new CarritoManager();
        }
        return instance;
    }

    // Agregar producto al carrito
    public void agregarProducto(Producto producto) {
        if (itemsCarrito.containsKey(producto.getId())) {
            // Si ya existe, aumentar cantidad
            ItemPedido item = itemsCarrito.get(producto.getId());
            item.setCantidad(item.getCantidad() + 1);
        } else {
            // Si no existe, agregarlo con cantidad 1
            itemsCarrito.put(producto.getId(), new ItemPedido(producto, 1));
        }
    }

    // Eliminar producto del carrito
    public void eliminarProducto(int productoId) {
        itemsCarrito.remove(productoId);
    }

    // Aumentar cantidad de un producto
    public void aumentarCantidad(int productoId) {
        if (itemsCarrito.containsKey(productoId)) {
            ItemPedido item = itemsCarrito.get(productoId);
            item.setCantidad(item.getCantidad() + 1);
        }
    }

    // Disminuir cantidad de un producto
    public void disminuirCantidad(int productoId) {
        if (itemsCarrito.containsKey(productoId)) {
            ItemPedido item = itemsCarrito.get(productoId);
            if (item.getCantidad() > 1) {
                item.setCantidad(item.getCantidad() - 1);
            } else {
                // Si la cantidad es 1, eliminar el producto
                eliminarProducto(productoId);
            }
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
    }

    // Verificar si el carrito está vacío
    public boolean estaVacio() {
        return itemsCarrito.isEmpty();
    }
}