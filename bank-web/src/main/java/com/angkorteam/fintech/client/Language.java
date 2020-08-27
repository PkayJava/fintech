package com.angkorteam.fintech.client;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

import java.util.Locale;

public enum Language {

    Khmer("khmer", "ភាសាខ្មែរ", new Locale("kh"), "khmer"), English("english", "English", new Locale("en"), "latin");

    private final String literal;

    private final String description;

    private final String localization;

    private final Locale locale;

    Language(String literal, String description, Locale locale, String localization) {
        this.literal = literal;
        this.description = description;
        this.locale = locale;
        this.localization = localization;
    }

    public String getLiteral() {
        return literal;
    }

    public String getLocalization() {
        return localization;
    }

    public String getDescription() {
        return description;
    }

    public Locale getLocale() {
        return locale;
    }

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public static Language parseLiteral(String literal) {
        for (Language value : Language.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        Language value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}