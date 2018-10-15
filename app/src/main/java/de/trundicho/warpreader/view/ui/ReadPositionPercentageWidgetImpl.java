package de.trundicho.warpreader.view.ui;

import android.view.View;
import android.widget.NumberPicker;

import de.trundicho.warp.reader.core.controller.position.ReadingPositionPlayModelUpdater;
import de.trundicho.warp.reader.core.controller.speed.WpmBoxSpeedModelUpdater;
import de.trundicho.warp.reader.core.view.api.widgets.ReadingPositionBox;

class ReadPositionPercentageWidgetImpl implements ReadingPositionBox {
    private final NumberPicker readPosition;

    ReadPositionPercentageWidgetImpl(NumberPicker readPosition) {
        this.readPosition = readPosition;
    }

    @Override
    public Integer getReadPositionPercentage() {
        return readPosition.getValue();
    }

    @Override
    public void setReadPositionPercentage(Integer integer) {
        readPosition.setValue(integer);
    }

    @Override
    public void registerChangeListenerAction(ReadingPositionPlayModelUpdater readingPositionPlayModelUpdater) {
        readPosition.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                readingPositionPlayModelUpdater.run();
            }
        });
    }
}
