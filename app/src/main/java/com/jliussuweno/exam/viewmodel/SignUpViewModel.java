package com.jliussuweno.exam.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jliussuweno.exam.database.GlobalDao;
import com.jliussuweno.exam.database.GlobalRoomDatabase;
import com.jliussuweno.exam.model.User;

public class SignUpViewModel extends AndroidViewModel {

    Context context;
    GlobalRoomDatabase globalRoomDatabase;
    GlobalDao globalDao;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        globalRoomDatabase = GlobalRoomDatabase.getInstance(context);
        globalDao = globalRoomDatabase.globalDao();
    }

    public boolean checkUser(User user){
        if (globalDao.selectUser() == 0){
            return true;
        } else {
            return false;
        }

    }

    public void insertUser(User user){
        globalDao.insertUser(user);
    }

}
