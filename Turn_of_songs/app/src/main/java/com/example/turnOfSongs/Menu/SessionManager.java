package com.example.turnOfSongs.Menu;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    //Data
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context activityContext;

    public static final String SESSION_USERSESSION = "userSession";
    public static final String SESSION_REMEMBERME = "rememberMe";

    private static final String IS_LOGIN = "LoggedIn";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    private static final String IS_REMEMBER = "isRemember";
    public static final String KEY_SESSIONEMAIL = "email";
    public static final String KEY_SESSIONPASSWORD = "password";

    public SessionManager(Context context, String sessionName){
        this.activityContext = context;
        //create a session if not
        userSession = activityContext.getSharedPreferences(sessionName,Context.MODE_PRIVATE);
        //allow us to edit userSession with editor
        editor = userSession.edit();
    }

    public void createLoginSession(String username,String email, String password){
        //the user is logged in
        editor.putBoolean(IS_LOGIN,true);
        //defining the value of our user
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASSWORD,password);
        //we commit and store the data of the session
        editor.commit();
    }

    public HashMap<String, String> getUserValueSession(){
        HashMap<String,String> userData = new HashMap<>();

        userData.put(KEY_USERNAME,userSession.getString(KEY_USERNAME,null));
        userData.put(KEY_EMAIL,userSession.getString(KEY_EMAIL,null));
        userData.put(KEY_PASSWORD,userSession.getString(KEY_PASSWORD,null));

        return userData;
    }

    public Boolean checkLoggedIn(){
        Boolean res =false;
        if(userSession.getBoolean(IS_LOGIN,false)){
            res = true;
        }
        else{
            res = false;
        }
        return res;
    }

    public void logOutFromSession(){
        editor.clear();
        editor.commit();
    }

    public void createRememberMeSession(String email, String password){
        //the user is remember me
        editor.putBoolean(IS_REMEMBER,true);
        //defining the value of our user
        editor.putString(KEY_SESSIONEMAIL,email);
        editor.putString(KEY_SESSIONPASSWORD,password);
        //we commit and store the data of the session
        editor.commit();
    }

    public HashMap<String, String> getRememberMeValueSession(){
        HashMap<String,String> userData = new HashMap<>();

        userData.put(KEY_SESSIONEMAIL,userSession.getString(KEY_SESSIONEMAIL,null));
        userData.put(KEY_SESSIONPASSWORD,userSession.getString(KEY_SESSIONPASSWORD,null));

        return userData;
    }

    public Boolean checkRememberMe(){
        Boolean res =false;
        if(userSession.getBoolean(IS_REMEMBER,false)){
            res = true;
        }
        else{
            res = false;
        }
        return res;
    }

}
