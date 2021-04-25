package com.kivitool.theweatherchannel2020.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.adapter.list.RHourlyAdapter;
import com.kivitool.theweatherchannel2020.adapter.list.RTAdapter;
import com.kivitool.theweatherchannel2020.interfaces.IHourlyForecast;
import com.kivitool.theweatherchannel2020.services.forecast.Api;
import com.kivitool.theweatherchannel2020.ui.current_day_hourly.HourlyItem;
import com.kivitool.theweatherchannel2020.ui.forecast_hourly.HourlyForecast;
import com.kivitool.theweatherchannel2020.ui.forecast_hourly.ListItem;
import com.kivitool.theweatherchannel2020.utils.PreferenceManagers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TomorrowFragment extends Fragment {

    private View rootView;
    private Context context;
    private RecyclerView recyclerView;
    private List<ListItem> listItems;
    private LinearLayoutManager layoutManager;
    private RTAdapter adapter;
    private String city;
    private String units = "metric;";
    private String lang;
    private Bundle bundle;
    private String appid = "6d03a68e0f7c04b439d415eb359d32d2";
    private double lat,lon;
    private PreferenceManagers preferenceManagers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_tomorrow, container, false);

        context = getActivity();

        recyclerView = rootView.findViewById(R.id.tomorrow_fragment_recycler_view);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        preferenceManagers = new PreferenceManagers(context);

        lat = preferenceManagers.getDouble("lat");
        lon = preferenceManagers.getDouble("lon");



        hourlyTomorrowtempData();


        return rootView;
    }

    private void hourlyTomorrowtempData() {

        IHourlyForecast forecast = Api.getRetrofit().create(IHourlyForecast.class);

        Call<HourlyForecast> call = forecast.getHourlyData(lat,lon, units,"az", appid);

        call.enqueue(new Callback<HourlyForecast>() {
            @Override
            public void onResponse(Call<HourlyForecast> call, Response<HourlyForecast> response) {

                if (response.isSuccessful()) {

                    listItems = response.body().getList();

                    todayDataListArraySave(listItems);

                    adapter = new RTAdapter(context, listItems);

                    recyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<HourlyForecast> call, Throwable t) {

                todayDataListLoad();

            }
        });

    }

    private void todayDataListArraySave(List<ListItem> listItems){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OOPASS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonArray = gson.toJson(listItems);
        editor.putString("MMMXXX", jsonArray);
        editor.apply();

    }

    private void todayDataListLoad(){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OOPASS", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("MMMXXX", null);
        Type type = new TypeToken<ArrayList<ListItem>>() {
        }.getType();

        listItems = gson.fromJson(json,type);

        adapter = new RTAdapter(context,listItems);

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


    }

}