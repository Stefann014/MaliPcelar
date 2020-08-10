package com.example.malipcelar.activity.adapteri;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Pregled;

public class PregledAdapter extends ListAdapter<Pregled, PregledAdapter.PregledHolder> {

    private PregledAdapter.OnItemClickListener listener;

    public PregledAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Pregled> DIFF_CALLBACK = new DiffUtil.ItemCallback<Pregled>() {
        @Override
        public boolean areItemsTheSame(Pregled oldItem, Pregled newItem) {
            return oldItem.getPregledID() == newItem.getPregledID();
        }

        @Override
        public boolean areContentsTheSame(Pregled oldItem, Pregled newItem) {
            return oldItem.getPcelinjakID() == newItem.getPcelinjakID() && oldItem.getKosnicaID() == newItem.getKosnicaID()
                    && oldItem.getNapomena().equals(newItem.getNapomena()) && oldItem.getDatumPregleda().equals(newItem.getDatumPregleda())
                    && oldItem.getBrojIzvadjenihRamovaSaMedom() == newItem.getBrojIzvadjenihRamovaSaMedom();
        }
    };

    @NonNull
    @Override
    public PregledAdapter.PregledHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_pregled, parent, false);
        return new PregledAdapter.PregledHolder(itemView);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PregledAdapter.PregledHolder holder, int position) {
        Pregled trenutniPregled = getItem(position);

        holder.txtPcelinjakIKosnica.setText(trenutniPregled.getKosnicaID() + ". košnica u " + trenutniPregled.getPcelinjakID() + ". pčelinjaku");
        holder.txtNapomena.setText("Napomena: " + trenutniPregled.getNapomena());
        holder.txtDatumPregleda.setText(datumZaPrikaz(trenutniPregled.getDatumPregleda()));
    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }

    public Pregled getPregledAt(int position) {
        return getItem(position);
    }

    class PregledHolder extends RecyclerView.ViewHolder {
        private TextView txtPcelinjakIKosnica;
        private TextView txtNapomena;
        private TextView txtDatumPregleda;

        public PregledHolder(View itemView) {
            super(itemView);
            txtPcelinjakIKosnica = itemView.findViewById(R.id.txtPcelinjakIKosnica);
            txtNapomena = itemView.findViewById(R.id.txttxt);
            txtDatumPregleda = itemView.findViewById(R.id.txtDatumPregleda);

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
                        listener.onLongItemClick(getItem(position));
                    }
                    return false;
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Pregled pregled);

        void onLongItemClick(Pregled pregled);
    }

    public void setOnItemClickListener(PregledAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
