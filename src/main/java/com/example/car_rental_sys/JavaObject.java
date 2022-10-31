package com.example.car_rental_sys;

import com.teamdev.jxbrowser.js.JsAccessible;

public final class JavaObject {
    @JsAccessible
    public String sayHelloTo(String firstName) {
        System.out.println( "Hello, " + firstName + "!");
        return "Hello " + firstName + "!";
    }

    @JsAccessible
    public String sayByeTo(String firstName) {
        System.out.println( "ByeBye, " + firstName + "!");
        return "ByeBye " + firstName + "!";
    }

    @JsAccessible
    public void setLocationText(String text) {
        //System.out.println( "Location: " + text + "!");
        StatusContainer.locationChose = text;
    }


}