package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.Dar22500DisposionDropDownElement;
import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface Dar22500DisposionDropDownElementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDar22500DisposionDropDownElement(Dar22500DisposionDropDownElement... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDar22500DisposionDropDownElement(Dar22500DisposionDropDownElement data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffsiteDropdownElements(List<Dar22500DisposionDropDownElement> dar22500DisposionDropDownElementList);

    @Query("DELETE from dar_22500_disposition_dropdown")
    void removeAll();

    @Query("DELETE from dar_22500_disposition_dropdown where id=:id")
    void removeById(int id);


    @Query("select * from dar_22500_disposition_dropdown where custid=:custId")
    Maybe<List<Dar22500DisposionDropDownElement>> getDar22500DisposionDropDownElement(int custId);

}
