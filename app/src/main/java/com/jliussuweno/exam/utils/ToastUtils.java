package com.jliussuweno.exam.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ToastUtils {

    static Toast toast;

    public static void makeToast(Context context, String message) {

        if (toast != null){
            toast = null;
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
