package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarSchoolDropDownElement;
import java.util.List;
import io.reactivex.Maybe;
@Dao
public interface DarSchoolDropDownElementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarSchoolDropDown(DarSchoolDropDownElement... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarSchoolDropDown(DarSchoolDropDownElement data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarSchoolDropDown(List<DarSchoolDropDownElement> darFieldContactDropdowns);

    @Query("DELETE from dar_school_drop_down")
    void removeAll();

    @Query("DELETE from dar_school_drop_down where id=:id")
    void removeById(int id);

    @Query("select * from dar_school_drop_down where custid=:id")
    Maybe<List<DarSchoolDropDownElement>> getDarSchoolDropDown(int id);

    @Query("select * from dar_school_drop_down where id=:id")
    Maybe<List<DarSchoolDropDownElement>> getschooldropdowndetails(int id);
}
