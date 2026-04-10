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
import com.example.planing1.adapter.NotaAdapterOcio;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class ListaTarde extends AppCompatActivity {

    FloatingActionButton btnNuevaNota;
    RecyclerView recyclerView;
    ImageButton btnMenuDespegable;

    NotaAdapterOcio notaAdapterOcio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ocio);

        btnNuevaNota = findViewById(R.id.btnNuevaNotaOcio);
        recyclerView = findViewById(R.id.recyclerViewOcio);

        btnNuevaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaTarde.this, Tarde.class));
            }
        });

        mostrarRecycleView();
    }

    void mostrarRecycleView() {
        Query query = Utilidad.getCollectionReferenceNotaOcio().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Nota> opciones = new FirestoreRecyclerOptions.Builder<Nota>()
                .setQuery(query, Nota.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notaAdapterOcio = new NotaAdapterOcio(opciones, this);
        recyclerView.setAdapter(notaAdapterOcio);

    }

    @Override
    protected void onStart() {
        super.onStart();

        notaAdapterOcio.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notaAdapterOcio.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notaAdapterOcio.notifyDataSetChanged();
    }
}