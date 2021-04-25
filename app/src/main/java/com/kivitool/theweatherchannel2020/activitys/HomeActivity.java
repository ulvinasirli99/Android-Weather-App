package com.kivitool.theweatherchannel2020.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.adapter.list.RHourlyAdapter;
import com.kivitool.theweatherchannel2020.fragment.HourlyFragment;
import com.kivitool.theweatherchannel2020.fragment.TomorrowFragment;
import com.kivitool.theweatherchannel2020.interfaces.ICurrent;
import com.kivitool.theweatherchannel2020.services.forecast.Api;
import com.kivitool.theweatherchannel2020.ui.current_day_hourly.HourlyItem;
import com.kivitool.theweatherchannel2020.ui.currentweather.CurrentWeather;
import com.kivitool.theweatherchannel2020.utils.PagerAdapter;
import com.kivitool.theweatherchannel2020.utils.PreferenceManagers;
import com.onesignal.OneSignal;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HomeActivity";
    private static final String CHANNEL_ID = " HD WALLPAPER";
    private static final String CHANNEL_NAME = "4K Wallpaper";
    private static final String CHANNEL_DESC = "Download";
    private Context context;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager slidePager;
    private TabLayout tabLayout;
    private PagerAdapter slidePagerAdapter;
    private Window window;
    private ImageView newLocationAdd, action_bar_toggle, rain;
    private Switch darkSwitch;
    private View headerView;
    private TextView weekActivity;
    private TextView currentTemp, location_country, location_city, humidity, description, current_date, sun_condition_time;
    private SharedPreferences preferences;
    private Bundle bundle;
    private SharedPreferences.Editor editor;
    private static String q;
    private SwipeRefreshLayout refreshLayout;
    public static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String units = "metric";
    private static final String appid = "6d03a68e0f7c04b439d415eb359d32d2";
    private static String lang;
    private PreferenceManagers preferenceManagers;
    private double latim, lotum;
    private NotificationManagerCompat notificationManager;
    public static final int a = 4011;
    private LineChart tempChart;
    private List<HourlyItem> mForecastList;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;

        //TOdo OneSignal initialization this is app set up .....
        // TODO OneSignal Initialization
        OneSignal.startInit(context)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


        /**
         * Prefefrences manager set save default values this is line downn...
         */
        preferenceManagers = new PreferenceManagers(context);

        tempChart = findViewById(R.id.tempChartHourly);
        mForecastList = new ArrayList<>();

        Intent intent = getIntent();
        q = intent.getStringExtra("q");//Todo This key from AddLocationActivity....
        lang = intent.getStringExtra("lang");

        latim = intent.getDoubleExtra("LT", 40.18);
        lotum = intent.getDoubleExtra("LG", 47.76);

        Log.d(TAG, "onCreate: Latimmm " + latim);
        Log.d(TAG, "onCreate: Longummm" + lotum);

        preferenceManagers.putDouble("lat", (float) latim);
        preferenceManagers.putDouble("lon", (float) lotum);


        drawerLayout = findViewById(R.id.drawer_layout);
        weekActivity = findViewById(R.id.next_day_data);
        slidePager = findViewById(R.id.home_view_pager);
        tabLayout = findViewById(R.id.home_tab_layout);
        action_bar_toggle = findViewById(R.id.action_toggle);
        newLocationAdd = findViewById(R.id.addNewLocation);
        navigationView = findViewById(R.id.design_navigation_view);
        currentTemp = findViewById(R.id.current_temp);
        location_country = findViewById(R.id.location_country);
        location_city = findViewById(R.id.location_city);
        rain = findViewById(R.id.rain);
        humidity = findViewById(R.id.feels_like_count);
        description = findViewById(R.id.weather_description);
        current_date = findViewById(R.id.current_date);
        sun_condition_time = findViewById(R.id.sun_condition_time);
        refreshLayout = findViewById(R.id.swipe_refresh);
        refreshLayout.setOnRefreshListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.inflateHeaderView(R.layout.navigation_for_header);
        darkSwitch = headerView.findViewById(R.id.darkModeSwitch);


        weekActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, WeekActivity.class));
            }
        });


        window = this.getWindow();
        window.setNavigationBarColor(R.color.red);

        newLocationAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent get = new Intent(context, NewLocationActivity.class);
                startActivity(get);
            }
        });

        /**
         * @return slide tablayout and viewpager set is action HomeActivity....
         *
         */
        slidePagerAdapter = new PagerAdapter(getSupportFragmentManager());
        slidePagerAdapter.addFragment(new HourlyFragment(), "Hourly");
        slidePagerAdapter.addFragment(new TomorrowFragment(), "Today");
        slidePager.setAdapter(slidePagerAdapter);
        tabLayout.setupWithViewPager(slidePager);
        tabLayout.setTabTextColors(ColorStateList.valueOf(getColor(R.color.white)));
        tabLayout.setSelectedTabIndicatorColor(getColor(R.color.white));


        UIDarkModeSetChecked();

        saveDarkModeUX();

        onLoadingRefresh();


        SharedPreferences sharedPreferences = getSharedPreferences("XXMMLL", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task_list", null);
        Type type = new TypeToken<ArrayList<HourlyItem>>() {
        }.getType();
        mForecastList = gson.fromJson(json, type);

//        TODO Burda line data set methodu proqram ilk acilanda atir buna sebeb ise ilk defe acilanda hourly listin bos olmasidu ve bunu duzletmek lazimdi

//            lineDataSet();

            




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.newLocation:
                startActivity(new Intent(context, NewLocationActivity.class));
                break;

            case R.id.myLocation:
                if (isServiceOK()) {
                    Intent my_location = new Intent(context, MyLocationActivity.class);
                    startActivity(my_location);
                }
                break;

            case R.id.refreshAll:
                onLoadingRefresh();
                break;

            case R.id.setting:
                startActivity(new Intent(context,SettingActivity.class));
                break;

            case R.id.myRadar:
                startActivity(new Intent(context, RadarActivity.class));
                break;

            case R.id.manageLocations:
                startActivity(new Intent(context, LocationHistoryActivity.class));
                break;

            case R.id.aboutApp:
                startActivity(new Intent(context, AboutActivity.class));
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();

        }
    }

    private void UIDarkModeSetChecked() {

        darkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("NewApi")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (darkSwitch.isChecked()) {

                    headerView.setBackgroundColor(getColor(R.color.current_time_weather_color));

                    drawerLayout.setBackgroundColor(getColor(R.color.current_time_weather_color));

                    action_bar_toggle.setImageResource(R.drawable.menu_bar);

                    editor.putBoolean("checked", true);

                    editor.apply();

                    darkSwitch.setChecked(true);

                } else {

                    headerView.setBackgroundColor(getColor(R.color.reverse_color));

                    drawerLayout.setBackgroundColor(getColor(R.color.reverse_color));

                    action_bar_toggle.setImageResource(R.drawable.cts_dark_mode_reverse);

                    editor.putBoolean("checked", false);

                    editor.apply();

                    darkSwitch.setChecked(false);

                }

            }
        });

    }

    private void saveDarkModeUX() {

        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        editor = preferences.edit();

        if (preferences.contains("checked") && preferences.getBoolean("checked", false) == true) {
            darkSwitch.setChecked(true);
        } else {
            darkSwitch.setChecked(false);
        }

    }

    private void openCurrentWeatherData() {

        refreshLayout.setRefreshing(true);

        final Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        current_date.setText(currentDate);

        ICurrent currentData = Api.getRetrofit().create(ICurrent.class);

        Call<CurrentWeather> currentServiceCall = currentData.getCurrentWeatherData(latim, lotum, units, lang, appid);

        currentServiceCall.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {

                if (response.isSuccessful() && response.body().getName() != null) {

                    refreshLayout.setRefreshing(false);

                    location_city.setText(response.body().getName());
                    location_country.setText(response.body().getSys().getCountry() + " ,");
                    humidity.setText(response.body().getMain().getHumidity() + "%");

                    //Todo Weather icon no response

                    // String iconUrl = "http://openweathermap.org/img/wn/11d@2x.png";

                    String iconUrl = response.body().getWeather().get(0).getIcon();


                    if (iconUrl.equals("01d")) {
                        rain.setImageResource(R.drawable.icon_01d);
                    } else if (iconUrl.equals("02d")) {
                        rain.setImageResource(R.drawable.icon_02d);
                    } else if (iconUrl.equals("03d")) {
                        rain.setImageResource(R.drawable.icon03d);
                    } else if (iconUrl.equals("04d")) {
                        rain.setImageResource(R.drawable.icon_04n);
                    } else if (iconUrl.equals("09d")) {
                        rain.setImageResource(R.drawable.icon_9d);
                    } else if (iconUrl.equals("10d")) {
                        rain.setImageResource(R.drawable.icon_10d);
                    } else if (iconUrl.equals("11d")) {
                        rain.setImageResource(R.drawable.icon_11d);
                    } else if (iconUrl.equals("13d")) {
                        rain.setImageResource(R.drawable.icon_13n);
                    } else if (iconUrl.equals("50d")) {
                        rain.setImageResource(R.drawable.icon_50d);
                    } else if (iconUrl.equals("01n")) {
                        rain.setImageResource(R.drawable.icon_01n);
                    } else if (iconUrl.equals("02n")) {
                        rain.setImageResource(R.drawable.icon_02n);
                    } else if (iconUrl.equals("03n")) {
                        rain.setImageResource(R.drawable.icon03d);
                    } else if (iconUrl.equals("04n")) {
                        rain.setImageResource(R.drawable.icon_04n);
                    } else if (iconUrl.equals("09n")) {
                        rain.setImageResource(R.drawable.icon_9d);
                    } else if (iconUrl.equals("10n")) {
                        rain.setImageResource(R.drawable.icon_10n);
                    } else if (iconUrl.equals("11n")) {
                        rain.setImageResource(R.drawable.icon_11n);
                    } else if (iconUrl.equals("13n")) {
                        rain.setImageResource(R.drawable.icon_13n);
                    } else if (iconUrl.equals("50n")) {
                        rain.setImageResource(R.drawable.icon_50d);
                    } else {

                        Toast.makeText(context, "Not found weather !", Toast.LENGTH_SHORT).show();

                    }


                    currentTemp.setText(response.body().getMain().getTemp() + " ℃");

                    String upperLetter = response.body().getWeather().get(0).getDescription().substring(0, 1).toUpperCase() +
                            response.body().getWeather().get(0).getDescription().substring(1);
                    description.setText(upperLetter);
                    int let = response.body().getSys().getSunset();
                    Date convertDate = new Date(let * 1000L);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    dateFormat.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
                    String formattedDate = dateFormat.format(convertDate);
                    sun_condition_time.setText(formattedDate);

                    /**
                     * TODO Burda widget ucun datalar save olunur ve diger terefde alinir
                     * qeyd olunmus ededle Her f de her App run olanda olur
                     * buda yarimciq qalib.TODO.............
                     */
                    String widget_temp = response.body().getMain().getTemp() + "";
                    String icon_id = response.body().getWeather().get(0).getIcon();
                    preferenceManagers.putString("widget_desc", upperLetter);
                    preferenceManagers.putString("widget_temp", widget_temp);
                    preferenceManagers.putString("icon_id", icon_id);

                    /**Todo Əgər response 200 olmaszsa o zaman Failure dusecek ve burda
                     * prefeces manager ile sav edib failure de yeniden save olan
                     * datalari alaciq...Todo ............
                     */
                    int Temp = (int) response.body().getMain().getTemp();
                    String desC = response.body().getWeather().get(0).getDescription().substring(0, 1).toUpperCase() +
                            response.body().getWeather().get(0).getDescription().substring(1);
                    String cunEndT = formattedDate;
                    int humiDity = response.body().getMain().getHumidity();
                    String local_CityName = response.body().getName();
                    String local_CountryName = response.body().getSys().getCountry();
                    String iconIdO = response.body().getWeather().get(0).getIcon();

                    preferenceManagers.putInteger("sTemp", Temp);
                    preferenceManagers.putString("sDesc", desC);
                    preferenceManagers.putString("sCunEndT", cunEndT);
                    preferenceManagers.putInteger("sHumidity", humiDity);
                    preferenceManagers.putString("sCityName", local_CityName);
                    preferenceManagers.putString("sCountryName", local_CountryName);
                    preferenceManagers.putString("sIconO", iconIdO);

                    //TODO SetRefreshing is false and end point.....

                    refreshLayout.setRefreshing(false);

                } else {
                    Toast.makeText(context, "Not Found" + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

                location_city.setText(preferenceManagers.getSting("sCityName"));
                location_country.setText(preferenceManagers.getSting("sCountryName") + " ,");
                humidity.setText(preferenceManagers.getInteger("sHumidity") + "%");
                currentTemp.setText(preferenceManagers.getInteger("sTemp") + " ℃");
                description.setText(preferenceManagers.getSting("sDesc"));
                sun_condition_time.setText(preferenceManagers.getSting("sCunEndT"));

                String iconUrl = preferenceManagers.getSting("sIconO");

                if (iconUrl.equals("01d")) {
                    rain.setImageResource(R.drawable.icon_01d);
                } else if (iconUrl.equals("02d")) {
                    rain.setImageResource(R.drawable.icon_02d);
                } else if (iconUrl.equals("03d")) {
                    rain.setImageResource(R.drawable.icon03d);
                } else if (iconUrl.equals("04d")) {
                    rain.setImageResource(R.drawable.icon_04n);
                } else if (iconUrl.equals("09d")) {
                    rain.setImageResource(R.drawable.icon_9d);
                } else if (iconUrl.equals("10d")) {
                    rain.setImageResource(R.drawable.icon_10d);
                } else if (iconUrl.equals("11d")) {
                    rain.setImageResource(R.drawable.icon_11d);
                } else if (iconUrl.equals("13d")) {
                    rain.setImageResource(R.drawable.icon_13n);
                } else if (iconUrl.equals("50d")) {
                    rain.setImageResource(R.drawable.icon_50d);
                } else if (iconUrl.equals("01n")) {
                    rain.setImageResource(R.drawable.icon_01n);
                } else if (iconUrl.equals("02n")) {
                    rain.setImageResource(R.drawable.icon_02n);
                } else if (iconUrl.equals("03n")) {
                    rain.setImageResource(R.drawable.icon03d);
                } else if (iconUrl.equals("04n")) {
                    rain.setImageResource(R.drawable.icon_04n);
                } else if (iconUrl.equals("09n")) {
                    rain.setImageResource(R.drawable.icon_9d);
                } else if (iconUrl.equals("10n")) {
                    rain.setImageResource(R.drawable.icon_10n);
                } else if (iconUrl.equals("11n")) {
                    rain.setImageResource(R.drawable.icon_11n);
                } else if (iconUrl.equals("13n")) {
                    rain.setImageResource(R.drawable.icon_13n);
                } else if (iconUrl.equals("50n")) {
                    rain.setImageResource(R.drawable.icon_50d);
                } else {

                    Toast.makeText(context, "Not found weather !", Toast.LENGTH_SHORT).show();

                }

                refreshLayout.setRefreshing(false);

                Toast.makeText(context, "Data cannot be updated. Please activate the connection.", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private boolean isServiceOK() {

        Log.d(TAG, "isServiceOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);

        if (available == ConnectionResult.SUCCESS) {
            // everything is fine and the user can make requests
            Log.d(TAG, "isServiceOK: Google Play Service is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can fix it
            Log.d(TAG, "isServiceOK: error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(HomeActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(context, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    @Override
    public void onRefresh() {

        openCurrentWeatherData();

    }

    private void onLoadingRefresh() {

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                openCurrentWeatherData();
            }
        });

    }


    private void lineDataSet() {

        tempChart.setDrawGridBackground(false);
        tempChart.setTouchEnabled(true);
        tempChart.setDragEnabled(false);
        tempChart.setPinchZoom(false);
        tempChart.setTouchEnabled(false);
        tempChart.getLegend().setEnabled(false);

        XAxis x = tempChart.getXAxis();
        x.setEnabled(true);
        x.setAxisLineColor(Color.parseColor("#CFE708"));
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        x.setDrawGridLines(false);
        x.setDrawAxisLine(false);
        x.enableGridDashedLine(5f, 10f, 0f);
        x.setGridColor(Color.parseColor("#F9C805"));
        x.setTextColor((Color.parseColor("#FEFEFE")));// Todo Burda xettin qiraginda 300 yeralan qiymetlerin rengidi
        x.setXOffset(30);


        YAxis yLeft = tempChart.getAxisLeft();
        yLeft.setEnabled(true);
        yLeft.setAxisLineColor(Color.parseColor("#CFE708"));
        yLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yLeft.setDrawGridLines(false);
        yLeft.setDrawAxisLine(false);
        yLeft.enableGridDashedLine(5f, 10f, 0f);
        yLeft.setGridColor(Color.parseColor("#F9C805"));
        yLeft.setTextColor((Color.parseColor("#FEFEFE")));
        yLeft.setXOffset(30);

        tempChart.getAxisRight().setEnabled(false);
        tempChart.getAxisLeft().setTextColor(Color.parseColor("#1e1e45"));
        tempChart.getAxisRight().setTextColor(Color.parseColor("#1e1e45"));

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < mForecastList.size(); i++) {
            float temperatureDay = (float) mForecastList.get(i).getTemp();
            entries.add(new Entry(i, temperatureDay));
        }

        LineDataSet set;
        if (tempChart.getData() != null) {
            tempChart.getData().removeDataSet(tempChart.getData().getDataSetByIndex(
                    tempChart.getData().getDataSetCount() - 1));
            set = new LineDataSet(entries, "Day");
            set.setMode(LineDataSet.Mode.LINEAR);
            set.setHighLightColor(Color.parseColor("#EC0B0B"));
            set.setCubicIntensity(0.2f);
            set.setDrawCircles(true);//Todo Xettin ustunde olan yumrularlar bildimrlerdi
            set.setCircleColor(Color.parseColor("#CF00FF"));//Todo Xettin ustunde olan yumrularin rengi
            set.setLineWidth(4f);//Todo Xettin qalinligini ifade edir....
            set.setDrawValues(false);//Todo xettin ustunde olan yazilardi dereceler
            set.setValueTextSize(1.0f);
            set.setColor(Color.parseColor("#FF0000"));//Todo Yazilan cizigin rengidi
            set.setHighlightEnabled(true);//Todo
            set.setCircleRadius(2.5f);// Todo xettedki cricleerin olcusudur
            LineData data = new LineData(set);
            tempChart.setData(data);
        } else {
            set = new LineDataSet(entries, "Day");
            set.setHighLightColor(Color.parseColor("#EC0B0B"));
            set.setMode(LineDataSet.Mode.LINEAR);
            set.setCubicIntensity(0.2f);
            set.setDrawCircles(true);
            set.setCircleColor(Color.parseColor("#CF00FF"));
            set.setLineWidth(4f);
            set.setValueTextSize(1.0f);
            set.setDrawValues(false);
            set.setColor(Color.parseColor("#FF0000"));
            set.setHighlightEnabled(true);
            set.setCircleRadius(2.5f);
            LineData data = new LineData(set);
            tempChart.setData(data);
        }

        tempChart.notifyDataSetChanged();


    }

    private void IfChartNullDataSet(){

        tempChart.setDrawGridBackground(false);
        tempChart.setTouchEnabled(true);
        tempChart.setDragEnabled(false);
        tempChart.setPinchZoom(false);
        tempChart.setTouchEnabled(false);
        tempChart.getLegend().setEnabled(false);

        XAxis x = tempChart.getXAxis();
        x.setEnabled(true);
        x.setAxisLineColor(Color.parseColor("#CFE708"));
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        x.setDrawGridLines(false);
        x.setDrawAxisLine(false);
        x.enableGridDashedLine(5f, 10f, 0f);
        x.setGridColor(Color.parseColor("#F9C805"));
        x.setTextColor((Color.parseColor("#FEFEFE")));// Todo Burda xettin qiraginda 300 yeralan qiymetlerin rengidi
        x.setXOffset(30);


        YAxis yLeft = tempChart.getAxisLeft();
        yLeft.setEnabled(true);
        yLeft.setAxisLineColor(Color.parseColor("#CFE708"));
        yLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yLeft.setDrawGridLines(false);
        yLeft.setDrawAxisLine(false);
        yLeft.enableGridDashedLine(5f, 10f, 0f);
        yLeft.setGridColor(Color.parseColor("#F9C805"));
        yLeft.setTextColor((Color.parseColor("#FEFEFE")));
        yLeft.setXOffset(30);

        tempChart.getAxisRight().setEnabled(false);
        tempChart.getAxisLeft().setTextColor(Color.parseColor("#1e1e45"));
        tempChart.getAxisRight().setTextColor(Color.parseColor("#1e1e45"));

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < mForecastList.size(); i++) {
            float temperatureDay = (float) mForecastList.get(i).getTemp();
            entries.add(new Entry(400, 40));
        }

        LineDataSet set;
        if (tempChart.getData() != null) {
            tempChart.getData().removeDataSet(tempChart.getData().getDataSetByIndex(
                    tempChart.getData().getDataSetCount() - 1));
            set = new LineDataSet(entries, "Day");
            set.setMode(LineDataSet.Mode.LINEAR);
            set.setHighLightColor(Color.parseColor("#EC0B0B"));
            set.setCubicIntensity(0.2f);
            set.setDrawCircles(true);//Todo Xettin ustunde olan yumrularlar bildimrlerdi
            set.setCircleColor(Color.parseColor("#CF00FF"));//Todo Xettin ustunde olan yumrularin rengi
            set.setLineWidth(4f);//Todo Xettin qalinligini ifade edir....
            set.setDrawValues(false);//Todo xettin ustunde olan yazilardi dereceler
            set.setValueTextSize(1.0f);
            set.setColor(Color.parseColor("#FF0000"));//Todo Yazilan cizigin rengidi
            set.setHighlightEnabled(true);//Todo
            set.setCircleRadius(2.5f);// Todo xettedki cricleerin olcusudur
            LineData data = new LineData(set);
            tempChart.setData(data);
        } else {
            set = new LineDataSet(entries, "Day");
            set.setHighLightColor(Color.parseColor("#EC0B0B"));
            set.setMode(LineDataSet.Mode.LINEAR);
            set.setCubicIntensity(0.2f);
            set.setDrawCircles(true);
            set.setCircleColor(Color.parseColor("#CF00FF"));
            set.setLineWidth(4f);
            set.setValueTextSize(1.0f);
            set.setDrawValues(false);
            set.setColor(Color.parseColor("#FF0000"));
            set.setHighlightEnabled(true);
            set.setCircleRadius(2.5f);
            LineData data = new LineData(set);
            tempChart.setData(data);
        }

        tempChart.notifyDataSetChanged();



    }


}