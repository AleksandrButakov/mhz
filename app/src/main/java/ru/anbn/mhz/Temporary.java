package ru.anbn.mhz;

public class Temporary {
    /*
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // ЗАГРУЗКА ФАЙЛА version.txt
        File file_version = new File(getExternalFilesDir(null), "version.txt");

        if (file_version.exists()) {
            file_version.delete();
        }

        count = 20;
        while (file_version.exists() && count > 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
        }

        System.out.println("1111111" + "count = " + count);

        DownloadManager.Request request_version = null;
        request_version = new DownloadManager.Request(Uri.parse(FILE_PATH_GOOGLE_DISK_VERSION))
                .setTitle("version_mhz")
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.fromFile(file_version))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);
        //DownloadManager downloadManager_version = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request_version);

        System.out.println("22222");

        count = 20;
        while (!file_version.exists() && count > 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
        }

        System.out.println("3333333333" + "count = " + count);
        // file.delete();


        // ЗАГРУЗКА ФАЙЛА mhz_data.txt
        File file_data = new File(getExternalFilesDir(null), "mhz_data.txt");

        if (file_data.exists()) {
            file_data.delete();
        }

        count = 20;
        while (file_data.exists() && count > 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
        }

        System.out.println("1111111" + "count = " + count);

        DownloadManager.Request request_data = null;
        request_data = new DownloadManager.Request(Uri.parse(FILE_PATH_GOOGLE_DISK_DATA))
                .setTitle("database_mhz")
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.fromFile(file_data))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);
        // DownloadManager downloadManager_data = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request_data);

        System.out.println("22222");

        count = 20;
        while (!file_data.exists() && count > 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
        }

        System.out.println("3333333333" + "count = " + count);
        // file.delete();
         */



}
