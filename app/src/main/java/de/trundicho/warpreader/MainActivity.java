package de.trundicho.warpreader;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import java.util.Locale;

import de.trundicho.warp.reader.core.controller.Disposer;
import de.trundicho.warp.reader.core.controller.WarpInitializer;
import de.trundicho.warp.reader.core.controller.WarpUpdater;
import de.trundicho.warp.reader.core.controller.play.PlayButtonListenerInitializer;
import de.trundicho.warp.reader.core.controller.position.ReadingPositionPlayModelUpdater;
import de.trundicho.warp.reader.core.controller.position.ReadingPositionUpdaterListener;
import de.trundicho.warp.reader.core.controller.speed.WpmBoxSpeedModelUpdater;
import de.trundicho.warp.reader.core.model.playmode.PlayModeModel;
import de.trundicho.warp.reader.core.model.playmode.PlayState;
import de.trundicho.warp.reader.core.model.playmode.impl.PlayModeModelImpl;
import de.trundicho.warp.reader.core.model.playmode.impl.PlayModel;
import de.trundicho.warp.reader.core.model.speed.DelayModel;
import de.trundicho.warp.reader.core.model.speed.SpeedWeightModel;
import de.trundicho.warp.reader.core.model.speed.WpmSpeedExchanger;
import de.trundicho.warp.reader.core.model.speed.impl.DelayModelImpl;
import de.trundicho.warp.reader.core.model.speed.impl.SpeedWeightModelImpl;
import de.trundicho.warp.reader.core.model.speed.impl.WpmSpeedExchangerImpl;
import de.trundicho.warp.reader.core.model.warpword.TextSplitter;
import de.trundicho.warp.reader.core.model.warpword.WordLengthModelMutable;
import de.trundicho.warp.reader.core.model.warpword.impl.WordLengthModelImpl;
import de.trundicho.warp.reader.core.view.api.WarpReaderViewBuilder;
import de.trundicho.warp.reader.core.view.api.WarpReaderViewModel;
import de.trundicho.warp.reader.core.view.api.parser.TextAreaParser;
import de.trundicho.warp.reader.core.view.api.timer.WarpTimer;
import de.trundicho.warp.reader.core.view.api.widgets.InputTextWidget;
import de.trundicho.warp.reader.core.view.api.widgets.NumberLabelWidget;
import de.trundicho.warp.reader.core.view.api.widgets.PlayButtonWidget;
import de.trundicho.warp.reader.core.view.api.widgets.ReadingPositionBox;
import de.trundicho.warp.reader.core.view.api.widgets.WarpTextWidget;
import de.trundicho.warp.reader.core.view.api.widgets.WordsPerMinuteWidget;
import de.trundicho.warpreader.view.parser.TextAreaParserTimerBuilder;
import de.trundicho.warpreader.view.timer.WarpTimerImpl;
import de.trundicho.warpreader.view.ui.ClipboardWidgetImpl;
import de.trundicho.warpreader.view.ui.I18nLocalizer;
import de.trundicho.warpreader.view.ui.WarpReaderViewBuilderImpl;

public class MainActivity extends Activity {
    private static final int DEFAULT_NUMBER_OF_CHARS_TO_DISPLAY = 15;
    private static final int DEFAULT_WORDS_PER_MINUTE = 140;
    private static final String WORDS_PER_MINUTE_INSTANCE = "WORDS_PER_MINUTE";
    private I18nLocalizer i18nLocalizer;
    private Disposer disposer;
    private DelayModel speedModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disposer = new Disposer();
        i18nLocalizer = new I18nLocalizer(Locale.ENGLISH);
        PlayModeModel playModeModel = new PlayModeModelImpl(PlayState.PLAYING);
        WordLengthModelMutable wordLengthModel = new WordLengthModelImpl(DEFAULT_NUMBER_OF_CHARS_TO_DISPLAY);
        TextSplitter textSplitter = new TextSplitter(wordLengthModel);
        SpeedWeightModel speedWeightModel = new SpeedWeightModelImpl();
        WpmSpeedExchanger wpmSpeedExchanger = new WpmSpeedExchangerImpl();
        double defaultWordsPerMinute = getDefaultWordsPerMinute();
        speedModel = new DelayModelImpl(wpmSpeedExchanger.exchangeToSpeed(defaultWordsPerMinute));
        PlayModel playModel = new PlayModel();

        WarpReaderViewBuilder viewBuilder = new WarpReaderViewBuilderImpl(this);
        WarpReaderViewModel uiModel = viewBuilder.buildView();
        initUiAndRegisterListeners(uiModel, wpmSpeedExchanger, speedModel, playModeModel, speedWeightModel,
                textSplitter, playModel);

