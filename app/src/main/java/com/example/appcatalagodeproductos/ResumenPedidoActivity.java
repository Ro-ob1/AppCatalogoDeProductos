package com.example.appcatalagodeproductos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Locale;

public class ResumenPedidoActivity extends AppCompatActivity {

    private LinearLayout layoutListaProductos;
    private TextView tvSubtotalResumen, tvEnvioResumen, tvTotalResumen;
    private Button btnConfirmarPedido, btnCancelar;

    private ArrayList<ItemPedido> listaItems;
    private double subtotal;
    private double costoEnvio = 0.0; // Envío gratis
    private double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_pedido);

        // Inicializar vistas
        layoutListaProductos = findViewById(R.id.layoutListaProductos);
        tvSubtotalResumen = findViewById(R.id.tvSubtotalResumen);
        tvEnvioResumen = findViewById(R.id.tvEnvioResumen);
        tvTotalResumen = findViewById(R.id.tvTotalResumen);
        btnConfirmarPedido = findViewById(R.id.btnConfirmarPedido);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Obtener datos del carrito
        listaItems = CarritoManager.getInstance(this).obtenerItems();

        // Verificar si el carrito está vacío
        if (listaItems.isEmpty()) {
            Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Calcular totales
        calcularTotales();

        // Mostrar productos
        mostrarProductos();

        // Mostrar resumen de costos
        mostrarResumenCostos();

        // Configurar botones
        configurarBotones();
    }

    private void calcularTotales() {
        subtotal = CarritoManager.getInstance(this).calcularTotal();
        total = subtotal + costoEnvio;
    }

    private void mostrarProductos() {
        layoutListaProductos.removeAllViews();

        for (ItemPedido item : listaItems) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_resumen_producto, layoutListaProductos, false);

            ImageView ivProducto = itemView.findViewById(R.id.ivProductoResumen);
            TextView tvNombre = itemView.findViewById(R.id.tvNombreResumen);
            TextView tvCantidadPrecio = itemView.findViewById(R.id.tvCantidadPrecioResumen);
            TextView tvSubtotalItem = itemView.findViewById(R.id.tvSubtotalItemResumen);

            Producto producto = item.getProducto();

            ivProducto.setImageResource(producto.getImagenResId());
            tvNombre.setText(producto.getNombre());
            tvCantidadPrecio.setText(String.format(Locale.getDefault(),
                    "%d x $%.2f", item.getCantidad(), producto.getPrecio()));
            tvSubtotalItem.setText(String.format(Locale.getDefault(), "$%.2f", item.getSubtotal()));

            layoutListaProductos.addView(itemView);

            // Agregar línea divisoria entre productos (excepto el último)
            if (listaItems.indexOf(item) < listaItems.size() - 1) {
                View divider = new View(this);
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2
                ));
                divider.setBackgroundColor(getResources().getColor(R.color.divider));
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) divider.getLayoutParams();
                params.setMargins(0, 12, 0, 12);
                divider.setLayoutParams(params);
                layoutListaProductos.addView(divider);
            }
        }
    }

    private void mostrarResumenCostos() {
        tvSubtotalResumen.setText(String.format(Locale.getDefault(), "$%.2f", subtotal));

        if (costoEnvio == 0) {
            tvEnvioResumen.setText("Gratis");
            tvEnvioResumen.setTextColor(getResources().getColor(R.color.success));
        } else {
            tvEnvioResumen.setText(String.format(Locale.getDefault(), "$%.2f", costoEnvio));
            tvEnvioResumen.setTextColor(getResources().getColor(R.color.text_primary));
        }

        tvTotalResumen.setText(String.format(Locale.getDefault(), "$%.2f", total));
    }

    private void configurarBotones() {
        // Botón Confirmar Pedido
        btnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });

        // Botón Cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Crear vista personalizada para el diálogo
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_pedido_exitoso, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        Button btnAceptar = dialogView.findViewById(R.id.btnAceptarDialogo);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vaciar el carrito
                CarritoManager.getInstance(ResumenPedidoActivity.this).vaciarCarrito();

                dialog.dismiss();

                // Regresar al menú principal
                Intent intent = new Intent(ResumenPedidoActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
    }
}