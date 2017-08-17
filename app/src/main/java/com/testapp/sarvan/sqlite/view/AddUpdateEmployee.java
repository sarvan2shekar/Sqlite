package com.testapp.sarvan.sqlite.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.testapp.sarvan.sqlite.R;
import com.testapp.sarvan.sqlite.model.Employee;
import com.testapp.sarvan.sqlite.presenter.DB.EmployeeOperations;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUpdateEmployee extends AppCompatActivity implements DatePickerFragment.DateDialogListener {
    private static final String EXTRA_EMP_ID = "com.testapp.sarvan.sqlite.empId";
    private static final String EXTRA_ADD_UPDATE = "com.testapp.sarvan.sqlite.add_update";
    private static final String DIALOG_DATE = "DialogDate";
    private Button addUpdateButton;
    private String mode;
    private Long empId;
    private EditText firstNameEditText, lastNameEditText, deptEditText, hireDateEditText;
    private RadioGroup radioGroup;
    private Employee newEmployee, oldEmployee;
    private EmployeeOperations employeeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_employee);
        firstNameEditText = (EditText) findViewById(R.id.edit_text_first_name);
        lastNameEditText = (EditText) findViewById(R.id.edit_text_last_name);
        deptEditText = (EditText) findViewById(R.id.edit_text_dept);
        hireDateEditText = (EditText) findViewById(R.id.edit_text_hire_date);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        addUpdateButton = (Button) findViewById(R.id.addUpdateButton);
        newEmployee = new Employee();
        oldEmployee = new Employee();
        employeeData = new EmployeeOperations(this);
        employeeData.open();
        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        if (mode.equals("Update")) {
            empId = getIntent().getLongExtra(EXTRA_EMP_ID, 0);
            if (initializeEmployee(empId)) {
                addUpdateButton.setText("Update Employee");
            } else {
                finish();
            }
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioButtonMale) {
                    newEmployee.setGender("M");
                    if (mode.equals("Update")) {
                        oldEmployee.setGender("M");
                    }
                } else if (checkedId == R.id.radioButtonFemale) {
                    newEmployee.setGender("F");
                    if (mode.equals("Update")) {
                        oldEmployee.setGender("F");
                    }
                }
            }
        });
    }

    public void pickDate(View v) {
        FragmentManager manager = getSupportFragmentManager();
        DatePickerFragment dialog = new DatePickerFragment();
        dialog.show(manager, DIALOG_DATE);
    }

    public void addUpdate(View v) {
        if (mode.equals("Add")) {
            if (firstNameEditText != null && firstNameEditText.getText() != null &&
                    firstNameEditText.getText().toString().trim().length() > 0) {
                newEmployee.setFirstname(firstNameEditText.getText().toString());
            }
            if (lastNameEditText != null && lastNameEditText.getText() != null &&
                    lastNameEditText.getText().toString().trim().length() > 0) {
                newEmployee.setLastname(lastNameEditText.getText().toString());
            }
            if (hireDateEditText != null && hireDateEditText.getText() != null &&
                    hireDateEditText.getText().toString().trim().length() > 0) {
                newEmployee.setHiredate(hireDateEditText.getText().toString());
            }
            if (deptEditText != null && deptEditText.getText() != null &&
                    deptEditText.getText().toString().trim().length() > 0) {
                newEmployee.setDept(deptEditText.getText().toString());
            }
            if (newEmployee.getFirstname() != null && newEmployee.getLastname() != null
                    && newEmployee.getDept() != null && newEmployee.getHiredate() != null && newEmployee.getGender() != null) {
                employeeData.addEmployee(newEmployee);
                Toast t = Toast.makeText(AddUpdateEmployee.this, "Employee "
                        + newEmployee.getFirstname() + " has been added successfully !", Toast.LENGTH_SHORT);
                t.show();
                Intent i = new Intent(AddUpdateEmployee.this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast t = Toast.makeText(AddUpdateEmployee.this, "Enter the required data!", Toast.LENGTH_SHORT);
                t.show();
            }
        } else {
            oldEmployee.setFirstname(firstNameEditText.getText().toString());
            oldEmployee.setLastname(lastNameEditText.getText().toString());
            oldEmployee.setHiredate(hireDateEditText.getText().toString());
            oldEmployee.setDept(deptEditText.getText().toString());
            employeeData.updateEmployee(oldEmployee);
            Toast t = Toast.makeText(AddUpdateEmployee.this, "Employee "
                    + oldEmployee.getFirstname() + " has been updated successfully !", Toast.LENGTH_SHORT);
            t.show();
            Intent i = new Intent(AddUpdateEmployee.this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    private boolean initializeEmployee(long empId) {
        oldEmployee = employeeData.getEmployee(empId);
        if (oldEmployee != null) {
            firstNameEditText.setText(oldEmployee.getFirstname());
            lastNameEditText.setText(oldEmployee.getLastname());
            hireDateEditText.setText(oldEmployee.getHiredate());
            radioGroup.check(oldEmployee.getGender().equals("M")
                    ? R.id.radioButtonMale : R.id.radioButtonFemale);
            deptEditText.setText(oldEmployee.getDept());
            return true;
        }
        return false;
    }

    @Override
    public void onFinishDialog(Date date) {
        hireDateEditText.setText(formatDate(date));
    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hireDate = sdf.format(date);
        return hireDate;
    }
}
