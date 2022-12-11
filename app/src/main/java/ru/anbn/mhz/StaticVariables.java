package ru.anbn.mhz;

import static android.os.Environment.getExternalStorageDirectory;

import java.util.ArrayList;

public class StaticVariables {

    // массив для дальнейшего заполнения найденными позициями
    protected ArrayList listCardArray = new ArrayList();

    protected static boolean update = true;

    protected static final String FILE_PATH_LOCAL_VERSION = "version.txt";
    protected static final String FILE_PATH_LOCAL_DATA = "mhz_data.txt";

    // путь к файлу version.txt на google drive
    protected static final String FILE_PATH_GOOGLE_DISK_VERSION = "https://drive.google.com/uc?export=" +
            "download&confirm=no_antivirus&id=1o7fiLYOSHK175bwU6pbe0xA_MmyXoZvZ";

    // путь к файлу mhz_data.txt на google drive
    protected static final String FILE_PATH_GOOGLE_DISK_DATA = "https://drive.google.com/uc?export=" +
            "download&confirm=no_antivirus&id=1tbWt0VaBAuhfBcD62ssK7CpeY8dG4fuy";

    // путь к файлу на external drive
    protected static final String FILE_PATH_EXTERNAL = getExternalStorageDirectory().
            getAbsolutePath() + "/Download/mhz.txt";

}
