package com.mabel.worldchampionship.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mabel.worldchampionship.R;
import com.mabel.worldchampionship.database.AppDatabase;
import com.mabel.worldchampionship.database.MatchEntity;
import com.mabel.worldchampionship.database.RoomWrapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ScheduleEditActivity extends AppCompatActivity {

    private final String DATE_TAG = "DATE_TAG";

    private EditText etTeamFirst, etTeamSecond, etGroup, etScore;
    private TextView tvDateTime;
    private Button btnSave;

    private AppDatabase database;

    // Объект для хранения даты
    private final Calendar calendar = Calendar.getInstance();
    // Объект для форматирования даты в строку
    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);
        database = RoomWrapper.getDatabase(this);

        etTeamFirst = findViewById(R.id.etTeamFirst);
        etTeamSecond = findViewById(R.id.etTeamSecond);
        etGroup = findViewById(R.id.etGroup);
        etScore = findViewById(R.id.etScore);
        tvDateTime = findViewById(R.id.tvDateTime);
        btnSave = findViewById(R.id.btnSave);

        // предзаполняем поля данными из редактируемого матча
        if (getId() >= 0) {
            MatchEntity matchEntity = database.matchDao().getById(getId());
            etTeamFirst.setText(matchEntity.getTeamFirst());
            etTeamSecond.setText(matchEntity.getTeamSecond());
            etGroup.setText(matchEntity.getGroup());
            etScore.setText(matchEntity.getScore());

            calendar.setTimeInMillis(matchEntity.getTimestamp());
            tvDateTime.setText(format.format(calendar.getTime()));
        }

        // проверяем что savedInstanceState не равен null,
        // это условие выполнится только после поворота экрана, при первом запуске onCreate savedInstanceState равен null
        if (savedInstanceState != null) {
            long millis = savedInstanceState.getLong(DATE_TAG, 0);
            // перезаписываем значение внутри calendar тем что было сохранено ранее
            if (millis > 0) {
                calendar.setTimeInMillis(millis);
                tvDateTime.setText(format.format(calendar.getTime()));
            }
        }

        tvDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        tvDateTime.setText(format.format(calendar.getTime()));
                    }
                };
                new DatePickerDialog(ScheduleEditActivity.this, dateListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();

                TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        tvDateTime.setText(format.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(ScheduleEditActivity.this, timeListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true)
                        .show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMatch();
            }
        });
    }

    private void saveMatch() {
        MatchEntity matchEntity = new MatchEntity(
                etTeamFirst.getText().toString(),
                etTeamSecond.getText().toString(),
                etGroup.getText().toString(),
                etScore.getText().toString(),
                calendar.getTimeInMillis()
        );

        // проверяем находится ли экран в режиме редактирования
        if (getId() >= 0) {
            // записываем в собранный по полям ввода матч id того матча который должен быть изменен(с предыдущего экрана)
            matchEntity.setId(getId());
            database.matchDao().update(matchEntity);
        } else {
            database.matchDao().insert(matchEntity);
        }

        // закрытие экрана редактирования
        finish();
    }

    // метод возвращает тот id с которым мы открыли этот экран, если >= 0 то находимся в режиме редактирования, если -1 значит режим создания
    private int getId() {
        return getIntent().getIntExtra("ID_TAG", -1);
    }

    @Override
    // метод вызывается перед переворотом экрана и любой другой сменой конфигурации
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // в специальный объект outState записываем занчение calendar в миллисекундах с ключем DATE_TAG
        outState.putLong(DATE_TAG, calendar.getTimeInMillis());
    }
}