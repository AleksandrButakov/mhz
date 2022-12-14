package ru.anbn.mhz;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;
import static ru.anbn.mhz.StaticVariables.FILE_PATH_GOOGLE_DISK_DATA;
import static ru.anbn.mhz.StaticVariables.FILE_PATH_LOCAL_DATA;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
1. Необходимо приостанавливать процесс выполнения обновления в случае прерывания по таймеру.

 */

public class MainActivity extends AppCompatActivity {
    // количество строк в файле mhz_data.txt
    private int countRows;
    private String[][] sData = null;

    // счетчик для числа переходов
    private int countSleep;
    private int timerSeconds = 120;

    // id загрузки файла в менеджере
    private long downloadId;

    // массив для дальнейшего заполнения найденными позициями при поиске станций
    private ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
    private ArrayList<String> stringArrayList = new ArrayList<String>();


    // в эти переменные запишем строки приведенные к верхнему регистру
    private String sArrayUpper, sSearchUpper;

    // вспомогательная текстовая переменная
    private String sTemp;

    // номер выбранной позиции в ListView
    private int number;

    // переменная хранит состояние поиска
    private boolean bSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // запретим ночную тему
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // поиск содержимого по строке введенной в searchView
        // зададим идентификаторы полю searchView
        // создадим listner searchView1
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // обработчик нажатия кнопки поиска поля searchView1
            @Override
            public boolean onQueryTextSubmit(String query) {
                // User pressed the search button
                return false;
            }

