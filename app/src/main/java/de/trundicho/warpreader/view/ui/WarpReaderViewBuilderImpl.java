package de.trundicho.warpreader.view.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import de.trundicho.warp.reader.core.view.api.WarpReaderViewBuilder;
import de.trundicho.warp.reader.core.view.api.WarpReaderViewModel;
import de.trundicho.warp.reader.core.view.api.WarpReaderViewModelImpl;
import de.trundicho.warp.reader.core.view.api.WarpReaderViewModelMutable;
import de.trundicho.warpreader.R;

public class WarpReaderViewBuilderImpl implements WarpReaderViewBuilder {

    private Activity appCompatActivity;

    public WarpReaderViewBuilderImpl(Activity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public WarpReaderViewModel buildView() {
        WarpReaderViewModelMutable warpReaderViewModel = new WarpReaderViewModelImpl();
        TextView textArea = appCompatActivity.findViewById(R.id.textArea);
        warpReaderViewModel.setInputTextArea(new InputTextWidgetImpl(textArea));
        SeekBar wordsPerMinute = appCompatActivity.findViewById(R.id.wordsPerMinute);
        Button playButton = appCompatActivity.findViewById(R.id.playButton);
        TextView wordsPerMinuteLabel = appCompatActivity.findViewById(R.id.wordsPerMinuteLabel);
        warpReaderViewModel.setWordsPerMinuteBox(new WordsPerMinuteWidgetImpl(wordsPerMinute, wordsPerMinuteLabel));
        warpReaderViewModel.setPlayButton(new PlayButtonWidgetImpl(playButton));
        SeekBar positionBar = appCompatActivity.findViewById(R.id.readPosition);
        TextView positionLabel = appCompatActivity.findViewById(R.id.positionLabel);
        warpReaderViewModel.setReadPosition(new ReadPositionPercentageWidgetImpl(positionBar, positionLabel));
        TextView leftWarpLabel = appCompatActivity.findViewById(R.id.leftWarpLabel);
        TextView rightWarpLabel = appCompatActivity.findViewById(R.id.rightWarpLabel);
        TextView centerWarpLabel = appCompatActivity.findViewById(R.id.centerWarpLabel);
        warpReaderViewModel.setWarpTextLabelUpdater(new WarpTextWidgetImpl(leftWarpLabel,
                centerWarpLabel,
                rightWarpLabel));
        TextView durationLabel = appCompatActivity.findViewById(R.id.durationLabel);
        warpReaderViewModel.setDurationLabel(new DurationWidgetImpl(durationLabel));
        return warpReaderViewModel;
    }

}
