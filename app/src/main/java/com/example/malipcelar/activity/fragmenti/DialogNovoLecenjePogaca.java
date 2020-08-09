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
import java.util.Objects;

public class DialogNovoLecenjePogaca extends AppCompatDialogFragment {

    Button btnDatumPrihrane;
    private TextView txtKilogrami;
    private CheckBox chPrimeniNaSveKosnice;

    private DialogNovoLecenjeListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_novo_lecenje_layout, null);
        btnDatumPrihrane = view.findViewById(R.id.btnDatumPrihrane);
        txtKilogrami = view.findViewById(R.id.txtKilogramiLitri);
        chPrimeniNaSveKosnice = view.findViewById(R.id.chPrimeniPrihranuNaSveKosnice);

        Date c = Calendar.getInstance().getTime();
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        btnDatumPrihrane.setText(currentDateString);

        builder.setView(view)
                .setTitle("Unesi kolicinu (kg)")
                .setNegativeButton("Izadji", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Sacuvaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String datum = btnDatumPrihrane.getText().toString();
                        String kg = txtKilogrami.getText().toString();
                        boolean primeniNaSve = chPrimeniNaSveKosnice.isChecked();
                        String pogaca = "Pogaca";

                        if (kg.isEmpty()) {
                            return;
                        }

                        double kilo;
                        try {
                            kilo = Double.parseDouble(kg);
                        } catch (Exception e) {
                            return;
                        }

                        listener.sacuvaj(datum, kilo, primeniNaSve,pogaca);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogNovoLecenjeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface DialogNovoLecenjeListener {
        void sacuvaj(String datum, double kg, boolean primeniNasve, String vrstaPrihrane);
    }


}