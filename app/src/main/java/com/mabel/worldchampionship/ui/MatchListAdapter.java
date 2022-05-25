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
import com.mabel.worldchampionship.ui.data.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.MatchViewHolder>{

    private final LayoutInflater inflater;
    private final ArrayList<Match> matches = new ArrayList<>();

    // обработка нажатия на матч
    private ItemMatchClickListener onMatchClickListener;
    // обработка долгого нажатия на матч
    private ItemMatchClickListener onMatchLongClickListener;

    public MatchListAdapter(@NonNull Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    // возвращает объект ViewHolder, который будет хранить данные по одному объекту Match.
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    // выполняет привязку объекта ViewHolder к объекту Match по определенной позиции.
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        holder.bind(matches.get(position));
    }

    @Override
    // возвращает количество объектов в списке
    public int getItemCount() {
        return matches.size();
    }


    // метод заполнения списка данными
    public void setMatches(List<Match> newMatches) {
        // очистка текущего списка
        matches.clear();
        // заполнение новыми матчами
        matches.addAll(newMatches);
        // удаление пустых значений в конце списка, которые ArrayList создаёт автоматически, это нужно для оптимизации памяти
        matches.trimToSize();
        // команда адаптеру перерисовать себя, автоматически он этого не делает
        notifyDataSetChanged();
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public ItemMatchClickListener getOnMatchClickListener() {
        return onMatchClickListener;
    }

    public void setOnMatchClickListener(ItemMatchClickListener onMatchClickListener) {
        this.onMatchClickListener = onMatchClickListener;
    }

    public ItemMatchClickListener getOnMatchLongClickListener() {
        return onMatchLongClickListener;
    }

    public void setOnMatchLongClickListener(ItemMatchClickListener onMatchLongClickListener) {
        this.onMatchLongClickListener = onMatchLongClickListener;
    }

    class MatchViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivTeamFirstFlag, ivTeamSecondFlag;
        private final TextView tvTeamFirstName, tvGroup, tvTime, tvScore, tvTeamSecondName;

        MatchViewHolder(View itemView) {
            super(itemView);
            ivTeamFirstFlag = itemView.findViewById(R.id.ivTeamFirstFlag);
            ivTeamSecondFlag = itemView.findViewById(R.id.ivTeamSecondFlag);
            tvTeamFirstName = itemView.findViewById(R.id.tvTeamFirstName);
            tvTeamSecondName = itemView.findViewById(R.id.tvTeamSecondName);
            tvGroup = itemView.findViewById(R.id.tvGroup);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvScore = itemView.findViewById(R.id.tvScore);
        }

        // заполняет виджеты значениями из конкретного матча
        public void bind(Match match) {
            tvTeamFirstName.setText(match.getTeamFirst());
            tvTeamSecondName.setText(match.getTeamSecond());
            tvGroup.setText(match.getGroup());
            tvTime.setText(match.getDatetime());

            tvScore.setText(match.getScore());
            if (match.getScore().isEmpty()) {
                tvScore.setVisibility(View.GONE);
            } else {
                tvScore.setVisibility(View.VISIBLE);
            }

            ivTeamFirstFlag.setImageResource(match.getTeamFirstFlag());
            ivTeamSecondFlag.setImageResource(match.getTeamSecondFlag());

            // itemView - это виджет-контейнер матча, в котором лежат все остальные описанные выше виджеты
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMatchClickListener != null) {
                        // открывает экран редактирования матча для администратора(передаётся снаружи)
                        onMatchClickListener.onItemClick(match);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onMatchLongClickListener != null) {
                        // удаляет матч(передаётся снаружи)
                        onMatchLongClickListener.onItemClick(match);
                    }
                    return false;
                }
            });
        }
    }

    // интерфейс описывает действие, которое необходимо выполнить при нажатии на виджет
    public interface ItemMatchClickListener {
        void onItemClick(Match match);
    }
}
