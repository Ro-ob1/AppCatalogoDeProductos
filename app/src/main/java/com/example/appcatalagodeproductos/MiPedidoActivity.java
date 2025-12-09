package com.example.appcatalagodeproductos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Locale;

public class MiPedidoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPedido;
    private PedidoAdapter adapter;
    private TextView tvTotalPedido, tvCantidadProductos;
    private Button btnVaciarPedido, btnEnviarPedido, btnIrACatalogo;
    private LinearLayout layoutCarritoVacio, layoutFooterPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_pedido);

        // Inicializar vistas
        recyclerViewPedido = findViewById(R.id.recyclerViewPedido);
        tvTotalPedido = findViewById(R.id.tvTotalPedido);
        tvCantidadProductos = findViewById(R.id.tvCantidadProductos);
        btnVaciarPedido = findViewById(R.id.btnVaciarPedido);
        btnEnviarPedido = findViewById(R.id.btnEnviarPedido);
        btnIrACatalogo = findViewById(R.id.btnIrACatalogo);
        layoutCarritoVacio = findViewById(R.id.layoutCarritoVacio);
        layoutFooterPedido = findViewById(R.id.layoutFooterPedido);

        // Configurar RecyclerView
        recyclerViewPedido.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPedido.setHasFixedSize(true);

        // Configurar adapter
        adapter = new PedidoAdapter(this, CarritoManager.getInstance(this).obtenerItems(), new PedidoAdapter.OnCantidadChangeListener() {
            @Override
            public void onCantidadChanged() {
                actualizarInterfaz();
            }
        });
        recyclerViewPedido.setAdapter(adapter);

        // Configurar botones
        configurarBotones();

        // Actualizar interfaz
        actualizarInterfaz();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista cuando se regrese a esta actividad
        adapter.actualizarLista(CarritoManager.getInstance(this).obtenerItems());
        actualizarInterfaz();
    }

    private void configurarBotones() {
        // Botón Vaciar Pedido
        btnVaciarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoVaciar();
            }
        });

        // Botón Enviar Pedido
        btnEnviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CarritoManager.getInstance().estaVacio()) {
                    Intent intent = new Intent(MiPedidoActivity.this, ResumenPedidoActivity.class);
                    startActivity(intent);
                }
            }
        });

        // Botón Ir al Catálogo (cuando está vacío)
        btnIrACatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiPedidoActivity.this, CatalogoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void actualizarInterfaz() {
        if (CarritoManager.getInstance(this).estaVacio()) {
            // Mostrar mensaje de carrito vacío
            recyclerViewPedido.setVisibility(View.GONE);
            layoutFooterPedido.setVisibility(View.GONE);
            layoutCarritoVacio.setVisibility(View.VISIBLE);
        } else {
            // Mostrar productos
            recyclerViewPedido.setVisibility(View.VISIBLE);
            layoutFooterPedido.setVisibility(View.VISIBLE);
            layoutCarritoVacio.setVisibility(View.GONE);

            // Actualizar cantidad de productos
            int cantidadTotal = CarritoManager.getInstance(this).obtenerCantidadTotal();
            tvCantidadProductos.setText(cantidadTotal + (cantidadTotal == 1 ? " producto" : " productos"));

            // Actualizar total
            double total = CarritoManager.getInstance(this).calcularTotal();
            tvTotalPedido.setText(String.format(Locale.getDefault(), "$%.2f", total));
        }
    }

    private void mostrarDialogoVaciar() {
        new AlertDialog.Builder(this)
                .setTitle("Vaciar Carrito")
                .setMessage("¿Estás seguro de que deseas vaciar todo el carrito?")
                .setPositiveButton("Sí, vaciar", (dialog, which) -> {
                    CarritoManager.getInstance().vaciarCarrito();
                    adapter.actualizarLista(CarritoManager.getInstance().obtenerItems());
                    actualizarInterfaz();
                    Toast.makeText(MiPedidoActivity.this, "Carrito vaciado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
        CarritoManager.getInstance(MiPedidoActivity.this).vaciarCarrito();
        adapter.actualizarLista(CarritoManager.getInstance(MiPedidoActivity.this).obtenerItems());
    }
}