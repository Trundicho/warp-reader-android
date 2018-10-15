package de.trundicho.warpreader.view.ui;

import android.widget.TextView;

import de.trundicho.warp.reader.core.view.api.widgets.InputTextWidget;

class InputTextWidgetImpl implements InputTextWidget {
    private final TextView textArea;

    InputTextWidgetImpl(TextView textArea) {
        this.textArea = textArea;
    }

    @Override
    public void setText(String s) {
        textArea.setText(s);
    }

    @Override
    public void setHelpText(String s) {
    }

    @Override
    public String getText() {
        CharSequence text = textArea.getText();
        return text != null ? text.toString() : "";
    }
}
