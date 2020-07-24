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
import com.example.malipcelar.activity.domen.Kosnica;

public class KosniceAdapter extends ListAdapter<Kosnica, KosniceAdapter.KosnicaHolder> {

    private KosniceAdapter.OnItemClickListener listener;

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
        //holder.txtGodina.setText(trenutnaKosnica.getGodinaProizvodnjeMatice());
        holder.txtPcelinjak.setText(trenutnaKosnica.getRednibrojPcelinjaka()+" pcelinjak");
    }

    public Kosnica getKosnicaAt(int position) {
        return getItem(position);
    }

    class KosnicaHolder extends RecyclerView.ViewHolder {
        private TextView txtRBiNazivKosnice;
        private TextView txtPcelinjak;
        //private TextView txtGodina;

        public KosnicaHolder(View itemView) {
            super(itemView);
            txtRBiNazivKosnice = itemView.findViewById(R.id.txtRedniBrojKosnice);
            txtPcelinjak = itemView.findViewById(R.id.txtPcelinjak);
            //txtGodina = itemView.findViewById(R.id.txtGodina);

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
}
