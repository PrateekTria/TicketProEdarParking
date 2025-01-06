package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarBreakAndLunchDropDown;
import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarBreakAndLunchDropDownDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarBreakAndLunchDropDown(DarBreakAndLunchDropDown... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarBreakAndLunchDropDown(DarBreakAndLunchDropDown data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarBreakAndLunchDropDown(List<DarBreakAndLunchDropDown> darFieldContactDropdowns);

    @Query("DELETE from dar_break_and_lunch_dropdown")
    void removeAll();

    @Query("DELETE from dar_break_and_lunch_dropdown where id=:id")
    void removeById(int id);

    @Query("select * from dar_break_and_lunch_dropdown where custid=:id")
    Maybe<List<DarBreakAndLunchDropDown>> getList(int id);
}
