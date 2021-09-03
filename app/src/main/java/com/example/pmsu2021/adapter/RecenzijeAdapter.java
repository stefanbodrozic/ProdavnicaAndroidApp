package com.example.pmsu2021.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pmsu2021.Model.PorudzbinaFinal;
import com.example.pmsu2021.R;

import java.util.ArrayList;
import java.util.List;

public class RecenzijeAdapter extends ArrayAdapter<PorudzbinaFinal> {

    private Context context;
    private List<PorudzbinaFinal> porudzbine;

    public RecenzijeAdapter(Context context, List<PorudzbinaFinal> porudzbine) {

        super(context, R.layout.lv_recenzija_layout, porudzbine);

        this.context = context;
        this.porudzbine = porudzbine;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // convertView je konekcija ka layout-u
        convertView = inflater.inflate(R.layout.lv_recenzija_layout, parent, false);

        TextView tvPorudzbinaSifra = convertView.findViewById(R.id.tv_porudzbina_sifra);
        TextView tvPorudzbinaKupac = convertView.findViewById(R.id.tv_porudzbina_kupac);
        TextView tvPorudzbinaAdresa = convertView.findViewById(R.id.tv_porudzbina_adresa_kupca);
        TextView tvPorudzbinaCena = convertView.findViewById(R.id.tv_porudzbina_cena);


        tvPorudzbinaSifra.setText("Sifra porudzbine: " + porudzbine.get(position).getSifraPorudzbine());
        tvPorudzbinaKupac.setText("Kupac: " +porudzbine.get(position).getKupac());
        tvPorudzbinaAdresa.setText("Adresa isporuke: " +porudzbine.get(position).getAdresaKupca());
        tvPorudzbinaCena.setText("Cena: " + String.valueOf(porudzbine.get(position).getUkupnaCena()));


        return convertView;
    }
}
