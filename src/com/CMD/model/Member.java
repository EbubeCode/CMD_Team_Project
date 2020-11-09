package com.CMD.model;


import javafx.beans.property.SimpleStringProperty;

public class Member {
    private final int ID;
    private  SimpleStringProperty firstName;
    private  SimpleStringProperty lastName;
    private  String phoneNumber;
    private  String email;
    private  String dateOfBirth;
    private  String imgUrl;

    public Member(int ID, String firstName, String lastName, String phoneNumber, String email, String dateOfBirth,
                  String imgUrl) {
        this.ID = ID;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.imgUrl = imgUrl;
    }

    public SimpleStringProperty getFirstName() {
        return firstName;
    }

    public int getID() {
        return ID;
    }

    public SimpleStringProperty getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
