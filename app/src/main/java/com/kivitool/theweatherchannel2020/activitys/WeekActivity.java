package com.kivitool.theweatherchannel2020.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.adapter.list.RHourlyAdapter;
import com.kivitool.theweatherchannel2020.adapter.list.RWeekAdapter;
import com.kivitool.theweatherchannel2020.interfaces.ICurrent;
import com.kivitool.theweatherchannel2020.interfaces.ICurrentHourlyDesc;
import com.kivitool.theweatherchannel2020.services.forecast.Api;
import com.kivitool.theweatherchannel2020.services.location.Constants;
import com.kivitool.theweatherchannel2020.ui.current_day_hourly.DailyItem;
import com.kivitool.theweatherchannel2020.ui.current_day_hourly.HCallback;
import com.kivitool.theweatherchannel2020.ui.current_day_hourly.HourlyItem;
import com.kivitool.theweatherchannel2020.utils.PreferenceManagers;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeekActivity extends AppCompatActivity {

    private Context context;
    private Window window;
    private ImageView getHomeActivity;
    private RWeekAdapter adapter;
    private List<DailyItem> itemList;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView monday,max,min,uv,humid,pressure;
    private ImageView icon;
    private double lat,lon;
    private PreferenceManagers managers;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        context = this;


        window = this.getWindow();
        window.setNavigationBarColor(R.color.red);

        managers = new PreferenceManagers(context);

        recyclerView = findViewById(R.id.week_recycler);
        monday = findViewById(R.id.ffl);
        icon = findViewById(R.id.mmnd);
        max = findViewById(R.id.lmt);
        min = findViewById(R.id.miniTemlp);
        uv = findViewById(R.id.uv_count);
        humid = findViewById(R.id.otr);
        pressure = findViewById(R.id.pres_count);
        lat = managers.getDouble("lat");
        lon = managers.getDouble("lon");
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getHomeActivity = findViewById(R.id.rrm);

        getHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "laaaa laaa", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context,HomeActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        dailyForecastData();


    }

    private void dailyForecastData(){

        ICurrentHourlyDesc hourlyDesc = Api.getRetrofit().create(ICurrentHourlyDesc.class);

        Call<HCallback> callbackCall = hourlyDesc.getHourlyData(lat,lon,"hourly,daily", Constants.API_KEY);

        callbackCall.enqueue(new Callback<HCallback>() {
            @Override
            public void onResponse(Call<HCallback> call, Response<HCallback> response) {

                if (response.isSuccessful() && response.body().getCurrent()!=null){

                    itemList = response.body().getDaily();

                    dailyForecastSaveDataList(itemList);

                    adapter = new RWeekAdapter(context,itemList);

                    recyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<HCallback> call, Throwable t) {

                dailyForecastLastDataList();

            }
        });

    }

    private void dailyForecastSaveDataList(List<DailyItem> itemList){

        SharedPreferences sharedPreferences = getSharedPreferences("OTRSAL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonArray = gson.toJson(itemList);
        editor.putString("QMMXXLS", jsonArray);
        editor.apply();

    }

    private void dailyForecastLastDataList(){

        SharedPreferences sharedPreferences = getSharedPreferences("OTRSAL", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("QMMXXLS", null);
        Type type = new TypeToken<ArrayList<DailyItem>>() {
        }.getType();
        itemList = gson.fromJson(json, type);

        adapter = new RWeekAdapter(context, itemList);

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


    }


}