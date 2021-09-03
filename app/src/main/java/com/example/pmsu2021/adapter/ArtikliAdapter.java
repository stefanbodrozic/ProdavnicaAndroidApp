package com.example.pmsu2021.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.pmsu2021.ArtikalEdit;
import com.example.pmsu2021.ListaArtikala;
import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.PMSUApplication;
import com.example.pmsu2021.R;
import com.example.pmsu2021.RecenzijaDetaljnoFinal;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class ArtikliAdapter extends ArrayAdapter<Artikal> {

    private Context context;
    private List<Artikal> artikli;

    public ArtikliAdapter(Context context, List<Artikal> artikli) {

        super(context, R.layout.lv_artikli_layout, artikli);

        this.context = context;
        this.artikli = artikli;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // convertView je konekcija ka layout-u
        convertView = inflater.inflate(R.layout.lv_artikli_layout, parent, false);

        ImageView ivSlikaArtikla = convertView.findViewById(R.id.iv_slika_artikla);
        TextView tvNazivArtikla = convertView.findViewById(R.id.tv_artikal_naziv);
        TextView etOpisArtikla = convertView.findViewById(R.id.et_artikal_opis);
        TextView tvCenaArtikla = convertView.findViewById(R.id.tv_cena_artikla);
        TextView tvCenaNaPopustu = convertView.findViewById(R.id.tv_cena_artikla_popust);
        TextView tvKolicinaArtikla = convertView.findViewById(R.id.tv_kolicina_artikla);
        Button btnDodajUKorpu = convertView.findViewById(R.id.btn_dodaj_u_korpu);

        tvNazivArtikla.setText(artikli.get(position).getNazivArtikla());
        etOpisArtikla.setText(artikli.get(position).getOpis());
        tvCenaArtikla.setText(String.valueOf(artikli.get(position).getCena()));

        String url = "https://backendlessappcontent.com/" + PMSUApplication.APPLICATION_ID + "/" + PMSUApplication.API_KEY + "/files/images/" + artikli.get(position).getFileColumn();

        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_cancel)
                .into(ivSlikaArtikla);

        if (artikli.get(position).getCenaNaPopustu() != 0) {
            tvCenaArtikla.setPaintFlags(tvCenaArtikla.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvCenaNaPopustu.setVisibility(View.VISIBLE);
            tvCenaNaPopustu.setText(String.valueOf(artikli.get(position).getCenaNaPopustu()));
        }


        btnDodajUKorpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // poslati kolicinu artikala
                String artikal = artikli.get(position).getNazivArtikla();
                String kolicina = tvKolicinaArtikla.getText().toString();
                String cena = "";

                if (artikli.get(position).getCenaNaPopustu() != 0) {
                    cena = String.valueOf(artikli.get(position).getCenaNaPopustu());
                } else {
                    cena = String.valueOf(artikli.get(position).getCena());
                }

                Intent intent = new Intent("custom-message");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtra("kolicina",kolicina);
                intent.putExtra("artikal",artikal);
                intent.putExtra("cena", cena);

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }
        });

        return convertView;

    }
}
