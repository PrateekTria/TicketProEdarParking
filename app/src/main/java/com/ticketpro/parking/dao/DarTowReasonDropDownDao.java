package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarTowReasonDropDown;

import java.util.List;

import io.reactivex.Maybe;
@Dao
public interface DarTowReasonDropDownDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarTowReasonDropDown(DarTowReasonDropDown... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarTowReasonDropDown(DarTowReasonDropDown data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarTowReasonDropDown(List<DarTowReasonDropDown> darFieldContactDropdowns);

    @Query("DELETE from dar_tow_reason_dropdown")
    void removeAll();

    @Query("DELETE from dar_tow_reason_dropdown where id=:id")
    void removeById(int id);

    @Query("select * from dar_tow_reason_dropdown where custid=:id")
    Maybe<List<DarTowReasonDropDown>> getDarTowReasonDropDown(int id);
}
