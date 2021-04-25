package com.kivitool.theweatherchannel2020.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kivitool.theweatherchannel2020.adapter.uix.BarColorAdapter;
import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.database.DatabaseHandler;
import com.kivitool.theweatherchannel2020.services.location.PlaceAutocompleteAdapter;

public class NewLocationActivity extends AppCompatActivity {

    private Context context;
    private Window window;
    private BarColorAdapter colorAdapter;
    private ImageView backL, search;
    private EditText edtSearchLocation;
    private DatabaseHandler databaseHandler;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);

        context = this;

        databaseHandler = new DatabaseHandler(context);

        window = this.getWindow();
        window.setNavigationBarColor(R.color.red);

        backL = findViewById(R.id.back_allow);
        search = findViewById(R.id.searchLocation);
        edtSearchLocation = findViewById(R.id.add_location_new);

        backL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,HomeActivity.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setEdtSearchLocationDatabaseSave();
            }
        });

    }

    private void setEdtSearchLocationDatabaseSave(){

        String editLocation = edtSearchLocation.getText().toString().trim();

        Boolean check = databaseHandler.checkIsRowExists(editLocation);

        if (check==true){
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
            finish();
        }else {
            databaseHandler.InsertColumn(editLocation);
            Intent intent = new Intent(context,HomeActivity.class);
            intent.putExtra("editKeys",editLocation);
            startActivity(intent);
            finish();
        }

    }

}