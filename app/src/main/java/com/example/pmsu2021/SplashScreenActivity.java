package com.example.pmsu2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.example.pmsu2021.Model.Akcija;
import com.example.pmsu2021.Model.Artikal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    Boolean korisnikPrijavljen;


    List<Akcija> istekleAkcije = new ArrayList<>();
    List<Artikal> artikliBezAkcijeTEMP = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        korisnikPrijavljen = false;

        // provera da li je korisnik prijavljen
        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {

                if(response) {

                    String userObjectId = UserIdStorageFactory.instance().getStorage().get();

                    Backendless.Data.of(BackendlessUser.class).findById(userObjectId, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            korisnikPrijavljen = true;
                            PMSUApplication.prijavljeniKorisnik = response;
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(SplashScreenActivity.this,"Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                } else {

                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(SplashScreenActivity.this,"Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (korisnikPrijavljen) {
                    Intent intent = new Intent(SplashScreenActivity.this, Prodavci.class);
                    startActivity(intent);

                    finish();
                    Toast.makeText(SplashScreenActivity.this,"Prijavljen", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(SplashScreenActivity.this, Login.class);
                    startActivity(intent);

                    finish();
                    Toast.makeText(SplashScreenActivity.this,"Nije prijavljen", Toast.LENGTH_SHORT).show();
                }
            }
        }, 3000);


        // provera da li su istekle akcije i ako jesu update za artikle

        Date danasnjiDatum = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
        String samoDatumDanas = dateFormat.format(danasnjiDatum);

        Date danas = new Date();
        try {
            danas = new SimpleDateFormat("dd-MM-yyyy").parse(samoDatumDanas);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String uslov = "datumDo before '" + danas + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create(); // kreira novi query builder
        queryBuilder.setPageSize(100).setOffset(0);
        queryBuilder.setWhereClause(uslov);

        Backendless.Persistence.of(Akcija.class).find(queryBuilder, new AsyncCallback<List<Akcija>>() {
            @Override
            public void handleResponse(List<Akcija> response) {
                istekleAkcije = response;
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        String uslov2 = "nazivAkcije != '" + null + "'";
        DataQueryBuilder queryBuilder2 = DataQueryBuilder.create(); // kreira novi query builder
        queryBuilder2.setPageSize(100).setOffset(0);
        queryBuilder2.setWhereClause(uslov2);

        Backendless.Persistence.of(Artikal.class).find(queryBuilder2, new AsyncCallback<List<Artikal>>() {
            @Override
            public void handleResponse(List<Artikal> response) {

                artikliBezAkcijeTEMP = response;

                for (Akcija akcija: istekleAkcije) {

                    for (Artikal artikal: artikliBezAkcijeTEMP) {

                        if (akcija.getNazivAkcije().equals(artikal.getNazivAkcije())) {
                            artikal.setCenaNaPopustu(0);
                            artikal.setNazivAkcije("null");

                            Backendless.Data.of(Artikal.class).save(artikal, new AsyncCallback<Artikal>() {
                                @Override
                                public void handleResponse(Artikal response) {
//                                    Toast.makeText(SplashScreenActivity.this,"Artikal izmenjen ", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                }
                            });

                        }
                    }
                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });




    }
}