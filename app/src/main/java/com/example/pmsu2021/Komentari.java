package com.example.pmsu2021;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.ETC1;
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
import com.example.pmsu2021.Model.PorudzbinaFinal;
import com.example.pmsu2021.adapter.KomentariAdapter;

import java.util.List;

public class Komentari extends AppCompatActivity {

    ListView lvKomentari;
    TextView etKomentariInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentari);

        lvKomentari = findViewById(R.id.lv_komentari);
        etKomentariInfo = findViewById(R.id.et_komentari_info);

        String prodavac = getIntent().getStringExtra("prodavac");

        String uslov = "prodavac = '" + prodavac + "' AND komentar is not null AND arhiviranKomentar is null";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create(); // kreira novi query builder
        queryBuilder.setPageSize(100).setOffset(0);
        queryBuilder.setWhereClause(uslov);

        Backendless.Persistence.of(PorudzbinaFinal.class).find(queryBuilder, new AsyncCallback<List<PorudzbinaFinal>>() {
            @Override
            public void handleResponse(List<PorudzbinaFinal> response) {

                if (response.isEmpty()) {
                    etKomentariInfo.setText("Ne postoje komentari za izabranog prodavca!");
                } else {
                    KomentariAdapter komentariAdapter = new KomentariAdapter(Komentari.this, response);
                    lvKomentari.setAdapter(komentariAdapter);
                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(Komentari.this, "Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}