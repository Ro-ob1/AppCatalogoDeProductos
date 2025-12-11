package com.example.appcatalagodeproductos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CategoriasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategorias;
    private CategoriaAdapter adapter;
    private ArrayList<Categoria> listaCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        recyclerViewCategorias = findViewById(R.id.recyclerViewCategorias);
        recyclerViewCategorias.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCategorias.setHasFixedSize(true);

        cargarCategorias();

        adapter = new CategoriaAdapter(this, listaCategorias, new CategoriaAdapter.OnCategoriaClickListener() {
            @Override
            public void onCategoriaClick(Categoria categoria) {
                abrirCatalogoFiltrado(categoria.getNombre());
            }
        });
        recyclerViewCategorias.setAdapter(adapter);
    }

    private void cargarCategorias() {
        listaCategorias = new ArrayList<>();

        // Categoría: Ropa
        listaCategorias.add(new Categoria(
                "Ropa",
                "Prendas de vestir para todas las ocasiones",
                R.drawable.cat_ropa,
                8  // Cantidad de productos en esta categoría
        ));

        // Categoría: Mochilas
        listaCategorias.add(new Categoria(
                "Mochilas escolares",
                "Mochilas escolares, deportivas y para laptop",
                R.drawable.cat_mochilas,
                6 // Cantidad de productos en esta categoría
        ));
        listaCategorias.add(new Categoria(
                "Bolsos",
                "Perfectos para combinar con cualquier outfit",
                R.drawable.cat_bolsos_mano,
                8
        ));

        // Categoría: Accesorios
        listaCategorias.add(new Categoria(
                "Accesorios & Complementos",
                "Lentes, gorras, relojes y demás accesorios",
                R.drawable.cat_aces,
                15
        ));

    }

    private void abrirCatalogoFiltrado(String categoria) {
        Intent intent = new Intent(CategoriasActivity.this, CatalogoActivity.class);
        intent.putExtra("categoria_filtro", categoria);
        startActivity(intent);
    }
}