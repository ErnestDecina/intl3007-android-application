package com.ernestjohndecina.intl3007_diary_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ernestjohndecina.intl3007_diary_application.R;

public class RegisterActivity extends AppCompatActivity {
    EditText firstNameEditText;
    EditText emailEditText;
    EditText usernameEditText;
    EditText pinEditNumber;
    Button registerButton;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setEditText();
        setRegisterButton();
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
       //Toast.makeText(this, "Register done", Toast.LENGTH_LONG).show();
       String firstName = firstNameEditText.getText().toString();
       String email = emailEditText.getText().toString();
       String userName = usernameEditText.getText().toString();
       String pinCode = pinEditNumber.getText().toString();

       Toast.makeText(this, firstName, Toast.LENGTH_LONG).show();
       //systemFeature.userFeatures.createAccount(firstName, email, userName, pinCode)

    }
}