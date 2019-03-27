package com.techease.pdfapplication.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.techease.pdfapplication.R;


public class GeneralUtils {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static Fragment connectFragmentWithOutBackStack(Context activity, Fragment fragment){
        ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        return fragment;
    }

    public static Fragment connectFragmentWithBack(Context activity, Fragment fragment){
        ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("abc").commit();
        return fragment;
    }




    public static SharedPreferences.Editor putValueInEditor(Context context) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        return editor;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        //sharedPreferences = context.getSharedPreferences(Configuration.MY_PREF, 0);
        return context.getSharedPreferences(Configurations.MY_PREF, 0);
    }

    private static class Configurations {
        public static final String MY_PREF = null;
    }




}
