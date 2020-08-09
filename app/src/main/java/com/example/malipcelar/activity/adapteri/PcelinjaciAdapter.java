package com.example.malipcelar.activity.adapteri;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


public class PcelinjaciAdapter extends ListAdapter<Pcelinjak, PcelinjaciAdapter.PcelinjakHolder> {
    Context context;
    private OnItemClickListener listener;
    private static final float PREFERRED_WIDTH = 250;
    private static final float PREFERRED_HEIGHT = 250;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PcelinjaciAdapter.PcelinjakHolder holder, int position) {
        Pcelinjak trenuntiPcelinjak = getItem(position);
        holder.txtRBiNazivPcelinjaka.setText(trenuntiPcelinjak.toString());
        holder.txtLokacija.setText(napraviAdresu(trenuntiPcelinjak.getLokacija()));
        holder.txtNadmorskaVisina.setText(trenuntiPcelinjak.getNadmorskaVisina() + "m");
        if (trenuntiPcelinjak.getSlika() != null && !trenuntiPcelinjak.getSlika().equals("")) {
            Bitmap mBitmap = stringToBitmap(trenuntiPcelinjak.getSlika());
            holder.pcelinjak_slika.setImageBitmap(mBitmap);
        }
    }

    private String napraviAdresu(String lokacija) {
        String adresa = "";
        LatLng latLng = srediLatLng(lokacija);

        if (latLng != null) {
            Address address = getAddressFromLatLng(latLng);
            assert address != null;
            if (address.getLocality() == null) {

                adresa += address.getSubAdminArea() + ", непозната адреса, " + address.getCountryName();

            } else {
                adresa += address.getAddressLine(0);
            }
        }


        return adresa;
    }

    class PcelinjakHolder extends RecyclerView.ViewHolder {
        private TextView txtRBiNazivPcelinjaka;
        private TextView txtLokacija;
        private TextView txtNadmorskaVisina;
        private ImageView pcelinjak_slika;

        public PcelinjakHolder(View itemView) {
            super(itemView);
            txtRBiNazivPcelinjaka = itemView.findViewById(R.id.txtRBiNaziv);
            txtLokacija = itemView.findViewById(R.id.txtLokacija);
            txtNadmorskaVisina = itemView.findViewById(R.id.txtNadmorskaVisina);
            pcelinjak_slika = itemView.findViewById(R.id.pcelinjak_slika);
            ImageView izmeni = itemView.findViewById(R.id.ic_izmeni);
            ImageView izbrisi = itemView.findViewById(R.id.ic_izbrisi);

            izmeni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onIzmeniClick(getItem(position));
                    }
                }
            });

            izbrisi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onIzbrisiClick(getItem(position));
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

        void onIzmeniClick(Pcelinjak item);

        void onIzbrisiClick(Pcelinjak item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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

    public static Bitmap resizeBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = PREFERRED_WIDTH / width;
        float scaleHeight = PREFERRED_HEIGHT / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }

}
