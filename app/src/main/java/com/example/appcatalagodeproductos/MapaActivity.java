package com.example.appcatalagodeproductos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnAbrirEnMaps, btnCompartirUbicacion;

    // Coordenadas del negocio (ejemplo: Zócalo de CDMX)
    private static final double LATITUD = 17.551478;
    private static final double LONGITUD = -99.500444;
    private static final String NOMBRE_NEGOCIO = "Mi Tienda";
    private static final String DIRECCION = "Calle Ingeniero Manuel Altamirano 2, Chilpancingo Centro, 39000 Chilpancingo de los Bravo, GRO, México";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        btnAbrirEnMaps = findViewById(R.id.btnAbrirEnMaps);
        btnCompartirUbicacion = findViewById(R.id.btnCompartirUbicacion);

        // Inicializar el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        configurarBotones();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Coordenadas del negocio
        LatLng ubicacionNegocio = new LatLng(LATITUD, LONGITUD);

        // Agregar marcador
        mMap.addMarker(new MarkerOptions()
                .position(ubicacionNegocio)
                .title(NOMBRE_NEGOCIO)
                .snippet(DIRECCION));

        // Mover la cámara al negocio con zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionNegocio, 15));

        // Habilitar controles
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    private void configurarBotones() {
        // Botón Abrir en Google Maps
        btnAbrirEnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEnGoogleMaps();
            }
        });

        // Botón Compartir Ubicación
        btnCompartirUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compartirUbicacion();
            }
        });
    }

    private void abrirEnGoogleMaps() {
        // URI para abrir Google Maps
        String uri = "geo:" + LATITUD + "," + LONGITUD + "?q=" + LATITUD + "," + LONGITUD + "(" + NOMBRE_NEGOCIO + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Si no tiene Google Maps, abrir en navegador
            String urlBrowser = "https://www.google.com/maps/search/?api=1&query=" + LATITUD + "," + LONGITUD;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlBrowser));
            startActivity(browserIntent);
        }
    }

    private void compartirUbicacion() {
        String mensaje = "📍 Visítanos en " + NOMBRE_NEGOCIO + "\n" +
                DIRECCION + "\n\n" +
                "Ver en mapa: https://www.google.com/maps/search/?api=1&query=" + LATITUD + "," + LONGITUD;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Ubicación de " + NOMBRE_NEGOCIO);
        shareIntent.putExtra(Intent.EXTRA_TEXT, mensaje);

        startActivity(Intent.createChooser(shareIntent, "Compartir ubicación mediante"));
    }
}