package com.example.pmsu2021.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.NapraviAkciju;
import com.example.pmsu2021.PMSUApplication;
import com.example.pmsu2021.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtikliAdapterZaPravljenjeAkcije extends ArrayAdapter<Artikal> {

    private Context context;
    private List<Artikal> artikli;

    public ArtikliAdapterZaPravljenjeAkcije(Context context, List<Artikal> artikli) {
        super(context, R.layout.lv_artikli_za_pravljenje_akcija_layout, artikli);

        this.context = context;
        this.artikli = artikli;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // convertView je konekcija ka layout-u
        convertView = inflater.inflate(R.layout.lv_artikli_za_pravljenje_akcija_layout, parent, false);

        ImageView ivSlikaArtiklaAkcija = convertView.findViewById(R.id.iv_slika_artikla_akcija);
        TextView tvNazivArtiklaAkcija = convertView.findViewById(R.id.tv_artikal_naziv_akcija);
        EditText etOpisArtiklaAkcija = convertView.findViewById(R.id.et_artikal_opis_akcija);
        TextView tvCenaArtiklaAkcija = convertView.findViewById(R.id.tv_cena_artikla_akcija);

        tvNazivArtiklaAkcija.setText(artikli.get(position).getNazivArtikla());
        etOpisArtiklaAkcija.setText(artikli.get(position).getOpis());
        tvCenaArtiklaAkcija.setText(String.valueOf(artikli.get(position).getCena()));

        String url = "https://backendlessappcontent.com/" + PMSUApplication.APPLICATION_ID + "/" + PMSUApplication.API_KEY + "/files/images/" + artikli.get(position).getFileColumn();

        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_cancel)
                .into(ivSlikaArtiklaAkcija);

        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        checkBox.setTag(position);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int position = (int) buttonView.getTag();

                if (NapraviAkciju.korisnikIzabrao.contains(artikli.get(position))) {

                    NapraviAkciju.korisnikIzabrao.remove(artikli.get(position));

                } else {

                    NapraviAkciju.korisnikIzabrao.add(artikli.get(position));

                }

            }
        });


        return convertView;
    }
}
