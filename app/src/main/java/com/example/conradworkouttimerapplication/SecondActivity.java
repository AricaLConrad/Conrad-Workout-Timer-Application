package com.example.conradworkouttimerapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Allows this second activity to get a hold on the data and save it. I am also logging the
        // information to make sure I received it properly.
        int seconds = getIntent().getExtras().getInt("seconds");
        Log.i("INFO", "Number of seconds received: " + seconds);
        String title = getIntent().getExtras().getString("title");
        Log.i("INFO", "Title received: " + title);

        // This dynamically loads the Timer Fragment.
        getSupportFragmentManager()
                .beginTransaction()
                // Pass in the id of the fragment container and the instance of the fragment.
                // The Timer Fragment requires the seconds and title variables.
                .add(R.id.fragment_container, TimerFragment.newInstance(seconds, title))
                .commit();
    }
}