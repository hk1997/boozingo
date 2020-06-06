package com.example.boozingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.boozingo.util.Auth;
import com.example.boozingo.util.RetrofitClient;
import com.squareup.picasso.Picasso;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView image = findViewById(R.id.image_logo);
        Picasso.get().load(RetrofitClient.getBase_Url()+"/images/logo.png").into(image);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if(Auth.getAccessToken(getApplicationContext())==null){
                    i= new Intent(SplashScreen.this,AppActivity.class);
                }
                else{
                   i = new Intent(SplashScreen.this,MainActivity.class);
                   //  i = new Intent(SplashScreen.this,ShopDetails.class);
                }
                startActivity(i);
                finish();
            }
        },500);
    }
}
