package com.example.car_rental_sys.ui_components;

import javafx.scene.Cursor;
import javafx.scene.control.Button;

import java.util.Objects;

public class UIPaginationCard extends Button {
    private final int cardHeight = 30;
    private final int cardWidth = 30;

    public UIPaginationCard(int number){
        super();
        initialize(Integer.toString(number));
}

    private void initSize(){
        this.setPrefSize(cardWidth,cardHeight);
    }

    private void initStyle(){
        this.getStyleClass().add("default-style");
    }

    public void initPageNumber(String number){
        String content;
        switch (number) {
            case "-1":
                //is dotPrevious
                content = "...1";
                this.getStyleClass().add("dot-style");
                break;
            case "-2":
                //is dotNext
                content = "...2";
                this.getStyleClass().add("dot-style");
                break;
            case "-3":
                content = "<";
                this.getStyleClass().add("control-style");
                break;
            case "-4":
                content = ">";
                this.getStyleClass().add("control-style");
                break;
            default:
                content = number;
                initStyle();
                break;
            }
            this.setText(content);
        }

    private void addClickEvent(){
        this.setOnAction(event -> {
            //UIPagination01.minButton.getStyleClass().remove("focus-style");
            UIPagination.minPageButton.getStyleClass().remove("focus-style");
            UIPagination.focusMaxButton = false;
            //System.out.println("clicked on "+this.getText()+" page");
            switch (this.getText()) {
                case "...1":
                    if(UIPagination.currentPageNumber - 5 < 4) {
                        UIPagination.showDotPrevious = false;
                    }
                    setFocusState("","","","false","false");
                    //System.out.println("clicked on previous5 page");
                    if(UIPagination.currentPageNumber == UIPagination.totalPageNumber){
                        UIPagination.focusOnCenter = true;
                    }
                    if(UIPagination.currentPageNumber < UIPagination.totalPageNumber){
                        UIPagination.focusOnCenter = true;
                    }
                    UIPagination.currentPageNumber-=5;
                    if(UIPagination.currentPageNumber == 2){
                        setFocusState("true", "false", "false", "", "");
                    }
                    if(UIPagination.currentPageNumber == 3){
                        setFocusState("", "true", "false", "", "");
                    }
                    if(UIPagination.currentPageNumber < 4) {
                        UIPagination.focusOnCenter = false;
                    }
                    if(UIPagination.currentPageNumber == 5){
                        setFocusState("", "", "false", "true", "");
                    }

                    UIPagination.renewRequest();
                    return;
                case "...2":
                    //System.out.println("clicked on next5 page");
                    //UIPagination02.currentPageNumber+=5;
                    UIPagination.focusOnFourth = false;
                    if(UIPagination.currentPageNumber+5 >= 4 && UIPagination.currentPageNumber+5 <= UIPagination.totalPageNumber-3) {
                        setFocusState("false", "", "true", "", "");
                    }
                    if(UIPagination.currentPageNumber+5 == UIPagination.totalPageNumber-2){
                        setFocusState("", "false", "true", "false", "false");
                    }
                    if(UIPagination.currentPageNumber+5 == UIPagination.totalPageNumber-1){
                        setFocusState("", "false", "false", "", "true");
                    }
                    if(UIPagination.currentPageNumber+5 == UIPagination.totalPageNumber){
                        setFocusState("", "false", "false", "false", "false");
                        UIPagination.focusMaxButton = true;
                    }
                    UIPagination.currentPageNumber+=5;
                    UIPagination.renewRequest();
                    return;
                case "<":
                    if(UIPagination.currentPageNumber == 1){
                        UIPagination.leftButton.setDisable(false);
                        UIPagination.focusMinButton = true;
                        UIPagination.renewRequest();
                        return;
                    }else{
                        //UIPagination02.currentPageNumber-=1;
                        if(UIPagination.currentPageNumber == 2){
                            UIPagination.focusOnFirst = false;
                            UIPagination.renewRequest();
                        }
                        if (UIPagination.currentPageNumber == 3) {
                            setFocusState("true", "false", "", "", "");
                        }
                        if(UIPagination.currentPageNumber == 4){
                            setFocusState("", "true", "false", "false", "");
                        }
                        if(UIPagination.currentPageNumber == 5){
                            setFocusState("", "", "true", "false", "");
                        }
                        if(UIPagination.currentPageNumber == 6){
                            setFocusState("", "", "false", "true", "");
                        }

                        UIPagination.currentPageNumber-=1;
                        UIPagination.renewRequest();
                    }
                    return;
                    //break;
                case ">":
                    if(UIPagination.currentPageNumber == UIPagination.totalPageNumber){
                        UIPagination.rightButton.setDisable(false);
                        return;
                    }else{

                        if (UIPagination.currentPageNumber == 1) {
                            UIPagination.focusOnFirst = true;
                        }
                        if(UIPagination.currentPageNumber == 2){
                            setFocusState("false", "true", "", "", "");
                        }
                        if(UIPagination.currentPageNumber == 3){
                            setFocusState("", "false", "true", "false", "");
                        }
                        if(UIPagination.currentPageNumber == 4){
                            setFocusState("", "", "false", "true", "");
                        }
                        if(UIPagination.currentPageNumber >= 5){
                            setFocusState("", "", "true", "false", "");
                        }
                        if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 2){
                            setFocusState("", "", "false", "", "true");
                        }
                        if(UIPagination.currentPageNumber == UIPagination.totalPageNumber-1){
                            setFocusState("", "", "false", "false", "false");
                            UIPagination.focusMaxButton = true;
                        }
                        UIPagination.currentPageNumber += 1;
                        UIPagination.renewRequest();
                    }
                    return;
                    //break;
                default:
                    UIPagination.currentPageNumber = Integer.parseInt(this.getText());
                    break;
            }

            //print current page number
            UIPagination.currentPageNumber = Integer.parseInt(this.getText());
            //System.out.println("current page number: "+UIPagination02.currentPageNumber);

            if(UIPagination.currentPageNumber < 3){
                setFocusState("", "false", "false", "false", "false");
                if(UIPagination.currentPageNumber == 1){
                    UIPagination.focusOnFirst = false;
                }
                if(UIPagination.currentPageNumber == 2){
                    UIPagination.focusOnFirst = true;
                }
                UIPagination.showDotPrevious = false;
                UIPagination.renewRequest();
            }
            else if (UIPagination.currentPageNumber >= 3 && UIPagination.currentPageNumber <= UIPagination.totalPageNumber-4){
                //System.out.println("here");
                setFocusState("false", "", "", "false", "false");
                if(UIPagination.currentPageNumber == 3){
                    setFocusState("", "true", "false", "", "");
                }
                if(UIPagination.currentPageNumber == 5){
                    setFocusState("", "false", "false", "true", "");
                }
                if(UIPagination.currentPageNumber == 4){
                    setFocusState("", "false", "true", "false", "");
                }
                if(UIPagination.currentPageNumber >= 6){
                    setFocusState("", "false", "true", "false", "");
                }
                if(UIPagination.currentPageNumber == UIPagination.totalPageNumber-4){
                    UIPagination.focusOnCenter = true;
                }
                UIPagination.renewRequest();
            }else if (UIPagination.currentPageNumber == UIPagination.totalPageNumber){
                System.out.println("at last page");
                setFocusState("false", "false", "false", "false", "false");
                UIPagination.focusMaxButton = true;
                UIPagination.showDotNext = false;
                UIPagination.renewRequest();
            }
            else if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 1){
                System.out.println("at last page - 1");
                System.out.println(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 1);
                setFocusState("", "false", "false", "true", "true");
                UIPagination.renewRequest();
            }
            else if(UIPagination.currentPageNumber >= UIPagination.totalPageNumber - 3){
                //System.out.println("31 is at here");
                if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 3 ||
                        UIPagination.currentPageNumber == UIPagination.totalPageNumber - 2){
                    setFocusState("", "", "true", "false", "false");
                }
                UIPagination.renewRequest();
            }
            else {
                UIPagination.renewRequest();
            }
        });
    }

    private void setFocusState(String first, String second, String center, String fourth, String last){
        if(!Objects.equals(first, "")){
            UIPagination.focusOnFirst = Boolean.parseBoolean(first);
        }
        if(!Objects.equals(second, "")){
            UIPagination.focusOnSecond = Boolean.parseBoolean(second);
        }
        if(!Objects.equals(center, "")){
            UIPagination.focusOnCenter = Boolean.parseBoolean(center);
        }
        if(!Objects.equals(fourth, "")){
            UIPagination.focusOnFourth = Boolean.parseBoolean(fourth);
        }
        if(!Objects.equals(last, "")){
            UIPagination.focusOnLast = Boolean.parseBoolean(last);
        }
    }

    private void addHoverEvent(){
        //onMouseEntered
        this.setOnMouseEntered(event -> setCursor(Cursor.HAND));
        //onMouseExited
        this.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
    }

    private void initialize(String number){
        initSize();
        addClickEvent();
        initPageNumber(number);
        addHoverEvent();
    }
}
