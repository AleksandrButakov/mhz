package ru.anbn.mhz;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// extends AppCompatActivity необходим для загрузки файлов
public class TwoActivity extends AppCompatActivity {

    private ImageView imageView;
    private int currentPage = 0;
    private Button next, previous;

    private static final int WRITE_EXTERNAL_STORAGE = 1;
    // private String link = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=" +
    // "1c6v46vgYnRSR4n54veKgC4oAVusmpjlz";

    // нарисуем экран
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        // для стрелки назад в ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    // для стрелки назад в ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // для стрелки назад в ActionBar
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void onClickButtonTwo(View v) {
        //Intent intent = new  Intent(this, MainActivity.class);
        //startActivity(MainActivity.class);
        onBackPressed();// возврат на предыдущий activity
    }
    //"#FF9800"

    // дальнейший фрагмент выполняет загрузку файла по нажатию кнопки Download
    public void onClickButtonDownload(View v) {
        //
        Toast.makeText(this, "Функционал находится в разработке...",
                Toast.LENGTH_SHORT).show();
        //DefineVersion();
    }


}