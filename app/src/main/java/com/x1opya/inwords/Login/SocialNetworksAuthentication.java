package com.x1opya.inwords.Login;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.x1opya.inwords.Login.Data.CurrentUser;
import com.x1opya.inwords.Login.Data.UserLoginManager;
import com.x1opya.inwords.Login.Data.UserLocalData;

public class SocialNetworksAuthentication extends LoginActivity implements UserType {
    private UserLoginTask mAuthTask = null;
    private GoogleSignInClient mGoogleClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //Вк авторизаци -----------------------------------------------------------
    public void onClickLoginVK(View view) {
        String[] scope = new String[]{VKScope.EMAIL};
        VKSdk.login(this,scope);

        Toast.makeText(this,"Vk presed", Toast.LENGTH_SHORT).show();
    }

    // Гугл авторизация ---------------------------------------------------
    public void onClickLoginGoogle(View view) {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();
        mGoogleClient = GoogleSignIn.getClient(this,options);
        Intent signIntent = mGoogleClient.getSignInIntent();
        startActivityForResult(signIntent,1);
    }

    //вызов api соцсетей
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        final Context context = this;
        final String[] id = new String[1];
        Log.println(Log.ASSERT,SocialNetworksAuthentication.class.toString(),"старт фрагмента: resultCod = "+resultCode+"| requestCod = "+requestCode);
        showProgress(true);
        if(!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                mAuthTask = new UserLoginTask(VK_USER, res.userId);
                //data.putExtra("id","1111");
                //не дает создать обычную переменную, поэтому массив
                id[0] = res.userId;
                Log.println(Log.ASSERT,SocialNetworksAuthentication.class.toString(),"ID vk: "+res.userId);
                mAuthTask.execute();
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(context,"Error inter",Toast.LENGTH_LONG).show();
                //startActivity(Intent.makeMainActivity(data.getComponent()));
                showProgress(false);

            }

        })){
            super.onActivityResult(requestCode, resultCode, data);
        }
        if(requestCode==GOOGLE_USER) {
            //else {
            Log.println(Log.ASSERT, SocialNetworksAuthentication.class.toString(), "До инициализации аккаунта ");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Log.println(Log.ASSERT, SocialNetworksAuthentication.class.toString(), "Инициализация гугл акаунта ");
                GoogleSignInAccount account = task.getResult(ApiException.class);
                mAuthTask = new UserLoginTask(GOOGLE_USER, account.getId());
                mAuthTask.execute((Void) null);
                super.onActivityResult(requestCode, resultCode, data);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
       // }



    }

    //логин анонимного юзера
    public void onClickAnonimousLogin(View view) {
        String id = "ANONYM";
        UserLocalData saveAnonym = new UserLocalData(this);
        saveAnonym.saveCurrentUser(new CurrentUser(id));
        finish();
    }

    @Override
    public void onBackPressed() {

    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private int typeAuth;
        CurrentUser user;
        UserLoginManager manger;
        UserLoginTask(int requestTypeAuth, String userId) {
            typeAuth = requestTypeAuth;
            user = new CurrentUser(userId);
            manger = new UserLoginManager(getBaseContext());
        }

        @Override
        protected Boolean doInBackground(Void... data) {
            // TODO: attempt authentication against a network service.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(typeAuth==VK_USER){
                Log.println(Log.ASSERT,UserLoginTask.class.toString(),"ID vk из патока: " + user.getId());
                manger.tryLogin(user);
                return true;
            }
            else if(typeAuth==GOOGLE_USER){
                Log.println(Log.ASSERT,UserLoginTask.class.toString(),"ID google из патока: " + user.getId());
                manger.tryLogin(user);
                return true;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                finish();
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}
