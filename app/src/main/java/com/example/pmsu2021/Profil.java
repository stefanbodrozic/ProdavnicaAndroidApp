package com.example.pmsu2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profil extends AppCompatActivity {

    TextView tvKorisnikIme, tvKorisnikPrezime, tvKorisnikAdresa, tvKorisnikTip;
    DrawerLayout drawerLayout;
    Button btnPrikaziArtikleProdavcu, btnPrikaziPorudzbineKupcu, btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        drawerLayout = findViewById(R.id.drawer_layout);

        tvKorisnikIme = findViewById(R.id.tv_korisnik_ime);
        tvKorisnikPrezime = findViewById(R.id.tv_korisnik_prezime);
        tvKorisnikAdresa = findViewById(R.id.tv_korisnik_adresa);
        tvKorisnikTip = findViewById(R.id.tv_korisnik_tip_korisnika);

        btnPrikaziArtikleProdavcu = findViewById(R.id.btn_prikazi_artikle_prodavcu);
        btnPrikaziPorudzbineKupcu = findViewById(R.id.btn_prikazi_porudzbine_kupcu);
        btnAdmin = findViewById(R.id.btn_admin);

        tvKorisnikIme.setText("Ime: " + PMSUApplication.prijavljeniKorisnik.getProperty("ime").toString());
        tvKorisnikPrezime.setText("Prezime: " + PMSUApplication.prijavljeniKorisnik.getProperty("prezime").toString());
        tvKorisnikAdresa.setText("Adresa: " + PMSUApplication.prijavljeniKorisnik.getProperty("adresa").toString());
        tvKorisnikTip.setText("Tip korisnika: " + PMSUApplication.prijavljeniKorisnik.getProperty("tipKorisnika").toString());

        if (PMSUApplication.prijavljeniKorisnik.getProperty("tipKorisnika").equals("Kupac")) {

            btnPrikaziArtikleProdavcu.setVisibility(View.GONE);
            btnAdmin.setVisibility(View.GONE);

        } else if (PMSUApplication.prijavljeniKorisnik.getProperty("tipKorisnika").equals("Prodavac")) {

            btnPrikaziPorudzbineKupcu.setVisibility(View.GONE);
            btnAdmin.setVisibility(View.GONE);

        } else if (PMSUApplication.prijavljeniKorisnik.getProperty("tipKorisnika").equals("Administrator")) {

            btnPrikaziArtikleProdavcu.setVisibility(View.GONE);
            btnPrikaziPorudzbineKupcu.setVisibility(View.GONE);

        }

        btnPrikaziArtikleProdavcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profil.this,ListaArtikalaZaProdavca.class));
            }
        });

        btnPrikaziPorudzbineKupcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profil.this, Recenzije.class));
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profil.this, AdminPregledKorisnika.class));
            }
        });

    }

    public void ClickMenu(View view) {
        Prodavci.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        Prodavci.closeDrawer(drawerLayout);
    }

    public void ClickProdavci(View view) {
        Prodavci.redirectActivity(this, Prodavci.class);
    }

    public void ClickProfil(View view) {
        recreate();
    }

    public void ClickOdjava(View view) {
        Prodavci.odjava(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Prodavci.closeDrawer(drawerLayout);
    }
}