package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class UIToDoController {
    @FXML
    Label label1,label2,time1,time2;

    @FXML
    Pane pane1,pane2,addTaskPane;

    @FXML
    CheckBox checkBox1,checkBox2;

    @FXML
    DatePicker datePicker;

    @FXML
    TextArea taskContent;

    private String currentToDoID1 = null;
    private String currentToDoID2 = null;

    @FXML
    public void initialize() {
//        System.out.println("UIToDoController");
        initData();
        initEvent();
        //showAddTaskPane();
        hideAddTaskPane();
    }

    private void initData(){
        ArrayList<String[]> list = DataTools.getAdminToDo();
        if(list.size() == 0){
            pane1.setVisible(false);
            pane2.setVisible(false);}
        else if(list.size() == 1){
            pane2.setVisible(false);
            label1.setText(list.get(0)[1]);
            time1.setText(list.get(0)[3]);
            currentToDoID1 = list.get(0)[0];
        }
        else{
            label1.setText(list.get(0)[1]);
            time1.setText(list.get(0)[3]);
            label2.setText(list.get(1)[1]);
            time2.setText(list.get(1)[3]);
            currentToDoID1 = list.get(0)[0];
            currentToDoID2 = list.get(1)[0];
        }
        label1.getStyleClass().remove("delete");
        label2.getStyleClass().remove("delete");
        checkBox1.setSelected(false);
        checkBox2.setSelected(false);
    }

    private void initEvent(){
        pane1.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            deleteToDo1();
        });

        pane2.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            deleteToDo2();
        });
    }
    private void  deleteToDo1() {
        checkBox1.setSelected(true);
        label1.getStyleClass().add("delete");
        DataTools.setTaskAsDone(Integer.parseInt(currentToDoID1));
        //use timeLine to wait for 1 second
        refresh();
    }

    private void  deleteToDo2() {
        checkBox2.setSelected(true);
        label1.getStyleClass().add("delete");
        DataTools.setTaskAsDone(Integer.parseInt(currentToDoID2));
        refresh();
    }


    private void refresh(){
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new javafx.animation.KeyFrame(
                        javafx.util.Duration.millis(1000),
                        ae -> initData()
                )
        );
        timeline.play();
    }

    @FXML
    private void showAddTaskPane(){
        if(addTaskPane.isVisible()){
            hideAddTaskPane();
            return;
        }
        addTaskPane.setVisible(true);
        Platform.runLater(() -> taskContent.requestFocus());
        datePicker.setPromptText(DateTools.getNow());
    }

    private void hideAddTaskPane(){
        addTaskPane.setVisible(false);
    }

    @FXML
    private void addTaskBtnClick(){
        String content = taskContent.getText();
        String date = datePicker.getValue().toString();
        DataTools.addTask(content,date);
        initData();
        hideAddTaskPane();
    }
}
