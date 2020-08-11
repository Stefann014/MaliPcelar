package com.example.malipcelar.activity.adapteri;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Base64;
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
import com.example.malipcelar.activity.pomocneKlase.KlasaBilans;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class BilansProizvodaAdapter extends ListAdapter<KlasaBilans, BilansProizvodaAdapter.BilansProizvodaHolder> {


    public BilansProizvodaAdapter() {
        super(DIFF_CALLBACK);
    }

    Context context;

    private static final DiffUtil.ItemCallback<KlasaBilans> DIFF_CALLBACK = new DiffUtil.ItemCallback<KlasaBilans>() {
        @Override
        public boolean areItemsTheSame(KlasaBilans oldItem, KlasaBilans newItem) {
            return oldItem.getRbINazivPcelinjaka().equals(newItem.getRbINazivPcelinjaka());
        }

        @Override
        public boolean areContentsTheSame(KlasaBilans oldItem, KlasaBilans newItem) {
            return oldItem.getLokacija().equals(newItem.getLokacija()) &&
                    oldItem.getSlikaPcelinjaka().equals(newItem.getSlikaPcelinjaka()) &&
                    oldItem.getUkupnoMeda() == newItem.getUkupnoMeda();
        }
    };

    @NonNull
    @Override
    public BilansProizvodaAdapter.BilansProizvodaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stavka_bilans_proizvoda, parent, false);
        return new BilansProizvodaHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BilansProizvodaAdapter.BilansProizvodaHolder holder, int position) {
        KlasaBilans trenuntiPcelinjak = getItem(position);
        holder.txtRBiNazivPcelinjaka.setText(trenuntiPcelinjak.getRbINazivPcelinjaka());
        String lokacija = srediLokaciju(trenuntiPcelinjak.getLokacija());
        holder.txtLokacija.setText(lokacija);

        if (trenuntiPcelinjak.getSlikaPcelinjaka() != null && !trenuntiPcelinjak.getSlikaPcelinjaka().equals("")) {
            holder.pcelinjak_slika.setImageBitmap(stringToBitmap(trenuntiPcelinjak.getSlikaPcelinjaka()));
        }

        holder.btnUkupnoMeda.setText("Ukupno prikupljeno meda: \n" + trenuntiPcelinjak.getUkupnoMeda() + " kg");
        holder.btnUkupnoPolena.setText("Ukupno prikupljeno polena: \n" + trenuntiPcelinjak.getUkupnoPolena() + " kg");
        holder.btnUkupnoPropolisa.setText("Ukupno prikupljeno propolisa: \n" + trenuntiPcelinjak.getUkupnoPropolisa() + " kg");
        holder.btnUkupnoMaticnogMleca.setText("Ukupno prikupljeno matičnog mleča: \n" + trenuntiPcelinjak.getUkupnoMaticnogMleca() + " kg");
        holder.btnUkupnoPerge.setText("Ukupno prikupljeno perge: \n" + trenuntiPcelinjak.getUkupnoPrikupljenePerge() + " kg");

    }

    private String srediLokaciju(String lokacija) {
        LatLng latLng = srediLatLng(lokacija);
        String string = "";

        if (latLng != null) {
            Address address = getAddressFromLatLng(latLng);

            assert address != null;
            if (address.getLocality() == null) {
                if (address.getSubAdminArea() != null) {
                    string = address.getSubAdminArea() + ", непозната адреса, " + address.getCountryName();
                }
            } else {
                string = address.getAddressLine(0);
            }
        }
        return string;
    }

    static class BilansProizvodaHolder extends RecyclerView.ViewHolder {
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

    private LatLng srediLatLng(String lokacija) {
        String[] lokacije = lokacija.split(",");
        String lok1 = lokacije[0] + "";
        String lok2 = lokacije[1] + "";

        if (lok1.equals("null") || lok2.equals("null")) {
            return null;
        }
        double latitude = Double.parseDouble(lokacije[0]);
        double longitude = Double.parseDouble(lokacije[1]);
        return new LatLng(latitude, longitude);
    }

    private Address getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
            if (addresses != null) {
                return addresses.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }
}
