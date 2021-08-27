package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final int splash=2400;
    Animation topanim,bottomanim;
    ImageView imagee;
    TextView textView;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topanim= AnimationUtils.loadAnimation(this,R.anim.top_anime);
        bottomanim= AnimationUtils.loadAnimation(this,R.anim.bottom_anime);
        imagee=findViewById(R.id.my_image);
        textView=findViewById(R.id.text);
        imagee.setAnimation(topanim);
        textView.setAnimation(bottomanim);

        auth=FirebaseAuth.getInstance();


        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        },splash);*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser= auth.getCurrentUser();
        if(firebaseUser !=null){
            Intent intent=new Intent(MainActivity.this,Home.class);
            startActivity(intent);

        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(MainActivity.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            },splash);

        }
    }
}
