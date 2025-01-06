package com.ticketpro.parking.dao;

import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.TaskAndAction;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskAndActionDao_Impl implements TaskAndActionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TaskAndAction> __insertionAdapterOfTaskAndAction;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public TaskAndActionDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTaskAndAction = new EntityInsertionAdapter<TaskAndAction>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `dar_task_action` (`action_id`,`activity_id`,`id_assg_loc`,`custid`,`userid`,`action_name`,`order_number`,`is_active`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskAndAction value) {
        stmt.bindLong(1, value.getActionId());
        stmt.bindLong(2, value.getActivityId());
        stmt.bindLong(3, value.getIdAssgLoc());
        stmt.bindLong(4, value.getCustId());
        stmt.bindLong(5, value.getUserid());
        if (value.getActionName() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getActionName());
        }
        stmt.bindLong(7, value.getOrderNumber());
        if (value.getIsActive() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getIsActive());
        }
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
  public void insertTaskAndAction(final TaskAndAction... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTaskAndAction.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertTaskAndAction(final TaskAndAction data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTaskAndAction.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertTaskAndActionList(final List<TaskAndAction> taskAndActionList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTaskAndAction.insert(taskAndActionList);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
