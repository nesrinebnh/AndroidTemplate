package com.example.navjava.Model;


import java.util.Date;

public class Feedback {
    public Date date;
    public float rate;
    public String comment;
    public Feedback(){}

    public Feedback(Date date, float rate){
        this.date = date;
        this.rate = rate;
        this.comment = null;
    }

    public Feedback(Date date, float rate,String comment){
        this.date = date;
        this.rate = rate;
        this.comment = comment;
    }
}