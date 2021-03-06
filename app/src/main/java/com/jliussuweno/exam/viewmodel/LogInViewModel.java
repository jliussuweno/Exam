package com.jliussuweno.exam.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jliussuweno.exam.database.GlobalDao;
import com.jliussuweno.exam.database.GlobalRoomDatabase;

public class LogInViewModel extends AndroidViewModel {

    Context context;
    GlobalRoomDatabase globalRoomDatabase;
    GlobalDao globalDao;

    public LogInViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        globalRoomDatabase = GlobalRoomDatabase.getInstance(context);
        globalDao = globalRoomDatabase.globalDao();
    }

    public int checkLogin(String name, String password){
        return globalDao.selectUserLogin(name, password);
    }
}
