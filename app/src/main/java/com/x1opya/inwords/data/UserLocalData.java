package com.x1opya.inwords.data;

import android.content.Context;
import android.util.Log;

import com.x1opya.inwords.Login.CurrentUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserLocalData {
    Context context;
    public static final String F_NAME = "userdata.txt";

    public UserLocalData(Context context) {
        this.context = context;
    }

    // запись в файл
    public void saveCurrentUser(CurrentUser user){
        Log.println(Log.ASSERT,UserLocalData.class.toString(),"SaveUser: " + user.getId().isEmpty());
        if(!user.getId().isEmpty()){
            try {
                FileOutputStream out = context.openFileOutput(F_NAME,Context.MODE_PRIVATE);
                out.write(user.getId().getBytes());
                Log.println(Log.ASSERT,UserLocalData.class.toString(),"Юзера записан в файл: ");
                out.close();

            } catch (FileNotFoundException e) {
                Log.println(Log.ASSERT,UserLocalData.class.toString(),"ошибка записи: "+e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.println(Log.ASSERT,UserLocalData.class.toString(),"Ошибка записи: "+e.getMessage());
                e.printStackTrace();
            }

        }
    }

    //чтение из файла
    public CurrentUser readCurrentUser(){
        try {
            FileInputStream in = context.openFileInput(F_NAME);
            byte[] b = new byte[in.available()];
            in.read(b);
            String text = new String(b);
            in.close();
            Log.println(Log.ASSERT,UserLocalData.class.toString(),"Юзер прочитан: ");
            return new CurrentUser(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }


    public void logOut(){
        context.deleteFile(F_NAME);
    }
}
