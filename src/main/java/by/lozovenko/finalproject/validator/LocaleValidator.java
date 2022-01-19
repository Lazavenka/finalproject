package by.lozovenko.finalproject.validator;

public class LocaleValidator {
    private static final String RU_LOCALE = "ru_RU";
    private static final String EN_LOCALE = "en_US";
    public static boolean isLocaleExist(String locale){
        return locale.matches(RU_LOCALE) || locale.equals(EN_LOCALE);
    }
}
