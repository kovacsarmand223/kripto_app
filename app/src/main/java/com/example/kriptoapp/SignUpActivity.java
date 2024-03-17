package com.example.kriptoapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    EditText emailInput, jelszoInput;
    String email, jelszo;
    Button regisztGomb;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailInput = findViewById(R.id.emailRegist);
        jelszoInput = findViewById(R.id.passwordRegist);
        regisztGomb = findViewById(R.id.registButton);

        regisztGomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                email = emailInput.getText().toString();
                jelszo = jelszoInput.getText().toString();

                if (isValidEmail(email) && isValidPassword(jelszo)) {

                    mAuth.createUserWithEmailAndPassword(email, jelszo)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            Toast.makeText(SignUpActivity.this, "Felhasznalo letrehozva: " + user.getEmail(),
                                                    Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Nem sikerült az authentikáció!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        Button button = findViewById(R.id.bejelentkezAtiranyit);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }

    // Alapbol megnezi, hogy email formatumu-e ezert ezt nem kell implementalni
    private boolean isValidEmail(String email) {
        if(TextUtils.isEmpty(email)){
           return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if(TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }
}
