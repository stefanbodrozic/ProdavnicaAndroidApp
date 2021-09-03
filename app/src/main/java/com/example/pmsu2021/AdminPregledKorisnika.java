package com.example.pmsu2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.pmsu2021.adapter.AdminAdapter;
import com.example.pmsu2021.adapter.ProdavciAdapter;

import java.util.List;

public class AdminPregledKorisnika extends AppCompatActivity {

    ListView lvKorisnici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pregled_korisnika);

        lvKorisnici = findViewById(R.id.lv_korisnici);

        Backendless.Data.of(BackendlessUser.class).find(new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response) {

                AdminAdapter adminAdapter = new AdminAdapter(AdminPregledKorisnika.this, response);
                lvKorisnici.setAdapter(adminAdapter);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(AdminPregledKorisnika.this, "Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}