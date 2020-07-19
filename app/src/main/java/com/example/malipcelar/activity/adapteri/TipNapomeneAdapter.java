package com.example.malipcelar.activity.adapteri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.pomocneKlase.TipNapomeneZaSpinner;
import java.util.ArrayList;

public class TipNapomeneAdapter extends ArrayAdapter<TipNapomeneZaSpinner> {
    public TipNapomeneAdapter(Context context, ArrayList<TipNapomeneZaSpinner> tipNapomeneZaSpinner) {
        super(context, 0, tipNapomeneZaSpinner);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.opste_napomene_spinner, parent, false
            );
        }
        //pocetni
        ImageView imageViewFlag = convertView.findViewById(R.id.slikaTipOpsteNapomene);
        TextView textViewName = convertView.findViewById(R.id.txtTipOpsteNapomene);

        TipNapomeneZaSpinner tipNapomeneZaSpinner = getItem(position);

        if (tipNapomeneZaSpinner != null) {
            imageViewFlag.setImageResource(tipNapomeneZaSpinner.getSlikaTipaNapomene());
            textViewName.setText(tipNapomeneZaSpinner.getVrstaTipaNapomene());
        }

        return convertView;
    }
}
