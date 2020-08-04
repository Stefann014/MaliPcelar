package com.example.malipcelar.activity.adapteri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Pasa;

public class PasaAdapter extends ListAdapter<Pasa, PasaAdapter.PasaHolder> {

    private PasaAdapter.OnItemClickListener listener;

    public PasaAdapter() {
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
    public PasaAdapter.PasaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_pasa, parent, false);
        return new PasaAdapter.PasaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PasaAdapter.PasaHolder holder, int position) {
        Pasa trenutnaPasa = getItem(position);

        holder.txtDatumOd.setText("Datum od: " + datumZaPrikaz(trenutnaPasa.getDatumOd()));
        holder.txtSmisliti.setText("Nez jos");
        holder.txtDatumDo.setText("Datum do: " + datumZaPrikaz(trenutnaPasa.getDatumDo()));

    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0];
        return dobarDatum;
    }

    public Pasa getPasaAt(int position) {
        return getItem(position);
    }

    class PasaHolder extends RecyclerView.ViewHolder {
        private TextView txtDatumOd;
        private TextView txtSmisliti;
        private TextView txtDatumDo;

        public PasaHolder(View itemView) {
            super(itemView);
            txtDatumOd = itemView.findViewById(R.id.txtDatumOd);
            txtSmisliti = itemView.findViewById(R.id.txtCeVidimo);
            txtDatumDo = itemView.findViewById(R.id.txtDatumDo);

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
        void onItemClick(Pasa pasa);
    }

    public void setOnItemClickListener(PasaAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


}
