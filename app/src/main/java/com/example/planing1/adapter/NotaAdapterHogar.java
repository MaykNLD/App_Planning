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
import com.example.planing1.lista.Hogar;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NotaAdapterHogar extends FirestoreRecyclerAdapter<Nota, NotaAdapterHogar.NotaViewHolder> {

    Context context;

    public NotaAdapterHogar(@NonNull FirestoreRecyclerOptions<Nota> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NotaAdapterHogar.NotaViewHolder holder, int position, @NonNull Nota nota) {
        holder.tarea.setText(nota.tarea);
        holder.infTarea.setText(nota.infTarea);
        holder.fecha.setText(nota.getFecha());
        holder.hora.setText(nota.getHora());

        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, Hogar.class);
            intent.putExtra("tarea", nota.tarea);
            intent.putExtra("infTarea", nota.infTarea);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }


    @NonNull
    @Override
    public NotaAdapterHogar.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_nota_item_hogar, parent, false);
        return new NotaViewHolder(view);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder{

        TextView tarea, infTarea, fecha, hora;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tarea = itemView.findViewById(R.id.tareaTV);
            infTarea = itemView.findViewById(R.id.informacionTareaTV);
            fecha = itemView.findViewById(R.id.fechaHogarRecycler);
            hora = itemView.findViewById(R.id.horaHogarRecycler);
        }
    }
}
