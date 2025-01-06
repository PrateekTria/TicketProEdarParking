package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.TaskReportModel;
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
public final class TaskReportModelDao_Impl implements TaskReportModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TaskReportModel> __insertionAdapterOfTaskReportModel;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public TaskReportModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTaskReportModel = new EntityInsertionAdapter<TaskReportModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `task_report` (`id`,`userId`,`assignment_loc_report_id`,`dar_task_report_id`,`start_time`,`end_time`,`task_id`,`deviceid`,`ass_location_report_id`,`mileage_id`,`location_id`,`assignment_id`,`mileage`,`action_id`,`disinfecting`,`equipment_id`,`sub_equipment_id`,`vdr`,`appId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskReportModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getUserid() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getUserid());
        }
        if (value.getAssignmentLocReportId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAssignmentLocReportId());
        }
        if (value.getDartaskReportId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDartaskReportId());
        }
        if (value.getStartTime() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getStartTime());
        }
        if (value.getEndTime() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getEndTime());
        }
        if (value.getTaskId() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getTaskId());
        }
        if (value.getDeviceid() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getDeviceid());
        }
        if (value.getAsslocationReportId() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getAsslocationReportId());
        }
        if (value.getMileageId() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getMileageId());
        }
        if (value.getLocationId() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getLocationId());
        }
        if (value.getAssignmentId() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindLong(12, value.getAssignmentId());
        }
        if (value.getMileage() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getMileage());
        }
        if (value.getActionId() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getActionId());
        }
        if (value.getDisinfecting() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getDisinfecting());
        }
        if (value.getEquipmentId() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getEquipmentId());
        }
        if (value.getSubEquipmentId() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getSubEquipmentId());
        }
        if (value.getVdr() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getVdr());
        }
        if (value.getAppId() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindLong(19, value.getAppId());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from task_report";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from task_report where id=?";
        return _query;
      }
    };
  }

  @Override
  public void insertTaskReportModel(final TaskReportModel... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTaskReportModel.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Completable insertTaskReportModel(final TaskReportModel data) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTaskReportModel.insert(data);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public void insertTaskReportModelList(final List<TaskReportModel> taskForm22500ModelList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTaskReportModel.insert(taskForm22500ModelList);
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
  public List<TaskReportModel> getTaskReportModel() {
    final String _sql = "select * from task_report";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserid = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfAssignmentLocReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_loc_report_id");
      final int _cursorIndexOfDartaskReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "dar_task_report_id");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
      final int _cursorIndexOfDeviceid = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceid");
      final int _cursorIndexOfAsslocationReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "ass_location_report_id");
      final int _cursorIndexOfMileageId = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage_id");
      final int _cursorIndexOfLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "location_id");
      final int _cursorIndexOfAssignmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_id");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfActionId = CursorUtil.getColumnIndexOrThrow(_cursor, "action_id");
      final int _cursorIndexOfDisinfecting = CursorUtil.getColumnIndexOrThrow(_cursor, "disinfecting");
      final int _cursorIndexOfEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment_id");
      final int _cursorIndexOfSubEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_equipment_id");
      final int _cursorIndexOfVdr = CursorUtil.getColumnIndexOrThrow(_cursor, "vdr");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final List<TaskReportModel> _result = new ArrayList<TaskReportModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TaskReportModel _item;
        _item = new TaskReportModel();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final Integer _tmpUserid;
        if (_cursor.isNull(_cursorIndexOfUserid)) {
          _tmpUserid = null;
        } else {
          _tmpUserid = _cursor.getInt(_cursorIndexOfUserid);
        }
        _item.setUserid(_tmpUserid);
        final String _tmpAssignmentLocReportId;
        if (_cursor.isNull(_cursorIndexOfAssignmentLocReportId)) {
          _tmpAssignmentLocReportId = null;
        } else {
          _tmpAssignmentLocReportId = _cursor.getString(_cursorIndexOfAssignmentLocReportId);
        }
        _item.setAssignmentLocReportId(_tmpAssignmentLocReportId);
        final String _tmpDartaskReportId;
        if (_cursor.isNull(_cursorIndexOfDartaskReportId)) {
          _tmpDartaskReportId = null;
        } else {
          _tmpDartaskReportId = _cursor.getString(_cursorIndexOfDartaskReportId);
        }
        _item.setDartaskReportId(_tmpDartaskReportId);
        final String _tmpStartTime;
        if (_cursor.isNull(_cursorIndexOfStartTime)) {
          _tmpStartTime = null;
        } else {
          _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
        }
        _item.setStartTime(_tmpStartTime);
        final String _tmpEndTime;
        if (_cursor.isNull(_cursorIndexOfEndTime)) {
          _tmpEndTime = null;
        } else {
          _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
        }
        _item.setEndTime(_tmpEndTime);
        final String _tmpTaskId;
        if (_cursor.isNull(_cursorIndexOfTaskId)) {
          _tmpTaskId = null;
        } else {
          _tmpTaskId = _cursor.getString(_cursorIndexOfTaskId);
        }
        _item.setTaskId(_tmpTaskId);
        final String _tmpDeviceid;
        if (_cursor.isNull(_cursorIndexOfDeviceid)) {
          _tmpDeviceid = null;
        } else {
          _tmpDeviceid = _cursor.getString(_cursorIndexOfDeviceid);
        }
        _item.setDeviceid(_tmpDeviceid);
        final String _tmpAsslocationReportId;
        if (_cursor.isNull(_cursorIndexOfAsslocationReportId)) {
          _tmpAsslocationReportId = null;
        } else {
          _tmpAsslocationReportId = _cursor.getString(_cursorIndexOfAsslocationReportId);
        }
        _item.setAsslocationReportId(_tmpAsslocationReportId);
        final String _tmpMileageId;
        if (_cursor.isNull(_cursorIndexOfMileageId)) {
          _tmpMileageId = null;
        } else {
          _tmpMileageId = _cursor.getString(_cursorIndexOfMileageId);
        }
        _item.setMileageId(_tmpMileageId);
        final String _tmpLocationId;
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _tmpLocationId = null;
        } else {
          _tmpLocationId = _cursor.getString(_cursorIndexOfLocationId);
        }
        _item.setLocationId(_tmpLocationId);
        final Integer _tmpAssignmentId;
        if (_cursor.isNull(_cursorIndexOfAssignmentId)) {
          _tmpAssignmentId = null;
        } else {
          _tmpAssignmentId = _cursor.getInt(_cursorIndexOfAssignmentId);
        }
        _item.setAssignmentId(_tmpAssignmentId);
        final String _tmpMileage;
        if (_cursor.isNull(_cursorIndexOfMileage)) {
          _tmpMileage = null;
        } else {
          _tmpMileage = _cursor.getString(_cursorIndexOfMileage);
        }
        _item.setMileage(_tmpMileage);
        final String _tmpActionId;
        if (_cursor.isNull(_cursorIndexOfActionId)) {
          _tmpActionId = null;
        } else {
          _tmpActionId = _cursor.getString(_cursorIndexOfActionId);
        }
        _item.setActionId(_tmpActionId);
        final String _tmpDisinfecting;
        if (_cursor.isNull(_cursorIndexOfDisinfecting)) {
          _tmpDisinfecting = null;
        } else {
          _tmpDisinfecting = _cursor.getString(_cursorIndexOfDisinfecting);
        }
        _item.setDisinfecting(_tmpDisinfecting);
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
    final String _sql = "select max(id) as max_id from task_report";
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
