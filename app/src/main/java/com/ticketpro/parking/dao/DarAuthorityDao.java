package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarAuthorityResponse;
import com.ticketpro.parking.dar.model.DarBreakAndLunchDropDown;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarAuthorityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarBrDarAuthorityResponse(DarAuthorityResponse... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarAuthorityResponse(DarAuthorityResponse data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarAuthorityResponse(List<DarAuthorityResponse> darDarAuthorityResponse);

    @Query("DELETE from dar_authority_dropdown")
    void removeAll();

    @Query("DELETE from dar_authority_dropdown where id=:id")
    void removeById(int id);

    @Query("select * from dar_authority_dropdown where custid=:id")
    Maybe<List<DarAuthorityResponse>> getList(int id);
}
