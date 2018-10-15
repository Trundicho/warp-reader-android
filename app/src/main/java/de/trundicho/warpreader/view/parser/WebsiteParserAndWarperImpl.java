package de.trundicho.warpreader.view.parser;

import de.trundicho.warp.reader.core.controller.WarpInitializer;
import de.trundicho.warp.reader.core.view.api.WebsiteParserAndWarper;
import de.trundicho.warp.reader.core.view.api.widgets.InputTextWidget;
import de.trundicho.warpreader.view.ui.I18nLocalizer;

class WebsiteParserAndWarperImpl implements WebsiteParserAndWarper {
    private final TextFromWebUrlParserService boilerplateService;
    private final WarpInitializer warpInitializer;
    private final I18nLocalizer i18nLocalizer;
    private final String errorText;

    WebsiteParserAndWarperImpl(WarpInitializer warpInitializer, I18nLocalizer i18nLocalizer) {
        this.warpInitializer = warpInitializer;
        this.i18nLocalizer = i18nLocalizer;
        this.errorText = "Error occured: Please try other URL.";
        this.boilerplateService = new TextFromWebUrlParserService();
    }

    public void parseWebsiteAndStartWarping(InputTextWidget textArea) {
        String text = textArea.getText();
        try {
            final String textFromWebsite = boilerplateService.parseTextFromWebsite(text);
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
        } catch (Exception e) {
            onFailure(textArea, text, e);
        }
    }

    public void onFailure(InputTextWidget textArea, String text, Throwable caught) {
        textArea.setText(errorText);
        warpInitializer.initAndStartWarping(textArea.getText());
        textArea.setHelpText(errorText + "\nCan not parse " + text);
        textArea.setText("");
    }
}