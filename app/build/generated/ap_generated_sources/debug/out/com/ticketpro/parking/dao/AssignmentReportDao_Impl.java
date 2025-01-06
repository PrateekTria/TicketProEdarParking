package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.AssignmentReportModel;
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
public final class AssignmentReportDao_Impl implements AssignmentReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AssignmentReportModel> __insertionAdapterOfAssignmentReportModel;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public AssignmentReportDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAssignmentReportModel = new EntityInsertionAdapter<AssignmentReportModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `assignment_report` (`id`,`userid`,`assignment_id`,`start_time`,`end_time`,`deviceid`,`assignment_report_id`,`appId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AssignmentReportModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getUserid() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getUserid());
        }
        if (value.getAssignmentId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAssignmentId());
        }
        if (value.getStartTime() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStartTime());
        }
        if (value.getEndTime() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEndTime());
        }
        if (value.getDeviceid() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDeviceid());
        }
        if (value.getAssignmentreportId() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getAssignmentreportId());
        }
        if (value.getAppId() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, value.getAppId());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from assignment_report";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from assignment_report where id=?";
        return _query;
      }
    };
  }

  @Override
  public void insertAssignmentReportModel(final AssignmentReportModel... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAssignmentReportModel.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Completable insertAssignmentReportModel(final AssignmentReportModel data) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAssignmentReportModel.insert(data);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public void insertAssignmentReportModelList(
      final List<AssignmentReportModel> taskForm22500ModelList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAssignmentReportModel.insert(taskForm22500ModelList);
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
  public List<AssignmentReportModel> getAssignmentReportModel() {
    final String _sql = "select * from assignment_report";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserid = CursorUtil.getColumnIndexOrThrow(_cursor, "userid");
      final int _cursorIndexOfAssignmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_id");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
      final int _cursorIndexOfDeviceid = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceid");
      final int _cursorIndexOfAssignmentreportId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_report_id");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final List<AssignmentReportModel> _result = new ArrayList<AssignmentReportModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final AssignmentReportModel _item;
        _item = new AssignmentReportModel();
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
        final String _tmpAssignmentId;
        if (_cursor.isNull(_cursorIndexOfAssignmentId)) {
          _tmpAssignmentId = null;
        } else {
          _tmpAssignmentId = _cursor.getString(_cursorIndexOfAssignmentId);
        }
        _item.setAssignmentId(_tmpAssignmentId);
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
        final String _tmpAssignmentreportId;
        if (_cursor.isNull(_cursorIndexOfAssignmentreportId)) {
          _tmpAssignmentreportId = null;
        } else {
          _tmpAssignmentreportId = _cursor.getString(_cursorIndexOfAssignmentreportId);
        }
        _item.setAssignmentreportId(_tmpAssignmentreportId);
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
    final String _sql = "select max(id) as max_id from assignment_report";
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
