package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.TowModel;
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
public final class TowModelDao_Impl implements TowModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TowModel> __insertionAdapterOfTowModel;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public TowModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTowModel = new EntityInsertionAdapter<TowModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `tow` (`id`,`userId`,`assignment_id`,`equipment_id`,`sub_equipment_id`,`task_id`,`vdr`,`disinfecting`,`mileage`,`vehicle`,`deviceid`,`pd_event`,`tow_company_ddElemId`,`tow_authority_ddElemId`,`date_time`,`dispatch_time`,`tow_arrival`,`location`,`vehicle_licence_plate`,`tow_refused_ddElemId`,`comments`,`appId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TowModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getUserId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserId());
        }
        if (value.getAssignmentId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.getAssignmentId());
        }
        if (value.getEquipmentId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEquipmentId());
        }
        if (value.getSubEquipmentId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getSubEquipmentId());
        }
        if (value.getTaskId() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getTaskId());
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
        if (value.getDeviceid() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getDeviceid());
        }
        if (value.getPdEvent() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getPdEvent());
        }
        if (value.getTowCompanyDdElemId() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getTowCompanyDdElemId());
        }
        if (value.getTowAuthorityDdElemId() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getTowAuthorityDdElemId());
        }
        if (value.getDateTime() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getDateTime());
        }
        if (value.getDispatchTime() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getDispatchTime());
        }
        if (value.getTowArrival() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getTowArrival());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getLocation());
        }
        if (value.getVehicleLicencePlate() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getVehicleLicencePlate());
        }
        if (value.getTowRefusedDdElemId() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getTowRefusedDdElemId());
        }
        if (value.getComments() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getComments());
        }
        if (value.getAppId() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindLong(22, value.getAppId());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from tow";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from tow where appId=?";
        return _query;
      }
    };
  }

  @Override
  public void insertTowModel(final TowModel... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTowModel.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Completable insertTowModel(final TowModel data) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTowModel.insert(data);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public void insertTowModel(final List<TowModel> taskTowModelList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTowModel.insert(taskTowModelList);
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
  public List<TowModel> getTowModel() {
    final String _sql = "select * from tow";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfAssignmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_id");
      final int _cursorIndexOfEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment_id");
      final int _cursorIndexOfSubEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_equipment_id");
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
      final int _cursorIndexOfVdr = CursorUtil.getColumnIndexOrThrow(_cursor, "vdr");
      final int _cursorIndexOfDisinfecting = CursorUtil.getColumnIndexOrThrow(_cursor, "disinfecting");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfVehicle = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicle");
      final int _cursorIndexOfDeviceid = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceid");
      final int _cursorIndexOfPdEvent = CursorUtil.getColumnIndexOrThrow(_cursor, "pd_event");
      final int _cursorIndexOfTowCompanyDdElemId = CursorUtil.getColumnIndexOrThrow(_cursor, "tow_company_ddElemId");
      final int _cursorIndexOfTowAuthorityDdElemId = CursorUtil.getColumnIndexOrThrow(_cursor, "tow_authority_ddElemId");
      final int _cursorIndexOfDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "date_time");
      final int _cursorIndexOfDispatchTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dispatch_time");
      final int _cursorIndexOfTowArrival = CursorUtil.getColumnIndexOrThrow(_cursor, "tow_arrival");
      final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
      final int _cursorIndexOfVehicleLicencePlate = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicle_licence_plate");
      final int _cursorIndexOfTowRefusedDdElemId = CursorUtil.getColumnIndexOrThrow(_cursor, "tow_refused_ddElemId");
      final int _cursorIndexOfComments = CursorUtil.getColumnIndexOrThrow(_cursor, "comments");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final List<TowModel> _result = new ArrayList<TowModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TowModel _item;
        _item = new TowModel();
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
        final String _tmpTaskId;
        if (_cursor.isNull(_cursorIndexOfTaskId)) {
          _tmpTaskId = null;
        } else {
          _tmpTaskId = _cursor.getString(_cursorIndexOfTaskId);
        }
        _item.setTaskId(_tmpTaskId);
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
        final String _tmpDeviceid;
        if (_cursor.isNull(_cursorIndexOfDeviceid)) {
          _tmpDeviceid = null;
        } else {
          _tmpDeviceid = _cursor.getString(_cursorIndexOfDeviceid);
        }
        _item.setDeviceid(_tmpDeviceid);
        final String _tmpPdEvent;
        if (_cursor.isNull(_cursorIndexOfPdEvent)) {
          _tmpPdEvent = null;
        } else {
          _tmpPdEvent = _cursor.getString(_cursorIndexOfPdEvent);
        }
        _item.setPdEvent(_tmpPdEvent);
        final String _tmpTowCompanyDdElemId;
        if (_cursor.isNull(_cursorIndexOfTowCompanyDdElemId)) {
          _tmpTowCompanyDdElemId = null;
        } else {
          _tmpTowCompanyDdElemId = _cursor.getString(_cursorIndexOfTowCompanyDdElemId);
        }
        _item.setTowCompanyDdElemId(_tmpTowCompanyDdElemId);
        final String _tmpTowAuthorityDdElemId;
        if (_cursor.isNull(_cursorIndexOfTowAuthorityDdElemId)) {
          _tmpTowAuthorityDdElemId = null;
        } else {
          _tmpTowAuthorityDdElemId = _cursor.getString(_cursorIndexOfTowAuthorityDdElemId);
        }
        _item.setTowAuthorityDdElemId(_tmpTowAuthorityDdElemId);
        final String _tmpDateTime;
        if (_cursor.isNull(_cursorIndexOfDateTime)) {
          _tmpDateTime = null;
        } else {
          _tmpDateTime = _cursor.getString(_cursorIndexOfDateTime);
        }
        _item.setDateTime(_tmpDateTime);
        final String _tmpDispatchTime;
        if (_cursor.isNull(_cursorIndexOfDispatchTime)) {
          _tmpDispatchTime = null;
        } else {
          _tmpDispatchTime = _cursor.getString(_cursorIndexOfDispatchTime);
        }
        _item.setDispatchTime(_tmpDispatchTime);
        final String _tmpTowArrival;
        if (_cursor.isNull(_cursorIndexOfTowArrival)) {
          _tmpTowArrival = null;
        } else {
          _tmpTowArrival = _cursor.getString(_cursorIndexOfTowArrival);
        }
        _item.setTowArrival(_tmpTowArrival);
        final String _tmpLocation;
        if (_cursor.isNull(_cursorIndexOfLocation)) {
          _tmpLocation = null;
        } else {
          _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        }
        _item.setLocation(_tmpLocation);
        final String _tmpVehicleLicencePlate;
        if (_cursor.isNull(_cursorIndexOfVehicleLicencePlate)) {
          _tmpVehicleLicencePlate = null;
        } else {
          _tmpVehicleLicencePlate = _cursor.getString(_cursorIndexOfVehicleLicencePlate);
        }
        _item.setVehicleLicencePlate(_tmpVehicleLicencePlate);
        final String _tmpTowRefusedDdElemId;
        if (_cursor.isNull(_cursorIndexOfTowRefusedDdElemId)) {
          _tmpTowRefusedDdElemId = null;
        } else {
          _tmpTowRefusedDdElemId = _cursor.getString(_cursorIndexOfTowRefusedDdElemId);
        }
        _item.setTowRefusedDdElemId(_tmpTowRefusedDdElemId);
        final String _tmpComments;
        if (_cursor.isNull(_cursorIndexOfComments)) {
          _tmpComments = null;
        } else {
          _tmpComments = _cursor.getString(_cursorIndexOfComments);
        }
        _item.setComments(_tmpComments);
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
    final String _sql = "select max(id) as max_id from tow";
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
