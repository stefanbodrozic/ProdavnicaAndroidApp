package com.example.pmsu2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.example.pmsu2021.Model.Kupac;

public class Login extends AppCompatActivity {

    TextView tvLogo, tvUsername, tvPassword;
    EditText etUsername, etPassword;
    Button btnPrijava, btnRegistracija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        btnPrijava = findViewById(R.id.btn_prijava);
        btnRegistracija = findViewById(R.id.btn_login_registracija);

        btnPrijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this,"Popunite sva polja!", Toast.LENGTH_LONG).show();
                } else {

                    String username = etUsername.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();

                    Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {

                            if (response.getProperty("blokiran").toString().equals("true")) {
                                Toast.makeText(Login.this,"Korisnik je blokiran i nema pristup aplikaciji!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Toast.makeText(Login.this,"Uspesno ste se prijavili!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, Prodavci.class));

                            PMSUApplication.prijavljeniKorisnik = response;

                            Login.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(Login.this,"Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }, true);
                }

            }
        });

        btnRegistracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this, Register.class));

            }
        });

    }
}