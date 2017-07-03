 package com.heena.d2dmedicine;

/**
 * Created by Heena on 12/19/2016.
 */
public class Chemist {
    private String name;
    private String description;
    private String dob;
    private String country;
    private String height;
    private String spouse;
    private String children;
    private boolean selected;
    private String image;
    private boolean isRowSelected;
    private String fname;
    private String lname;
    private String mail;
    private String state;
    private String mobile;
    private String pincode;
    private String house;
    private String colony;
    private String address;
    private String city;
    private String category;
    private String medicine;
    private String menufacuter;
    private String compostion;
    private String addedby;
    private String todaydate;
    private String userip;
    private String stock;
    private String added_by;
    private String date;
    private String quantity;
    private String license;
    private String expiery;


    public Chemist() {
        // TODO Auto-generated constructor stub
    }

    public Chemist(String name, String description, String dob, String country,
                   String height, String spouse, String children, String image,
                   String fname,String address,String city,String state,String category,String medicine
            ,String menufacuter,String compostion,String addedby,String todaydate,String userip, String stock,String date,String Quantity) {
        super();
        this.name = name;
        this.description = description;
        this.dob = dob;
        this.country = country;
        this.height = height;
        this.spouse = spouse;
        this.children = children;
        this.image = image;
        this.fname = fname;
        this.address=address;
        this.city=city;
        this.state=state;
        this.category=category;
        this.medicine=medicine;
        this.menufacuter= menufacuter;
        this.compostion=compostion;
        this.addedby=addedby;
        this.todaydate=todaydate;
        this.userip=userip;
        this.stock=stock;
        this.date=date;
        this.quantity=Quantity;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public void setDob(String dob) {
        this.dob = dob;
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



    public void setSpouse(String spouse) {
        this.spouse = spouse;
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

    public boolean getIsRowSelected() {
        return isRowSelected;
    }

    public void setIsRowSelected(boolean isRowSelected) {
        this.isRowSelected = isRowSelected;
    }



    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }
    public String getFname(){
        return fname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void detState(String state) {
        this.state=state;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory(){return category;}

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }


    public void setMenufacuter(String menufacuter) {
        this.menufacuter = menufacuter;
    }

    public void setCompostion(String compostion) {
        this.compostion = compostion;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public void setTodaydate(String todaydate) {
        this.todaydate = todaydate;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getMenufacuter() {
        return menufacuter;
    }

    public String getCompostion() {
        return compostion;
    }

    public String getadded_by() {
        return added_by;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setExpiery(String expiery) {
        this.expiery = expiery;
    }

    public String getLicense() {
        return license;
    }
}