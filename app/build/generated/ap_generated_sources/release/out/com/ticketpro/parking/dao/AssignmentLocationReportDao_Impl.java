package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.AssignmentLocationReportModel;
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
public final class AssignmentLocationReportDao_Impl implements AssignmentLocationReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AssignmentLocationReportModel> __insertionAdapterOfAssignmentLocationReportModel;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public AssignmentLocationReportDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAssignmentLocationReportModel = new EntityInsertionAdapter<AssignmentLocationReportModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `assignment_loc_report` (`id`,`userid`,`assignment_report_id`,`ass_report_id`,`start_time`,`end_time`,`deviceid`,`location_id`,`assignment_loc_id`,`ass_location_report_id`,`appId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AssignmentLocationReportModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getUserid() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getUserid());
        }
        if (value.getAssignmentReportId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAssignmentReportId());
        }
        if (value.getAssReportId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAssReportId());
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
        if (value.getDeviceid() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDeviceid());
        }
        if (value.getLocationId() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getLocationId());
        }
        if (value.getAssignmentLocId() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getAssignmentLocId());
        }
        if (value.getAssLocationReportId() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getAssLocationReportId());
        }
        if (value.getAppId() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, value.getAppId());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from assignment_loc_report";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from assignment_loc_report where id=?";
        return _query;
      }
    };
  }

  @Override
  public void insertAssignmentLocationReportModel(final AssignmentLocationReportModel... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAssignmentLocationReportModel.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Completable insertAssignmentLocationReportModel(final AssignmentLocationReportModel data) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAssignmentLocationReportModel.insert(data);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public void insertAssignmentLocationReportModelList(
      final List<AssignmentLocationReportModel> taskForm22500ModelList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAssignmentLocationReportModel.insert(taskForm22500ModelList);
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
  public List<AssignmentLocationReportModel> getAssignmentLocationReportModel() {
    final String _sql = "select * from assignment_loc_report";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserid = CursorUtil.getColumnIndexOrThrow(_cursor, "userid");
      final int _cursorIndexOfAssignmentReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_report_id");
      final int _cursorIndexOfAssReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "ass_report_id");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
      final int _cursorIndexOfDeviceid = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceid");
      final int _cursorIndexOfLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "location_id");
      final int _cursorIndexOfAssignmentLocId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_loc_id");
      final int _cursorIndexOfAssLocationReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "ass_location_report_id");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final List<AssignmentLocationReportModel> _result = new ArrayList<AssignmentLocationReportModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final AssignmentLocationReportModel _item;
        _item = new AssignmentLocationReportModel();
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
        final String _tmpAssignmentReportId;
        if (_cursor.isNull(_cursorIndexOfAssignmentReportId)) {
          _tmpAssignmentReportId = null;
        } else {
          _tmpAssignmentReportId = _cursor.getString(_cursorIndexOfAssignmentReportId);
        }
        _item.setAssignmentReportId(_tmpAssignmentReportId);
        final String _tmpAssReportId;
        if (_cursor.isNull(_cursorIndexOfAssReportId)) {
          _tmpAssReportId = null;
        } else {
          _tmpAssReportId = _cursor.getString(_cursorIndexOfAssReportId);
        }
        _item.setAssReportId(_tmpAssReportId);
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
        final String _tmpDeviceid;
        if (_cursor.isNull(_cursorIndexOfDeviceid)) {
          _tmpDeviceid = null;
        } else {
          _tmpDeviceid = _cursor.getString(_cursorIndexOfDeviceid);
        }
        _item.setDeviceid(_tmpDeviceid);
        final String _tmpLocationId;
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _tmpLocationId = null;
        } else {
          _tmpLocationId = _cursor.getString(_cursorIndexOfLocationId);
        }
        _item.setLocationId(_tmpLocationId);
        final String _tmpAssignmentLocId;
        if (_cursor.isNull(_cursorIndexOfAssignmentLocId)) {
          _tmpAssignmentLocId = null;
        } else {
          _tmpAssignmentLocId = _cursor.getString(_cursorIndexOfAssignmentLocId);
        }
        _item.setAssignmentLocId(_tmpAssignmentLocId);
        final String _tmpAssLocationReportId;
        if (_cursor.isNull(_cursorIndexOfAssLocationReportId)) {
          _tmpAssLocationReportId = null;
        } else {
          _tmpAssLocationReportId = _cursor.getString(_cursorIndexOfAssLocationReportId);
        }
        _item.setAssLocationReportId(_tmpAssLocationReportId);
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
    final String _sql = "select max(id) as max_id from assignment_loc_report";
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
