package com.jliussuweno.exam.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jliussuweno.exam.model.User;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT COUNT(*) from user_table where email = :email")
    int selectUser(String email);

    @Query("DELETE FROM user_table where email = :email")
    void deleteUser(String email);

    @Query("SELECT COUNT(*) from user_table where email = :email and password = :password")
    int selectUserLogin(String email, String password);

}
