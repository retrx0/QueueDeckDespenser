/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.model;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class HomePageButton extends Button{
    
    String name;
    StackPane stackPane;
    Service service;
    Node paneToRemove;
    
    public HomePageButton(String name) {
        this.name = name;
        init();
    }
    
    public HomePageButton(String name,StackPane sp, Service s) {
        this.name = name;
        this.stackPane = sp;
        this.service = s;
        init();
    }
    
    private void init(){
        
        this.setAlignment(Pos.CENTER);
        this.setText(name);
        this.setPrefHeight(150);
        this.setPrefWidth(300);
        this.setMaxSize(Integer.MAX_VALUE, 150);
        this.getStylesheets().clear();
        this.getStylesheets().add("/styles/Style-Default.css");
        this.getStyleClass().clear();
        this.getStyleClass().add("button");
        
        this.setOnAction((t) -> {
            PageView pv = new PageView(this.service.getServiceNo());
            pv.setCardStack(stackPane);
            pv.backBtn.setOnAction((sa) -> {
                doSlideInFromLeft(stackPane, paneToRemove);
            });
            doSlideInFromRight(stackPane, pv);
            
        });
    
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public StackPane getStackPane() {
        return stackPane;
    }
    
    public HomePageButton getButton(){
        return this;
    }
    
    void doSlideInFromRight(StackPane stackPane, Node paneToAdd) {
        paneToRemove = stackPane.getChildren().get(0);
        paneToAdd.translateXProperty().set(stackPane.getWidth());
        stackPane.getChildren().add(paneToAdd);
        KeyValue kv= new KeyValue(paneToAdd.translateXProperty(), 0, Interpolator.SPLINE(0.25, 0.1, 0.25, 1));
        KeyFrame  kf= new KeyFrame(Duration.millis(250), kv);
        Timeline timeline = new Timeline(kf);
        timeline.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        timeline.play();
    }
    void doSlideInFromLeft(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        paneToAdd.translateXProperty().set(-1 * stackPane.getWidth());
        stackPane.getChildren().add(paneToAdd);
        KeyValue kv = new KeyValue(paneToAdd.translateXProperty(), 0, Interpolator.SPLINE(0.25, 0.1, 0.25, 1));
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        Timeline timeline = new Timeline(kf);
        timeline.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        timeline.play();
    }
    
}