package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarPPZDropdown;
import com.ticketpro.parking.dar.model.DarSchoolDropDownElement;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarPPZDropdownDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarPPZDropdown(DarPPZDropdown... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarPPZDropdown(DarPPZDropdown data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarPPZDropdown(List<DarPPZDropdown> darFieldContactDropdowns);

    @Query("DELETE from dar_ppz_dropdown")
    void removeAll();

    @Query("DELETE from dar_ppz_dropdown where id=:id")
    void removeById(int id);

    @Query("select * from dar_ppz_dropdown where custid=:id")
    Maybe<List<DarPPZDropdown>> getDarSchoolDropDown(int id);
}
