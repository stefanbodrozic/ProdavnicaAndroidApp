package com.example.pmsu2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.Model.PorudzbinaFinal;
import com.example.pmsu2021.Model.PorudzbinaStavka;
import com.example.pmsu2021.adapter.ArtikliAdapter;
import com.example.pmsu2021.adapter.PotvrdiPorudzbinuAdapter;

import java.util.ArrayList;
import java.util.List;

public class PotvrdiPorudzbinu extends AppCompatActivity {

    ListView listaArtikalaZaPotvrduKupovine;
    EditText etAdresaKupca;
    ImageView ivZavrsiKupovinu, ivOtkaziKupovinu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potvrdi_porudzbinu);

        listaArtikalaZaPotvrduKupovine = findViewById(R.id.lv_artikli_za_potvrdu_kupovine);

        etAdresaKupca = findViewById(R.id.et_adresa_kupca);
        ivZavrsiKupovinu = findViewById(R.id.iv_zavrsi_kupovinu);
        ivOtkaziKupovinu = findViewById(R.id.iv_otkazi_kupovinu);

        String sifra = getIntent().getStringExtra("sifra");
        String prodavac = getIntent().getStringExtra("prodavac");

        PorudzbinaFinal porudzbinaFinal = new PorudzbinaFinal();

        porudzbinaFinal.setSifraPorudzbine(sifra);
        porudzbinaFinal.setProdavac(prodavac);

        TextView cenaFinal = findViewById(R.id.et_ukupna_cena);
        cenaFinal.setText(String.valueOf(porudzbinaFinal.getUkupnaCena()));

        Backendless.Data.of(PorudzbinaFinal.class).save(porudzbinaFinal, new AsyncCallback<PorudzbinaFinal>() {
            @Override
            public void handleResponse(PorudzbinaFinal response) {
//                Toast.makeText(PotvrdiPorudzbinu.this, "Porudzbina final upisan", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(PotvrdiPorudzbinu.this, "Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        String uslov = "kupac = '" + PMSUApplication.prijavljeniKorisnik.getProperty("username") + "' AND sifra = '" + sifra  + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create(); // kreira novi query builder
        queryBuilder.setPageSize(100).setOffset(0);
        queryBuilder.setWhereClause(uslov);

        Backendless.Persistence.of(PorudzbinaStavka.class).find(queryBuilder, new AsyncCallback<List<PorudzbinaStavka>>() {
            @Override
            public void handleResponse(List<PorudzbinaStavka> response) {

                PotvrdiPorudzbinuAdapter potvrdiPorudzbinuAdapter = new PotvrdiPorudzbinuAdapter(PotvrdiPorudzbinu.this, response);
                listaArtikalaZaPotvrduKupovine.setAdapter(potvrdiPorudzbinuAdapter);

                double cena = 0;

                for (PorudzbinaStavka stavka: response) {
                    cena += stavka.getCenaArtikla() * stavka.getKolicina();
                }

                porudzbinaFinal.setUkupnaCena(cena);

                cenaFinal.setText(String.valueOf(porudzbinaFinal.getUkupnaCena()));

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(PotvrdiPorudzbinu.this, "Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        ivZavrsiKupovinu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etAdresaKupca.getText().toString().isEmpty()) {
                    Toast.makeText(PotvrdiPorudzbinu.this, "Adresa nije uneta!", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    porudzbinaFinal.setAdresaKupca(etAdresaKupca.getText().toString());
                    porudzbinaFinal.setKupac(String.valueOf(PMSUApplication.prijavljeniKorisnik.getProperty("username")));

                    Backendless.Persistence.save(porudzbinaFinal, new AsyncCallback<PorudzbinaFinal>() {
                        @Override
                        public void handleResponse(PorudzbinaFinal response) {
                            Toast.makeText(PotvrdiPorudzbinu.this, "Porudzbina zavrsena!", Toast.LENGTH_SHORT).show();

                            setResult(RESULT_OK);
                            PotvrdiPorudzbinu.this.finish();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                }

            }
        });

        ivOtkaziKupovinu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                PotvrdiPorudzbinu.this.finish();
            }
        });

    }
}