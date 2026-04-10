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

public class Noche extends AppCompatActivity {

    TextView tv_fecha_Trabajo, tvHora_trabajo, tituloPaginaTrabajoTV, borrarNotaTrabajo, btnBorrarNotaTrabajo;
    EditText motivoET, notasReunionET;
    Button btn_calendario_trabajo, btn_hora_trabajo, btnGuardar;
    Nota nota;
    String motivo, notaReunion, docId;
    boolean activarEditor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Fecha
        tv_fecha_Trabajo = findViewById(R.id.tVfechaTrabajoXml);
        btn_calendario_trabajo = findViewById(R.id.btnCalendarioTrabajoXml);

        btn_calendario_trabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatepickerClase.mostrarDatePicker(Noche.this, tv_fecha_Trabajo);
            }
        });

        //Hora
        tvHora_trabajo = findViewById(R.id.tVHoraTrabajoXml);
        btn_hora_trabajo = findViewById(R.id.btnHoraTrabajoXml);

        btn_hora_trabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimepickerClase.mostrarTimePicker(Noche.this, tvHora_trabajo);
            }
        });

        //----------------------------------------------------

        btnGuardar = findViewById(R.id.btnGuardarTrabajo);
        motivoET = findViewById(R.id.motivoEt);
        notasReunionET = findViewById(R.id.notaReunionEt);

        //CAMBIO AÑADIR Y REVISAR XML activity_deporte en los demas
        tituloPaginaTrabajoTV = findViewById(R.id.notaTrabajo);
        borrarNotaTrabajo = findViewById(R.id.btnBorrarNotaTrabajo);

        //botones
        btnGuardar = findViewById(R.id.btnGuardarTrabajo);

        //BOTON AÑADIDO PARA BORRAR NOTAS
        btnBorrarNotaTrabajo = findViewById(R.id.btnBorrarNotaTrabajo);

        /*DATOS A RECIBIR A LA HORA DE ABRIR NORA Y QUE NO SALAGA UNA NOTA VACIA, CON ESTO
            LA NOTA AL ABRIRLA NO SALE VACIA Y SE PUEDE MODIFICAR LOS DATOS
            MODIFICAR CON LOS DATOS EXACTOS DE LA BBDD (LOS NOMBRES SON IGAUALES QUE EN EL OBJETOS)*/
        motivo = getIntent().getStringExtra("motivo");
        notaReunion = getIntent().getStringExtra("notaReunion");
        docId = getIntent().getStringExtra("docId");

        //docId ES EL ID DEL DOCUEMNTO QUE SE USA EN EL ADAPTER PARA PODER MODIFICA O BORRAR UN
        //UNA NOTA TENER EN CUENTA QUE DEBE SER UN BOOLENA
        if(docId != null && !docId.isEmpty()){
            activarEditor = true;
        }

        motivoET.setText(motivo);
        notasReunionET.setText(notaReunion);

        if(activarEditor){
            tituloPaginaTrabajoTV.setText("Editar nota");
            borrarNotaTrabajo.setVisibility(View.VISIBLE);
        }

        //BOTONES
        btnGuardar.setOnClickListener((v) -> guardarDatos());

        btnBorrarNotaTrabajo.setOnClickListener((v) -> borrarDatosTrabajo());

    }

    void borrarDatosTrabajo() {
        DocumentReference documentReference;

        //ES LO QUE MAS EN CUENTA DEBEN TENER A LA HORA DE MODIFICAR ESTA FUNCION PARA RECOGER
        //LOS DATOS EXACTOS
        documentReference = Utilidad.getCollectionReferenceNotaTrabajo().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //nota borrada
                    Utilidad.showToast(Noche.this, "Nota borrada");
                    finish();
                } else {
                    Utilidad.showToast(Noche.this, "No se borro la nota ");
                }
            }
        });
    }

    void guardarDatos() {
        String motivoNota = motivoET.getText().toString();
        String notaReunionNota = notasReunionET.getText().toString();
        String notaFechatrabajo = tv_fecha_Trabajo.getText().toString();
        String notaHoratrabajo = tvHora_trabajo.getText().toString();
        if (motivoNota == null || motivoNota.isEmpty()){
            //Toast.makeText(this, "Sin titulo", Toast.LENGTH_SHORT).show();
            motivoET.setError("Añadir titulo, por favor");
            return;
        }

        Nota nota = new Nota();
        nota.setMotivo(motivoNota);
        nota.setNotaReunion(notaReunionNota);
        nota.setFecha(notaFechatrabajo);
        nota.setHora(notaHoratrabajo);


        guardarDatosFirebase(nota);
    }

    void guardarDatosFirebase(Nota nota) {
        DocumentReference documentReference;

        if(activarEditor){
            //actaliza
            documentReference = Utilidad.getCollectionReferenceNotaTrabajo().document(docId);
        } else {
            //crea nueva nota
            documentReference = Utilidad.getCollectionReferenceNotaTrabajo().document();
        }
        documentReference.set(nota).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Utilidad.showToast(Noche.this, "Nota añadida");
                    finish();
                } else {
                    Utilidad.showToast(Noche.this, "Nota no añadida");
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