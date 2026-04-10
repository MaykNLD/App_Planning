package com.example.planing1.Picker;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimepickerClase {

    public static void mostrarTimePicker(Context context, TextView textViewHora) {
        // Obtiene la hora actual
        Calendar calendar = Calendar.getInstance();
        int horaActual = calendar.get(Calendar.HOUR_OF_DAY);
        int minutoActual = calendar.get(Calendar.MINUTE);

        // Crea un TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Formatea la hora seleccionada
                        String horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute);
                        // Muestra la hora seleccionada en el TextView
                        textViewHora.setText(horaSeleccionada);
                    }
                },
                horaActual,
                minutoActual,
                false
        );

        // Muestra el TimePickerDialog
        timePickerDialog.show();
    }




}
