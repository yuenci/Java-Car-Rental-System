package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;

public class PasswordPageController {

    @FXML
    private Pane panelSettings;
    @FXML
    private Button btnChangePwd;
    private boolean showPassword = false;

    @FXML
    private ImageView ivAvatar;

    @FXML
    private TextField txtOldPwd;

    @FXML
    private TextField txtReEnterPwd;

    @FXML
    private ImageView imgOldPwd;

    @FXML
    private Label ivUsername;

    @FXML
    private TextField txtNewPwd;

    @FXML
    private void initialize(){
        initTheme();
        initDefaultText();
        initAvatar();
    }

    private void initDefaultText(){
        ivUsername.setText("");
        changeDisplayPwdStyle();
    }

    private void initAvatar(){
//        Image image = new Image("file:src/main/resources/images/avatar.png");
//        ivAvatar.setImage(image);
    }

    private void changeDisplayPwdStyle(){
        if(showPassword){
            txtOldPwd.setText("");
        }else{
            //String defaultTxt = "abcdfgsd";
            //replace the characters with *
            //txtOldPwd.setText(defaultTxt.replaceAll("\\.", "*"));
        }
    }

    private void checkChangePassword(){
        String newPwd = txtNewPwd.getText();
        String reEnterPwd = txtReEnterPwd.getText();
        if(!newPwd.equals("") && !reEnterPwd.equals("")){
            if(newPwd.equals(reEnterPwd)){
                //change password
                System.out.println("Change password");
            }else{
                //show warning
                System.out.println("Password not match or empty");
            }
        }
    }

    @FXML
    void changePasswordClicked(MouseEvent event) {
        checkChangePassword();
    }

    @FXML
    void imgOldPwdClicked(MouseEvent event) {
        if(showPassword){
            imgOldPwd.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/view.png"));
            showPassword = false;
        }else{
            imgOldPwd.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/hidden.png"));
            showPassword = true;
        }
        changeDisplayPwdStyle();
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            panelSettings.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/settingComponentDark.css")
                            .toURI().toString());
        }
    }
}