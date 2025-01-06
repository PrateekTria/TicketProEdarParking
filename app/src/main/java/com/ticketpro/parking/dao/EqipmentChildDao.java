package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarChildEquipments;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface EqipmentChildDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipmentChild(DarChildEquipments... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipmentChild(DarChildEquipments data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipmentChildList(List<DarChildEquipments> equipmentList);

    @Query("DELETE from dar_equipment_child")
    void removeAll();

    @Query("DELETE from dar_equipment_child where child_id=:id")
    void removeById(int id);

    @Query("select * from dar_equipment_child where equipment_id = :equipment_id")
    Maybe<List<DarChildEquipments>> getEquipmentChildByTitle(int equipment_id);

    /*@Query("select * from dar_equipment_child where child_id = :child_id")
    Maybe<List<DarChildEquipments>> getEquipmentChildByitem(int child_id);*/

}
