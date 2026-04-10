package com.example.planing1.Picker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatepickerClase {

    public static void mostrarDatePicker(Context context, TextView fechaTextView) {
        // Obtener fecha actual del sistema
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Actualizar el TextView con la fecha seleccionada
                        String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                        fechaTextView.setText(fechaSeleccionada);
                    }
                }, year, month, dayOfMonth);

        // Establecer la fecha mínima como la fecha actual del sistema
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Mostrar el DatePickerDialog
        datePickerDialog.show();
    }

}
