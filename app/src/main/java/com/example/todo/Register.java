package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {
    private EditText regEmail , regPassword ;
    private Button regBtn ;
    private TextView regTologin;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register_layout);


        toolbar=findViewById(R.id.registerToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");


        regEmail=findViewById(R.id.registerEmail);
        regPassword=findViewById(R.id.registerPassword);
        regBtn=findViewById(R.id.registerButton);
        regTologin=findViewById(R.id.registerPageQuestion);







    }

}