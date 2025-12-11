package com.quizapp.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Helper class for creating countdown timers.
 * UI layer will attach listeners to update labels or handle timeout.
 */
public class TimerUtil {

    private Timeline timeline;

    /**
     * Starts a countdown.
     * @param seconds initial time.
     * @param onTick callback every second.
     * @param onFinish callback on timeout.
     */
    public void startTimer(int seconds, Runnable onTick, Runnable onFinish) {
        final int[] timeLeft = {seconds};

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    timeLeft[0]--;
                    onTick.run();

                    if (timeLeft[0] <= 0) {
                        timeline.stop();
                        onFinish.run();
                    }
                })
        );

        timeline.setCycleCount(seconds);
        timeline.play();
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
