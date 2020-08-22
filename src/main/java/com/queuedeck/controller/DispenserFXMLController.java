package com.queuedeck.controller;

import com.queuedeck.model.HomePageView;
import com.queuedeck.model.Service;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.swing.Timer;
import com.queuedeck.pool.BasicConnectionPool;
import com.queuedeck.transitions.FadeInLeftTransition;
import com.queuedeck.transitions.FadeOutRightTransition;
import com.queuedeck.model.Ticket;
import java.util.List;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

public class DispenserFXMLController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Global variables">
    static final String Url = "jdbc:mysql://104.155.33.7:3306/ticketing";
    static final String Username = "root";
    static final String Password = "rotflmao0000";
    int ticketCounter = 1;
    String t1;
    int t2;
    String t3;
    public static BasicConnectionPool pool = BasicConnectionPool.create(Url, Username, Password);
    public String currentCusTag;
    public String newCusTag;
    public String serviceTag;
    public String othersTag;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Action Methods">
    public void buttonPerformAction(StackPane cardStack, Node card, String tag, String service) {
        Ticket oTkt = new Ticket();
        oTkt.setTicketNumber(ticketCounter++);
        oTkt.setTag(tag);
        oTkt.getTimeStamp();
        doFadeInDownTransition(cardsStackPane, card);
        Timer tt = new Timer(4000, (ae) -> {
            Platform.runLater(() -> {
                doFadeIn(cardStack, homePane);
            });
        });
        tt.start();
        tt.setRepeats(false);
        try {
            Connection con = pool.getConnection();
            Statement stmt = con.createStatement();
            String t4 = LocalDate.now().toString();
            PreparedStatement max_ticket = con.prepareStatement("select max(t_no) from tickets where tag = '" + tag + "' and t_date = '" + t4 + "' ");
            ResultSet mt = max_ticket.executeQuery();
            while (mt.next()) {
                t2 = mt.getInt("max(t_no)");
                if (t2 > 0) {
                    t2 += 1;
                } else {
                    t2 = 1;
                }
            }
            String cmd = "insert into tickets (tag,t_no, time_created, service, t_date ) values('" + oTkt.getTag() + "','" + t2 + "','" + oTkt.getTimeStamp().toLocalTime().minusHours(1).toString().substring(0, 8) + "','" + service + "','" + t4 + "')";
            ticketNumberLabel.setText(oTkt.getTag() + t2);
            stmt.executeUpdate(cmd);
            pool.releaseConnection(con);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    void printTicket() {

    }

    void showNode(StackPane sp, Node nodeToShow) {
        sp.getChildren().clear();
        sp.getChildren().add(nodeToShow);
    }

    void showNodeWithTransitionIN(StackPane sp, Node nodeToShow) {
        try {
            FadeInLeftTransition filt = new FadeInLeftTransition(nodeToShow);
            filt.play();
            sp.getChildren().clear();
            sp.getChildren().add(nodeToShow);
        } catch (Exception e) {
        }
    }

    void showNodeWithTransitionOut(StackPane sp, Node nodeToShow) {
        try {
            FadeOutRightTransition fot = new FadeOutRightTransition(nodeToShow);
            fot.play();
            sp.getChildren().clear();
            sp.getChildren().add(nodeToShow);

        } catch (Exception e) {
        }
    }
    public static final Interpolator WEB_EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);

    void doSlideInFromRight(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        paneToAdd.translateXProperty().set(stackPane.getWidth());
        stackPane.getChildren().add(paneToAdd);
        KeyValue kv = new KeyValue(paneToAdd.translateXProperty(), 0, Interpolator.SPLINE(0.25, 0.1, 0.25, 1));
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        Timeline timeline = new Timeline(kf);
        timeline.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        timeline.play();
    }

    public void doSlideInFromLeft(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        paneToAdd.translateXProperty().set(-1 * stackPane.getWidth());
        stackPane.getChildren().add(paneToAdd);
        KeyValue kv = new KeyValue(paneToAdd.translateXProperty(), 0, Interpolator.SPLINE(0.25, 0.1, 0.25, 1));
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        Timeline timeline = new Timeline(kf);
        timeline.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        timeline.play();
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

    //<editor-fold defaultstate="collapsed" desc="FXML Variables">
    @FXML
    private AnchorPane currentCustomerNodeP1;
    @FXML
    private AnchorPane currentCustomerNodeP2;
    @FXML
    Button currentCusBtn;
    @FXML
    Button newCusBtn;
    @FXML
    Button servicesBtn;
    @FXML
    Button otherBtn;
    @FXML
    AnchorPane closedNode;
    @FXML
    private Button currentCustomerBackBtn;
    @FXML
    private AnchorPane newCustomerNode;
    @FXML
    private Button newCustomerBackBtn;
    @FXML
    private AnchorPane servicesNode;
    @FXML
    private Button servicesBackBtn;
    @FXML
    private AnchorPane otherNode;
    @FXML
    private AnchorPane container;
    @FXML
    private Button otherBackBtn;
    @FXML
    public AnchorPane homePane;
    @FXML
    private Label ticketNumberLabel;
    @FXML
    private Button otherButton;
    @FXML
    public AnchorPane printingTicketPane;
    @FXML
    public StackPane cardsStackPane;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Back Buttons">
    @FXML
    void currentCusBackBtnPressedP1() {
        doSlideInFromLeft(cardsStackPane, homePane);
    }

    @FXML
    void currentCustomerBackBtnPressed() {
        doSlideInFromLeft(cardsStackPane, currentCustomerNodeP1);
    }

    @FXML
    void newCustomerBackBtnPressed() {
        doSlideInFromLeft(cardsStackPane, homePane);
    }

    @FXML
    void servicesBackBtnPressed() {
        doSlideInFromLeft(cardsStackPane, homePane);
    }

    @FXML
    void otherBackBtnPressed() {
        doSlideInFromLeft(cardsStackPane, homePane);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Home Buttons">
    @FXML
    void currentCusButtonClicked(ActionEvent event) {
        doSlideInFromRight(cardsStackPane, currentCustomerNodeP1);
    }

    @FXML
    void newCustomerButtonClicked(ActionEvent event) {
        doSlideInFromRight(cardsStackPane, newCustomerNode);
    }

    @FXML
    void servicesButtonClicked(ActionEvent event) {
        doSlideInFromRight(cardsStackPane, servicesNode);
    }

    @FXML
    void otherButtonClicked(ActionEvent event) {
        doSlideInFromRight(cardsStackPane, otherNode);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Current Customer">
    @FXML
    void current_SavingsBtnPressed() {
        doSlideInFromRight(cardsStackPane, currentCustomerNodeP2);
    }

    @FXML
    void bussiness_TimeDepositBtnPressed() {
        doSlideInFromRight(cardsStackPane, currentCustomerNodeP2);
    }

    @FXML
    void dormAccBtnPressed() {
        doSlideInFromRight(cardsStackPane, currentCustomerNodeP2);
    }

    @FXML
    void creditAndDebitBtnPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, othersTag, "Credit/Debit cards");
    }

    @FXML
    void depositBtnPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, currentCusTag, "Deposit");
    }

    @FXML
    void mobileBankingBtnPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, serviceTag, "Mobile Banking");
    }

    @FXML
    void transferBtnPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, currentCusTag, "Transfer");
    }

    @FXML
    void withdrawalBtnPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, currentCusTag, "Withdrawals");
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="New Customer">
    @FXML
    void inquiryBtnPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, newCusTag, "Inquiries");
    }

    @FXML
    void openNewAccountBtnPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, newCusTag, "Open New Account");
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Services">
    @FXML
    void paymentOfBillsButtonPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, serviceTag, "Payment of Bills");
    }

    @FXML
    void safeDepositButtonPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, serviceTag, "Safe Deposit");
    }

    @FXML
    void insuranceButtonPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, serviceTag, "Insurance");
    }

    @FXML
    void loanAndInvButtonPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, serviceTag, "Loan/Investment");
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Other">
    @FXML
    void issuingOfChequesPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, othersTag, "Cheque Issuing");
    }

    @FXML
    void buyingAndSellingForeignPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, othersTag, "Buy/Sell $");
    }

    @FXML
    void accountReactivationPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, othersTag, "Acct Reactivation");
    }

    @FXML
    void complaintsAndEnqPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, othersTag, "Complaints/Enq");
    }

    @FXML
    void creditAndDebitPressed() {
        buttonPerformAction(cardsStackPane, printingTicketPane, othersTag, "Credit/Debit Cards");
    }
//</editor-fold>

    @Override
    public void initialize(URL Url, ResourceBundle rb) {
        HomePageView homePage = new HomePageView("homePane", new ImageView("/img/banner-logo.png"), cardsStackPane);
        showNode(cardsStackPane, homePage);
        //showNode(cardsStackPane, homePane);
        Service s = new Service();
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
                            //doFadeIn(cardsStackPane, closedNode);
                        } else {
                            if (LocalTime.now().isAfter(LocalTime.of(8, 00, 00)) && LocalTime.now().isBefore(LocalTime.of(8, 00, 05))) {
                                doFadeIn(cardsStackPane, homePane);
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
