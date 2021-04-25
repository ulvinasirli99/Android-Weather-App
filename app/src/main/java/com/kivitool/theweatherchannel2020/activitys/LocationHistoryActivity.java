package com.kivitool.theweatherchannel2020.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.database.DatabaseHandler;
import com.kivitool.theweatherchannel2020.database.DbAdapter;
import com.kivitool.theweatherchannel2020.database.LocationModel;

import java.util.ArrayList;

public class LocationHistoryActivity extends AppCompatActivity {

    private static final String TAG = "LocationHistoryActivity";
    private DatabaseHandler databaseHandler;
    private Context context;
    private Window window;
    private ListView mListView;
    private ArrayList<LocationModel> modelArrayList;
    private DbAdapter adapter = null;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_history);

        context = this;

        window = this.getWindow();
        window.setNavigationBarColor(R.color.red);

        databaseHandler = new DatabaseHandler(context);

        modelArrayList = new ArrayList<>();

        mListView = findViewById(R.id.location_history);


        getAllDataLocations();


    }

    private void setDataListViewLocations() {

        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = databaseHandler.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
//
            }
        });


    }


    /**
     * customizable toast
     *
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void getAllDataLocations() {

        adapter = new DbAdapter(context, modelArrayList, R.layout.custom_lisview_data_item);

        Cursor cursor = databaseHandler.getData();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            modelArrayList.add(new LocationModel(id, name));

        }

        adapter.notifyDataSetChanged();

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }


}