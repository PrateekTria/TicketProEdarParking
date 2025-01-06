package com.ticketpro.parking.dao;

import android.content.Context;
import android.database.SQLException;
import android.os.Handler;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ticketpro.model.ALPRChalk;
import com.ticketpro.model.Body;
import com.ticketpro.model.CallInList;
import com.ticketpro.model.CallInReport;
import com.ticketpro.model.ChalkComment;
import com.ticketpro.model.ChalkPicture;
import com.ticketpro.model.ChalkVehicle;
import com.ticketpro.model.Color;
import com.ticketpro.model.Comment;
import com.ticketpro.model.CommentGroup;
import com.ticketpro.model.CommentGroupComment;
import com.ticketpro.model.Contact;
import com.ticketpro.model.CustomerInfo;
import com.ticketpro.model.CustomerModule;
import com.ticketpro.model.DeviceData;
import com.ticketpro.model.DeviceGroup;
import com.ticketpro.model.DeviceInfo;
import com.ticketpro.model.Direction;
import com.ticketpro.model.Duration;
import com.ticketpro.model.Duty;
import com.ticketpro.model.DutyReport;
import com.ticketpro.model.ErrorLog;
import com.ticketpro.model.EulaModel;
import com.ticketpro.model.Feature;
import com.ticketpro.model.GPSLocation;
import com.ticketpro.model.Hotlist;
import com.ticketpro.model.LPRNotify;
import com.ticketpro.model.Location;
import com.ticketpro.model.LocationGroup;
import com.ticketpro.model.LocationGroupLocation;
import com.ticketpro.model.LprBodyMap;
import com.ticketpro.model.MaintenanceLog;
import com.ticketpro.model.MaintenancePicture;
import com.ticketpro.model.MakeAndModel;
import com.ticketpro.model.Message;
import com.ticketpro.model.Meter;
import com.ticketpro.model.MobileNowLog;
import com.ticketpro.model.Module;
import com.ticketpro.model.Permit;
import com.ticketpro.model.Placard;
import com.ticketpro.model.PrintMacro;
import com.ticketpro.model.PrintTemplate;
import com.ticketpro.model.RepeatOffender;
import com.ticketpro.model.SpecialActivitiesLocation;
import com.ticketpro.model.SpecialActivity;
import com.ticketpro.model.SpecialActivityDisposition;
import com.ticketpro.model.SpecialActivityPicture;
import com.ticketpro.model.SpecialActivityReport;
import com.ticketpro.model.State;
import com.ticketpro.model.StreetPrefix;
import com.ticketpro.model.StreetSuffix;
import com.ticketpro.model.SyncData;
import com.ticketpro.model.Ticket;
import com.ticketpro.model.TicketComment;
import com.ticketpro.model.TicketCommentTemp;
import com.ticketpro.model.TicketHistory;
import com.ticketpro.model.TicketPicture;
import com.ticketpro.model.TicketPictureTemp;
import com.ticketpro.model.TicketTemp;
import com.ticketpro.model.TicketViolation;
import com.ticketpro.model.TicketViolationTemp;
import com.ticketpro.model.User;
import com.ticketpro.model.UserSetting;
import com.ticketpro.model.Vendor;
import com.ticketpro.model.VendorItem;
import com.ticketpro.model.VendorService;
import com.ticketpro.model.VendorServiceRegistration;
import com.ticketpro.model.Violation;
import com.ticketpro.model.ViolationGroup;
import com.ticketpro.model.ViolationGroupViolation;
import com.ticketpro.model.VoidTicketReason;
import com.ticketpro.model.Zone;
import com.ticketpro.parking.dar.model.Admin;
import com.ticketpro.parking.dar.model.AssignmentLocationReportModel;
import com.ticketpro.parking.dar.model.AssignmentReportModel;
import com.ticketpro.parking.dar.model.Assignments;
import com.ticketpro.parking.dar.model.CommunityModel;
import com.ticketpro.parking.dar.model.Dar22500DisposionDropDownElement;
import com.ticketpro.parking.dar.model.DarAdminDropdown;
import com.ticketpro.parking.dar.model.DarAssignmentLocation;
import com.ticketpro.parking.dar.model.DarAuthorityResponse;
import com.ticketpro.parking.dar.model.DarBreakAndLunchDropDown;
import com.ticketpro.parking.dar.model.DarChildEquipments;
import com.ticketpro.parking.dar.model.DarDisinfectingElements;
import com.ticketpro.parking.dar.model.DarFieldContactDropdown;
import com.ticketpro.parking.dar.model.DarLocationActivityTask;
import com.ticketpro.parking.dar.model.DarOffStreetPatrolDropDown;
import com.ticketpro.parking.dar.model.DarOffsiteDistrictDropdown;
import com.ticketpro.parking.dar.model.DarOffsiteDropdownElementsResult;
import com.ticketpro.parking.dar.model.DarOffsiteTrainerDropdown;
import com.ticketpro.parking.dar.model.DarPPZDropdown;
import com.ticketpro.parking.dar.model.DarSchoolDropDownElement;
import com.ticketpro.parking.dar.model.DarSeniorDutiesElements;
import com.ticketpro.parking.dar.model.DarServiceRequestDropDown;
import com.ticketpro.parking.dar.model.DarSignCheck;
import com.ticketpro.parking.dar.model.DarTowCompanyDrop;
import com.ticketpro.parking.dar.model.DarTowReasonDropDown;
import com.ticketpro.parking.dar.model.DarVdrElements;
import com.ticketpro.parking.dar.model.DarVehicleList;
import com.ticketpro.parking.dar.model.DarVehicleTask;
import com.ticketpro.parking.dar.model.Equipments;
import com.ticketpro.parking.dar.model.FieldContactDetails;
import com.ticketpro.parking.dar.model.FlayerModel;
import com.ticketpro.parking.dar.model.LunchBreakModel;
import com.ticketpro.parking.dar.model.Mileage;
import com.ticketpro.parking.dar.model.OffStreetModel;
import com.ticketpro.parking.dar.model.PPZModel;
import com.ticketpro.parking.dar.model.RideAlongModel;
import com.ticketpro.parking.dar.model.School;
import com.ticketpro.parking.dar.model.SeniorDutiesModel;
import com.ticketpro.parking.dar.model.ServiceRequestModel;
import com.ticketpro.parking.dar.model.SignCheckModel;
import com.ticketpro.parking.dar.model.TaskAndAction;
import com.ticketpro.parking.dar.model.TaskForm22500Model;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.TowModel;
import com.ticketpro.parking.dar.model.TrafficDetails;
import com.ticketpro.parking.dar.model.TrainingModel;
import com.ticketpro.parking.dar.model.VehMaintenanceModel;
import com.ticketpro.util.Converters;
import com.ticketpro.util.TPUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Rohit on 21-07-2020.
 */

