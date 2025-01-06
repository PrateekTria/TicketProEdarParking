package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarOffsiteTrainerDropdown;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarOffsiteTrainerDropdownDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffsiteTrainerDropdown(DarOffsiteTrainerDropdown... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffsiteTrainerDropdown(DarOffsiteTrainerDropdown data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffsiteTrainerDropdown(List<DarOffsiteTrainerDropdown> darOffsiteDropdownElementsResultList);

    @Query("DELETE from dar_offsite_trainer_dropdown")
    void removeAll();

    @Query("DELETE from dar_offsite_trainer_dropdown where id=:id")
    void removeById(int id);


    @Query("select * from dar_offsite_trainer_dropdown where custid=:custId")
    Maybe<List<DarOffsiteTrainerDropdown>> getDarOffsiteTrainerDropdown(int custId);
}
