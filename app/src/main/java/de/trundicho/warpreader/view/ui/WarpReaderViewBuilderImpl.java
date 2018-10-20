package de.trundicho.warpreader.view.ui;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
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
        InputTextWidgetImpl inputTextWidget = new InputTextWidgetImpl(textArea);
        warpReaderViewModel.setInputTextArea(inputTextWidget);
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
        warpReaderViewModel.setWarpTextLabelUpdater(new WarpTextWidgetImpl(leftWarpLabel,
                rightWarpLabel));
        TextView durationLabel = appCompatActivity.findViewById(R.id.durationLabel);
        warpReaderViewModel.setDurationLabel(new DurationWidgetImpl(durationLabel));
        Button clipboardButton = appCompatActivity.findViewById(R.id.clipboardButton);
        ClipboardManager clipboardManager = (ClipboardManager) appCompatActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        warpReaderViewModel.setClipboardButton(new ClipboardWidgetImpl(clipboardButton, inputTextWidget,
                clipboardManager));
        return warpReaderViewModel;
    }

}
