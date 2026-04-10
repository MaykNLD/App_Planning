package com.example.planing1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planing1.R;
import com.example.planing1.lista.Dia;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;



public class NotaAdapter extends FirestoreRecyclerAdapter<Nota, NotaAdapter.NotaViewHolder> {

    Context context;


    public NotaAdapter(@NonNull FirestoreRecyclerOptions<Nota> options, Context context) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull NotaViewHolder holder, int position, @NonNull Nota nota) {
        holder.titulo.setText(nota.titulo);
        holder.descripcion.setText(nota.descripcion);
        holder.fecha.setText(nota.getFecha());
        holder.hora.setText(nota.getHora());
        //IMPORTANTE ESTE ABRE LA ACTIVIDAD GUARDA PARA ACTUALIZAR Y BORRAR AÑADI EL docId para saber
        //QUE NOTA SE ELIMINARA O ACTULIZARA
        //NO SE A MODIFICADO NADA MAS QUE ESTO
        //AÑADIR EN LOS XML DE CADA CLASE EL TEXT VIEW btnBorrar (SEGUN LA CLASE QUE SEA COMO
        // IDENTIFICADOR) ASI NO SE PIERDEN
        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, Dia.class);
            intent.putExtra("titulo", nota.titulo);
            intent.putExtra("descripcion", nota.descripcion);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_notas_items, parent, false);
        return new NotaViewHolder(view);
    }


    class NotaViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, descripcion, fecha, hora;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tituloNotaDeporteTV);
            descripcion = itemView.findViewById(R.id.descripcionNotaDeporteTV);
            fecha = itemView.findViewById(R.id.fechaDeporteLista);
            hora = itemView.findViewById(R.id.horaDeporteLista);
        }
    }

}
