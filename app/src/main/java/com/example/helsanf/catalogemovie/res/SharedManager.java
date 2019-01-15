package com.example.helsanf.catalogemovie.res;

import android.content.Context;

import java.util.HashMap;

public class SharedManager {
    android.content.SharedPreferences pref;
    android.content.SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    public static final String PREF_NAME = "SessionPref";
    public static final String ID_MOVIE = "id_movie";

    public SharedManager(Context _context) {
        this.context = _context;
        pref = _context.getSharedPreferences(PREF_NAME,mode);
        editor=pref.edit();
    }

    public void createIdMovie(String id_movie){
        editor.putString(ID_MOVIE,id_movie);
        editor.commit();
    }

    public HashMap<String, String> getDetailsMovie(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(ID_MOVIE,pref.getString(ID_MOVIE,null));
        return user;

    }
}
