package com.jliussuweno.exam.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jliussuweno.exam.database.GlobalDao;
import com.jliussuweno.exam.database.GlobalRoomDatabase;
import com.jliussuweno.exam.model.User;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    Context context;
    GlobalRoomDatabase globalRoomDatabase;
    GlobalDao globalDao;
    LiveData<List<User>> listLiveDataUser;

    public ListViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        globalRoomDatabase = GlobalRoomDatabase.getInstance(context);
        globalDao = globalRoomDatabase.globalDao();
    }

    public LiveData<List<User>> getListLiveDataUser() {
        return listLiveDataUser;
    }

    public void getListAll(){
         listLiveDataUser = globalDao.selectUserAll();
    }
}
