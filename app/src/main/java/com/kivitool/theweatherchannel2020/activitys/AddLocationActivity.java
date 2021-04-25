package com.kivitool.theweatherchannel2020.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kivitool.theweatherchannel2020.R;
import com.kivitool.theweatherchannel2020.database.DatabaseHandler;
import com.kivitool.theweatherchannel2020.services.location.Constants;
import com.kivitool.theweatherchannel2020.utils.PreferenceManagers;

import es.dmoral.toasty.Toasty;

public class AddLocationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Context context;
    private Window window;
    private EditText location_add;
    private Button addLocation;
    private Spinner language_spin;
    private DatabaseHandler databaseHandler;
    private String city_data;
    private PreferenceManagers preferenceManagers;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        context = this;

        preferenceManagers = new PreferenceManagers(context);

        window = this.getWindow();
        window.setNavigationBarColor(R.color.red);

        databaseHandler = new DatabaseHandler(context);

        location_add = findViewById(R.id.location_add);
        addLocation = findViewById(R.id.btnAddLocation);
        language_spin = findViewById(R.id.location_language);

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(

                context,
                R.array.languages,
                R.layout.spinner_layout_color

        );

        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        language_spin.setAdapter(arrayAdapter);
        language_spin.setOnItemSelectedListener(this);

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (location_add.getText().toString().length() > 0) {

                    city_data = location_add.getText().toString();


                    long res = databaseHandler.InsertColumn(city_data);

                    if (res > 0) {

                        Intent intent = new Intent(context, HomeActivity.class);
                        databaseHandler.InsertColumn(location_add.getText().toString());
                        intent.putExtra("q", city_data);
                        intent.putExtra("lang", String.valueOf(language_spin.getSelectedItem()));
                        dataPrefsSave();
                        startActivity(intent);
                        finish();

                    } else {
                        toastMessage("You data no insert column !");
                    }
                } else {

                    Toasty.info(context, "Your location is empty !", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean storageData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean dataSave = pref.getBoolean("mmXl", false);
        return dataSave;

    }

    private void dataPrefsSave() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("mmXl", true);
        editor.commit();

    }

    private void AddData(String entry) {

        long insertData = databaseHandler.InsertColumn(entry);

        if (insertData > 0) {
            toastMessage("Location Successfully Inserted !");
        } else {
            toastMessage("Something with wrong !");
        }


    }

    private void toastMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}