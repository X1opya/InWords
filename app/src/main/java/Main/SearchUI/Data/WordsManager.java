package Main.SearchUI.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        Cursor cursor = db.rawQuery("SELECT * FROM words WHERE " + field +" LIKE '%"+part+"%'", null);
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
}
