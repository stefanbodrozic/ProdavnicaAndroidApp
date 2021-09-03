package com.example.pmsu2021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.Media;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.UploadCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.example.pmsu2021.Model.Artikal;
import com.example.pmsu2021.adapter.ArtikliAdapterZaProdavca;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoviArtikal extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    String currentPhotoPath;

    ImageView ivUploadSlike;
    Button btnSacuvaj, btnGalerija, btnKamera;
    EditText etNazivArtikla, etCenaNovogArtikla, etOpisNovogArtikla;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novi_artikal);

        ivUploadSlike = findViewById(R.id.iv_upload_slike);

        btnGalerija = findViewById(R.id.btn_galerija);
        btnKamera = findViewById(R.id.btn_kamera);
        btnSacuvaj = findViewById(R.id.btn_sacuvaj_novi_artikal);
        etNazivArtikla = findViewById(R.id.et_naziv_novog_artikla);
        etCenaNovogArtikla = findViewById(R.id.et_cena_novog_artikla);
        etOpisNovogArtikla = findViewById(R.id.et_opis_novog_artikla);

        btnSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etNazivArtikla.getText().toString().isEmpty() || etCenaNovogArtikla.getText().toString().isEmpty() || etOpisNovogArtikla.getText().toString().isEmpty()) {
                    Toast.makeText(NoviArtikal.this, "Polja za unos podataka moraju biti popunjena", Toast.LENGTH_SHORT).show();
                } else {

                    String naziv = etNazivArtikla.getText().toString();
                    Double cena = Double.parseDouble(etCenaNovogArtikla.getText().toString());
                    String opis = etOpisNovogArtikla.getText().toString();
                    String imeProdavca = PMSUApplication.prijavljeniKorisnik.getProperty("ime").toString();

                    Artikal artikal = new Artikal();
                    artikal.setNazivArtikla(naziv);
                    artikal.setCena(cena);
                    artikal.setOpis(opis);
                    artikal.setImeProdavca(imeProdavca);


                    String timestamp = new SimpleDateFormat("yyyyMMdd__HHmmss").format(new Date());
                    final String fileName = "PNG_" + timestamp + ".png";

                    artikal.setFileColumn(fileName);

                    Backendless.Files.Android.upload(
                            bitmap,
                            Bitmap.CompressFormat.PNG,
                            100,
                            fileName,
                            "images",
                            new AsyncCallback<BackendlessFile>() {
                                @Override
                                public void handleResponse(BackendlessFile response) {

                                    // cuvam artikal

                                    Backendless.Persistence.save(artikal, new AsyncCallback<Artikal>() {
                                        @Override
                                        public void handleResponse(Artikal response) {
                                            Toast.makeText(NoviArtikal.this, "Uspesno kreiran novi artikal!", Toast.LENGTH_SHORT).show();

                                            setResult(RESULT_OK);
                                            NoviArtikal.this.finish();

                                        }

                                        @Override
                                        public void handleFault(BackendlessFault fault) {
                                            Toast.makeText(NoviArtikal.this, "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(NoviArtikal.this, "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });

        btnKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);

            }
        });

        btnGalerija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), 2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            bitmap = (Bitmap) data.getExtras().get("data");

            ivUploadSlike.setImageBitmap(bitmap);
        }

        if (requestCode == 2) {
            Uri imageUri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                ivUploadSlike.setImageBitmap(bitmap);
                ivUploadSlike.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}