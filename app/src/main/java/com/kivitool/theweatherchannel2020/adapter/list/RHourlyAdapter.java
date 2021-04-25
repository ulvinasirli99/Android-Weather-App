package com.kivitool.theweatherchannel2020.adapter.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.activitys.HourlyDescriptionActivity;
import com.kivitool.theweatherchannel2020.ui.current_day_hourly.HourlyItem;
import com.kivitool.theweatherchannel2020.ui.forecast_hourly.ListItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class RHourlyAdapter extends RecyclerView.Adapter<RHourlyAdapter.ViewHolder> {

    private Context context;

    private List<HourlyItem> list;

    public RHourlyAdapter(Context context, List<HourlyItem> list) {
        this.context = context;
        this.list = list;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.custom_today_listview_horizantal_data,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HourlyItem item = list.get(position);

        int time = item.getDt();

        final Calendar calendar = Calendar.getInstance();

        Date convertDate = new Date(time * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
        String formattedDate = dateFormat.format(convertDate);
        holder.hourly_time.setText(formattedDate);

        int temp = (int) (item.getTemp()-273);

        holder.hourly_temp.setText(temp+"°C");

        String iconUrl = item.getWeather().get(0).getIcon();

        if (iconUrl.equals("01d")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_01d);
        } else if (iconUrl.equals("02d")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_02d);
        } else if (iconUrl.equals("03d")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon03d);
        } else if (iconUrl.equals("04d")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_04n);
        } else if (iconUrl.equals("09d")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_9d);
        } else if (iconUrl.equals("10d")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_10d);
        } else if (iconUrl.equals("11d")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_11d);
        } else if (iconUrl.equals("13d")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_13n);
        } else if (iconUrl.equals("50d")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_50d);
        } else if (iconUrl.equals("01n")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_01n);
        } else if (iconUrl.equals("02n")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_02n);
        } else if (iconUrl.equals("03n")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon03d);
        } else if (iconUrl.equals("04n")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_04n);
        } else if (iconUrl.equals("09n")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_9d);
        } else if (iconUrl.equals("10n")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_10n);
        } else if (iconUrl.equals("11n")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_11n);
        } else if (iconUrl.equals("13n")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_13n);
        } else if (iconUrl.equals("50n")) {
            holder.hourly_temp_icon.setImageResource(R.drawable.icon_50d);
        } else {

            Toast.makeText(context, "Not found weather !", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView hourly_time, hourly_temp;
        CircleImageView hourly_temp_icon;
        RelativeLayout today_description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hourly_time = itemView.findViewById(R.id.hourly_time);
            hourly_temp = itemView.findViewById(R.id.hourly_temp);
            hourly_temp_icon = itemView.findViewById(R.id.hourly_temp_icon);
            today_description = itemView.findViewById(R.id.today_description);


        }

    }

}