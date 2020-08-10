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
import com.example.malipcelar.activity.domen.Lecenje;

public class LecenjeAdapter extends ListAdapter<Lecenje, LecenjeAdapter.LecenjeHolder> {

    private LecenjeAdapter.OnItemClickListener listener;

    public LecenjeAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Lecenje> DIFF_CALLBACK = new DiffUtil.ItemCallback<Lecenje>() {
        @Override
        public boolean areItemsTheSame(Lecenje oldItem, Lecenje newItem) {
            return oldItem.getLecenjeID() == newItem.getLecenjeID();
        }

        @Override
        public boolean areContentsTheSame(Lecenje oldItem, Lecenje newItem) {
            return oldItem.getDatumLecenja().equals(newItem.getDatumLecenja()) &&
                    oldItem.getBolest().equals(newItem.getBolest()) && oldItem.getPcelinjakID() ==
                    newItem.getPcelinjakID() && oldItem.getKosnicaID() == newItem.getKosnicaID();
        }
    };

    @NonNull
    @Override
    public LecenjeAdapter.LecenjeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_lecenje, parent, false);
        return new LecenjeAdapter.LecenjeHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LecenjeAdapter.LecenjeHolder holder, int position) {
        Lecenje trenutnoLecenje = getItem(position);

        holder.txtPcelinjakIKosnica.setText(" " + trenutnoLecenje.getKosnicaID() + ". košnica u " + trenutnoLecenje.getPcelinjakID() + ". pčelinjaku ");
        holder.txtBolest.setText(" Bolest: " + trenutnoLecenje.getBolest() + " ");
        holder.txtDatumLecenja.setText(" " + datumZaPrikaz(trenutnoLecenje.getDatumLecenja()) + " ");

    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }

    public Lecenje getLecenjeAt(int position) {
        return getItem(position);
    }

    class LecenjeHolder extends RecyclerView.ViewHolder {
        private TextView txtPcelinjakIKosnica;
        private TextView txtBolest;
        private TextView txtDatumLecenja;

        public LecenjeHolder(View itemView) {
            super(itemView);
            txtPcelinjakIKosnica = itemView.findViewById(R.id.txtPcelinjakKosnica);
            txtBolest = itemView.findViewById(R.id.txtBolestLecenje);
            txtDatumLecenja = itemView.findViewById(R.id.txtDatumLecenja);

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
        void onItemClick(Lecenje lecenje);

        void onLongItemClick(Lecenje lecenje);
    }

    public void setOnItemClickListener(LecenjeAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
