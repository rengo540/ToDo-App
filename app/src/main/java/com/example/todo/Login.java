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

public class Login extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText loginEmail,loginPwd;
    private Button loginBtn;
    private TextView loginQ;

    private FirebaseAuth auth;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_layout);

        toolbar=findViewById(R.id.loginToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        auth=FirebaseAuth.getInstance();
        loader=new ProgressDialog(this);




        loginEmail=findViewById(R.id.loginEmail);
        loginPwd=findViewById(R.id.loginPassword);
        loginBtn=findViewById(R.id.loginButton);
        loginQ=findViewById(R.id.loginPageQuestion);

        loginQ.setOnClickListener((v)->{
            Intent intent = new Intent(Login.this,Register.class);
            startActivity(intent);
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginEmail.getText().toString().trim();
                String password = loginPwd.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    loginEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    loginPwd.setError("Password is required");
                    return;
                }else {
                    loader.setMessage("Login in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(Login.this,Home.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }else{
                                String error=task.getException().toString();
                                Toast.makeText(Login.this, "Login faild", Toast.LENGTH_SHORT).show();
                                loader.dismiss();


                            }
                        }
                    });


                }


            }
        });


    }


}