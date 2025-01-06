package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarOffsiteDistrictDropdown;

import java.util.List;

import io.reactivex.Maybe;
@Dao
public interface DarOffsiteDistrictDropdownDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffsiteDistrictDropdown(DarOffsiteDistrictDropdown... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffsiteDistrictDropdown(DarOffsiteDistrictDropdown data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffsiteDistrictDropdown(List<DarOffsiteDistrictDropdown> darOffsiteDropdownElementsResultList);

    @Query("DELETE from dar_offsite_district_dropdown")
    void removeAll();

    @Query("DELETE from dar_offsite_district_dropdown where id=:id")
    void removeById(int id);


    @Query("select * from dar_offsite_district_dropdown where custid=:custId")
    Maybe<List<DarOffsiteDistrictDropdown>> getDarOffsiteDistrictDropdown(int custId);
}
