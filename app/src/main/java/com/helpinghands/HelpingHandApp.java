package com.helpinghands;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.helpinghands.database.ContactsDao;
import com.helpinghands.database.DaoMaster;
import com.helpinghands.database.DaoSession;


/**
 * Created by himangi on 19/03/2017.
 */

public class HelpingHandApp extends Application {

    private  final String DB_NAME ="helping-db" ;

    public static ContactsDao contactsDao;

    @Override
    public void onCreate() {
        super.onCreate();
        contactsDao = setupDb();
    }

    public ContactsDao setupDb(){
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(this, DB_NAME, null); //create database db file if not exist
        SQLiteDatabase db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
        DaoSession masterSession=master.newSession(); //Creates Session session
        return masterSession.getContactsDao();
    }

}
