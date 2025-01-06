package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarTowCompanyDrop;

import java.util.List;

import io.reactivex.Maybe;
@Dao
public interface DarTowCompanyDropDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarTowCompanyDrop(DarTowCompanyDrop... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarTowCompanyDrop(DarTowCompanyDrop data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarTowCompanyDrop(List<DarTowCompanyDrop> darFieldContactDropdowns);

    @Query("DELETE from dar_tow_company_dropdown")
    void removeAll();

    @Query("DELETE from dar_tow_company_dropdown where id=:id")
    void removeById(int id);

    @Query("select * from dar_tow_company_dropdown where custid=:id")
    Maybe<List<DarTowCompanyDrop>> getDarTowCompanyDrop(int id);
}
