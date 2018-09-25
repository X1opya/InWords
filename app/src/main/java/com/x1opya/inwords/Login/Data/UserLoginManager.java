package com.x1opya.inwords.Login.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.x1opya.inwords.data.BaseHelper;

public class UserLoginManager {

    SQLiteDatabase db;
    BaseHelper helper;
    UserLocalData data;

    public UserLoginManager(Context context) {
        helper = new BaseHelper(context);
        db = helper.getWritableDatabase();
        data= new UserLocalData(context);
    }

    private CurrentUser registration(String id){
        Log.println(Log.ASSERT,UserLoginManager.class.toString(),"Юзер зарегистрировался: "+id);
        ContentValues addedUser = new ContentValues();
        addedUser.put("soc_id",id);
        db.insert("users", null,addedUser);
        db.close();
        return new CurrentUser(id);
    }

    private CurrentUser login(String id, Cursor user){
        Log.println(Log.ASSERT,UserLoginManager.class.toString(),"Юзер залогинелся");
        db.close();
        user.close();
        return new CurrentUser(id);
    }

    /**
     * @param user - пользователm
     * @return Возвращает пользователя, который зарегистрировался или залогинелсяя
     */
    public CurrentUser tryLogin(CurrentUser user){

        ResultQuery res = isUserExist(user.getId());
        data.saveCurrentUser(user);
        if(res.exist){
            return login(user.getId(), res.cursor);
        }
        else{
            return registration(user.getId());
        }
    }

    //существует пользователь или нет
    private ResultQuery isUserExist(String id){
        Cursor user = db.rawQuery("select * from users where soc_id = "+id,null);
        Log.println(Log.ASSERT,UserLoginManager.class.toString(),"Существует ли пользователь в базе:" + user.moveToFirst());
        ResultQuery res = new ResultQuery();
        if(user.moveToFirst()){
            res.exist = true;
            res.cursor = user;
            return res;
        }
        user.close();
        return new ResultQuery(false,null);
    }

    //клас добавлен, что бы после проверки можно было не обращаться к базе сново
    public class ResultQuery{
        public boolean exist;
        public Cursor cursor;

        public ResultQuery(boolean exist,@Nullable Cursor cursor) {
            this.exist = exist;
            this.cursor = cursor;
        }
        public ResultQuery() {
        }
    }

}
