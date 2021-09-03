package com.example.pmsu2021.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.pmsu2021.AdminPregledKorisnika;
import com.example.pmsu2021.ArtikalEdit;
import com.example.pmsu2021.Komentari;
import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.PMSUApplication;
import com.example.pmsu2021.R;

import java.util.List;

public class AdminAdapter extends ArrayAdapter<BackendlessUser> {

    private Context context;
    private List<BackendlessUser> korisnici;

    public AdminAdapter(Context context, List<BackendlessUser> korisnici) {

        super(context, R.layout.lv_korisnici_layout, korisnici);

        this.context = context;
        this.korisnici = korisnici;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // convertView je konekcija ka layout-u
        convertView = inflater.inflate(R.layout.lv_korisnici_layout, parent, false);

        TextView tvKorisnikIme = convertView.findViewById(R.id.tv_korisnik_ime);
        TextView tvKorisnikUloga = convertView.findViewById(R.id.tv_korisnik_uloga);
        TextView tvBlokiran = convertView.findViewById(R.id.tv_korisnik_blokiran);
        Button btnBlokiraj = convertView.findViewById(R.id.blokiraj_korisnika);

        tvKorisnikIme.setText(korisnici.get(position).getProperty("ime").toString());
        tvKorisnikUloga.setText(korisnici.get(position).getProperty("tipKorisnika").toString());
        tvBlokiran.setText("Blokiran: " + korisnici.get(position).getProperty("blokiran").toString());

        if (korisnici.get(position).getProperty("blokiran").toString().equals("true")) {
            btnBlokiraj.setVisibility(View.GONE);
        }

        btnBlokiraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                korisnici.get(position).setProperty("blokiran", true);

                Backendless.Persistence.save(korisnici.get(position), new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(context, "Korisnik blokiran!", Toast.LENGTH_SHORT).show();
                        ((AdminPregledKorisnika)context).recreate();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(context, "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        return convertView;
    }
}
