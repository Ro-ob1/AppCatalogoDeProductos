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
                R.mipmap.ic_launcher,
                4  // Cantidad de productos en esta categoría
        ));

        // Categoría: Mochilas
        listaCategorias.add(new Categoria(
                "Mochilas",
                "Mochilas escolares, deportivas y para laptop",
                R.mipmap.ic_launcher,
                3  // Cantidad de productos en esta categoría
        ));

        // Categoría: Accesorios
        listaCategorias.add(new Categoria(
                "Accesorios",
                "Complementos y accesorios de moda",
                R.mipmap.ic_launcher,
                4  // Cantidad de productos en esta categoría
        ));
    }

    private void abrirCatalogoFiltrado(String categoria) {
        Intent intent = new Intent(CategoriasActivity.this, CatalogoActivity.class);
        intent.putExtra("categoria_filtro", categoria);
        startActivity(intent);
    }
}