package by.mx.ui.utils;

/**
 * Утилитарный класс для очистки и конвертации текстовых цен с сайта mx.by в числа типа double.
 * Изолирует логику парсинга строк от UI-шагов Selenium, реализуя принцип Single Responsibility.
 * Декомпозирован на микрометоды для удаления валюты, отсечения копеек и удаления пробелов.
 * Позволяет безопасно передавать очищенные числовые данные в математические ассерты тестов.
 */
public class Prices {

    public static double parseRawPriceToDouble(String rawPrice){
        String priceAsText = removeAllExceptNumbersSpaceDotComma(rawPrice);
        priceAsText = getRubles(priceAsText);
        priceAsText = clearFormSpaces(priceAsText);
        return Double.parseDouble(priceAsText);
    }

    public static String removeAllExceptNumbersSpaceDotComma(String str){
        return str.replaceAll("[^0-9,. ]", "").trim();
    }

    public static String getRubles(String price) {
        String cleanText = price;
        if (price.contains(",")) {
            cleanText = price.split(",")[0];
        } else if (price.contains(".")) {
            cleanText = price.split("\\.")[0];
        }
        return cleanText;
    }

    public static String clearFormSpaces(String str){
        return str.replaceAll("\\s+", "");
    }
}
