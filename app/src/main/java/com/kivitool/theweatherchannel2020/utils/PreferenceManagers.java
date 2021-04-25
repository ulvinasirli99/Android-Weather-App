package com.kivitool.theweatherchannel2020.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kivitool.theweatherchannel2020.services.location.Constants;

public class PreferenceManagers {

    private SharedPreferences sharedPreferences;

    public PreferenceManagers(Context context){
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME,Context.MODE_PRIVATE);
    }

    public void putBoolean(String key,Boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public Boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }

    public void putString(String key,String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getSting(String key){
        return sharedPreferences.getString(key,null);
    }

    public void putInteger(String key,int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public int getInteger(String key){
        return sharedPreferences.getInt(key,0);
    }

    public void putDouble(String key,float location){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, location);
        editor.apply();
    }

    public Float getDouble(String key){
        return sharedPreferences.getFloat(key,20.2f);
    }



}
