/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.model;

import com.queuedeck.pool.BasicConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class HomePageView extends AnchorPane{
    
    String url = "jdbc:mysql://104.155.33.7:3306/ticketing";
    String username = "root";
    String password = "rotflmao0000";
    BasicConnectionPool pool = BasicConnectionPool.create(url, username, password);
    
    String name;
    ImageView logo;
    List<Service> servicesList = new ArrayList<>();

    public HomePageView() {
        init();
    }
    
    private void init(){
        
        HBox lghb = new HBox(logo);
        lghb.setAlignment(Pos.CENTER);
        
        VBox serviceVb = new VBox(10);
        try {
            Connection con = pool.getConnection();
            ResultSet rs = con.prepareStatement("select s_no,service,staff_no,locked,unlock_time from services").executeQuery();
            int i = 0;
            while (rs.next()) {
                Service s = new Service();
                s.setServiceNo(rs.getString("s_no"));
                s.setServiceName(rs.getString("service"));
                servicesList.add(s);
                serviceVb.getChildren().add(new PageButton(s.getServiceName()));
                i++;
            }
            
            pool.releaseConnection(con);
        } catch (SQLException s) {}
        
        Label cpright = new Label("Copyright © "+LocalDate.now().getYear()+"all rights reserved Queue Deck LLC");
        
        VBox layout = new VBox(20, lghb,serviceVb,cpright);
        layout.setAlignment(Pos.CENTER);
        layout.setFillWidth(true);
        this.getChildren().add(layout);
    }
    
    public void listServices(){}
}
