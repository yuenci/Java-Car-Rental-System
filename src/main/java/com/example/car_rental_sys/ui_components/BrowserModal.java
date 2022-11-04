package com.example.car_rental_sys.ui_components;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Function;

public class BrowserModal extends BrowserFrame{
    public String modal = "ApplicationModal";
    public Stage stage  = null;
    public BrowserModal(int width, int height, String url) {
        super(width, height, url);
        initialize();
    }
    private void initialize(){
        stage = super.stage;
    }

    public void setModality(){
        super.stage.initModality(Modality.APPLICATION_MODAL);
    }

    public void setModality(Modality modal){
        super.stage.initModality(modal);
    }

    public void setFunction(Function<String,Void> func){
        super.callBackFunc = func;
    }

}
