package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.Assignments;
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
public final class AssignmentsDao_Impl implements AssignmentsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Assignments> __insertionAdapterOfAssignments;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public AssignmentsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAssignments = new EntityInsertionAdapter<Assignments>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `dar_assignment` (`id_asg`,`custid`,`userid`,`items`,`order_number`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Assignments value) {
        stmt.bindLong(1, value.getIdAsg());
        stmt.bindLong(2, value.getCustId());
        stmt.bindLong(3, value.getUserid());
        if (value.getItems() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getItems());
        }
        stmt.bindLong(5, value.getOrderNumber());
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_assignment";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_assignment where id_asg=?";
        return _query;
      }
    };
  }

  @Override
  public void insertAssignments(final Assignments... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAssignments.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAssignments(final Assignments data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAssignments.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAssignmentsList(final List<Assignments> assignmentsList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAssignments.insert(assignmentsList);
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
  public Maybe<List<Assignments>> getAssignment(final int custId) {
    final String _sql = "select * from dar_assignment where custid=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, custId);
    return Maybe.fromCallable(new Callable<List<Assignments>>() {
      @Override
      public List<Assignments> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdAsg = CursorUtil.getColumnIndexOrThrow(_cursor, "id_asg");
          final int _cursorIndexOfCustId = CursorUtil.getColumnIndexOrThrow(_cursor, "custid");
          final int _cursorIndexOfUserid = CursorUtil.getColumnIndexOrThrow(_cursor, "userid");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final int _cursorIndexOfOrderNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "order_number");
          final List<Assignments> _result = new ArrayList<Assignments>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Assignments _item;
            _item = new Assignments();
            final int _tmpIdAsg;
            _tmpIdAsg = _cursor.getInt(_cursorIndexOfIdAsg);
            _item.setIdAsg(_tmpIdAsg);
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