            // обработчик ввода символа поля searchView
            @Override
            public boolean onQueryTextChange(String sSearch) {
                integerArrayList.clear();
                stringArrayList.clear();
                sSearchUpper = sSearch.toUpperCase();
                bSearch = false;

                // проверим что длина введенной строки более 2х символов, тогда поиск совпадений
                if (sSearch.length() > 1) {

                    for (int i = 2; i < countRows; i++) {
                        sArrayUpper = sData[i][2].toUpperCase();
                        // проверим вхождение искомой строки в название станции в массиве
                        if (sArrayUpper.indexOf(sSearchUpper) != -1) {
                            /* сохраним индекс позиции с соответствием текста
                               в дальнейшем по этим индексам будем выводить информацию */
                            integerArrayList.add(i);
                            sTemp = sData[i][0] + "  " + sData[i][1] + "  " + sData[i][2];
                            stringArrayList.add(sTemp);

                            bSearch = true;
                        }
                    }

                }

                // проверим успешный ли был поиск
                if (bSearch == false) {
                    // поиск не дал результата, очистим ListView от информации
                    // после предыдущего поиска
                    listViewClear();

                } else {
                    // поиск успешен, делаем textView invisible и выводим результаты в listView
                    textViewInvisible();
                    searchResultsDisplay();
                }

                // listener ListView слушает клики. При выборе позиции закрываем listView
                // и отображаем данные в формате дорога, регион, станция, частота

                return false;
            }

        });


        // метод прослушивания нажатий на ListView (выбор нужной позиции и отображение описания)
        // отображение происходит в классе TwoActivity.java
        ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                number = 0;
                // используем position для получения номера пункта по которому кликнул пользователь
                number = integerArrayList.get(position);


                // прячем клавиатуру. butCalculate - это кнопка
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                // отобразим выбранную позицию
                displayTheSelectedPositionListView();

            }

        });

    }


    // делаем textView inbisible and clear
    public void textViewInvisible() {
        TextView textView1 = findViewById(R.id.textView1);

        textView1.setVisibility(View.INVISIBLE);

    }


    // отображение выбранной в ListView позиции
    public void displayTheSelectedPositionListView() {
        // делаем ListView невидимым
        listViewInvisible();
        // Заполняем TextView5, 6 ,7 8 данными
        TextView textView1 = findViewById(R.id.textView1);

        sTemp = "\n" + "    Дорога:    " + sData[number][0] + "\n" + "    Регион:    " +
                sData[number][1] + "\n" + "    Станция:  " + sData[number][2] + "\n" + "    Частота:  " + sData[number][3] + " МГц" + "\n";

        textView1.setText(sTemp);

        // делаем textView visible
        textView1.setVisibility(View.VISIBLE);

    }

    // сделаем ListView visible = false
    public void listViewInvisible() {
        ListView listView = findViewById(R.id.listView);
        listView.setVisibility(View.INVISIBLE);
    }


    // очистка содержимого в ListView
    public void listViewClear() {
        // получаем экземпляр элемента ListView
        ListView listView = findViewById(R.id.listView);
        // очистим listArray для дальнейшей очистки массива
        stringArrayList.clear();
        // используем адаптер данных
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this, R.layout.my_list_item, stringArrayList);

        listView.setAdapter(adapter);
    }


    // вывод результатов поиска в ListView
    public void searchResultsDisplay() {
        // получаем экземпляр элемента ListView
        ListView listView = findViewById(R.id.listView);
        listView.setVisibility(View.VISIBLE);
        // используем адаптер данных
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this, R.layout.my_list_item, stringArrayList);
        listView.setAdapter(adapter);
        // android.R.layout.simple_list_item_1
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        /* при запуске приложения проверим наличие файла с данными, в случае необходимости
           выполним загрузку и заполним массив для дальнейшей работы приложения */
        try {
            downloadAndReadFileData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
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

        // инструкция пользователя
        if (id == R.id.manual) {
            Variables.setUrl("https://AleksandrButakov.github.io/Pinout/PolicyPrivacy/");
            Intent intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        }

        // политика конфиденциальности
        if (id == R.id.privacy) {
            Variables.setUrl("https://AleksandrButakov.github.io/MHz/PolicyPrivacy/");
            Intent intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        }

        // оценить приложение
        if (id == R.id.estimate) {
            Toast.makeText(this, "Данный раздел станет доступен после публикации " +
                    "приложения в Google Play", Toast.LENGTH_SHORT).show();
            /*
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.anbn.pinout"));
            startActivity(intent);
             */
        }

        // о программе
        if (id == R.id.about) {
            Variables.setUrl("https://AleksandrButakov.github.io/MHz/About/");
            Intent intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        }

        return true;
    }

    // клик по кнопре для отображения инструкции
    public void onButtonClickShowInstruction(View view) throws IOException {
        /* проверяем что выбрана станция и модель оборудования
           отображаем layout с инструкцией pdf
           в случае если станция не выбрана, отображаем инструкцию и пишем что
           необходимо выбрать станцию;
           если выбрана, отображаем и частоту и инструкцию */


        // переходим к ActivityTwo
        Intent intent = new Intent(this, TwoActivity.class);
        startActivity(intent);


    }




    // основной алгоритм проверки наличия файл данных и его чтения
    private void downloadAndReadFileData() throws IOException {

        /* проверка наличия подключения к интернету и в случае отсутствия
           интернета прерываем программу */
        if (!isOnline()) {
            return;
        }

        // проверим что локальные файлы mhz_data.txt и version.txt существуют
        File fileLocalData = new File(getExternalFilesDir(null), FILE_PATH_LOCAL_DATA);

        // проверим что файл существует
        if (!fileLocalData.exists()) {
            // загрузка файлов mhz_data.csv
            downloadFile(FILE_PATH_GOOGLE_DISK_DATA, FILE_PATH_LOCAL_DATA);
        }


        // определим количество строк в файле
        countRows = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocalData))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                countRows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // зададим размерность массива в соответствии с размером файла
        sData = new String[countRows][4];

        // открываем файл
        BufferedReader reader = new BufferedReader(new FileReader(fileLocalData));
        // считываем построчно
        String line = null;
        Scanner scanner = null;
        int id = 0;
        int index = 0;

        // заполняем массив данными из файла mhz_data.csv
        String data;
        String version;
        while ((line = reader.readLine()) != null) {
            //Frequency frequency = new Frequency();
            scanner = new Scanner(line);
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                // заполнение данными массива
                data = scanner.next();
                if (index == 0)
                    sData[id][index] = data;
                else if (index == 1)
                    sData[id][index] = data;
                else if (index == 2)
                    sData[id][index] = data;
                else if (index == 3)
                    sData[id][index] = data;
                else
                    System.out.println("Некорректные данные: " + data);
                index++;
            }
            id++;
            index = 0;

        }
        //закрываем reader
        reader.close();

        // выводим данные в консоль
        for (int i = 0; i < countRows; i++) {
            System.out.println(sData[i][0] + sData[i][1] + sData[i][2] + sData[i][3]);
        }

        /* на этом шаге файл загружен или произведена проверка того что файл существовал,
           двумерный массив заполнен данными. Ожидаем ввода требуемой станции в поле поиска
           и после ввода второго символа обновляем все используемые в данном блоке переменные,
           производим поиск совпадений с заполнением индексов в listStationArray.
         */


    }


    // проверка наличия подключения к интернету
    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }

    // загрузка файла из google drive на смартфон
    private void downloadFile(String pathServerFile, String pathLocalFile) {
        File file = new File(getExternalFilesDir(null), pathLocalFile);

        // если такой файл уже существует то перед загрузкой новой версии удалим его
        if (file.exists()) {
            file.delete();
        }

        // ожидаем уделение файла
        countSleep = timerSeconds;
        while (file.exists() && countSleep > 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countSleep--;
        }

        // загрузка файла
        DownloadManager.Request request_version = null;
        request_version = new DownloadManager.Request(Uri.parse(pathServerFile))
                .setTitle(pathLocalFile)
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.fromFile(file))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request_version);

        // ожидание загрузки файла
        countSleep = timerSeconds;
        while (!file.exists() && countSleep > 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countSleep--;
        }

    }


}