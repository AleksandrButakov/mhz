package ru.anbn.mhz;

public class FormatTextToDisplay {

    // TODO Think about how to implement a beautiful transfer programmatically,
    //  because the width of the screen is different for everyone. This algorithm will
    //  work crookedly on smaller screens
    /* Передаем название станции, в случае если встречается символ @, значит переносим строку
       и добавляем 13 пробелов для красивого отображения.
       Длина текста 23 символа и 13 пробелов в начале строки
     */
    private String textFormatting23(String text) {
        String sResult = "";
        char sChar;

        for (int i = 0; i < text.length(); i++) {
            sChar = text.charAt(i);
            if (sChar == '@') {
                sResult += "\n" + "             ";
            } else {
                sResult += String.valueOf(sChar);
            }
        }
        return sResult;
    }

    /* Передаем название станции в ListView, в случае если встречается символ @, значит
   переносим, вырезаем его. Длина текста 34 символа */
    private String textFormatting34(String text) {
        String sResult = "";
        char sChar;

        for (int i = 0; i < text.length(); i++) {
            sChar = text.charAt(i);
            if (sChar == '@') {
                // пропускаем добавление символа
            } else {
                sResult += String.valueOf(sChar);
            }
        }
        return sResult;
    }

}
