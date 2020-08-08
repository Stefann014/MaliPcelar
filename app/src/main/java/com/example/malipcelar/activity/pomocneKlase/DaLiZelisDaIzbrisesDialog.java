package com.example.malipcelar.activity.pomocneKlase;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.malipcelar.activity.domen.Pasa;

import java.util.Objects;

public class DaLiZelisDaIzbrisesDialog extends AppCompatDialogFragment {
    private Pasa pasa;
    private ExampleDialogListener listener;

    public DaLiZelisDaIzbrisesDialog(Pasa pasa) {
        this.pasa = pasa;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Izbrisi pašu")
                .setMessage("Da li želite da izbrišete pašu?")
                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.kliknutoDa(pasa);
                    }
                });
        return builder.create();
    }

    public interface ExampleDialogListener {
        void kliknutoDa(Pasa pasa);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement ExampleDialogListener");
        }
    }
}