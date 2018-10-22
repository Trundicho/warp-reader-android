package de.trundicho.warpreader.view.ui;

import android.view.View;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import de.trundicho.warp.reader.core.controller.speed.WpmBoxSpeedModelUpdater;
import de.trundicho.warp.reader.core.view.api.widgets.WordsPerMinuteWidget;

class WordsPerMinuteWidgetImpl implements WordsPerMinuteWidget {
    private final SeekBar wordsPerMinute;
    private final TextView wordsPerMinuteLabel;

    WordsPerMinuteWidgetImpl(SeekBar wordsPerMinute, TextView wordsPerMinuteLabel) {
        this.wordsPerMinute = wordsPerMinute;
        this.wordsPerMinuteLabel = wordsPerMinuteLabel;
        wordsPerMinute.setMin(40);
        wordsPerMinute.setMax(600);
    }

    @Override
    public Integer getWordsPerMinute() {
        return wordsPerMinute.getProgress();
    }

    @Override
    public void setWordsPerMinute(Integer integer) {
        wordsPerMinute.setProgress(integer);
        updateLabel(integer);
    }

    private void updateLabel(Integer integer) {
        wordsPerMinuteLabel.setText("Updates per minute: " + integer);
    }

    @Override
    public void registerChangeListenerAction(WpmBoxSpeedModelUpdater wpmBoxSpeedModelUpdater) {
        wordsPerMinute.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                updateLabel(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                wpmBoxSpeedModelUpdater.run();
                updateLabel(seekBar.getProgress());
            }
        });
    }
}
