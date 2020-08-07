package com.example.malipcelar.activity.adapteri;

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
import com.example.malipcelar.activity.domen.OpstaNapomena;

public class OpsteNapomeneAdapter extends ListAdapter<OpstaNapomena, OpsteNapomeneAdapter.OpstaNapomenaHolder> {

    private OnItemClickListener listener;

    public OpsteNapomeneAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<OpstaNapomena> DIFF_CALLBACK = new DiffUtil.ItemCallback<OpstaNapomena>() {
        @Override
        public boolean areItemsTheSame(OpstaNapomena oldItem, OpstaNapomena newItem) {
            return oldItem.getOpstaNapomenaID() == newItem.getOpstaNapomenaID();
        }

        @Override
        public boolean areContentsTheSame(OpstaNapomena oldItem, OpstaNapomena newItem) {
            return oldItem.getTipOpsteNapomene().equals(newItem.getTipOpsteNapomene()) &&
                    oldItem.getOpstaNapomena().equals(newItem.getOpstaNapomena()) &&
                    oldItem.getDatumNapomene().equals(newItem.getDatumNapomene());
        }
    };

    @NonNull
    @Override
    public OpstaNapomenaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_opsta_napomena, parent, false);
        return new OpstaNapomenaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OpstaNapomenaHolder holder, int position) {
        OpstaNapomena trenutnaOpstaNapomena = getItem(position);
        holder.txtTipOpsteNapomene.setText(trenutnaOpstaNapomena.getTipOpsteNapomene());
        holder.txtNapomena.setText(trenutnaOpstaNapomena.getOpstaNapomena());
        holder.txtDatumOpsteNapomene.setText(datumZaPrikaz(trenutnaOpstaNapomena.getDatumNapomene()));
        if (trenutnaOpstaNapomena.getTipOpsteNapomene().equals("Opšte")) {
            holder.slikaNapomene.setImageResource(R.drawable.tip_napomene_spinner_opste);
        }
        if (trenutnaOpstaNapomena.getTipOpsteNapomene().equals("Košnica")) {
            holder.slikaNapomene.setImageResource(R.drawable.tip_napomene_spinner_kosnica);
        }
        if (trenutnaOpstaNapomena.getTipOpsteNapomene().equals("Pčelinjak")) {
            holder.slikaNapomene.setImageResource(R.drawable.tip_napomene_spinner_pcelinjak);
        }
        if (trenutnaOpstaNapomena.getTipOpsteNapomene().equals("Hitno")) {
            holder.slikaNapomene.setImageResource(R.drawable.tip_napomene_spinner_veomavazno);
        }
    }

    private String datumZaPrikaz(String datum) {
        String[] datumi = datum.split("-");
        return datumi[2] + "." + datumi[1] + "." + datumi[0];
    }

    public OpstaNapomena getOpstaNapomenaAt(int position) {
        return getItem(position);
    }

    class OpstaNapomenaHolder extends RecyclerView.ViewHolder {
        private TextView txtTipOpsteNapomene;
        private TextView txtNapomena;
        private TextView txtDatumOpsteNapomene;

        private ImageView slikaNapomene;

        public OpstaNapomenaHolder(View itemView) {
            super(itemView);
            txtTipOpsteNapomene = itemView.findViewById(R.id.txtTipOpsteNapomene);
            txtNapomena = itemView.findViewById(R.id.txtOpstaNapomenaKartica);
            txtDatumOpsteNapomene = itemView.findViewById(R.id.txtDatumOpsteNapomene);
            slikaNapomene = itemView.findViewById(R.id.slikaNapomene);

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
        void onItemClick(OpstaNapomena opstaNapomena);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
