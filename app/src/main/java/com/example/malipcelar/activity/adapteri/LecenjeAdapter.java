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
            return oldItem.getLecenjeID() == newItem.getLecenjeID();
        }
    };

    @NonNull
    @Override
    public LecenjeAdapter.LecenjeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_lecenje, parent, false);
        return new LecenjeAdapter.LecenjeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LecenjeAdapter.LecenjeHolder holder, int position) {
        Lecenje trenutnoLecenje = getItem(position);

        holder.txtDatum.setText("Datum lecenja: ");
        holder.txtSmisliti.setText("Nez jos");
        holder.txtDatumLecenja.setText(datumZaPrikaz(trenutnoLecenje.getDatumLecenja()));

    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        String dobarDatum = datumi[2] + "." + datumi[1] + "." + datumi[0];
        return dobarDatum;
    }

    public Lecenje getLecenjeAt(int position) {
        return getItem(position);
    }

    class LecenjeHolder extends RecyclerView.ViewHolder {
        private TextView txtDatum;
        private TextView txtSmisliti;
        private TextView txtDatumLecenja;

        public LecenjeHolder(View itemView) {
            super(itemView);
            txtDatum = itemView.findViewById(R.id.textDatumiLecenja);
            txtSmisliti = itemView.findViewById(R.id.txtSmislicemo);
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
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Lecenje lecenje);
    }

    public void setOnItemClickListener(LecenjeAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
