package com.example.pmsu2021;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.adapter.ArtikliAdapter;
import com.example.pmsu2021.adapter.ArtikliAdapterZaProdavca;

import java.util.List;

public class ListaArtikalaZaProdavca extends AppCompatActivity {

    ListView lvArtikliZaProdavca;
    TextView nazivProdavniceToolbar;
    ImageView dodajNoviArtikal;

    ArtikliAdapterZaProdavca artikliAdapterZaProdavca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_artikala_za_prodavca);

        lvArtikliZaProdavca = findViewById(R.id.lv_artikli_za_prodavca);

        String imeProdavca = PMSUApplication.prijavljeniKorisnik.getProperty("ime").toString();

        nazivProdavniceToolbar = findViewById(R.id.naziv_prodavnice_toolbar);
        nazivProdavniceToolbar.setText(PMSUApplication.prijavljeniKorisnik.getProperty("naziv").toString());

        String uslov = "imeProdavca = '" + imeProdavca + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create(); // kreira novi query builder
        queryBuilder.setPageSize(100).setOffset(0);
        queryBuilder.setWhereClause(uslov);

        Backendless.Persistence.of(Artikal.class).find(queryBuilder, new AsyncCallback<List<Artikal>>() {
            @Override
            public void handleResponse(List<Artikal> response) {

                PMSUApplication.artikli = response;
                artikliAdapterZaProdavca = new ArtikliAdapterZaProdavca(ListaArtikalaZaProdavca.this, PMSUApplication.artikli);
                lvArtikliZaProdavca.setAdapter(artikliAdapterZaProdavca);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ListaArtikalaZaProdavca.this, "Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        dodajNoviArtikal = findViewById(R.id.dodaj_novi_artikal);
        dodajNoviArtikal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListaArtikalaZaProdavca.this, NoviArtikal.class);

               // startActivity(intent);

               startActivityForResult(intent, 1); //odlazimo u novu aktivnost (artikaledit) i vracamo se u ovu (lista..)

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {
            //artikliAdapterZaProdavca.notifyDataSetChanged();


            if (resultCode == RESULT_OK) {
                recreate();
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }

    }

}