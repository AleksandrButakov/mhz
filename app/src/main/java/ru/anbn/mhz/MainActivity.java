package ru.anbn.mhz;

import static java.lang.Thread.sleep;
import static ru.anbn.mhz.StaticVariables.FILE_PATH_GOOGLE_DISK_DATA;
import static ru.anbn.mhz.StaticVariables.FILE_PATH_GOOGLE_DISK_VERSION;
import static ru.anbn.mhz.StaticVariables.FILE_PATH_LOCAL_DATA;
import static ru.anbn.mhz.StaticVariables.FILE_PATH_LOCAL_VERSION;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
1. Необходимо приостанавливать процесс выполнения обновления в случае прерывания по таймеру.

 */

public class MainActivity extends AppCompatActivity {

    // счетчик для числа переходов
    private int count;
    private int timerSeconds = 120;

    private long downloadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // запретим ночную тему
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

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
        if (id == R.id.manual) {
            Variables.setUrl("https://AleksandrButakov.github.io/Pinout/PolicyPrivacy/");
            Intent intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        }

        // политика конфиденциальности
        if (id == R.id.privacy) {
            Variables.setUrl("https://AleksandrButakov.github.io/Pinout/PolicyPrivacy/");
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
            Variables.setUrl("https://AleksandrButakov.github.io/Pinout/About/");
            Intent intent = new Intent(this, WebViewActivity.class);
            startActivity(intent);
        }

        return true;
    }

    public void onClickButton1(View view) {
        button1();

    }

    public void onClickButton2(View view) throws IOException {

        // модуль чтения файла mhz_data.csv с сохранением данных в ArrayList
        File fileLocalData = new File(getExternalFilesDir(null), FILE_PATH_LOCAL_DATA);

        // определим количество строк в файле
        count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocalData))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // объявим размерность массива в соответствии с размером файла
        String[][] sData = new String[count][4];

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
        for (int i = 0; i < count; i++) {
            System.out.println(sData[i][0] + sData[i][1] + sData[i][2] + sData[i][3]);
        }

    }


    public void button1() {
        /* проверка наличия подключения к интернету и в случае отсутствия
           интернета прерываем программу */
        if (!isOnline()) {
            return;
        }

        // проверим что локальные файлы mhz_data.txt и version.txt существуют
        File fileLocalVersion = new File(getExternalFilesDir(null), FILE_PATH_LOCAL_VERSION);
        File fileLocalData = new File(getExternalFilesDir(null), FILE_PATH_LOCAL_DATA);

        if (fileLocalVersion.exists() && fileLocalData.exists()) {
            // проверим актуальность локальных данных

        } else {
            // загрузка файлов mhz_data.txt и version.txt
            downloadFile(FILE_PATH_GOOGLE_DISK_VERSION, FILE_PATH_LOCAL_VERSION);
            downloadFile(FILE_PATH_GOOGLE_DISK_DATA, FILE_PATH_LOCAL_DATA);

        }

        String s = "";

        EditText editText1 = findViewById(R.id.editText1);


        try (BufferedReader br = new BufferedReader(new FileReader(fileLocalVersion))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Line = " + s);
        Toast.makeText(this, "Line = " + s, Toast.LENGTH_SHORT).show();

        /*
        System.out.println("Line = " + i);
        Toast.makeText(this, "Line = " + i, Toast.LENGTH_SHORT).show();


        editText1.setText("Line = " + i);
        */

        /* Создаем массивы для заполнения данными

         */

        System.out.println("7777777");
        // fileLocalVersion.delete();
        System.out.println("888888888");

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
        count = timerSeconds;
        while (file.exists() && count > 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
        }

        System.out.println("1111111" + "count = " + count);

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

        System.out.println("22222");

        // ожидание загрузки файла
        count = timerSeconds;
        while (!file.exists() && count > 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
        }

        System.out.println("3333333333" + "count = " + count);

    }


    /* алгоритм работы метода актуализации данных следующий:
       сохраняем файл с google disk в папку download
       там его последовательно считывая записываем в служебную директорию программы
       для дальнейшего использования. После считывания удаляем скачанный файл
       /Download/mhz_data.txt из External хранилища
     */


}