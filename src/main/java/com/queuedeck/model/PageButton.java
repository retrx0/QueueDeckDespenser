/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.model;

import com.queuedeck.controller.DispenserFXMLController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class PageButton extends Button{

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
        this.getStylesheets().clear();
        this.getStylesheets().add("/styles/Style-Default.css");
        this.getStyleClass().clear();
        this.getStyleClass().add("button");

    }

    public String getName() {
        return name;
    }
    
}
