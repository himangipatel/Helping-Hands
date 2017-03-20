package com.ashutosh.helpinghand.database;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DatabaseGenerator {
    public static void main(String[] args)  throws Exception {

        //place where db folder will be created inside the project folder
        Schema schema = new Schema(1,"com.ashutosh.helpinghand");

        //Entity i.e. Class to be stored in the database // ie table LOG
        Entity word_entity= schema.addEntity("Contacts");

        word_entity.addIdProperty(); //It is the primary key for uniquely identifying a row

        word_entity.addStringProperty("Name").notNull();
        //Not null is SQL constrain
        word_entity.addStringProperty("PhonuNumber").unique();
        word_entity.addStringProperty("Photo");

        //  ./app/src/main/java/   ----   com/codekrypt/greendao/db is the full path
        new DaoGenerator().generateAll(schema, "./app/src/main/java");

    }
}