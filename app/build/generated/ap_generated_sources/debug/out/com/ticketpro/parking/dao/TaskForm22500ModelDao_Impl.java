package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.TaskForm22500Model;
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
public final class TaskForm22500ModelDao_Impl implements TaskForm22500ModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TaskForm22500Model> __insertionAdapterOfTaskForm22500Model;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public TaskForm22500ModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTaskForm22500Model = new EntityInsertionAdapter<TaskForm22500Model>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `form_22500` (`id`,`userId`,`task_id`,`mileage_id`,`dar_task_report_id`,`task_date`,`location_id`,`assignment_id`,`equipment_id`,`sub_equipment_id`,`vdr`,`location`,`disinfecting`,`mileage`,`vehicle`,`pdEvent`,`disposition_ddElemId`,`activity_id`,`device_id`,`customerContact`,`notes`,`action_id`,`appId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskForm22500Model value) {
        stmt.bindLong(1, value.getId());
        if (value.getUserId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserId());
        }
        if (value.getTaskId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTaskId());
        }
        if (value.getMileageId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMileageId());
        }
        if (value.getDarTaskReportId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDarTaskReportId());
        }
        if (value.getTaskDate() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getTaskDate());
        }
        if (value.getLocationId() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLocationId());
        }
        if (value.getAssignmentId() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, value.getAssignmentId());
        }
        if (value.getEquipmentId() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getEquipmentId());
        }
        if (value.getSubEquipmentId() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getSubEquipmentId());
        }
        if (value.getVdr() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getVdr());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getLocation());
        }
        if (value.getDisinfecting() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getDisinfecting());
        }
        if (value.getMileage() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getMileage());
        }
        if (value.getVehicle() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getVehicle());
        }
        if (value.getPdEvent() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getPdEvent());
        }
        if (value.getDispositionDdElemId() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindLong(17, value.getDispositionDdElemId());
        }
        if (value.getActivityId() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getActivityId());
        }
        if (value.getDeviceId() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getDeviceId());
        }
        if (value.getCustomerContact() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getCustomerContact());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getNotes());
        }
        if (value.getActionId() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getActionId());
        }
        if (value.getAppId() == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindLong(23, value.getAppId());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from form_22500";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from form_22500 where appId=?";
        return _query;
      }
    };
  }

  @Override
  public void insertTaskForm22500Model(final TaskForm22500Model... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTaskForm22500Model.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Completable insertTaskForm22500Model(final TaskForm22500Model data) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTaskForm22500Model.insert(data);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public void insertTaskForm22500ModelList(final List<TaskForm22500Model> taskForm22500ModelList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTaskForm22500Model.insert(taskForm22500ModelList);
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
  public List<TaskForm22500Model> getTaskForm22500Model() {
    final String _sql = "select * from form_22500";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
      final int _cursorIndexOfMileageId = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage_id");
      final int _cursorIndexOfDarTaskReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "dar_task_report_id");
      final int _cursorIndexOfTaskDate = CursorUtil.getColumnIndexOrThrow(_cursor, "task_date");
      final int _cursorIndexOfLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "location_id");
      final int _cursorIndexOfAssignmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_id");
      final int _cursorIndexOfEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment_id");
      final int _cursorIndexOfSubEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_equipment_id");
      final int _cursorIndexOfVdr = CursorUtil.getColumnIndexOrThrow(_cursor, "vdr");
      final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
      final int _cursorIndexOfDisinfecting = CursorUtil.getColumnIndexOrThrow(_cursor, "disinfecting");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfVehicle = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicle");
      final int _cursorIndexOfPdEvent = CursorUtil.getColumnIndexOrThrow(_cursor, "pdEvent");
      final int _cursorIndexOfDispositionDdElemId = CursorUtil.getColumnIndexOrThrow(_cursor, "disposition_ddElemId");
      final int _cursorIndexOfActivityId = CursorUtil.getColumnIndexOrThrow(_cursor, "activity_id");
      final int _cursorIndexOfDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "device_id");
      final int _cursorIndexOfCustomerContact = CursorUtil.getColumnIndexOrThrow(_cursor, "customerContact");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfActionId = CursorUtil.getColumnIndexOrThrow(_cursor, "action_id");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final List<TaskForm22500Model> _result = new ArrayList<TaskForm22500Model>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TaskForm22500Model _item;
        _item = new TaskForm22500Model();
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
        final String _tmpTaskId;
        if (_cursor.isNull(_cursorIndexOfTaskId)) {
          _tmpTaskId = null;
        } else {
          _tmpTaskId = _cursor.getString(_cursorIndexOfTaskId);
        }
        _item.setTaskId(_tmpTaskId);
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
        final String _tmpTaskDate;
        if (_cursor.isNull(_cursorIndexOfTaskDate)) {
          _tmpTaskDate = null;
        } else {
          _tmpTaskDate = _cursor.getString(_cursorIndexOfTaskDate);
        }
        _item.setTaskDate(_tmpTaskDate);
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
        final String _tmpLocation;
        if (_cursor.isNull(_cursorIndexOfLocation)) {
          _tmpLocation = null;
        } else {
          _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        }
        _item.setLocation(_tmpLocation);
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
        final String _tmpPdEvent;
        if (_cursor.isNull(_cursorIndexOfPdEvent)) {
          _tmpPdEvent = null;
        } else {
          _tmpPdEvent = _cursor.getString(_cursorIndexOfPdEvent);
        }
        _item.setPdEvent(_tmpPdEvent);
        final Integer _tmpDispositionDdElemId;
        if (_cursor.isNull(_cursorIndexOfDispositionDdElemId)) {
          _tmpDispositionDdElemId = null;
        } else {
          _tmpDispositionDdElemId = _cursor.getInt(_cursorIndexOfDispositionDdElemId);
        }
        _item.setDispositionDdElemId(_tmpDispositionDdElemId);
        final String _tmpActivityId;
        if (_cursor.isNull(_cursorIndexOfActivityId)) {
          _tmpActivityId = null;
        } else {
          _tmpActivityId = _cursor.getString(_cursorIndexOfActivityId);
        }
        _item.setActivityId(_tmpActivityId);
        final String _tmpDeviceId;
        if (_cursor.isNull(_cursorIndexOfDeviceId)) {
          _tmpDeviceId = null;
        } else {
          _tmpDeviceId = _cursor.getString(_cursorIndexOfDeviceId);
        }
        _item.setDeviceId(_tmpDeviceId);
        final String _tmpCustomerContact;
        if (_cursor.isNull(_cursorIndexOfCustomerContact)) {
          _tmpCustomerContact = null;
        } else {
          _tmpCustomerContact = _cursor.getString(_cursorIndexOfCustomerContact);
        }
        _item.setCustomerContact(_tmpCustomerContact);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
        final String _tmpActionId;
        if (_cursor.isNull(_cursorIndexOfActionId)) {
          _tmpActionId = null;
        } else {
          _tmpActionId = _cursor.getString(_cursorIndexOfActionId);
        }
        _item.setActionId(_tmpActionId);
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
    final String _sql = "select max(id) as max_id from form_22500";
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
