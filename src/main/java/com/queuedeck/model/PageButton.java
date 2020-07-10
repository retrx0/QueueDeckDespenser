/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.model;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class PageButton extends Button{

    String name;
    
    public PageButton(String name) {
        this.name = name;
        init();
    }
    
    private void init(){
        
        this.setAlignment(Pos.CENTER);
        this.setText(name);
        this.setPrefHeight(200);
        this.setPrefWidth(200);
        
        this.setOnAction((t) -> {
        });
        
    }
    
}
