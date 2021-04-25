package com.kivitool.theweatherchannel2020.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.adapter.list.RHourlyAdapter;
import com.kivitool.theweatherchannel2020.interfaces.ICurrentHourlyDesc;
import com.kivitool.theweatherchannel2020.services.forecast.Api;
import com.kivitool.theweatherchannel2020.ui.current_day_hourly.HCallback;
import com.kivitool.theweatherchannel2020.ui.current_day_hourly.HourlyItem;
import com.kivitool.theweatherchannel2020.utils.PreferenceManagers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourlyFragment extends Fragment {

    private static final String TAG = "HourlyFragment";
    private View view;
    private RecyclerView recyclerView;
    private RHourlyAdapter adapter;
    private List<HourlyItem> itemList;
    private double lat, lon;
    private LinearLayoutManager layoutManager;
    private String appid = "6d03a68e0f7c04b439d415eb359d32d2";
    private Context context;
    private PreferenceManagers preferenceManagers;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hourly, container, false);

        context = getActivity();

        preferenceManagers = new PreferenceManagers(context);

        recyclerView = view.findViewById(R.id.today_fragment_recycler_view);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        lat = preferenceManagers.getDouble("lat");
        lon = preferenceManagers.getDouble("lon");

        Log.d(TAG, "onCreateView: Lat Aldim burda " + (float) lat);

        /**
         * Tjis is lat and lon is shared prefences give to
         * local current temp lat - lon set Action manager
         *
         */

        hourlyWeatherData();

        return view;

    }

    private void hourlyWeatherData() {

        ICurrentHourlyDesc desc = Api.getRetrofit().create(ICurrentHourlyDesc.class);

        Call<HCallback> hCallbackCall = desc.getHourlyData(lat, lon, "hourly,daily", appid);

        hCallbackCall.enqueue(new Callback<HCallback>() {
            @Override
            public void onResponse(Call<HCallback> call, Response<HCallback> response) {

                if (response.isSuccessful() && response.body().getCurrent() != null) {

                    itemList = response.body().getHourly();

                    JsonSaveDataListArray(itemList);

                    adapter = new RHourlyAdapter(context, itemList);

                    recyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                }
                adapter = new RHourlyAdapter(context, itemList);

                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<HCallback> call, Throwable t) {

                loadData();

            }
        });

    }

    private void JsonSaveDataListArray(List<HourlyItem> hourlyItemList) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("XXMMLL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonArray = gson.toJson(hourlyItemList);
        editor.putString("task_list", jsonArray);
        editor.apply();

    }

    private void loadData() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("XXMMLL", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task_list", null);
        Type type = new TypeToken<ArrayList<HourlyItem>>() {
        }.getType();
        itemList = gson.fromJson(json, type);

        adapter = new RHourlyAdapter(context, itemList);

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


    }


}
