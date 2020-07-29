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
import com.example.malipcelar.activity.domen.Prihrana;

public class PrihranaAdapter extends ListAdapter<Prihrana, PrihranaAdapter.PrihranaHolder> {

    private PrihranaAdapter.OnItemClickListener listener;

    public PrihranaAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Prihrana> DIFF_CALLBACK = new DiffUtil.ItemCallback<Prihrana>() {
        @Override
        public boolean areItemsTheSame(Prihrana oldItem, Prihrana newItem) {
            return oldItem.getPrihranaID() == newItem.getPrihranaID();
        }

        @Override
        public boolean areContentsTheSame(Prihrana oldItem, Prihrana newItem) {
            return oldItem.getDatumPrihrane().equals(newItem.getDatumPrihrane()) &&
                    oldItem.getVrstaPrihrane().equals(newItem.getVrstaPrihrane()) && oldItem.getPcelinjakID() ==
                    newItem.getPcelinjakID() && oldItem.getKosnicaID() == newItem.getKosnicaID() && oldItem.getKolicinaPrihrane() == newItem.getKolicinaPrihrane();
        }
    };

    @NonNull
    @Override
    public PrihranaAdapter.PrihranaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_prihrane, parent, false);
        return new PrihranaAdapter.PrihranaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrihranaAdapter.PrihranaHolder holder, int position) {
        Prihrana trenutnaPrihrana = getItem(position);

        holder.txtDatum.setText("Datum lecenja: ");
        holder.txtSmisliti.setText("Nez jos");
        holder.txtDatumPrihrane.setText(datumZaPrikaz(trenutnaPrihrana.getDatumPrihrane()));

    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0];
        return dobarDatum;
    }

    public Prihrana getPrihranaAt(int position) {
        return getItem(position);
    }

    class PrihranaHolder extends RecyclerView.ViewHolder {
        private TextView txtDatum;
        private TextView txtSmisliti;
        private TextView txtDatumPrihrane;

        public PrihranaHolder(View itemView) {
            super(itemView);
            txtDatum = itemView.findViewById(R.id.textDatumiPrihrane);
            txtSmisliti = itemView.findViewById(R.id.txtSmislicemoPrihranu);
            txtDatumPrihrane = itemView.findViewById(R.id.txtDatumPrihrane);

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
        void onItemClick(Prihrana prihrana);
    }

    public void setOnItemClickListener(PrihranaAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
