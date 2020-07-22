package com.example.malipcelar.activity.adapteri;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
import com.example.malipcelar.activity.domen.Pcelinjak;


public class PcelinjaciAdapter extends ListAdapter<Pcelinjak, PcelinjaciAdapter.PcelinjakHolder> {

    private OnItemClickListener listener;
    private OnDropdownClickListener listenerr;

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
        if (trenuntiPcelinjak.getSlika() != null && !trenuntiPcelinjak.getSlika().equals("")) {
            holder.pcelinjak_slika.setImageBitmap(stringToBitmap(trenuntiPcelinjak.getSlika()));
        }
    }

    public Pcelinjak getPcelinjakAt(int position) {
        return getItem(position);
    }

    class PcelinjakHolder extends RecyclerView.ViewHolder {
        private TextView txtRBiNazivPcelinjaka;
        private TextView txtLokacija;
        private TextView txtNadmorskaVisina;
        private ImageView pcelinjak_slika;
        private ImageView izmeni;

        public PcelinjakHolder(View itemView) {
            super(itemView);
            txtRBiNazivPcelinjaka = itemView.findViewById(R.id.txtRBiNaziv);
            txtLokacija = itemView.findViewById(R.id.txtLokacija);
            txtNadmorskaVisina = itemView.findViewById(R.id.txtNadmorskaVisina);
            pcelinjak_slika = itemView.findViewById(R.id.pcelinjak_slika);
            izmeni = itemView.findViewById(R.id.ic_izmeni);

            izmeni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listenerr != null && position != RecyclerView.NO_POSITION) {
                        listenerr.onItemClickk(getItem(position));
                    }
                }
            });

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

    public interface OnDropdownClickListener {
        void onItemClickk(Pcelinjak pcelinjak);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnIzmeniClickListener(OnDropdownClickListener listenerr) {
        this.listenerr = listenerr;
    }

    private static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
