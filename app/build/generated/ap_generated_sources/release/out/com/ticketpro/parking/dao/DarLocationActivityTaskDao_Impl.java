package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.DarLocationActivityTask;
import io.reactivex.Maybe;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DarLocationActivityTaskDao_Impl implements DarLocationActivityTaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DarLocationActivityTask> __insertionAdapterOfDarLocationActivityTask;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public DarLocationActivityTaskDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDarLocationActivityTask = new EntityInsertionAdapter<DarLocationActivityTask>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `dar_location_task` (`id`,`id_assg`,`custid`,`userid`,`items`,`type`,`order_number`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DarLocationActivityTask value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdAssgLoc());
        stmt.bindLong(3, value.getCustId());
        stmt.bindLong(4, value.getUserid());
        if (value.getItems() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getItems());
        }
        if (value.getType() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getType());
        }
        stmt.bindLong(7, value.getOrderNumber());
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_location_task";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_location_task where id=?";
        return _query;
      }
    };
  }

  @Override
  public void insertDarLocationActivityTask(final DarLocationActivityTask... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarLocationActivityTask.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertEquipment(final DarLocationActivityTask data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarLocationActivityTask.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertDarLocationActivityTaskList(
      final List<DarLocationActivityTask> darLocationActivityTaskList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarLocationActivityTask.insert(darLocationActivityTaskList);
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
  public Maybe<List<DarLocationActivityTask>> getDarLocationActivityTask(final int id) {
    final String _sql = "select * from dar_location_task where id_assg=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return Maybe.fromCallable(new Callable<List<DarLocationActivityTask>>() {
      @Override
      public List<DarLocationActivityTask> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfIdAssgLoc = CursorUtil.getColumnIndexOrThrow(_cursor, "id_assg");
          final int _cursorIndexOfCustId = CursorUtil.getColumnIndexOrThrow(_cursor, "custid");
          final int _cursorIndexOfUserid = CursorUtil.getColumnIndexOrThrow(_cursor, "userid");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfOrderNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "order_number");
          final List<DarLocationActivityTask> _result = new ArrayList<DarLocationActivityTask>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DarLocationActivityTask _item;
            _item = new DarLocationActivityTask();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpIdAssgLoc;
            _tmpIdAssgLoc = _cursor.getInt(_cursorIndexOfIdAssgLoc);
            _item.setIdAssgLoc(_tmpIdAssgLoc);
            final int _tmpCustId;
            _tmpCustId = _cursor.getInt(_cursorIndexOfCustId);
            _item.setCustId(_tmpCustId);
            final int _tmpUserid;
            _tmpUserid = _cursor.getInt(_cursorIndexOfUserid);
            _item.setUserid(_tmpUserid);
            final String _tmpItems;
            if (_cursor.isNull(_cursorIndexOfItems)) {
              _tmpItems = null;
            } else {
              _tmpItems = _cursor.getString(_cursorIndexOfItems);
            }
            _item.setItems(_tmpItems);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            _item.setType(_tmpType);
            final int _tmpOrderNumber;
            _tmpOrderNumber = _cursor.getInt(_cursorIndexOfOrderNumber);
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
