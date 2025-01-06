package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarAdminDropdown;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarAdminDropdownDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarFieldContactDropdown(DarAdminDropdown... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarFieldContactDropdown(DarAdminDropdown data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarFieldContactDropdown(List<DarAdminDropdown> darFieldContactDropdowns);

    @Query("DELETE from dar_admin_dropdown")
    void removeAll();

    @Query("DELETE from dar_admin_dropdown where id=:id")
    void removeById(int id);

    @Query("select * from dar_admin_dropdown where custid=:id")
    Maybe<List<DarAdminDropdown>> getDarFieldContactDropdown(int id);
}
