package de.trundicho.warpreader.view.ui;

import android.widget.TextView;

import de.trundicho.warp.reader.core.view.api.widgets.WarpTextWidget;

class WarpTextWidgetImpl implements WarpTextWidget {
    private final TextView leftWarpLabel;
    private final TextView rightWarpLabel;

    public WarpTextWidgetImpl(TextView leftWarpLabel, TextView rightWarpLabel) {
        this.leftWarpLabel = leftWarpLabel;
        this.rightWarpLabel = rightWarpLabel;
    }

    @Override
    public void setWarpText(String leftText, String centerText, String rightText) {
        leftWarpLabel.setText(leftText);
        rightWarpLabel.setText(centerText + rightText);
    }
}
