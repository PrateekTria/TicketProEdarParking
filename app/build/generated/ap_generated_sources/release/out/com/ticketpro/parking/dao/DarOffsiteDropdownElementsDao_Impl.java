package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.DarOffsiteDropdownElementsResult;
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
public final class DarOffsiteDropdownElementsDao_Impl implements DarOffsiteDropdownElementsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DarOffsiteDropdownElementsResult> __insertionAdapterOfDarOffsiteDropdownElementsResult;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public DarOffsiteDropdownElementsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDarOffsiteDropdownElementsResult = new EntityInsertionAdapter<DarOffsiteDropdownElementsResult>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `dar_OffSiteNonEnforcement_dropdown` (`dd_elementId`,`dd_elementName`,`userid`,`custid`,`is_active`,`activity_id`,`order_number`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DarOffsiteDropdownElementsResult value) {
        if (value.getDdElementId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getDdElementId());
        }
        if (value.getDdElementName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDdElementName());
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
        if (value.getIsActive() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getIsActive());
        }
        if (value.getActivityId() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.getActivityId());
        }
        if (value.getOrderNumber() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getOrderNumber());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_OffSiteNonEnforcement_dropdown";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_OffSiteNonEnforcement_dropdown where dd_elementId=?";
        return _query;
      }
    };
  }

  @Override
  public void insertDarOffsiteDropdownElements(final DarOffsiteDropdownElementsResult... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarOffsiteDropdownElementsResult.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertDarOffsiteDropdownElements(final DarOffsiteDropdownElementsResult data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarOffsiteDropdownElementsResult.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertDarOffsiteDropdownElements(
      final List<DarOffsiteDropdownElementsResult> darOffsiteDropdownElementsResultList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarOffsiteDropdownElementsResult.insert(darOffsiteDropdownElementsResultList);
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
  public Maybe<List<DarOffsiteDropdownElementsResult>> getDarOffsiteDropdownElements(
      final int custId) {
    final String _sql = "select * from dar_OffSiteNonEnforcement_dropdown where custid=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, custId);
    return Maybe.fromCallable(new Callable<List<DarOffsiteDropdownElementsResult>>() {
      @Override
      public List<DarOffsiteDropdownElementsResult> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDdElementId = CursorUtil.getColumnIndexOrThrow(_cursor, "dd_elementId");
          final int _cursorIndexOfDdElementName = CursorUtil.getColumnIndexOrThrow(_cursor, "dd_elementName");
          final int _cursorIndexOfUserid = CursorUtil.getColumnIndexOrThrow(_cursor, "userid");
          final int _cursorIndexOfCustid = CursorUtil.getColumnIndexOrThrow(_cursor, "custid");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfActivityId = CursorUtil.getColumnIndexOrThrow(_cursor, "activity_id");
          final int _cursorIndexOfOrderNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "order_number");
          final List<DarOffsiteDropdownElementsResult> _result = new ArrayList<DarOffsiteDropdownElementsResult>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DarOffsiteDropdownElementsResult _item;
            _item = new DarOffsiteDropdownElementsResult();
            final Integer _tmpDdElementId;
            if (_cursor.isNull(_cursorIndexOfDdElementId)) {
              _tmpDdElementId = null;
            } else {
              _tmpDdElementId = _cursor.getInt(_cursorIndexOfDdElementId);
            }
            _item.setDdElementId(_tmpDdElementId);
            final String _tmpDdElementName;
            if (_cursor.isNull(_cursorIndexOfDdElementName)) {
              _tmpDdElementName = null;
            } else {
              _tmpDdElementName = _cursor.getString(_cursorIndexOfDdElementName);
            }
            _item.setDdElementName(_tmpDdElementName);
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
            final Integer _tmpIsActive;
            if (_cursor.isNull(_cursorIndexOfIsActive)) {
              _tmpIsActive = null;
            } else {
              _tmpIsActive = _cursor.getInt(_cursorIndexOfIsActive);
            }
            _item.setIsActive(_tmpIsActive);
            final Integer _tmpActivityId;
            if (_cursor.isNull(_cursorIndexOfActivityId)) {
              _tmpActivityId = null;
            } else {
              _tmpActivityId = _cursor.getInt(_cursorIndexOfActivityId);
            }
            _item.setActivityId(_tmpActivityId);
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
