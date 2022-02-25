package com.abdo.databasedemo;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button nextButton, closeButton;
    private EditText editTextTextMessage, editTextTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.nextButton);
        closeButton = findViewById(R.id.closeButton);
        editTextTextMessage = findViewById(R.id.editTextTextMessage);
        editTextTextNumber = findViewById(R.id.editTextTextNumber);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("MESSAGE", editTextTextMessage.getText().toString().trim());
                intent.putExtra("NUMBER", editTextTextNumber.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}