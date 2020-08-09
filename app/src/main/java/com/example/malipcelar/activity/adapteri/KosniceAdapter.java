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
import com.example.malipcelar.activity.domen.Kosnica;

public class KosniceAdapter extends ListAdapter<Kosnica, KosniceAdapter.KosnicaHolder> {

    private KosniceAdapter.OnItemClickListener listener;

    public KosniceAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Kosnica> DIFF_CALLBACK = new DiffUtil.ItemCallback<Kosnica>() {
        @Override
        public boolean areItemsTheSame(Kosnica oldItem, Kosnica newItem) {
            return oldItem.getRednibrojPcelinjaka() == newItem.getRednibrojPcelinjaka() && oldItem.getRedniBrojKosnice() == newItem.getRedniBrojKosnice();
        }

        @Override
        public boolean areContentsTheSame(Kosnica oldItem, Kosnica newItem) {

            return oldItem.getNazivKosnice().equals(newItem.getNazivKosnice());
        }
    };

    @NonNull
    @Override
    public KosniceAdapter.KosnicaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_kosnica, parent, false);
        return new KosniceAdapter.KosnicaHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull KosniceAdapter.KosnicaHolder holder, int position) {
        Kosnica trenutnaKosnica = getItem(position);
        holder.txtRBiNazivKosnice.setText(trenutnaKosnica.getRedniBrojKosnice() + ". " + trenutnaKosnica.getNazivKosnice());
        holder.txtPcelinjak.setText(trenutnaKosnica.getRednibrojPcelinjaka() + ". Pčelinjak");
        if (trenutnaKosnica.getDatumPoslednjegPregleda() != null && !trenutnaKosnica.getDatumPoslednjegPregleda().equals("")) {
            holder.btnPregled.setText("Datum poslednjeg pregleda:\n" + datumZaPrikaz(trenutnaKosnica.getDatumPoslednjegPregleda()));
        } else {
            holder.btnPregled.setText("Još uvek nema unesenih pregleda");
        }
        if (trenutnaKosnica.getDatumPoslednjegLecenja() != null && !trenutnaKosnica.getDatumPoslednjegLecenja().equals("")) {
            holder.btnLecenje.setText("Datum poslednjeg lečenja:\n" + datumZaPrikaz(trenutnaKosnica.getDatumPoslednjegLecenja()));
        } else {
            holder.btnLecenje.setText("Još uvek nema lečenja");
        }
        if (trenutnaKosnica.getDatumPoslednjePrihrane() != null && !trenutnaKosnica.getDatumPoslednjePrihrane().equals("")) {
            holder.btnPrihrana.setText("Datum poslednje prihrane:\n" + datumZaPrikaz(trenutnaKosnica.getDatumPoslednjePrihrane()));
        } else {
            holder.btnPrihrana.setText("Još uvek nema unesenih prihrana");
        }
    }

    public Kosnica getKosnicaAt(int position) {
        return getItem(position);
    }

    class KosnicaHolder extends RecyclerView.ViewHolder {
        private TextView txtRBiNazivKosnice;
        private TextView txtPcelinjak;

        private Button btnPregled;
        private Button btnLecenje;
        private Button btnPrihrana;
        private Button btnStrelica;

        private ConstraintLayout expandableView;
        private CardView cardView;

        public KosnicaHolder(View itemView) {
            super(itemView);
            txtRBiNazivKosnice = itemView.findViewById(R.id.txtRedniBrojKosnice);
            txtPcelinjak = itemView.findViewById(R.id.txtPcelinjak);

            expandableView = itemView.findViewById(R.id.prosirivView);
            cardView = itemView.findViewById(R.id.cardViewKosnica);

            btnStrelica = itemView.findViewById(R.id.btnStrelica);
            btnPregled = itemView.findViewById(R.id.btnPregled);
            btnLecenje = itemView.findViewById(R.id.btnLecenje);
            btnPrihrana = itemView.findViewById(R.id.btnPrihrana);

            btnStrelica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableView.getVisibility() == View.GONE) {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.VISIBLE);
                        btnStrelica.setBackgroundResource(R.drawable.ic_dropdown_strelica_gore);
                    } else {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.GONE);
                        btnStrelica.setBackgroundResource(R.drawable.ic_dropdown_strelica_dole);
                    }
                }
            });

            btnPregled.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onPregledClick(getItem(position));
                    }
                }
            });

            btnLecenje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onLecenjeClick(getItem(position));
                    }
                }
            });

            btnPrihrana.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onPrihranaClick(getItem(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onLongItemClick(getItem(position));
                    }
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Kosnica kosnica);

        void onPregledClick(Kosnica kosnica);

        void onLecenjeClick(Kosnica kosnica);

        void onPrihranaClick(Kosnica kosnica);

        void onLongItemClick(Kosnica item);
    }

    public void setOnItemClickListener(KosniceAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }
}
