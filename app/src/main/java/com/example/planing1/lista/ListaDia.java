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
import com.example.planing1.adapter.NotaAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;


public class
ListaDia extends AppCompatActivity {

    FloatingActionButton btnNuevaNota;
    RecyclerView recyclerView;
    ImageButton btnMenuDespegable;

    NotaAdapter notaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        btnNuevaNota = findViewById(R.id.btnNuevaNota);
        recyclerView = findViewById(R.id.recyclerViewDeporte);

        btnNuevaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaDia.this, Dia.class));
            }
        });

        mostrarRecycleView();
    }

    void mostrarRecycleView() {
        Query query = Utilidad.getCollectionReferenceNotas().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Nota> opciones = new FirestoreRecyclerOptions.Builder<Nota>()
                .setQuery(query, Nota.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notaAdapter = new NotaAdapter(opciones, this);
        recyclerView.setAdapter(notaAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        notaAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notaAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notaAdapter.notifyDataSetChanged();
    }
}