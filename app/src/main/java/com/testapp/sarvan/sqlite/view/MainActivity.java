package com.testapp.sarvan.sqlite.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.testapp.sarvan.sqlite.R;
import com.testapp.sarvan.sqlite.presenter.DB.EmployeeOperations;

public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_EMP_ID = "com.testapp.sarvan.sqlite.empId";
    private static final String EXTRA_ADD_UPDATE = "com.testapp.sarvan.sqlite.add_update";
    private EmployeeOperations employeeOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        employeeOps = new EmployeeOperations(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        employeeOps.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        employeeOps.close();
    }

    public void onClick(View v) {
        if (v != null && v.getTag() != null) {
            ButtonVal val = ButtonVal.valueOf(v.getTag().toString());
            switch (val) {
                case add:
                    Intent i = new Intent(MainActivity.this, AddUpdateEmployee.class);
                    i.putExtra(EXTRA_ADD_UPDATE, "Add");
                    startActivity(i);
                    break;
                case update:
                    getEmpIdAndUpdateEmp();
                    break;
                case remove:
                    getEmpIdAndRemoveEmp();
                    break;
                case viewall:
                    Intent intent = new Intent(MainActivity.this, ViewAllEmployees.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    public void getEmpIdAndUpdateEmp() {
        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_emp_get_id, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setView(getEmpIdView);
        final EditText empId = getEmpIdView.findViewById(R.id.empId);
        ab.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (empId != null && empId.getText() != null &&
                        !empId.getText().toString().trim().equalsIgnoreCase("")) {
                    Long val = null;
                    try {
                        val = Long.parseLong(empId.getText().toString());
                    } catch (NumberFormatException nfe) {
                        val = null;
                    }
                    if (val != null) {
                        Intent i = new Intent(MainActivity.this, AddUpdateEmployee.class);
                        i.putExtra(EXTRA_ADD_UPDATE, "Update");
                        i.putExtra(EXTRA_EMP_ID, val);
                        startActivity(i);
                    }
                }
            }
        }).create().show();
    }

    private void getEmpIdAndRemoveEmp() {
        LayoutInflater li = LayoutInflater.from(this);
        View empIdView = li.inflate(R.layout.dialog_emp_get_id, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setView(empIdView);
        final EditText empId = empIdView.findViewById(R.id.empId);
        ab.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                if (empId != null && empId.getText() != null &&
                        !empId.getText().toString().trim().equalsIgnoreCase("")) {
                    Long val = null;
                    try {
                        val = Long.parseLong(empId.getText().toString());
                    } catch (NumberFormatException nfe) {
                        val = null;
                    }
                    if (val != null && employeeOps.removeEmployee(employeeOps.getEmployee(val))) {
                        Toast t = Toast.makeText(MainActivity.this, "Employee removed successfully!",
                                Toast.LENGTH_SHORT);
                        t.show();
                    } else {
                        Toast t = Toast.makeText(MainActivity.this, "No such record found!",
                                Toast.LENGTH_SHORT);
                        t.show();
                    }
                }

            }
        }).create()
                .show();
    }

    private enum ButtonVal {
        add, update, remove, viewall
    }
}



