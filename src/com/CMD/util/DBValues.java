package com.CMD.util;

//Enum for the set of constant strings used in the DataBaseHandler class
public enum DBValues {
    TABLE_MEMBERS("members"),
    TABLE_RECORD("records2020"),
    MEMBER_ID("memberID"),
    MONTH("month"),
    AMOUNT("amount"),
    COLUMN_ID("_id"),
    COLUMN_FIRST_NAME("fName"),
    COLUMN_LAST_NAME("lName"),
    COLUMN_PHONE_NUMBER("phoneNumber"),
    COLUMN_EMAIL_ADDRESS("email"),
    COLUMN_BIRTH_DATE("dateOfBirth"),
    COLUMN_IMAGE_URL("imageUrl"),
    DB_NAME("CMD.db"),
    CONNECTION_STRING("jdbc:sqlite:src/resources/dataBase/" + DB_NAME.value),
    CREATE_TABLE("CREATE TABLE IF NOT EXISTS "
            + TABLE_MEMBERS.value + "(" + COLUMN_ID.value + " INTEGER PRIMARY KEY, "
            + COLUMN_FIRST_NAME.value + " TEXT, " + COLUMN_LAST_NAME.value + " TEXT, "
            + COLUMN_PHONE_NUMBER.value + " TEXT, " + COLUMN_EMAIL_ADDRESS.value + " TEXT, "
            + COLUMN_BIRTH_DATE.value + " TEXT, " + COLUMN_IMAGE_URL.value + " TEXT)"),
    INSERT_MEMBER("INSERT INTO " + TABLE_MEMBERS.value
            + "(" + COLUMN_FIRST_NAME.value + ", " + COLUMN_LAST_NAME.value + ", " + COLUMN_PHONE_NUMBER.value
            + ", " + COLUMN_EMAIL_ADDRESS.value + ", " + COLUMN_BIRTH_DATE.value + ", " + COLUMN_IMAGE_URL.value + ")"
            + " VALUES (?, ?, ?, ?, ?, ?)"),
    QUERY_MEMBER("SELECT " + COLUMN_ID.value + " FROM "
            + TABLE_MEMBERS.value + " WHERE " + COLUMN_FIRST_NAME.value + " = ? " +
            "AND " + COLUMN_LAST_NAME.value + " = ?"),
    QUERY_MEMBERS("SELECT * FROM " + TABLE_MEMBERS.value + " ORDER BY _id"),
    CREATE_RECORD_TABLE("CREATE TABLE IF NOT EXISTS "
            + TABLE_RECORD.value + "(" + COLUMN_ID.value + " INTEGER PRIMARY KEY, "
            + AMOUNT.value + " TEXT, " + MONTH.value + " TEXT, " + MEMBER_ID.value + " INTEGER)");

    public final String value;

    DBValues(String value) {
        this.value = value;
    }
}
