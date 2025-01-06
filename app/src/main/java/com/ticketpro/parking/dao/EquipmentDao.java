package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.ticketpro.model.Body;
import com.ticketpro.parking.dar.model.Equipments;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface EquipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipment(Equipments... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipment(Equipments data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipmentList(List<Equipments> equipmentList);

    @Query("DELETE from dar_equipment")
    void removeAll();

    @Query("DELETE from dar_equipment where id=:id")
    void removeById(int id);

    @Query("select * from dar_equipment where custid = :custId")
    Maybe<List<Equipments>> getEquipmentByTitle(int custId);

}
