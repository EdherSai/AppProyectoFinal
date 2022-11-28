package com.example.myapplication;

import java.io.Serializable;

public class MyData implements Serializable {
    private String name;
    private String contra;
    private int image;
    private String red;

    public MyData()
    {
    }

    public String getContra() {

        return contra;
    }

    public String setContra(String contra) {

        this.contra = contra;
    }

    public String getName() {

        return name;
    }

    public String setName(String name) {

        this.name = name;
    }

    public int getImage() {

        return image;
    }

    public void setImage(int image) {

        this.image = image;
    }

    public String getRed() {

        return red;
    }

    public void setRed(String red) {

        this.red = red;
    }
}
