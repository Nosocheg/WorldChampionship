package com.mabel.worldchampionship.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mabel.worldchampionship.R;
import com.mabel.worldchampionship.database.AppDatabase;
import com.mabel.worldchampionship.database.RoomWrapper;
import com.mabel.worldchampionship.database.UserEntity;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnAuth;
    private TextView tvRegistration;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = RoomWrapper.getDatabase(this);

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnAuth = findViewById(R.id.btnAuth);
        tvRegistration = findViewById(R.id.tvRegistration);

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = etLogin.getText().toString();
                if (login.isEmpty()) {
                    etLogin.requestFocus();
                    etLogin.setError(getString(R.string.empty_field_error));
                    return;
                }
                String password = etPassword.getText().toString();
                if (password.isEmpty()) {
                    etPassword.requestFocus();
                    etPassword.setError(getString(R.string.empty_field_error));
                    return;
                }

                auth(login, password);
            }
        });

        tvRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }

    private void auth(String login, String password) {
        UserEntity userEntity = database.userDao().getByCredential(login, password);
        if (userEntity != null) {

            userEntity.setLoggedIn(true);
            database.userDao().update(userEntity);

            startActivity(
                    new Intent(LoginActivity.this, MenuActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            );
        } else {
            Toast.makeText(this, "Проверьте правильность данных", Toast.LENGTH_LONG).show();
        }
    }
}
