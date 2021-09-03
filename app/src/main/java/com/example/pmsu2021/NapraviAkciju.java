package com.example.pmsu2021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.pmsu2021.Model.Akcija;
import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.adapter.ArtikliAdapterZaPravljenjeAkcije;
import com.example.pmsu2021.adapter.ArtikliAdapterZaProdavca;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NapraviAkciju extends AppCompatActivity {

    ListView lvArtikliKodPravljenjaAkcije;
    EditText etNazivAkcije, etDatumOd, etDatumDo, etOpisAkcije, etPopust;
    ImageView ivSacuvajAKciju;

    ArtikliAdapterZaPravljenjeAkcije adapterZaPravljenjeAkcije;

    public static List<Artikal> korisnikIzabrao = new ArrayList<Artikal>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napravi_akciju);

        lvArtikliKodPravljenjaAkcije = findViewById(R.id.lv_artikli_kod_pravljenja_akcije);

        lvArtikliKodPravljenjaAkcije.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        etNazivAkcije = findViewById(R.id.et_naziv_akcije);
        etDatumOd = findViewById(R.id.et_datum_od);
        etDatumDo = findViewById(R.id.et_datum_do);
        etOpisAkcije = findViewById(R.id.et_opis_akcije);
        etPopust = findViewById(R.id.et_popust);
        ivSacuvajAKciju = findViewById(R.id.sacuvaj_akciju);

        etPopust.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});

        String imeProdavca = PMSUApplication.prijavljeniKorisnik.getProperty("username").toString();

        // za prikaz svih artikala koji trenutno nisu na akciji
        String uslov = "imeProdavca = '" + imeProdavca + "' AND nazivAkcije = '" + "null" + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create(); // kreira novi query builder
        queryBuilder.setPageSize(100).setOffset(0);
        queryBuilder.setWhereClause(uslov);

        Backendless.Persistence.of(Artikal.class).find(queryBuilder, new AsyncCallback<List<Artikal>>() {
            @Override
            public void handleResponse(List<Artikal> response) {

                PMSUApplication.artikliBezAkcije = response;
                adapterZaPravljenjeAkcije = new ArtikliAdapterZaPravljenjeAkcije(NapraviAkciju.this, PMSUApplication.artikliBezAkcije);
                lvArtikliKodPravljenjaAkcije.setAdapter(adapterZaPravljenjeAkcije);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(NapraviAkciju.this, "Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etDatumOd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(NapraviAkciju.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "-" + month + "-" + year;
                        etDatumOd.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        etDatumDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(NapraviAkciju.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "-" + month + "-" + year;
                        etDatumDo.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });


        // cuvanje akcije
        ivSacuvajAKciju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etNazivAkcije.getText().toString().isEmpty() || etDatumOd.getText().toString().isEmpty() || etDatumDo.getText().toString().isEmpty() || etOpisAkcije.getText().toString().isEmpty() || etPopust.getText().toString().isEmpty()) {
                    Toast.makeText(NapraviAkciju.this, "Polja za unos podataka moraju biti popunjena", Toast.LENGTH_SHORT).show();
                } else {

                    //uraditi proveru za datume
                    String nazivAkcije = etNazivAkcije.getText().toString();
                    String datumOd = etDatumOd.getText().toString();
                    String datumDo = etDatumDo.getText().toString();
                    String opis = etOpisAkcije.getText().toString();
                    Integer popust = Integer.parseInt(etPopust.getText().toString());

                    String imeProdavca = PMSUApplication.prijavljeniKorisnik.getProperty("username").toString();

                    //provera za datume
                    Date datumPocetka;
                    Date datumZavrsetka;
                    try {

                        datumPocetka = new SimpleDateFormat("dd-MM-yyyy").parse(datumOd);
                        datumZavrsetka = new SimpleDateFormat("dd-MM-yyyy").parse(datumDo);

                        if (datumPocetka.after(datumZavrsetka)) {
                            // datum pocetka je posle datuma zavrsetka i to ne moze
                            Toast.makeText(NapraviAkciju.this, "Greska: Datum pocetka je posle datuma zavrsetka!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Date danasnjiDatum = new Date();
                        SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
                        String samoDatumDanas = dateFormat.format(danasnjiDatum);

                        Date danas = new SimpleDateFormat("dd-MM-yyyy").parse(samoDatumDanas);

                        if (datumPocetka.before(danas)) {
                            Toast.makeText(NapraviAkciju.this, "Greska: Datum pocetka akcije ne moze biti pre danasnjeg dana!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (datumPocetka.equals(datumZavrsetka)) {
                            Toast.makeText(NapraviAkciju.this, "Greska: Nije dozvoljeno praviti akciju danas za danas!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } catch (Exception exception) {
                        Toast.makeText(NapraviAkciju.this, "Neispravan format datuma! Greska: " + exception, Toast.LENGTH_LONG).show();
                        return;
                    }

                    Akcija akcija = new Akcija();
                    akcija.setNazivAkcije(nazivAkcije);
                    akcija.setDatumOd(datumPocetka);
                    akcija.setDatumDo(datumZavrsetka);
                    akcija.setOpis(opis);
                    akcija.setPopust(popust);
                    akcija.setImeProdavca(imeProdavca);

                    if (!korisnikIzabrao.isEmpty()) { //izabrani su proizvodi koji mogu biti stavljeni na akciju

                        Backendless.Persistence.save(akcija, new AsyncCallback<Akcija>() {
                            @Override
                            public void handleResponse(Akcija response) {

                                Toast.makeText(NapraviAkciju.this, "Uspesno kreirana nova akcija!", Toast.LENGTH_SHORT).show();

                                korisnikIzabrao.clear();

                                setResult(RESULT_OK);
                                NapraviAkciju.this.finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(NapraviAkciju.this, "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(NapraviAkciju.this, "Nisu izabrani proizvodi koji treba da budu na akciji!", Toast.LENGTH_SHORT).show();
                    }


                    // update za cenu i naziv akcije u artiklima
                    for (Artikal artikal: korisnikIzabrao) {

                        artikal.setNazivAkcije(akcija.getNazivAkcije());
                        double cena = artikal.getCena();
                        double popustAkcija = akcija.getPopust();
                        double cenaNaPopustu = cena - (popustAkcija/100 * cena);
                        artikal.setCenaNaPopustu(cenaNaPopustu);

                        Backendless.Data.of(Artikal.class).save(artikal, new AsyncCallback<Artikal>() {
                            @Override
                            public void handleResponse(Artikal response) {
//                                Toast.makeText(NapraviAkciju.this, "Sacuvana izmena artikla na serveru", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(NapraviAkciju.this, "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                  }

                }
            }

        });
    }
}