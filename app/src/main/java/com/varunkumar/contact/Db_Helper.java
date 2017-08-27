package com.varunkumar.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by varun on 8/21/2017.
 */

public class Db_Helper extends SQLiteOpenHelper {

    //database
    private  static final String DATABASE_NAME = "employee_records";

    //table
    private  static final String TABLE_NAME = "employee_contacts";

    //table columns
    private static final String COLUMN_NAME= "name";
    private static final String COLUMN_EMAIL = "email";


    public Db_Helper(Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    //Creating Table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME +"("+COLUMN_NAME+" TEXT"+" PRIMARY KEY"+" NOT NULL"+", "+COLUMN_EMAIL+" TEXT"+" NOT NULL"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }


    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName()); // Contact Name
        values.put(COLUMN_EMAIL, contact.getMail()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }


    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setName(cursor.getString(0));
                contact.setMail(cursor.getString(1));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    // Deleting single contact
    public void deleteContact(Contact contact)    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_EMAIL + " = ?",
                new String[] { contact.getMail() });
        db.close();
    }


    public int updateContact(Contact contact,String newEmail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_EMAIL, newEmail);


        // updating row
        return db.update(TABLE_NAME, values, COLUMN_EMAIL + " = ?",
                new String[] { contact.getMail() });
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
