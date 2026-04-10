package com.example.planing1.lista;

import com.example.planing1.Picker.DatepickerClase;
import com.example.planing1.Picker.TimepickerClase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.planing1.R;
import com.example.planing1.adapter.Nota;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class Dia extends AppCompatActivity {

    TextView fechaDeporte,horaDeporte, tituloPaginaTV, borrarNota, btnBorrarNota;
    EditText tituloET, descricpcionET;
    Button btnCalendarioDeporte, btnHora, btnGuardar;
    Nota nota;
    String titulo, descripcion, docId;
    boolean activarEditor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deporte);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Fecha
        fechaDeporte = findViewById(R.id.fechaDeporte);
        btnCalendarioDeporte = findViewById(R.id.btnCalendarioDeporte);

        btnCalendarioDeporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatepickerClase.mostrarDatePicker(Dia.this, fechaDeporte);
            }
        });

        //Hora
        horaDeporte = findViewById(R.id.horaDeporteXml);
        btnHora = findViewById(R.id.btnHoraDeporteXml);

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimepickerClase.mostrarTimePicker(Dia.this, horaDeporte);
            }
        });

        //----------------------------------------------------

        tituloET = findViewById(R.id.tituloEt);
        descricpcionET = findViewById(R.id.descripcionEt);

        //CAMBIO AÑADIR Y REVISAR XML activity_deporte en los demas
        tituloPaginaTV = findViewById(R.id.notaDeporte);
        borrarNota = findViewById(R.id.borrarNotaDeporte);

        //botones
        btnGuardar = findViewById(R.id.btnGuardar);

        //BOTON AÑADIDO PARA BORRAR NOTAS
        btnBorrarNota = findViewById(R.id.borrarNotaDeporte);

        /*DATOS A RECIBIR A LA HORA DE ABRIR NORA Y QUE NO SALAGA UNA NOTA VACIA, CON ESTO
            LA NOTA AL ABRIRLA NO SALE VACIA Y SE PUEDE MODIFICAR LOS DATOS
            MODIFICAR CON LOS DATOS EXACTOS DE LA BBDD (LOS NOMBRES SON IGAUALES QUE EN EL OBJETOS)*/
        titulo = getIntent().getStringExtra("titulo");
        descripcion = getIntent().getStringExtra("descripcion");
        docId = getIntent().getStringExtra("docId");

        //docId ES EL ID DEL DOCUEMNTO QUE SE USA EN EL ADAPTER PARA PODER MODIFICA O BORRAR UN
        //UNA NOTA TENER EN CUENTA QUE DEBE SER UN BOOLENA
        if(docId != null && !docId.isEmpty()){
            activarEditor = true;
        }

        tituloET.setText(titulo);
        descricpcionET.setText(descripcion);

        if(activarEditor){
            tituloPaginaTV.setText("Editar nota");
            borrarNota.setVisibility(View.VISIBLE);
        }

        //TIENE QUE SER TODAS ESTAS PARA ACTULIZAR Y BORRAR
        btnGuardar.setOnClickListener((v) -> guardarDatos());

        //BOTON BORRAR
        btnBorrarNota.setOnClickListener((v) -> borrarDatos());
    }

    //CLASE BORRAR
    void borrarDatos() {
        DocumentReference documentReference;

        //ES LO QUE MAS EN CUENTA DEBEN TENER A LA HORA DE MODIFICAR ESTA FUNCION PARA RECOGER
        //LOS DATOS EXACTOS
        documentReference = Utilidad.getCollectionReferenceNotas().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //nota borrada
                    Utilidad.showToast(Dia.this, "Nota borrada");
                    finish();
                } else {
                    Utilidad.showToast(Dia.this, "No se borro la nota ");
                }
            }
        });
    }

    //AQUI NO SE TOCO NADA
    void guardarDatos() {
        String tituloNota = tituloET.getText().toString();
        String descripcionNota = descricpcionET.getText().toString();
        String fechaNota = fechaDeporte.getText().toString();
        String horaNota = horaDeporte.getText().toString();
        if (tituloNota == null || tituloNota.isEmpty()){
            //Toast.makeText(this, "Sin titulo", Toast.LENGTH_SHORT).show();
            tituloET.setError("Añadir titulo, por favor");
            return;
        }
        //Crear Objeto Nuevo
        Nota nota = new Nota();
        nota.setTitulo(tituloNota);
        nota.setDescripcion(descripcionNota);
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
            documentReference = Utilidad.getCollectionReferenceNotas().document(docId);
        } else {
            //crea nueva nota
            documentReference = Utilidad.getCollectionReferenceNotas().document();
        }

        documentReference.set(nota).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Utilidad.showToast(Dia.this, "Nota añadida");
                    finish();
                } else {
                    Utilidad.showToast(Dia.this, "Nota no añadida");
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