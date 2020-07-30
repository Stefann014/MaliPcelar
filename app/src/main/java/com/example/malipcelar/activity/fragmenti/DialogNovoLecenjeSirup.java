package com.example.malipcelar.activity.fragmenti;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.malipcelar.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogNovoLecenjeSirup extends AppCompatDialogFragment {

    Button btnDatumPrihrane;
    private TextView txtLitri;
    private CheckBox chPrimeniNaSveKosnice;
    private TextView txtKolicina;

    private DialogNovoLecenjeSirupListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_novo_lecenje_layout, null);
        btnDatumPrihrane = view.findViewById(R.id.btnDatumPrihrane);
        txtLitri = view.findViewById(R.id.txtKilogramiLitri);
        chPrimeniNaSveKosnice = view.findViewById(R.id.chPrimeniPrihranuNaSveKosnice);
        txtKolicina = view.findViewById(R.id.tvKolicina);
        txtKolicina.setText("Unesite litre: ");
        Date c = Calendar.getInstance().getTime();
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        btnDatumPrihrane.setText(currentDateString);

        builder.setView(view)
                .setTitle("Unesi kolicinu (litri)")
                .setNegativeButton("Izadji", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Sacuvaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String datum = btnDatumPrihrane.getText().toString();
                        String litar = txtLitri.getText().toString();
                        boolean primeniNaSve = chPrimeniNaSveKosnice.isChecked();
                        String sirup = "Sirup";

                        if (litar.isEmpty()) {
                            return;
                        }

                        double litri = -1;
                        try {
                            litri = Double.parseDouble(litar);
                        } catch (Exception e) {
                            return;
                        }

                        if (litri < 0) {
                            return;
                        }

                        listener.sacuvajSirup(datum, litri, primeniNaSve, sirup);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogNovoLecenjeSirup.DialogNovoLecenjeSirupListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface DialogNovoLecenjeSirupListener {
        void sacuvajSirup(String datum, double litar, boolean primeniNasve, String vrstaPrihrane);
    }

}