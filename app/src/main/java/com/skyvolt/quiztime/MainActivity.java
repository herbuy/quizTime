package com.skyvolt.quiztime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(makeContentView());
    }

    private View makeContentView() {
        TextView textView = new TextView(this);
        textView.setText("I am a simple app");
        return textView;
    }

}