/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.transitions;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Animate a fade in left effect on a node
 * 
 * Port of FadeInLeft from Animate.css http://daneden.me/animate by Dan Eden
 * 
 * {@literal @}keyframes fadeInLeft {
 * 	0% {
 * 		opacity: 0;
 * 		transform: translateX(-20px);
 * 	}
 * 	100% {
 * 		opacity: 1;
 * 		transform: translateX(0);
 * 	}
 * }

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class FadeInLeftTransition extends CachedTimelineTransition{
    
    /**
     * Create new FadeInLeftTransition
     * 
     * @param node The node to affect
     */
    public FadeInLeftTransition(final Node node) {
        super(
            node,
                new Timeline(
                    new KeyFrame(Duration.millis(0),    
                        new KeyValue(node.opacityProperty(), 1, WEB_EASE),
                        new KeyValue(node.translateXProperty(), -20, WEB_EASE)
                    ),
                    new KeyFrame(Duration.millis(500),    
                        new KeyValue(node.opacityProperty(), 1, WEB_EASE),
                        new KeyValue(node.translateXProperty(), 0, WEB_EASE)
                    )
                )
            );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0));
    }
    
}
