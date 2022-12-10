package ru.anbn.mhz;

import static android.os.Environment.getExternalStorageDirectory;

import static java.lang.Thread.sleep;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
1. Необходимо приостанавливать процесс выполнения обновления в случае прерывания по таймеру.

 */

public class MainActivity extends AppCompatActivity {
    // массив для дальнейшего заполнения найденными позициями
    public ArrayList listCardArray = new ArrayList();

    private static boolean update = true;

    private static final String FILE_PATH_LOCAL = "content.txt";

    // путь к файлу на google drive
    private static final String FILE_PATH_GOOGLE_DISK = "https://drive.google.com/uc?export=" +
            "download&confirm=no_antivirus&id=1tbWt0VaBAuhfBcD62ssK7CpeY8dG4fuy";

    // путь к файлу на external drive
    private static final String FILE_PATH_EXTERNAL = getExternalStorageDirectory().
            getAbsolutePath() + "/Download/mhz.txt";

    private File filePathExternal = new File(FILE_PATH_EXTERNAL);
    private File filePathLocal = new File(FILE_PATH_LOCAL);

    // счетчик для числа переходов
    private int count;
    private int seconds = 30;

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

    public void onClickButton2(View view) throws InterruptedException {
        fileSynchronization();
    }


    public void button1() {
//        System.out.println("11111");
//        File file = new File(getExternalFilesDir(null), "Dummy");
//        //checking if android version is equal and greater than noughat
//        //now if download complete file not visible now lets show it
//        DownloadManager.Request request = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            request = new DownloadManager.Request(Uri.parse(FILE_PATH_GOOGLE_DISK))
//                    .setTitle("Dummy")
//                    .setDescription("Downloading")
//                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                    .setDestinationUri(Uri.fromFile(file))
//                    .setRequiresCharging(false)
//                    .setAllowedOverMetered(true)
//                    .setAllowedOverRoaming(true);
//        } else {
//            request = new DownloadManager.Request(Uri.parse(FILE_PATH_GOOGLE_DISK))
//                    .setTitle("Dummy")
//                    .setDescription("Downloading")
//                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                    .setDestinationUri(Uri.fromFile(file))
//                    .setAllowedOverRoaming(true);
//        }
//
//        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//        downloadId = downloadManager.enqueue(request);
//        System.out.println("22222");

        File file = new File(getExternalFilesDir(null), "mhz_data.txt");


        DownloadManager.Request request = null;
        request = new DownloadManager.Request(Uri.parse(FILE_PATH_GOOGLE_DISK))
                .setTitle("database_mhz")
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.fromFile(file))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);


        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request);


        System.out.println("22222");
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3333333333");

        file.delete();
    }

    //now checking if download complete

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("eeeeeeeee");
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadId == id) {
                Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }
    };


    //now checking if download complete
