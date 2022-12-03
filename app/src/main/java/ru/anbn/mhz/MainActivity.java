package ru.anbn.mhz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // запретим ночную тему
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        // зададим идентификаторы полям spinner
        final Spinner spinner_road = findViewById(R.id.spinner_road);
        final Spinner spinner_region = findViewById(R.id.spinner_region);
        final Spinner spinner_station = findViewById(R.id.spinner_station);


        String[] s1 = {"Свердловская железная дорога"};

        // адаптер для spinner1 со списком оборудования
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.
                R.layout.simple_spinner_item, s1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_road.setAdapter(adapter);

    }


}