@Database(entities = {ALPRChalk.class, DeviceData.class, Body.class, LprBodyMap.class, CallInList.class,
        CallInReport.class, ChalkComment.class, ChalkPicture.class, ChalkVehicle.class,
        Color.class, CommentGroupComment.class, CommentGroup.class, Comment.class,
        Contact.class, CustomerModule.class, CustomerInfo.class, DeviceGroup.class,
        DeviceInfo.class, Direction.class, Duration.class, Duty.class, DutyReport.class,
        ErrorLog.class, Feature.class, GPSLocation.class, Hotlist.class,
        LocationGroupLocation.class, LocationGroup.class, Location.class, LPRNotify.class,
        MaintenanceLog.class, MaintenancePicture.class, MakeAndModel.class, Message.class,
        Meter.class, MobileNowLog.class, Module.class, Permit.class, PrintMacro.class,
        PrintTemplate.class, RepeatOffender.class, SpecialActivity.class,
        SpecialActivitiesLocation.class, SpecialActivityPicture.class,
        SpecialActivityDisposition.class, SpecialActivityReport.class, State.class,
        StreetPrefix.class, StreetSuffix.class, SyncData.class, TicketComment.class,
        TicketPicture.class, TicketHistory.class, TicketViolation.class, Ticket.class,
        EulaModel.class, UserSetting.class, User.class, Vendor.class, VendorService.class,
        VendorServiceRegistration.class, VendorItem.class, ViolationGroupViolation.class,
        ViolationGroup.class, Violation.class, VoidTicketReason.class, Zone.class, Placard.class, Equipments.class,
        Assignments.class, DarAssignmentLocation.class, DarLocationActivityTask.class, TaskAndAction.class,
        DarVehicleTask.class, DarSeniorDutiesElements.class, DarOffsiteDropdownElementsResult.class,
        DarChildEquipments.class, Dar22500DisposionDropDownElement.class, DarVehicleList.class,
        DarFieldContactDropdown.class, DarAdminDropdown.class, DarVdrElements.class, DarDisinfectingElements.class,
        DarSchoolDropDownElement.class, DarBreakAndLunchDropDown.class, DarSignCheck.class, DarPPZDropdown.class,
        DarServiceRequestDropDown.class, DarOffStreetPatrolDropDown.class, DarOffsiteTrainerDropdown.class,
        DarOffsiteDistrictDropdown.class, TaskForm22500Model.class, FieldContactDetails.class, LunchBreakModel.class,
        Admin.class, SignCheckModel.class, PPZModel.class, OffStreetModel.class, ServiceRequestModel.class,
        TrafficDetails.class, CommunityModel.class, RideAlongModel.class, TrainingModel.class,
        FlayerModel.class, SeniorDutiesModel.class, DarTowCompanyDrop.class, DarTowReasonDropDown.class,
        School.class, Mileage.class, VehMaintenanceModel.class, AssignmentReportModel.class, AssignmentLocationReportModel.class, TaskReportModel.class,
        TowModel.class, DarAuthorityResponse.class,
        TicketTemp.class, TicketViolationTemp.class, TicketPictureTemp.class, TicketCommentTemp.class,


},
        version = 10, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ParkingDatabase extends RoomDatabase {
    // Migration from 1 to 2, Room 4.3.59
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE senior_duties ADD COLUMN mileage_id TEXT ");
                database.execSQL(
                        "ALTER TABLE senior_duties ADD COLUMN dar_task_report_id TEXT ");

                database.execSQL(
                        "ALTER TABLE flayer ADD COLUMN mileage_id TEXT ");
                database.execSQL(
                        "ALTER TABLE flayer ADD COLUMN dar_task_report_id TEXT ");

                database.execSQL(
                        "ALTER TABLE ride_along ADD COLUMN mileage_id TEXT ");
                database.execSQL(
                        "ALTER TABLE ride_along ADD COLUMN dar_task_report_id TEXT ");

                database.execSQL(
                        "ALTER TABLE community_meeting ADD COLUMN mileage_id TEXT ");
                database.execSQL(
                        "ALTER TABLE community_meeting ADD COLUMN dar_task_report_id TEXT ");

                database.execSQL(
                        "ALTER TABLE training ADD COLUMN mileage_id TEXT ");
                database.execSQL(
                        "ALTER TABLE training ADD COLUMN dar_task_report_id TEXT ");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    // Migration from 15 to 16, Room 2.1.0
    static final Migration MIGRATION_15_16 = new Migration(15, 16) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE FT_DeviceHistory ADD COLUMN accuracy REAL NOT NULL DEFAULT 0");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    // Migration from 16 to 17
    static final Migration MIGRATION_16_17 = new Migration(16, 17) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE chalk_pictures ADD COLUMN download_image TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL("ALTER TABLE chalk_vehicles ADD COLUMN sync_status TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL("ALTER TABLE tickets ADD COLUMN ticket_date_new TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL("ALTER TABLE field_contact ADD COLUMN task_date TEXT ");
                database.execSQL("ALTER TABLE admin ADD COLUMN task_date TEXT ");
                database.execSQL("ALTER TABLE service_request ADD COLUMN task_date TEXT ");
                database.execSQL("ALTER TABLE form_22500 ADD COLUMN task_date TEXT ");
                database.execSQL("ALTER TABLE sign_check ADD COLUMN task_date TEXT ");
                database.execSQL("ALTER TABLE veh_maintenance ADD COLUMN task_date TEXT ");
                database.execSQL("ALTER TABLE off_street ADD COLUMN task_date TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL("ALTER TABLE field_contact ADD COLUMN device_id TEXT ");
                database.execSQL("ALTER TABLE form_22500 ADD COLUMN device_id TEXT ");
                database.execSQL("ALTER TABLE school RENAME COLUMN userId TO user_id ");
                database.execSQL("ALTER TABLE break_lunch RENAME COLUMN deviceid TO device_id ");
                database.execSQL("ALTER TABLE school RENAME COLUMN deviceid TO device_id ");
                database.execSQL("ALTER TABLE sign_check RENAME COLUMN deviceid TO device_id ");
                database.execSQL("ALTER TABLE ppz ADD COLUMN device_id TEXT ");
                database.execSQL("ALTER TABLE admin ADD COLUMN device_id TEXT ");
                database.execSQL("ALTER TABLE off_street RENAME COLUMN deviceid TO device_id ");
                database.execSQL("ALTER TABLE veh_maintenance RENAME COLUMN deviceid TO device_id ");
                database.execSQL("ALTER TABLE ride_along RENAME COLUMN deviceid TO device_id ");


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    static final Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL("ALTER TABLE admin ADD COLUMN location TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    static final Migration MIGRATION_8_9 = new Migration(8, 9) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                // Add SQL statement to create a new temporary table
                database.execSQL("CREATE TABLE IF NOT EXISTS duty_reports_temp (report_id INTEGER PRIMARY KEY AUTOINCREMENT, userid INTEGER, custid INTEGER, duty_id INTEGER, date_in INTEGER, date_out INTEGER, signature TEXT, dutyTitle TEXT, device_id INTEGER, duty_report_id TEXT, sync_status TEXT)");

                // Copy data from old table to temporary table
                database.execSQL("INSERT INTO duty_reports_temp (report_id, userid, custid, duty_id, date_in, date_out, signature, dutyTitle, device_id, duty_report_id, sync_status) SELECT report_id, userid, custid, duty_id, date_in, date_out, signature, dutyTitle, device_id, duty_report_id, sync_status FROM duty_reports");

                // Drop the old table
                database.execSQL("DROP TABLE duty_reports");

                // Rename the temporary table to the original table name
                database.execSQL("ALTER TABLE duty_reports_temp RENAME TO duty_reports");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    static final Migration MIGRATION_9_10 = new Migration(9, 10) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                // Add SQL statement to create a new temporary table
                database.execSQL("ALTER TABLE duty_reports ADD COLUMN date_string TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };


    // Migration from 3 to 4, Room 2.1.0
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {

                database.execSQL(
                        "ALTER TABLE task_report RENAME COLUMN userid TO userId ");
                database.execSQL(
                        "ALTER TABLE task_report ADD COLUMN mileage_id TEXT ");
                database.execSQL(
                        "ALTER TABLE task_report ADD COLUMN location_id TEXT ");
                database.execSQL(
                        "ALTER TABLE task_report ADD COLUMN assignment_id INTEGER ");
                database.execSQL(
                        "ALTER TABLE task_report ADD COLUMN mileage TEXT ");
                database.execSQL(
                        "ALTER TABLE task_report ADD COLUMN action_id TEXT ");
                database.execSQL(
                        "ALTER TABLE task_report ADD COLUMN disinfecting TEXT ");
                database.execSQL(
                        "ALTER TABLE task_report ADD COLUMN equipment_id TEXT ");
                database.execSQL(
                        "ALTER TABLE task_report ADD COLUMN sub_equipment_id TEXT ");
                database.execSQL(
                        "ALTER TABLE task_report ADD COLUMN vdr TEXT ");


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    // Migration from 4 to 5, Room 2.1.0
 /*   static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE tickets ADD COLUMN duty_report_id TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                database.execSQL(
                        "ALTER TABLE duty_reports ADD COLUMN sync_status TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                database.execSQL(
                        "ALTER TABLE duty_reports ADD COLUMN duty_report_id TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };*/

  /*  static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE tickets ADD COLUMN app_version TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };*/

 /*   static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE special_activity_reports ADD COLUMN ticket_count TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };*/

   /* static final Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE error_logs ADD COLUMN module TEXT ");
                database.execSQL(
                        "ALTER TABLE error_logs ADD COLUMN app_version TEXT ");
                database.execSQL(
                        "ALTER TABLE error_logs ADD COLUMN device TEXT ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };*/


  /*  static final Migration MIGRATION_8_9 = new Migration(8, 9) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE users ADD COLUMN rmsid TEXT ");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };*/

/*    static final Migration MIGRATION_9_10 = new Migration(9, 10) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE mobile_now_logs ADD COLUMN latitude TEXT ");
                database.execSQL(
                        "ALTER TABLE mobile_now_logs ADD COLUMN longitude TEXT ");
                database.execSQL(
                        "ALTER TABLE mobile_now_logs ADD COLUMN location TEXT ");
                database.execSQL(
                        "ALTER TABLE mobile_now_logs ADD COLUMN duty_id TEXT ");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };*/


    static final Migration MIGRATION_10_11 = new Migration(10, 11) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS lprbodymap (body_id INTEGER NOT NULL DEFAULT 0, " +
                        "custid INTEGER NOT NULL DEFAULT 0,LPRBody TEXT, TicketBody TEXT, order_number INTEGER NOT NULL DEFAULT 0," +
                        " PRIMARY KEY(body_id))");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    static final Migration MIGRATION_11_12 = new Migration(11, 12) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE vendor_items ADD COLUMN order_number INTEGER NOT NULL DEFAULT 0");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    static final Migration MIGRATION_13_14 = new Migration(13, 14) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE FT_DeviceHistory ADD COLUMN altitude REAL NOT NULL DEFAULT 0");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    /*static final Migration MIGRATION_12_13 = new Migration(12, 13) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "CREATE TABLE IF NOT EXISTS \"temp_chalk_pictures\" (\n" +
                                "\t\"picture_id\"\tINTEGER NOT NULL DEFAULT 0,\n" +
                                "\t\"chalk_id\"\tINTEGER NOT NULL DEFAULT 0,\n" +
                                "\t\"chalk_time\"\tINTEGER,\n" +
                                "\t\"location\"\tTEXT,\n" +
                                "\t\"latitute\"\tTEXT,\n" +
                                "\t\"longitude\"\tTEXT,\n" +
                                "\t\"image_path\"\tTEXT,\n" +
                                "\t\"image_resolution\"\tTEXT,\n" +
                                "\t\"image_size\"\tTEXT,\n" +
                                "\t\"sync_status\"\tTEXT,\n" +
                                "\t\"custid\"\tINTEGER NOT NULL DEFAULT 0,\n" +
                                "\tPRIMARY KEY(\"picture_id\" AUTOINCREMENT)\n" +
                                ");");
                database.execSQL("INSERT INTO `temp_chalk_pictures` SELECT * FROM `chalk_pictures`");
                database.execSQL("ALTER TABLE `chalk_pictures` RENAME TO `original_chalk_pictures`");
                database.execSQL("ALTER TABLE `temp_chalk_pictures` RENAME TO `chalk_pictures`");
                database.execSQL("DROP TABLE IF EXISTS `original_chalk_pictures`");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };*/

    private static final String DB_NAME = "ticketpronew.db";
    public static ParkingDatabase instance;
    private static String DATABASE_PATH = "";

    public static ParkingDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (ParkingDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), ParkingDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2)
                            .addMigrations(MIGRATION_2_3)
                            .addMigrations(MIGRATION_3_4)
                            .addMigrations(MIGRATION_4_5)
                            .addMigrations(MIGRATION_5_6)
                            .addMigrations(MIGRATION_6_7)
                            .addMigrations(MIGRATION_7_8)
                            .addMigrations(MIGRATION_8_9)
                            .addMigrations(MIGRATION_9_10)
                            /*    //.addMigrations(MIGRATION_1_2)
                                .addMigrations(MIGRATION_3_4)
                                .addMigrations(MIGRATION_4_5)
                                .addMigrations(MIGRATION_5_6)
                                .addMigrations(MIGRATION_6_7)
                                .addMigrations(MIGRATION_7_8)
                                .addMigrations(MIGRATION_8_9)
                                .addMigrations(MIGRATION_9_10)
                                .addMigrations(MIGRATION_10_11)
                                .addMigrations(MIGRATION_11_12)
                                .addMigrations(MIGRATION_12_13)
                                .addMigrations(MIGRATION_13_14)
                                .addMigrations(MIGRATION_14_15)
                                .addMigrations(MIGRATION_15_16)*/
                            .build();
                }
            }
        }
        return instance;
    }

    static void destroyInstance() {
        if (instance.isOpen()) {
            instance.close();
        }
        instance = null;
    }

    public static void backupDatabase(final Context context) {
        if (instance != null) {
            destroyInstance();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //DATABASE_PATH = context.getDatabasePath(DB_NAME).getAbsolutePath();
                DATABASE_PATH = "/data/data/com.ticketpro.parking/databases/" + DB_NAME;
                try {
                    if (DATABASE_PATH == null) {
                        DATABASE_PATH = context.getDatabasePath(DB_NAME).getAbsolutePath();
                    }

                    File dbFile = new File(DATABASE_PATH);
                    FileInputStream fis;

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
                    String dateStr = sdf.format(cal.getTime());

                    fis = new FileInputStream(dbFile);
                    String outFileName = TPUtility.getBackupFolder() + "ticketPRO_" + dateStr + ".sqlite";
                    OutputStream output = new FileOutputStream(outFileName);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }

                    output.flush();
                    output.close();
                    fis.close();

                    //log.info("Done database backup..." + outFileName);

                } catch (IOException e) {
                    //log.error(TPUtility.getPrintStackTrace(e));
                }

            }
        }, 1500);
    }

    //Uses for Dar module {Start}
    public abstract EquipmentDao equipmentDao();

    public abstract EqipmentChildDao equipmentChaildDao();

    public abstract DarAssignmentLocationDao darAssignmentLocationDao();

    public abstract AssignmentsDao assignmentsDao();

    public abstract DarLocationActivityTaskDao darLocationActivityTaskDao();

    public abstract TaskAndActionDao taskAndActionDao();

    public abstract DarVehicleTaskDao darVehicleTaskDao();

    public abstract DarSeniorDutiesElementsDao darSeniorDutiesElementsDao();

    public abstract DarOffsiteDropdownElementsDao darOffsiteDropdownElementsDao();

    public abstract Dar22500DisposionDropDownElementDao dar22500DisposionDropDownElementDao();

    public abstract DarVehicleListDao darVehicleListDao();

    public abstract DarFieldContactDropdownDao darFieldContactDropdownDao();

    public abstract DarAdminDropdownDao darAdminDropdownDao();

    public abstract DarVdrElementsDao darVdrElementsDao();

    public abstract DarDisinfectingElementsDao darDisinfectingElementsDao();

    public abstract DarSchoolDropDownElementDao darSchoolDropDownElementDao();

    public abstract DarBreakAndLunchDropDownDao darBreakAndLunchDropDownDao();

    public abstract DarSignCheckDao darSignCheckDao();

    public abstract DarPPZDropdownDao darPPZDropdownDao();

    public abstract DarAuthorityDao darAuthorityDao();

    public abstract DarServiceRequestDropDownDao darServiceRequestDropDownDao();

    public abstract DarOffStreetPatrolDropDownDao darOffStreetPatrolDropDownDao();

    public abstract DarOffsiteTrainerDropdownDao darOffsiteTrainerDropdownDao();

    public abstract DarOffsiteDistrictDropdownDao darOffsiteDistrictDropdownDao();

    public abstract TaskForm22500ModelDao taskForm22500ModelDao();

    public abstract TowModelDao towModelDao();

    public abstract FieldContactDetailsDao fieldContactDetailsDao();

    public abstract LunchBreakModelDao lunchBreakModelDao();

    public abstract AdminDao adminDao();

    public abstract SignCheckModelDAo signCheckModelDAo();

    public abstract PPZModelDao ppzModelDao();

    public abstract OffStreetModelDao offStreetModelDao();

    public abstract VehMaintenanceDao vehMaintenanceDao();


    public abstract ServiceRequestModelDao serviceRequestModelDao();

    public abstract TrafficDetailsDao trafficDetailsDao();

    public abstract CommunityModelDao communityModelDao();

    public abstract RideAlongModelDao rideAlongModelDao();

    public abstract TrainingModelDao trainingModelDao();

    public abstract FlayerModelDao flayerModelDao();

    public abstract SeniorDutiesModelDao seniorDutiesModelDao();

    public abstract DarTowCompanyDropDao darTowCompanyDropDao();

    public abstract DarTowReasonDropDownDao darTowReasonDropDownDao();

    public abstract SchoolDao schoolDao();

    public abstract MileageDao mileageDao();

    public abstract AssignmentReportDao assignmentReportDao();

    public abstract AssignmentLocationReportDao assignmentLocationReportDao();

    public abstract TaskReportModelDao taskReportModelDao();


    //{End}

    public abstract BodyDao bodyDao();

    public abstract ALPRPhotoChalkDao alprPhotoChalkDao();

    // public abstract BodyDao bodyDao();

    public abstract PlrBodyDao lprbodyDao();

    public abstract CallInListDao callInListDao();

    public abstract CallInListReportDao callInListReportDao();

    public abstract ChalkCommentsDao chalkCommentsDao();

    public abstract ChalkPicturesDao chalkPicturesDao();

    public abstract ChalkVehiclesDao chalkVehiclesDao();

    public abstract ColorsDao colorsDao();

    public abstract CommentgroupCommentsDao commentgroupCommentsDao();

    public abstract CommentgroupsDao commentgroupsDao();

    public abstract CommentsDao commentsDao();

    public abstract ContactsDao contactsDao();

    public abstract CustomerModulesDao customerModulesDao();

    public abstract CustomersDao customersDao();

    public abstract DeviceGroupsDao deviceGroupsDao();

    public abstract DevicesDao devicesDao();

    public abstract DirectionsDao directionsDao();

    public abstract DurationDao durationDao();

    public abstract DutiesDao dutiesDao();

    public abstract DutyReportsDao dutyReportsDao();

    public abstract ErrorLogsDao errorLogsDao();

    public abstract FeaturesDao featuresDao();

    public abstract FT_DeviceHistoryDao ftDeviceHistoryDao();

    public abstract GPSLocationsDao gpsLocationsDao();

    public abstract HotlistDao hotlistDao();

    public abstract LocationGroupsDao locationGroupsDao();

    public abstract LocationGroupLocationsDao locationGroupLocationsDao();

    public abstract LocationsDao locationsDao();

    public abstract LPRNotificationsDao lprNotificationsDao();

    public abstract MaintenanceLogsDao maintenanceLogsDao();

    public abstract MaintenancePicturesDao maintenancePicturesDao();

    public abstract MakesAndModelsDao makesAndModelsDao();

    public abstract MessagesDao messagesDao();

    public abstract MetersDao metersDao();

    public abstract MobileNowLogsDao mobileNowLogsDao();

    public abstract ModulesDao modulesDao();

    public abstract PermitsDao permitsDao();

    public abstract PrintMacrosDao printMacrosDao();

    public abstract PrintTemplatesDao printTemplatesDao();

    public abstract RepeatOffendersDao repeatOffendersDao();

    public abstract SpecialActivitiesDao specialActivitiesDao();

    public abstract SpecialActivityDispositionDao specialActivityDispositionDao();

    public abstract SpecialActivityReportsDao specialActivityReportsDao();

    public abstract SpecialActivityLocationDao specialActivityLocationDao();

    public abstract SpecialActivityPictureDao specialActivityPictureDao();

    public abstract StatesDao statesDao();

    public abstract StreetPrefixesDao streetPrefixesDao();

    public abstract StreetSuffixesDao streetSuffixesDao();

    public abstract SyncDataDao syncDataDao();

    public abstract TicketCommentsDao ticketCommentsDao();

    public abstract TicketCommentsDaoTemp ticketCommentsDaoTemp();

    public abstract TicketHistoryDao ticketHistoryDao();

    public abstract TicketPicturesDao ticketPicturesDao();

    public abstract TicketPicturesDaoTemp ticketPicturesDaoTemp();

    public abstract TicketsDao ticketsDao();

    public abstract TicketsDaoTemp ticketsDaoTemp();

    public abstract TicketViolationsDao ticketViolationsDao();

    public abstract TicketViolationsDaoTemp ticketViolationsDaoTemp();

    public abstract TPMEulaDao tpmEulaDao();

    public abstract UsersDao usersDao();

    public abstract UserSettingsDao userSettingsDao();

    public abstract VendorsDao vendorsDao();

    public abstract VendorItemsDao vendorItemsDao();

    public abstract VendorServicesDao vendorServicesDao();

    public abstract VendorServiceRegistrationsDao vendorServiceRegistrationsDao();

    public abstract ViolationGroupsDao violationGroupsDao();

    public abstract ViolationGroupViolationsDao violationGroupViolationsDao();

    public abstract ViolationsDao violationsDao();

    public abstract VoidTicketReasonsDao voidTicketReasonsDao();

    public abstract ZonesDao zonesDao();

    public abstract PlacardDao placardDao();
}
