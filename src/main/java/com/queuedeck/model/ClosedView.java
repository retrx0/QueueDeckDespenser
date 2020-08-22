/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class ClosedView extends AnchorPane{

    public ClosedView() {
        init();
    }
    
    private void init(){
        
        Label l1 = new Label("Sorry This Branch Is Closed");
        l1.setStyle("-fx-font-size: 40px;-fx-text-fill: white;");
        l1.setWrapText(true);
        Label l2 = new Label("Opening Hours");
        l2.setStyle("-fx-font-size: 30px;-fx-text-fill: white;");
        Label l3 = new Label("Monday - Friday");
        l3.setStyle("-fx-font-size: 25px;-fx-text-fill: white;");
        Label l4 = new Label("8:00 AM - 4:00PM");
        l4.setStyle("-fx-font-size: 25px;-fx-text-fill: white;");
        VBox vb = new VBox(30, l1,l2,l3,l4);
        vb.setPadding(new Insets(30,10,20,10));
        vb.setAlignment(Pos.CENTER);
        ClosedView.setTopAnchor(vb, 130.0);
        ClosedView.setLeftAnchor(vb, 64.0);
        ClosedView.setRightAnchor(vb, 64.0);
        ClosedView.setBottomAnchor(vb, 230.0);
        
        this.getStylesheets().clear();
        this.getStylesheets().add("/styles/Style-Default.css");
        this.getStyleClass().clear();
        this.getStyleClass().add("pane");
        this.getChildren().add(vb);
    }
    
}
