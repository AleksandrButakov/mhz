package ru.anbn.mhz;

import static android.os.Environment.getExternalStorageDirectory;

import static java.lang.Thread.sleep;

import android.app.DownloadManager;
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

public class MainActivity extends AppCompatActivity {
    // массив для дальнейшего заполнения найденными позициями
    public ArrayList listCardArray = new ArrayList();

    private static final String FILE_PATH_LOCAL = "content.txt";

    // путь к файлу на google drive
    private static final String FILE_PATH_GOOGLE_DISK =
            "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=" +
                    "1lrK0nrZGsxdawt5DidHASyfiJ5QtwST4";
    // путь к файлу на external drive
    private static final String FILE_PATH_EXTERNAL = getExternalStorageDirectory().
            getAbsolutePath() + "/Download/mhz_data.txt";

    private File filePathExternal = new File(FILE_PATH_EXTERNAL);
    private File filePathLocal = new File(FILE_PATH_LOCAL);

    // счетчик для числа переходов
    private int count;

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


    }


    /* алгоритм работы метода актуализации данных следующий:
       сохраняем файл с google disk в папку download
       там его последовательно считывая записываем в служебную директорию программы
       для дальнейшего использования. После считывания удаляем скачанный файл
       /Download/mhz_data.txt из External хранилища
     */

    private void fileSynchronization() throws InterruptedException {
        // при наличии файла /Download/mhz_data.txt удаляем его
        deleteFile(filePathExternal);

        // ожидаем удаления файла (установим таймер 60 сек)
        count = 30;
        while (filePathExternal.exists() && count > 0) {
            pauseWhenLoading();
            count--;
        }

        // если по истичении таймера 90 сек файл все еще существует выдаем ошибку
        if (filePathExternal.exists()) {
            Toast.makeText(this, "File not deleted!", Toast.LENGTH_LONG).show();
        }

        EditText editText1 = findViewById(R.id.editText1);
        EditText editText2 = findViewById(R.id.editText2);

        // загружаем файл с google disk
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(FILE_PATH_GOOGLE_DISK));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Download...");
        request.setDescription("File is download...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE |
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationUri(Uri.fromFile(filePathExternal));
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        // Выводим сообщение об успешной загрузке
        Toast.makeText(this, "File uploaded successfully!",
                Toast.LENGTH_SHORT).show();


        // ожидаем загрузку файла mhz.txt
        count = 10;
        while (!filePathExternal.exists() && count > 0) {
            pauseWhenLoading();
            count--;
        }
        Toast.makeText(this, "File donnload. Timer = " + count, Toast.LENGTH_LONG).show();

        /* удалим внутренний файл context.txt при его наличии
           необходимо для создания нового файла content.txt из нового
         */
        deleteFile(filePathLocal);

        // READER EXTERNAL DOWNLOAD
        try {
            // построчно читаем файл /Download/mhz_data.txt и записываем данные в локальный
            FileInputStream inputStream = new FileInputStream(filePathExternal);

            // stream для записи файла
            FileOutputStream fos = null;
            fos = openFileOutput(FILE_PATH_LOCAL, MODE_PRIVATE);

            // буфферезируем данные из выходного потока файла
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // Класс для создания строк из последовательностей символов
            //StringBuilder stringBuilder = new StringBuilder();
            String line;
            String s = "111";
            try {
                /* Производим построчное считывание данных из файла также построчно
                 * записываем это во внутренний файл приложения
                 */
                while ((line = bufferedReader.readLine()) != null) {
                    //stringBuilder.append(line);
                    fos.write(line.getBytes());
                    s = line;
                }
                Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
                editText1.setText(s);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.close();
                } catch (IOException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




        /*
        // записываем как внутренний файл приложения
    // сохранение файла
    public void saveText(View view){

        FileOutputStream fos = null;
        try {
            EditText textBox = findViewById(R.id.editor);
            String text = textBox.getText().toString();

            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


         */


        // при наличии файла /Download/mhz_data.txt удаляем его
        deleteFile(filePathExternal);

        // ожидаем удаления файла
        count = 3;
        while (filePathExternal.exists() && count > 0) {
            pauseWhenLoading();
            count--;
        }

        if (filePathExternal.exists()) {
            Toast.makeText(this, "File not deleted!", Toast.LENGTH_LONG).show();
        }

    }

    // при наличии файла /Download/mhz_data.txt удаляем его
    public static void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    // наличие паузы при загрузке и считывании файла
    public static void pauseWhenLoading() throws InterruptedException {
        sleep(1000);
    }


}