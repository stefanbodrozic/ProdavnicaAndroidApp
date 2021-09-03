package com.example.pmsu2021;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.pmsu2021.Model.Akcija;
import com.example.pmsu2021.adapter.AkcijeAdapter;
import com.example.pmsu2021.adapter.ProdavciAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListaAkcija extends AppCompatActivity {

    ListView lvAkcijeZaProdavca;
    TextView nazivProdavniceToolbar;
    ImageView napraviNovuAkciju;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_akcija);

        lvAkcijeZaProdavca = findViewById(R.id.lv_akcije_za_prodavca);

        final int index = getIntent().getIntExtra("index", 0); // index prodavca u listi prodavaca
        //String imeProdavca = PMSUApplication.prodavci.get(index).getProperty("ime").toString();

        String prodavac = getIntent().getStringExtra("prodavac");

        nazivProdavniceToolbar = findViewById(R.id.naziv_prodavnice_toolbar);
        napraviNovuAkciju = findViewById(R.id.napravi_novu_akciju);

        String nazivProdavnice = getIntent().getStringExtra("nazivProdavnice");
        nazivProdavniceToolbar.setText(nazivProdavnice + " - Akcije");

        Date danasnjiDatum = new Date();

        String uslov = "imeProdavca = '" + prodavac + "' AND datumOd at or before '" + danasnjiDatum + "' AND datumDo at or after '" + danasnjiDatum + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create(); // kreira novi query builder
        queryBuilder.setPageSize(100).setOffset(0);
        queryBuilder.setWhereClause(uslov);

        Backendless.Persistence.of(Akcija.class).find(queryBuilder, new AsyncCallback<List<Akcija>>() {
            @Override
            public void handleResponse(List<Akcija> response) {

                AkcijeAdapter akcijeAdapter = new AkcijeAdapter(ListaAkcija.this, response);
                lvAkcijeZaProdavca.setAdapter(akcijeAdapter);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ListaAkcija.this, "Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



        napraviNovuAkciju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListaAkcija.this, NapraviAkciju.class);

                startActivityForResult(intent, 1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {

            if (resultCode == RESULT_OK) {

                recreate();

            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}