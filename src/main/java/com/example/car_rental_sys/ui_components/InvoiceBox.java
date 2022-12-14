package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class InvoiceBox {

    public static InvoiceBox instance;
    public Stage stageInvoice = null;

    public InvoiceBox() {
        //DO NOTHING
        instance = this;
    }

    public void showInvoiceStage() throws IOException {
        stageInvoice = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/Invoice.fxml"));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stageInvoice.setX((screenBounds.getWidth() - 500) / 2);
        stageInvoice.setY((screenBounds.getHeight() - 600) / 2);
        Scene scene = new Scene(fxmlLoader.load(), 500, 600);
        stageInvoice.initStyle(StageStyle.UNDECORATED);
        stageInvoice.setTitle("Order Invoice");
        stageInvoice.setScene(scene);
        stageInvoice.show();
    }

    public void closeInvoiceStage() throws IOException {
        stageInvoice.close();
    }
}
