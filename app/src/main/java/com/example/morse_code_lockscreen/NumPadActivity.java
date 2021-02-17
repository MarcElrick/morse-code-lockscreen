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



public class NumPadActivity extends AppCompatActivity {
    private boolean setting_password;
    private ArrayList<Integer> password, attempt;
    private GestureDetector gestureDetector;
    private Button switchModesButton;
    private EditText hint;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_pad);

        setting_password = false;

        this.hint = findViewById(R.id.editTextNumberSigned);
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
        gestureCatcher.setOnTouchListener((View.OnTouchListener) this);
        gestureDetector = new GestureDetector(this, (GestureDetector.OnGestureListener) this);

        attempt = new ArrayList<>();
        password = new ArrayList<>();

        password.addAll(Arrays.asList(0,0,0,0,1));
    }
}