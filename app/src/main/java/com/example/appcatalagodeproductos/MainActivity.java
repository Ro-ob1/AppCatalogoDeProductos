package com.example.appcatalagodeproductos; // Ajusta el nombre de tu paquete

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnVerCatalogo, btnMiPedido, btnCategorias, btnContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenciar los botones
        btnVerCatalogo = findViewById(R.id.btnVerCatalogo);
        btnMiPedido = findViewById(R.id.btnMiPedido);
        btnCategorias = findViewById(R.id.btnCategorias);
        btnContacto = findViewById(R.id.btnContacto);

        // Configurar listeners (por ahora solo mensajes Toast)
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
                Toast.makeText(MainActivity.this, "Categorías - Próximamente", Toast.LENGTH_SHORT).show();
                // TODO: Navegar a CategoriasActivity
            }
        });

        btnContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Contacto - Próximamente", Toast.LENGTH_SHORT).show();
                // TODO: Navegar a ContactoActivity
            }
        });
    }
}