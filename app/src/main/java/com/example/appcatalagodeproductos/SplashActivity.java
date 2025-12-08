package com.example.appcatalagodeproductos; // Ajusta el nombre de tu paquete

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    // Duración del splash en milisegundos (3 segundos)
    private static final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Ocultar la barra de acción si existe
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Usar Handler para esperar y luego navegar a MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Crear Intent para ir a MainActivity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                // Finalizar SplashActivity para que no vuelva con el botón atrás
                finish();
            }
        }, SPLASH_DURATION);
    }
}