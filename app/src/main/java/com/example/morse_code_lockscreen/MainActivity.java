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

    // Identifies current gesture detector
    private boolean isLeft = true;

    // Stores the password entered by the user.
    private ArrayList<TouchState> password, attempt;

    // Gesture detector used to accept input on left/right sections
    private GestureDetector gestureDetectorLeft, gestureDetectorRight;

    // Submit button
    private ImageButton authenticateButton;

    // Displays character placeholders
    private EditText hint;

    // Displays date
    private TextView date;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Assign variables to UI elements
        this.hint = findViewById(R.id.editTextTextPassword);
        this.date = findViewById(R.id.date);
        this.authenticateButton = findViewById(R.id.button);
        ImageButton clearButton = findViewById(R.id.button2);

        // onSubmit listener
        this.authenticateButton.setOnClickListener(v -> {
            if(!this.password.equals(this.attempt)) {

                // Show toast on incorrect password
                Log.d("INCORRECT PASSWORD", this.password.toString());
                Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                attempt.clear();
                hint.setText("");
            } else {
                // permit user into system.
                Intent intent = new Intent(this, SuccessActivity.class);
                startActivity(intent);
            }
        });

        // Initialise date and time
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM");//formating according to my need
        String d = formatter.format(today);
        date.setText(d + "  " + new String(Character.toChars(0x1F324)));

        //Clear button listner
        clearButton.setOnClickListener(v -> {
            attempt.clear();
            hint.setText("");
        });

        // Initialise left and right gesture detectors
        View gestureCatcherLeft = findViewById(R.id.gestureCatcherLeft);
        View gestureCatcherRight = findViewById(R.id.gestureCatcherRight);
        gestureCatcherLeft.setOnTouchListener(this);
        gestureCatcherRight.setOnTouchListener(this);
        gestureDetectorLeft = new GestureDetector(this, this);
        gestureDetectorRight = new GestureDetector(this, this);

        attempt = new ArrayList<>();
        password = new ArrayList<>();

        password.addAll(Arrays.asList(TouchState.SHORT_LEFT, TouchState.SHORT_RIGHT, TouchState.LONG_LEFT, TouchState.LONG_RIGHT));
    }

    // Method to make device vibrate.
    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        Log.d("VIBRATION", "vibrate: ");
    }


    // Method called whenever user touches screen
    // Appropriate method then called based on touch typ (long or short press)
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

        // Random character appended for hint field.
        for (int i = 0; i < this.attempt.size(); i++) {
            temp.append("T");
        }
        this.hint.setText(temp.toString());
        return true;
    }

    // Useless interface method
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    // Useless interface method
    @Override
    public void onShowPress(MotionEvent e) {

    }

    // Called on short tap. Adds appropriate value to password array
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

    // Useless interface method
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    // Called on long press
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

    // Useless interface method
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}