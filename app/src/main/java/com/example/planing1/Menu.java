/**
 * Menú principal — App Planning
 * @author Michael
 *
 * Pantalla central de la aplicación. Muestra las categorías de planificación
 * disponibles y gestiona el cierre de sesión del usuario autenticado.
 */
package com.example.planing1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.planing1.lista.ListaCitasMedicas;
import com.example.planing1.lista.ListaDia;
import com.example.planing1.lista.ListaTarde;
import com.example.planing1.lista.ListaNoche;
import com.example.planing1.login.PreLogin;
import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {

    Button cerrarSesion;
    FirebaseAuth firebaseAuth;

    ImageView btnDia, btnTarde, btnNoche, btnCitasMedicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Planning");

        cerrarSesion = findViewById(R.id.cerrarSesion);
        firebaseAuth = FirebaseAuth.getInstance();

        btnDia = findViewById(R.id.btnDia);
        btnDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ListaDia.class));
            }
        });

        btnTarde = findViewById(R.id.btnTarde);
        btnTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ListaTarde.class));
            }
        });

        btnNoche = findViewById(R.id.btnNoche);
        btnNoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ListaNoche.class));
            }
        });

        btnCitasMedicas = findViewById(R.id.btnProhibido);
        btnCitasMedicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ListaCitasMedicas.class));
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });
    }

    private void cerrarSesion() {
        firebaseAuth.signOut();
        startActivity(new Intent(Menu.this, PreLogin.class));
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        finish();
    }
}