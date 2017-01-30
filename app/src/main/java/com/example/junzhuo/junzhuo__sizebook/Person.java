package com.example.junzhuo.junzhuo__sizebook;

import java.io.Serializable;

import static java.lang.Math.round;

/**
 * Created by junzhuo on 1/29/17.
 */

public class Person implements Serializable {
    private String name;
    private String date; //format in yyyy-mm-dd
    private Double neck;
    private Double bust;
    private Double chest;
    private Double waist;
    private Double hip;
    private Double inseam;
    private String comment;

    public  Person(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getBust() {
        return doubleToString(bust);
    }

    public String getChest() {
        return doubleToString(chest);
    }

    public String getHip() {
        return doubleToString(hip);
    }

    public String getInseam() {
        return doubleToString(inseam);
    }

    public String getNeck() {
        return doubleToString(neck);
    }

    public String getWaist() {
        return doubleToString(waist);
    }

    public String getComment() {
        return comment;
    }


    private String doubleToString (Double num) {
        String s = "";

        if (num != -1) {
            s = num.toString();
        }

        return s;
    }

    // setter

    public void setChest(String chest) {
        this.chest = convertToDouble(chest);
    }

    public void setBust(String bust) {
        this.bust = convertToDouble(bust);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHip(String hip) {
        this.hip = convertToDouble(hip);
    }

    public void setInseam(String inseam) {
        this.inseam = convertToDouble(inseam);
    }

    public void setNeck(String neck) {
        this.neck = convertToDouble(neck);
    }

    public void setWaist(String waist) {
        this.waist = convertToDouble(waist);
    }

    private Double convertToDouble(String text) {
       // try {
        if (text.length() == 0) {
            return -1.0;
        }
       Double parsedNum = Double.parseDouble(text);


                //size need round to 2 decimal.
        parsedNum = (double) round(parsedNum * 100);
        parsedNum = parsedNum / 100;
        //} catch (NumberFormatException e) {
        //    throw new InputNumberException();
        //}
        return parsedNum;
    }


}

