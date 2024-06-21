package com.example.service;

import com.ibm.icu.text.Transliterator;

import java.util.HashMap;
import java.util.Map;

public class TranslatorService {

    public String translate(String countryCode, String word) {
        Map<String, String> languageCodes = getLanguageCodesMap();

        if (languageCodes.containsKey(countryCode)) {
            String transLanguage = languageCodes.get(countryCode);
            Transliterator translator = Transliterator.getInstance(transLanguage);
            return translator.transliterate(word);
        }
        return word;
    }

    private Map<String, String> getLanguageCodesMap() {
        Map<String, String> languageCodes = new HashMap<>();
        languageCodes.put("AE", "Arabic-Latin");
        languageCodes.put("AM", "Armenian-Latin");
        languageCodes.put("AR", "Arabic-Latin");
        languageCodes.put("AZ", "Cyrillic-Latin");
        languageCodes.put("BD", "Bengali-Latin");
        languageCodes.put("BG", "Cyrillic-Latin");
        languageCodes.put("BH", "Arabic-Latin");
        languageCodes.put("BY", "Cyrillic-Latin");
        languageCodes.put("CN", "Han-Latin");
        languageCodes.put("CY", "Greek-Latin");
        languageCodes.put("EG", "Arabic-Latin");
        languageCodes.put("ET", "Ethiopic-Latin");
        languageCodes.put("GE", "Georgian-Latin");
        languageCodes.put("GR", "Greek-Latin");
        languageCodes.put("HK", "Han-Latin");
        languageCodes.put("IN", "Devanagari-Latin");
        languageCodes.put("IQ", "Arabic-Latin");
        languageCodes.put("IR", "Arabic-Latin");
        languageCodes.put("IS", "Latin-ASCII");
        languageCodes.put("JO", "Arabic-Latin");
        languageCodes.put("JP", "Katakana-Latin");
        languageCodes.put("KG", "Cyrillic-Latin");
        languageCodes.put("KH", "Khmer-Latin");
        languageCodes.put("KP", "Hangul-Latin");
        languageCodes.put("KR", "Hangul-Latin");
        languageCodes.put("KZ", "Cyrillic-Latin");
        languageCodes.put("LA", "Lao-Latin");
        languageCodes.put("LB", "Arabic-Latin");
        languageCodes.put("MN", "Cyrillic-Latin");
        languageCodes.put("MV", "Thaana-Latin");
        languageCodes.put("MY", "Latin-ASCII");
        languageCodes.put("NP", "Devanagari-Latin");
        languageCodes.put("OM", "Arabic-Latin");
        languageCodes.put("PK", "Arabic-Latin");
        languageCodes.put("QA", "Arabic-Latin");
        languageCodes.put("RU", "Cyrillic-Latin");
        languageCodes.put("SA", "Arabic-Latin");
        languageCodes.put("SG", "Latin-ASCII");
        languageCodes.put("SY", "Arabic-Latin");
        languageCodes.put("TH", "Thai-Latin");
        languageCodes.put("TJ", "Cyrillic-Latin");
        languageCodes.put("TM", "Cyrillic-Latin");
        languageCodes.put("TR", "Latin-ASCII");
        languageCodes.put("UA", "Cyrillic-Latin");
        languageCodes.put("UZ", "Cyrillic-Latin");
        languageCodes.put("VN", "Latin-ASCII");
        languageCodes.put("YE", "Arabic-Latin");

        return languageCodes;
    }
}