/*
    private BroadcastReceiver onDownloadComplete=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            System.out.println("33333");
            if(downloadId==id){
                Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }
    };

 */

    /* алгоритм работы метода актуализации данных следующий:
       сохраняем файл с google disk в папку download
       там его последовательно считывая записываем в служебную директорию программы
       для дальнейшего использования. После считывания удаляем скачанный файл
       /Download/mhz_data.txt из External хранилища
     */
    private void fileSynchronization() throws InterruptedException {
        // проверим что обновление еще не выполнялось
        if (!update) {
            System.out.println("Обновление уже выполнялось");
            return;
        }

        // при наличии файла /Download/mhz_data.txt удаляем его
        deleteFile(filePathExternal);
        System.out.println("111111");
        pauseWhenLoading();
        /*
        // ожидаем удаления файла (установим таймер 60 сек)
        count = seconds;
        while (filePathExternal.exists() && count > 0) {
            pauseWhenLoading();
            count--;
        }
*/
        // если по истичении таймера 60 сек файл все еще существует выдаем ошибку
        if (filePathExternal.exists()) {
            Toast.makeText(this, "01 File /Download/mhz_data.txt not deleted!",
                    Toast.LENGTH_LONG).show();
        }


        EditText editText1 = findViewById(R.id.editText1);
        EditText editText2 = findViewById(R.id.editText2);

        /*
        // загружаем файл с google disk
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(FILE_PATH_GOOGLE_DISK));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download...");
        request.setDescription("File is download...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE |
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationUri(Uri.fromFile((File) filePathExternal));       // !!!!!!!!!
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

         */
        //pauseWhenLoading();
        pauseWhenLoading();
        System.out.println("222222");
        /*
        // ожидаем загрузку файла mhz.txt (установим таймер 60 секунд)
        count = seconds;
        while (!(filePathExternal.exists()) && count > 0) {
            pauseWhenLoading();
            count--;
        }
         */

        File file = new File(getExternalFilesDir(null), "mhz_data.txt");
        //checking if android version is equal and greater than noughat
        //now if download complete file not visible now lets show it
        DownloadManager.Request request = null;
        request = new DownloadManager.Request(Uri.parse(FILE_PATH_GOOGLE_DISK))
                .setTitle("database_mhz")
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.fromFile(file))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);


        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request);
        System.out.println("22222");


        if (!(filePathExternal).exists()) {
            Toast.makeText(this, "02 File not uploaded. Timer = " + count, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "03 File download. Timer = " + count, Toast.LENGTH_LONG).show();
        }


        /* удалим внутренний файл context.txt при его наличии
           необходимо для создания нового файла content.txt из нового
         */
        deleteFile(filePathLocal);
        System.out.println("333333");

        pauseWhenLoading();
        /*
        // ожидаем удаления файла (установим таймер 60 сек)
        count = seconds;
        while (filePathLocal.exists() && count > 0) {
            pauseWhenLoading();
            count--;
        }
*/
        // если по истичении таймера 60 сек файл все еще существует выдаем ошибку
        if (filePathLocal.exists()) {
            Toast.makeText(this, "04 File content.txt not deleted!",
                    Toast.LENGTH_LONG).show();
        }


        // READER EXTERNAL DOWNLOAD
        try {
            // построчно читаем файл /Download/mhz_data.txt и записываем данные в локальный
            FileInputStream inputStream = new FileInputStream((File) filePathExternal);   //!!!!!!!!

            // stream для записи файла
            FileOutputStream fos = null;
            fos = openFileOutput(FILE_PATH_LOCAL, MODE_PRIVATE);

            // буфферезируем данные из выходного потока файла
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // Класс для создания строк из последовательностей символов
            //StringBuilder stringBuilder = new StringBuilder();


            String line;
            String s = null;
            int count = 0;
            try {
                /* Производим построчное считывание данных из файла также построчно
                 * записываем это во внутренний файл приложения
                 */
                while ((line = bufferedReader.readLine()) != null) {
                    //stringBuilder.append(line);
                    fos.write(line.getBytes());
                    s = line;
                    count++;
                }
                Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
                editText1.setText(count + " " + s);

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fos.close();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        // при наличии файла /Download/mhz_data.txt удаляем его
        deleteFile(filePathExternal);
        System.out.println("44444");
        pauseWhenLoading();
        /*
        // ожидаем удаления файла
        count = seconds;
        while (filePathExternal.exists() && count > 0) {
            pauseWhenLoading();
            count--;
        }
*/
        System.out.println("555555");
        if (filePathExternal.exists()) {
            Toast.makeText(this, "05 File not deleted!", Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, "06 The code is executed", Toast.LENGTH_LONG).show();

        // изменим значение update для исключения повторного запуска обновления в одной сессии
        update = false;
        System.out.println("555555");
    }

    // при наличии файла /Download/mhz_data.txt удаляем его
    public static void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    // наличие паузы при загрузке и считывании файла
    public static void pauseWhenLoading() throws InterruptedException {
        //sleep(20000);
    }


}