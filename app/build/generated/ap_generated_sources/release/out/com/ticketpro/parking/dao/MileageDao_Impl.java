package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.Mileage;
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
public final class MileageDao_Impl implements MileageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Mileage> __insertionAdapterOfMileage;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public MileageDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMileage = new EntityInsertionAdapter<Mileage>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `mileage` (`id`,`userid`,`custid`,`startmileage`,`endmileage`,`vehicleid`,`vehiclenumber`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Mileage value) {
        stmt.bindLong(1, value.getId());
        if (value.getUserId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getUserId());
        }
        if (value.getDeviceid() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.getDeviceid());
        }
        if (value.getStartMileage() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStartMileage());
        }
        if (value.getEndMileage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEndMileage());
        }
        if (value.getVehicleId() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getVehicleId());
        }
        if (value.getVehicleNumber() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getVehicleNumber());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from mileage";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from mileage where custid=?";
        return _query;
      }
    };
  }

  @Override
  public Completable insertMileage(final Mileage data) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMileage.insert(data);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
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
  public List<Mileage> getmileage() {
    final String _sql = "select * from mileage";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userid");
      final int _cursorIndexOfDeviceid = CursorUtil.getColumnIndexOrThrow(_cursor, "custid");
      final int _cursorIndexOfStartMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "startmileage");
      final int _cursorIndexOfEndMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "endmileage");
      final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleid");
      final int _cursorIndexOfVehicleNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "vehiclenumber");
      final List<Mileage> _result = new ArrayList<Mileage>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Mileage _item;
        _item = new Mileage();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final Integer _tmpUserId;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUserId = null;
        } else {
          _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
        }
        _item.setUserId(_tmpUserId);
        final Integer _tmpDeviceid;
        if (_cursor.isNull(_cursorIndexOfDeviceid)) {
          _tmpDeviceid = null;
        } else {
          _tmpDeviceid = _cursor.getInt(_cursorIndexOfDeviceid);
        }
        _item.setDeviceid(_tmpDeviceid);
        final String _tmpStartMileage;
        if (_cursor.isNull(_cursorIndexOfStartMileage)) {
          _tmpStartMileage = null;
        } else {
          _tmpStartMileage = _cursor.getString(_cursorIndexOfStartMileage);
        }
        _item.setStartMileage(_tmpStartMileage);
        final String _tmpEndMileage;
        if (_cursor.isNull(_cursorIndexOfEndMileage)) {
          _tmpEndMileage = null;
        } else {
          _tmpEndMileage = _cursor.getString(_cursorIndexOfEndMileage);
        }
        _item.setEndMileage(_tmpEndMileage);
        final String _tmpVehicleId;
        if (_cursor.isNull(_cursorIndexOfVehicleId)) {
          _tmpVehicleId = null;
        } else {
          _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
        }
        _item.setVehicleId(_tmpVehicleId);
        final String _tmpVehicleNumber;
        if (_cursor.isNull(_cursorIndexOfVehicleNumber)) {
          _tmpVehicleNumber = null;
        } else {
          _tmpVehicleNumber = _cursor.getString(_cursorIndexOfVehicleNumber);
        }
        _item.setVehicleNumber(_tmpVehicleNumber);
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
    final String _sql = "select max(id) as max_id from mileage";
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
