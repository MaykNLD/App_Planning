/**
 * Actividad principal — Splash Screen con verificación de sesión
 * @author Michael
 *
 * Al iniciar la app espera 3 segundos (splash) y redirige al usuario:
 * - Si tiene sesión activa → Menú principal
 * - Si no tiene sesión → Pantalla de bienvenida/login
 */
package com.example.planing1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.planing1.login.PreLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                verificarSesion();
            }
        }, 3000);
    }

    private void verificarSesion() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null) {
            startActivity(new Intent(MainActivity.this, PreLogin.class));
        } else {
            startActivity(new Intent(MainActivity.this, Menu.class));
        }
        finish();
    }
}