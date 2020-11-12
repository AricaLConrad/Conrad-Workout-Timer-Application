package com.example.conradworkouttimerapplication;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

// I removed most of the factory method code.

public class TimerFragment extends Fragment {

    // All of the variables I need.
    private int seconds = 0;
    // The secondsReset variable is needed for when the timer resets.
    private int secondsReset;
    private String title;
    private TextView tv;
    private boolean paused = true;
    private FloatingActionButton fab;
    private Drawable PLAY;
    private Drawable PAUSE;


    // Left this method how it was.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment.
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        // Get a handle on the Floating Action Button.
        fab = view.findViewById(R.id.play_pause_fab);

        // Set a listener on the Floating Action Button.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the button is pressed and the seconds are less than zero (the timer has
                // finished), reset the number of seconds to what it was before, change the text color
                // from green to white, and then run the timer again.
                if (seconds <= 0)
                {
                    seconds = secondsReset;
                    tv.setTextColor(Color.parseColor("#FFFFFF"));
                    runTimer(view);
                }

                // Log to make sure the button has been pressed.
                Log.i("INFO", "Floating Action Button has been pressed!");
                // Switch the boolean variable.
                paused = !paused;
                // Call the method that will change the Floating Action Button's icon.
                setIcon();
            }
        });

        // Return the view.
        return view;
    }

    // Added this method.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get a handle on the title TextView.
        TextView ft = view.findViewById(R.id.fragment_text);
        // Store the data received, and set the secondsReset variable equal to the number of
        // seconds that were passed.
        seconds = getArguments().getInt("seconds");
        secondsReset = seconds;
        title = getArguments().getString("title");
        // Log the data to make sure everything was received correctly.
        Log.i("INFO", "Seconds received are: " + seconds);
        Log.i("INFO", "Title received: " + title);
        // Set the TextView to display the title that was received.
        ft.setText(title);

        // Get a handle on the timer countdown text and the Floating Action Button.
        tv = getView().findViewById(R.id.time_text);
        fab = getView().findViewById(R.id.play_pause_fab);

        // Create the drawables that will store the play and pause icons for the Floating Action Button.
        PLAY = getResources().getDrawable(android.R.drawable.ic_media_pause);
        PAUSE = getResources().getDrawable(android.R.drawable.ic_media_play);

        // If the fragment starts again and it is not the first time, store the data that was passed
        // into the savedInstanceState bundle. The icon for the Floating Action Button is also set up again.
        if (savedInstanceState != null) {
            paused = savedInstanceState.getBoolean("paused");
            seconds = savedInstanceState.getInt("seconds");
            secondsReset = savedInstanceState.getInt("reset");
            setIcon();
        }

        // Run the timer, passing in the view.
        runTimer(view);
    }


    // This determines what icon should be displayed. If the timer is paused, the play icon should be
    // shown, and if the timer is not paused, the pause icon should be shown.
    private void setIcon() {
        Drawable icon = paused ? PAUSE : PLAY;
        fab.setImageDrawable(icon);
    }


    // The method did not like the word "protected", so I had to change the method to being public.
    // This stores the number of seconds currently, the status of the paused variable, and the number
    // of seconds originally. This allows the timer to keep running when the phone changes orientation.
    // This bundle will be utilized when the fragment starts up again.
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("paused", paused);
        outState.putInt("reset", secondsReset);
    }


    // I needed to add "Looper." to "getMainLooper()" in order for this to work. In an activity,
    // you might be able to just use "getMainLooper()" like what the professor had.
    // This method is where all the timer logic is. The handler will loop every 1000 milliseconds,
    // or every 1 second. Once the timer reaches zero, the timer will stop counting down.
    public void runTimer(View view) {
        // Create a new handler.
        final Handler handler = new Handler(Looper.getMainLooper());
        // Pass in a new runnable to the handler.
        handler.post(new Runnable() {
            @Override
            public void run() {

                // Calculate how many seconds, minutes, and hours there are from a certain number
                // of seconds. For instance, 1800 seconds equals 30 minutes.
                // Everything is in integers, as we do not deal with decimal time.
                int sec = seconds % 60;
                int min = (seconds % 3600) / 60;
                int hour = seconds / 3600;

                // Format the timer text to display two digits per hour, minute, and second value.
                // For instance, 00 : 30 : 00.
                tv.setText(String.format("%02d : %02d : %02d", hour, min, sec));

                // If the timer has reached zero, the timer will stop, the Floating Action Button's
                // icon will switch to the other icon (from pause to play), and the timer's text will
                // be shown in a light green color. The return statement exits out of this method so
                // the timer will actually stop and not continue on forever.
                if (seconds <= 0) {

                    // Change the icon to the opposite one.
                    paused = !paused;
                    setIcon();

                    // Change the text color to a light green.
                    tv.setTextColor(Color.parseColor("#8EF38F"));

                    // Exit out of this method so this timer does not run on forever.
                    return;
                }

                // Otherwise, as long as the Floating Action Button is not pressed (meaning the timer
                // should pause), decrease the number of seconds by one, as this timer counts down
                // by one second.
                if (!paused) {
                    seconds--;
                }

                // Repeat the method every 1000 milliseconds, or every 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }


    // This was one of the factory methods that I recreated.
    // This creates an instance of the timer fragment, passes along some data, and then
    // returns the new instance.
    public static TimerFragment newInstance(int seconds, String title) {
        Log.i("INFO", "Made it to the Timer Fragment!");
        TimerFragment tf = new TimerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("seconds", seconds);
        bundle.putString("title", title);
        tf.setArguments(bundle);
        return tf;
    }
}