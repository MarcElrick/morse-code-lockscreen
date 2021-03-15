package com.example.morse_code_lockscreen;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private boolean isLeft = true;

    private ArrayList<TouchState> password, attempt;


    private GestureDetector gestureDetectorLeft, gestureDetectorRight;
    private ImageButton authenticateButton;
    private EditText hint;
    private TextView date;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        this.hint = findViewById(R.id.editTextTextPassword);
        this.date = findViewById(R.id.date);
        this.authenticateButton = findViewById(R.id.button);
        ImageButton clearButton = findViewById(R.id.button2);
        this.authenticateButton.setOnClickListener(v -> {
            if(!this.password.equals(this.attempt)) {
                Log.d("INCORRECT PASSWORD", this.password.toString());
                Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                attempt.clear();
                hint.setText("");
            } else {
                Intent intent = new Intent(this, SuccessActivity.class);
                startActivity(intent);
            }
        });

        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM");//formating according to my need
        String d = formatter.format(today);
        date.setText(d + "  " + new String(Character.toChars(0x1F324)));

        clearButton.setOnClickListener(v -> {
            attempt.clear();
            hint.setText("");
        });

        View gestureCatcherLeft = findViewById(R.id.gestureCatcherLeft);
        View gestureCatcherRight = findViewById(R.id.gestureCatcherRight);


        gestureCatcherLeft.setOnTouchListener(this);
        gestureCatcherRight.setOnTouchListener(this);
        gestureDetectorLeft = new GestureDetector(this, this);
        gestureDetectorRight = new GestureDetector(this, this);


        attempt = new ArrayList<>();
        password = new ArrayList<>();

        password.addAll(Arrays.asList(TouchState.SHORT_LEFT, TouchState.SHORT_LEFT, TouchState.SHORT_LEFT, TouchState.LONG_RIGHT));
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        Log.d("VIBRATION", "vibrate: ");
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.gestureCatcherLeft) {
            isLeft = true;
            gestureDetectorLeft.onTouchEvent(event);
        }

        if (v.getId() == R.id.gestureCatcherRight) {
            isLeft = false;
            gestureDetectorRight.onTouchEvent(event);
        }
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < this.attempt.size(); i++) {
            temp.append("T");
        }
        this.hint.setText(temp.toString());
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (isLeft) {
            this.attempt.add(TouchState.SHORT_LEFT);
        } else {
            this.attempt.add(TouchState.SHORT_RIGHT);
        }
        Log.d("ResultShort", attempt.toString());

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (isLeft) {
            this.attempt.add(TouchState.LONG_LEFT);
        } else {
            this.attempt.add(TouchState.LONG_RIGHT);
        }
        Log.d("ResultLong", attempt.toString());
        this.vibrate();

    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}