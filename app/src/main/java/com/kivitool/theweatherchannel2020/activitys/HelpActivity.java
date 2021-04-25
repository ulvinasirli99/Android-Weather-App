package com.kivitool.theweatherchannel2020.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import com.kivitool.theweatherchannel2020.R;

public class HelpActivity extends AppCompatActivity {

    private Context context;
    private Window window;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        context = this;

        window = this.getWindow();
        window.setNavigationBarColor(R.color.red);

    }
}