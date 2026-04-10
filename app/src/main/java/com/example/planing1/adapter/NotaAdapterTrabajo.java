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
import com.example.planing1.lista.Noche;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NotaAdapterTrabajo extends FirestoreRecyclerAdapter<Nota, NotaAdapterTrabajo.NotaViewHolder> {

    Context context;

    public NotaAdapterTrabajo(@NonNull FirestoreRecyclerOptions<Nota> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NotaViewHolder holder, int position, @NonNull Nota nota) {
        holder.motivoReunion.setText(nota.motivo);
        holder.notaReunion.setText(nota.notaReunion);
        holder.fecha.setText(nota.getFecha());
        holder.hora.setText(nota.getHora());

        //----
        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, Noche.class);
            intent.putExtra("motivo", nota.motivo);
            intent.putExtra("notaReunion", nota.notaReunion);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_nota_item_trabajo, parent, false);
        return new NotaViewHolder(view);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder{

        TextView motivoReunion, notaReunion, fecha, hora;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            motivoReunion = itemView.findViewById(R.id.motivoNotaTrabajoTV);
            notaReunion = itemView.findViewById(R.id.notaNotaReunionTV);
            fecha = itemView.findViewById(R.id.fechaRecyclerTrabajo);
            hora = itemView.findViewById(R.id.horaRecyclerTrabajo);
        }
    }
}
