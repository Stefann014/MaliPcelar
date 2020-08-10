package com.example.malipcelar.activity.fragmenti;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.malipcelar.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PogacaIliSirupBottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pogaca_ili_sirup, container, false);
        Button pogaca = v.findViewById(R.id.btnPogaca);
        Button sirup = v.findViewById(R.id.btnSirup);

        pogaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnPogacaClicked();
                dismiss();
            }
        });
        sirup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnSirupClicked();
                dismiss();
            }
        });
        return v;
    }

    public interface BottomSheetListener {
        void onBtnPogacaClicked();

        void onBtnSirupClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}