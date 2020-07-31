package com.example.malipcelar.activity.adapteri;


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
    private OnPregledClickListener listener1;
    private OnLecenjeClickListener listener2;
    private OnPrihranaClickListener listener3;

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

    @Override
    public void onBindViewHolder(@NonNull KosniceAdapter.KosnicaHolder holder, int position) {
        Kosnica trenutnaKosnica = getItem(position);
        holder.txtRBiNazivKosnice.setText(trenutnaKosnica.getRedniBrojKosnice() + ". " + trenutnaKosnica.getNazivKosnice());
        holder.txtPcelinjak.setText(trenutnaKosnica.getRednibrojPcelinjaka() + " pcelinjak");
        if (trenutnaKosnica.getDatumPoslednjegPregleda() != null && !trenutnaKosnica.getDatumPoslednjegPregleda().equals("")) {
            holder.btnPregled.setText("Datum poslednjeg pregleda: " + datumZaPrikaz(trenutnaKosnica.getDatumPoslednjegPregleda()));
        } else {
            holder.btnPregled.setText("Jos uvek nema unesenih pregleda");
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
                    if (listener1 != null && position != RecyclerView.NO_POSITION) {
                        listener1.onPregledClick(getItem(position));
                    }
                }
            });

            btnLecenje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener2 != null && position != RecyclerView.NO_POSITION) {
                        listener2.onLecenjeClick(getItem(position));
                    }
                }
            });

            btnPrihrana.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener3 != null && position != RecyclerView.NO_POSITION) {
                        listener3.onPrihranaClick(getItem(position));
                    }
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
    }

    public void setOnItemClickListener(KosniceAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnPregledClickListener {
        void onPregledClick(Kosnica kosnica);
    }

    public interface OnLecenjeClickListener {
        void onLecenjeClick(Kosnica kosnica);
    }


    public interface OnPrihranaClickListener {
        void onPrihranaClick(Kosnica kosnica);
    }

    public void onPregledClickListener(OnPregledClickListener listener1) {
        this.listener1 = listener1;
    }

    public void onLecenjeClickListener(OnLecenjeClickListener listener2) {
        this.listener2 = listener2;
    }

    public void onPrihranaClickListener(OnPrihranaClickListener listener3) {
        this.listener3 = listener3;
    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0];
        return dobarDatum;
    }
}
