/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.model;

import com.jfoenix.controls.JFXButton;
import static com.queuedeck.controller.DispenserFXMLController.WEB_EASE;
import static com.queuedeck.controller.DispenserFXMLController.pool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javax.swing.Timer;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class PageView extends AnchorPane {

    List<QueueServices> queServicesList = new ArrayList<>();
    List<Service> servList = new ArrayList<>();
    JFXButton backBtn = new JFXButton("Back");
    
    VBox queueServicesVB;
    StackPane cardStack;
    String service_no;
    
    PrintingPaneView printingPaneView = new PrintingPaneView();

    public PageView() {
        init();
    }
    
    public PageView(String service_no) {
        this.service_no = service_no;
        init();
    }

    public PageView(StackPane sp, Node... nodes) {
        queueServicesVB.getChildren().addAll(nodes);
        this.cardStack = sp;
        init();
    }

    private void init() {
        backBtn.setPrefSize(100, 50);
        backBtn.setLayoutX(14);
        backBtn.setLayoutY(35);

        queueServicesVB = new VBox();
        queueServicesVB.setFillWidth(true);
        queueServicesVB.setPadding(new Insets(10));
        queueServicesVB.setAlignment(Pos.CENTER);
        queueServicesVB.setSpacing(15);
        queueServicesVB.setPrefHeight(400);
        
        QueueServices q = new QueueServices();
        List<QueueServices> ql = q.listQueueServices(this.service_no);
        for(int i=0; i<ql.size(); i++){
            PageButton pb = new PageButton(ql.get(i).getQueueServiceName());
            pb.setOnAction((t) -> {
                buttonPerformAction(cardStack, printingPaneView, this.service_no, pb.getName());
            });
            queueServicesVB.getChildren().add(i, pb);
        }
        
        ScrollPane sp = new ScrollPane(queueServicesVB);
        sp.setPadding(new Insets(10, 5, 10, 5));
        sp.setFitToWidth(true);
        sp.setPrefHeight(600);
        sp.setLayoutX(145);
        sp.setLayoutY(140);
        
        this.getChildren().addAll(backBtn,queueServicesVB);
        this.getStylesheets().clear();
        this.getStylesheets().add("/styles/Style-Default.css");
        this.getStyleClass().clear();
        this.getStyleClass().add("pane");
        PageView.setTopAnchor(queueServicesVB, 130.0);
        PageView.setLeftAnchor(queueServicesVB, 64.0);
        PageView.setRightAnchor(queueServicesVB, 64.0);
        PageView.setBottomAnchor(queueServicesVB, 65.0);
        
    }

    int ticketCounter = 1;
    String t1;int  t2;String t3;
    public void buttonPerformAction(StackPane cardStack,PrintingPaneView card, String tag, String service){
                Ticket oTkt = new Ticket();
                oTkt.setTicketNumber(ticketCounter++);
                oTkt.setTag(tag);
                oTkt.getTimeStamp();
                Node paneToRemove = cardStack.getChildren().get(0);
                doFadeInDownTransition(cardStack, card);
                Timer tt = new Timer(4000, (ae) -> {
                    Platform.runLater(() -> {
                        //doFadeIn(cardStack, paneToRemove);
                        backBtn.fire();
                    });
                });
                tt.start();
                tt.setRepeats(false); 
                try{
                        Connection con = pool.getConnection();
                        Statement stmt=con.createStatement();  
                        String t4 = LocalDate.now().toString();
                        PreparedStatement max_ticket = con.prepareStatement("select max(t_no) from tickets where tag = '"+tag+"' and t_date = '"+t4+"' ");
                        ResultSet mt = max_ticket.executeQuery();
                        while(mt.next()){
                           t2 = mt.getInt("max(t_no)");
                           if(t2 >0 ){
                               t2 += 1;
                           }
                           else{t2=1;}
                        }
                        String cmd = "insert into tickets (tag,t_no, time_created, service, t_date ) values('"+oTkt.getTag()+"','"+t2+"','"+oTkt.getTimeStamp().toLocalTime().minusHours(1).toString().substring(0, 8)+"','"+service+"','"+t4+"')";
                        card.getTicketNoLabel().setText(oTkt.getTag()+t2);
                        stmt.executeUpdate(cmd);
                        pool.releaseConnection(con);
                }
                catch(SQLException ex){System.out.println(ex);}
        }
    
        void doFadeInDownTransition(StackPane stackPane, Node paneToAdd){
            Node paneToRemove = stackPane.getChildren().get(0);
            paneToAdd.translateYProperty().set(-1 * stackPane.getHeight());
            stackPane.getChildren().add(paneToAdd);
            Timeline tl = new Timeline(
                   new KeyFrame(Duration.millis(0),    
                        new KeyValue(paneToAdd.opacityProperty(), 0, WEB_EASE),
                        new KeyValue(paneToAdd.translateYProperty(), -20, WEB_EASE)
                    ),
                    new KeyFrame(Duration.millis(1000),    
                        new KeyValue(paneToAdd.opacityProperty(), 1, WEB_EASE),
                        new KeyValue(paneToAdd.translateYProperty(), 0, WEB_EASE)
                    ));
            tl.setOnFinished(evt -> stackPane.getChildren().remove(paneToRemove));
            tl.play();
        }
        
        void doFadeIn(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        stackPane.getChildren().add(paneToAdd);
         FadeTransition ft = new FadeTransition(Duration.millis(200));
        ft.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        ft.setNode(paneToAdd);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    public void setCardStack(StackPane cardStack) {
        this.cardStack = cardStack;
    }
        
        
}
