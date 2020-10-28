package com.CMD.model;


import javafx.beans.property.SimpleStringProperty;

public class Member {
    private final int ID;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty imgUrl;

    public Member(int ID, String firstName, String lastName, String phoneNumber, String email, String dateOfBirth,
                  String imgUrl) {
        this.ID = ID;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
        this.imgUrl = new SimpleStringProperty(imgUrl);
    }

    public SimpleStringProperty getFirstName() {
        return firstName;
    }

    public SimpleStringProperty getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public String getImgUrl() {
        return imgUrl.get();
    }

}
