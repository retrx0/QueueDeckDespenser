/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.model;

import com.github.qrcode.QrcodeGenerator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sun.javafx.iio.bmp.BMPImageLoaderFactory;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import static javax.management.Query.value;
import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.ConfigurableBarcodeGenerator;
import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapBuilder;
import org.krysalis.barcode4j.output.bitmap.BitmapEncoder;
import org.krysalis.barcode4j.output.bitmap.ImageIOBitmapEncoder;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class PrintingPaneView extends AnchorPane{
    
    String ticketNo = "00";
    Label ticketNoLabel;

    public PrintingPaneView() {
        init();
    }

    public PrintingPaneView(String ticketNo) {
        this.ticketNo = ticketNo;
        init();
    }
    
    private void init(){
        
        Label topLabel = new Label("Please take your ticket and wait");
        topLabel.setAlignment(Pos.CENTER);
        topLabel.setWrapText(true);
        topLabel.setFont(Font.font(30));
        topLabel.setTextFill(Paint.valueOf("white"));
        PrintingPaneView.setTopAnchor(topLabel, 45.0);
        PrintingPaneView.setLeftAnchor(topLabel, 100.0);
        PrintingPaneView.setRightAnchor(topLabel, 100.0);
        
        ImageView ticketicon = new ImageView("/img/printingTicketD.png");
        ticketicon.setPreserveRatio(true);
        ticketicon.setSmooth(true);
        ticketicon.setFitHeight(230);
        ticketicon.setFitWidth(280);
        
        //ticketicon.relocate(350,300 );
        
        HBox hb =new HBox(ticketicon);
        hb.setAlignment(Pos.CENTER);
        hb.relocate(360, 260);
        PrintingPaneView.setLeftAnchor(hb, 100.0);
        PrintingPaneView.setRightAnchor(hb, 100.0);
        
        ticketNoLabel = new Label(ticketNo);
        ticketNoLabel.setAlignment(Pos.CENTER);
        ticketNoLabel.setFont(Font.font(95));
        ticketNoLabel.relocate(360, 300);
        PrintingPaneView.setLeftAnchor(ticketNoLabel, 100.0);
        PrintingPaneView.setRightAnchor(ticketNoLabel, 100.0);
        
        java.awt.Image qrcode  =BarcodeImage.getQRCode(ticketNo);
        Graphics g  = qrcode.getGraphics();
        
//        Image castImage = Image.class.cast(qrcode);
       // HBox h  =new HBox(qrcode);
        //ImageView i = new ImageView((Image)qrcode);
        
        this.getChildren().addAll(topLabel,hb,ticketNoLabel);
        this.getStylesheets().clear();
        this.getStylesheets().add("/styles/Style-Default.css");
        this.getStyleClass().clear();
        this.getStyleClass().add("pane");
    }

//    public static javafx.scene.image.Image convertToFxImage(java.awt.image.BufferedImage awtImage) {
//        
////        if (Image.class.impl_isExternalFormatSupported(BufferedImage.class)) {
////            return javafx.scene.image.Image.impl_fromExternalImage(awtImage);
////        } else {
////            return null;
////        }
//    }
    
    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Label getTicketNoLabel() {
        return ticketNoLabel;
    }

    public void setTicketNoLabel(Label ticketNoLabel) {
        this.ticketNoLabel = ticketNoLabel;
    }
    
    
    
}
