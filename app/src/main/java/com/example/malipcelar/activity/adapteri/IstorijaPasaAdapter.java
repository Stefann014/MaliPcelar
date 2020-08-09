package com.example.malipcelar.activity.adapteri;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Pasa;

public class IstorijaPasaAdapter extends ListAdapter<Pasa, IstorijaPasaAdapter.PasaHolder> {

    private IstorijaPasaAdapter.OnItemClickListener listener;

    public IstorijaPasaAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Pasa> DIFF_CALLBACK = new DiffUtil.ItemCallback<Pasa>() {
        @Override
        public boolean areItemsTheSame(Pasa oldItem, Pasa newItem) {
            return oldItem.getPasaID() == newItem.getPasaID();
        }

        @Override
        public boolean areContentsTheSame(Pasa oldItem, Pasa newItem) {
            return oldItem.getDatumOd().equals(newItem.getDatumOd()) &&
                    oldItem.getDatumDo().equals(newItem.getDatumDo()) && oldItem.getPcelinjakID() ==
                    newItem.getPcelinjakID() && oldItem.getPrikupljenoMeda() == newItem.getPrikupljenoMeda();
        }
    };

    @NonNull
    @Override
    public IstorijaPasaAdapter.PasaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_pasa, parent, false);
        return new IstorijaPasaAdapter.PasaHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull IstorijaPasaAdapter.PasaHolder holder, int position) {
        Pasa trenutnaPasa = getItem(position);

        Log.d("DATUMOD", trenutnaPasa.getDatumOd());
        Log.d("DATUMDO", trenutnaPasa.getDatumDo());

        holder.txtDatumOd.setText(datumZaPrikaz(trenutnaPasa.getDatumOd()));
        holder.txtDatumDo.setText(datumZaPrikaz(trenutnaPasa.getDatumDo()));

    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }

    class PasaHolder extends RecyclerView.ViewHolder {
        private TextView txtDatumOd;
        private TextView txtDatumDo;

        public PasaHolder(View itemView) {
            super(itemView);

            txtDatumOd = itemView.findViewById(R.id.txtDatumOd);
            txtDatumDo = itemView.findViewById(R.id.txtDatumDo);
            ImageView slikaIzbrisi = itemView.findViewById(R.id.slikaKanta);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onLongItemClick((getItem(position)));
                    }
                    return false;
                }
            });
            
            slikaIzbrisi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.izbrisiPasu((getItem(position)));
                    }

                }
            });
            
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Pasa pasa);

        void onLongItemClick(Pasa pasa);

        void izbrisiPasu(Pasa item);
    }

    public void setOnItemClickListener(IstorijaPasaAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
