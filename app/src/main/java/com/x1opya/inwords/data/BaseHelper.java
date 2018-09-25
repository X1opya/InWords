package com.x1opya.inwords.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "test.db";
    private final Context context;

    public BaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table users (id INTEGER primary key autoincrement," +
                "soc_id TEXT);");
        db.execSQL("CREATE table words (id INTEGER primary key autoincrement," +
                "word TEXT," +
                "translate TEXT);");
        addItem(db, "animal", "животное");
        addItem(db, "big", "большой");
        addItem(db, "cinema", "кинотеатр");
        addItem(db, "dildo", "писюн резиновый");
        addItem(db, "fox", "лис");
        addItem(db, "genius", "ты");
        addItem(db, "head", "голова");
        addItem(db, "icon", "икона отца нашего");
        addItem(db, "jew", "животное");
        addItem(db, "kapusta", "ты дурак чтоле? это вообще не слово");

    }

    private void addItem(SQLiteDatabase db,String word, String translate){
        ContentValues item = new ContentValues();
        item.put("word",word);
        item.put("translate",translate);
        db.insert("words", null,item);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