        disposer.add(() -> {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            double defaultDelay = speedModel.getDefaultDelay();
            editor.putInt(WORDS_PER_MINUTE_INSTANCE, new Double(wpmSpeedExchanger.exchangeToWpm(defaultDelay)).intValue());
            editor.commit();
        });
    }

    @Override
    protected void onDestroy() {
        disposer.doDispose();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        disposer.doDispose();
        super.onStop();
    }

    private void initUiAndRegisterListeners(WarpReaderViewModel uiModel, WpmSpeedExchanger wpmSpeedExchanger,
                                            DelayModel speedModel, PlayModeModel playModeModel,
                                            SpeedWeightModel speedWeightModel,
                                            TextSplitter textSplitter, PlayModel playModel) {
        initAndRegisterWpmBox(uiModel, wpmSpeedExchanger, speedModel);

        PlayButtonWidget playButton = uiModel.getPlayButton();
        PlayButtonListenerInitializer playButtonListenerInitializer = new PlayButtonListenerInitializer(playModeModel,
                playButton);
        playButtonListenerInitializer.initListeners();

        InputTextWidget inputTextWidget = uiModel.getInputTextArea();
        initInputTextArea(inputTextWidget);

        ReadingPositionBox readingPosition = uiModel.getReadPosition();
        initAndRegisterReadingPosition(playModel, readingPosition, playModeModel);

        WarpTextWidget warpTextLabelUpdater = uiModel.getWarpTextLabelUpdater();

        NumberLabelWidget durationWidget = uiModel.getDurationLabel();
        WarpTimer warpTimer = new WarpTimerImpl(new WarpUpdater(playModel), this);
        disposer.add(() -> warpTimer.cancel());
        WarpInitializer warpInitializer = new WarpInitializer(warpTextLabelUpdater, speedModel,
                playModeModel, speedWeightModel, textSplitter, playModel, durationWidget, warpTimer);

        TextAreaParserTimerBuilder textAreaParserTimerBuilder = new TextAreaParserTimerBuilder(warpInitializer, i18nLocalizer,
                this, inputTextWidget);
        TextAreaParser textAreaParserTimer = textAreaParserTimerBuilder.build();
        ClipboardWidgetImpl clipBoardButton = (ClipboardWidgetImpl) uiModel.getClipBoardButton();
        clipBoardButton.addClickListener(new Runnable() {
            @Override
            public void run() {
                ClipboardManager clipboardManager = (ClipboardManager) MainActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData primaryClip = clipboardManager.getPrimaryClip();
                if (primaryClip != null) {
                    if (primaryClip.getItemCount() > 0) {
                        ClipData.Item itemAt = primaryClip.getItemAt(0);
                        if (itemAt != null) {
                            CharSequence text = itemAt.getText();
                            if (text != null) {
                                textAreaParserTimer.parseInputTextAndStartWarping(text.toString());
                            }
                        }
                    }
                }
            }
        });

        textAreaParserTimer.parseInputTextAndStartWarping(inputTextWidget.getText());
        inputTextWidget.setText("");
    }

    private double getDefaultWordsPerMinute() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        return preferences.getInt(WORDS_PER_MINUTE_INSTANCE, DEFAULT_WORDS_PER_MINUTE);
    }

    private void initAndRegisterReadingPosition(PlayModel playModel, ReadingPositionBox readingPosition,
                                                PlayModeModel playModeModel) {
        readingPosition.setReadPositionPercentage(Integer.valueOf(0));
        readingPosition.registerChangeListenerAction(new ReadingPositionPlayModelUpdater(readingPosition, playModel,
                playModeModel));
        playModel.addListener(new ReadingPositionUpdaterListener(readingPosition));
    }

    private void initInputTextArea(InputTextWidget inputTextWidget) {
        String textToRead = i18nLocalizer.localize("warpreader.initial.text");
        inputTextWidget.setText(textToRead);
        inputTextWidget.setHelpText(i18nLocalizer.localize("warpreader.help.text"));
    }

    private void initAndRegisterWpmBox(WarpReaderViewModel uiModel, WpmSpeedExchanger wpmSpeedExchanger,
                                       DelayModel speedModel) {
        WordsPerMinuteWidget wpmBox = uiModel.getWordsPerMinuteBox();
        double wpm = wpmSpeedExchanger.exchangeToWpm(speedModel.getDefaultDelay());
        wpmBox.setWordsPerMinute((int) wpm);
        wpmBox.registerChangeListenerAction(new WpmBoxSpeedModelUpdater(wpmSpeedExchanger, speedModel, wpmBox));
    }
}
