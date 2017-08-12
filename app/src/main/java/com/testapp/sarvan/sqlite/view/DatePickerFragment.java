package com.testapp.sarvan.sqlite.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.testapp.sarvan.sqlite.R;

import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {
    public static final String EXTRA_DATE =
            "com.testapp.sarvan.sqlite.date";
    private static final int REQUEST_DATE = 1;
    private DatePicker datePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
        datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);

        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Hire Date")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = datePicker.getYear();
                                int mon = datePicker.getMonth();
                                int day = datePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year, mon, day).getTime();
                                DateDialogListener activity = (DateDialogListener) getActivity();
                                activity.onFinishDialog(date);
                                dismiss();
                            }
                        })
                .create();
    }

    public interface DateDialogListener {
        void onFinishDialog(Date date);
    }
}
