package ru.anbn.mhz;

import static android.os.Environment.getExternalStorageDirectory;
import static java.lang.Thread.sleep;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

    private static String FILE_NAME = "content.txt";

    // путь к файлу на google drive
    private String link = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1u9-qgZsTMSruygixHCJZvLbtlG2QWWmS";
    // путь к файлу на external drive
    private String filePath = getExternalStorageDirectory().getAbsolutePath() + "/Download/mhz_data.txt";

    private File file = new File(filePath);

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
        // openText();
        saveFile();

    }

    // сохранение файла
    public void button1() {

        EditText editText2 = findViewById(R.id.editText2);

        // удаляем загруженный файл из папки download
        if (file.exists()) {
            file.delete();
            editText2.setText("true");
        } else {
            editText2.setText("false");
        }


        /*
        FileOutputStream fos = null;
        try {
            EditText editText1 = findViewById(R.id.editText1);
            String text = editText1.getText().toString();

            fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(text.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        */

    }

    // открытие файла
    public void openText() {
        FILE_NAME = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1u9-qgZsTMSruygixHCJZvLbtlG2QWWmS";
        FileInputStream fin = null;
        EditText editText2 = findViewById(R.id.editText2);
        try {
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            editText2.setText(text);
        } catch (IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {

            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    // скопируем файл из сети
    // https://drive.google.com/file/d/1u9-qgZsTMSruygixHCJZvLbtlG2QWWmS/view?usp=sharing
    private void saveFile() {
        EditText editText1 = findViewById(R.id.editText1);
        EditText editText2 = findViewById(R.id.editText2);

        // загружаем файл с google disk
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(link));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Download...");
        request.setDescription("File is download...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationUri(Uri.fromFile(file));
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        // Выводим сообщение об успешной загрузке
        Toast.makeText(this, "File uploaded successfully!", Toast.LENGTH_SHORT).show();




        try {
            String text = "13131313";
            File myFile = new File(Environment.getExternalStorageDirectory().toString() + "/Download/" + "123.txt");
            /*
             * Создается объект файла, при этом путь к файлу находиться методом класcа Environment
             * Обращение идёт, как и было сказано выше к внешнему накопителю
             */
            myFile.createNewFile();                                         // Создается файл, если он не был создан
            FileOutputStream outputStream = new FileOutputStream(myFile);   // После чего создаем поток для записи
            outputStream.write(text.getBytes());                            // и производим непосредственно запись
            outputStream.close();
            /*
             * Вызов сообщения Toast не относится к теме.
             * Просто для удобства визуального контроля исполнения метода в приложении
             */
            //Toast.makeText(this, R.string.write\_done, Toast.LENGTH\_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
         * Аналогично создается объект файла
         */
        try {
            File myFile = new File(Environment.getExternalStorageDirectory().toString() + "/Download/" + "123.txt");
            FileInputStream inputStream = new FileInputStream(myFile);
            /*
             * Буфферезируем данные из выходного потока файла
             */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            /*
             * Класс для создания строк из последовательностей символов
             */
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                /*
                 * Производим построчное считывание данных из файла в конструктор строки,
                 * Псоле того, как данные закончились, производим вывод текста в TextView
                 */
                /*
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                 */
                line = bufferedReader.readLine();
                stringBuilder.append(line);
                editText1.setText(stringBuilder);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        // установим таймер перед удалением файла
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // удаляем загруженный файл из папки download
        if (file.exists()) {
            file.delete();
            editText2.setText("true");
        } else {
            editText2.setText("false");
        }





    }

}