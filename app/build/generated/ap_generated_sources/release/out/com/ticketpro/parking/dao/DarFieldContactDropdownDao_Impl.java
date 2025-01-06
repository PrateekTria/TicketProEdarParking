package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.DarFieldContactDropdown;
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
public final class DarFieldContactDropdownDao_Impl implements DarFieldContactDropdownDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DarFieldContactDropdown> __insertionAdapterOfDarFieldContactDropdown;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public DarFieldContactDropdownDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDarFieldContactDropdown = new EntityInsertionAdapter<DarFieldContactDropdown>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `dar_field_contact_dropdown` (`id`,`menu_name`,`userid`,`custid`,`is_active`,`order_number`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DarFieldContactDropdown value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getMenuName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMenuName());
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
        if (value.getOrderNumber() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.getOrderNumber());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_field_contact_dropdown";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_field_contact_dropdown where id=?";
        return _query;
      }
    };
  }

  @Override
  public void insertDarFieldContactDropdown(final DarFieldContactDropdown... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarFieldContactDropdown.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertDarFieldContactDropdown(final DarFieldContactDropdown data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarFieldContactDropdown.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertDarFieldContactDropdown(
      final List<DarFieldContactDropdown> darFieldContactDropdowns) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarFieldContactDropdown.insert(darFieldContactDropdowns);
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
  public Maybe<List<DarFieldContactDropdown>> getDarFieldContactDropdown(final int id) {
    final String _sql = "select * from dar_field_contact_dropdown where custid=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return Maybe.fromCallable(new Callable<List<DarFieldContactDropdown>>() {
      @Override
      public List<DarFieldContactDropdown> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMenuName = CursorUtil.getColumnIndexOrThrow(_cursor, "menu_name");
          final int _cursorIndexOfUserid = CursorUtil.getColumnIndexOrThrow(_cursor, "userid");
          final int _cursorIndexOfCustid = CursorUtil.getColumnIndexOrThrow(_cursor, "custid");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfOrderNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "order_number");
          final List<DarFieldContactDropdown> _result = new ArrayList<DarFieldContactDropdown>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DarFieldContactDropdown _item;
            _item = new DarFieldContactDropdown();
            final Integer _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpMenuName;
            if (_cursor.isNull(_cursorIndexOfMenuName)) {
              _tmpMenuName = null;
            } else {
              _tmpMenuName = _cursor.getString(_cursorIndexOfMenuName);
            }
            _item.setMenuName(_tmpMenuName);
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
