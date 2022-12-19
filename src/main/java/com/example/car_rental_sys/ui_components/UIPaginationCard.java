package com.example.car_rental_sys.ui_components;

import javafx.scene.Cursor;
import javafx.scene.control.Button;

import java.util.Objects;


public class UIPaginationCard extends Button {
    private final int cardHeight = 30;
    private final int cardWidth = 31;

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
        //this.addEventFilter(MouseEvent.MOUSE_CLICKED, event ->{
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

                    UIPagination.getCurrentPageNumber();
                    UIPagination.renewRequest();
                    return;
                case "...2":
                    //System.out.println("clicked on next5 page");
                    //UIPagination02.currentPageNumber+=5;
                    UIPagination.focusOnFourth = false;
                    if(UIPagination.currentPageNumber+5 >= 4 && UIPagination.currentPageNumber+5 <= UIPagination.totalPageNumber-3) {
                        setFocusState("false", "false", "true", "", "");
                    }
                    if(UIPagination.currentPageNumber+5 == UIPagination.totalPageNumber-2){
                        setFocusState("false", "false", "true", "false", "false");
                    }
                    if(UIPagination.currentPageNumber+5 == UIPagination.totalPageNumber-1){
                        setFocusState("false", "false", "false", "", "true");
                    }
                    if(UIPagination.currentPageNumber+5 == UIPagination.totalPageNumber){
                        setFocusState("false", "false", "false", "false", "false");
                        UIPagination.focusMaxButton = true;
                    }
                    UIPagination.currentPageNumber+=5;

