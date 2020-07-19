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
import com.example.malipcelar.activity.domen.Pcelinjak;


public class PcelinjaciAdapter extends ListAdapter<Pcelinjak, PcelinjaciAdapter.PcelinjakHolder> {

    private OnItemClickListener listener;

    public PcelinjaciAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Pcelinjak> DIFF_CALLBACK = new DiffUtil.ItemCallback<Pcelinjak>() {
        @Override
        public boolean areItemsTheSame(Pcelinjak oldItem, Pcelinjak newItem) {
            return oldItem.getRedniBrojPcelinjaka() == newItem.getRedniBrojPcelinjaka();
        }

        @Override
        public boolean areContentsTheSame(Pcelinjak oldItem, Pcelinjak newItem) {
            return oldItem.getNazivPcelinjaka().equals(newItem.getNazivPcelinjaka()) &&
                    oldItem.getLokacija().equals(newItem.getLokacija()) &&
                    oldItem.getNadmorskaVisina().equals(newItem.getNadmorskaVisina());
        }
    };

    @NonNull
    @Override
    public PcelinjaciAdapter.PcelinjakHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_pcelinjak, parent, false);
        return new PcelinjaciAdapter.PcelinjakHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PcelinjaciAdapter.PcelinjakHolder holder, int position) {
        Pcelinjak trenuntiPcelinjak = getItem(position);
        holder.txtRBiNazivPcelinjaka.setText(trenuntiPcelinjak.toString());
        holder.txtLokacija.setText(trenuntiPcelinjak.getLokacija());
        holder.txtNadmorskaVisina.setText(trenuntiPcelinjak.getNadmorskaVisina() + "m");
    }

    public Pcelinjak getPcelinjakAt(int position) {
        return getItem(position);
    }

    class PcelinjakHolder extends RecyclerView.ViewHolder {
        private TextView txtRBiNazivPcelinjaka;
        private TextView txtLokacija;
        private TextView txtNadmorskaVisina;

        public PcelinjakHolder(View itemView) {
            super(itemView);
            txtRBiNazivPcelinjaka = itemView.findViewById(R.id.txtRBiNaziv);
            txtLokacija = itemView.findViewById(R.id.txtLokacija);
            txtNadmorskaVisina = itemView.findViewById(R.id.txtNadmorskaVisina);

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
        void onItemClick(Pcelinjak pcelinjak);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
