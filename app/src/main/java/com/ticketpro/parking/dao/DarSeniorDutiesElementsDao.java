package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.Assignments;
import com.ticketpro.parking.dar.model.DarSeniorDutiesElements;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarSeniorDutiesElementsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarSeniorDutiesElements(DarSeniorDutiesElements... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarSeniorDutiesElements(DarSeniorDutiesElements data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarSeniorDutiesElementsList(List<DarSeniorDutiesElements> darSeniorDutiesElementsList);

    @Query("DELETE from dar_senior_duties_dropdown")
    void removeAll();

    @Query("DELETE from dar_senior_duties_dropdown where id=:id")
    void removeById(int id);


    @Query("select * from dar_senior_duties_dropdown where custid=:custId")
    Maybe<List<DarSeniorDutiesElements>> getDarSeniorDutiesElements(int custId);
}
