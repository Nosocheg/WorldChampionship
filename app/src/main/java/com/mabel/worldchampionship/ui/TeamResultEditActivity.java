package com.mabel.worldchampionship.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mabel.worldchampionship.R;
import com.mabel.worldchampionship.database.AppDatabase;
import com.mabel.worldchampionship.database.RoomWrapper;
import com.mabel.worldchampionship.database.TeamResultEntity;

public class TeamResultEditActivity extends AppCompatActivity {

    private EditText etTeam, etGroup, etGames, etWins, etDraws, etLoses, etGoals, etGoalsMisses, etScore;
    private Button btnSave;

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_result_edit);
        database = RoomWrapper.getDatabase(this);

        etTeam = findViewById(R.id.etTeam);
        etGroup = findViewById(R.id.etGroup);
        etGames = findViewById(R.id.etGames);
        etWins = findViewById(R.id.etWins);
        etDraws = findViewById(R.id.etDraws);
        etLoses = findViewById(R.id.etLoses);
        etGoals = findViewById(R.id.etGoals);
        etGoalsMisses = findViewById(R.id.etGoalsMisses);
        etScore = findViewById(R.id.etScore);
        btnSave = findViewById(R.id.btnSave);

        // предзаполняем поля данными из редактируемого матча
        if (getId() >= 0) {
            TeamResultEntity teamResultEntity = database.teamResultDao().getById(getId());
            etTeam.setText(teamResultEntity.getName());
            etGroup.setText(teamResultEntity.getGroup());
            etGames.setText(String.valueOf(teamResultEntity.getGames()));
            etWins.setText(String.valueOf(teamResultEntity.getWins()));
            etDraws.setText(String.valueOf(teamResultEntity.getDraws()));
            etLoses.setText(String.valueOf(teamResultEntity.getLoses()));
            etGoals.setText(String.valueOf(teamResultEntity.getGoals()));
            etGoalsMisses.setText(String.valueOf(teamResultEntity.getGoalsMisses()));
            etScore.setText(String.valueOf(teamResultEntity.getScore()));
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTeamResult();
            }
        });
    }

    private void saveTeamResult() {
        try {
            TeamResultEntity teamResultEntity = new TeamResultEntity(
                    etTeam.getText().toString(),
                    etGroup.getText().toString(),
                    Integer.parseInt(etGames.getText().toString()),
                    Integer.parseInt(etWins.getText().toString()),
                    Integer.parseInt(etDraws.getText().toString()),
                    Integer.parseInt(etLoses.getText().toString()),
                    Integer.parseInt(etGoals.getText().toString()),
                    Integer.parseInt(etGoalsMisses.getText().toString()),
                    Integer.parseInt(etScore.getText().toString())
            );

            // проверяем находится ли экран в режиме редактирования
            if (getId() >= 0) {
                // записываем в собранный по полям ввода результат команды id того результата, который должен быть изменен(с предыдущего экрана)
                teamResultEntity.setId(getId());
                database.teamResultDao().update(teamResultEntity);
            } else {
                database.teamResultDao().insert(teamResultEntity);
            }

            // закрытие экрана редактирования
            finish();
        } catch (NumberFormatException exception) {
            Toast.makeText(this, "Введите правильные данные во все поля", Toast.LENGTH_SHORT).show();
        }
    }

    // метод возвращает тот id с которым мы открыли этот экран, если >= 0 то находимся в режиме редактирования, если -1 значит режим создания
    private int getId() {
        return getIntent().getIntExtra("ID_TAG", -1);
    }
}