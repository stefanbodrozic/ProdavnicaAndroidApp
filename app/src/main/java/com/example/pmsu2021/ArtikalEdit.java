package com.example.pmsu2021;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.Result;
import com.backendless.exceptions.BackendlessFault;
import com.example.pmsu2021.Model.Artikal;
import com.squareup.picasso.Picasso;

public class ArtikalEdit extends AppCompatActivity {

    ImageView ivSlikaArtiklaEdit;
    EditText artikalNazivEdit, artikalOpisEdit, cenaArtiklaEdit;
    Button btnSacuvajIzmene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikal_edit);

        ivSlikaArtiklaEdit = findViewById(R.id.iv_edit_artikla);
        artikalNazivEdit = findViewById(R.id.et_artikal_naziv_edit);
        artikalOpisEdit = findViewById(R.id.et_artikal_opis_edit);
        cenaArtiklaEdit = findViewById(R.id.et_cena_artikla_edit);
        btnSacuvajIzmene = findViewById(R.id.btn_sacuvaj_izmene);

        final int index = getIntent().getIntExtra("index", 0);

        artikalNazivEdit.setText(PMSUApplication.artikli.get(index).getNazivArtikla());
        artikalOpisEdit.setText(PMSUApplication.artikli.get(index).getOpis());
        cenaArtiklaEdit.setText(Double.toString(PMSUApplication.artikli.get(index).getCena()));

        String url = "https://backendlessappcontent.com/" + PMSUApplication.APPLICATION_ID + "/" + PMSUApplication.API_KEY + "/files/images/" + PMSUApplication.artikli.get(index).getFileColumn();

        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_cancel)
                .into(ivSlikaArtiklaEdit);

        btnSacuvajIzmene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(artikalNazivEdit.getText().toString().isEmpty() || artikalOpisEdit.getText().toString().isEmpty() || cenaArtiklaEdit.getText().toString().isEmpty()) {

                    Toast.makeText(ArtikalEdit.this, "Polja za unos podataka moraju biti popunjena", Toast.LENGTH_SHORT).show();

                } else {

                    PMSUApplication.artikli.get(index).setNazivArtikla(artikalNazivEdit.getText().toString());
                    PMSUApplication.artikli.get(index).setOpis(artikalOpisEdit.getText().toString());
                    PMSUApplication.artikli.get(index).setCena(Double.parseDouble(cenaArtiklaEdit.getText().toString()));

                    Backendless.Persistence.save(PMSUApplication.artikli.get(index), new AsyncCallback<Artikal>() {
                        @Override
                        public void handleResponse(Artikal response) {

                            Toast.makeText(ArtikalEdit.this, "Uspesno sacuvana izmena", Toast.LENGTH_SHORT).show();

                            setResult(RESULT_OK); //zavrsava ovu aktivnost i vraca na prethodnu
                            ArtikalEdit.this.finish();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(ArtikalEdit.this, "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

    }

}