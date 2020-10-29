package com.CMD.model;

import javafx.beans.property.SimpleStringProperty;

public class Record {
    private final SimpleStringProperty month;
    private final SimpleStringProperty amount;

    public Record(String amount, String month) {
        this.month = new SimpleStringProperty(month);
        this.amount = new SimpleStringProperty(amount);
    }

    public SimpleStringProperty monthProperty() {
        return month;
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }
}
