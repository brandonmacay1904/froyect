package com.hivluver.facebook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.google.rpc.LocalizedMessageOrBuilder;

public class LoginActivity extends AppCompatActivity {
    EditText emailclick;
    EditText passclick;
    TextView btnsecret;
    Button resetbtn;
    Button registerbtn;
    TextView englishtxt,italiantxt;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailclick = findViewById(R.id.emailclick);
        passclick = findViewById(R.id.passclick);
        btnsecret =  findViewById(R.id.btnsecrett);
        btnsecret.setText(" "+getResources().getString(R.string.m_s));
        resetbtn = findViewById(R.id.btnresetaccount);
        registerbtn = findViewById(R.id.registerbtn);
        englishtxt = findViewById(R.id.txtenglish);
        italiantxt = findViewById(R.id.txtitalian);
        italiantxt.setText(" "+getResources().getString(R.string.italiano));

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ResetPassActivity.class);
                intent.putExtra("message",getResources().getString(R.string.crear_cuenta));
                startActivity(intent);
            }
        });
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ResetPassActivity.class);
                intent.putExtra("message",getResources().getString(R.string.recupera_tu_cuenta));
                startActivity(intent);
            }
        });
        btnsecret.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(LoginActivity.this,AccountsList.class));
                return false;
            }
        });
        btnsecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final KAlertDialog dialog = new KAlertDialog(LoginActivity.this);
                dialog.setTitleText(getResources().getString(R.string.error_de_conexi_n));
                dialog.setContentText(getResources().getString(R.string.no_obtuvimos_nuevos_idiomas_intentelo_mas_tarde));
                dialog.confirmButtonColor(R.color.azulfuerte);
                dialog.setConfirmText("Ok").setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog kAlertDialog) {
                        dialog.dismissWithAnimation();
                    }
                });
                dialog.show();
            }
        });
        emailclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Login2.class);
                intent.putExtra("type","email");
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        passclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Login2.class);
                intent.putExtra("type","password");
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        englishtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (englishtxt.getText().toString().contentEquals("English •")){
                    setLocale("en");
                }else if (englishtxt.getText().toString().contentEquals("Español •")){
                    setLocale("es");
                }
            }
        });
        italiantxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (italiantxt.getText().toString().contentEquals(" Italiano •")){
                    setLocale("it");
                }else if (italiantxt.getText().toString().contentEquals(" Español •")){
                    setLocale("es");
                }

            }
        });

    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, LoginActivity.class);
        startActivity(refresh);
        finish();
        overridePendingTransition(0,0);
    }
}