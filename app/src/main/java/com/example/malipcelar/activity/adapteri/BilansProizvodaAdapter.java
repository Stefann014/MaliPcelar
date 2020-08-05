package com.example.malipcelar.activity.adapteri;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malipcelar.R;
import com.example.malipcelar.activity.domen.Pcelinjak;

public class BilansProizvodaAdapter extends ListAdapter<Pcelinjak, BilansProizvodaAdapter.BilansProizvodaHolder> {


    public BilansProizvodaAdapter() {
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
    public BilansProizvodaAdapter.BilansProizvodaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_bilans_proizvoda, parent, false);
        return new BilansProizvodaAdapter.BilansProizvodaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BilansProizvodaAdapter.BilansProizvodaHolder holder, int position) {
        Pcelinjak trenuntiPcelinjak = getItem(position);
        holder.txtRBiNazivPcelinjaka.setText(trenuntiPcelinjak.toString());
        holder.txtLokacija.setText(trenuntiPcelinjak.getLokacija());
        if (trenuntiPcelinjak.getSlika() != null && !trenuntiPcelinjak.getSlika().equals("")) {
            holder.pcelinjak_slika.setImageBitmap(stringToBitmap(trenuntiPcelinjak.getSlika()));
        }

        Log.d("PCELINJAK", trenuntiPcelinjak.getUkupnoMeda() + "");

        holder.btnUkupnoMeda.setText("Ukupno prikupljeno meda: " + trenuntiPcelinjak.getUkupnoMeda());
        holder.btnUkupnoPolena.setText("Ukupno prikupljeno polena: " + trenuntiPcelinjak.getUkupnoPolena());
        holder.btnUkupnoPropolisa.setText("Ukupno prikupljeno propolisa: " + trenuntiPcelinjak.getUkupnoPropolisa());
        holder.btnUkupnoMaticnogMleca.setText("Ukupno prikupljeno maticnog mleca: " + trenuntiPcelinjak.getUkupnoMaticnogMleca());
        holder.btnUkupnoPerge.setText("Ukupno prikupljeno perge: " + trenuntiPcelinjak.getUkupnoPrikupljenePerge());

    }

    public Pcelinjak getPcelinjakAt(int position) {
        return getItem(position);
    }

    class BilansProizvodaHolder extends RecyclerView.ViewHolder {
        private TextView txtRBiNazivPcelinjaka;
        private TextView txtLokacija;
        private ImageView pcelinjak_slika;
        private ConstraintLayout expandableView;
        private CardView cardView;

        private Button btnUkupnoMeda;
        private Button btnUkupnoPolena;
        private Button btnUkupnoPropolisa;
        private Button btnUkupnoMaticnogMleca;
        private Button btnUkupnoPerge;

        private Button btnStrelica;

        public BilansProizvodaHolder(View itemView) {
            super(itemView);

            txtRBiNazivPcelinjaka = itemView.findViewById(R.id.txtRBPcelinjaka);
            txtLokacija = itemView.findViewById(R.id.txtLokacijaPcelinjaka);
            pcelinjak_slika = itemView.findViewById(R.id.slikaBilans);

            expandableView = itemView.findViewById(R.id.prosiriviView);


            btnUkupnoMeda = itemView.findViewById(R.id.btnUkupnoMeda);
            btnUkupnoPolena = itemView.findViewById(R.id.btnUkupnoPolena);
            btnUkupnoPropolisa = itemView.findViewById(R.id.btnUkupnoPropolisa);
            btnUkupnoMaticnogMleca = itemView.findViewById(R.id.btnUkupnoMaticnogMleca);
            btnUkupnoPerge = itemView.findViewById(R.id.btnUkupnoPrikupljenePerge);
            cardView = itemView.findViewById(R.id.cardViewBilans);

            btnStrelica = itemView.findViewById(R.id.btnStrelicaDole);

            btnStrelica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableView.getVisibility() == View.GONE) {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.VISIBLE);
                        btnStrelica.setBackgroundResource(R.drawable.ic_dropdown_strelica_gore);
                    } else {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.GONE);
                        btnStrelica.setBackgroundResource(R.drawable.ic_dropdown_strelica_dole);
                    }
                }
            });

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
