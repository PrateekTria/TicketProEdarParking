package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.SeniorDutiesModel;
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
public final class SeniorDutiesModelDao_Impl implements SeniorDutiesModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SeniorDutiesModel> __insertionAdapterOfSeniorDutiesModel;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public SeniorDutiesModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSeniorDutiesModel = new EntityInsertionAdapter<SeniorDutiesModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `senior_duties` (`id`,`userId`,`assignment_id`,`equipment_id`,`sub_equipment_id`,`vdr`,`disinfecting`,`mileage`,`mileage_id`,`dar_task_report_id`,`vehicle`,`nonInforcement_dd_elementId`,`datetime`,`senior_duty_val`,`deviceid`,`appId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SeniorDutiesModel value) {
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
        if (value.getVdr() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getVdr());
        }
        if (value.getDisinfecting() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDisinfecting());
        }
        if (value.getMileage() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getMileage());
        }
        if (value.getMileageId() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getMileageId());
        }
        if (value.getDarTaskReportId() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getDarTaskReportId());
        }
        if (value.getVehicle() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getVehicle());
        }
        if (value.getNonInforcementDdElementId() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getNonInforcementDdElementId());
        }
        if (value.getDatetime() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getDatetime());
        }
        if (value.getSeniorDutyVal() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getSeniorDutyVal());
        }
        if (value.getDeviceid() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getDeviceid());
        }
        if (value.getAppId() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindLong(16, value.getAppId());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from senior_duties";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from senior_duties where id=?";
        return _query;
      }
    };
  }

  @Override
  public void insertSeniorDutiesModel(final SeniorDutiesModel... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSeniorDutiesModel.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Completable insertSeniorDutiesModel(final SeniorDutiesModel data) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSeniorDutiesModel.insert(data);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public void insertSeniorDutiesModelList(final List<SeniorDutiesModel> taskForm22500ModelList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSeniorDutiesModel.insert(taskForm22500ModelList);
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
  public List<SeniorDutiesModel> getSeniorDutiesModel() {
    final String _sql = "select * from senior_duties";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfAssignmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_id");
      final int _cursorIndexOfEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment_id");
      final int _cursorIndexOfSubEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_equipment_id");
      final int _cursorIndexOfVdr = CursorUtil.getColumnIndexOrThrow(_cursor, "vdr");
      final int _cursorIndexOfDisinfecting = CursorUtil.getColumnIndexOrThrow(_cursor, "disinfecting");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfMileageId = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage_id");
      final int _cursorIndexOfDarTaskReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "dar_task_report_id");
      final int _cursorIndexOfVehicle = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicle");
      final int _cursorIndexOfNonInforcementDdElementId = CursorUtil.getColumnIndexOrThrow(_cursor, "nonInforcement_dd_elementId");
      final int _cursorIndexOfDatetime = CursorUtil.getColumnIndexOrThrow(_cursor, "datetime");
      final int _cursorIndexOfSeniorDutyVal = CursorUtil.getColumnIndexOrThrow(_cursor, "senior_duty_val");
      final int _cursorIndexOfDeviceid = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceid");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final List<SeniorDutiesModel> _result = new ArrayList<SeniorDutiesModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SeniorDutiesModel _item;
        _item = new SeniorDutiesModel();
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
        final String _tmpMileageId;
        if (_cursor.isNull(_cursorIndexOfMileageId)) {
          _tmpMileageId = null;
        } else {
          _tmpMileageId = _cursor.getString(_cursorIndexOfMileageId);
        }
        _item.setMileageId(_tmpMileageId);
        final String _tmpDarTaskReportId;
        if (_cursor.isNull(_cursorIndexOfDarTaskReportId)) {
          _tmpDarTaskReportId = null;
        } else {
          _tmpDarTaskReportId = _cursor.getString(_cursorIndexOfDarTaskReportId);
        }
        _item.setDarTaskReportId(_tmpDarTaskReportId);
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
        final String _tmpDatetime;
        if (_cursor.isNull(_cursorIndexOfDatetime)) {
          _tmpDatetime = null;
        } else {
          _tmpDatetime = _cursor.getString(_cursorIndexOfDatetime);
        }
        _item.setDatetime(_tmpDatetime);
        final String _tmpSeniorDutyVal;
        if (_cursor.isNull(_cursorIndexOfSeniorDutyVal)) {
          _tmpSeniorDutyVal = null;
        } else {
          _tmpSeniorDutyVal = _cursor.getString(_cursorIndexOfSeniorDutyVal);
        }
        _item.setSeniorDutyVal(_tmpSeniorDutyVal);
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
    final String _sql = "select max(id) as max_id from senior_duties";
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
