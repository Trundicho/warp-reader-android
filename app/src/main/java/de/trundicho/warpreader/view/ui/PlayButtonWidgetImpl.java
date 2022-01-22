package de.trundicho.warpreader.view.ui;

import android.view.View;
import android.widget.Button;

import de.trundicho.warp.reader.core.model.playmode.PlayState;
import de.trundicho.warp.reader.core.view.api.widgets.PlayButtonWidget;

class PlayButtonWidgetImpl implements PlayButtonWidget {
    private final Button playButton;

    public PlayButtonWidgetImpl(Button playButton) {
        this.playButton = playButton;
    }

    @Override
    public void updateWidgetStyle(PlayState playState) {
        playButton.setText(playState.getSign());
    }

    @Override
    public void click() {
        playButton.callOnClick();
    }

    @Override
    public void registerChangeListenerAction(Runnable playButtonWidgetActionRunner) {
        playButton.setOnClickListener(view -> playButtonWidgetActionRunner.run());
    }
}
