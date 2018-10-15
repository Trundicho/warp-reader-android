package de.trundicho.warpreader.view.ui;

import android.view.View;
import android.widget.NumberPicker;

import de.trundicho.warp.reader.core.controller.speed.WpmBoxSpeedModelUpdater;
import de.trundicho.warp.reader.core.view.api.widgets.WordsPerMinuteWidget;

class WordsPerMinuteWidgetImpl implements WordsPerMinuteWidget {
    private final NumberPicker wordsPerMinute;

    WordsPerMinuteWidgetImpl(NumberPicker wordsPerMinute) {
        this.wordsPerMinute = wordsPerMinute;
    }

    @Override
    public Integer getWordsPerMinute() {
        return wordsPerMinute.getValue();
    }

    @Override
    public void setWordsPerMinute(Integer integer) {
        wordsPerMinute.setValue(integer);
    }

    @Override
    public void registerChangeListenerAction(WpmBoxSpeedModelUpdater wpmBoxSpeedModelUpdater) {
        wordsPerMinute.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                wpmBoxSpeedModelUpdater.run();
            }
        });
    }
}
