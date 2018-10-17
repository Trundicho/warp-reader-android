package de.trundicho.warpreader.view.ui;

import java.util.Locale;

public class I18nLocalizer {

    private Locale locale;
    private static final String WARPREADER_INITIAL_TEXT = "WarpReader Copy and paste your text into the text field and start reading. As you get faster just increase the tempo as much as you like. Have fun reading at warp speed with WarpReader";
    private static final String WARPREADER_HELP_TEXT = "Paste your text or website URL in here...";

    public I18nLocalizer(Locale locale) {
        //set default locale
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String localize(String key) {
        if (key.equals("warpreader.initial.text")) {
            return WARPREADER_INITIAL_TEXT;
        } else if (key.equals("warpreader.help.text")) {
            return WARPREADER_HELP_TEXT;
        }
        return "";
    }

}
