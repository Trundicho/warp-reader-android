package de.trundicho.warpreader.view.ui;

import android.view.View;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import de.trundicho.warp.reader.core.controller.position.ReadingPositionPlayModelUpdater;
import de.trundicho.warp.reader.core.controller.speed.WpmBoxSpeedModelUpdater;
import de.trundicho.warp.reader.core.view.api.widgets.ReadingPositionBox;

class ReadPositionPercentageWidgetImpl implements ReadingPositionBox {
    private final SeekBar readPosition;
    private final TextView positionLabel;

    ReadPositionPercentageWidgetImpl(SeekBar readPosition, TextView positionLabel) {
        this.readPosition = readPosition;
        this.positionLabel = positionLabel;
    }

    @Override
    public Integer getReadPositionPercentage() {
        return readPosition.getProgress();
    }

    @Override
    public void setReadPositionPercentage(Integer integer) {
        readPosition.setProgress(integer);
        positionLabel.setText("Position " + integer + "%");
    }

    @Override
    public void registerChangeListenerAction(ReadingPositionPlayModelUpdater readingPositionPlayModelUpdater) {
        readPosition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                readingPositionPlayModelUpdater.run();
            }
        });
    }
}
