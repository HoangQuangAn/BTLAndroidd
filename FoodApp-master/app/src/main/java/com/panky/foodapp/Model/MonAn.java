package com.panky.foodapp.Model;

public class MonAn {

    private  String ID;
    private String Name;
    private String Image;


    public MonAn(String ID, String name, String image) {
        this.ID = ID;
        Name = name;
        Image = image;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
