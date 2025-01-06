package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.TrafficDetails;
import io.reactivex.Completable;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.Void;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TrafficDetailsDao_Impl implements TrafficDetailsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TrafficDetails> __insertionAdapterOfTrafficDetails;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public TrafficDetailsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTrafficDetails = new EntityInsertionAdapter<TrafficDetails>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `traffic_details` (`id`,`userId`,`mileage_id`,`assignment_id`,`equipment_id`,`sub_equipment_id`,`vdr`,`disinfecting`,`mileage`,`vehicle`,`nonInforcement_dd_elementId`,`name`,`badge`,`datetime`,`event_name`,`dar_task_report_id`,`start_shift`,`release_post_time`,`end_shift`,`deviceid`,`appId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TrafficDetails value) {
        stmt.bindLong(1, value.getId());
        if (value.getUserId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserId());
        }
        if (value.getMileageId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMileageId());
        }
        if (value.getAssignmentId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getAssignmentId());
        }
        if (value.getEquipmentId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEquipmentId());
        }
        if (value.getSubEquipmentId() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getSubEquipmentId());
        }
        if (value.getVdr() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getVdr());
        }
        if (value.getDisinfecting() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getDisinfecting());
        }
        if (value.getMileage() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getMileage());
        }
        if (value.getVehicle() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getVehicle());
        }
        if (value.getNonInforcementDdElementId() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getNonInforcementDdElementId());
        }
        if (value.getName() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getName());
        }
        if (value.getBadge() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getBadge());
        }
        if (value.getDatetime() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getDatetime());
        }
        if (value.getEventName() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getEventName());
        }
        if (value.getDarTaskReportId() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getDarTaskReportId());
        }
        if (value.getStartShift() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getStartShift());
        }
        if (value.getReleasePostTime() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getReleasePostTime());
        }
        if (value.getEndShift() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getEndShift());
        }
        if (value.getDeviceid() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getDeviceid());
        }
        if (value.getAppId() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindLong(21, value.getAppId());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from traffic_details";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from traffic_details where appId=?";
        return _query;
      }
    };
  }

  @Override
  public void insertTrafficDetails(final TrafficDetails... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTrafficDetails.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Completable insertTrafficDetails(final TrafficDetails data) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTrafficDetails.insert(data);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public void insertTrafficDetailsList(final List<TrafficDetails> taskForm22500ModelList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTrafficDetails.insert(taskForm22500ModelList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void removeAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfRemoveAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfRemoveAll.release(_stmt);
    }
  }

  @Override
  public void removeById(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfRemoveById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfRemoveById.release(_stmt);
    }
  }

  @Override
  public List<TrafficDetails> getTrafficDetails() {
    final String _sql = "select * from traffic_details";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfMileageId = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage_id");
      final int _cursorIndexOfAssignmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_id");
      final int _cursorIndexOfEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment_id");
      final int _cursorIndexOfSubEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_equipment_id");
      final int _cursorIndexOfVdr = CursorUtil.getColumnIndexOrThrow(_cursor, "vdr");
      final int _cursorIndexOfDisinfecting = CursorUtil.getColumnIndexOrThrow(_cursor, "disinfecting");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfVehicle = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicle");
      final int _cursorIndexOfNonInforcementDdElementId = CursorUtil.getColumnIndexOrThrow(_cursor, "nonInforcement_dd_elementId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfBadge = CursorUtil.getColumnIndexOrThrow(_cursor, "badge");
      final int _cursorIndexOfDatetime = CursorUtil.getColumnIndexOrThrow(_cursor, "datetime");
      final int _cursorIndexOfEventName = CursorUtil.getColumnIndexOrThrow(_cursor, "event_name");
      final int _cursorIndexOfDarTaskReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "dar_task_report_id");
      final int _cursorIndexOfStartShift = CursorUtil.getColumnIndexOrThrow(_cursor, "start_shift");
      final int _cursorIndexOfReleasePostTime = CursorUtil.getColumnIndexOrThrow(_cursor, "release_post_time");
      final int _cursorIndexOfEndShift = CursorUtil.getColumnIndexOrThrow(_cursor, "end_shift");
      final int _cursorIndexOfDeviceid = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceid");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final List<TrafficDetails> _result = new ArrayList<TrafficDetails>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TrafficDetails _item;
        _item = new TrafficDetails();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpUserId;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUserId = null;
        } else {
          _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
        }
        _item.setUserId(_tmpUserId);
        final String _tmpMileageId;
        if (_cursor.isNull(_cursorIndexOfMileageId)) {
          _tmpMileageId = null;
        } else {
          _tmpMileageId = _cursor.getString(_cursorIndexOfMileageId);
        }
        _item.setMileageId(_tmpMileageId);
        final Integer _tmpAssignmentId;
        if (_cursor.isNull(_cursorIndexOfAssignmentId)) {
          _tmpAssignmentId = null;
        } else {
          _tmpAssignmentId = _cursor.getInt(_cursorIndexOfAssignmentId);
        }
        _item.setAssignmentId(_tmpAssignmentId);
        final String _tmpEquipmentId;
        if (_cursor.isNull(_cursorIndexOfEquipmentId)) {
          _tmpEquipmentId = null;
        } else {
          _tmpEquipmentId = _cursor.getString(_cursorIndexOfEquipmentId);
        }
        _item.setEquipmentId(_tmpEquipmentId);
        final String _tmpSubEquipmentId;
        if (_cursor.isNull(_cursorIndexOfSubEquipmentId)) {
          _tmpSubEquipmentId = null;
        } else {
          _tmpSubEquipmentId = _cursor.getString(_cursorIndexOfSubEquipmentId);
        }
        _item.setSubEquipmentId(_tmpSubEquipmentId);
        final String _tmpVdr;
        if (_cursor.isNull(_cursorIndexOfVdr)) {
          _tmpVdr = null;
        } else {
          _tmpVdr = _cursor.getString(_cursorIndexOfVdr);
        }
        _item.setVdr(_tmpVdr);
        final String _tmpDisinfecting;
        if (_cursor.isNull(_cursorIndexOfDisinfecting)) {
          _tmpDisinfecting = null;
        } else {
          _tmpDisinfecting = _cursor.getString(_cursorIndexOfDisinfecting);
        }
        _item.setDisinfecting(_tmpDisinfecting);
        final String _tmpMileage;
        if (_cursor.isNull(_cursorIndexOfMileage)) {
          _tmpMileage = null;
        } else {
          _tmpMileage = _cursor.getString(_cursorIndexOfMileage);
        }
        _item.setMileage(_tmpMileage);
        final String _tmpVehicle;
        if (_cursor.isNull(_cursorIndexOfVehicle)) {
          _tmpVehicle = null;
        } else {
          _tmpVehicle = _cursor.getString(_cursorIndexOfVehicle);
        }
        _item.setVehicle(_tmpVehicle);
        final String _tmpNonInforcementDdElementId;
        if (_cursor.isNull(_cursorIndexOfNonInforcementDdElementId)) {
          _tmpNonInforcementDdElementId = null;
        } else {
          _tmpNonInforcementDdElementId = _cursor.getString(_cursorIndexOfNonInforcementDdElementId);
        }
        _item.setNonInforcementDdElementId(_tmpNonInforcementDdElementId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final String _tmpBadge;
        if (_cursor.isNull(_cursorIndexOfBadge)) {
          _tmpBadge = null;
        } else {
          _tmpBadge = _cursor.getString(_cursorIndexOfBadge);
        }
        _item.setBadge(_tmpBadge);
        final String _tmpDatetime;
        if (_cursor.isNull(_cursorIndexOfDatetime)) {
          _tmpDatetime = null;
        } else {
          _tmpDatetime = _cursor.getString(_cursorIndexOfDatetime);
        }
        _item.setDatetime(_tmpDatetime);
        final String _tmpEventName;
        if (_cursor.isNull(_cursorIndexOfEventName)) {
          _tmpEventName = null;
        } else {
          _tmpEventName = _cursor.getString(_cursorIndexOfEventName);
        }
        _item.setEventName(_tmpEventName);
        final String _tmpDarTaskReportId;
        if (_cursor.isNull(_cursorIndexOfDarTaskReportId)) {
          _tmpDarTaskReportId = null;
        } else {
          _tmpDarTaskReportId = _cursor.getString(_cursorIndexOfDarTaskReportId);
        }
        _item.setDarTaskReportId(_tmpDarTaskReportId);
        final String _tmpStartShift;
        if (_cursor.isNull(_cursorIndexOfStartShift)) {
          _tmpStartShift = null;
        } else {
          _tmpStartShift = _cursor.getString(_cursorIndexOfStartShift);
        }
        _item.setStartShift(_tmpStartShift);
        final String _tmpReleasePostTime;
        if (_cursor.isNull(_cursorIndexOfReleasePostTime)) {
          _tmpReleasePostTime = null;
        } else {
          _tmpReleasePostTime = _cursor.getString(_cursorIndexOfReleasePostTime);
        }
        _item.setReleasePostTime(_tmpReleasePostTime);
        final String _tmpEndShift;
        if (_cursor.isNull(_cursorIndexOfEndShift)) {
          _tmpEndShift = null;
        } else {
          _tmpEndShift = _cursor.getString(_cursorIndexOfEndShift);
        }
        _item.setEndShift(_tmpEndShift);
        final String _tmpDeviceid;
        if (_cursor.isNull(_cursorIndexOfDeviceid)) {
          _tmpDeviceid = null;
        } else {
          _tmpDeviceid = _cursor.getString(_cursorIndexOfDeviceid);
        }
        _item.setDeviceid(_tmpDeviceid);
        final Integer _tmpAppId;
        if (_cursor.isNull(_cursorIndexOfAppId)) {
          _tmpAppId = null;
        } else {
          _tmpAppId = _cursor.getInt(_cursorIndexOfAppId);
        }
        _item.setAppId(_tmpAppId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public long getNextPrimaryId() {
    final String _sql = "select max(id) as max_id from traffic_details";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final long _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getLong(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
