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
import com.example.planing1.lista.Finanzas;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NotaAdapterFinanzas extends FirestoreRecyclerAdapter<Nota, NotaAdapterFinanzas.NotaViewHolder> {

    Context context;

    public NotaAdapterFinanzas(@NonNull FirestoreRecyclerOptions<Nota> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NotaAdapterFinanzas.NotaViewHolder holder, int position, @NonNull Nota nota) {
        holder.recibo.setText(nota.recibo);
        holder.infRecibo.setText(nota.infRecibo);
        holder.fecha.setText(nota.getFecha());
        holder.hora.setText(nota.getHora());

        //---
        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, Finanzas.class);
            intent.putExtra("recibo", nota.recibo);
            intent.putExtra("infRecibo", nota.infRecibo);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }


    @NonNull
    @Override
    public NotaAdapterFinanzas.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_nota_item_finanzas, parent, false);
        return new NotaViewHolder(view);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder{

        TextView recibo, infRecibo, fecha, hora;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            recibo = itemView.findViewById(R.id.reciboTV);
            infRecibo = itemView.findViewById(R.id.informacionReciboTV);
            fecha = itemView.findViewById(R.id.fechaRecyclerFinanzas);
            hora = itemView.findViewById(R.id.horaRecyclerFinanzas);

        }
    }
}
