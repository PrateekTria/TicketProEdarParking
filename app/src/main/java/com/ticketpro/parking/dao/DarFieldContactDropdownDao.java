package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarFieldContactDropdown;
import com.ticketpro.parking.dar.model.DarLocationActivityTask;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarFieldContactDropdownDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarFieldContactDropdown(DarFieldContactDropdown... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarFieldContactDropdown(DarFieldContactDropdown data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarFieldContactDropdown(List<DarFieldContactDropdown> darFieldContactDropdowns);

    @Query("DELETE from dar_field_contact_dropdown")
    void removeAll();

    @Query("DELETE from dar_field_contact_dropdown where id=:id")
    void removeById(int id);

    @Query("select * from dar_field_contact_dropdown where custid=:id")
    Maybe<List<DarFieldContactDropdown>> getDarFieldContactDropdown(int id);

}
