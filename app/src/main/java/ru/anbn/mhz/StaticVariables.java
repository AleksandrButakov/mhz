package ru.anbn.mhz;

public class StaticVariables {

    // путь к файлу с данными на смартфоне
    protected static final String FILE_PATH_LOCAL_DATA = "mhz_data.csv";

    protected static final String FILE_PATH_YANDEX_DISK_DATA = "https://s276iva.storage.yandex.net/rdisk/3e98c46f8d347db19d1cc5347c108c8e548e569066f30449866242103d326411/63a4b107/F11Pi2nPNKwfgYOegvjK8mj-bfSzbFsQAEgwO8vCgBFt1YYMBSvQ8nmpJ-f-tXqeHN_eDBcY5IUWGjqWbfVsbw==?uid=244211374&filename=mhz_data.csv&disposition=attachment&hash=&limit=0&content_type=text%2Fplain&owner_uid=244211374&fsize=68622&hid=246892bbec374ee129f2009cee9c7170&media_type=spreadsheet&tknv=v2&etag=a6c1777235fa0b41626c7b9f91b649e7&rtoken=ydnQ8Ty1CM5z&force_default=yes&ycrid=na-63fa1472ea6b0e653b636aa26542dda3-downloader21e&ts=5f06fbe390fc0&s=69742974813b7e2117c9520304767d6a660d3ca509195ea232ad3c601bd8c49a&pb=U2FsdGVkX18OcQePj5cdbAqi3V_Uf2YbKzADMb9M74pKigwdRf8zucFOLWHViGAXweEf8gbk1dkJHz349Twuef-6Lce4TD8kEeTrupEm2l8";

    // заполним массив статическими данными по частота
    protected static final String[][] radioFrequencyChannel = {
            {"2", "УКВ ПРС", "режим ПРС 1Г-1К", "режим ПРС 1Г-1К", "режим ПРС 1Г-1К", "режим ПРС 1Г-1К", "11"},
            {"10", "УКВ СРС", "2Г-4К", "2Г-4К", "2Г-2К", "1К-3Г", "31"},
            {"14", "УКВ СРС", "3Г-2К", "3Г-2К", "2Г-6К", "5К-3Г", "35"},
            {"31", "УКВ СРС", "6Г-1К", "6Г-1К", "4Г-7К", "4К-5Г", "54"},
            {"33", "УКВ СРС", "6Г-3К", "6Г-3К", "5Г-1К", "6К-5Г", "56"},
            {"34", "УКВ СРС", "6Г-4К", "6Г-4К", "5Г-2К", "7К-5Г", "57"},
            {"36", "УКВ СРС", "6Г-6К", "6Г-6К", "5Г-4К", "9К-5Г", "59"},
    };


}
