package de.trundicho.warpreader.view.timer;

import android.app.Activity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.trundicho.warp.reader.core.controller.WarpUpdater;
import de.trundicho.warp.reader.core.view.api.timer.WarpTimer;

class WarpTimerImpl implements WarpTimer {
    private final Runnable warper;
    private final ScheduledExecutorService scheduledExecutorService;

    WarpTimerImpl(WarpUpdater warpUpdater, Activity activity) {
        warper = () -> {
            activity.runOnUiThread(() -> warpUpdater.doNextWarp());
        };
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void doNextWarp(WarpUpdater warpUpdater) {
        warpUpdater.doNextWarp();
    }

    @Override
    public void cancel() {
    }

    @Override
    public void scheduleRepeating(int periodMillis) {
        System.out.println(System.currentTimeMillis() % 1000);
        scheduledExecutorService.schedule(warper, periodMillis, TimeUnit.MILLISECONDS);
    }

}
