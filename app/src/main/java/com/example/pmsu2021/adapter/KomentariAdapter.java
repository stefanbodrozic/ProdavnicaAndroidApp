package com.example.pmsu2021.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.pmsu2021.ArtikalEdit;
import com.example.pmsu2021.Komentari;
import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.Model.PorudzbinaFinal;
import com.example.pmsu2021.PMSUApplication;
import com.example.pmsu2021.R;

import java.util.List;

public class KomentariAdapter extends ArrayAdapter<PorudzbinaFinal> {

    private Context context;
    private List<PorudzbinaFinal> porudzbine;

    public KomentariAdapter(Context context, List<PorudzbinaFinal> porudzbine) {

        super(context, R.layout.lv_komentari_layout, porudzbine);

        this.context = context;
        this.porudzbine = porudzbine;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // convertView je konekcija ka layout-u
        convertView = inflater.inflate(R.layout.lv_komentari_layout, parent, false);

        TextView tvImeKupca = convertView.findViewById(R.id.komentari_ime_kupca);
        TextView tvKomentar = convertView.findViewById(R.id.komentari_komentar_text);
        TextView tvOcena = convertView.findViewById(R.id.komentari_ocena);
        ImageView ivArhiviraj = convertView.findViewById(R.id.arhiviraj_komentar);

        if(porudzbine.get(position).getAnonimanKomentar()) {
            tvImeKupca.setText("Kupac: " + "Korisnik je ostavio anoniman komentar");
        } else {
            tvImeKupca.setText("Kupac: " + porudzbine.get(position).getKupac());
        }

        tvKomentar.setText("Komentar: " +porudzbine.get(position).getKomentar());

        tvOcena.setText("Ocena: " + String.valueOf(porudzbine.get(position).getOcena()));


        if (!PMSUApplication.prijavljeniKorisnik.getProperty("tipKorisnika").equals("Prodavac")) {
            ivArhiviraj.setVisibility(View.GONE);
        }

        if (!porudzbine.get(position).getProdavac().equals(PMSUApplication.prijavljeniKorisnik.getProperty("username"))) {
            ivArhiviraj.setVisibility(View.GONE);
        }

        ivArhiviraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                dialog.setMessage("Da li ste sigurni da zelite da arhivirate ovaj komentar?");

                dialog.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        porudzbine.get(position).setArhiviranKomentar(true);

                        Backendless.Persistence.save(porudzbine.get(position), new AsyncCallback<PorudzbinaFinal>() {
                            @Override
                            public void handleResponse(PorudzbinaFinal response) {
                                Toast.makeText(context, "Komentar je arhiviran", Toast.LENGTH_SHORT).show();
                                ((Komentari)context).recreate();
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

        return convertView;

    }
}
