package de.trundicho.warpreader.view.ui;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import de.trundicho.warp.reader.core.view.api.WarpReaderViewBuilder;
import de.trundicho.warp.reader.core.view.api.WarpReaderViewModel;
import de.trundicho.warp.reader.core.view.api.WarpReaderViewModelImpl;
import de.trundicho.warp.reader.core.view.api.WarpReaderViewModelMutable;
import de.trundicho.warpreader.R;

public class WarpReaderViewBuilderImpl implements WarpReaderViewBuilder {

    private AppCompatActivity appCompatActivity;

    public WarpReaderViewBuilderImpl(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public WarpReaderViewModel buildView() {
        WarpReaderViewModelMutable warpReaderViewModel = new WarpReaderViewModelImpl();
        TextView textArea = appCompatActivity.findViewById(R.id.textArea);
        warpReaderViewModel.setInputTextArea(new InputTextWidgetImpl(textArea));
        NumberPicker wordsPerMinute = appCompatActivity.findViewById(R.id.wordsPerMinute);
        Button playButton = appCompatActivity.findViewById(R.id.playButton);
        warpReaderViewModel.setWordsPerMinuteBox(new WordsPerMinuteWidgetImpl(wordsPerMinute));
        warpReaderViewModel.setPlayButton(new PlayButtonWidgetImpl(playButton));
        warpReaderViewModel.setReadPosition(new ReadPositionPercentageWidgetImpl(appCompatActivity.findViewById(R.id.readPosition)));
        TextView leftWarpLabel = appCompatActivity.findViewById(R.id.leftWarpLabel);
        TextView rightWarpLabel = appCompatActivity.findViewById(R.id.rightWarpLabel);
        warpReaderViewModel.setWarpTextLabelUpdater(new WarpTextWidgetImpl(leftWarpLabel, rightWarpLabel));
        TextView durationLabel = appCompatActivity.findViewById(R.id.durationLabel);
        warpReaderViewModel.setDurationLabel(new DurationWidgetImpl(durationLabel));
        return warpReaderViewModel;
    }

}
