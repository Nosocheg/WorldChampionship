package com.mabel.worldchampionship.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mabel.worldchampionship.R;
import com.mabel.worldchampionship.database.RoomWrapper;
import com.mabel.worldchampionship.database.UserEntity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etLogin, etPassword, etPasswordConfirm, etUserName;
    private Button btnAuth;
    private CheckBox chbAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etLogin = findViewById(R.id.etLogin);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        btnAuth = findViewById(R.id.btnAuth);
        chbAdmin = findViewById(R.id.chbAdmin);

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = etLogin.getText().toString();
                if (login.isEmpty()) {
                    showEditTextError(etLogin);
                    return;
                }
                String userName = etUserName.getText().toString();
                if (userName.isEmpty()) {
                    showEditTextError(etUserName);
                    return;
                }
                String password = etPassword.getText().toString();
                if (password.isEmpty()) {
                    showEditTextError(etPassword);
                    return;
                }
                String passwordConfirm = etPasswordConfirm.getText().toString();
                if (passwordConfirm.isEmpty()) {
                    showEditTextError(etPasswordConfirm);
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    etPasswordConfirm.requestFocus();
                    etPasswordConfirm.setError(getString(R.string.password_match_error));
                    return;
                }

                register(login, userName, password, chbAdmin.isChecked());
            }
        });
    }

    private void showEditTextError(EditText editText) {
        editText.requestFocus();
        editText.setError(getString(R.string.empty_field_error));
    }

    private void register(String login, String userName, String password, Boolean isAdmin) {

        UserEntity userEntity = new UserEntity(login, password, userName, isAdmin, true);
        RoomWrapper.getDatabase(this).userDao().insert(userEntity);

        startActivity(
                new Intent(RegistrationActivity.this, MenuActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }
}