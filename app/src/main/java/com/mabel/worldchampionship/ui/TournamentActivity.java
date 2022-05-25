package com.mabel.worldchampionship.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mabel.worldchampionship.R;
import com.mabel.worldchampionship.database.AppDatabase;
import com.mabel.worldchampionship.database.RoomWrapper;
import com.mabel.worldchampionship.database.TeamResultEntity;
import com.mabel.worldchampionship.database.UserEntity;
import com.mabel.worldchampionship.ui.data.ColumnNames;
import com.mabel.worldchampionship.ui.data.FlagFactory;
import com.mabel.worldchampionship.ui.data.GroupName;
import com.mabel.worldchampionship.ui.data.TeamResult;
import com.mabel.worldchampionship.ui.data.TournamentItem;

import java.util.ArrayList;
import java.util.List;

public class TournamentActivity extends AppCompatActivity {

    private RecyclerView rvTeamResults;
    private TextView tvNoResults;
    private FloatingActionButton fabAdd;
    private AppDatabase database;
    private TournamentItemsAdapter tournamentItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        database = RoomWrapper.getDatabase(this);

        rvTeamResults = findViewById(R.id.rvTeamResults);
        fabAdd = findViewById(R.id.fabAdd);
        tvNoResults = findViewById(R.id.tvNoResults);

        tournamentItemsAdapter = new TournamentItemsAdapter(this);
        rvTeamResults.setAdapter(tournamentItemsAdapter);

        // показывает кнопку только для администратора
        UserEntity userEntity = database.userDao().getLoggedInUser();
        if (userEntity != null && userEntity.isAdmin()) {
            fabAdd.setVisibility(View.VISIBLE);
            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // открывает экран редактора результата команды в режиме создания нового результата
                    startActivity(new Intent(TournamentActivity.this, TeamResultEditActivity.class));
                }
            });

            tournamentItemsAdapter.setOnTeamResultClickListener(new TournamentItemsAdapter.ItemTeamResultClickListener() {
                @Override
                public void onItemClick(TeamResult teamResult) {
                    // для администратора открывает экран редактирования для нажатаго результата команды
                    editTeamResult(teamResult.getId());
                }
            });

            tournamentItemsAdapter.setOnTeamResultLongClickListener(new TournamentItemsAdapter.ItemTeamResultClickListener() {
                @Override
                public void onItemClick(TeamResult teamResult) {
                    // удаляет результата команды при долгом нажатии на него
                    database.teamResultDao().deleteById(teamResult.getId());
                    // обновляет список, чтобы удаленный результат больше не отображался
                    setTeamResults();
                }
            });
        } else {
            fabAdd.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTeamResults();
    }

    // показывает список матчей на экране
    private void setTeamResults() {
        // Список результатов команд для передаче в адаптер
        List<TournamentItem> tournamentItems = new ArrayList<>();

        // Получения списка результатов команд хранящихся в базе
        List<TeamResultEntity> teamResultEntities = database.teamResultDao().getAll();

        // Для каждой группы делаем отдельный список
        List<TeamResultEntity> teamResultEntitiesA = new ArrayList<>();
        List<TeamResultEntity> teamResultEntitiesB = new ArrayList<>();
        List<TeamResultEntity> teamResultEntitiesC = new ArrayList<>();
        List<TeamResultEntity> teamResultEntitiesD = new ArrayList<>();
        List<TeamResultEntity> teamResultEntitiesE = new ArrayList<>();
        List<TeamResultEntity> teamResultEntitiesF = new ArrayList<>();
        List<TeamResultEntity> teamResultEntitiesDefault = new ArrayList<>();

        // Распределяем по спискам групп команды из этой группы
        for (TeamResultEntity teamResultEntity : teamResultEntities) {
            switch (teamResultEntity.getGroup()) {
                case "A":
                    teamResultEntitiesA.add(teamResultEntity);
                    break;
                case "B":
                    teamResultEntitiesB.add(teamResultEntity);
                    break;
                case "C":
                    teamResultEntitiesC.add(teamResultEntity);
                    break;
                case "D":
                    teamResultEntitiesD.add(teamResultEntity);
                    break;
                case "E":
                    teamResultEntitiesE.add(teamResultEntity);
                    break;
                case "F":
                    teamResultEntitiesF.add(teamResultEntity);
                    break;
                default:
                    teamResultEntitiesDefault.add(teamResultEntity);
            }
        }

        // для каждой группы формируем список из TournamentItem м передаём его в общий tournamentItems
        tournamentItems.addAll(getGroupItems(teamResultEntitiesA, getString(R.string.groupA)));
        tournamentItems.addAll(getGroupItems(teamResultEntitiesB, getString(R.string.groupB)));
        tournamentItems.addAll(getGroupItems(teamResultEntitiesC, getString(R.string.groupC)));
        tournamentItems.addAll(getGroupItems(teamResultEntitiesD, getString(R.string.groupD)));
        tournamentItems.addAll(getGroupItems(teamResultEntitiesE, getString(R.string.groupE)));
        tournamentItems.addAll(getGroupItems(teamResultEntitiesF, getString(R.string.groupF)));

        // показывает группу с неправильно заданным именеем группы только для администратора
        UserEntity userEntity = database.userDao().getLoggedInUser();
        if (userEntity != null && userEntity.isAdmin()) {
            tournamentItems.addAll(getGroupItems(teamResultEntitiesDefault, getString(R.string.groupDefault)));
        }

        // отправляет общий список из TournamentItem в адаптер
        tournamentItemsAdapter.setTournamentItems(tournamentItems);

        // в случае когда список tournamentItems пуст показывает текстовку "Список пуст"
        if (tournamentItems.isEmpty()) {
            tvNoResults.setVisibility(View.VISIBLE);
        } else {
            tvNoResults.setVisibility(View.GONE);
        }
    }

    // Для каждой отдельной группы создаёт список из элемента названия этой группы, элемента с названиями колонок и ниже сами результаты команд
    private List<TournamentItem> getGroupItems(List<TeamResultEntity> teamResultEntities, String groupName) {
        List<TournamentItem> tournamentItems = new ArrayList<>();

        if (!teamResultEntities.isEmpty()) {
            // элемент названия этой группы
            tournamentItems.add(new GroupName(groupName));
            // элемент с названиями колонок
            tournamentItems.add(new ColumnNames());

            // в цикле преобразует результаты команд из базы данных в результаты команд для адаптера
            for (TeamResultEntity teamResultEntity : teamResultEntities) {
                TeamResult teamResult = new TeamResult(
                        teamResultEntity.getId(),
                        teamResultEntity.getName(),
                        FlagFactory.getFlagRes(teamResultEntity.getName()),
                        teamResultEntity.getGames(),
                        teamResultEntity.getWins(),
                        teamResultEntity.getDraws(),
                        teamResultEntity.getLoses(),
                        teamResultEntity.getGoals() + "-" + teamResultEntity.getGoalsMisses(),
                        teamResultEntity.getScore()
                );
                tournamentItems.add(teamResult);
            }
        }
        return tournamentItems;
    }

    // открывает экран редактора результата команды в режиме редактирования(изменения существующего результата)
    // для этого необходимо в интент передать id этого результата, чтобы было понятно какой результат редактируется
    private void editTeamResult(int id) {
        Intent intent = new Intent(TournamentActivity.this, TeamResultEditActivity.class);
        intent.putExtra("ID_TAG", id);
        startActivity(intent);
    }
}