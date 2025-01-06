package com.ticketpro.parking.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ticketpro.parking.dar.model.School;
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
public final class SchoolDao_Impl implements SchoolDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<School> __insertionAdapterOfSchool;

  private final SharedSQLiteStatement __preparedStmtOfRemoveAll;

  private final SharedSQLiteStatement __preparedStmtOfRemoveById;

  public SchoolDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSchool = new EntityInsertionAdapter<School>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `school` (`id`,`user_id`,`schooldd_id`,`school_type`,`school_district`,`council_district`,`dar_task_report_id`,`school_address`,`contacts_principal`,`school_name`,`contacts_staff`,`mileage_id`,`issues_concerns`,`common_violations`,`speed_display_sign_deployed`,`reason`,`number_of_citations_issued`,`number_of_warnings_issued`,`drop_off_times`,`pick_up_time`,`reason_for_no_school_visit`,`date_time`,`task_id`,`location_id`,`assignment_id`,`equipment_id`,`vdr`,`activity_id`,`mileage`,`vehicle`,`device_id`,`action_id`,`disinfecting`,`sub_equipment_id`,`appId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, School value) {
        stmt.bindLong(1, value.getId());
        if (value.getUserId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserId());
        }
        if (value.getSchoolddId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSchoolddId());
        }
        if (value.getSchoolType() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSchoolType());
        }
        if (value.getSchoolDistrict() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getSchoolDistrict());
        }
        if (value.getCouncilDistrict() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCouncilDistrict());
        }
        if (value.getDarTaskReportId() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDarTaskReportId());
        }
        if (value.getSchoolAddress() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getSchoolAddress());
        }
        if (value.getContactsPrincipal() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getContactsPrincipal());
        }
        if (value.getSchoolName() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getSchoolName());
        }
        if (value.getContactsStaff() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getContactsStaff());
        }
        if (value.getMileageId() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getMileageId());
        }
        if (value.getIssuesConcerns() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getIssuesConcerns());
        }
        if (value.getCommonViolations() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getCommonViolations());
        }
        if (value.getSpeedDisplaySignDeployed() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getSpeedDisplaySignDeployed());
        }
        if (value.getReason() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getReason());
        }
        if (value.getNumberOfCitationsIssued() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getNumberOfCitationsIssued());
        }
        if (value.getNumberOfWarningsIssued() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getNumberOfWarningsIssued());
        }
        if (value.getDropOffTimes() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getDropOffTimes());
        }
        if (value.getPickUpTime() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getPickUpTime());
        }
        if (value.getReasonForNoSchoolVisit() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getReasonForNoSchoolVisit());
        }
        if (value.getDateTime() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getDateTime());
        }
        if (value.getTaskId() == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindString(23, value.getTaskId());
        }
        if (value.getLocationId() == null) {
          stmt.bindNull(24);
        } else {
          stmt.bindString(24, value.getLocationId());
        }
        if (value.getAssignmentId() == null) {
          stmt.bindNull(25);
        } else {
          stmt.bindString(25, value.getAssignmentId());
        }
        if (value.getEquipmentId() == null) {
          stmt.bindNull(26);
        } else {
          stmt.bindString(26, value.getEquipmentId());
        }
        if (value.getVdr() == null) {
          stmt.bindNull(27);
        } else {
          stmt.bindString(27, value.getVdr());
        }
        if (value.getActivityId() == null) {
          stmt.bindNull(28);
        } else {
          stmt.bindString(28, value.getActivityId());
        }
        if (value.getMileage() == null) {
          stmt.bindNull(29);
        } else {
          stmt.bindString(29, value.getMileage());
        }
        if (value.getVehicle() == null) {
          stmt.bindNull(30);
        } else {
          stmt.bindString(30, value.getVehicle());
        }
        if (value.getDeviceid() == null) {
          stmt.bindNull(31);
        } else {
          stmt.bindString(31, value.getDeviceid());
        }
        if (value.getActionId() == null) {
          stmt.bindNull(32);
        } else {
          stmt.bindString(32, value.getActionId());
        }
        if (value.getDisinfecting() == null) {
          stmt.bindNull(33);
        } else {
          stmt.bindString(33, value.getDisinfecting());
        }
        if (value.getSubEquipmentId() == null) {
          stmt.bindNull(34);
        } else {
          stmt.bindString(34, value.getSubEquipmentId());
        }
        if (value.getAppId() == null) {
          stmt.bindNull(35);
        } else {
          stmt.bindLong(35, value.getAppId());
        }
      }
    };
    this.__preparedStmtOfRemoveAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from school";
        return _query;
      }
    };
    this.__preparedStmtOfRemoveById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from school where appId=?";
        return _query;
      }
    };
  }

  @Override
  public void insertSchool(final School... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSchool.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Completable insertSchool(final School data) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSchool.insert(data);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public void insertSchoolList(final List<School> taskForm22500ModelList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSchool.insert(taskForm22500ModelList);
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
  public List<School> getSchool() {
    final String _sql = "select * from school";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
      final int _cursorIndexOfSchoolddId = CursorUtil.getColumnIndexOrThrow(_cursor, "schooldd_id");
      final int _cursorIndexOfSchoolType = CursorUtil.getColumnIndexOrThrow(_cursor, "school_type");
      final int _cursorIndexOfSchoolDistrict = CursorUtil.getColumnIndexOrThrow(_cursor, "school_district");
      final int _cursorIndexOfCouncilDistrict = CursorUtil.getColumnIndexOrThrow(_cursor, "council_district");
      final int _cursorIndexOfDarTaskReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "dar_task_report_id");
      final int _cursorIndexOfSchoolAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "school_address");
      final int _cursorIndexOfContactsPrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "contacts_principal");
      final int _cursorIndexOfSchoolName = CursorUtil.getColumnIndexOrThrow(_cursor, "school_name");
      final int _cursorIndexOfContactsStaff = CursorUtil.getColumnIndexOrThrow(_cursor, "contacts_staff");
      final int _cursorIndexOfMileageId = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage_id");
      final int _cursorIndexOfIssuesConcerns = CursorUtil.getColumnIndexOrThrow(_cursor, "issues_concerns");
      final int _cursorIndexOfCommonViolations = CursorUtil.getColumnIndexOrThrow(_cursor, "common_violations");
      final int _cursorIndexOfSpeedDisplaySignDeployed = CursorUtil.getColumnIndexOrThrow(_cursor, "speed_display_sign_deployed");
      final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
      final int _cursorIndexOfNumberOfCitationsIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "number_of_citations_issued");
      final int _cursorIndexOfNumberOfWarningsIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "number_of_warnings_issued");
      final int _cursorIndexOfDropOffTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "drop_off_times");
      final int _cursorIndexOfPickUpTime = CursorUtil.getColumnIndexOrThrow(_cursor, "pick_up_time");
      final int _cursorIndexOfReasonForNoSchoolVisit = CursorUtil.getColumnIndexOrThrow(_cursor, "reason_for_no_school_visit");
      final int _cursorIndexOfDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "date_time");
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
      final int _cursorIndexOfLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "location_id");
      final int _cursorIndexOfAssignmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignment_id");
      final int _cursorIndexOfEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment_id");
      final int _cursorIndexOfVdr = CursorUtil.getColumnIndexOrThrow(_cursor, "vdr");
      final int _cursorIndexOfActivityId = CursorUtil.getColumnIndexOrThrow(_cursor, "activity_id");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfVehicle = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicle");
      final int _cursorIndexOfDeviceid = CursorUtil.getColumnIndexOrThrow(_cursor, "device_id");
      final int _cursorIndexOfActionId = CursorUtil.getColumnIndexOrThrow(_cursor, "action_id");
      final int _cursorIndexOfDisinfecting = CursorUtil.getColumnIndexOrThrow(_cursor, "disinfecting");
      final int _cursorIndexOfSubEquipmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_equipment_id");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final List<School> _result = new ArrayList<School>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final School _item;
        _item = new School();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpUserId;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUserId = null;
        } else {
          _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
        }
        _item.setUserId(_tmpUserId);
        final String _tmpSchoolddId;
        if (_cursor.isNull(_cursorIndexOfSchoolddId)) {
          _tmpSchoolddId = null;
        } else {
          _tmpSchoolddId = _cursor.getString(_cursorIndexOfSchoolddId);
        }
        _item.setSchoolddId(_tmpSchoolddId);
        final String _tmpSchoolType;
        if (_cursor.isNull(_cursorIndexOfSchoolType)) {
          _tmpSchoolType = null;
        } else {
          _tmpSchoolType = _cursor.getString(_cursorIndexOfSchoolType);
        }
        _item.setSchoolType(_tmpSchoolType);
        final String _tmpSchoolDistrict;
        if (_cursor.isNull(_cursorIndexOfSchoolDistrict)) {
          _tmpSchoolDistrict = null;
        } else {
          _tmpSchoolDistrict = _cursor.getString(_cursorIndexOfSchoolDistrict);
        }
        _item.setSchoolDistrict(_tmpSchoolDistrict);
        final String _tmpCouncilDistrict;
        if (_cursor.isNull(_cursorIndexOfCouncilDistrict)) {
          _tmpCouncilDistrict = null;
        } else {
          _tmpCouncilDistrict = _cursor.getString(_cursorIndexOfCouncilDistrict);
        }
        _item.setCouncilDistrict(_tmpCouncilDistrict);
        final String _tmpDarTaskReportId;
        if (_cursor.isNull(_cursorIndexOfDarTaskReportId)) {
          _tmpDarTaskReportId = null;
        } else {
          _tmpDarTaskReportId = _cursor.getString(_cursorIndexOfDarTaskReportId);
        }
        _item.setDarTaskReportId(_tmpDarTaskReportId);
        final String _tmpSchoolAddress;
        if (_cursor.isNull(_cursorIndexOfSchoolAddress)) {
          _tmpSchoolAddress = null;
        } else {
          _tmpSchoolAddress = _cursor.getString(_cursorIndexOfSchoolAddress);
        }
        _item.setSchoolAddress(_tmpSchoolAddress);
        final String _tmpContactsPrincipal;
        if (_cursor.isNull(_cursorIndexOfContactsPrincipal)) {
          _tmpContactsPrincipal = null;
        } else {
          _tmpContactsPrincipal = _cursor.getString(_cursorIndexOfContactsPrincipal);
        }
        _item.setContactsPrincipal(_tmpContactsPrincipal);
        final String _tmpSchoolName;
        if (_cursor.isNull(_cursorIndexOfSchoolName)) {
          _tmpSchoolName = null;
        } else {
          _tmpSchoolName = _cursor.getString(_cursorIndexOfSchoolName);
        }
        _item.setSchoolName(_tmpSchoolName);
        final String _tmpContactsStaff;
        if (_cursor.isNull(_cursorIndexOfContactsStaff)) {
          _tmpContactsStaff = null;
        } else {
          _tmpContactsStaff = _cursor.getString(_cursorIndexOfContactsStaff);
        }
        _item.setContactsStaff(_tmpContactsStaff);
        final String _tmpMileageId;
        if (_cursor.isNull(_cursorIndexOfMileageId)) {
          _tmpMileageId = null;
        } else {
          _tmpMileageId = _cursor.getString(_cursorIndexOfMileageId);
        }
        _item.setMileageId(_tmpMileageId);
        final String _tmpIssuesConcerns;
        if (_cursor.isNull(_cursorIndexOfIssuesConcerns)) {
          _tmpIssuesConcerns = null;
        } else {
          _tmpIssuesConcerns = _cursor.getString(_cursorIndexOfIssuesConcerns);
        }
        _item.setIssuesConcerns(_tmpIssuesConcerns);
        final String _tmpCommonViolations;
        if (_cursor.isNull(_cursorIndexOfCommonViolations)) {
          _tmpCommonViolations = null;
        } else {
          _tmpCommonViolations = _cursor.getString(_cursorIndexOfCommonViolations);
        }
        _item.setCommonViolations(_tmpCommonViolations);
        final String _tmpSpeedDisplaySignDeployed;
        if (_cursor.isNull(_cursorIndexOfSpeedDisplaySignDeployed)) {
          _tmpSpeedDisplaySignDeployed = null;
        } else {
          _tmpSpeedDisplaySignDeployed = _cursor.getString(_cursorIndexOfSpeedDisplaySignDeployed);
        }
        _item.setSpeedDisplaySignDeployed(_tmpSpeedDisplaySignDeployed);
        final String _tmpReason;
        if (_cursor.isNull(_cursorIndexOfReason)) {
          _tmpReason = null;
        } else {
          _tmpReason = _cursor.getString(_cursorIndexOfReason);
        }
        _item.setReason(_tmpReason);
        final String _tmpNumberOfCitationsIssued;
        if (_cursor.isNull(_cursorIndexOfNumberOfCitationsIssued)) {
          _tmpNumberOfCitationsIssued = null;
        } else {
          _tmpNumberOfCitationsIssued = _cursor.getString(_cursorIndexOfNumberOfCitationsIssued);
        }
        _item.setNumberOfCitationsIssued(_tmpNumberOfCitationsIssued);
        final String _tmpNumberOfWarningsIssued;
        if (_cursor.isNull(_cursorIndexOfNumberOfWarningsIssued)) {
          _tmpNumberOfWarningsIssued = null;
        } else {
          _tmpNumberOfWarningsIssued = _cursor.getString(_cursorIndexOfNumberOfWarningsIssued);
        }
        _item.setNumberOfWarningsIssued(_tmpNumberOfWarningsIssued);
        final String _tmpDropOffTimes;
        if (_cursor.isNull(_cursorIndexOfDropOffTimes)) {
          _tmpDropOffTimes = null;
        } else {
          _tmpDropOffTimes = _cursor.getString(_cursorIndexOfDropOffTimes);
        }
        _item.setDropOffTimes(_tmpDropOffTimes);
        final String _tmpPickUpTime;
        if (_cursor.isNull(_cursorIndexOfPickUpTime)) {
          _tmpPickUpTime = null;
        } else {
          _tmpPickUpTime = _cursor.getString(_cursorIndexOfPickUpTime);
        }
        _item.setPickUpTime(_tmpPickUpTime);
        final String _tmpReasonForNoSchoolVisit;
        if (_cursor.isNull(_cursorIndexOfReasonForNoSchoolVisit)) {
          _tmpReasonForNoSchoolVisit = null;
        } else {
          _tmpReasonForNoSchoolVisit = _cursor.getString(_cursorIndexOfReasonForNoSchoolVisit);
        }
        _item.setReasonForNoSchoolVisit(_tmpReasonForNoSchoolVisit);
        final String _tmpDateTime;
        if (_cursor.isNull(_cursorIndexOfDateTime)) {
          _tmpDateTime = null;
        } else {
          _tmpDateTime = _cursor.getString(_cursorIndexOfDateTime);
        }
        _item.setDateTime(_tmpDateTime);
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
        final String _tmpAssignmentId;
        if (_cursor.isNull(_cursorIndexOfAssignmentId)) {
          _tmpAssignmentId = null;
        } else {
          _tmpAssignmentId = _cursor.getString(_cursorIndexOfAssignmentId);
        }
        _item.setAssignmentId(_tmpAssignmentId);
        final String _tmpEquipmentId;
        if (_cursor.isNull(_cursorIndexOfEquipmentId)) {
          _tmpEquipmentId = null;
        } else {
          _tmpEquipmentId = _cursor.getString(_cursorIndexOfEquipmentId);
        }
        _item.setEquipmentId(_tmpEquipmentId);
        final String _tmpVdr;
        if (_cursor.isNull(_cursorIndexOfVdr)) {
          _tmpVdr = null;
        } else {
          _tmpVdr = _cursor.getString(_cursorIndexOfVdr);
        }
        _item.setVdr(_tmpVdr);
        final String _tmpActivityId;
        if (_cursor.isNull(_cursorIndexOfActivityId)) {
          _tmpActivityId = null;
        } else {
          _tmpActivityId = _cursor.getString(_cursorIndexOfActivityId);
        }
        _item.setActivityId(_tmpActivityId);
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
        final String _tmpDeviceid;
        if (_cursor.isNull(_cursorIndexOfDeviceid)) {
          _tmpDeviceid = null;
        } else {
          _tmpDeviceid = _cursor.getString(_cursorIndexOfDeviceid);
        }
        _item.setDeviceid(_tmpDeviceid);
        final String _tmpActionId;
        if (_cursor.isNull(_cursorIndexOfActionId)) {
          _tmpActionId = null;
        } else {
          _tmpActionId = _cursor.getString(_cursorIndexOfActionId);
        }
        _item.setActionId(_tmpActionId);
        final String _tmpDisinfecting;
        if (_cursor.isNull(_cursorIndexOfDisinfecting)) {
          _tmpDisinfecting = null;
        } else {
          _tmpDisinfecting = _cursor.getString(_cursorIndexOfDisinfecting);
        }
        _item.setDisinfecting(_tmpDisinfecting);
        final String _tmpSubEquipmentId;
        if (_cursor.isNull(_cursorIndexOfSubEquipmentId)) {
          _tmpSubEquipmentId = null;
        } else {
          _tmpSubEquipmentId = _cursor.getString(_cursorIndexOfSubEquipmentId);
        }
        _item.setSubEquipmentId(_tmpSubEquipmentId);
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
    final String _sql = "select max(id) as max_id from school";
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
