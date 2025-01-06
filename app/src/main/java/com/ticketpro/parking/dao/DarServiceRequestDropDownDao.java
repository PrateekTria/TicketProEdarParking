package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarServiceRequestDropDown;

import java.util.List;

import io.reactivex.Maybe;
@Dao
public interface DarServiceRequestDropDownDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarServiceRequestDropDown(DarServiceRequestDropDown... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarServiceRequestDropDown(DarServiceRequestDropDown data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarServiceRequestDropDown(List<DarServiceRequestDropDown> darFieldContactDropdowns);

    @Query("DELETE from dar_service_request_dropdown")
    void removeAll();

    @Query("DELETE from dar_service_request_dropdown where id=:id")
    void removeById(int id);

    @Query("select * from dar_service_request_dropdown where custid=:id")
    Maybe<List<DarServiceRequestDropDown>> getDarSchoolDropDown(int id);
}
