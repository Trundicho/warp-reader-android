package de.trundicho.warpreader.view.parser;

import android.app.Activity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import de.trundicho.warp.reader.core.controller.WarpInitializer;
import de.trundicho.warp.reader.core.view.api.WebsiteParserAndWarper;
import de.trundicho.warp.reader.core.view.api.parser.TextAreaParser;
import de.trundicho.warp.reader.core.view.api.widgets.InputTextWidget;
import de.trundicho.warpreader.view.ui.I18nLocalizer;

public class TextAreaParserTimerBuilder {

    private final Activity activity;
    private final InputTextWidget textArea;
    private final WarpInitializer warpInitializer;
    private final I18nLocalizer i18nLocalizer;

    public TextAreaParserTimerBuilder(WarpInitializer warpInitializer, I18nLocalizer i18nLocalizer,
                                      Activity activity, InputTextWidget textArea) {
        this.warpInitializer = warpInitializer;
        this.i18nLocalizer = i18nLocalizer;
        this.activity = activity;
        this.textArea = textArea;
    }

    public TextAreaParser build() {
        WebsiteParserAndWarper websiteParserAndWarper = new WebsiteParserAndWarperImpl(warpInitializer,
                i18nLocalizer,
                activity, textArea);
        return new TextAreaParser(warpInitializer, websiteParserAndWarper);
    }


}
