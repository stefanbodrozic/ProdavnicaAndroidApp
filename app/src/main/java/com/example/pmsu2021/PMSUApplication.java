package com.example.pmsu2021;

import android.app.Application;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.pmsu2021.Model.Akcija;
import com.example.pmsu2021.Model.Artikal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//uvek se pokrece pre svega (cak i MainActivity), zatvara se poslednja
public class PMSUApplication extends Application {

    public static final String APPLICATION_ID = "A734C9E3-063D-CEB2-FF96-067C94F09000";
    public static final String API_KEY = "23982A56-4C73-46FA-8338-FD1431FD34B6";
    public static final String SERVER_URL = "https://api.backendless.com";

    public static List<BackendlessUser> prodavci;
    public static BackendlessUser prijavljeniKorisnik;
    public static List<Artikal> artikli;
    public static List<Artikal> artikliBezAkcije;

    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY);
    }

}
