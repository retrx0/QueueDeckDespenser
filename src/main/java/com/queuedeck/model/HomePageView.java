/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.model;

import com.queuedeck.controller.DispenserFXMLController;
import com.queuedeck.pool.BasicConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class HomePageView extends AnchorPane{
    
    String name;
    ImageView logo;
    List<HomePageButton> btnList = new ArrayList<>();
    StackPane cardStack;

    
    public HomePageView() {
        init();
    }

    public HomePageView(String name, ImageView logo) {
        this.name = name;
        this.logo = logo;
        init();
    }
    
    public HomePageView(String name, ImageView logo,StackPane sp) {
        this.name = name;
        this.logo = logo;
        this.cardStack = sp;
        init();
    }
    
    private void init(){
        
        HBox lghb = new HBox(logo);
        lghb.setAlignment(Pos.CENTER);
        logo.setPreserveRatio(true);
        logo.setFitHeight(120);
        logo.setFitWidth(360);
        logo.setSmooth(true);
        lghb.setLayoutX(147);
        lghb.setLayoutY(14);
        
        VBox serviceVb = new VBox(10);
        serviceVb.setAlignment(Pos.CENTER);
        serviceVb.setFillWidth(true);
        serviceVb.setPadding(new Insets(15));
        serviceVb.setMaxHeight(Integer.MAX_VALUE);
        
        
        Service s = new Service();
        List<Service> servicesList = s.listServices();
        for(int i=0; i< servicesList.size();i++){
            HomePageButton hpb = new HomePageButton(servicesList.get(i).getServiceName(), cardStack, servicesList.get(i));
            btnList.add(i, hpb);
            serviceVb.getChildren().add(i,hpb);
        }
        ScrollPane sp = new ScrollPane(serviceVb);
        sp.setPadding(new Insets(10, 5, 10, 5));
        sp.setFitToWidth(true);
        sp.setPrefHeight(400);
        sp.setLayoutX(125);
        sp.setLayoutY(145);
        
        this.getChildren().addAll(lghb,serviceVb);
        this.getStylesheets().clear();
        this.getStylesheets().add("/styles/Style-Default.css");
        this.getStyleClass().clear();
        this.getStyleClass().add("pane");
        this.setPrefSize(500, 550);
        
        HomePageView.setTopAnchor(lghb, 15.0);
        HomePageView.setLeftAnchor(lghb, 145.0);
        HomePageView.setRightAnchor(lghb, 145.0);
        
        HomePageView.setTopAnchor(serviceVb, 145.0);
        HomePageView.setLeftAnchor(serviceVb, 80.0);
        HomePageView.setRightAnchor(serviceVb, 80.0);
        HomePageView.setBottomAnchor(serviceVb, 40.0);
        
    }

    public List<HomePageButton> getHomePageButtonList() {
        return btnList;
    }
    
}
