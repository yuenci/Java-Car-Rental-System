package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.User;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class CustomerSideBarController {
    @FXML
    Pane item1, item2, item3, item4, item5, item6;

    @FXML
    Label nameTextLabel, emailTextLabel,orderNumLabel;

    @FXML
    ImageView avatarImageView, vipBadgeImageView, vipCardImageView;

    @FXML
    Pane bar1, bar2, bar3, bar4, bar5;

    @FXML
    Button customerSideBarUpdateBtn;

    Customer customer = (Customer) StatusContainer.currentUser;

    @FXML
    private void initialize() {
        initUserData();
        initMenuEvent();
        initLabelEvent();
        intCustomerImages();
    }


    private void initMenuEvent() {
        item1.setOnMouseClicked(event -> changeMenuStyle(item1));
        item2.setOnMouseClicked(event -> {
            changeMenuStyle(item2);
            FXTools.changeScene("messagePage.fxml");
        });
        item3.setOnMouseClicked(event -> changeMenuStyle(item3));
        item4.setOnMouseClicked(event -> changeMenuStyle(item4));
        item5.setOnMouseClicked(event -> changeMenuStyle(item5));
        item6.setOnMouseClicked(event -> {
            changeMenuStyle(item6);
            FXTools.changeScene("mainPage.fxml");
        });

    }

    private void changeMenuStyle(Pane activePane) {
        //System.out.println(activePane.toString() + "changeMenuStyle-line229");
        item1.getStyleClass().remove("menuItemActive");
        item2.getStyleClass().remove("menuItemActive");
        item3.getStyleClass().remove("menuItemActive");
        item4.getStyleClass().remove("menuItemActive");
        item5.getStyleClass().remove("menuItemActive");
        item6.getStyleClass().remove("menuItemActive");

        activePane.getStyleClass().add("menuItemActive");
    }

    private void initLabelEvent() {
        nameTextLabel.setAlignment(Pos.CENTER);
        emailTextLabel.setAlignment(Pos.CENTER);
    }

    private void initUserData() {
        User user = StatusContainer.currentUser;
        //user.get
    }

    private void intCustomerImages() {

        nameTextLabel.setText(customer.getUserName());
        emailTextLabel.setText(customer.getEmail());

        avatarImageView.setImage(customer.getAvatar());
        vipBadgeImageView.setImage(customer.getVipBadge());
        vipCardImageView.setImage(customer.getVipCard());

        setVipCard(customer.getOrderNum());
    }

    private void setVipCard(int orderNum) {
        orderNumLabel.setVisible(false);
        Pane[] bans = {bar1, bar2, bar3, bar4, bar5};
        if (orderNum > 20) {
            for (Pane ban : bans) {
                ban.setVisible(false);
            }
            orderNumLabel.setText(String.valueOf(customer.getOrderNum()));
            orderNumLabel.setVisible(true);
            customerSideBarUpdateBtn.setVisible(false);
        } else {
            // Take remainder
            int remainder = customer.getOrderNum() % 5;
            remainder = remainder == 0 ? 5 : remainder;
            for (int i = 0; i < remainder; i++) {
                bans[i].getStyleClass().add("barActive");
            }
        }

    }

    @FXML
    private void updateBtnClick() {
        System.out.println("updateBtnClick");
    }
}
