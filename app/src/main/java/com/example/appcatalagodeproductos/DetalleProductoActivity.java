package com.example.appcatalagodeproductos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class DetalleProductoActivity extends AppCompatActivity {

    private ImageView ivProductoDetalle;
    private TextView tvNombreDetalle, tvCategoriaDetalle, tvPrecioDetalle;
    private TextView tvDescripcionDetalle, tvDisponibilidadDetalle;
    private Button btnAgregarAlPedido, btnAgregarFavoritos, btnVolverCatalogo;

    private Producto productoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        // Inicializar vistas
        ivProductoDetalle = findViewById(R.id.ivProductoDetalle);
        tvNombreDetalle = findViewById(R.id.tvNombreDetalle);
        tvCategoriaDetalle = findViewById(R.id.tvCategoriaDetalle);
        tvPrecioDetalle = findViewById(R.id.tvPrecioDetalle);
        tvDescripcionDetalle = findViewById(R.id.tvDescripcionDetalle);
        tvDisponibilidadDetalle = findViewById(R.id.tvDisponibilidadDetalle);
        btnAgregarAlPedido = findViewById(R.id.btnAgregarAlPedido);
        btnAgregarFavoritos = findViewById(R.id.btnAgregarFavoritos);
        btnVolverCatalogo = findViewById(R.id.btnVolverCatalogo);

        // Obtener datos del producto desde el Intent
        recibirDatosProducto();

        // Mostrar información del producto
        if (productoActual != null) {
            mostrarDetallesProducto();
        }

        // Configurar botones
        configurarBotones();
    }

    private void recibirDatosProducto() {
        Intent intent = getIntent();
        if (intent != null) {
            int id = intent.getIntExtra("producto_id", -1);
            String nombre = intent.getStringExtra("producto_nombre");
            String descripcion = intent.getStringExtra("producto_descripcion");
            double precio = intent.getDoubleExtra("producto_precio", 0.0);
            String categoria = intent.getStringExtra("producto_categoria");
            int imagenResId = intent.getIntExtra("producto_imagen", ImagenesProductos.IMAGEN_DEFAULT);
            boolean disponible = intent.getBooleanExtra("producto_disponible", true);

            if (id != -1) {
                productoActual = new Producto(id, nombre, descripcion, precio, categoria, imagenResId, disponible);
            }
        }
    }

    private void mostrarDetallesProducto() {
        tvNombreDetalle.setText(productoActual.getNombre());
        tvCategoriaDetalle.setText(productoActual.getCategoria());
        tvPrecioDetalle.setText(String.format(Locale.getDefault(), "$%.2f", productoActual.getPrecio()));
        tvDescripcionDetalle.setText(productoActual.getDescripcion());
        ivProductoDetalle.setImageResource(productoActual.getImagenResId());

        // Mostrar disponibilidad
        if (productoActual.isDisponible()) {
            tvDisponibilidadDetalle.setText("En stock");
            tvDisponibilidadDetalle.setTextColor(getResources().getColor(R.color.success));
            btnAgregarAlPedido.setEnabled(true);
        } else {
            tvDisponibilidadDetalle.setText("Agotado");
            tvDisponibilidadDetalle.setTextColor(getResources().getColor(R.color.error));
            btnAgregarAlPedido.setEnabled(false);
            btnAgregarAlPedido.setText("Producto Agotado");
        }
    }

    private void configurarBotones() {
        // Botón Agregar al Pedido
        btnAgregarAlPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productoActual != null) {
                    CarritoManager.getInstance().agregarProducto(productoActual);
                    Toast.makeText(DetalleProductoActivity.this,
                            productoActual.getNombre() + " agregado al pedido",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón Agregar a Favoritos
        btnAgregarFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productoActual != null) {
                    Toast.makeText(DetalleProductoActivity.this,
                            productoActual.getNombre() + " agregado a favoritos",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón Volver al Catálogo
        btnVolverCatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}