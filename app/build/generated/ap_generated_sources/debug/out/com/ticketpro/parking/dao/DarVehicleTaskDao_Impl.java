package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.DarVehicleTask;
import io.reactivex.Maybe;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DarVehicleTaskDao_Impl implements DarVehicleTaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DarVehicleTask> __insertionAdapterOfDarVehicleTask;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public DarVehicleTaskDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDarVehicleTask = new EntityInsertionAdapter<DarVehicleTask>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `dar_vehicle_task` (`veh_task_id`,`veh_task_name`,`userid`,`custid`,`order_number`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DarVehicleTask value) {
        if (value.getVehTaskId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getVehTaskId());
        }
        if (value.getVehTaskName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVehTaskName());
        }
        if (value.getUserid() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.getUserid());
        }
        if (value.getCustid() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getCustid());
        }
        if (value.getOrderNumber() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getOrderNumber());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_vehicle_task";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_vehicle_task where veh_task_id=?";
        return _query;
      }
    };
  }

  @Override
  public void insertDarVehicleTask(final DarVehicleTask... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarVehicleTask.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertDarVehicleTask(final DarVehicleTask data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarVehicleTask.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertDarVehicleTaskList(final List<DarVehicleTask> equipmentList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarVehicleTask.insert(equipmentList);
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
  public Maybe<List<DarVehicleTask>> getDarVehicleTask(final int userid) {
    final String _sql = "select * from dar_vehicle_task where custid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userid);
    return Maybe.fromCallable(new Callable<List<DarVehicleTask>>() {
      @Override
      public List<DarVehicleTask> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfVehTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "veh_task_id");
          final int _cursorIndexOfVehTaskName = CursorUtil.getColumnIndexOrThrow(_cursor, "veh_task_name");
          final int _cursorIndexOfUserid = CursorUtil.getColumnIndexOrThrow(_cursor, "userid");
          final int _cursorIndexOfCustid = CursorUtil.getColumnIndexOrThrow(_cursor, "custid");
          final int _cursorIndexOfOrderNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "order_number");
          final List<DarVehicleTask> _result = new ArrayList<DarVehicleTask>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DarVehicleTask _item;
            _item = new DarVehicleTask();
            final Integer _tmpVehTaskId;
            if (_cursor.isNull(_cursorIndexOfVehTaskId)) {
              _tmpVehTaskId = null;
            } else {
              _tmpVehTaskId = _cursor.getInt(_cursorIndexOfVehTaskId);
            }
            _item.setVehTaskId(_tmpVehTaskId);
            final String _tmpVehTaskName;
            if (_cursor.isNull(_cursorIndexOfVehTaskName)) {
              _tmpVehTaskName = null;
            } else {
              _tmpVehTaskName = _cursor.getString(_cursorIndexOfVehTaskName);
            }
            _item.setVehTaskName(_tmpVehTaskName);
            final Integer _tmpUserid;
            if (_cursor.isNull(_cursorIndexOfUserid)) {
              _tmpUserid = null;
            } else {
              _tmpUserid = _cursor.getInt(_cursorIndexOfUserid);
            }
            _item.setUserid(_tmpUserid);
            final Integer _tmpCustid;
            if (_cursor.isNull(_cursorIndexOfCustid)) {
              _tmpCustid = null;
            } else {
              _tmpCustid = _cursor.getInt(_cursorIndexOfCustid);
            }
            _item.setCustid(_tmpCustid);
            final Integer _tmpOrderNumber;
            if (_cursor.isNull(_cursorIndexOfOrderNumber)) {
              _tmpOrderNumber = null;
            } else {
              _tmpOrderNumber = _cursor.getInt(_cursorIndexOfOrderNumber);
            }
            _item.setOrderNumber(_tmpOrderNumber);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
