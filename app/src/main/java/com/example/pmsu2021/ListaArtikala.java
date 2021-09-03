package com.example.pmsu2021;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.Model.PorudzbinaStavka;
import com.example.pmsu2021.adapter.ArtikliAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListaArtikala extends AppCompatActivity {

    ListView lvArtikli;
    TextView artikliNaslov;
    ImageView ivPotvrdiKupovinu, ivPrikaziKomentare, ivPrikaziAkcije;

    public static List<PorudzbinaStavka> porudzbineStavka = new ArrayList<>();

    public static String prodavac = "";

    Random random = new Random();
    int gornjaGranica = Integer.MAX_VALUE;
    int randomInt = random.nextInt(gornjaGranica);

    String sifra = "S" + randomInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_artikala);

        lvArtikli = findViewById(R.id.lv_artikli);

        ivPotvrdiKupovinu = findViewById(R.id.iv_potvrdi_kupovinu);
        ivPrikaziKomentare = findViewById(R.id.iv_prikazi_komentare);
        ivPrikaziAkcije = findViewById(R.id.iv_prikazi_akcije);

        final int index = getIntent().getIntExtra("index", 0); // index prodavca u listi prodavaca

        String nazivProdavnice = PMSUApplication.prodavci.get(index).getProperty("naziv").toString();

        artikliNaslov = findViewById(R.id.naziv_prodavnice_toolbar);
        artikliNaslov.setText(nazivProdavnice + " - Artikli");

        String imeProdavca = PMSUApplication.prodavci.get(index).getProperty("ime").toString();
        prodavac = imeProdavca;

        if (!PMSUApplication.prijavljeniKorisnik.getProperty("username").equals(prodavac)) {
            ivPrikaziAkcije.setVisibility(View.GONE);
        }

        String uslov = "imeProdavca = '" + imeProdavca + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create(); // kreira novi query builder
        queryBuilder.setPageSize(100).setOffset(0);
        queryBuilder.setWhereClause(uslov);

        Backendless.Persistence.of(Artikal.class).find(queryBuilder, new AsyncCallback<List<Artikal>>() {
            @Override
            public void handleResponse(List<Artikal> response) {

                ArtikliAdapter artikliAdapter = new ArtikliAdapter(ListaArtikala.this, response);
                lvArtikli.setAdapter(artikliAdapter);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ListaArtikala.this, "Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        ivPotvrdiKupovinu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!porudzbineStavka.isEmpty()) {
                    //otvori novu aktivnost sa prikazom svih narucenih artikala, kolicinom i cenom

                    Backendless.Data.of(PorudzbinaStavka.class).create(porudzbineStavka, new AsyncCallback<List<String>>() {
                        @Override
                        public void handleResponse(List<String> response) {
                            Toast.makeText(ListaArtikala.this, "Uspesno napravljena nova porudzbina!", Toast.LENGTH_SHORT).show();

                            // otvoriti novu aktivnost za potvrdu narudzbine!

                            Intent intent = new Intent(ListaArtikala.this, PotvrdiPorudzbinu.class);

                            intent.putExtra("sifra", sifra);
                            intent.putExtra("prodavac", prodavac);

                            startActivityForResult(intent, 1); //odlazi u novu aktivnost i posle save/cancel vraca se u ovu aktivnost

                            porudzbineStavka.clear();

                            LocalBroadcastManager.getInstance(ListaArtikala.this).unregisterReceiver(mMessageReceiver); // kao .clear() metoda da ne bi doslo do ponavljanja artikala u novoj narudzbini

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(ListaArtikala.this, "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(ListaArtikala.this, "Korpa je prazna!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ivPrikaziKomentare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListaArtikala.this, Komentari.class);

                intent.putExtra("prodavac", prodavac);

                startActivity(intent);

            }
        });

        ivPrikaziAkcije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListaArtikala.this, ListaAkcija.class);

                intent.putExtra("prodavac", prodavac);
                intent.putExtra("nazivProdavnice", nazivProdavnice);

                startActivity(intent);

            }
        });


    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String artikal = intent.getStringExtra("artikal");
            String kolicina = intent.getStringExtra("kolicina");
            String cena = intent.getStringExtra("cena");
            Double c = Double.parseDouble(cena);
            Toast.makeText(ListaArtikala.this, "Artikal dodat u korpu!",Toast.LENGTH_SHORT).show();

            PorudzbinaStavka porudzbina = new PorudzbinaStavka();
            porudzbina.setArtikalNaziv(artikal);
            if (kolicina.equals("")) {
                porudzbina.setKolicina(0);
            } else
            {
                porudzbina.setKolicina(Integer.parseInt(kolicina));
            }

            porudzbina.setKupac(PMSUApplication.prijavljeniKorisnik.getProperty("username").toString());
            porudzbina.setSifra(sifra);
            porudzbina.setProdavac(prodavac);
            porudzbina.setCenaArtikla(c);



            ListaArtikala.porudzbineStavka.add(porudzbina);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {

            if (resultCode == RESULT_OK) {
//                recreate();
                this.finish();
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }

    }
}