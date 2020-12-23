package com.example.servicetech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ServiceTech.db";
    public static final String  USERS_TABlE = "Users",
            USERS_COLUMN_ID = "id", USERS_COLUMN_FNAME = "fname", USERS_COLUMN_SURNAME = "surname",
            USERS_COLUMN_USERNAME = "username", USERS_COLUMN_PHONE = "phone", USERS_COLUMN_EMAIL = "email",
            USERS_COLUMN_ADDRESS = "address", USERS_COLUMN_PASSWORD = "password";
    public static String  EVENTS_TABlE = "Events",
            EVENTS_COLUMN_ID = "id", EVENTS_COLUMN_TYPE = "type", EVENTS_COLUMN_SERVICE = "service",
            EVENTS_COLUMN_TIME = "time", EVENTS_COLUMN_LOCATION = "location", EVENTS_COLUMN_IMG = "img",
            EVENTS_COLUMN_CONTEXT = "context";
    private String TECHNICIAN_TABlE = "Technicians",
            TECHNICIAN_COLUMN_ID = "id", TECHNICIAN_COLUMN_FNAME= "fName", TECHNICIAN_COLUMN_SURNAME ="surName",
            TECHNICIAN_COLUMN_USERNAME = "username", TECHNICIAN_COLUMN_PHONE = "phone",
            TECHNICIAN_COLUMN_EMAIL = "mail", TECHNICIAN_COLUMN_ADDRESS = "address", TECHNICIAN_COLUMN_SPECIALTY = "specialty",
    TECHNICIAN_COLUMN_EDUCATION = "EDUC_LVL", TECHNICIAN_COLUMN_OPSTIME = "OpsTime",
    TECHNICIAN_COLUMN_TETHER = "tether";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
        String Users = "CREATE TABLE " + USERS_TABlE + "( " +
                USERS_COLUMN_ID +"INTEGER PRIMARY KEY AUTOINCREMENT, "+ USERS_COLUMN_FNAME +"TEXT, "+
                USERS_COLUMN_SURNAME +"TEXT, " + USERS_COLUMN_USERNAME +"TEXT, " +
                USERS_COLUMN_PHONE + "INT, " + USERS_COLUMN_EMAIL +" TEXT, " +
                USERS_COLUMN_ADDRESS + "TEXT" + USERS_COLUMN_PASSWORD +"TEXT)";

        String events = "create table " + EVENTS_TABlE + "(" +
                EVENTS_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + EVENTS_COLUMN_TYPE+" TEXT, "+
                EVENTS_COLUMN_SERVICE +" TEXT, " + EVENTS_COLUMN_TIME +" TEXT, "+
                EVENTS_COLUMN_LOCATION +" TEXT, "+EVENTS_COLUMN_IMG + " BLOB, "+
                EVENTS_COLUMN_CONTEXT +" TEXT )";
        String Technicians = "CREATE TABLE " + TECHNICIAN_TABlE + "( " +
                TECHNICIAN_COLUMN_ID +"INTEGER PRIMARY KEY AUTOINCREMENT, "+ TECHNICIAN_COLUMN_FNAME +"TEXT, "+
                TECHNICIAN_COLUMN_SURNAME +"TEXT, " + TECHNICIAN_COLUMN_USERNAME + "TEXT, " +
                TECHNICIAN_COLUMN_PHONE + "INTEGER, " + TECHNICIAN_COLUMN_EMAIL + " TEXT, " +
                TECHNICIAN_COLUMN_ADDRESS + " TEXT, " + TECHNICIAN_COLUMN_SPECIALTY + " TEXT, " +
                TECHNICIAN_COLUMN_EDUCATION+ " TEXT, " + TECHNICIAN_COLUMN_OPSTIME + "TEXT, " +
                TECHNICIAN_COLUMN_TETHER+ " TEXT )";

        db.execSQL(Users);
        db.execSQL(events);
        db.execSQL(Technicians);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Events");
        db.execSQL("DROP TABLE IF EXISTS Technicians");
        onCreate(db);
    }
    public boolean addCustomers (CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERS_COLUMN_FNAME, CustomerModel.getFirstName());
        cv.put(USERS_COLUMN_SURNAME, CustomerModel.getLastName());
        cv.put(USERS_COLUMN_USERNAME, CustomerModel.getUserName());
        cv.put(USERS_COLUMN_PHONE, CustomerModel.getPhone());
        cv.put(USERS_COLUMN_EMAIL, CustomerModel.getMail());
        cv.put(USERS_COLUMN_ADDRESS, CustomerModel.getAddress());
        cv.put(USERS_COLUMN_PASSWORD, CustomerModel.getPassword());

        long insert = db.insert(USERS_TABlE, null, cv);
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }
    public boolean addTechnician(TechnicianModel technicianModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TECHNICIAN_COLUMN_FNAME, CustomerModel.getFirstName());
        cv.put(TECHNICIAN_COLUMN_SURNAME, CustomerModel.getLastName());
        cv.put(TECHNICIAN_COLUMN_USERNAME, CustomerModel.getUserName());
        cv.put(TECHNICIAN_COLUMN_PHONE, CustomerModel.getPhone());
        cv.put(TECHNICIAN_COLUMN_EMAIL, CustomerModel.getMail());
        cv.put(TECHNICIAN_COLUMN_ADDRESS, CustomerModel.getAddress());
        cv.put(TECHNICIAN_COLUMN_SPECIALTY, TechnicianModel.getSpecialty());
        cv.put(TECHNICIAN_COLUMN_EDUCATION, TechnicianModel.getEdLevel());
        cv.put(TECHNICIAN_COLUMN_OPSTIME, TechnicianModel.getOpsTime());
        cv.put(TECHNICIAN_COLUMN_TETHER, TechnicianModel.getLocTether());

        long insert = db.insert(TECHNICIAN_TABlE, null, cv);
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }
    public boolean addEvent(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EVENTS_COLUMN_TYPE, EventModel.getType());
        cv.put(EVENTS_COLUMN_SERVICE, EventModel.getService());
        cv.put(EVENTS_COLUMN_TIME, EventModel.getTime());
        cv.put(EVENTS_COLUMN_LOCATION, EventModel.getLocation());
       // cv.put(EVENTS_COLUMN_IMG, EventModel.getImg());
        cv.put(EVENTS_COLUMN_CONTEXT, EventModel.getContext());

        db.insert(EVENTS_TABlE, null, cv);
        return false;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from customers where id="+id+"", null );
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USERS_COLUMN_FNAME);
        return numRows;
    }

    public List<String> getAllCustomers() {
        List<String> customers_list = new ArrayList<String>();

        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Users", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            customers_list.add(res.getString(res.getColumnIndex(USERS_COLUMN_FNAME)));
            res.moveToNext();
        }
        return customers_list;
    }
}
