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
            return oldItem.getPregledID() == newItem.getPregledID();
        }
    };

    @NonNull
    @Override
    public PregledAdapter.PregledHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_pregled, parent, false);
        return new PregledAdapter.PregledHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull PregledAdapter.PregledHolder holder, int position) {
        Pregled trenutniPregled = getItem(position);

        holder.txtDatum.setText("Datum: ");
        holder.txtSmisliti.setText("Nez jos");
        holder.txtDatumPregleda.setText(datumZaPrikaz(trenutniPregled.getDatumPregleda()));

    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0];
        return dobarDatum;
    }

    public Pregled getPregledAt(int position) {
        return getItem(position);
    }

    class PregledHolder extends RecyclerView.ViewHolder {
        private TextView txtDatum;
        private TextView txtSmisliti;
        private TextView txtDatumPregleda;

        public PregledHolder(View itemView) {
            super(itemView);
            txtDatum = itemView.findViewById(R.id.txtDatumiPregleda);
            txtSmisliti = itemView.findViewById(R.id.txttxt);
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
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Pregled pregled);
    }

    public void setOnItemClickListener(PregledAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
