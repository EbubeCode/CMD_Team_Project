package com.CMD.database;

//Enum for the set of constant strings used in the DataBaseHandler class
public enum DBValues {
    DB_NAME("CMD.db"),
    CONNECTION_STRING("jdbc:sqlite:src/resources/db/" + DB_NAME.value),

    TABLE_MEMBERS("members"),
    COLUMN_ID("_id"),
    COLUMN_FIRST_NAME("fName"),
    COLUMN_LAST_NAME("lName"),
    COLUMN_PHONE_NUMBER("phoneNumber"),
    COLUMN_EMAIL_ADDRESS("email"),
    COLUMN_BIRTH_DATE("dateOfBirth"),
    COLUMN_IMAGE_URL("imageUrl"),

    TABLE_RECORDS("records"),
    COLUMN_MEMBER_ID("memberID"),
    COLUMN_MONTH("month"),
    COLUMN_AMOUNT("amount"),
    COLUMN_YEAR("year"),
    COLUMN_DETAILS("details"),

    TABLE_MAIL_SERVER_INFO("mail_server_info"),
    COLUMN_SERVER_NAME("server_name"),
    COLUMN_SERVER_PORT("server_port"),
    COLUMN_USER_EMAIL("user_email"),
    COLUMN_USER_PASSWORD("user_password"),
    COLUMN_SSL_ENABLED("ssl_enabled"),

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

    QUERY_NEW_MEMBER("SELECT * FROM "
            + TABLE_MEMBERS.value + " WHERE " + COLUMN_FIRST_NAME.value + " = ? " +
            "AND " + COLUMN_LAST_NAME.value + " = ?"),

    QUERY_MEMBERS("SELECT * FROM " + TABLE_MEMBERS.value + " ORDER BY " + COLUMN_FIRST_NAME.value),

    CREATE_RECORD_TABLE("CREATE TABLE IF NOT EXISTS "
            + TABLE_RECORDS.value + "(" + COLUMN_ID.value + " INTEGER PRIMARY KEY, "
            + COLUMN_AMOUNT.value + " TEXT, " + COLUMN_MONTH.value + " TEXT, " + COLUMN_MEMBER_ID.value + " INTEGER, "
            + COLUMN_YEAR.value + " INTEGER, " + COLUMN_DETAILS.value + " TEXT)"),

    INSERT_RECORD("INSERT INTO " + TABLE_RECORDS.value
            + "(" + COLUMN_AMOUNT.value + ", " + COLUMN_MONTH.value + ", " + COLUMN_MEMBER_ID.value + ", " +
            COLUMN_YEAR.value+ ", " + COLUMN_DETAILS.value + ")" + "VALUES (?, ?, ?, ?, ?)"),

    QUERY_RECORD_INSERT("SELECT " + COLUMN_ID.value + " FROM " + TABLE_RECORDS.value
            + " WHERE " + COLUMN_AMOUNT.value + " = ? AND " + COLUMN_MONTH.value + " = ? AND " + COLUMN_MEMBER_ID.value
            + " = ? AND " + COLUMN_YEAR.value + " = ?"),

    QUERY_MEMBER_RECORDS("SELECT " + COLUMN_AMOUNT.value + ", " + COLUMN_MONTH.value + " FROM " + TABLE_RECORDS.value
            + " WHERE " + COLUMN_MEMBER_ID.value + " = ? ORDER BY " + COLUMN_ID.value + " ASC"),

    QUERY_MEMBERS_RECORDS("SELECT " + COLUMN_AMOUNT.value + ", " + COLUMN_MONTH.value + ", " + COLUMN_YEAR.value
            + ", " + COLUMN_FIRST_NAME.value+ ", " + COLUMN_LAST_NAME.value+ ", " + COLUMN_DETAILS.value +" FROM " + TABLE_RECORDS.value +
            " INNER JOIN " + TABLE_MEMBERS.value + " ON "+ TABLE_RECORDS.value + "." + COLUMN_MEMBER_ID.value +
            " = " + TABLE_MEMBERS.value + "." + COLUMN_ID.value),

    QUERY_EXPENSES("SELECT " + COLUMN_AMOUNT.value + ", " + COLUMN_MONTH.value + ", " + COLUMN_YEAR.value
            + ", "  + COLUMN_DETAILS.value +" FROM " + TABLE_RECORDS.value + " WHERE " + COLUMN_MEMBER_ID.value +
            " = -1"),

    UPDATE_MEMBER("UPDATE "+ TABLE_MEMBERS.value + " SET " + COLUMN_FIRST_NAME.value + " = ?, " + COLUMN_LAST_NAME.value +
            " = ?, " + COLUMN_PHONE_NUMBER.value + " = ?, " + COLUMN_EMAIL_ADDRESS.value + " = ?, "+
            COLUMN_BIRTH_DATE.value + " = ?, " + COLUMN_IMAGE_URL.value + " = ?  WHERE " +
            COLUMN_ID.value + " = ?"),
    DELETE_MEMBER("DELETE FROM " + TABLE_MEMBERS.value + " WHERE " + COLUMN_ID.value + " = ?"),


    CREATE_MAIL_SERVER_INFO_TABLE("CREATE TABLE IF NOT EXISTS "
            + TABLE_MAIL_SERVER_INFO.value + "(" + COLUMN_SERVER_NAME.value + " TEXT, "
            + COLUMN_SERVER_PORT.value + " INTEGER, " + COLUMN_USER_EMAIL.value + " TEXT, "
            + COLUMN_USER_PASSWORD.value + " TEXT, " + COLUMN_SSL_ENABLED.value + " BOOLEAN)");

    public final String value;

    DBValues(String value) {
        this.value = value;
    }
}