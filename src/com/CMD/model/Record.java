package com.CMD.model;

import javafx.beans.property.SimpleStringProperty;

public class Record {
    private final SimpleStringProperty month;
    private final SimpleStringProperty amount;
    private final String memberFirstName;
    private final String memberLastName;
    private final int year;

    public Record(String amount, String month, int year, String firstName, String lastName) {
        this.month = new SimpleStringProperty(month);
        this.amount = new SimpleStringProperty(amount);
        memberFirstName = firstName;
        this.year = year;
        memberLastName = lastName;
    }

    public SimpleStringProperty monthProperty() {
        return month;
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public String getMemberFirstName() {
        return memberFirstName;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Record{" +
                "month=" + month.get() +
                ", amount=" + amount.get() +
                ", memberFirstName='" + memberFirstName + '\'' +
                ", memberLastName='" + memberLastName + '\'' +
                ", year=" + year +
                '}';
    }
}
