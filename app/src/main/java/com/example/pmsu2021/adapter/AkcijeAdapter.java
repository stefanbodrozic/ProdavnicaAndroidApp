package com.example.pmsu2021.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pmsu2021.Model.Akcija;
import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.R;

import java.util.List;

public class AkcijeAdapter extends ArrayAdapter<Akcija> {


    private Context context;
    private List<Akcija> akcije;

    public AkcijeAdapter(Context context, List<Akcija> akcije) {

        super(context, R.layout.lv_akcije_layout, akcije);

        this.context = context;
        this.akcije = akcije;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // convertView je konekcija ka layout-u
        convertView = inflater.inflate(R.layout.lv_akcije_layout, parent, false);

        TextView tvNazivAkcije = convertView.findViewById(R.id.tv_naziv_akcije);
        TextView tvOpisAkcije = convertView.findViewById(R.id.tv_akcija_opis);
        TextView tvDatumPocetkaAkcije = convertView.findViewById(R.id.tv_datum_pocetka_akcije);
        TextView tvDatumZavrsetkaAkcije = convertView.findViewById(R.id.tv_datum_zavrsetka_akcije);
        TextView tvAkcijaProdavac = convertView.findViewById(R.id.tv_akcija_prodavac);
        TextView tvAkcijaPopust = convertView.findViewById(R.id.tv_akcija_popust);

        tvNazivAkcije.setText(akcije.get(position).getNazivAkcije());
        tvOpisAkcije.setText(akcije.get(position).getOpis());
        tvDatumPocetkaAkcije.setText("Datum pocetka akcije: " + akcije.get(position).getDatumOd().toString());
        tvDatumZavrsetkaAkcije.setText("Datum zavrsetka akcije: " + akcije.get(position).getDatumDo().toString());
        tvAkcijaProdavac.setText("Ime prodavca: " + akcije.get(position).getImeProdavca());
        tvAkcijaPopust.setText("Popust %: " + String.valueOf(akcije.get(position).getPopust()));

        return convertView;

    }


}
