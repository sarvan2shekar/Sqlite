package com.testapp.sarvan.sqlite.presenter.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.testapp.sarvan.sqlite.model.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarva on 07-08-2017.
 * For CRUD operations
 */

public class EmployeeOperations {
    private static final String LOGTAG = "EMP_MNGMNT_SYS";
    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase database;

    public EmployeeOperations(Context context) {
        dbhandler = new EmployeeDBHandler(context);
    }

    private static final String[] allColumns = {
            EmployeeDBHandler.COLUMN_ID,
            EmployeeDBHandler.COLUMN_FIRST_NAME,
            EmployeeDBHandler.COLUMN_LAST_NAME,
            EmployeeDBHandler.COLUMN_GENDER,
            EmployeeDBHandler.COLUMN_HIRE_DATE,
            EmployeeDBHandler.COLUMN_DEPT
    };

    public void open() {
        Log.i(LOGTAG, "Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    public Employee addEmployee(Employee Employee) {
        ContentValues values = new ContentValues();
        values.put(EmployeeDBHandler.COLUMN_FIRST_NAME, Employee.getFirstname());
        values.put(EmployeeDBHandler.COLUMN_LAST_NAME, Employee.getLastname());
        values.put(EmployeeDBHandler.COLUMN_GENDER, Employee.getGender());
        values.put(EmployeeDBHandler.COLUMN_HIRE_DATE, Employee.getHiredate());
        values.put(EmployeeDBHandler.COLUMN_DEPT, Employee.getDept());
        long insertId = database.insert(EmployeeDBHandler.TABLE_EMPLOYEES, null, values);
        Employee.setEmpId(insertId);
        return Employee;
    }

    // Getting single Employee
    public Employee getEmployee(long id) {
        Cursor cursor = null;
        try {
            cursor = database.query(EmployeeDBHandler.TABLE_EMPLOYEES, allColumns,
                    EmployeeDBHandler.COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                    null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                return new Employee(Long.parseLong(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5));
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        Cursor cursor = null;
        List<Employee> employees = new ArrayList<>();
        try {
            cursor = database.query(EmployeeDBHandler.TABLE_EMPLOYEES, allColumns,
                    null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Employee employee = new Employee();
                    employee.setEmpId(cursor.getLong(cursor.getColumnIndex(
                            EmployeeDBHandler.COLUMN_ID)));
                    employee.setFirstname(cursor.getString(cursor.getColumnIndex(
                            EmployeeDBHandler.COLUMN_FIRST_NAME)));
                    employee.setLastname(cursor.getString(cursor.getColumnIndex(
                            EmployeeDBHandler.COLUMN_LAST_NAME)));
                    employee.setGender(cursor.getString(cursor.getColumnIndex(
                            EmployeeDBHandler.COLUMN_GENDER)));
                    employee.setHiredate(cursor.getString(cursor.getColumnIndex(
                            EmployeeDBHandler.COLUMN_HIRE_DATE)));
                    employee.setDept(cursor.getString(cursor.getColumnIndex(
                            EmployeeDBHandler.COLUMN_DEPT)));
                    employees.add(employee);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        // return All Employees
        return employees;

    }

    // Updating Employee
    public int updateEmployee(Employee employee) {
        ContentValues values = new ContentValues();
        values.put(EmployeeDBHandler.COLUMN_FIRST_NAME, employee.getFirstname());
        values.put(EmployeeDBHandler.COLUMN_LAST_NAME, employee.getLastname());
        values.put(EmployeeDBHandler.COLUMN_GENDER, employee.getGender());
        values.put(EmployeeDBHandler.COLUMN_HIRE_DATE, employee.getHiredate());
        values.put(EmployeeDBHandler.COLUMN_DEPT, employee.getDept());

        // updating row
        return database.update(EmployeeDBHandler.TABLE_EMPLOYEES, values,
                EmployeeDBHandler.COLUMN_ID + "=?",
                new String[]{String.valueOf(employee.getEmpId())});
    }

    // Deleting Employee
    public void removeEmployee(Employee employee) {
        database.delete(EmployeeDBHandler.TABLE_EMPLOYEES,
                EmployeeDBHandler.COLUMN_ID + "=" + employee.getEmpId(), null);
    }
}
