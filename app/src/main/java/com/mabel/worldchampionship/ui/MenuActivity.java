package com.mabel.worldchampionship.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mabel.worldchampionship.R;
import com.mabel.worldchampionship.database.AppDatabase;
import com.mabel.worldchampionship.database.RoomWrapper;
import com.mabel.worldchampionship.database.UserEntity;

public class MenuActivity extends AppCompatActivity {

    private Button btnExit, btnSchedule, btnTournament;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        database = RoomWrapper.getDatabase(this);

        btnExit = findViewById(R.id.btnExit);
        btnSchedule = findViewById(R.id.btnSchedule);
        btnTournament = findViewById(R.id.btnTournament);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Разлогиниваем пользователя
                UserEntity userEntity = database.userDao().getLoggedInUser();
                userEntity.setLoggedIn(false);
                database.userDao().update(userEntity);

                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, ScheduleActivity.class));
            }
        });

        btnTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, TournamentActivity.class));
            }
        });
    }
}