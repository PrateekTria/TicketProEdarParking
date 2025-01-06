package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.ServiceRequestModel;
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
public final class ServiceRequestModelDao_Impl implements ServiceRequestModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ServiceRequestModel> __insertionAdapterOfServiceRequestModel;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public ServiceRequestModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfServiceRequestModel = new EntityInsertionAdapter<ServiceRequestModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `service_request` (`id`,`task_id`,`location_id`,`userId`,`assignment_id`,`equipment_id`,`mileage_id`,`sub_equipment_id`,`vdr`,`task_date`,`dar_task_report_id`,`disinfecting`,`mileage`,`vehicle`,`serviceRequest_ddElemId`,`deviceid`,`image_extension`,`location`,`image_name`,`image_path`,`image_resolution`,`image_size`,`appId`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ServiceRequestModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getTaskId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTaskId());
        }
        if (value.getLocationId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLocationId());
        }
        if (value.getUserId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUserId());
        }
        if (value.getAssignmentId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getAssignmentId());
        }
        if (value.getEquipmentId() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getEquipmentId());
        }
        if (value.getMileageId() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getMileageId());
        }
        if (value.getSubEquipmentId() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getSubEquipmentId());
        }
        if (value.getVdr() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getVdr());
        }
        if (value.getTaskDate() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getTaskDate());
        }
        if (value.getDarTaskReportId() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getDarTaskReportId());
        }
        if (value.getDisinfecting() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getDisinfecting());
        }
        if (value.getMileage() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getMileage());
        }
        if (value.getVehicle() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getVehicle());
        }
        if (value.getServiceRequestDdElemId() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getServiceRequestDdElemId());
        }
        if (value.getDeviceid() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getDeviceid());
        }
        if (value.getImageExtension() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getImageExtension());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getLocation());
        }
        if (value.getImageName() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getImageName());
        }
        if (value.getImagePath() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getImagePath());
        }
        if (value.getImageResolution() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getImageResolution());
        }
        if (value.getImageSize() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getImageSize());
        }
        if (value.getAppId() == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindLong(23, value.getAppId());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(24);
        } else {
          stmt.bindString(24, value.getNotes());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from service_request";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from service_request where id=?";
        return _query;
      }
    };
  }

  @Override
  public void insertServiceRequestModel(final ServiceRequestModel... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfServiceRequestModel.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Completable insertServiceRequestModel(final ServiceRequestModel data) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfServiceRequestModel.insert(data);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public void insertServiceRequestModelList(
      final List<ServiceRequestModel> taskForm22500ModelList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfServiceRequestModel.insert(taskForm22500ModelList);
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
  public List<ServiceRequestModel> getServiceRequestModel() {
    final String _sql = "select * from service_request";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
      final int _cursorIndexOfLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "location_id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfAssignmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_id");
      final int _cursorIndexOfEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment_id");
      final int _cursorIndexOfMileageId = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage_id");
      final int _cursorIndexOfSubEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_equipment_id");
      final int _cursorIndexOfVdr = CursorUtil.getColumnIndexOrThrow(_cursor, "vdr");
      final int _cursorIndexOfTaskDate = CursorUtil.getColumnIndexOrThrow(_cursor, "task_date");
      final int _cursorIndexOfDarTaskReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "dar_task_report_id");
      final int _cursorIndexOfDisinfecting = CursorUtil.getColumnIndexOrThrow(_cursor, "disinfecting");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfVehicle = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicle");
      final int _cursorIndexOfServiceRequestDdElemId = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceRequest_ddElemId");
      final int _cursorIndexOfDeviceid = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceid");
      final int _cursorIndexOfImageExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "image_extension");
      final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
      final int _cursorIndexOfImageName = CursorUtil.getColumnIndexOrThrow(_cursor, "image_name");
      final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "image_path");
      final int _cursorIndexOfImageResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "image_resolution");
      final int _cursorIndexOfImageSize = CursorUtil.getColumnIndexOrThrow(_cursor, "image_size");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final List<ServiceRequestModel> _result = new ArrayList<ServiceRequestModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ServiceRequestModel _item;
        _item = new ServiceRequestModel();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTaskId;
        if (_cursor.isNull(_cursorIndexOfTaskId)) {
          _tmpTaskId = null;
        } else {
          _tmpTaskId = _cursor.getString(_cursorIndexOfTaskId);
        }
        _item.setTaskId(_tmpTaskId);
        final String _tmpLocationId;
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _tmpLocationId = null;
        } else {
          _tmpLocationId = _cursor.getString(_cursorIndexOfLocationId);
        }
        _item.setLocationId(_tmpLocationId);
        final String _tmpUserId;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUserId = null;
        } else {
          _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
        }
        _item.setUserId(_tmpUserId);
        final Integer _tmpAssignmentId;
        if (_cursor.isNull(_cursorIndexOfAssignmentId)) {
          _tmpAssignmentId = null;
        } else {
          _tmpAssignmentId = _cursor.getInt(_cursorIndexOfAssignmentId);
        }
        _item.setAssignmentId(_tmpAssignmentId);
        final String _tmpEquipmentId;
        if (_cursor.isNull(_cursorIndexOfEquipmentId)) {
          _tmpEquipmentId = null;
        } else {
          _tmpEquipmentId = _cursor.getString(_cursorIndexOfEquipmentId);
        }
        _item.setEquipmentId(_tmpEquipmentId);
        final String _tmpMileageId;
        if (_cursor.isNull(_cursorIndexOfMileageId)) {
          _tmpMileageId = null;
        } else {
          _tmpMileageId = _cursor.getString(_cursorIndexOfMileageId);
        }
        _item.setMileageId(_tmpMileageId);
        final String _tmpSubEquipmentId;
        if (_cursor.isNull(_cursorIndexOfSubEquipmentId)) {
          _tmpSubEquipmentId = null;
        } else {
          _tmpSubEquipmentId = _cursor.getString(_cursorIndexOfSubEquipmentId);
        }
        _item.setSubEquipmentId(_tmpSubEquipmentId);
        final String _tmpVdr;
        if (_cursor.isNull(_cursorIndexOfVdr)) {
          _tmpVdr = null;
        } else {
          _tmpVdr = _cursor.getString(_cursorIndexOfVdr);
        }
        _item.setVdr(_tmpVdr);
        final String _tmpTaskDate;
        if (_cursor.isNull(_cursorIndexOfTaskDate)) {
          _tmpTaskDate = null;
        } else {
          _tmpTaskDate = _cursor.getString(_cursorIndexOfTaskDate);
        }
        _item.setTaskDate(_tmpTaskDate);
        final String _tmpDarTaskReportId;
        if (_cursor.isNull(_cursorIndexOfDarTaskReportId)) {
          _tmpDarTaskReportId = null;
        } else {
          _tmpDarTaskReportId = _cursor.getString(_cursorIndexOfDarTaskReportId);
        }
        _item.setDarTaskReportId(_tmpDarTaskReportId);
        final String _tmpDisinfecting;
        if (_cursor.isNull(_cursorIndexOfDisinfecting)) {
          _tmpDisinfecting = null;
        } else {
          _tmpDisinfecting = _cursor.getString(_cursorIndexOfDisinfecting);
        }
        _item.setDisinfecting(_tmpDisinfecting);
        final String _tmpMileage;
        if (_cursor.isNull(_cursorIndexOfMileage)) {
          _tmpMileage = null;
        } else {
          _tmpMileage = _cursor.getString(_cursorIndexOfMileage);
        }
        _item.setMileage(_tmpMileage);
        final String _tmpVehicle;
        if (_cursor.isNull(_cursorIndexOfVehicle)) {
          _tmpVehicle = null;
        } else {
          _tmpVehicle = _cursor.getString(_cursorIndexOfVehicle);
        }
        _item.setVehicle(_tmpVehicle);
        final String _tmpServiceRequestDdElemId;
        if (_cursor.isNull(_cursorIndexOfServiceRequestDdElemId)) {
          _tmpServiceRequestDdElemId = null;
        } else {
          _tmpServiceRequestDdElemId = _cursor.getString(_cursorIndexOfServiceRequestDdElemId);
        }
        _item.setServiceRequestDdElemId(_tmpServiceRequestDdElemId);
        final String _tmpDeviceid;
        if (_cursor.isNull(_cursorIndexOfDeviceid)) {
          _tmpDeviceid = null;
        } else {
          _tmpDeviceid = _cursor.getString(_cursorIndexOfDeviceid);
        }
        _item.setDeviceid(_tmpDeviceid);
        final String _tmpImageExtension;
        if (_cursor.isNull(_cursorIndexOfImageExtension)) {
          _tmpImageExtension = null;
        } else {
          _tmpImageExtension = _cursor.getString(_cursorIndexOfImageExtension);
        }
        _item.setImageExtension(_tmpImageExtension);
        final String _tmpLocation;
        if (_cursor.isNull(_cursorIndexOfLocation)) {
          _tmpLocation = null;
        } else {
          _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        }
        _item.setLocation(_tmpLocation);
        final String _tmpImageName;
        if (_cursor.isNull(_cursorIndexOfImageName)) {
          _tmpImageName = null;
        } else {
          _tmpImageName = _cursor.getString(_cursorIndexOfImageName);
        }
        _item.setImageName(_tmpImageName);
        final String _tmpImagePath;
        if (_cursor.isNull(_cursorIndexOfImagePath)) {
          _tmpImagePath = null;
        } else {
          _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
        }
        _item.setImagePath(_tmpImagePath);
        final String _tmpImageResolution;
        if (_cursor.isNull(_cursorIndexOfImageResolution)) {
          _tmpImageResolution = null;
        } else {
          _tmpImageResolution = _cursor.getString(_cursorIndexOfImageResolution);
        }
        _item.setImageResolution(_tmpImageResolution);
        final String _tmpImageSize;
        if (_cursor.isNull(_cursorIndexOfImageSize)) {
          _tmpImageSize = null;
        } else {
          _tmpImageSize = _cursor.getString(_cursorIndexOfImageSize);
        }
        _item.setImageSize(_tmpImageSize);
        final Integer _tmpAppId;
        if (_cursor.isNull(_cursorIndexOfAppId)) {
          _tmpAppId = null;
        } else {
          _tmpAppId = _cursor.getInt(_cursorIndexOfAppId);
        }
        _item.setAppId(_tmpAppId);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
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
    final String _sql = "select max(id) as max_id from service_request";
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
