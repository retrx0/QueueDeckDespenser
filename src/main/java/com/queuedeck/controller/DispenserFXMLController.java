package com.queuedeck.controller;

import com.queuedeck.model.ClosedView;
import com.queuedeck.model.HomePageView;
import com.queuedeck.model.Service;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import com.queuedeck.pool.BasicConnectionPool;
import java.util.List;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

public class DispenserFXMLController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Global var">
    static final String URL_STRING = "jdbc:mysql://104.155.33.7:3306/ticketing";
    static final String USERNAME_STRING = "root";
    static final String PASSWORD_STRING = "rotflmao0000";
    int ticketCounter = 1;
    public static final Interpolator WEB_EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);
    public static BasicConnectionPool pool = BasicConnectionPool.create(URL_STRING, USERNAME_STRING, PASSWORD_STRING);
    boolean showedClosed = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Action Methods">
 
    void printTicket() {

    }

    void showNode(StackPane sp, Node nodeToShow) {
            sp.getChildren().clear();
            sp.getChildren().add(nodeToShow);
    }

    void doFadeIn(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        stackPane.getChildren().add(paneToAdd);
        FadeTransition ft = new FadeTransition(Duration.millis(300));
        ft.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        ft.setNode(paneToAdd);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    void doFadeOut(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        FadeTransition ft = new FadeTransition(Duration.millis(300));
        ft.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        ft.setNode(paneToRemove);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
    }

    void doFadeInDownTransition(StackPane stackPane, Node paneToAdd) {
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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FXML Var">
    @FXML
    private AnchorPane container;
    @FXML
    public StackPane cardsStackPane;
//</editor-fold>

    @Override
    public void initialize(URL Url, ResourceBundle rb) {
        HomePageView homePage = new HomePageView("homePane", new ImageView("/img/banner-logo.png"), cardsStackPane);
        showNode(cardsStackPane, homePage);
        Service s = new Service();
        ClosedView cv = new ClosedView();
        
        Platform.runLater(() -> {
            Window window = container.getScene().getWindow();
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    while (true) {
                        if (!window.isShowing()) {
                            break;
                        }
                        List<Service> serviceList = s.listServices();
                        LocalTime localTime = LocalTime.parse(String.valueOf(LocalTime.now()).substring(0, 2) + ":" + String.valueOf(LocalTime.now()).substring(3, 5) + ":" + "00");
                        for (int i = 0; i < serviceList.size(); i++) {
                            if (serviceList.get(i).getLocked()) {
                                homePage.getHomePageButtonList().get(i).setDisable(true);
                                LocalTime unlockT = LocalTime.parse(serviceList.get(i).getUnlockTime());
                                if (unlockT.equals(localTime)) {
                                    homePage.getHomePageButtonList().get(i).setDisable(false);
                                    s.unlockService(serviceList.get(i).getServiceNo());
                                }
                            } else {
                                homePage.getHomePageButtonList().get(i).setDisable(false);
                            }
                        }

                        if (LocalTime.now().isAfter(LocalTime.of(16, 15)) || LocalTime.now().isBefore(LocalTime.of(07, 59))) {
                            if(!showedClosed){
                                Platform.runLater(() -> {
                                    doFadeIn(cardsStackPane, cv);
                                });
                                showedClosed = true;
                            }
                        } else {
                            if (LocalTime.now().isAfter(LocalTime.of(8, 00, 00)) && LocalTime.now().isBefore(LocalTime.of(8, 00, 05))) {
                                Platform.runLater(() -> {
                                   doFadeIn(cardsStackPane, homePage); 
                                });
                            }
                        }
                        Thread.sleep(2000);
                    }
                    return null;
                }
            };
            new Thread(task).start();
        });

    }
}
