package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarFieldContactDropdown;
import com.ticketpro.parking.dar.model.DarSignCheck;

import java.util.List;

import io.reactivex.Maybe;
@Dao
public interface DarSignCheckDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarSignCheck(DarSignCheck... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarSignCheck(DarSignCheck data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarSignCheck(List<DarSignCheck> darFieldContactDropdowns);

    @Query("DELETE from dar_sign_check_dropdown")
    void removeAll();

    @Query("DELETE from dar_sign_check_dropdown where id=:id")
    void removeById(int id);

    @Query("select * from dar_sign_check_dropdown where custid=:id")
    Maybe<List<DarSignCheck>> getDarFieldContactDropdown(int id);
}
