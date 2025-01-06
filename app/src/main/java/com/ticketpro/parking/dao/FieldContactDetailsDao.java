package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.FieldContactDetails;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface FieldContactDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFieldContactDetails(FieldContactDetails... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFieldContactDetails(FieldContactDetails data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFieldContactDetailsList(List<FieldContactDetails> taskForm22500ModelList);

    @Query("select * from field_contact")
    List<FieldContactDetails> getFieldContactDetails();

    @Query("DELETE from field_contact")
    void removeAll();

    @Query("DELETE from field_contact where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from field_contact")
    long getNextPrimaryId();

}
