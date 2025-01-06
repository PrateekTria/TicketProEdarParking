package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.CommunityModel;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface CommunityModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCommunityModel(CommunityModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCommunityModel(CommunityModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCommunityModelList(List<CommunityModel> taskForm22500ModelList);

    @Query("select * from community_meeting")
    List<CommunityModel> getCommunityModel();

    @Query("DELETE from community_meeting")
    void removeAll();

    @Query("DELETE from community_meeting where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from community_meeting")
    long getNextPrimaryId();
}
