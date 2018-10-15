package de.trundicho.warpreader.view.timer;

import android.app.Activity;

import de.trundicho.warp.reader.core.controller.WarpUpdater;
import de.trundicho.warp.reader.core.view.api.timer.WarpTimer;
import de.trundicho.warp.reader.core.view.api.timer.WarpTimerFactory;

public class WarpTimerFactoryImpl implements WarpTimerFactory {

    private final Activity activity;

    public WarpTimerFactoryImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public WarpTimer createWarpTimer(WarpUpdater warpUpdater) {
        return new WarpTimerImpl(warpUpdater, activity);
    }

}
