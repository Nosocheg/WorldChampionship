package com.mabel.worldchampionship.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mabel.worldchampionship.R;
import com.mabel.worldchampionship.ui.data.ColumnNames;
import com.mabel.worldchampionship.ui.data.GroupName;
import com.mabel.worldchampionship.ui.data.Match;
import com.mabel.worldchampionship.ui.data.TeamResult;
import com.mabel.worldchampionship.ui.data.TournamentItem;

import java.util.ArrayList;
import java.util.List;

public class TournamentItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TEAM_RESULT = 0;
    public static final int GROUP_NAME = 1;
    public static final int COLUMN_NAMES = 2;

    private final LayoutInflater inflater;
    private final ArrayList<TournamentItem> tournamentItems = new ArrayList<>();

    // обработка нажатия на результат команды
    private ItemTeamResultClickListener onTeamResultClickListener;

    // обработка долгого нажатия на результат команды
    private ItemTeamResultClickListener onTeamResultLongClickListener;

    public TournamentItemsAdapter(@NonNull Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    // определяет тип элемента(результат команды, группа или названия столбцов)
    @Override
    public int getItemViewType(int position) {
        TournamentItem tournamentItem = tournamentItems.get(position);
        if (tournamentItem instanceof TeamResult) {
            return TEAM_RESULT;
        } else if (tournamentItem instanceof GroupName){
            return GROUP_NAME;
        } else {
            return COLUMN_NAMES;
        }
    }

    @NonNull
    @Override
    // возвращает один из типов ViewHolder-а, который хранит данные по результату команды, группе или названию столбцов
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TEAM_RESULT: {
                View view = inflater.inflate(R.layout.item_team_result, parent, false);
                return new TeamResultViewHolder(view);
            }
            case GROUP_NAME: {
                View view = inflater.inflate(R.layout.item_group, parent, false);
                return new GroupNameViewHolder(view);
            }
            default: {
                View view = inflater.inflate(R.layout.item_column_names, parent, false);
                return new ColumnNamesViewHolder(view);
            }
        }
    }

    @Override
    // выполняет привязку объекта ViewHolder к объекту TournamentItem по определенной позиции.
    // если holder является TeamResultViewHolder и tournamentItem является TeamResult, то мы имем дело с редзультом команды и
    // нужно вызвать метод TeamResultViewHolder.bind(), который заполненит holder данными из TeamResult
    // аналогично для GroupNameViewHolder
    // для ColumnNamesViewHolder ничего заполнять не нужно так как он всегда содержит одни и те же данные
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TournamentItem tournamentItem = tournamentItems.get(position);
        if (holder instanceof TeamResultViewHolder && tournamentItem instanceof TeamResult) {
            ((TeamResultViewHolder) holder).bind((TeamResult)tournamentItem);
        } else if (holder instanceof GroupNameViewHolder && tournamentItem instanceof GroupName) {
            ((GroupNameViewHolder) holder).bind((GroupName)tournamentItem);
        }
    }

    @Override
    // возвращает количество объектов в списке
    public int getItemCount() {
        return tournamentItems.size();
    }


    // метод заполнения списка данными
    public void setTournamentItems(List<TournamentItem> newTournamentItems) {
        // очистка текущего списка
        tournamentItems.clear();
        // заполнение новыми матчами
        tournamentItems.addAll(newTournamentItems);
        // удаление пустых значений в конце списка, которые ArrayList создаёт автоматически, это нужно для оптимизации памяти
        tournamentItems.trimToSize();
        // команда адаптеру перерисовать себя, автоматически он этого не делает
        notifyDataSetChanged();
    }

    public void setOnTeamResultClickListener(ItemTeamResultClickListener onTeamResultClickListener) {
        this.onTeamResultClickListener = onTeamResultClickListener;
    }

    public void setOnTeamResultLongClickListener(ItemTeamResultClickListener onTeamResultLongClickListener) {
        this.onTeamResultLongClickListener = onTeamResultLongClickListener;
    }

    // ViewHolder для результата команды, содержит логику заполнения полей результата команды
    class TeamResultViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivFlag;
        private final TextView tvName, tvGames, tvWins, tvDraws, tvLoses, tvGoals, tvScore;

        TeamResultViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvGames = itemView.findViewById(R.id.tvGames);
            tvWins = itemView.findViewById(R.id.tvWins);
            tvDraws = itemView.findViewById(R.id.tvDraws);
            tvLoses = itemView.findViewById(R.id.tvLoses);
            tvGoals = itemView.findViewById(R.id.tvGoals);
            tvScore = itemView.findViewById(R.id.tvScore);
            ivFlag = itemView.findViewById(R.id.ivFlag);
        }

        // заполняет виджеты значениями из конкретного матча
        public void bind(TeamResult result) {
            tvName.setText(result.getName());
            ivFlag.setImageResource(result.getTeamFlag());
            tvGames.setText(String.valueOf(result.getGames()));
            tvWins.setText(String.valueOf(result.getWins()));
            tvDraws.setText(String.valueOf(result.getDraws()));
            tvLoses.setText(String.valueOf(result.getLoses()));
            tvGoals.setText(String.valueOf(result.getGoals()));
            tvScore.setText(String.valueOf(result.getScore()));

            // itemView - это виджет-контейнер матча, в котором лежат все остальные описанные выше виджеты
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTeamResultClickListener != null) {
                        // открывает экран редактирования результата команды (передаётся снаружи)
                        onTeamResultClickListener.onItemClick(result);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onTeamResultLongClickListener != null) {
                        // удаляет результат команды (передаётся снаружи)
                        onTeamResultLongClickListener.onItemClick(result);
                    }
                    return false;
                }
            });
        }
    }

    // ViewHolder для названия группы, содержит логику заполнения полей названия группы
    class GroupNameViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvGroupName;

        GroupNameViewHolder(View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
        }

        // заполняет виджеты значениями из конкретного матча
        public void bind(GroupName groupName) {
            tvGroupName.setText(groupName.getName());
        }
    }

    // ViewHolder для названия полей
    class ColumnNamesViewHolder extends RecyclerView.ViewHolder {

        ColumnNamesViewHolder(View itemView) {
            super(itemView);
        }
    }

    // интерфейс описывает действие, которое необходимо выполнить при нажатии на результат команды
    public interface ItemTeamResultClickListener {
        void onItemClick(TeamResult teamResult);
    }
}
