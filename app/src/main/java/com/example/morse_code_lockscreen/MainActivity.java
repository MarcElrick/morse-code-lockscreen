package com.example.morse_code_lockscreen;


import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements  View.OnTouchListener, GestureDetector.OnGestureListener{
    private boolean setting_password;

    private ArrayList<Integer> password, attempt;


    private GestureDetector gestureDetector;
    private Button switchModesButton;
    private EditText hint;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setting_password = false;
        this.hint = findViewById(R.id.editTextTextPassword);
        this.switchModesButton = findViewById(R.id.button);
        Button clearButton = findViewById(R.id.button2);
        this.switchModesButton.setOnClickListener(v -> {
            setting_password = !setting_password;

            if (setting_password) {

                switchModesButton.setText("Set Password");
            } else {
                switchModesButton.setText("Authenticate");
            }
        });

        clearButton.setOnClickListener(v -> {
            attempt.clear();
            hint.setText("");
        });

        View gestureCatcher = findViewById(R.id.gestureCatcher);
        gestureCatcher.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this,this);

        attempt = new ArrayList<>();
        password = new ArrayList<>();

        password.addAll(Arrays.asList(0,0,0,0,1));
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        Log.d("VIBRATION", "vibrate: ");
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        StringBuilder temp = new StringBuilder();

        for(int i=0; i < this.attempt.size(); i++){
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
        this.attempt.add(0);
        Log.d("ResultShort", attempt.toString());
        this.validate();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        this.attempt.add(1);
        Log.d("ResultLong", attempt.toString());
        this.validate();
        this.vibrate();

    }

    private void validate() {
        if(attempt.size() != password.size()){
            return;
        }

        if(this.attempt.equals(this.password)) {
            Toast.makeText(getApplicationContext(), "Match", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}