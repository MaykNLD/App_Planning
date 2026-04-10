/**
 * Registro de usuario — App Planning
 * @author Michael
 *
 * Gestiona el alta de nuevos usuarios mediante Firebase Authentication.
 * Los datos del perfil se persisten en Firebase Realtime Database.
 * NOTA: La contraseña NUNCA se almacena en la base de datos. Solo la gestiona Firebase Auth.
 */
package com.example.planing1.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planing1.Menu;
import com.example.planing1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    EditText nombreEt, correoEt, contraseñaEt, confirmarContraseñaEt;
    Button registroUsuario;
    TextView tengoCuenta;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    String nombre = "", correo = "", password = "", confirmaPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Crear cuenta");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        nombreEt = findViewById(R.id.nombreEt);
        correoEt = findViewById(R.id.correoEt);
        contraseñaEt = findViewById(R.id.contraseñaEt);
        confirmarContraseñaEt = findViewById(R.id.confirmarContraseñaEt);
        registroUsuario = findViewById(R.id.registroUsuario);
        tengoCuenta = findViewById(R.id.tengoCuenta);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setTitle("Por favor, espera");
        progressDialog.setCanceledOnTouchOutside(false);

        registroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });

        tengoCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registro.this, Login.class));
            }
        });
    }

    private void validarDatos() {
        nombre = nombreEt.getText().toString().trim();
        correo = correoEt.getText().toString().trim();
        password = contraseñaEt.getText().toString();
        confirmaPassword = confirmarContraseñaEt.getText().toString();

        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Introduce tu nombre", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Introduce un correo válido", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Introduce una contraseña", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(confirmaPassword)) {
            Toast.makeText(this, "Confirma la contraseña", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmaPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        } else {
            crearCuenta();
        }
    }

    private void crearCuenta() {
        progressDialog.setMessage("Creando cuenta...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(correo, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        guardarPerfil();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Guarda únicamente los datos de perfil públicos del usuario.
     * La contraseña es gestionada exclusivamente por Firebase Authentication
     * y NUNCA se almacena en la base de datos.
     */
    private void guardarPerfil() {
        progressDialog.setMessage("Guardando perfil...");

        String uid = firebaseAuth.getUid();

        HashMap<String, String> perfil = new HashMap<>();
        perfil.put("uid", uid);
        perfil.put("correo", correo);
        perfil.put("nombre", nombre);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid)
                .setValue(perfil)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registro.this, Menu.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}