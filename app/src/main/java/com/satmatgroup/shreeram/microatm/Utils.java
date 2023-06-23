package com.satmatgroup.shreeram.microatm;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

public class Utils {

    Context context;
    public static boolean isValidString(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(c >= 'A' && c <= 'Z') ||
                    !(c >= 'a' && c <= 'z') ||
                    !(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidArrayList(ArrayList<?> l) {
        if(l.isEmpty()) {
            return false;
        }
        else
            return true;
    }

    public static void dissmissKeyboard(EditText merchIdEt) {

/*        merchIdEt.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(merchIdEt, InputMethodManager.SHOW_IMPLICIT);*/
    }

    public static void logD(String s) {
        Log.d("Debug Error",s);

    }

    public static void logE(String toString) {
        Log.e("Error",toString);
    }
}
