package com.example.appcatalagodeproductos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnVerCatalogo, btnMiPedido, btnCategorias, btnContacto;
    private TextView tvBadgeCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar CarritoManager
        CarritoManager.getInstance(this);

        // Referenciar vistas
        btnVerCatalogo = findViewById(R.id.btnVerCatalogo);
        btnMiPedido = findViewById(R.id.btnMiPedido);
        btnCategorias = findViewById(R.id.btnCategorias);
        btnContacto = findViewById(R.id.btnContacto);
        tvBadgeCarrito = findViewById(R.id.tvBadgeCarrito);

        configurarBotones();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarBadgeCarrito();
    }

    private void configurarBotones() {
        btnVerCatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CatalogoActivity.class);
                startActivity(intent);
            }
        });

        btnMiPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MiPedidoActivity.class);
                startActivity(intent);
            }
        });

        btnCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriasActivity.class);
                startActivity(intent);
            }
        });

        btnContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void actualizarBadgeCarrito() {
        int cantidad = CarritoManager.getInstance(this).obtenerCantidadTotal();

        if (cantidad > 0) {
            tvBadgeCarrito.setVisibility(View.VISIBLE);
            tvBadgeCarrito.setText(String.valueOf(cantidad));
        } else {
            tvBadgeCarrito.setVisibility(View.GONE);
        }
    }
}