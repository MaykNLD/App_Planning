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
import com.example.planing1.lista.CitasMedicas;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NotaAdapterCitasMedicas extends FirestoreRecyclerAdapter<Nota, NotaAdapterCitasMedicas.NotaViewHolder> {


    Context context;

    public NotaAdapterCitasMedicas(@NonNull FirestoreRecyclerOptions<Nota> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NotaViewHolder holder, int position, @NonNull Nota nota) {
        holder.centro.setText(nota.centro);
        holder.especialista.setText(nota.especialista);
        holder.descripcion.setText(nota.descripcion);
        holder.fecha.setText(nota.getFecha());
        holder.hora.setText(nota.getHora());


        //_________________
        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, CitasMedicas.class);
            intent.putExtra("centro", nota.centro);
            intent.putExtra("especialista", nota.especialista);
            intent.putExtra("descripcion", nota.descripcion);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_nota_item_citas_medicas, parent, false);
        return new NotaViewHolder(view);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder{

        TextView centro, especialista, descripcion,fecha, hora;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            centro = itemView.findViewById(R.id.centroTV);
            especialista = itemView.findViewById(R.id.especialistaTV);
            descripcion = itemView.findViewById(R.id.descripcionCitaMedicaTV);
            fecha =itemView.findViewById(R.id.fechaRecyclerCm);
            hora =itemView.findViewById(R.id.horaRecyclerCm);
        }
    }
}
