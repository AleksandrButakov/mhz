package ru.anbn.mhz;

public class Variables {
    private static String url;

    public static void setUrl(String url) {
        Variables.url = url;
    }

    public static String getUrl() {
        return url;
    }

    public static final String[] SEQUIPMENT = {
            "  Выберите оборудование...",
            "  РЛСМ-10",
            "  РВ1-1М в СРС",
            "  РВ-1М",
            "  РВС-1"};

}