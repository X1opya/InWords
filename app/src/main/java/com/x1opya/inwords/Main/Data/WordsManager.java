package com.x1opya.inwords.Main.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.x1opya.inwords.data.BaseHelper;

import java.util.ArrayList;
import java.util.List;

public class WordsManager {
    SQLiteDatabase db;
    BaseHelper helper;
    List<Word> words;


    public WordsManager(Context context) {
        helper = new BaseHelper(context);
        db = helper.getWritableDatabase();
        words = new ArrayList<>();
    }

    public List<Word> getWordsToSearch(String part, boolean isEngSymbols){
        words.clear();
        String field = "";
        if(isEngSymbols) field = "word";
        else field = "translate";
        if(part.isEmpty()) return null;
        Cursor cursor = db.rawQuery("SELECT * FROM words WHERE " + field +" LIKE '"+part+"%' LIMIT 20", null);
        if(cursor.moveToFirst()){
            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                Word word = new Word(cursor.getString(1),cursor.getString(2));
                words.add(word);
            }
            cursor.close();
            return words;
        }
        cursor.close();
          return null;
    }

    public void closeBase(){
        db.close();
    }

    public List<Word> getLocalBase() {
        Cursor cursor = db.rawQuery("SELECT * FROM words",null);
        List<Word> allWords = new ArrayList<>();
            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                Log.println(Log.ASSERT,getClass().toString(),"шаг цикла");
                Word word = new Word(cursor.getString(1),cursor.getString(2));
                allWords.add(word);
            }
            if(allWords.isEmpty()){
                cursor.close();
                return null;
            }
            cursor.close();
        return allWords;
    }
}
