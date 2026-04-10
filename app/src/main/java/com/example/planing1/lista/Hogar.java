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

public class Hogar extends AppCompatActivity {

    TextView fecha_hogar,hora_hogar , tituloPaginaHogarTV, borrarNotaHogar, btnBorrarNotaHogar;
    EditText tareaET, infTareaET;
    Button btn_Calendario_hogar, btn_Hora_hogar, btnGuardar;
    String tarea, infTarea, docId;
    Nota nota;
    boolean activarEditor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hogar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Fecha
        fecha_hogar = findViewById(R.id.tVFechaHogarXml);
        btn_Calendario_hogar = findViewById(R.id.btnCalendarioHogarXml);

        btn_Calendario_hogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatepickerClase.mostrarDatePicker(Hogar.this, fecha_hogar);
            }
        });

        //Hora
        hora_hogar = findViewById(R.id.tVHoraHogarXml);
        btn_Hora_hogar = findViewById(R.id.btnHoraHogarXml);

        btn_Hora_hogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimepickerClase.mostrarTimePicker(Hogar.this, hora_hogar);
            }
        });

        //----------------------------------------------------


        tareaET = findViewById(R.id.tareaEt);
        infTareaET = findViewById(R.id.informacionTareaEt);

        //CAMBIO AÑADIR Y REVISAR XML activity_deporte en los demas
        tituloPaginaHogarTV = findViewById(R.id.notaDeporte);
        borrarNotaHogar = findViewById(R.id.btnBorrarNotaHogar);

        //botones
        btnGuardar = findViewById(R.id.btnGuardarHogar);

        //BOTON AÑADIDO PARA BORRAR NOTAS
        btnBorrarNotaHogar = findViewById(R.id.btnBorrarNotaHogar);

        /*DATOS A RECIBIR A LA HORA DE ABRIR NORA Y QUE NO SALAGA UNA NOTA VACIA, CON ESTO
            LA NOTA AL ABRIRLA NO SALE VACIA Y SE PUEDE MODIFICAR LOS DATOS
            MODIFICAR CON LOS DATOS EXACTOS DE LA BBDD (LOS NOMBRES SON IGAUALES QUE EN EL OBJETOS)*/
        tarea = getIntent().getStringExtra("tarea");
        infTarea = getIntent().getStringExtra("infTarea");
        docId = getIntent().getStringExtra("docId");

        //docId ES EL ID DEL DOCUEMNTO QUE SE USA EN EL ADAPTER PARA PODER MODIFICA O BORRAR UN
        //UNA NOTA TENER EN CUENTA QUE DEBE SER UN BOOLENA
        if(docId != null && !docId.isEmpty()){
            activarEditor = true;
        }

        tareaET.setText(tarea);
        infTareaET.setText(infTarea);

        if(activarEditor){
            tituloPaginaHogarTV.setText("Editar nota");
            borrarNotaHogar.setVisibility(View.VISIBLE);
        }

        //TIENE QUE SER TODAS ESTAS PARA ACTULIZAR Y BORRAR


        btnGuardar.setOnClickListener((v) -> guardarDatos());

        //BOTON BORRAR
        btnBorrarNotaHogar.setOnClickListener((v) -> borrarDatosHogar());
    }
    void borrarDatosHogar() {
        DocumentReference documentReference;

        //ES LO QUE MAS EN CUENTA DEBEN TENER A LA HORA DE MODIFICAR ESTA FUNCION PARA RECOGER
        //LOS DATOS EXACTOS
        documentReference = Utilidad.getCollectionReferenceNotaHogar().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //nota borrada
                    Utilidad.showToast(Hogar.this, "Nota borrada");
                    finish();
                } else {
                    Utilidad.showToast(Hogar.this, "No se borro la nota ");
                }
            }
        });
    }

    void guardarDatos() {
        String tareaNota = tareaET.getText().toString();
        String infTareaNota = infTareaET.getText().toString();
        String fechaHogarNota = fecha_hogar.getText().toString();
        String horaHogarNota = hora_hogar.getText().toString();

        if (tareaNota == null || tareaNota.isEmpty()){
            //Toast.makeText(this, "Sin titulo", Toast.LENGTH_SHORT).show();
            tareaET.setError("Añadir titulo, por favor");
            return;
        }

        Nota nota = new Nota();
        nota.setTarea(tareaNota);
        nota.setInfTarea(infTareaNota);
        nota.setFecha(fechaHogarNota);
        nota.setHora(horaHogarNota);


        guardarDatosFirebase(nota);
    }

    void guardarDatosFirebase(Nota nota) {
        DocumentReference documentReference;

        if(activarEditor){
            //actaliza
            documentReference = Utilidad.getCollectionReferenceNotaHogar().document(docId);
        } else {
            //crea nueva nota
            documentReference = Utilidad.getCollectionReferenceNotaHogar().document();
        }

        documentReference.set(nota).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Utilidad.showToast(Hogar.this, "Nota añadida");
                    finish();
                } else {
                    Utilidad.showToast(Hogar.this, "Nota no añadida");
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