package com.example.pmsu2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.pmsu2021.Model.PorudzbinaFinal;
import com.example.pmsu2021.Model.PorudzbinaStavka;
import com.example.pmsu2021.adapter.PotvrdiPorudzbinuAdapter;

import java.util.List;

public class RecenzijaDetaljnoFinal extends AppCompatActivity {

    ListView lvArtikliZaRecenzijuDetaljno;
    TextView etAdresa, etCena;
    EditText etKomentar, etOcena;
    CheckBox cbOstaniAnoniman;
    ImageView ivSacuvaj, ivOtkazi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recenzija_detaljno_final);

        lvArtikliZaRecenzijuDetaljno = findViewById(R.id.lv_artikli_za_recenziju_detaljno);

        etAdresa = findViewById(R.id.et_adresa_kupca_recenzija);
        etCena = findViewById(R.id.et_ukupna_cena_recenzija);

        etKomentar = findViewById(R.id.et_komentar_recenzija);
        etOcena = findViewById(R.id.et_ocena_recenzija);

        etOcena.setFilters(new InputFilter[]{new InputFilterMinMax("1", "5")});

        cbOstaniAnoniman = findViewById(R.id.cb_ostani_anoniman);

        ivSacuvaj = findViewById(R.id.iv_zavrsi_recenziju);
        ivOtkazi = findViewById(R.id.iv_otkazi_recenziju);

        final String sifra = getIntent().getStringExtra("sifra"); // sifra recenzije

        PorudzbinaFinal porudzbinaFinal = new PorudzbinaFinal();
        porudzbinaFinal.setSifraPorudzbine(sifra);

        // pronadji porudzbinu preko sifre
        String uslov = "sifraPorudzbine = '" + sifra  + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create(); // kreira novi query builder
        queryBuilder.setPageSize(100).setOffset(0);
        queryBuilder.setWhereClause(uslov);

        Backendless.Persistence.of(PorudzbinaFinal.class).find(queryBuilder, new AsyncCallback<List<PorudzbinaFinal>>() {
            @Override
            public void handleResponse(List<PorudzbinaFinal> response) {

                for (PorudzbinaFinal porudzbina: response) {
                    etAdresa.setText(porudzbina.getAdresaKupca());
                    etCena.setText(String.valueOf(porudzbina.getUkupnaCena()));

                    porudzbinaFinal.setAdresaKupca(porudzbina.getAdresaKupca());
                    porudzbinaFinal.setUkupnaCena(porudzbina.getUkupnaCena());
                    porudzbinaFinal.setObjectId(porudzbina.getObjectId());

                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(RecenzijaDetaljnoFinal.this, "Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // pronadji stavke porudzbine
        String uslov2 = "kupac = '" + PMSUApplication.prijavljeniKorisnik.getProperty("username") + "' AND sifra = '" + sifra  + "'";
        DataQueryBuilder queryBuilder2 = DataQueryBuilder.create(); // kreira novi query builder
        queryBuilder2.setPageSize(100).setOffset(0);
        queryBuilder2.setWhereClause(uslov2);

        Backendless.Persistence.of(PorudzbinaStavka.class).find(queryBuilder2, new AsyncCallback<List<PorudzbinaStavka>>() {
            @Override
            public void handleResponse(List<PorudzbinaStavka> response) {

                PotvrdiPorudzbinuAdapter potvrdiPorudzbinuAdapter = new PotvrdiPorudzbinuAdapter(RecenzijaDetaljnoFinal.this, response);
                lvArtikliZaRecenzijuDetaljno.setAdapter(potvrdiPorudzbinuAdapter);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(RecenzijaDetaljnoFinal.this, "Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        ivSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etKomentar.getText().toString().isEmpty() || etOcena.getText().toString().isEmpty()) {
                    Toast.makeText(RecenzijaDetaljnoFinal.this, "Polja za unos podataka moraju biti popunjena", Toast.LENGTH_SHORT).show();
                } else {

                    porudzbinaFinal.setOcena(Integer.parseInt(etOcena.getText().toString()));
                    porudzbinaFinal.setKomentar(etKomentar.getText().toString());

                    if(cbOstaniAnoniman.isChecked()) {
                        porudzbinaFinal.setAnonimanKomentar(true);
                    } else {
                        porudzbinaFinal.setAnonimanKomentar(false);
                    }

                    porudzbinaFinal.setDostavljeno(true);

                    //sacuvaj porudzbinu
                    Backendless.Persistence.save(porudzbinaFinal, new AsyncCallback<PorudzbinaFinal>() {
                        @Override
                        public void handleResponse(PorudzbinaFinal response) {
                            Toast.makeText(RecenzijaDetaljnoFinal.this, "Uspesno ostavljena recenzija!", Toast.LENGTH_SHORT).show();

                            setResult(RESULT_OK); //zavrsava ovu aktivnost i vraca na prethodnu
                            RecenzijaDetaljnoFinal.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(RecenzijaDetaljnoFinal.this, "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

        ivOtkazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK); //zavrsava ovu aktivnost i vraca na prethodnu
                RecenzijaDetaljnoFinal.this.finish();
            }
        });



    }
}