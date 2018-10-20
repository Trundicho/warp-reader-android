package de.trundicho.warpreader.view.ui;

import android.view.View;
import android.widget.Button;

import de.trundicho.warp.reader.core.view.api.widgets.ButtonWidget;

public class ClipboardWidgetImpl implements ButtonWidget {
    private final Button clipboardButton;

    ClipboardWidgetImpl(Button clipboardButton) {
        this.clipboardButton = clipboardButton;
    }

    @Override
    public void click() {
        clipboardButton.callOnClick();
    }

    public void addClickListener(Runnable runnable){
        clipboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runnable.run();
            }
        });
    }
}
