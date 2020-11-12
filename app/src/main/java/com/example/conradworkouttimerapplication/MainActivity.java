package com.example.conradworkouttimerapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

// Photo citation: All images used were found on Unsplash.com.

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add listeners to all of the buttons.
        ((Button)findViewById(R.id.sprint_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.walk_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.meditation_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.yoga_button)).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        // Connect to the second activity.
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

        // Depending on which button is pressed, a unique number of seconds is passed to the new
        // activity, along with a unique title. Then the next activity is started.
        // I am also logging which button is pressed, just to make sure things are working correctly.
        switch (view.getId()) {
            case R.id.sprint_button:
                intent.putExtra("seconds", 30);
                intent.putExtra("title", "SPRINT: 30 seconds");
                Log.i("INFO", "Sprint button was pressed.");
                startActivity(intent);
                break;
            case R.id.walk_button:
                intent.putExtra("seconds", 1800);
                intent.putExtra("title", "WALK: 30 minutes");
                Log.i("INFO", "Walk button was pressed.");
                startActivity(intent);
                break;
            case R.id.meditation_button:
                intent.putExtra("seconds", 600);
                intent.putExtra("title", "MEDITATION: 10 minutes");
                Log.i("INFO", "Meditation button was pressed.");
                startActivity(intent);
                break;
            case R.id.yoga_button:
                intent.putExtra("seconds", 3600);
                intent.putExtra("title", "YOGA: 1 hour");
                Log.i("INFO", "Yoga button was pressed.");
                startActivity(intent);
                break;
        }
    }
}