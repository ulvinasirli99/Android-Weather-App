package com.kivitool.theweatherchannel2020.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.activitys.HomeActivity;
import com.kivitool.theweatherchannel2020.utils.PreferenceManagers;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class Weatherwidget extends AppWidgetProvider {

    private static final String TAG = "Weatherwidget";
    private PreferenceManagers preferenceManagers;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            preferenceManagers = new PreferenceManagers(context);

            final Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());


            String icon_id = preferenceManagers.getSting("icon_id");
            String temp = preferenceManagers.getSting("widget_temp");
            String desc = preferenceManagers.getSting("widget_desc");


            Log.d(TAG, "onUpdate: Tempratura : " + temp);
            Log.d(TAG, "onUpdate: Melumat : " + desc);

            Intent intent = new Intent(context, HomeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 110202, intent, 0);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.weatherwidget);

            remoteViews.setOnClickPendingIntent(R.id.getPendig, pendingIntent);

            remoteViews.setTextViewText(R.id.widget_temp, temp + "℃");
            remoteViews.setTextViewText(R.id.widget_description, desc);
            remoteViews.setTextViewText(R.id.widget_calendar, currentDate);
//
            if (icon_id.equals("01d")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_01d);
            } else if (icon_id.equals("02d")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_02d);
            } else if (icon_id.equals("03d")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon03d);
            } else if (icon_id.equals("04d")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_04n);
            } else if (icon_id.equals("09d")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_9d);
            } else if (icon_id.equals("10d")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_10d);
            } else if (icon_id.equals("11d")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_11d);
            } else if (icon_id.equals("13d")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_13n);
            } else if (icon_id.equals("50d")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_50d);
            } else if (icon_id.equals("01n")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_01n);
            } else if (icon_id.equals("02n")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_02n);
            } else if (icon_id.equals("03n")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon03d);
            } else if (icon_id.equals("04n")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_04n);
            } else if (icon_id.equals("09n")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_9d);
            } else if (icon_id.equals("10n")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_10n);
            } else if (icon_id.equals("11n")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_11n);
            } else if (icon_id.equals("13n")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_13n);
            } else if (icon_id.equals("50n")) {
                remoteViews.setImageViewResource(R.id.widget_icon, R.drawable.icon_50d);
            } else {
                Toast.makeText(context, "Not found weather !", Toast.LENGTH_SHORT).show();
            }

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}

