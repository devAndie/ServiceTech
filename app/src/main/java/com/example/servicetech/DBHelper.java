package com.example.servicetech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ServiceTech.db";
    public static final String CUSTOMERS_TABlE = "Customers";
    public static final String CUSTOMERS_COLUMN_ID = "id";
    public static final String CUSTOMERS_COLUMN_FNAME = "fname";
    public static final String CUSTOMERS_COLUMN_SURNAME = "surname";
    public static final String CUSTOMERS_COLUMN_USERNAME = "username";
    public static final String CUSTOMERS_COLUMN_PHONE = "phone";
    public static final String CUSTOMERS_COLUMN_EMAIL = "email";
    public static final String CUSTOMERS_COLUMN_ADDRESS = "address";
    public static final String CUSTOMERS_COLUMN_PASSWORD = "password";
    private HashMap hp;

//mydatabase.execSQL("CREATE TABLE IF NOT EXISTS TutorialsPoint(Username VARCHAR,Password VARCHAR);");
//mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin','admin');
//SQLiteDatabase mydatabase = openOrCreateDatabase("your database name",MODE_PRIVATE,null);

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
        String CreateCustomersTable = "CREATE TABLE " + CUSTOMERS_TABlE + "( " +
                CUSTOMERS_COLUMN_ID +"INTEGER PRIMARY KEY AUTOINCREMENT, "+ CUSTOMERS_COLUMN_FNAME +"TEXT, "+
                CUSTOMERS_COLUMN_SURNAME +"TEXT, " + CUSTOMERS_COLUMN_USERNAME +"TEXT, " +
                CUSTOMERS_COLUMN_PHONE + "INT, " + CUSTOMERS_COLUMN_EMAIL +" TEXT, " +
                CUSTOMERS_COLUMN_ADDRESS + "TEXT" + CUSTOMERS_COLUMN_PASSWORD +"TEXT)";

        db.execSQL(CreateCustomersTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Customers");
        onCreate(db);
    }
    public boolean addCustomers (CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUSTOMERS_COLUMN_FNAME, CustomerModel.getFirstName());
        cv.put(CUSTOMERS_COLUMN_SURNAME, CustomerModel.getLastName());
        cv.put(CUSTOMERS_COLUMN_USERNAME, CustomerModel.getUserName());
        cv.put(CUSTOMERS_COLUMN_PHONE, CustomerModel.getPhone());
        cv.put(CUSTOMERS_COLUMN_EMAIL, CustomerModel.getMail());
        cv.put(CUSTOMERS_COLUMN_ADDRESS, CustomerModel.getAddress());
        cv.put(CUSTOMERS_COLUMN_PASSWORD, CustomerModel.getPassword());

        long insert = db.insert(CUSTOMERS_TABlE, null, cv);
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from customers where id="+id+"", null );
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CUSTOMERS_COLUMN_FNAME);
        return numRows;
    }

    public ArrayList<String> getAllCustomers() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from customers", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CUSTOMERS_COLUMN_FNAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
