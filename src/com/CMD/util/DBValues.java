package com.CMD.util;

//Enum for the set of constant strings used in the DataBaseHandler class
public enum DBValues {
    TABLE_MEMBERS("members"),
    COLUMN_ID("_id"),
    COLUMN_FIRST_NAME("fName"),
    COLUMN_LAST_NAME("lName"),
    COLUMN_PHONE_NUMBER("phoneNumber"),
    COLUMN_EMAIL_ADDRESS("email"),
    COLUMN_BIRTH_DATE("dateOfBirth"),
    COLUMN_IMAGE_URL("imageUrl"),
    DB_NAME("CMD.db"),
    CONNECTION_STRING("jdbc:sqlite:src/resources/dataBase/" + DB_NAME.value);

    public final String value;

    DBValues(String value) {
        this.value = value;
    }
}
