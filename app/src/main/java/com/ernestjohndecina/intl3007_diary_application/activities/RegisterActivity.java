package com.ernestjohndecina.intl3007_diary_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity {
    EditText firstNameEditText;
    EditText emailEditText;
    EditText usernameEditText;
    EditText pinEditNumber;
    ImageButton registerButton;



    ExecutorService executorService;
    SystemFeatures systemFeatures;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createDependencies();
        setEditText();
        setRegisterButton();
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

    void setEditText(){
        //Toast.makeText(this, "add string", Toast.LENGTH_LONG).show();

        this.firstNameEditText = findViewById(R.id.firstNameEditText);
        this.emailEditText = findViewById(R.id.emailEditText);
        this.usernameEditText = findViewById(R.id.usernameEditText);
        this.pinEditNumber = findViewById(R.id.pinEditNumber);

    }

    void setRegisterButton(){
       this.registerButton = findViewById(R.id.registerButton);
       this.registerButton.setOnClickListener(v -> onClickRegisterButton());
    }

    void onClickRegisterButton(){
       String firstName = firstNameEditText.getText().toString();
       String email = emailEditText.getText().toString();
       String userName = usernameEditText.getText().toString();
       String pinCode = pinEditNumber.getText().toString();

       systemFeatures.userFeatures.createUserAccount(
               firstName,
               email,
               userName,
               pinCode
       );

       Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show();
       finish();
    }


}