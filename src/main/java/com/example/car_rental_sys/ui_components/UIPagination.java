package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.sqlParser.FileOperate;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class UIPagination extends Pane {
    //generate a self-defined pagination component
    private static HBox mainBox;
    private static FlowPane flowPane;
    public static Button leftButton;
    public static Button rightButton;
    public static Button minPageButton;    //the first page button
    public static Button maxPageButton;    //the last page button

    private static ArrayList<Node> nodes;    //store the components of this pagination

    public static boolean showDotNext = false;
    public static boolean showDotPrevious = false;

    public static boolean focusMinButton = true;
    public static boolean focusMaxButton = false;
    public static boolean focusOnFirst = false;
    public static boolean focusOnSecond = false;
    public static boolean focusOnCenter = false;
    public static boolean focusOnFourth = false;
    public static boolean focusOnLast = false;

    public static int currentPageNumber = 1;
    public static int totalPageNumber;


    public UIPagination(){
        initMain();
    }

    public UIPagination(String query){

    }

    private void initMain(){
        countTotalPageNumber();
        initMainBox();
        initEvent();
    }

    private void countTotalPageNumber(){
        //select the data from database with query
        //count the total number of data
        //ArrayList<String[]> data = UIPaginationController.selectData();
        ArrayList<String[]> data = FileOperate.readFileToArray(ConfigFile.fakeData);
        int total = data.size();
        totalPageNumber = (int)Math.ceil((double)total/12);
        System.out.println("totalData: " + total);
        System.out.println("totalPageNumber: " + totalPageNumber);
        if(totalPageNumber > 7){
            showDotNext = true;
            //max;
        }
    }

    private void initMainBox(){
        mainBox = new HBox();
        mainBox.setPrefHeight(30);
        mainBox.setLayoutX(0);
        mainBox.setLayoutY(0);
        nodes = initElements();

        //for (Node node : nodes){
//           HBox.setMargin(node,new Insets(0,1,0,1));
//        }

        mainBox.getChildren().addAll(nodes);
        this.getChildren().add(mainBox);
    }

    private static ArrayList<Node> initElements(){
        nodes = new ArrayList<>();
        //
        leftButton = new UIPaginationCard(-3);
        nodes.add(leftButton);
        //
        minPageButton = new UIPaginationCard(1);
        if(currentPageNumber == 1 && focusMinButton){
            minPageButton.getStyleClass().add("focus-style");
        }
        nodes.add(minPageButton);
        //
        Button dotPreviousButton;
        if(showDotPrevious || currentPageNumber > 5){
            dotPreviousButton = new UIPaginationCard(-1);
            nodes.add(dotPreviousButton);
        }
        //
        flowPane = initFlowPane();
        nodes.add(flowPane);
        //
        Button dotNextButton;
        if(showDotNext || currentPageNumber < totalPageNumber - 4){
            dotNextButton = new UIPaginationCard(-2);
            nodes.add(dotNextButton);
        }
        //
        maxPageButton = null;
        if(totalPageNumber >= 2){
            maxPageButton = new UIPaginationCard(totalPageNumber);
            nodes.add(maxPageButton);
        }
        if(focusMaxButton){
            assert maxPageButton != null;
            maxPageButton.getStyleClass().add("focus-style");
        }

        rightButton = new UIPaginationCard(-4);
        nodes.add(rightButton);

        return nodes;
    }

    private static ArrayList<Integer> getDisplayNumberList(){
        ArrayList<Integer> displayNumberList = new ArrayList<>();
        int maxDisplayNumber = 7;
        if(totalPageNumber < maxDisplayNumber){
            for (int i = 2; i <= totalPageNumber; i++) {
                displayNumberList.add(i);
            }
            showDotNext = false;
        }else{
            if(currentPageNumber <= 3 ){
                for (int i = 2; i <= 5; i++) {
                    displayNumberList.add(i);
                }
            }else if(currentPageNumber == 4){
                checkShowDotPrevious();
                for (int i = 2; i <= 6; i++) {
                    displayNumberList.add(i);
                }
            }
            else if(currentPageNumber == 5){
                checkShowDotPrevious();
                for (int i = 2; i <= 7; i++) {
                    displayNumberList.add(i);
                }
            }
            else if(currentPageNumber == 6){
                checkShowDotPrevious();
                for (int i = 4; i <= 8; i++) {
                    displayNumberList.add(i);
                }
            }
            else if(currentPageNumber == totalPageNumber - 4){
                checkShowDotNext();
                for (int i = totalPageNumber - 6; i <= totalPageNumber-1; i++) {
                    displayNumberList.add(i);
                }
                showDotPrevious = true;
            }
            else if(currentPageNumber == totalPageNumber - 3){
                checkShowDotNext();
                //print "i m 31"
                System.out.println("i m 31");

                for (int i = totalPageNumber - 5; i <= totalPageNumber-1; i++) {
                    displayNumberList.add(i);
                }
                showDotPrevious = true;
            }
            else if(currentPageNumber == totalPageNumber - 2){
                checkShowDotNext();
                for (int i = totalPageNumber - 4; i <= totalPageNumber - 1; i++) {
                    displayNumberList.add(i);
                }
                showDotPrevious = true;
            }
            else if(currentPageNumber >= totalPageNumber - 1){
                checkShowDotNext();
                for (int i = totalPageNumber - 4; i <= totalPageNumber - 1; i++) {
                    displayNumberList.add(i);
                }
                showDotPrevious = true;
            }
            else{
                checkShowDotNext();
                for (int i = currentPageNumber - 2; i <= currentPageNumber + 2; i++) {
                    displayNumberList.add(i);
                }
                showDotPrevious = true;
            }
        }
        //print list as string
        //System.out.println("displayNumberList: " + displayNumberList);

        return displayNumberList;
    }

    private static ArrayList<UIPaginationCard> initPaginationCardList(){
        //store the pagination card
        ArrayList<UIPaginationCard> paginationCardList = new ArrayList<>();
        ArrayList<Integer> displayNumberList = getDisplayNumberList();

        for (Integer integer : displayNumberList) {
            paginationCardList.add(new UIPaginationCard(integer));
        }
        return paginationCardList;
    }

    private static FlowPane initFlowPane(){
        ArrayList<UIPaginationCard> cards = initPaginationCardList();
        flowPane = new FlowPane();
        //add all cards into flowPane
        //if focusOnCenter is true, set focus-style on the third pagination on array
        if(focusOnFirst){
            cards.get(0).getStyleClass().add("focus-style");
        }
        if(focusOnSecond){
            cards.get(1).getStyleClass().add("focus-style");
        }
        if(focusOnCenter) {
            cards.get(2).getStyleClass().add("focus-style");
        }
        if (focusOnFourth){
            cards.get(3).getStyleClass().add("focus-style");
        }
        if(focusOnLast){
            cards.get(cards.size()-1).getStyleClass().add("focus-style");
        }
        for (UIPaginationCard card : cards) {
            flowPane.getChildren().add(card);
        }
        //get the length of cards then set the width of flowPane
        int length = cards.size();
        flowPane.setPrefWidth(length * 30);

        return flowPane;
    }

    private static void refreshMainBox(){
        //refresh the mainBox
        Thread thread = new Thread(() -> Platform.runLater(() -> {
            mainBox.getChildren().clear();
            nodes = initElements();
            mainBox.getChildren().addAll(nodes);
        }));
        thread.start();
    }

    public static void renewRequest(){
        refreshMainBox();
    }

    private static void checkShowDotPrevious(){
        if (showDotPrevious){
            showDotPrevious = false;
            refreshMainBox();
        }
    }

    private static void checkShowDotNext() {
        if (showDotNext) {
            showDotNext = false;
            refreshMainBox();
        }
    }
    //initEvent
    private void initEvent(){
        leftButton.setOnAction(event -> {
            //System.out.println("leftButton has clicked");
            if(currentPageNumber >= 1){
                currentPageNumber--;
                refreshMainBox();
            }
        });

        minPageButton.setOnAction(event -> {
            currentPageNumber = 1;
            checkShowDotPrevious();
            //System.out.println("currentPageNumber: " + currentPageNumber);
        });

        maxPageButton.setOnAction(event -> {
            //System.out.println("is here");
            showDotNext = false;
            currentPageNumber = totalPageNumber;
            focusMaxButton = true;
            refreshMainBox();
        });

        rightButton.setOnAction(event -> {
            //System.out.println("rightButton has clicked");
            if(currentPageNumber < totalPageNumber){
                currentPageNumber+=1;
                UIPagination.focusOnFirst = true;
                refreshMainBox();
            }
        });
    }
}
