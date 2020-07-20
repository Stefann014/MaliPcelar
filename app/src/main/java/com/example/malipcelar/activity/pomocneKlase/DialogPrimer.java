package com.example.malipcelar.activity.pomocneKlase;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.malipcelar.activity.activity.PcelinjaciActivity;
import com.example.malipcelar.activity.domen.Pcelinjak;

public class DialogPrimer extends AppCompatDialogFragment {

    Pcelinjak pcelinjak;

    public DialogPrimer(Pcelinjak pcelinjak) {
        this.pcelinjak = pcelinjak;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Da li zelite da izmenite ili izbrisete pcelinjak?")
                .setMessage("This is a Dialog")
                .setPositiveButton("Izmeni", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("Izbrisi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

}
