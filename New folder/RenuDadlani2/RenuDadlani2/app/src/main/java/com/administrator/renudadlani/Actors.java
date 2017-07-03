package com.administrator.renudadlani;

/**
 * Created by Administrator on 8/25/2016.
 */
public class Actors {
    private boolean isRowSelected;
    private String name;
    private String dob;
    private String country;
    private String height;
    private boolean selected;
    private int childCount;
    private String email;
    private String message;
    private int id;

    public Actors( String dob,boolean selected) {
        super();
        this.dob = dob;
        this.selected=selected;
    }

    public Actors() {

    }


    public void setIsRowSelected(boolean isRowSelected) {
        this.isRowSelected = isRowSelected;
    }

    public boolean getIsRowSelected() {
        return isRowSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDob() {
        return dob;
    }

    public String getHeight() {
        return height;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void isSelected(boolean checked) {
        this.selected=checked;
    }


    public boolean isSelected() {
        return selected;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public String getemail() {
        return email;
    }


    public String getmessage() {
        return message;
    }
}
