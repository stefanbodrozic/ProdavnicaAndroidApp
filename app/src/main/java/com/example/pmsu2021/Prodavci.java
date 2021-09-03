package com.example.pmsu2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.example.pmsu2021.adapter.ProdavciAdapter;

import java.util.ArrayList;
import java.util.List;

public class Prodavci extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ListView lvProdavci;

    ProdavciAdapter prodavciAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodavci);

        drawerLayout = findViewById(R.id.drawer_layout);
        lvProdavci = findViewById(R.id.lv_prodavci);

        lvProdavci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Prodavci.this, ListaArtikala.class);
                intent.putExtra("index", position);  // index prodavca iz liste
                startActivity(intent);

            }
        });

        List<BackendlessUser> listaProdavaca = new ArrayList<>();

        Backendless.Data.of(BackendlessUser.class).find(new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response) {

                for (BackendlessUser user: response) {
                    if(user.getProperty("tipKorisnika").equals("Prodavac")){
                        listaProdavaca.add(user);
                        if (!user.getProperty("username").equals(PMSUApplication.prijavljeniKorisnik.getProperty("username")) && PMSUApplication.prijavljeniKorisnik.getProperty("tipKorisnika").equals("Prodavac")) {
                            listaProdavaca.remove(user);
                        }
                    }
                }

                PMSUApplication.prodavci = listaProdavaca; // da bih imao pristup istoj listi
                prodavciAdapter = new ProdavciAdapter(Prodavci.this, listaProdavaca);
                lvProdavci.setAdapter(prodavciAdapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(Prodavci.this, "Greska: " + fault.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public void ClickProdavci(View view) {
        recreate();
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //ako je drawer otvoren zatvori ga
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    public void ClickProfil(View view) {
        redirectActivity(this, Profil.class);
    }

    public void ClickOdjava(View view) {
        odjava(this);
    }

    public static void odjava(Activity activity) {
        // initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // title
        builder.setTitle("Odjava");
        // message
        builder.setMessage("Da li ste sigurni da zelite da se odjavite?");
        // da
        builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    Backendless.UserService.logout(new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            //finish activity
                            activity.finishAffinity();
                            //exit
                            System.exit(0);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                }
                catch (BackendlessException exception) {

                }

            }
        });

        // ne
        builder.setNegativeButton("NE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // dismiss dialog
                dialog.dismiss();
            }
        });

        //show dialog
        builder.show();

    }

    public static void redirectActivity(Activity activity, Class aClass) {
        //initialize intent
        Intent intent = new Intent(activity, aClass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}