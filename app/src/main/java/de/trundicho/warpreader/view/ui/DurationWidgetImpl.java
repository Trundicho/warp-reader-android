package de.trundicho.warpreader.view.ui;

import android.widget.TextView;

import de.trundicho.warp.reader.core.view.api.widgets.NumberLabelWidget;
import de.trundicho.warpreader.R;

class DurationWidgetImpl implements NumberLabelWidget {

    private TextView durationLabel;

    DurationWidgetImpl(TextView durationLabel) {
        this.durationLabel = durationLabel;
    }

    @Override
    public void updateNumberLabel(int overallDurationMs) {
        int seconds = (overallDurationMs / 1000) % 60;
        long minutes = ((overallDurationMs / 1000) / 60);// % 60;
        int hours = (((overallDurationMs / 1000) / 60) / 60);
        durationLabel.setText("Duration: " + minutes + "Min " + seconds + "Sec");
    }
}
