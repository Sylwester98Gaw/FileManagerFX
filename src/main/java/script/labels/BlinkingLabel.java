/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.labels;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class BlinkingLabel {
    public void blinkingHidden(Label label){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> label.setStyle("-fx-background-color: #454444; -fx-background-radius: 5;")),
                new KeyFrame(Duration.seconds( 0.5), evt -> label.setStyle("-fx-background-color: transparent;")));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
