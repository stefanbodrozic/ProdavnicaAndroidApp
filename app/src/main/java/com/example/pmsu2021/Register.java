package com.example.pmsu2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.os.Bundle;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.time.LocalDate;
import java.util.Date;


public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String[] uloge_korisnika = {"Kupac", "Prodavac"};
    Spinner spinner;
    String spinner_text;
    EditText etIme, etPrezime, etRegistracijaUsername, etRegistracijaPassword, etAdresa, etRegistracijaNaziv, etRegistracijaEmail;
    Button btnRegistracija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (Spinner) findViewById(R.id.registracija_spinner);
        spinner.setOnItemSelectedListener((OnItemSelectedListener) this);
        ArrayAdapter adapter = new ArrayAdapter(Register.this, android.R.layout.simple_spinner_item, uloge_korisnika);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        etIme = findViewById(R.id.et_ime);
        etPrezime = findViewById(R.id.et_prezime);
        etRegistracijaUsername = findViewById(R.id.et_registracija_username);
        etRegistracijaPassword = findViewById(R.id.et_registracija_password);
        etAdresa = findViewById(R.id.et_adresa);
        etRegistracijaNaziv = findViewById(R.id.et_registracija_naziv);
        etRegistracijaEmail = findViewById(R.id.et_registracija_email);

        btnRegistracija = findViewById(R.id.btn_registracija);

        btnRegistracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etIme.getText().toString().isEmpty() || etPrezime.getText().toString().isEmpty() || etRegistracijaUsername.getText().toString().isEmpty() ||
                        etRegistracijaPassword.getText().toString().isEmpty() || etAdresa.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this,"Unesite sve podatke!", Toast.LENGTH_LONG).show();
                }
                else {

                    String ime = etIme.getText().toString().trim();
                    String prezime = etPrezime.getText().toString().trim();
                    String username = etRegistracijaUsername.getText().toString().trim();
                    String password = etRegistracijaPassword.getText().toString().trim();
                    String adresa = etAdresa.getText().toString().trim();
                    String email = "";
                    String naziv = "";

                    String uloga = spinner.getSelectedItem().toString();
                    if(uloga == "Prodavac") {
                        if(etRegistracijaEmail.getText().toString().isEmpty() || etRegistracijaNaziv.getText().toString().isEmpty()) {
                            Toast.makeText(Register.this,"Unesite sve podatke!", Toast.LENGTH_LONG).show();
                        } else {
                            email = etRegistracijaEmail.getText().toString().trim();
                            naziv = etRegistracijaNaziv.getText().toString().trim();
                        }
                    }

                    BackendlessUser user = new BackendlessUser();
                    user.setProperty("ime", ime);
                    user.setProperty("prezime", prezime);
                    user.setProperty("username", username);
                    user.setPassword(password);
                    user.setProperty("adresa", adresa);
                    user.setProperty("blokiran", false);

                    if(uloga == "Prodavac") {
                        user.setEmail(email);
                        user.setProperty("naziv", naziv);
                        user.setProperty("tipKorisnika", "Prodavac");
                        // poslujeOd ce biti created vrednost
                    } else {
                        user.setProperty("tipKorisnika", "Kupac");
                    }

                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(Register.this,"Uspesno ste se registrovali!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Register.this, Login.class));
                            Register.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(Register.this,"Error: " + fault.getDetail(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner_text = spinner.getSelectedItem().toString();

        if(spinner_text == "Prodavac") {
            etRegistracijaNaziv.setVisibility(View.VISIBLE);
            etRegistracijaEmail.setVisibility(View.VISIBLE);
        } else {
            etRegistracijaNaziv.setVisibility(View.INVISIBLE);
            etRegistracijaEmail.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}