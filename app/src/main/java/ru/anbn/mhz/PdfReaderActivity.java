package ru.anbn.mhz;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

// extends AppCompatActivity необходим для загрузки файлов
public class PdfReaderActivity extends AppCompatActivity {

    String path;

    // нарисуем экран
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);

        // для стрелки назад в ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // pdf Viewer
        PDFView pdfView = findViewById(R.id.pdfView);



        // выведем выбранную станцию и частоту на экран
        TextView textView = findViewById(R.id.textView1);
        if (!MainActivity.choiceFrequency.equals("")) {
            // станция выбрана
            textView.setText(MainActivity.choiceFrequency);
        } //else {
//            // станция не выбрана
//            textView.setText("Выберите станцию для отображения частоты");
//        }

        // найдем путь к файлу
        path = MainActivity.fileName;

        pdfView.fromAsset(path)
                //.pages(35) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false) // render annotations (such as comments,
                // colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                // screen
                .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                // scaled relative to largest page.
                .load();
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

//    public void onClickButtonTwo(View v) {
//        //Intent intent = new  Intent(this, MainActivity.class);
//        //startActivity(MainActivity.class);
//        onBackPressed();// возврат на предыдущий activity
//    }
    //"#FF9800"

    // дальнейший фрагмент выполняет загрузку файла по нажатию кнопки Download
//    public void onClickButtonDownload(View v) {
//        //
//        Toast.makeText(this, "Функционал находится в разработке...",
//                Toast.LENGTH_SHORT).show();
//        //DefineVersion();
//    }


}
