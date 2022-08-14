package com.csupporter.techwiz.presentation.view.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Appointment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mct.components.baseui.BaseOverlayDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class AddAppointmentDialog extends BaseOverlayDialog implements View.OnClickListener {
    private TextInputEditText txtDate;
    private TextInputEditText txtTime;
    private TextInputEditText txtDescription;
    private TextInputEditText lastEdtClick;
    private AppCompatButton btnBook;


    private OnClickAddNewAppointment onClickAddNew;

    public AddAppointmentDialog(@NonNull Context context, OnClickAddNewAppointment onClickAddNewHealthTracking) {
        super(context);
        this.onClickAddNew = onClickAddNewHealthTracking;
    }


    public interface OnClickAddNewAppointment {
        void onClickAddNew(Appointment appointment);
    }

    @NonNull
    @Override
    protected androidx.appcompat.app.AlertDialog.Builder onCreateDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_book_appointment, null);

        init(view);
        addEventAddClick();

        return new androidx.appcompat.app.AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(view);
    }

    private void init(View view) {
        txtDate = view.findViewById(R.id.edt_date_end);
        txtTime = view.findViewById(R.id.edt_time_end);
        txtDescription = view.findViewById(R.id.edt_event_description);
        btnBook = view.findViewById(R.id.btn_book);

        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);

        getTextInputLayout(txtDate).setEndIconOnClickListener(v -> txtDate.performClick());
        getTextInputLayout(txtTime).setEndIconOnClickListener(v -> txtTime.performClick());

    }



    @Override
    public void onClick(View view) {
        lastEdtClick = (TextInputEditText) view;
        switch (view.getId()) {

            case R.id.edt_date_end:
                showDatePicker(getDateFrom(lastEdtClick));
                break;

            case R.id.edt_time_end:
                showTimePicker(getTimeFrom(lastEdtClick));
                break;
        }
    }
    private long getTime() {
        Date date = getDateFrom(txtDate);
        Date time =  getTimeFrom(txtTime);
        Calendar calendar = mergeDateTime(date, time);
        return calendar.getTimeInMillis();
    }

    @NonNull
    private Calendar mergeDateTime(@NonNull Date date, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time == null ? date : new Date(date.getTime() + time.getTime()));
        return calendar;
    }
    private void addEventAddClick() {

        btnBook.setOnClickListener(view -> {

            String description = txtDescription.getText().toString().trim();
            Account account = App.getApp().getAccount();

            Appointment appointment = new Appointment();
            appointment.setId(FirebaseUtils.uniqueId());
            appointment.setUserId(account.getId());
            appointment.setDescription(description);
            appointment.setTime(getTime());
            onClickAddNew.onClickAddNew(appointment);
        });
    }


    @Override
    protected void onDialogCreated(@NonNull AlertDialog dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().

                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().

                setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().

                getAttributes().windowAnimations = androidx.appcompat.R.style.Base_Animation_AppCompat_DropDownUp;

        dialog.setCanceledOnTouchOutside(true);
    }

    private void showDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DatePickerDialog dialog = new DatePickerDialog(context,
                this::onDateSet,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_radius_20);
        dialog.show();
    }

    private void showTimePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        TimePickerDialog dialog = new TimePickerDialog(context,
                this::onTimeSet,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_radius_20);
        dialog.show();
    }


    private void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        onDateOrTimeSet(calendar.getTime(), true);
    }

    private void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        onDateOrTimeSet(calendar.getTime(), false);
    }

    private void onDateOrTimeSet(Date date, boolean isDateFormat) {
        getTextInputLayout(txtDate).setError(null);
        getTextInputLayout(txtTime).setError(null);
        lastEdtClick.setText(getFormat(isDateFormat).format(date));
    }

    @NonNull
    private Date getDateFrom(TextInputEditText edt) {
        return parseDateOrTime(getText(edt), true);
    }

    @NonNull
    private Date getTimeFrom(TextInputEditText edt) {
        return parseDateOrTime(getText(edt), false);
    }

    @NonNull
    private Date parseDateOrTime(String text, boolean isDateFormat) {
        Date date = null;
        try {
            date = getFormat(isDateFormat).parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            date = new Date();
        }
        return date;
    }

    private DateFormat getFormat(boolean isDateFormat) {
        return isDateFormat ? DateFormat.getDateInstance() : DateFormat.getTimeInstance(3);
    }

    protected String getText(@NonNull TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString();
    }

    protected TextInputLayout getTextInputLayout(@NonNull TextInputEditText editText) {
        ViewParent parent = editText.getParent();
        while (parent instanceof View) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }
}
