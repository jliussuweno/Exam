package com.jliussuweno.exam.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "todo_table")
public class ToDo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int todoId = 0;

    String title;
    String description;
    String status;

    @Ignore
    public ToDo(int todoId, String title, String description, String status) {
        this.todoId = todoId;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public ToDo(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