                    UIPagination.getCurrentPageNumber();
                    UIPagination.renewRequest();
                    return;
                case "<":
                    if(UIPagination.currentPageNumber == 1 && UIPagination.totalPageNumber == 1){
                        UIPagination.leftButton.setDisable(false);
                        UIPagination.focusMinButton = true;
                        return;
                    }
                    else if(UIPagination.currentPageNumber == 1 && UIPagination.totalPageNumber != 1){
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
                            //System.out.println("focus on second@@");
                        }
                        if(UIPagination.currentPageNumber == 5){
                            setFocusState("", "", "true", "false", "");
                            //System.out.println("focus on here##");
                        }
                        if(UIPagination.currentPageNumber == 6){
                            setFocusState("", "", "false", "true", "false");
                            //System.out.println("focus on here@@");
                        }
                        if(UIPagination.currentPageNumber == UIPagination.totalPageNumber && UIPagination.totalPageNumber == 4){
                            setFocusState("", "true", "false", "", "");
                        }
                        if(UIPagination.currentPageNumber == UIPagination.totalPageNumber && UIPagination.totalPageNumber == 5){
                            setFocusState("", "", "true", "false", "");
                        }
                        if(UIPagination.currentPageNumber == UIPagination.totalPageNumber && UIPagination.totalPageNumber == 6){
                            setFocusState("", "", "false", "true", "false");
                            //System.out.println("focus on here%%");
                        }
                        if(UIPagination.currentPageNumber == UIPagination.totalPageNumber && UIPagination.totalPageNumber > 7){
                            //for last -1
                            setFocusState("", "", "false", "false", "true");
                            UIPagination.focusMaxButton = false;
                            //System.out.println(UIPagination.currentPageNumber + " this last page");
                        }
                        if(UIPagination.currentPageNumber == UIPagination.totalPageNumber && UIPagination.totalPageNumber == 7){
                            //System.out.println("halo+");
                            setFocusState("", "", "false", "false", "true");
                        }
                        if(UIPagination.currentPageNumber == UIPagination.totalPageNumber-1 && UIPagination.totalPageNumber == 7){
                            setFocusState("", "", "false", "true", "false");
                        }
                        if(UIPagination.currentPageNumber == UIPagination.totalPageNumber-1 && UIPagination.totalPageNumber > 7){
                            //System.out.println(UIPagination.currentPageNumber + " is last page -2");
                            setFocusState("", "", "true", "false", "false");
                        }
                        if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 2 && UIPagination.totalPageNumber == 7){
                            setFocusState("", "", "true", "false", "false");
                        }

                        UIPagination.currentPageNumber-=1;
                        UIPagination.renewRequest();
                    }

                    UIPagination.getCurrentPageNumber();
                    return;
                    //break;
                case ">":
                    //System.out.println(UIPagination.currentPageNumber + " now clicked on next page");
                    if(UIPagination.currentPageNumber == 1 && UIPagination.totalPageNumber == 2){
                        //do nothing
                    }

                    if(UIPagination.currentPageNumber == UIPagination.totalPageNumber){
                        UIPagination.rightButton.setDisable(false);
                        return;
                    }
                    else{
                        //System.out.println("go to else");
                        if (UIPagination.currentPageNumber == 1 && UIPagination.totalPageNumber == 2) {
                            //do nothing
                            //System.out.println("go to else 1");
                        }
                        if (UIPagination.currentPageNumber == 1 && UIPagination.totalPageNumber != 2) {
                            UIPagination.focusOnFirst = true;
                        }
                        if(UIPagination.currentPageNumber == 2 && UIPagination.totalPageNumber == 3){
                            setFocusState("false", "", "", "", "");
                            //System.out.println("focus on first");
                        }
                        if(UIPagination.currentPageNumber == 2 && UIPagination.totalPageNumber > 3){
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

                    UIPagination.getCurrentPageNumber();
                    return;
                    //break;
                default:
                    UIPagination.currentPageNumber = Integer.parseInt(this.getText());
                    break;
            }


            //print current page number
            UIPagination.currentPageNumber = Integer.parseInt(this.getText());
            //System.out.println("current page number: "+UIPagination02.currentPageNumber);

            if(UIPagination.currentPageNumber <= 3){
                //System.out.println("current page number: "+UIPagination.currentPageNumber);
                setFocusState("", "false", "false", "false", "false");
                if(UIPagination.currentPageNumber == 1){
                    UIPagination.focusOnFirst = false;
                }
                if(UIPagination.currentPageNumber == 2 && UIPagination.totalPageNumber == 2){
                    //do nothing
                    UIPagination.focusMaxButton= true;
                }
                if(UIPagination.currentPageNumber == 2 && UIPagination.totalPageNumber != 2){
                    UIPagination.focusOnFirst = true;
                }
                if(UIPagination.currentPageNumber == 3 && UIPagination.totalPageNumber == 3){
                    setFocusState("false", "", "", "", "");
                    UIPagination.focusMaxButton = true;
                }
                if(UIPagination.currentPageNumber == 3 && UIPagination.totalPageNumber != 3){
                    UIPagination.focusOnFirst = false;
                    UIPagination.focusOnSecond = true;
                }

                UIPagination.showDotPrevious = false;
                UIPagination.renewRequest();
            }
            else if (UIPagination.currentPageNumber > 3 && UIPagination.currentPageNumber <= UIPagination.totalPageNumber-4){
                //System.out.println("here");
                //System.out.println("current page number: "+UIPagination.currentPageNumber);
                setFocusState("false", "", "", "false", "false");
                if(UIPagination.currentPageNumber == 3){
                    setFocusState("", "true", "false", "", "");
                }
                if(UIPagination.currentPageNumber == 4){
                    setFocusState("false", "false", "true", "false", "");
                }
                if(UIPagination.currentPageNumber == 5){
                    //System.out.println("5 at here");
                    //UIPagination.focusOnCenter = false;
                    setFocusState("", "false", "false", "true", "");
                    //System.out.println(UIPagination.focusOnCenter);
                    //setFocusState("", "false", "false", "true", "");
                }
                if(UIPagination.currentPageNumber >= 6){
                    setFocusState("", "false", "true", "false", "");
                }
                if(UIPagination.currentPageNumber == UIPagination.totalPageNumber-4 && UIPagination.totalPageNumber <= 9){
                    if (UIPagination.currentPageNumber == UIPagination.totalPageNumber - 4 && UIPagination.totalPageNumber <= 8) {
                        setFocusState("", "", "true", "false", "false");
                    }else if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 3 && UIPagination.totalPageNumber <= 8){
                        setFocusState("", "", "false", "true", "false");
                    }
                    else{
                        UIPagination.focusOnCenter = false;
                    }

                }
                if(UIPagination.currentPageNumber == UIPagination.totalPageNumber-4 && UIPagination.totalPageNumber > 9){
                    UIPagination.focusOnCenter = true;
                }
                UIPagination.renewRequest();
            }else if (UIPagination.currentPageNumber == UIPagination.totalPageNumber){
                //System.out.println("at last page");
                setFocusState("false", "false", "false", "false", "false");
                UIPagination.focusMaxButton = true;
                UIPagination.showDotNext = false;
                UIPagination.renewRequest();
            }
            else if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 1){
                if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 1 && UIPagination.totalPageNumber == 7){
                    setFocusState("false", "", "false", "false", "true");
                }
                else if(UIPagination.currentPageNumber == UIPagination.totalPageNumber -1 && UIPagination.totalPageNumber == 5){
                    //System.out.println("at here");
                    setFocusState("false", "false", "true", "false", "false");
                }
                else{
                    //System.out.println("at last page - 1");
                   // System.out.println(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 1);
                    setFocusState("false", "false", "false", "true", "true");
                }

                UIPagination.renewRequest();
            }
            else if(UIPagination.currentPageNumber >= UIPagination.totalPageNumber - 3){
                //System.out.println("less than total -3 is at here");
                if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 3 && UIPagination.totalPageNumber == 8){
                    setFocusState("false", "false", "false", "true", "false");
                }else if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 2 && UIPagination.totalPageNumber == 8){
                    setFocusState("false", "false", "true", "false", "false");
                   // System.out.println("now at here");
                }else if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 3 && UIPagination.totalPageNumber == 7){
                    setFocusState("false", "false", "true", "false", "false");
                   // System.out.println("-3 now at here");
                }else if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 2 && UIPagination.totalPageNumber == 7){
                    setFocusState("false", "false", "false", "true", "false");
                   // System.out.println("-2 now at here");
                }else if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 2 && UIPagination.totalPageNumber == 6){
                    setFocusState("false", "false", "true", "false", "false");
                   // System.out.println("-1 now at here");
                }
                else if(UIPagination.currentPageNumber == UIPagination.totalPageNumber - 3 ||
                        UIPagination.currentPageNumber == UIPagination.totalPageNumber - 2){
                    setFocusState("", "", "true", "false", "false");
                   // System.out.println("less than total -3 is at here");
                }
                UIPagination.renewRequest();
            }else if( UIPagination.totalPageNumber <= 8 && UIPagination.currentPageNumber >= UIPagination.totalPageNumber - 4){
                //do nothing
            }
            else {
                UIPagination.renewRequest();
            }
            UIPagination.getCurrentPageNumber();
        });
    }

    private void setFocusState(String first, String second, String center, String fourth, String last){
        if(!Objects.equals(first, "" )){
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
