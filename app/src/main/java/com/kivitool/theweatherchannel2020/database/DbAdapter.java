package com.kivitool.theweatherchannel2020.database;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.interfaces.IDialogListener;

import java.util.ArrayList;

public class DbAdapter extends BaseAdapter implements IDialogListener {

    private static final String TAG = "DbAdapter";
    private Context context;
    private ArrayList<LocationModel> list;
    private int layout;
    private EditText location_Update_name;
    private DatabaseHandler databaseHandler;

    public DbAdapter(Context context, ArrayList<LocationModel> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void applyEditTexts(String location) {

    }

    private class ViewHolder {

        TextView textView;
        ImageView edit, up, down, delete_item;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;

        ViewHolder holder = new ViewHolder();

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(layout, null);

            holder.textView = row.findViewById(R.id.location_list_item);
            holder.delete_item = row.findViewById(R.id.delete_item);
            holder.down = row.findViewById(R.id.location_down_move);
            holder.up = row.findViewById(R.id.location_up_move);
            holder.edit = row.findViewById(R.id.editLocations);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        final LocationModel locationModel = list.get(position);

        holder.textView.setText(locationModel.getLocation_name());

        databaseHandler = new DatabaseHandler(context);

        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.removeLocationDataListItem(list.get(position).getId());
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateDialogBox();

            }
        });


        return row;
    }

    private void updateDialogBox() {

        databaseHandler = new DatabaseHandler(context);

        LayoutInflater factory = LayoutInflater.from(context);

        View view = factory.inflate(R.layout.custom_update_database_location, null);

        final AlertDialog builder = new AlertDialog.Builder(context).create();

        builder.setView(view);

        location_Update_name = view.findViewById(R.id.database_location_update_name);

        view.findViewById(R.id.auto_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location_name_for_database = location_Update_name.getText().toString();
                databaseHandler.updateDatabaseLocationName(location_name_for_database);
                notifyDataSetChanged();
                builder.dismiss();
            }
        });

        view.findViewById(R.id.auto_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        builder.show();

    }


}
