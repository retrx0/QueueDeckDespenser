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
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class PageView extends AnchorPane {

    String url = "jdbc:mysql://104.155.33.7:3306/ticketing";
    String username = "root";
    String password = "rotflmao0000";
    BasicConnectionPool pool = BasicConnectionPool.create(url, username, password);

    List<QueueServices> queServicesList = new ArrayList<>();
    
    public PageView() {
        init();
    }

    private void init() {

        Button backBtn = new Button("Back");
        backBtn.setPrefSize(100, 50);

        VBox queueServicesVB = new VBox();
        queueServicesVB.setFillWidth(true);
        queueServicesVB.setPadding(new Insets(10, 30, 10, 30));
        queueServicesVB.setAlignment(Pos.CENTER);

        try {
            Connection con = pool.getConnection();
            ResultSet rs = con.prepareStatement("select q_no,service from queue_services").executeQuery();
            int i = 0;
            while (rs.next()) {
                queServicesList.add(i, new QueueServices(rs.getString("q_no"), rs.getString("service")));
                i++;
            }

            pool.releaseConnection(con);
        } catch (SQLException s) {
        }

        VBox layoutVBox = new VBox(20, backBtn);
        layoutVBox.setAlignment(Pos.CENTER);
    }

}
