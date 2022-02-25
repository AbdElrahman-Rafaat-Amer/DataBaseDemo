package com.abdo.databasedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    // View Ref
    private Button closeButton;
    private Button writeSharedButton, readSharedButton;
    private Button writeInternalButton, readInternalButton;
    private Button writeSQLButton, readSQLButton;
    private TextView textViewMessage, textViewNumber;

    //Logic Ref
    private final String SHARED_PREFERENCE_NAME = "SHARED_PREFERENCE_FILE_NAME";
    private SharedPreferences sharedPreferences;
    private final String INTERNAL_STORAGE_NAME = "INTERNAL_STORAGE_FILE_NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        closeButton = findViewById(R.id.closeButton);
        writeSharedButton = findViewById(R.id.save_shared_pref);
        readSharedButton = findViewById(R.id.load_shared_pref);
        writeInternalButton = findViewById(R.id.save_internal);
        readInternalButton = findViewById(R.id.load_internal);
        writeSQLButton = findViewById(R.id.save_sql);
        readSQLButton = findViewById(R.id.load_sql);

        textViewMessage = findViewById(R.id.editTextTextMessage);
        textViewNumber = findViewById(R.id.editTextTextNumber);

        textViewMessage.setText(getIntent().getStringExtra("MESSAGE"));
        textViewNumber.setText(getIntent().getStringExtra("NUMBER"));
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        writeSharedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textViewMessage.getText().toString().trim();
                String number = textViewNumber.getText().toString().trim();

                if (!message.isEmpty() && !number.isEmpty()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("MESSAGE", message);
                    editor.putString("MOBILE_NUMBER", number);
                    editor.commit();
                    clearTextField();
                }else{
                    showErrorMessage();
                }
            }
        });

        readSharedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = sharedPreferences.getString("MESSAGE", textViewMessage.getText().toString().trim());
                String number = sharedPreferences.getString("MOBILE_NUMBER", textViewNumber.getText().toString().trim());
                textViewMessage.setText(message);
                textViewNumber.setText(number);
            }
        });

        writeInternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textViewMessage.getText().toString().trim();
                String number = textViewNumber.getText().toString().trim();

                if (!message.isEmpty() && !number.isEmpty()) {
                    try {
                        FileOutputStream fileOutputStream = openFileOutput(INTERNAL_STORAGE_NAME, Context.MODE_PRIVATE);
                        DataOutputStream outputStream = new DataOutputStream(fileOutputStream);
                        outputStream.writeUTF(textViewMessage.getText().toString().trim());
                        outputStream.writeUTF(textViewNumber.getText().toString().trim());
                        outputStream.flush();
                        fileOutputStream.close();
                        fileOutputStream.close();
                        outputStream.close();
                        clearTextField();
                    } catch (IOException e) {
                        showErrorMessage();
                        Toast.makeText(SecondActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showErrorMessage();
                }
            }
        });

        readInternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fileInputStream = openFileInput(INTERNAL_STORAGE_NAME);
                    DataInputStream inputStream = new DataInputStream(fileInputStream);
                    textViewMessage.setText(inputStream.readUTF());
                    textViewNumber.setText(inputStream.readUTF());
                    inputStream.close();
                    fileInputStream.close();
                } catch (IOException e) {
                    Toast.makeText(SecondActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    showErrorMessage();
                }
            }
        });

        writeSQLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textViewMessage.getText().toString().trim();
                String number = textViewNumber.getText().toString().trim();

                if (!message.isEmpty() && !number.isEmpty()) {
                    DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(getBaseContext());
                    DTO dto = new DTO();
                    dto.setMessage(message);
                    dto.setNumber(number);
                    long id = dataBaseAdapter.insert(dto);

                    if(id > 0){
                        textViewMessage.setText("");
                    }else{
                        textViewMessage.setText(R.string.error_inSave);
                        textViewNumber.setText(R.string.error_inSave);
                    }

                } else {
                    showErrorMessage();
                }

            }
        });

        readSQLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = textViewNumber.getText().toString().trim();
                if (!number.isEmpty()) {
                    DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(getBaseContext());
                    DTO dto = dataBaseAdapter.read(textViewNumber.getText().toString().trim());
                    System.err.println("Message in readSQLButton = " + dto.getMessage());
                    System.err.println("Number in readSQLButton = " + dto.getNumber());
                    String messageResponse = dto.getMessage();

                    if(messageResponse != null && !messageResponse.isEmpty() ){
                        textViewMessage.setText(messageResponse);
                        textViewNumber.setText(dto.getNumber());
                    }else{
                        Toast.makeText(SecondActivity.this, R.string.no_such_data, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showErrorMessage();
                }

            }
        });

    }

    private void clearTextField() {
        textViewMessage.setText("");
        textViewNumber.setText("");
    }

    private void showErrorMessage() {
        textViewMessage.setText(R.string.empty_data);
        textViewNumber.setText(R.string.empty_data);
    }
}