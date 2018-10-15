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

    private final TextAreaParser textAreaParser;
    private final ScheduledExecutorService scheduledExecutorService;
    private final Activity activity;
    private final int parserDelay;

    public TextAreaParserTimerBuilder(WarpInitializer warpInitializer, I18nLocalizer i18nLocalizer,
                                      int parserDelay,
                                      Activity activity) {
        this.parserDelay = parserDelay;
        this.activity = activity;
        WebsiteParserAndWarper websiteParserAndWarper = new WebsiteParserAndWarperImpl(warpInitializer, i18nLocalizer);
        this.textAreaParser = new TextAreaParser(warpInitializer, websiteParserAndWarper);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    public ScheduledFuture<?> buildTextAreaParserTimer(InputTextWidget inputTextWidget) {
        Runnable runnable = () -> {
            activity.runOnUiThread(() -> textAreaParser.parseInputTextAndStartWarping(inputTextWidget));
        };

        return scheduledExecutorService.scheduleAtFixedRate(runnable,
                parserDelay, parserDelay,
                TimeUnit.MILLISECONDS);
    }

}
