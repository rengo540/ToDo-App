package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private EditText regEmail , regPassword ;
    private Button regBtn ;
    private TextView regTologin;
    private Toolbar toolbar;
    private FirebaseAuth Fau;
    private ProgressDialog loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register_layout);


        toolbar=findViewById(R.id.registerToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");

        Fau = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);


        regEmail=findViewById(R.id.registerEmail);
        regPassword=findViewById(R.id.registerPassword);
        regBtn=findViewById(R.id.registerButton);
        regTologin=findViewById(R.id.registerPageQuestion);


        regTologin.setOnClickListener((v) -> {
            Intent intent = new Intent(Register.this,Login.class);
            startActivity(intent);
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = regEmail.getText().toString().trim();
                String password = regPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                {
                    regEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    regPassword.setError("Password is required");
                    return;
                }
                else
                {

                    loader.setMessage("Registration in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    Fau.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Intent intent = new Intent(Register.this, Home.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }
                            else
                            {
                                String error = task.getException().toString();
                                Toast.makeText(Register.this, "Registration failed" + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }


                        }
                    });

                }


            }
        });







    }

}