package com.example.mad;
import java.io.Serializable;

public class movieclass implements  Serializable {
    private String moviewreview;

    public movieclass() {

    }

    public String getMoviewreview() {
        return moviewreview;
    }


    public void setMoviewreview(String moviewreview) {
        this.moviewreview = moviewreview;
    }


}
