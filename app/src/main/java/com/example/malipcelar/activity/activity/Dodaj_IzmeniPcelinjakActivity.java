package com.example.malipcelar.activity.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.malipcelar.R;
import com.example.malipcelar.activity.adapteri.MestaAutoCompleteAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dodaj_IzmeniPcelinjakActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final String TAG = "Dodaj_IzmeniPcelinjakActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_ZAHTEV = 1234;
    private static final int DEFAULT_ZOOM = 15;
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final float PREFERRED_WIDTH = 120;
    private static final float PREFERRED_HEIGHT = 105;

    public static final String EXTRA_RB =
            "com.example.malipcelar.activity.activity.EXTRA_RB";
    public static final String EXTRA_NAZIV_PCELINJAKA =
            "com.example.malipcelar.activity.activity.EXTRA_NAZIV_PCELINJAKA";
    public static final String EXTRA_LOKACIJA =
            "com.example.malipcelar.activity.activity.EXTRA_LOKACIJA";
    public static final String EXTRA_NADMORSKA_VISINA =
            "com.example.malipcelar.activity.activity.EXTRA_NADMORSKA_VISINA";
    public static final String EXTRA_SLIKA =
            "com.example.malipcelar.activity.activity.EXTRA_SLIKA";
    public static final String EXTRA_ZAUZETI_RB =
            "com.example.malipcelar.activity.activity.EXTRA_ZAUZETI_RB";


    //widgets
    private AutoCompleteTextView autoCompleteTV;
    private ImageView mGps;
    private ImageView mInfo;
    private ImageView mSkini;
    private ImageView pcelinjakSlika;

    //vars
    private Boolean mLokacijaDozvoljena;
    GoogleMap mMap;
    private FusedLocationProviderClient mProvajderLokacije;
    MestaAutoCompleteAdapter mestaAutoCompleteAdapter;
    private Marker mMarker;
    SupportMapFragment mapFragment;
    TextView txtRedniBroj;
    TextView txtNaziv;
    Button btnSacuvaj;
    Button btnDodajIzGalerije;
    Button btnSlikaj;
    private RequestQueue mQueue;
    double nVisina;
    List<Integer> zauzetiRBovi;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLokacijaDozvoljena) {
            getLokacijaUredjaja();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            inicijalizacijaZaMapu();
        }
        if (isOnline()) {
            srediIntentMapa();
            //DODATI OBAVESTENJE AKO NIJE ONLINE DA NE MOZE DA MENJA LOKACIJU
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj__izmeni_pcelinjak_activity);

        srediAtribute();
        srediListenere();
        getDozvolaZaLokaciju();
        srediIntentBezMape();
    }

    private void sacuvajPcelinjak() {
        String rb = txtRedniBroj.getText().toString().trim();
        int redniBroj = -1;
        try {
            redniBroj = Integer.parseInt(rb);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Molimo unesite brojnu vrednost", Toast.LENGTH_LONG).show();
            return;
        }

        if (redniBroj == (getIntent().getIntExtra(EXTRA_RB, -1))) {
            //izmena

            String naziv = txtNaziv.getText().toString().trim();

            Double latituda = null;
            Double longituda = null;

            if (mMarker != null) {
                latituda = mMarker.getPosition().latitude;
                longituda = mMarker.getPosition().longitude;
            }

            String lokacija = latituda + "," + longituda;
            int visina = (int) nVisina;
            String nadmorskaVisina = visina + "";//-1000

            Bitmap image = ((BitmapDrawable) pcelinjakSlika.getDrawable()).getBitmap();
            String slika = bitmapToString((image));

            //Log.d(TAG, slika);

            if (naziv.isEmpty()) {
                Toast.makeText(this, "Unesite naziv pcelinjaka", Toast.LENGTH_SHORT).show();
                return;
            }

            if (visina == -1000) {
                Toast.makeText(this, "Niste postavili lokaciju", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent podaci = new Intent();
            podaci.putExtra(EXTRA_RB, redniBroj);
            podaci.putExtra(EXTRA_NAZIV_PCELINJAKA, naziv);
            podaci.putExtra(EXTRA_LOKACIJA, lokacija);
            podaci.putExtra(EXTRA_NADMORSKA_VISINA, nadmorskaVisina);
            podaci.putExtra(EXTRA_SLIKA, slika);

            setResult(RESULT_OK, podaci);
            finish();
            return;
        }
        //novi
        if (zauzetiRBovi.contains(redniBroj)) {
            Toast.makeText(getApplicationContext(), "Pokusavate da unosite postojeci redni broj", Toast.LENGTH_LONG).show();
            return;
        }

        String naziv = txtNaziv.getText().toString().trim();

        Double latituda = null;
        Double longituda = null;

        if (mMarker != null) {
            latituda = mMarker.getPosition().latitude;
            longituda = mMarker.getPosition().longitude;
        }
        String lokacija = latituda + "," + longituda;
        int visina = (int) nVisina;
        String nadmorskaVisina = visina + "";//-1000


        if (naziv.isEmpty()) {
            Toast.makeText(this, "Unesite pcelinjak", Toast.LENGTH_SHORT).show();
            return;
        }
        if (visina == -1000) {
            Toast.makeText(this, "Niste postavili lokaciju", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap image = ((BitmapDrawable) pcelinjakSlika.getDrawable()).getBitmap();
        String slika = bitmapToString((image));

        Intent podaci = new Intent();
        podaci.putExtra(EXTRA_RB, redniBroj);
        podaci.putExtra(EXTRA_NAZIV_PCELINJAKA, naziv);
        podaci.putExtra(EXTRA_LOKACIJA, lokacija);
        podaci.putExtra(EXTRA_NADMORSKA_VISINA, nadmorskaVisina);
        podaci.putExtra(EXTRA_SLIKA, slika);
/*
        int id = getIntent().getIntExtra(EXTRA_RB, -1);
        if (id != -1) {
            podaci.putExtra(EXTRA_RB, id);
        }*/
        setResult(RESULT_OK, podaci);
        finish();
    }

    private void srediIntentBezMape() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_RB)) {
            setTitle("Izmeni pcelinjak");
            txtRedniBroj.setText(intent.getIntExtra(EXTRA_RB, -1) + "");
            txtNaziv.setText(intent.getStringExtra(EXTRA_NAZIV_PCELINJAKA));
            String slika = intent.getStringExtra(EXTRA_SLIKA);
            if (slika != null) {
                pcelinjakSlika.setImageBitmap(stringToBitmap(slika));
            }
        } else {
            setTitle("Dodaj pcelinjak");
        }
    }

    private void srediIntentMapa() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_RB)) {

            String lokacija = intent.getStringExtra(EXTRA_LOKACIJA);
            String[] lokacije = lokacija.split(",");
            String lok1 = lokacije[0] + "";
            String lok2 = lokacije[1] + "";

            if (lok1.equals("null") || lok2.equals("null")) {
                return;
            }
            double latitude = Double.parseDouble(lokacije[0]);
            double longitude = Double.parseDouble(lokacije[1]);
            LatLng latLng = new LatLng(latitude, longitude);
            pomeriKameru(latLng, DEFAULT_ZOOM, "Adresa");

            vratiNadmorskuVisinu(latitude, longitude);

            Geocoder geocoder = new Geocoder(Dodaj_IzmeniPcelinjakActivity.this);
            List<Address> lista = new ArrayList<>();

            try {
                lista = geocoder.getFromLocation(mMarker.getPosition().latitude, mMarker.getPosition().longitude, 1);
            } catch (IOException e) {
                Log.e(TAG, "IO " + e.getMessage());
            }
            if (lista.size() > 0) {
                Address adresa = lista.get(0);

                pomeriKameru(new LatLng(latitude, longitude),
                        DEFAULT_ZOOM, adresa.getAddressLine(0));
            }
        } else {
            setTitle("Dodaj pcelinjak");
        }
    }

    private void srediListenere() {
        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMarker != null) {
                    Toast.makeText(getApplicationContext(), nVisina + "", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSlikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                }
            }
        });

    }

    private void vratiNadmorskuVisinu(double latitude, double longitude) {

        String url = "https://maps.googleapis.com/maps/api/elevation/json?locations=" + latitude + "," +
                longitude + "&key=AIzaSyBETypVelODVcyN2Pp_aYjSGs65-mDXW9s";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject visina = jsonArray.getJSONObject(i);

                                double nadmorskaVisina = visina.getDouble("elevation");
                                nVisina = nadmorskaVisina;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    private void srediAtribute() {
        mestaAutoCompleteAdapter = null;
        mLokacijaDozvoljena = false;
        autoCompleteTV = (AutoCompleteTextView) findViewById(R.id.txtPretrazi);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        mInfo = (ImageView) findViewById(R.id.mesto_info);
        pcelinjakSlika = (ImageView) findViewById(R.id.pcelinjak_slika);


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frgMap);
        mMarker = null;
        txtRedniBroj = findViewById(R.id.txtRedniBroj);
        txtNaziv = findViewById(R.id.txtNaziv);
        btnSacuvaj = findViewById(R.id.btnSacuvaj);
        btnDodajIzGalerije = findViewById(R.id.btnDodajIzGalerije);
        btnSlikaj = findViewById(R.id.btnSlikaj);
        mSkini = findViewById(R.id.skini_pin);
        mQueue = Volley.newRequestQueue(this);
        nVisina = -1000;
        zauzetiRBovi = getIntent().getIntegerArrayListExtra(EXTRA_ZAUZETI_RB);
    }

    private LatLng getLatLngFromAddress(String address) {

        Geocoder geocoder = new Geocoder(Dodaj_IzmeniPcelinjakActivity.this);
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if (addressList != null) {
                Address singleaddress = addressList.get(0);
                LatLng latLng = new LatLng(singleaddress.getLatitude(), singleaddress.getLongitude());
                return latLng;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Address getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(Dodaj_IzmeniPcelinjakActivity.this);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
            if (addresses != null) {
                Address address = addresses.get(0);
                return address;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void pomeriKameru(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!title.equals("Moja lokacija")) {
            skiniMarkere();
            mMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(title));
            vratiNadmorskuVisinu(mMarker.getPosition().latitude, mMarker.getPosition().longitude);
        }
        iskljuciTastaturu();
    }

    private void inicijalizacijaZaMapu() {
        postaviAdapterZaAutoComplete();
        postaviListenereZaMapu();
    }

    private void postaviListenereZaMapu() {

        autoCompleteTV.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    //izvrsavaPretragu
                    geoLocate();

                }
                autoCompleteTV.dismissDropDown();
                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLokacijaUredjaja();
            }
        });

        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMarker != null) {
                    try {
                        if (mMarker.isInfoWindowShown()) {
                            mMarker.hideInfoWindow();
                            pomeriKameru(new LatLng(mMarker.getPosition().latitude, mMarker.getPosition().longitude), DEFAULT_ZOOM, mMarker.getTitle());
                        } else {
                            mMarker.showInfoWindow();
                        }
                    } catch (NullPointerException e) {
                        Log.e(TAG, "onClick: NullPointerException: " + e.getMessage());
                    }
                }
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                // na marker se postavlja najbliza adresa
                skiniMarkere();
                mMarker = mMap.addMarker(new MarkerOptions().position(latLng));
                double latituda = mMarker.getPosition().latitude;
                double longituda = mMarker.getPosition().longitude;

                vratiNadmorskuVisinu(latituda, longituda);

                Geocoder geocoder = new Geocoder(Dodaj_IzmeniPcelinjakActivity.this);
                List<Address> lista = new ArrayList<>();

                try {
                    lista = geocoder.getFromLocation(mMarker.getPosition().latitude, mMarker.getPosition().longitude, 1);
                } catch (IOException e) {
                    Log.e(TAG, "IO " + e.getMessage());
                }
                if (lista.size() > 0) {
                    Address adresa = lista.get(0);

                    pomeriKameru(new LatLng(latituda, longituda),
                            DEFAULT_ZOOM, adresa.getAddressLine(0));

                }
            }
        });

        autoCompleteTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMap.clear();
                geoLocate();
            }
        });
        mSkini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMarker != null)
                    skiniMarkere();
            }
        });
    }

    private void geoLocate() {
        String pretraziString = autoCompleteTV.getText().toString();
        Geocoder geocoder = new Geocoder(Dodaj_IzmeniPcelinjakActivity.this);
        List<Address> lista = new ArrayList<>();

        try {
            lista = geocoder.getFromLocationName(pretraziString, 1);
        } catch (IOException e) {
            Log.e(TAG, "IOEXCEPTION: " + e.getMessage());
        }
        if (lista.size() > 0) {
            Address adresa = lista.get(0);
            Log.d(TAG, "Pronadjena lokacija: " + adresa.toString());
            pomeriKameru(new LatLng(adresa.getLatitude(), adresa.getLongitude()),
                    DEFAULT_ZOOM, adresa.getAddressLine(0));
        }
        iskljuciTastaturu();


    }

    private void postaviAdapterZaAutoComplete() {
        mestaAutoCompleteAdapter = new MestaAutoCompleteAdapter(Dodaj_IzmeniPcelinjakActivity.this, android.R.layout.simple_list_item_1);
        autoCompleteTV.setAdapter(mestaAutoCompleteAdapter);
    }

    private void getLokacijaUredjaja() {
        mProvajderLokacije = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLokacijaDozvoljena) {
                final Task lokacija = mProvajderLokacije.getLastLocation();
                lokacija.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location trenutnaLokacija = (Location) task.getResult();
                            pomeriKameru(new LatLng(trenutnaLokacija.getLatitude(), trenutnaLokacija.getLongitude()),
                                    DEFAULT_ZOOM, "Moja lokacija");
                        } else {
                            Toast.makeText(Dodaj_IzmeniPcelinjakActivity.this,
                                    "Nemoguće dobiti trenutnu lokaciju", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "SecurityException " + e.getMessage());
        }
    }

    private void inicijalizujMapu() {
        assert mapFragment != null;
        mapFragment.getMapAsync(Dodaj_IzmeniPcelinjakActivity.this);
    }

    private void getDozvolaZaLokaciju() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLokacijaDozvoljena = true;
                inicijalizujMapu();
            } else {
                ActivityCompat.requestPermissions(this, permissions,
                        LOCATION_PERMISSION_ZAHTEV);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSION_ZAHTEV);
        }
    }

    private void skiniMarkere() {
        mMap.clear();
        mMarker = null;
        nVisina = -1000;
    }

    //kad prvi put ulazi
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLokacijaDozvoljena = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_ZAHTEV: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLokacijaDozvoljena = false;
                            return;
                        }
                    }
                    mLokacijaDozvoljena = true;

                    inicijalizujMapu();
                }
            }
        }
    }

    private void iskljuciTastaturu() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dodaj_novu_napomenu_meni, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sacuvaj_napomenu:
                sacuvajPcelinjak();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isOnline() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connManager != null;
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            pcelinjakSlika.setImageBitmap(imageBitmap);
        }
    }

    private static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /*
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
        }*/
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
