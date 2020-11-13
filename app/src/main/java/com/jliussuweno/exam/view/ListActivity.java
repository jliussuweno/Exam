package com.jliussuweno.exam.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jliussuweno.exam.R;
import com.jliussuweno.exam.adapter.UserAdapter;
import com.jliussuweno.exam.callback.UserCallback;
import com.jliussuweno.exam.model.User;
import com.jliussuweno.exam.viewmodel.ListViewModel;

import java.io.Serializable;
import java.util.List;

public class ListActivity extends AppCompatActivity implements Serializable, UserCallback {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    FloatingActionButton floatingActionButton;
    ListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        userAdapter = new UserAdapter();
        userAdapter.setContext(this);
        userAdapter.setUserCallback(this);

        recyclerView = findViewById(R.id.recylcerViewToDo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listViewModel.getListAll();
        listViewModel.getListLiveDataUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.setUserList(users);
            }
        });
    }

    @Override
    public void userPressed(User user) {
        Intent intent = new Intent(ListActivity.this, PasswordActivity.class);
        intent.putExtra("user", (Serializable) user);
        startActivity(intent);
        finish();
    }
}