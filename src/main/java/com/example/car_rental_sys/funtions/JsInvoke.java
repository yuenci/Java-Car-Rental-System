package com.example.car_rental_sys.funtions;

import com.example.car_rental_sys.ToolsLib.FXTools;

public class JsInvoke {
    public void world() {
        FXTools.changeScene("customerServicePage.fxml");
        System.out.println("Check payment clicked");
    }

    public void log(String text)
    {
        System.out.println(text);
    }
}
