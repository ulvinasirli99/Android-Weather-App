package com.kivitool.theweatherchannel2020.adapter.list;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.activitys.HomeActivity;
import com.kivitool.theweatherchannel2020.ui.current_day_hourly.DailyItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class RWeekAdapter extends RecyclerView.Adapter<RWeekAdapter.ViewHolder> {

    private Context context;
    private List<DailyItem> itemsList;

    public RWeekAdapter(Context context, List<DailyItem> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.custom_week_remaining_items,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DailyItem item = itemsList.get(position);

        int week_name = item.getDt();
        Calendar calendar = Calendar.getInstance();
        Date convertDate = new Date(week_name * 1000L);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEE");
        dateFormat2.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
        String formattedDate2 = dateFormat2.format(convertDate);
        holder.week_name.setText(formattedDate2);
        int humi_count = item.getHumidity();
        holder.humidity_count.setText(humi_count+" %");
        int minTemp = (int) item.getTemp().getMin()-273;
        int maxTemp = (int) item.getTemp().getMax()-273;
        holder.min_temp.setText(minTemp+" ℃");
        holder.max_temp.setText(maxTemp+" ℃");

        String iconUrl = item.getWeather().get(0).getIcon();

        if (iconUrl.equals("01d")) {
            holder.imageIcon.setImageResource(R.drawable.icon_01d);
        } else if (iconUrl.equals("02d")) {
            holder.imageIcon.setImageResource(R.drawable.icon_02d);
        } else if (iconUrl.equals("03d")) {
            holder.imageIcon.setImageResource(R.drawable.icon03d);
        } else if (iconUrl.equals("04d")) {
            holder.imageIcon.setImageResource(R.drawable.icon_04n);
        } else if (iconUrl.equals("09d")) {
            holder.imageIcon.setImageResource(R.drawable.icon_9d);
        } else if (iconUrl.equals("10d")) {
            holder.imageIcon.setImageResource(R.drawable.icon_10d);
        } else if (iconUrl.equals("11d")) {
            holder.imageIcon.setImageResource(R.drawable.icon_11d);
        } else if (iconUrl.equals("13d")) {
            holder.imageIcon.setImageResource(R.drawable.icon_13n);
        } else if (iconUrl.equals("50d")) {
            holder.imageIcon.setImageResource(R.drawable.icon_50d);
        } else if (iconUrl.equals("01n")) {
            holder.imageIcon.setImageResource(R.drawable.icon_01n);
        } else if (iconUrl.equals("02n")) {
            holder.imageIcon.setImageResource(R.drawable.icon_02n);
        } else if (iconUrl.equals("03n")) {
            holder.imageIcon.setImageResource(R.drawable.icon03d);
        } else if (iconUrl.equals("04n")) {
            holder.imageIcon.setImageResource(R.drawable.icon_04n);
        } else if (iconUrl.equals("09n")) {
            holder.imageIcon.setImageResource(R.drawable.icon_9d);
        } else if (iconUrl.equals("10n")) {
            holder.imageIcon.setImageResource(R.drawable.icon_10n);
        } else if (iconUrl.equals("11n")) {
            holder.imageIcon.setImageResource(R.drawable.icon_11n);
        } else if (iconUrl.equals("13n")) {
            holder.imageIcon.setImageResource(R.drawable.icon_13n);
        } else if (iconUrl.equals("50n")) {
            holder.imageIcon.setImageResource(R.drawable.icon_50d);
        } else {

            Toast.makeText(context, "Not found weather !", Toast.LENGTH_SHORT).show();

        }




    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView week_name,humidity_count,min_temp,max_temp,wind_speed;
        ImageView imageIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            week_name = itemView.findViewById(R.id.week_name);
            humidity_count = itemView.findViewById(R.id.humidity_count);
            min_temp = itemView.findViewById(R.id.minTemp);
            max_temp= itemView.findViewById(R.id.maxTemp);
            imageIcon = itemView.findViewById(R.id.iconImage);

        }
    }
}
