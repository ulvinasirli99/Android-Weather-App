package com.kivitool.theweatherchannel2020.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.ui.forecast_hourly.ListItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class RTAdapter extends RecyclerView.Adapter<RTAdapter.ViewHolder> {

    private Context context;
    private List<ListItem> listItems;

    public RTAdapter(Context context, List<ListItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(context)

                .inflate(R.layout.custom_tomorrow_listview_horizantal_data,parent,false);

        ViewHolder viewHolder = new ViewHolder(layout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        final ListItem listItem = listItems.get(position);


        int updateTime = listItem.getDt();
        Date convertDate = new Date(updateTime * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+4"));
        String formattedDate = dateFormat.format(convertDate);
        holder.hourly_time_tomorrow.setText(formattedDate);
        int temprature = (int) (listItem.getMain().getTemp() - 273);
        holder.hourly_temp_tomorrow.setText(temprature + "°C");

        Glide.with(context)

                .asBitmap()

                .load("http://openweathermap.org/img/wn/" + listItem.getWeather().get(0).getIcon() + "@2x.png")

                .thumbnail(0.5f)

                .diskCacheStrategy(DiskCacheStrategy.ALL)

                .into(holder.hourly_temp_icon_tomorrow);


    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView hourly_time_tomorrow,hourly_temp_tomorrow;
        CircleImageView hourly_temp_icon_tomorrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hourly_time_tomorrow = itemView.findViewById(R.id.hourly_time_tomorrow);
            hourly_temp_tomorrow = itemView.findViewById(R.id.hourly_temp_tomorrow);
            hourly_temp_icon_tomorrow = itemView.findViewById(R.id.hourly_temp_icon_tomorrow);

        }
    }

}
