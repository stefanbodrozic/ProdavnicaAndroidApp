package com.example.pmsu2021.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.backendless.BackendlessUser;
import com.example.pmsu2021.Model.Korisnik;
import com.example.pmsu2021.Prodavci;
import com.example.pmsu2021.R;

import org.w3c.dom.Text;

import java.util.List;

public class ProdavciAdapter extends ArrayAdapter<BackendlessUser> {

    private Context context;
    private List<BackendlessUser> prodavci;

    public ProdavciAdapter(Context context, List<BackendlessUser> prodavci) {

        super(context, R.layout.lv_prodavci_layout, prodavci);

        this.context = context;
        this.prodavci = prodavci;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // convertView je konekcija ka layout-u
        convertView = inflater.inflate(R.layout.lv_prodavci_layout, parent, false);

        TextView tvProdavacNaziv = convertView.findViewById(R.id.tv_prodavac_naziv);
        TextView tvProdavacAdresa = convertView.findViewById(R.id.tv_prodavac_adresa);

        tvProdavacNaziv.setText(prodavci.get(position).getProperty("naziv").toString());
        tvProdavacAdresa.setText(prodavci.get(position).getProperty("adresa").toString());

        return convertView;
    }
}
