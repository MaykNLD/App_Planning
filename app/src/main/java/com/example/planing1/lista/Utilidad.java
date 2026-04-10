package com.example.planing1.lista;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;

public class Utilidad {

    static void showToast(Context context, String mensaje){
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionReferenceNotas(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notas")
                .document(firebaseUser.getUid()).collection("mis_notas");
    }

    static CollectionReference getCollectionReferenceNotaOcio(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        return FirebaseFirestore.getInstance().collection("notas")
                .document(firebaseUser.getUid()).collection("mis_notas_ocio");
    }

    static CollectionReference getCollectionReferenceNotaTrabajo(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        return FirebaseFirestore.getInstance().collection("notas")
                .document(firebaseUser.getUid()).collection("mis_notas_trabajo");
    }

    static CollectionReference getCollectionReferenceFinanzas(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notas")
                .document(firebaseUser.getUid()).collection("mis_notas_finanzas");
    }

    static CollectionReference getCollectionReferenceNotaCitasMedicas(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        return FirebaseFirestore.getInstance().collection("notas")
                .document(firebaseUser.getUid()).collection("mis_notas_citas_medicas");
    }

    static CollectionReference getCollectionReferenceNotaHogar(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        return FirebaseFirestore.getInstance().collection("notas")
                .document(firebaseUser.getUid()).collection("mis_notas_hogar");
    }

    @SuppressLint("SimpleDateFormat")
    static String timestampToString(Timestamp timestamp) {

        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }


}
