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

public class Finanzas extends AppCompatActivity {

    EditText reciboET, infReciboET;
    TextView fecha_finanzas, hora_finanzas, tituloPaginaFinanzasTV, borrarNotaFinanzas, btnBorrarNotaFinanzas;
    Button btnCalendarioFinanzas, btnHoraFinanzas, btnGuardar;
    Nota nota;
    String recibo, infRecibo, docId;
    boolean activarEditor = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finanzas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Fecha
        fecha_finanzas = findViewById(R.id.tVfechaFinanzasXml);
        btnCalendarioFinanzas = findViewById(R.id.btnCalendarioFinanzasXml);

        btnCalendarioFinanzas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatepickerClase.mostrarDatePicker(Finanzas.this, fecha_finanzas);
            }
        });

        //Hora
        hora_finanzas = findViewById(R.id.tVhoraFinanzasXml);
        btnHoraFinanzas = findViewById(R.id.btnHoraFinanzasXml);

        btnHoraFinanzas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimepickerClase.mostrarTimePicker(Finanzas.this, hora_finanzas);
            }
        });

        //----------------------------------------------------
        reciboET = findViewById(R.id.reciboEt);
        infReciboET = findViewById(R.id.informacionReciboEt);

        //CAMBIO AÑADIR Y REVISAR XML activity_deporte en los demas
        tituloPaginaFinanzasTV = findViewById(R.id.notaFinanzas);
        borrarNotaFinanzas = findViewById(R.id.btnBorrarNotaFinanzas);

        //botones
        btnGuardar = findViewById(R.id.btnGuardarFinanzas);

        //BOTON AÑADIDO PARA BORRAR NOTAS
        btnBorrarNotaFinanzas = findViewById(R.id.btnBorrarNotaFinanzas);

        /*DATOS A RECIBIR A LA HORA DE ABRIR NORA Y QUE NO SALAGA UNA NOTA VACIA, CON ESTO
            LA NOTA AL ABRIRLA NO SALE VACIA Y SE PUEDE MODIFICAR LOS DATOS
            MODIFICAR CON LOS DATOS EXACTOS DE LA BBDD (LOS NOMBRES SON IGAUALES QUE EN EL OBJETOS)*/
        recibo = getIntent().getStringExtra("recibo");
        infRecibo = getIntent().getStringExtra("infRecibo");
        docId = getIntent().getStringExtra("docId");

        //docId ES EL ID DEL DOCUEMNTO QUE SE USA EN EL ADAPTER PARA PODER MODIFICA O BORRAR UN
        //UNA NOTA TENER EN CUENTA QUE DEBE SER UN BOOLENA
        if(docId != null && !docId.isEmpty()){
            activarEditor = true;
        }

        reciboET.setText(recibo);
        infReciboET.setText(infRecibo);

        if(activarEditor){
            tituloPaginaFinanzasTV.setText("Editar nota");
            borrarNotaFinanzas.setVisibility(View.VISIBLE);
        }

        btnGuardar.setOnClickListener((v) -> guardarDatos());

        //BOTON BORRAR
        btnBorrarNotaFinanzas.setOnClickListener((v) -> borrarDatosFinanzas());

    }

    void borrarDatosFinanzas() {
        DocumentReference documentReference;

        //ES LO QUE MAS EN CUENTA DEBEN TENER A LA HORA DE MODIFICAR ESTA FUNCION PARA RECOGER
        //LOS DATOS EXACTOS
        documentReference = Utilidad.getCollectionReferenceFinanzas().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //nota borrada
                    Utilidad.showToast(Finanzas.this, "Nota borrada");
                    finish();
                } else {
                    Utilidad.showToast(Finanzas.this, "No se borro la nota ");
                }
            }
        });
    }

    void guardarDatos() {
        String reciboNota = reciboET.getText().toString();
        String infReciboNota = infReciboET.getText().toString();
        String  fechaNota = fecha_finanzas.getText().toString();
        String horaNota = hora_finanzas.getText().toString();

        if (reciboNota == null || reciboNota.isEmpty()){
            //Toast.makeText(this, "Sin titulo", Toast.LENGTH_SHORT).show();
            reciboET.setError("Añadir asunto, por favor");
            return;
        }

        //Clase nota de objeto
        Nota nota = new Nota();
        nota.setRecibo(reciboNota);
        nota.setInfRecibo(infReciboNota);
        nota.setFecha(fechaNota);
        nota.setHora(horaNota);


        guardarDatosFirebase(nota);
    }

    void guardarDatosFirebase(Nota nota) {
        DocumentReference documentReference;
//-------


        if(activarEditor){
            //actaliza
            documentReference = Utilidad.getCollectionReferenceFinanzas().document(docId);        } else {
            //crea nueva nota
            documentReference = Utilidad.getCollectionReferenceFinanzas().document();
        }

        documentReference.set(nota).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Utilidad.showToast(Finanzas.this, "Nota añadida");
                    finish();
                } else {
                    Utilidad.showToast(Finanzas.this, "Nota no añadida");
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