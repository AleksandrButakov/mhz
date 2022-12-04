package ru.anbn.mhz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String url;

    /* алгоритм работает следующим образом. Кликаем в TextView, возвращаем номер выбранной строки.
    По номеру строки находим в массиве индексы оборудования и платы, описание которого и выводим
    в pdfViewer.
     */

    // массив для дальнейшего заполнения найденными позициями
    public ArrayList listCardArray = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // запретим ночную тему
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        // зададим идентификаторы полям spinner
//        final Spinner spinner_road = findViewById(R.id.spinner_road);
//        final Spinner spinner_region = findViewById(R.id.spinner_region);
//        final Spinner spinner_station = findViewById(R.id.spinner_station);


        String[] sRoad = {
                "СВЖД",
                "ДВЖД",
                "ЗСЖД",
                "МСЖД",
                "СКЖД"
        };

        String[] sRegion = {
                "Пермский регион",
                "Свердловский регион",
                "Нижнетагильский регион",
                "Сургутский регион",
                "Тюменский регион"
        };

        String[] sStation = {
                "Демьянка",
                "Салым",
                "Пыть-Ях",
                "Усть-Юган",
                "Сургут",
                "Ульт-Ягун",
                "Лангепас",
                "Мегион",
                "Нижневартовск-2",
                "Нижневартовск-1",
                "Когалым",
                "Ноябрьск-1",
                "Ноябрьск-2",
                "Ханымей",
                "Пурпе",
                "Пуровск",
                "Сывдарма",
                "Коротчаево",
                "Н.Уренгой"
        };

        // адаптер для spinner_road со списком дорог
//        final ArrayAdapter<String> adapter_road = new ArrayAdapter<String>(this, android.
//                R.layout.simple_spinner_item, sRoad);
//        adapter_road.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_road.setAdapter(adapter_road);
//
//        // адаптер для spinner_region со списком регионов
//        final ArrayAdapter<String> adapter_region = new ArrayAdapter<String>(this, android.
//                R.layout.simple_spinner_item, sRegion);
//        adapter_region.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_region.setAdapter(adapter_region);
//
//        // адаптер для spinner_station со списком станций
//        final ArrayAdapter<String> adapter_station = new ArrayAdapter<String>(this, android.
//                R.layout.simple_spinner_item, sStation);
//        adapter_station.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_station.setAdapter(adapter_station);

    }

    // нарисуем меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    // обработчик нажатия позиций меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // строка поиска
        if (id == R.id.action_search) {
            Intent intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        }

        /*
        // политика конфиденциальности
        if (id == R.id.privacy) {
            url = "https://AleksandrButakov.github.io/Pinout/PolicyPrivacy/";
            Intent intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        }

        // оценить приложение
        if (id == R.id.estimate) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.anbn.pinout"));
            startActivity(intent);
        }

        // о программе
        if (id == R.id.about) {
            url = "https://AleksandrButakov.github.io/Pinout/About/";
            Intent intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        }

        return true;
    }




    // очистка содержимого в ListView
    public void ListViewClear() {
        // получаем экземпляр элемента ListView
        ListView listView = findViewById(R.id.listView);
        // очистим listArray для дальнейшей очистки массива
        listCardArray.clear();
        // используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listCardArray);
        listView.setAdapter(adapter);
    }

    // вывод результатов поиска в ListView
    public void SearchResultsDisplay() {
        // получаем экземпляр элемента ListView
        ListView listView = findViewById(R.id.listView);
        // используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listCardArray);
        listView.setAdapter(adapter);
    }
}