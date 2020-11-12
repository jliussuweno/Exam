package com.jliussuweno.exam.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jliussuweno.exam.database.UserDao;
import com.jliussuweno.exam.database.UserRoomDatabase;
import com.jliussuweno.exam.model.User;

public class SignUpViewModel extends AndroidViewModel {

    Context context;
    UserRoomDatabase userRoomDatabase;
    UserDao userDao;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        userRoomDatabase = UserRoomDatabase.getInstance(context);
        userDao = userRoomDatabase.userDao();
    }

    public boolean checkUser(User user){

        if (userDao.selectUser(user.getEmail()) == 0){
            return true;
        } else {
            return false;
        }

    }

    public void insertUser(User user){
        userDao.insertUser(user);
    }

}
