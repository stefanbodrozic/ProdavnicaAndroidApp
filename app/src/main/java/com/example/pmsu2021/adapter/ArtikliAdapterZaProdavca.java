package com.example.pmsu2021.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.pmsu2021.ArtikalEdit;
import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.PMSUApplication;
import com.example.pmsu2021.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class ArtikliAdapterZaProdavca extends ArrayAdapter<Artikal> {

    private Context context;
    private List<Artikal> artikli;

    public ArtikliAdapterZaProdavca(Context context, List<Artikal> artikli) {

        super(context, R.layout.lv_artikli_za_prodavca_layout, artikli);

        this.context = context;
        this.artikli = artikli;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // convertView je konekcija ka layout-u
        convertView = inflater.inflate(R.layout.lv_artikli_za_prodavca_layout, parent, false);

        ImageView ivSlikaArtiklaZaProdavca = convertView.findViewById(R.id.iv_slika_artikla_za_prodavca);
        TextView tvNazivArtiklaZaProdavca = convertView.findViewById(R.id.tv_artikal_naziv_za_prodavca);
        TextView etOpisArtiklaZaProdavca = convertView.findViewById(R.id.et_artikal_opis_za_prodavca);
        TextView tvCenaArtiklaZaProdavca = convertView.findViewById(R.id.tv_cena_artikla_za_prodavca);

        tvNazivArtiklaZaProdavca.setText(artikli.get(position).getNazivArtikla());
        etOpisArtiklaZaProdavca.setText(artikli.get(position).getOpis());
        tvCenaArtiklaZaProdavca.setText(String.valueOf(artikli.get(position).getCena()));

        String url = "https://backendlessappcontent.com/" + PMSUApplication.APPLICATION_ID + "/" + PMSUApplication.API_KEY + "/files/images/" + artikli.get(position).getFileColumn();

        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_cancel)
                .into(ivSlikaArtiklaZaProdavca);

        Button btnDelete = convertView.findViewById(R.id.btn_izbrisi_artikal_za_prodavca);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                dialog.setMessage("Da li ste sigurni da zelite da izbrise ovaj artikal?");

                dialog.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Backendless.Persistence.of(Artikal.class).remove(PMSUApplication.artikli.get(position), new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                PMSUApplication.artikli.remove(position);
                                Toast.makeText(context, "Uspesno izbrisan artikal!", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(context, "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                dialog.setNegativeButton("NE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.show();

            }
        });


        Button btnEdit = convertView.findViewById(R.id.btn_izmeni_artikal_za_prodavca);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ArtikalEdit.class);

                intent.putExtra("index", position); //saljem poziciju artikla na koji sam kliknuo

                ((Activity) context).startActivityForResult(intent, 1); //odlazimo u novu aktivnost (artikaledit) i vracamo se u ovu (lista..)

            }
        });

        return convertView;

    }

}