package com.example.planing1.lista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.planing1.R;
import com.example.planing1.adapter.Nota;
import com.example.planing1.adapter.NotaAdapterTrabajo;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class ListaNoche extends AppCompatActivity {

    FloatingActionButton btnNuevaNota;
    RecyclerView recyclerView;
    ImageButton btnMenuDespegable;

    NotaAdapterTrabajo notaAdapterTrabajo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_trabajo);

        btnNuevaNota = findViewById(R.id.btnNuevaNotaTrabajo);
        recyclerView = findViewById(R.id.recyclerViewTrabajo);

        btnNuevaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaNoche.this, Noche.class));
            }
        });

        mostrarRecycleView();
    }

    void mostrarRecycleView() {
        Query query = Utilidad.getCollectionReferenceNotaTrabajo().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Nota> opciones = new FirestoreRecyclerOptions.Builder<Nota>()
                .setQuery(query, Nota.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notaAdapterTrabajo = new NotaAdapterTrabajo(opciones, this);
        recyclerView.setAdapter(notaAdapterTrabajo);

    }

    @Override
    protected void onStart() {
        super.onStart();

        notaAdapterTrabajo.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notaAdapterTrabajo.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notaAdapterTrabajo.notifyDataSetChanged();
    }
}