package com.x1opya.inwords;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.vk.sdk.VKSdk;
import com.x1opya.inwords.Login.CurrentUser;
import com.x1opya.inwords.Login.SocialNetworksAuthentication;
import com.x1opya.inwords.data.UserLocalData;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    CurrentUser user;
    UserLocalData localData;

    int huy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        if(isUserLogined()){
            UserLocalData data = new UserLocalData(this);
            user = data.readCurrentUser();
            TextView textView = (TextView) findViewById(R.id.tv);
            textView.setText(user.getId());
        }else {
            startLoginActivity();
        }
        localData = new UserLocalData(this);
    }

    private void startLoginActivity(){
        startActivityForResult(new Intent(this, SocialNetworksAuthentication.class), RESULT_FIRST_USER);
    }

    public boolean isUserLogined(){
        File f = getFileStreamPath(UserLocalData.F_NAME);
        Log.println(Log.ASSERT,MainActivity.class.toString(),"Существует ли файл: "+f.exists());
        return f.exists();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        user = new CurrentUser(localData.readCurrentUser().getId());
        //Log.println(Log.ASSERT,MainActivity.class.toString()," Главное активити приняло результат: " + data.getStringExtra("id"));
        TextView tv = findViewById(R.id.tv);
        tv.setText(user.getId());
        /*tv.setText("Емэйл: "+data.getStringExtra("email")+"\n"
                +"Token: "+data.getStringExtra("token")+"\n"
                +"ID: "+data.getStringExtra("id")
                +"\n"+"Secret: "+data.getStringExtra("secret")+"\n");*/
    }

    public void onClickLogout(View view) {
        localData.logOut();
        VKSdk.logout();
        FirebaseAuth.getInstance().signOut();
        startLoginActivity();
    }
}
