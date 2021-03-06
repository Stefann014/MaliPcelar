package com.example.malipcelar.activity.adapteri;

import android.annotation.SuppressLint;
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

public class PcelinjakAdresaAdapter extends ListAdapter<Pcelinjak, PcelinjakAdresaAdapter.PcelinjakHolder> {


    public PcelinjakAdresaAdapter() {
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
    public PcelinjakAdresaAdapter.PcelinjakHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_pcelinjak_adresa, parent, false);
        return new PcelinjakHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PcelinjakAdresaAdapter.PcelinjakHolder holder, int position) {
        Pcelinjak trenuntiPcelinjak = getItem(position);
        holder.txtRBiNazivPcelinjaka.setText(trenuntiPcelinjak.toString());
        holder.txtLokacija.setText(trenuntiPcelinjak.getLokacija());
        holder.txtNadmorskaVisina.setText(trenuntiPcelinjak.getNadmorskaVisina() + "m");
        if (trenuntiPcelinjak.getSlika() != null && !trenuntiPcelinjak.getSlika().equals("")) {
            holder.pcelinjak_slika.setImageBitmap(stringToBitmap(trenuntiPcelinjak.getSlika()));
        }
    }

    static class PcelinjakHolder extends RecyclerView.ViewHolder {
        private TextView txtRBiNazivPcelinjaka;
        private TextView txtLokacija;
        private TextView txtNadmorskaVisina;
        private ImageView pcelinjak_slika;

        public PcelinjakHolder(View itemView) {
            super(itemView);
            txtRBiNazivPcelinjaka = itemView.findViewById(R.id.txtRBiNaziv_adresa);
            txtLokacija = itemView.findViewById(R.id.txtLokacija_adresa);
            txtNadmorskaVisina = itemView.findViewById(R.id.txtNadmorskaVisina_adresa);
            pcelinjak_slika = itemView.findViewById(R.id.slikaPcelinjaka);
        }

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
