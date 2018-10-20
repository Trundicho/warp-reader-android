package de.trundicho.warpreader.view.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View;
import android.widget.Button;

import de.trundicho.warp.reader.core.view.api.widgets.ButtonWidget;
import de.trundicho.warp.reader.core.view.api.widgets.InputTextWidget;

class ClipboardWidgetImpl implements ButtonWidget {
    private final Button clipboardButton;

    ClipboardWidgetImpl(Button clipboardButton, final InputTextWidget inputTextWidget,
                        ClipboardManager clipboardManager) {
        this.clipboardButton = clipboardButton;
        clipboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData primaryClip = clipboardManager.getPrimaryClip();
                if (primaryClip  != null) {
                    if (primaryClip.getItemCount() > 0) {
                        ClipData.Item itemAt = primaryClip.getItemAt(0);
                        if (itemAt != null) {
                            CharSequence text = itemAt.getText();
                            if (text != null) {
                                inputTextWidget.setText(text.toString());
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void click() {
        clipboardButton.callOnClick();
    }
}
