/**
 * Login de usuario — App Planning
 * @author Michael
 *
 * Gestiona la autenticación de usuarios existentes mediante Firebase Authentication.
 * Incluye validación de formato de correo y campo de contraseña antes de la petición.
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText correoLogin, contraLogin;
    Button btnLogin;
    TextView usuarioNuevo;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    String correo = "", password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Iniciar sesión");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        correoLogin = findViewById(R.id.correoUsuario);
        contraLogin = findViewById(R.id.contrasenaUsuario);
        btnLogin = findViewById(R.id.btnLogin);
        usuarioNuevo = findViewById(R.id.tengoCuenta);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Por favor, espera");
        progressDialog.setCanceledOnTouchOutside(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });

        usuarioNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });
    }

    private void validarDatos() {
        correo = correoLogin.getText().toString().trim();
        password = contraLogin.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Introduce un correo válido", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Introduce tu contraseña", Toast.LENGTH_SHORT).show();
        } else {
            iniciarSesion();
        }
    }

    private void iniciarSesion() {
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(correo, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            startActivity(new Intent(Login.this, Menu.class));
                            Toast.makeText(Login.this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
