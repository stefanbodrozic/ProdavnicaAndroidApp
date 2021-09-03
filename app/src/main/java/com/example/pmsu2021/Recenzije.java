package com.example.pmsu2021;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.pmsu2021.adapter.RecenzijeAdapter;

import java.util.List;

public class Recenzije extends AppCompatActivity {

    ListView lvArtikliZaRecenziju;
    TextView tvRecenzijeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recenzije);

        lvArtikliZaRecenziju = findViewById(R.id.lv_artikli_za_recenziju);
        tvRecenzijeInfo = findViewById(R.id.tv_recenzije_info);

        String uslov = "kupac = '" + PMSUApplication.prijavljeniKorisnik.getProperty("username") + "' AND dostavljeno is null";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create(); // kreira novi query builder
        queryBuilder.setPageSize(100).setOffset(0);
        queryBuilder.setWhereClause(uslov);


        Backendless.Persistence.of(PorudzbinaFinal.class).find(queryBuilder, new AsyncCallback<List<PorudzbinaFinal>>() {
            @Override
            public void handleResponse(List<PorudzbinaFinal> response) {

                if (response.isEmpty()) {
                    tvRecenzijeInfo.setText("Ne postoje porudzbine za koje mozete ostaviti recenziju!");
                } else {

                    RecenzijeAdapter recenzijeAdapter = new RecenzijeAdapter(Recenzije.this, response);
                    lvArtikliZaRecenziju.setAdapter(recenzijeAdapter);

                }


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(Recenzije.this, "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        lvArtikliZaRecenziju.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PorudzbinaFinal izabranaPorudzbina = (PorudzbinaFinal) parent.getItemAtPosition(position);

                Intent intent = new Intent(Recenzije.this, RecenzijaDetaljnoFinal.class);
                intent.putExtra("sifra", izabranaPorudzbina.getSifraPorudzbine());
                startActivityForResult(intent, 1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                recreate();
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}