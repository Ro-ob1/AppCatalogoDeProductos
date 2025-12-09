package com.example.appcatalagodeproductos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ContactoActivity extends AppCompatActivity {

    private CardView cardTelefono, cardEmail, cardFacebook, cardInstagram, cardWhatsApp;
    private Button btnVerUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        // Inicializar vistas
        cardTelefono = findViewById(R.id.cardTelefono);
        cardEmail = findViewById(R.id.cardEmail);
        cardFacebook = findViewById(R.id.cardFacebook);
        cardInstagram = findViewById(R.id.cardInstagram);
        cardWhatsApp = findViewById(R.id.cardWhatsApp);
        btnVerUbicacion = findViewById(R.id.btnVerUbicacion);

        // Configurar listeners
        configurarListeners();
    }

    private void configurarListeners() {
        // Tarjeta de Teléfono - Abrir marcador
        cardTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1234567890"));
                startActivity(intent);
            }
        });

        // Tarjeta de Email - Abrir cliente de correo
        cardEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:contacto@mitienda.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Consulta desde la app");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(ContactoActivity.this,
                            "No hay aplicación de correo instalada",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Facebook
        cardFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirURL("https://www.facebook.com");
            }
        });

        // Instagram
        cardInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirURL("https://www.instagram.com");
            }
        });

        // WhatsApp
        cardWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirWhatsApp();
            }
        });

        // Botón Ver Ubicación
        btnVerUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirMapa();
            }
        });
    }

    private void abrirURL(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void abrirWhatsApp() {
        try {
            String numero = "1234567890"; // Número sin espacios ni guiones
            String mensaje = "Hola, me gustaría obtener más información sobre sus productos.";
            String url = "https://wa.me/" + numero + "?text=" + Uri.encode(mensaje);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this,
                    "WhatsApp no está instalado",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirMapa() {
        // Coordenadas de ejemplo (Ciudad de México, Zócalo)
        String ubicacion = "19.4326,-99.1332";
        String label = "Mi Tienda";
        String uri = "geo:" + ubicacion + "?q=" + ubicacion + "(" + label + ")";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this,
                    "No hay aplicación de mapas instalada",
                    Toast.LENGTH_SHORT).show();
        }
    }
}