package de.trundicho.warpreader.view.parser;

import android.app.Activity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import de.trundicho.warp.reader.core.controller.WarpInitializer;
import de.trundicho.warp.reader.core.view.api.WebsiteParserAndWarper;
import de.trundicho.warp.reader.core.view.api.widgets.InputTextWidget;
import de.trundicho.warpreader.view.ui.I18nLocalizer;

class WebsiteParserAndWarperImpl implements WebsiteParserAndWarper {
    private final TextFromWebUrlParserService boilerplateService;
    private final WarpInitializer warpInitializer;
    private final I18nLocalizer i18nLocalizer;
    private final Activity activity;
    private final String errorText;
    private final ExecutorService scheduledExecutorService;

    WebsiteParserAndWarperImpl(WarpInitializer warpInitializer, I18nLocalizer i18nLocalizer,
                               Activity activity) {
        this.warpInitializer = warpInitializer;
        this.i18nLocalizer = i18nLocalizer;
        this.activity = activity;
        this.errorText = "Error occured: Please try other URL.";
        this.boilerplateService = new TextFromWebUrlParserService();
        this.scheduledExecutorService = Executors.newSingleThreadExecutor();
    }

    public void parseWebsiteAndStartWarping(InputTextWidget textArea) {
        String text = textArea.getText();

        this.scheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final String textFromWebsite = boilerplateService.parseTextFromWebsite(text);
                    updateUi(textFromWebsite, text, textArea);
                } catch (Exception e) {
                    onFailure(textArea, text, e);
                }
            }
        });

    }

    private void updateUi(String textFromWebsite, String text, InputTextWidget textArea) {
        activity.runOnUiThread(()->{
            boolean error = false;
            if (textFromWebsite != null && !textFromWebsite.equals(text)) {
                textArea.setText(textFromWebsite);
            } else {
                textArea.setText(errorText);
                error = true;
            }
            warpInitializer.initAndStartWarping(textArea.getText());
            textArea.setText("");
            textArea.setHelpText(i18nLocalizer.localize("warpreader.help.text"));
            if (error) {
                textArea.setHelpText(errorText + "\nCan not parse " + text);
            }
        });
    }

    public void onFailure(InputTextWidget textArea, String text, Throwable caught) {
        activity.runOnUiThread(()->{
            textArea.setText(errorText);
            warpInitializer.initAndStartWarping(textArea.getText());
            textArea.setHelpText(errorText + "\nCan not parse " + text);
            textArea.setText("");
        });
    }
}