package com.ernestjohndecina.intl3007_diary_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class LoginActivity extends AppCompatActivity {
    ExecutorService executorService;
    SystemFeatures systemFeatures;


    // EditTexts
    EditText usernameLoginEditText;
    EditText pinLoginEditText;

    // Button
    ImageButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createDependencies();
        setEditTexts();
        setButton();
    }


    private void createDependencies() {
        createThreadExecutor();
        createSystemFeatures();
    }

    private void createThreadExecutor() {
        executorService = new ThreadPoolExecutor(
                4,
                10,
                5L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>()
        );
    }

    private void createSystemFeatures() {
        this.systemFeatures = new SystemFeatures(
                this,
                this.executorService
        );
    }

    private void setEditTexts() {
        this.usernameLoginEditText = findViewById(R.id.usernameLoginEditText);
        this.pinLoginEditText = findViewById(R.id.pinLoginEditText);
    }

    private void setButton() {
        this.loginButton = findViewById(R.id.loginButton);
        this.loginButton.setOnClickListener(v -> onClickLoginButton());
    }

    private void onClickLoginButton() {
        String username = usernameLoginEditText.getText().toString();
        String pin = pinLoginEditText.getText().toString();

        if(!systemFeatures.userFeatures.validateUser(username, pin)) {
            Toast.makeText(this,  "Incorrect Username or Password", Toast.LENGTH_LONG).show();
            return;
        }

        finish();
    }
}