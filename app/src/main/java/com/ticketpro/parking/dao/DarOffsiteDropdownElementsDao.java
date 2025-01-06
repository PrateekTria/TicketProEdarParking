package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarOffsiteDropdownElementsResult;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarOffsiteDropdownElementsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffsiteDropdownElements(DarOffsiteDropdownElementsResult... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffsiteDropdownElements(DarOffsiteDropdownElementsResult data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffsiteDropdownElements(List<DarOffsiteDropdownElementsResult> darOffsiteDropdownElementsResultList);

    @Query("DELETE from dar_OffSiteNonEnforcement_dropdown")
    void removeAll();

    @Query("DELETE from dar_OffSiteNonEnforcement_dropdown where dd_elementId=:id")
    void removeById(int id);


    @Query("select * from dar_OffSiteNonEnforcement_dropdown where custid=:custId")
    Maybe<List<DarOffsiteDropdownElementsResult>> getDarOffsiteDropdownElements(int custId);
}
