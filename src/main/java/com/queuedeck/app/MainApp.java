package com.queuedeck.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Style-Default.css");
        
        Image icon1 = new Image(this.getClass().getResource("/img/icon/icon1.png").toString());
        Image icon2 = new Image(this.getClass().getResource("/img/icon/icon2.png").toString());
        Image icon3 = new Image(this.getClass().getResource("/img/icon/icon3.png").toString());
        Image icon4 = new Image(this.getClass().getResource("/img/icon/icon4.png").toString());
        Image icon5 = new Image(this.getClass().getResource("/img/icon/icon5.png").toString());
        Image icon6 = new Image(this.getClass().getResource("/img/icon/icon6.png").toString());
        Image icon7 = new Image(this.getClass().getResource("/img/icon/icon7.png").toString());
        
        stage.getIcons().addAll(icon1,icon2,icon3,icon4,icon5,icon6,icon7);
        
        stage.setTitle("Queue Deck Dispenser App");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
