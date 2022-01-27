package com.example.bajaj_firatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String name = getIntent().getExtras().getString("AT");
        TextView mTextView = findViewById(R.id.tvMain);
        mTextView.setText(name);
    }

    public void clickHandler(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                startHome();
                break;
            case R.id.btnCancel:
                createAlarm("bajaj",14,45);
                break;
        }
    }

    public void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void startHome() {
        Toast.makeText(this, "loggin in", Toast.LENGTH_SHORT).show();
        Intent mIntent;
        mIntent  = new Intent(MainActivity.this,HomeActivity.class);

        mIntent.putExtra("VT","Abhinav Tripathi");
        startActivity(mIntent);
    }
}