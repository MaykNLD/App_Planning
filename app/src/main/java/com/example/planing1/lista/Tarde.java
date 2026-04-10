package com.example.planing1.lista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.planing1.Picker.DatepickerClase;
import com.example.planing1.Picker.TimepickerClase;
import com.example.planing1.R;
import com.example.planing1.adapter.Nota;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class Tarde extends AppCompatActivity {

    TextView fechaOcio, horaOcio, tituloPaginaOcioTV, borrarNotaOcio, btnBorrarNotaOcio;
    EditText descripcionOcioET, direccionET;
    Button btnCalendarioOcio, btnHoraOcio, btnGuardarOcio;
    Nota nota;
    String direccion, descripcionOcio, docId;
    boolean activarEditor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocio);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Fecha
        fechaOcio = findViewById(R.id.tVfechaOcioXml);
        btnCalendarioOcio = findViewById(R.id.btnCalendarioOcio);

        btnCalendarioOcio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatepickerClase.mostrarDatePicker(Tarde.this, fechaOcio);
            }
        });

        //Hora
        horaOcio = findViewById(R.id.tVHoraOcioXml);
        btnHoraOcio = findViewById(R.id.btnHoraOcioXml);

        btnHoraOcio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimepickerClase.mostrarTimePicker(Tarde.this, horaOcio);
            }
        });

        //----------------------------------------------------

        descripcionOcioET = findViewById(R.id.asuntoET);
        direccionET = findViewById(R.id.direccionET);

        //CAMBIO AÑADIR Y REVISAR XML activity_deporte en los demas
        tituloPaginaOcioTV = findViewById(R.id.notaOcio);
        borrarNotaOcio = findViewById(R.id.btnBorrarNotaOcio);

        //botones
        btnGuardarOcio = findViewById(R.id.btnGuardarOcio);

        //BOTON AÑADIDO PARA BORRAR NOTAS
        btnBorrarNotaOcio = findViewById(R.id.btnBorrarNotaOcio);

        /*DATOS A RECIBIR A LA HORA DE ABRIR NORA Y QUE NO SALAGA UNA NOTA VACIA, CON ESTO
            LA NOTA AL ABRIRLA NO SALE VACIA Y SE PUEDE MODIFICAR LOS DATOS
            MODIFICAR CON LOS DATOS EXACTOS DE LA BBDD (LOS NOMBRES SON IGAUALES QUE EN EL OBJETOS)*/
        descripcionOcio = getIntent().getStringExtra("descripcion");
        direccion = getIntent().getStringExtra("direccion");
        docId = getIntent().getStringExtra("docId");

        //docId ES EL ID DEL DOCUEMNTO QUE SE USA EN EL ADAPTER PARA PODER MODIFICA O BORRAR UN
        //UNA NOTA TENER EN CUENTA QUE DEBE SER UN BOOLENA
        if(docId != null && !docId.isEmpty()){
            activarEditor = true;
        }
        descripcionOcioET.setText(descripcionOcio);
        direccionET.setText(direccion);

        if(activarEditor){
            tituloPaginaOcioTV.setText("Editar nota");
            borrarNotaOcio.setVisibility(View.VISIBLE);
        }

        //TIENE QUE SER TODAS ESTAS PARA ACTULIZAR Y BORRAR
        btnGuardarOcio.setOnClickListener((v) -> guardarDatos());

        //BOTON BORRAR
        btnBorrarNotaOcio.setOnClickListener((v) -> borrarDatos());
    }

    //CLASE BORRAR
    void borrarDatos() {
        DocumentReference documentReference;

        //ES LO QUE MAS EN CUENTA DEBEN TENER A LA HORA DE MODIFICAR ESTA FUNCION PARA RECOGER
        //LOS DATOS EXACTOS
        documentReference = Utilidad.getCollectionReferenceNotaOcio().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //nota borrada
                    Utilidad.showToast(Tarde.this, "Nota borrada");
                    finish();
                } else {
                    Utilidad.showToast(Tarde.this, "No se borro la nota ");
                }
            }
        });
    }

    //AQUI NO SE TOCO NADA
    void guardarDatos() {
        String asuntoNota = descripcionOcioET.getText().toString();
        String direccionNota = direccionET.getText().toString();
        String fechaNota = fechaOcio.getText().toString();
        String horaNota = horaOcio.getText().toString();
        if (asuntoNota == null || asuntoNota.isEmpty()){
            //Toast.makeText(this, "Sin titulo", Toast.LENGTH_SHORT).show();
            descripcionOcioET.setError("Añadir asunto, por favor");
            return;
        }
        //Crear Objeto Nuevo
        Nota nota = new Nota();
        nota.setDescripcion(asuntoNota);
        nota.setDireccion(direccionNota);
        nota.setFecha(fechaNota);
        nota.setHora(horaNota);
        //nota.setTimestamp(Timestamp.now());

        guardarDatosFirebase(nota);
    }

    void guardarDatosFirebase(Nota nota) {
        DocumentReference documentReference;

        //SE AÑADIO ESTO TENER EN CUENTA EL docId Y MIRAR QUE EL getCollectionReferenceNotas
        //SE DE CADA CALSE TIENE SU NOMBRE MIRAR L¡EL NOMBRE EN UTILIDAD SI NO LO SABEN
        if(activarEditor){
            //actaliza
            documentReference = Utilidad.getCollectionReferenceNotaOcio().document(docId);
        } else {
            //crea nueva nota
            documentReference = Utilidad.getCollectionReferenceNotaOcio().document();
        }

        documentReference.set(nota).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Utilidad.showToast(Tarde.this, "Nota añadida");
                    finish();
                } else {
                    Utilidad.showToast(Tarde.this, "Nota no añadida");
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}