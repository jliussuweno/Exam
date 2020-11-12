package com.jliussuweno.exam.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jliussuweno.exam.R;
import com.jliussuweno.exam.adapter.ToDoAdapter;
import com.jliussuweno.exam.callback.TodoCallback;
import com.jliussuweno.exam.model.ToDo;

import java.io.IOException;
import java.io.Serializable;

public class HomeActivity extends AppCompatActivity implements Serializable, TodoCallback {

    RecyclerView recyclerView;
    ToDoAdapter toDoAdapter;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        toDoAdapter = new ToDoAdapter();
        toDoAdapter.setContext(this);
        toDoAdapter.setTodoCallback(this);

        recyclerView = findViewById(R.id.recylcerViewToDo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(toDoAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void todoPressed(ToDo toDo) {

    }
}