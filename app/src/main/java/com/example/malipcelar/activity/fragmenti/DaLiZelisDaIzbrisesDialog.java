package com.example.malipcelar.activity.fragmenti;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.malipcelar.activity.domen.Pasa;
import com.example.malipcelar.activity.domen.Pcelinjak;

import java.util.Objects;

public class DaLiZelisDaIzbrisesDialog extends AppCompatDialogFragment {
    private Pasa pasa = null;
    private Pcelinjak pcelinjak = null;
    private ExampleDialogListener listener;

    public DaLiZelisDaIzbrisesDialog(Pasa pasa) {
        this.pasa = pasa;
    }

    public DaLiZelisDaIzbrisesDialog(Pcelinjak pcelinjak) {
        this.pcelinjak = pcelinjak;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (pasa != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            builder.setTitle("Izbriši pašu")
                    .setMessage("\nDa li želite da izbrišete pašu?")
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
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            builder.setTitle("Izbriši pčelinjak")
                    .setMessage("\nDa li želite da izbrišete pčelinjak?")
                    .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.kliknutoDa(pcelinjak);
                        }
                    });
            return builder.create();
        }
    }

    public interface ExampleDialogListener {
        void kliknutoDa(Pasa pasa);

        void kliknutoDa(Pcelinjak pcelinjak);
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