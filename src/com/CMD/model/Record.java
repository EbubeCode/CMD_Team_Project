package com.CMD.model;

import com.CMD.util.Months;
import javafx.beans.property.SimpleStringProperty;

public class Record {
    private final SimpleStringProperty month;
    private final SimpleStringProperty amount;
    private final String memberFirstName;
    private final String memberLastName;
    private final int year;
    private final String details;

    public Record(String amount, String month, int year, String firstName, String lastName, String details) {
        this.month = new SimpleStringProperty(month);
        this.amount = new SimpleStringProperty(amount);
        memberFirstName = firstName;
        this.year = year;
        memberLastName = lastName;
        this.details = details;
    }

    public SimpleStringProperty monthProperty() {
        return month;
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public String getMonth() {
        return month.get();
    }

    public String getAmount() {
        return amount.get();
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

    public String getDetails() {
        return details;
    }

    public Months getEnumMonth() {
        return Months.valueOf(month.get());
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
