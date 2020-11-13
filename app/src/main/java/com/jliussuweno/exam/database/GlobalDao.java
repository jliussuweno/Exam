package com.jliussuweno.exam.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jliussuweno.exam.model.User;

import java.util.List;

@Dao
public interface GlobalDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT COUNT(*) from user_table")
    int selectUser();

    @Query("SELECT * from user_table ORDER BY name")
    LiveData<List<User>> selectUserAll();

    @Query("SELECT * FROM user_table where userId = :userId")
    LiveData<List<User>> selectUserActive(int userId);

    @Query("UPDATE user_table set name = :name, image = :image, password = :password")
    void updateUser(String name , String password, String image);

    @Query("DELETE FROM user_table where userId = :userId")
    void deleteUser(int userId);

    @Query("SELECT COUNT(*) from user_table where name= :name and password = :password")
    int selectUserLogin(String name, String password);
}
