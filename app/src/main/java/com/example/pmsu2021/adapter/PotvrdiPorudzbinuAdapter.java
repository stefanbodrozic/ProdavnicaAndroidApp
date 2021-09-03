package com.example.pmsu2021.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.Model.PorudzbinaFinal;
import com.example.pmsu2021.Model.PorudzbinaStavka;
import com.example.pmsu2021.PMSUApplication;
import com.example.pmsu2021.PotvrdiPorudzbinu;
import com.example.pmsu2021.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PotvrdiPorudzbinuAdapter extends ArrayAdapter<PorudzbinaStavka> {

    private Context context;
    private List<PorudzbinaStavka> stavkePorudzbine;

    public PotvrdiPorudzbinuAdapter(Context context, List<PorudzbinaStavka> stavkePorudzbine) {

        super(context, R.layout.lv_potvrdi_porudzbinu_layout, stavkePorudzbine);

        this.context = context;
        this.stavkePorudzbine = stavkePorudzbine;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // convertView je konekcija ka layout-u
        convertView = inflater.inflate(R.layout.lv_potvrdi_porudzbinu_layout, parent, false);

        TextView tvNazivArtikla = convertView.findViewById(R.id.tv_artikal_naziv_artikla);
        TextView tvKolicina = convertView.findViewById(R.id.tv_kolicina_final);
        TextView tvCenaPojedinacno = convertView.findViewById(R.id.tv_cena_pojedinacno_final);
        TextView tvCenaUkupnoFinal = convertView.findViewById(R.id.tv_cena_ukupno_final);

        tvNazivArtikla.setText(stavkePorudzbine.get(position).getArtikalNaziv());
        tvKolicina.setText(String.valueOf(stavkePorudzbine.get(position).getKolicina()));
        tvCenaPojedinacno.setText(String.valueOf(stavkePorudzbine.get(position).getCenaArtikla()));

        double cena = stavkePorudzbine.get(position).getKolicina() * stavkePorudzbine.get(position).getCenaArtikla();

        tvCenaUkupnoFinal.setText(String.valueOf(cena));

        return convertView;
    }
}
