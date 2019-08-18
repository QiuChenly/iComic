package com.qiuchenly.comicx.ProductModules.Bika;

import java.util.Locale;

public class Language {
    public static final String CANTONESE = "cantonese";
    public static final String CHINESE = "chinese";
    public static final String ENGLISH = "english";
    public static final String JAPANESE = "japanese";
    public static String currentChatroomLanguage;

    public static Locale getLocale(String language) {
        if (language.equalsIgnoreCase(CANTONESE)) {
            return new Locale("yue", "HK");
        }
        if (language.equalsIgnoreCase(JAPANESE)) {
            return Locale.JAPANESE;
        }
        if (language.equalsIgnoreCase(ENGLISH)) {
            return Locale.ENGLISH;
        }
        return Locale.CHINESE;
    }
}
