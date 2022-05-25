package com.mabel.worldchampionship.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mabel.worldchampionship.R;
import com.mabel.worldchampionship.database.AppDatabase;
import com.mabel.worldchampionship.database.MatchEntity;
import com.mabel.worldchampionship.database.RoomWrapper;
import com.mabel.worldchampionship.database.UserEntity;
import com.mabel.worldchampionship.ui.data.FlagFactory;
import com.mabel.worldchampionship.ui.data.Match;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView rvSchedule;
    private TextView tvNoSchedule;
    private FloatingActionButton fabAdd;
    private AppDatabase database;
    private MatchListAdapter matchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        database = RoomWrapper.getDatabase(this);

        rvSchedule = findViewById(R.id.rvSchedule);
        fabAdd = findViewById(R.id.fabAdd);
        tvNoSchedule = findViewById(R.id.tvNoSchedule);

        matchListAdapter = new MatchListAdapter(this);
        rvSchedule.setAdapter(matchListAdapter);

        // показывает кнопку только для администратора
        UserEntity userEntity = database.userDao().getLoggedInUser();
        if (userEntity != null && userEntity.isAdmin()) {
            fabAdd.setVisibility(View.VISIBLE);

            matchListAdapter.setOnMatchClickListener(new MatchListAdapter.ItemMatchClickListener() {
                @Override
                public void onItemClick(Match match) {
                    // открывает экран редактора матча в режиме редактирования(изменения существующего матча)
                    editMatch(match.getId());
                }
            });
            matchListAdapter.setOnMatchLongClickListener(new MatchListAdapter.ItemMatchClickListener() {
                @Override
                public void onItemClick(Match match) {
                    // удаляет матч при долгом нажатии на него
                    database.matchDao().deleteById(match.getId());
                    // обновляет список, чтобы удаленный матч больше не отображался
                    setMatches();
                }
            });
        } else {
            fabAdd.setVisibility(View.GONE);
        }

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // открывает экран редактора матча в режиме создания нового матча
                startActivity(new Intent(ScheduleActivity.this, ScheduleEditActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setMatches();
    }

    // показывает список матчей на экране
    private void setMatches() {
        // Список матчей для передаче в адаптер
        List<Match> matches = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy в HH:mm", Locale.getDefault());

        // Получения списка матчей хранящихся в базе
        List<MatchEntity> matchEntities = database.matchDao().getAll();
        // переменная для сохранения индекса ближайшего несыгранного матча
        int currentMatch = -1;
        // текущий момент времени
        long now = System.currentTimeMillis();
        // в цикле преобразует матчи из базы данных в матчи для адаптера
        for (int i = 0; i < matchEntities.size(); i++) {
            MatchEntity matchEntity = matchEntities.get(i);
            Match match = new Match(
                    matchEntity.getId(),
                    matchEntity.getTeamFirst(),
                    matchEntity.getTeamSecond(),
                    matchEntity.getGroup(),
                    matchEntity.getScore(),
                    formatter.format(new Date(matchEntity.getTimestamp())),
                    FlagFactory.getFlagRes(matchEntity.getTeamFirst()),
                    FlagFactory.getFlagRes(matchEntity.getTeamSecond())
            );
            matches.add(match);
            // проверяет дату и время матча и запоминает индекс ближайшего несыгранного матча
            if (currentMatch == -1 && matchEntity.getTimestamp() > now) {
                currentMatch = i;
            }
        }
        // отправляет список полученных матчей в адаптер
        matchListAdapter.setMatches(matches);
        // скроллит список до ближайшего несыгранного матча
        rvSchedule.scrollToPosition(currentMatch);

        // в случае когда список матчей пуст показывает текстовку "Список пуст"
        if (matches.isEmpty()) {
            tvNoSchedule.setVisibility(View.VISIBLE);
        } else {
            tvNoSchedule.setVisibility(View.GONE);
        }
    }

    // открывает экран редактора матча в режиме редактирования(изменения существующего матча)
    // для этого необходимо в интент передать id этого матча, чтобы было понятно какой матч редактируется
    private void editMatch(int id) {
        Intent intent = new Intent(ScheduleActivity.this, ScheduleEditActivity.class);
        intent.putExtra("ID_TAG", id);
        startActivity(intent);
    }
}