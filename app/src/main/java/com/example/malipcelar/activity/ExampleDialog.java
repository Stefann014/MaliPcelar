package com.example.malipcelar.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.malipcelar.R;

public class ExampleDialog extends AppCompatDialogFragment {

    Button btnDatumPrihrane;
    private TextView txtKilogrami;

    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
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
                        listener.sacuvaj(datum, kg);
                    }
                });
        btnDatumPrihrane = view.findViewById(R.id.btnDatumPrihrane);
        txtKilogrami = view.findViewById(R.id.txtKilogrami);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void sacuvaj(String datum, String kg);
    }
}