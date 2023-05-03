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
            "  РВ-1.1М",
            "  РВ-1М",
            "  РВС-1",
            "  РВС-1 СРС ver.1",
            "  РВС-1 СРС ver.2"};

}