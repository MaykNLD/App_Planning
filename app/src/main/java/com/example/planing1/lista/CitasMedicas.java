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

public class CitasMedicas extends AppCompatActivity {

    TextView fecha_Cm,horas_CM,tituloPaginaMedicaTV, borrarNotaMedica, btnBorrarMedicaNota, norrarNotaMedica;
    EditText centroET, especialistaET, descripcionET;
    Button btn_Calendario_Cm, btn_Hora_Cm, btnGuardar;
    String centroS, descripcionS, especialistaS, docId;
    Nota nota;
    boolean activarEditor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_medicas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Fecha
        fecha_Cm = findViewById(R.id.tVfechaCitasMedicasXml);
        btn_Calendario_Cm = findViewById(R.id.btnCalendarioCitasMedicasXml);

        btn_Calendario_Cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatepickerClase.mostrarDatePicker(CitasMedicas.this, fecha_Cm);
            }
        });

        //Hora
        horas_CM = findViewById(R.id.tVhoraCitasMedicasXml);
        btn_Hora_Cm = findViewById(R.id.btnHoraCitasMedicasXml);

        btn_Hora_Cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimepickerClase.mostrarTimePicker(CitasMedicas.this, horas_CM);
            }
        });

        //----------------------------------------------------




        centroET = findViewById(R.id.centroET);
        especialistaET = findViewById(R.id.especialistaET);
        descripcionET = findViewById(R.id.descripcionMedicaEt);

        btnGuardar = findViewById(R.id.btnGuardarCitaMedica);

        //CAMBIO AÑADIR Y REVISAR XML activity_deporte en los demas
        tituloPaginaMedicaTV = findViewById(R.id.notaCitasMedicas);
        borrarNotaMedica = findViewById(R.id.borrarNotaCitasMedicas);

        //botones
        btnGuardar = findViewById(R.id.btnGuardarCitaMedica);

        //boton borrar
        btnBorrarMedicaNota = findViewById(R.id.borrarNotaCitasMedicas);

        /*DATOS A RECIBIR A LA HORA DE ABRIR NORA Y QUE NO SALAGA UNA NOTA VACIA, CON ESTO
            LA NOTA AL ABRIRLA NO SALE VACIA Y SE PUEDE MODIFICAR LOS DATOS
            MODIFICAR CON LOS DATOS EXACTOS DE LA BBDD (LOS NOMBRES SON IGAUALES QUE EN EL OBJETOS)*/
        centroS = getIntent().getStringExtra("centro");
        especialistaS = getIntent().getStringExtra("especialista");
        descripcionS = getIntent().getStringExtra("descripcion");
        docId = getIntent().getStringExtra("docId");

        //docId ES EL ID DEL DOCUEMNTO QUE SE USA EN EL ADAPTER PARA PODER MODIFICA O BORRAR UN
        //UNA NOTA TENER EN CUENTA QUE DEBE SER UN BOOLENA
        if(docId != null && !docId.isEmpty()){
            activarEditor = true;
        }

        centroET.setText(centroS);
        especialistaET.setText(especialistaS);
        descripcionET.setText(descripcionS);

        if(activarEditor){
            tituloPaginaMedicaTV.setText("Editar nota");
            borrarNotaMedica.setVisibility(View.VISIBLE);
        }

        //TIENE QUE SER TODAS ESTAS PARA ACTULIZAR Y BORRAR
        btnGuardar.setOnClickListener((v) -> guardarDatos());

        //BOTON BORRAR
        btnBorrarMedicaNota.setOnClickListener((v)-> borrarDatosCitasMedicas());
    }

    void borrarDatosCitasMedicas() {
        DocumentReference documentReference;

        //ES LO QUE MAS EN CUENTA DEBEN TENER A LA HORA DE MODIFICAR ESTA FUNCION PARA RECOGER
        //LOS DATOS EXACTOS
        documentReference = Utilidad.getCollectionReferenceNotaCitasMedicas().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //nota borrada
                    Utilidad.showToast(CitasMedicas.this, "Nota borrada");
                    finish();
                } else {
                    Utilidad.showToast(CitasMedicas.this, "No se borro la nota ");
                }
            }
        });
    }

    void guardarDatos() {
        String centroNota = centroET.getText().toString();
        String especialistaNota = especialistaET.getText().toString();
        String descripcionNota = descripcionET.getText().toString();
        String fechaNota = fecha_Cm.getText().toString();
        String horaNota = horas_CM.getText().toString();

        if (centroNota == null || centroNota.isEmpty()){
            //Toast.makeText(this, "Sin titulo", Toast.LENGTH_SHORT).show();
            centroET.setError("Añadir asunto, por favor");
            return;
        }

        //Clase nota de objeto
        Nota nota = new Nota();
        nota.setCentro(centroNota);
        nota.setEspecialista(especialistaNota);
        nota.setDescripcion(descripcionNota);
        nota.setFecha(fechaNota);
        nota.setHora(horaNota);

        guardarDatosFirebase(nota);
    }

    void guardarDatosFirebase(Nota nota) {
        DocumentReference documentReference;
//-------
        if(activarEditor){
            //actaliza
            documentReference = Utilidad.getCollectionReferenceNotaCitasMedicas().document(docId);
        } else {
            //crea nueva nota
            documentReference = Utilidad.getCollectionReferenceNotaCitasMedicas().document();
        }

        documentReference.set(nota).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Utilidad.showToast(CitasMedicas.this, "Nota añadida");
                    finish();
                } else {
                    Utilidad.showToast(CitasMedicas.this, "Nota no añadida");
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