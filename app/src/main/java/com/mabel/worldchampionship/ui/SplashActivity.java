package com.mabel.worldchampionship.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mabel.worldchampionship.R;
import com.mabel.worldchampionship.database.RoomWrapper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (RoomWrapper.getDatabase(this).userDao().getLoggedInUser() != null) {
            startActivity(new Intent(SplashActivity.this, MenuActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }

        finish();
    }
}