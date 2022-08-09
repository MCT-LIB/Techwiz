package com.csupporter.techwiz.presentation.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.csupporter.techwiz.R;

public class OnBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}