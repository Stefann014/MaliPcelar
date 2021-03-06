package com.example.malipcelar.activity.adapteri;


import android.annotation.SuppressLint;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.pomocneKlase.KosnicaIDatumi;

import java.util.ArrayList;
import java.util.List;

public class KosniceAdapter extends ListAdapter<KosnicaIDatumi, KosniceAdapter.KosnicaHolder> implements Filterable {

    private KosniceAdapter.OnItemClickListener listener;
    private List<KosnicaIDatumi> kosnice;
    private List<KosnicaIDatumi> sveKosnice;

    public KosniceAdapter(List<KosnicaIDatumi> kosnice) {
        super(DIFF_CALLBACK);
        this.kosnice = kosnice;
        sveKosnice = new ArrayList<>(this.kosnice);
    }

    private static final DiffUtil.ItemCallback<KosnicaIDatumi> DIFF_CALLBACK = new DiffUtil.ItemCallback<KosnicaIDatumi>() {
        @Override
        public boolean areItemsTheSame(KosnicaIDatumi oldItem, KosnicaIDatumi newItem) {
            return oldItem.getRednibrojPcelinjaka() == newItem.getRednibrojPcelinjaka() && oldItem.getRedniBrojKosnice() == newItem.getRedniBrojKosnice();
        }

        @Override
        public boolean areContentsTheSame(KosnicaIDatumi oldItem, KosnicaIDatumi newItem) {

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
        KosnicaIDatumi trenutnaKosnica = getItem(position);
        holder.txtRBiNazivKosnice.setText(trenutnaKosnica.getRedniBrojKosnice() + ". " + trenutnaKosnica.getNazivKosnice());
        holder.txtPcelinjak.setText(trenutnaKosnica.getRednibrojPcelinjaka() + ". Pčelinjak");
        if (trenutnaKosnica.getMaxDatumPregleda() != null && !trenutnaKosnica.getMaxDatumPregleda().equals("")) {
            holder.btnPregled.setText(" Datum poslednjeg pregleda:\n" + datumZaPrikaz(trenutnaKosnica.getMaxDatumPregleda()));
        } else {
            holder.btnPregled.setText(" Još uvek nema unesenih pregleda ");
        }
        if (trenutnaKosnica.getMaxDatumLecenja() != null && !trenutnaKosnica.getMaxDatumLecenja().equals("")) {
            holder.btnLecenje.setText(" Datum poslednjeg lečenja:\n" + datumZaPrikaz(trenutnaKosnica.getMaxDatumLecenja()));
        } else {
            holder.btnLecenje.setText(" Još uvek nema lečenja ");
        }
        if (trenutnaKosnica.getMaxDatumPrihrane() != null && !trenutnaKosnica.getMaxDatumPrihrane().equals("")) {
            holder.btnPrihrana.setText(" Datum poslednje prihrane:\n" + datumZaPrikaz(trenutnaKosnica.getMaxDatumPrihrane()));
        } else {
            holder.btnPrihrana.setText(" Još uvek nema unesenih prihrana ");
        }
    }

    public KosnicaIDatumi getKosnicaAt(int position) {
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
        void onItemClick(KosnicaIDatumi kosnica);

        void onPregledClick(KosnicaIDatumi kosnica);

        void onLecenjeClick(KosnicaIDatumi kosnica);

        void onPrihranaClick(KosnicaIDatumi kosnica);

        void onLongItemClick(KosnicaIDatumi kosnica);
    }

    public void setOnItemClickListener(KosniceAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }

    @Override
    public int getItemCount() {
        return kosnice.size();
    }

    @Override
    public Filter getFilter() {
        return filtriraj;
    }

    private Filter filtriraj = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<KosnicaIDatumi> filtriranaLista = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filtriranaLista.addAll(sveKosnice);
            } else {
                String parametar = constraint.toString().toLowerCase().trim();
                for (KosnicaIDatumi kosnica : sveKosnice) {
                    if (kosnica.getNazivKosnice().toLowerCase().contains(parametar)) {
                        filtriranaLista.add(kosnica);
                    }
                }
            }
            FilterResults rezultat = new FilterResults();
            rezultat.values = filtriranaLista;
            return rezultat;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            kosnice.clear();
            kosnice.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
