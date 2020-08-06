package com.example.malipcelar.activity.adapteri;

import android.annotation.SuppressLint;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.pomocneKlase.PcelinjakIDatumi;

public class IstorijaAktivnostiAdapter extends ListAdapter<PcelinjakIDatumi, IstorijaAktivnostiAdapter.IstorijaAktivnostiHolder> {

    public IstorijaAktivnostiAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<PcelinjakIDatumi> DIFF_CALLBACK;

    static {
        DIFF_CALLBACK = new DiffUtil.ItemCallback<PcelinjakIDatumi>() {
            @Override
            public boolean areItemsTheSame(@NonNull PcelinjakIDatumi oldItem, @NonNull PcelinjakIDatumi newItem) {
                return oldItem.getPcelinjak().equals(newItem.getPcelinjak());
            }

            @Override
            public boolean areContentsTheSame(@NonNull PcelinjakIDatumi oldItem, @NonNull PcelinjakIDatumi newItem) {
                return oldItem.getMaxDatumPregled().equals(newItem.getMaxDatumPregled()) && oldItem.getMaxDatumPrihrane().equals(newItem.getMaxDatumPrihrane()) && oldItem.getMaxDatumLecenja().equals(newItem.getMaxDatumLecenja());
            }
        };
    }

    @NonNull
    @Override
    public IstorijaAktivnostiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_istorija_aktivnosti, parent, false);
        return new IstorijaAktivnostiHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull IstorijaAktivnostiAdapter.IstorijaAktivnostiHolder holder, int position) {
        PcelinjakIDatumi trenutnaStavka = getItem(position);
        holder.txtRedniBrojINazivPcelinjaka.setText(trenutnaStavka.getPcelinjak() + "");
        holder.txtIstorijaAktivnosti.setText("Pogledajte poslednje aktivnosti ->");
        if (trenutnaStavka.getMaxDatumPregled() != null && !trenutnaStavka.getMaxDatumPregled().equals("")) {
            holder.btnPoslednjiPregled.setText("Datum poslednjeg pregleda: " + datumZaPrikaz(trenutnaStavka.getMaxDatumPregled()));
        } else {
            holder.btnPoslednjiPregled.setText("Jos uvek nema unesenih pregleda");
        }
        if (trenutnaStavka.getMaxDatumLecenja() != null && !trenutnaStavka.getMaxDatumLecenja().equals("")) {
            holder.btnPoslednjeLecenje.setText("Datum poslednjeg lecenja: " + datumZaPrikaz(trenutnaStavka.getMaxDatumLecenja()));
        } else {
            holder.btnPoslednjeLecenje.setText("Jos uvek nema lecenja");
        }
        if (trenutnaStavka.getMaxDatumPrihrane() != null && !trenutnaStavka.getMaxDatumPrihrane().equals("")) {
            holder.btnPoslednjaPrihrana.setText("Datum poslednje prihrane: " + datumZaPrikaz(trenutnaStavka.getMaxDatumPrihrane()));
        } else {
            holder.btnPoslednjaPrihrana.setText("Jos uvek nema unesenih prihrana");
        }
    }

    static class IstorijaAktivnostiHolder extends RecyclerView.ViewHolder {
        private TextView txtRedniBrojINazivPcelinjaka;
        private TextView txtIstorijaAktivnosti;

        private Button btnPoslednjiPregled;
        private Button btnPoslednjeLecenje;
        private Button btnPoslednjaPrihrana;
        private Button btnDropdownStrelica;

        private ConstraintLayout expandableView;
        private CardView cardView;

        public IstorijaAktivnostiHolder(View itemView) {
            super(itemView);
            txtRedniBrojINazivPcelinjaka = itemView.findViewById(R.id.txtRedniBrojINazivPcelinjaka);
            txtIstorijaAktivnosti = itemView.findViewById(R.id.txtIstorijaAktivnosti);

            expandableView = itemView.findViewById(R.id.expandableView);
            cardView = itemView.findViewById(R.id.cardViewIstorijaAktivnosti);

            btnDropdownStrelica = itemView.findViewById(R.id.btnDropdownStrelica);
            btnPoslednjiPregled = itemView.findViewById(R.id.btnPoslednjiPregled);
            btnPoslednjeLecenje = itemView.findViewById(R.id.btnPoslednjeLecenje);
            btnPoslednjaPrihrana = itemView.findViewById(R.id.btnPoslednjaPrihrana);

            btnDropdownStrelica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableView.getVisibility() == View.GONE) {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.VISIBLE);
                        btnDropdownStrelica.setBackgroundResource(R.drawable.ic_dropdown_strelica_gore);
                    } else {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.GONE);
                        btnDropdownStrelica.setBackgroundResource(R.drawable.ic_dropdown_strelica_dole);
                    }
                }
            });

        }
    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }
}
