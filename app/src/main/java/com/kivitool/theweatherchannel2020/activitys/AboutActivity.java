package com.kivitool.theweatherchannel2020.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kivitool.theweatherchannel2020.BuildConfig;
import com.kivitool.theweatherchannel2020.R;

public class AboutActivity extends AppCompatActivity {

    private Context context;
    private ImageView backJoker;
    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        context = this;

        backJoker = findViewById(R.id.back_joker);
        version = findViewById(R.id.version);

        version.setText(BuildConfig.VERSION_NAME);

        backJoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HomeActivity.class);
                startActivity(intent);
            }
        });


    }
}