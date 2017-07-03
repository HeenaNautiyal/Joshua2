package com.example.administrator.testing;

import android.widget.CheckBox;

/**
 * Created by Administrator on 8/31/2016.
 */
public class Actors {
    private String name;
    private String description;
    private String dob;
    private String country;
    private String height;
    private String spouse;
    private String children;
    private String image;
    private boolean selected;
    private boolean isRowSelected;
    private boolean selectedAll;
    private int count;
    private boolean selectedrow;

    public Actors() {
        // TODO Auto-generated constructor stub
    }

    public Actors(String name, String description, String dob, String country,
                  String height, String spouse, String children, String image,boolean
                  selected) {
        super();
        this.name = name;
        this.description = description;
        this.dob = dob;
        this.country = country;
        this.height = height;
        this.spouse = spouse;
        this.children = children;
        this.image = image;
        this.selected=selected;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean getIsRowSelected() {
        return isRowSelected;
    }

    public void setIsRowSelected(boolean isRowSelected) {
        this.isRowSelected = isRowSelected;
    }

    public boolean isSelectedAll() {
        return true;
    }


    public void setSelectedrow(boolean selectedrow) {
        this.selectedrow = selectedrow;

    }
}
