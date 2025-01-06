package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.DarChildEquipments;
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
public final class EqipmentChildDao_Impl implements EqipmentChildDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DarChildEquipments> __insertionAdapterOfDarChildEquipments;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public EqipmentChildDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDarChildEquipments = new EntityInsertionAdapter<DarChildEquipments>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `dar_equipment_child` (`child_id`,`equipment_id`,`userid`,`custid`,`items`,`order_number`,`isSelected`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DarChildEquipments value) {
        if (value.getChildId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getChildId());
        }
        if (value.getEquipmentId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getEquipmentId());
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
        if (value.getItems() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getItems());
        }
        if (value.getOrderNumber() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.getOrderNumber());
        }
        final int _tmp;
        _tmp = value.isSelected() ? 1 : 0;
        stmt.bindLong(7, _tmp);
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_equipment_child";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from dar_equipment_child where child_id=?";
        return _query;
      }
    };
  }

  @Override
  public void insertEquipmentChild(final DarChildEquipments... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarChildEquipments.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertEquipmentChild(final DarChildEquipments data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarChildEquipments.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertEquipmentChildList(final List<DarChildEquipments> equipmentList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDarChildEquipments.insert(equipmentList);
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
  public Maybe<List<DarChildEquipments>> getEquipmentChildByTitle(final int equipment_id) {
    final String _sql = "select * from dar_equipment_child where equipment_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, equipment_id);
    return Maybe.fromCallable(new Callable<List<DarChildEquipments>>() {
      @Override
      public List<DarChildEquipments> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfChildId = CursorUtil.getColumnIndexOrThrow(_cursor, "child_id");
          final int _cursorIndexOfEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment_id");
          final int _cursorIndexOfUserid = CursorUtil.getColumnIndexOrThrow(_cursor, "userid");
          final int _cursorIndexOfCustid = CursorUtil.getColumnIndexOrThrow(_cursor, "custid");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final int _cursorIndexOfOrderNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "order_number");
          final int _cursorIndexOfIsSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "isSelected");
          final List<DarChildEquipments> _result = new ArrayList<DarChildEquipments>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DarChildEquipments _item;
            _item = new DarChildEquipments();
            final Integer _tmpChildId;
            if (_cursor.isNull(_cursorIndexOfChildId)) {
              _tmpChildId = null;
            } else {
              _tmpChildId = _cursor.getInt(_cursorIndexOfChildId);
            }
            _item.setChildId(_tmpChildId);
            final Integer _tmpEquipmentId;
            if (_cursor.isNull(_cursorIndexOfEquipmentId)) {
              _tmpEquipmentId = null;
            } else {
              _tmpEquipmentId = _cursor.getInt(_cursorIndexOfEquipmentId);
            }
            _item.setEquipmentId(_tmpEquipmentId);
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
            final String _tmpItems;
            if (_cursor.isNull(_cursorIndexOfItems)) {
              _tmpItems = null;
            } else {
              _tmpItems = _cursor.getString(_cursorIndexOfItems);
            }
            _item.setItems(_tmpItems);
            final Integer _tmpOrderNumber;
            if (_cursor.isNull(_cursorIndexOfOrderNumber)) {
              _tmpOrderNumber = null;
            } else {
              _tmpOrderNumber = _cursor.getInt(_cursorIndexOfOrderNumber);
            }
            _item.setOrderNumber(_tmpOrderNumber);
            final boolean _tmpIsSelected;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSelected);
            _tmpIsSelected = _tmp != 0;
            _item.setSelected(_tmpIsSelected);
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
