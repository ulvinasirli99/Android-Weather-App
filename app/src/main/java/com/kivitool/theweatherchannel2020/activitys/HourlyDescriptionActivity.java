package com.kivitool.theweatherchannel2020.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
import com.anychart.charts.LinearGauge;

import com.anychart.enums.Anchor;
import com.anychart.enums.HAlign;
import com.anychart.enums.Orientation;
import com.anychart.enums.Position;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.scales.Base;
import com.anychart.scales.Linear;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kivitool.theweatherchannel2020.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HourlyDescriptionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Context mContext;
    private ImageView icon;
    private TextView hourly_desc_desc, high_temp_hourly_desc, low_tem__hourly_desc,
            wind_speed_hourly_desc, humidity_hourly_desc, pressure_hourly_desc,
            wind_degree_hourly_desc, date_hourly_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_description);

        mContext = this;

        toolbar = findViewById(R.id.hourly_desc_toolbar);
        setSupportActionBar(toolbar);

        icon = findViewById(R.id.hourly_desc_icon);
        hourly_desc_desc = findViewById(R.id.hourly_desc_desc);
        high_temp_hourly_desc = findViewById(R.id.high_temp_hourly_desc);
        low_tem__hourly_desc = findViewById(R.id.low_tem__hourly_desc);
        wind_speed_hourly_desc = findViewById(R.id.wind_speed_hourly_desc);
        wind_degree_hourly_desc = findViewById(R.id.wind_degree_hourly_desc);
        humidity_hourly_desc = findViewById(R.id.humidity_hourly_desc);
        pressure_hourly_desc = findViewById(R.id.pressure_hourly_desc);
        date_hourly_desc = findViewById(R.id.date_hourly_desc);

        //Todo Hourly Forecast getString Extra data give the Adapters

        String icons = getIntent().getStringExtra("icon");
        String description_clear = getIntent().getStringExtra("desc").substring(0,1).toUpperCase() +
                getIntent().getStringExtra("desc").substring(1);
        double max_temp = getIntent().getDoubleExtra("h_temp", 10.0);
        double min_temp = getIntent().getDoubleExtra("l_temp", 5.0);
        double wind_speed = getIntent().getDoubleExtra("wind_km", 23.0);
        int wind_degree = getIntent().getIntExtra("win_deg", 21);
        int humidity = getIntent().getIntExtra("humid", 10);
        int pressure = getIntent().getIntExtra("pres", 12);
        int date_time = getIntent().getIntExtra("date", 0);

        //Todo Hourly Forecast weather decription setText Text

        hourly_desc_desc.setText(description_clear);
        high_temp_hourly_desc.setText("High: " + max_temp);
        low_tem__hourly_desc.setText("Low: " + min_temp);
        wind_speed_hourly_desc.setText("Wind speed: " + wind_speed+" km/h");

        if (wind_degree > 0 && wind_degree < 90) {
            wind_degree_hourly_desc.setText("Wind degree: "+"SW");
        } else if (wind_degree > 90 && wind_degree < 180) {
            wind_degree_hourly_desc.setText("Wind degree: "+"NW");
        } else if (wind_degree > 180 && wind_degree < 270) {
            wind_degree_hourly_desc.setText("Wind degree: "+"NE");
        } else if (wind_degree > 270 && wind_degree < 360) {
            wind_degree_hourly_desc.setText("Wind degree: "+"SE");
        } else if (wind_degree == 0) {
            wind_degree_hourly_desc.setText("Wind degree: "+"S");
        } else if (wind_degree == 90) {
            wind_degree_hourly_desc.setText("Wind degree: "+"W");
        } else if (wind_degree == 180) {
            wind_degree_hourly_desc.setText("Wind degree: "+"N");
        } else if (wind_degree == 270) {
            wind_degree_hourly_desc.setText("Wind degree: "+"E");
        } else if (wind_degree == 360) {
            wind_degree_hourly_desc.setText("Wind degree: "+"S");
        } else {
            Toast.makeText(mContext, "There is no way", Toast.LENGTH_SHORT).show();
        }
        humidity_hourly_desc.setText("Humidity: " + humidity+" %");
        pressure_hourly_desc.setText("Pressure: " + pressure+" hPa");
        Date convertDate = new Date(date_time * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM / dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+4"));
        String formattedDate = dateFormat.format(convertDate);
        date_hourly_desc.setText(formattedDate);


        Glide.with(mContext)

                .asBitmap()

                .load("http://openweathermap.org/img/wn/" + icons + "@2x.png")

                .thumbnail(0.5f)

                .diskCacheStrategy(DiskCacheStrategy.ALL)

                .into(icon);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.horuly_desc_for_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.share_forecast_hourly_desc:
                Toast.makeText(mContext, "Qalib", Toast.LENGTH_SHORT).show();
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}