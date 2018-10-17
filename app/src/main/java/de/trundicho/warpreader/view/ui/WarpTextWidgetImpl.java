package de.trundicho.warpreader.view.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Html;
import android.widget.TextView;

import de.trundicho.warp.reader.core.view.api.widgets.WarpTextWidget;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

class WarpTextWidgetImpl implements WarpTextWidget {
    private final TextView leftWarpLabel;
    private final TextView rightWarpLabel;

    WarpTextWidgetImpl(TextView leftWarpLabel, TextView rightWarpLabel) {
        this.leftWarpLabel = leftWarpLabel;
        this.rightWarpLabel = rightWarpLabel;
    }

    @Override
    public void setWarpText(String leftText, String centerText, String rightText) {
        leftWarpLabel.setText(leftText);
        String right = "<font color='#EE0000'>" + centerText + "</font>" + rightText;
        rightWarpLabel.setText(Html.fromHtml(right, FROM_HTML_MODE_COMPACT));
    }
}
