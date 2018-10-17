package de.trundicho.warpreader.view.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.TextView;

import de.trundicho.warp.reader.core.view.api.widgets.WarpTextWidget;

class WarpTextWidgetImpl implements WarpTextWidget {
    private final TextView leftWarpLabel;
    private final TextView centerWarpLabel;
    private final TextView rightWarpLabel;

    public WarpTextWidgetImpl(TextView leftWarpLabel, TextView centerWarpLabel, TextView rightWarpLabel) {
        this.leftWarpLabel = leftWarpLabel;
        this.centerWarpLabel = centerWarpLabel;
        centerWarpLabel.setTextColor(Color.RED);
        this.rightWarpLabel = rightWarpLabel;
    }

    @Override
    public void setWarpText(String leftText, String centerText, String rightText) {
        leftWarpLabel.setText(leftText);
        centerWarpLabel.setText(centerText);
        rightWarpLabel.setText(rightText);
    }
}
