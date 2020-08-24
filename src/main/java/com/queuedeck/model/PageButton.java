/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.model;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class PageButton extends JFXButton{

    String name;

    public PageButton() {
    }
    
    public PageButton(String name) {
        this.name = name;
        init();
    }
    
    private void init(){
        
        this.setAlignment(Pos.CENTER);
        this.setText(name);
        this.setPrefHeight(150);
        this.setPrefWidth(400);
        this.setMaxSize(Integer.MAX_VALUE, 150);
        this.setFocusTraversable(false);
        this.getStylesheets().clear();
        this.getStylesheets().add("/styles/Style-Default.css");
        this.getStyleClass().clear();
        this.getStyleClass().add("button");

    }

    public String getName() {
        return name;
    }
    
}
