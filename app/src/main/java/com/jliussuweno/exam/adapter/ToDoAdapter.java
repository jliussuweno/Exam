package com.jliussuweno.exam.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.jliussuweno.exam.callback.TodoCallback;
import com.jliussuweno.exam.model.ToDo;
import com.jliussuweno.exam.R;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    List<ToDo> todoList = new ArrayList<>();
    TodoCallback todoCallback = null;
    Context context;

    public void setTodoList(List<ToDo> toDoList) {
        this.todoList = toDoList;
        notifyDataSetChanged();
    }

    public void setTodoCallback(TodoCallback todoCallback) {
        this.todoCallback = todoCallback;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tempView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new ToDoViewHolder(tempView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        if (todoList != null){
            ToDo current = todoList.get(position);
            holder.setViewData(current);
            holder.getParent().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (todoCallback != null){
                        todoCallback.todoPressed(current);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    static class ToDoViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_description;
        TextView tv_status;
        View parent;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_status = itemView.findViewById(R.id.tv_status);

        }

        public void setViewData(ToDo toDo){
            tv_title.setText(toDo.getTitle());
            tv_description.setText(toDo.getDescription());
            tv_status.setText(toDo.getStatus());
        }

        public View getParent(){
            return  parent;
        }
    }
}
