package com.testapp.sarvan.sqlite.presenter.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sarva on 07-08-2017.
 * DB handler
 */

public class EmployeeDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "employees.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_EMPLOYEES = "employees";
    public static final String COLUMN_ID = "empId";
    public static final String COLUMN_FIRST_NAME = "firstname";
    public static final String COLUMN_LAST_NAME = "lastname";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_HIRE_DATE = "hiredata";
    public static final String COLUMN_DEPT = "dept";

    public EmployeeDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_EMPLOYEES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_HIRE_DATE + " NUMERIC, " +
                    COLUMN_DEPT + " TEXT " +
                    ")";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }
}
