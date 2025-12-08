package com.example.appcatalagodeproductos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;

public class CatalogoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductoAdapter adapter;
    private HashMap<String, HashMap<Integer, Producto>> productosPorCategoria;
    private ArrayList<Producto> listaProductos;
    private ArrayList<Producto> listaProductosFiltrada;
    private SearchView searchView;
    private Spinner spinnerCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        recyclerView = findViewById(R.id.recyclerViewProductos);
        searchView = findViewById(R.id.searchViewProductos);
        spinnerCategorias = findViewById(R.id.spinnerCategorias);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        cargarProductos();

        adapter = new ProductoAdapter(this, listaProductosFiltrada, new ProductoAdapter.OnProductoClickListener() {
            @Override
            public void onProductoClick(Producto producto) {
                // Navegar a DetalleProductoActivity
                Intent intent = new Intent(CatalogoActivity.this, DetalleProductoActivity.class);
                intent.putExtra("producto_id", producto.getId());
                intent.putExtra("producto_nombre", producto.getNombre());
                intent.putExtra("producto_descripcion", producto.getDescripcion());
                intent.putExtra("producto_precio", producto.getPrecio());
                intent.putExtra("producto_categoria", producto.getCategoria());
                intent.putExtra("producto_imagen", producto.getImagenResId());
                intent.putExtra("producto_disponible", producto.isDisponible());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        configurarSpinnerCategorias();
        configurarBusqueda();
    }

    private void cargarProductos() {
        productosPorCategoria = new HashMap<>();
        listaProductos = new ArrayList<>();

        // ========== CATEGORÍA: ROPA ==========
        HashMap<Integer, Producto> productosRopa = new HashMap<>();

        productosRopa.put(1, new Producto(
                1,
                "Playera Deportiva",
                "Playera de algodón para ejercicio, cómoda y transpirable",
                199.50,
                "Ropa",
                ImagenesProductos.PLAYERA_DEPORTIVA,  // ← Usando constante
                true
        ));

        productosRopa.put(2, new Producto(
                2,
                "Pantalón Mezclilla",
                "Pantalón de mezclilla corte recto, resistente y moderno",
                399.00,
                "Ropa",
                ImagenesProductos.PANTALON_MEZCLILLA,  // ← Usando constante
                true
        ));

        productosRopa.put(3, new Producto(
                3,
                "Sudadera con Capucha",
                "Sudadera cómoda y abrigadora, perfecta para el invierno",
                349.99,
                "Ropa",
                ImagenesProductos.SUDADERA_CAPUCHA,  // ← Usando constante
                true
        ));

        productosRopa.put(4, new Producto(
                4,
                "Chamarra Deportiva",
                "Chamarra ligera resistente al viento",
                599.00,
                "Ropa",
                ImagenesProductos.CHAMARRA_DEPORTIVA,  // ← Usando constante
                true
        ));

        productosPorCategoria.put("Ropa", productosRopa);


        // ========== CATEGORÍA: MOCHILAS ==========
        HashMap<Integer, Producto> productosMochilas = new HashMap<>();

        productosMochilas.put(5, new Producto(
                5,
                "Mochila Escolar",
                "Mochila resistente con múltiples compartimientos para estudiantes",
                299.99,
                "Mochilas",
                ImagenesProductos.MOCHILA_ESCOLAR,  // ← Usando constante
                true
        ));

        productosMochilas.put(6, new Producto(
                6,
                "Mochila Laptop",
                "Mochila para laptop de 15 pulgadas con acolchado protector",
                499.99,
                "Mochilas",
                ImagenesProductos.MOCHILA_LAPTOP,  // ← Usando constante
                true
        ));

        productosMochilas.put(7, new Producto(
                7,
                "Mochila Deportiva",
                "Mochila espaciosa para el gimnasio y actividades deportivas",
                449.00,
                "Mochilas",
                ImagenesProductos.MOCHILA_DEPORTIVA,  // ← Usando constante
                true
        ));

        productosPorCategoria.put("Mochilas", productosMochilas);


        // ========== CATEGORÍA: ACCESORIOS ==========
        HashMap<Integer, Producto> productosAccesorios = new HashMap<>();

        productosAccesorios.put(8, new Producto(
                8,
                "Gorra Casual",
                "Gorra ajustable de varios colores, estilo urbano",
                149.99,
                "Accesorios",
                ImagenesProductos.GORRA_CASUAL,  // ← Usando constante
                true
        ));

        productosAccesorios.put(9, new Producto(
                9,
                "Lentes de Sol",
                "Lentes con protección UV400, diseño moderno",
                249.00,
                "Accesorios",
                ImagenesProductos.LENTES_SOL,  // ← Usando constante
                true
        ));

        productosAccesorios.put(10, new Producto(
                10,
                "Reloj Digital",
                "Reloj deportivo resistente al agua, con cronómetro",
                599.00,
                "Accesorios",
                ImagenesProductos.RELOJ_DIGITAL,  // ← Usando constante
                true
        ));

        productosAccesorios.put(11, new Producto(
                11,
                "Cinturón de Cuero",
                "Cinturón elegante de cuero genuino",
                279.00,
                "Accesorios",
                ImagenesProductos.CINTURON_CUERO,  // ← Usando constante
                true
        ));

        productosPorCategoria.put("Accesorios", productosAccesorios);


        // Convertir todos los productos a una lista única
        for (HashMap<Integer, Producto> categoria : productosPorCategoria.values()) {
            for (Producto producto : categoria.values()) {
                listaProductos.add(producto);
            }
        }

        listaProductosFiltrada = new ArrayList<>(listaProductos);
    }

    private void configurarSpinnerCategorias() {
        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("Todas");
        categorias.add("Ropa");
        categorias.add("Mochilas");
        categorias.add("Accesorios");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categorias
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(spinnerAdapter);

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoriaSeleccionada = categorias.get(position);
                filtrarPorCategoria(categoriaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void configurarBusqueda() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrarProductos(newText);
                return true;
            }
        });
    }

    private void filtrarPorCategoria(String categoria) {
        if (categoria.equals("Todas")) {
            listaProductosFiltrada = new ArrayList<>(listaProductos);
        } else {
            listaProductosFiltrada = new ArrayList<>();
            HashMap<Integer, Producto> productosCategoria = productosPorCategoria.get(categoria);
            if (productosCategoria != null) {
                for (Producto producto : productosCategoria.values()) {
                    listaProductosFiltrada.add(producto);
                }
            }
        }
        adapter.actualizarLista(listaProductosFiltrada);
    }

    private void filtrarProductos(String texto) {
        ArrayList<Producto> listaFiltrada = new ArrayList<>();

        for (Producto producto : listaProductos) {
            if (producto.getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                    producto.getCategoria().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(producto);
            }
        }

        listaProductosFiltrada = listaFiltrada;
        adapter.actualizarLista(listaProductosFiltrada);
    }

    public void agregarProductoACategoria(String categoria, Producto producto) {
        HashMap<Integer, Producto> productosCategoria = productosPorCategoria.get(categoria);
        if (productosCategoria != null) {
            productosCategoria.put(producto.getId(), producto);
            listaProductos.add(producto);
            listaProductosFiltrada = new ArrayList<>(listaProductos);
            adapter.actualizarLista(listaProductosFiltrada);
        }
    }

    public Producto obtenerProducto(String categoria, int idProducto) {
        HashMap<Integer, Producto> productosCategoria = productosPorCategoria.get(categoria);
        if (productosCategoria != null) {
            return productosCategoria.get(idProducto);
        }
        return null;
    }

    public ArrayList<Producto> obtenerProductosDeCategoria(String categoria) {
        ArrayList<Producto> productos = new ArrayList<>();
        HashMap<Integer, Producto> productosCategoria = productosPorCategoria.get(categoria);
        if (productosCategoria != null) {
            for (Producto producto : productosCategoria.values()) {
                productos.add(producto);
            }
        }
        return productos;
    }
}