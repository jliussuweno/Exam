package com.jliussuweno.exam.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HomeViewModel extends AndroidViewModel {

    Context context;


    public HomeViewModel(@NonNull Application application) {
        super(application);
    }
}
