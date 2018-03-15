package com.example.natthamondumrongkaviroyaphan.mytransferpatient;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CreateFareActivity extends AppCompatActivity {

    private LinearLayout notiLayout;
    private Spinner spNumNoti;
    private String[] notiItem;
    Calendar myCalendar;
    EditText etFareName;
    TextView txStartDate,txStartTime;
    Button btnSave,btnCancel;
    ImageView icLocateStart,icLocateEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_setting);
        String groupId = getIntent().getStringExtra("groupId");

        btnSave = (Button) findViewById(R.id.btn_save);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        etFareName = (EditText) findViewById(R.id.et_fare_name);
        txStartDate = (TextView) findViewById(R.id.et_start_date);
        txStartTime = (TextView) findViewById(R.id.et_start_time);
        notiLayout = (LinearLayout) findViewById(R.id.noti_layout);
        icLocateStart = (ImageView) findViewById(R.id.locate_ic);
        icLocateEnd = (ImageView) findViewById(R.id.locate_ic);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference groups = mRootRef.child("groups");
                    groups.child(groupId).child("endDate").setValue(txStartDate.getText() + " " + txStartTime.getText());
                    groups.child(groupId).child("fareBy").setValue("jira");
                    groups.child(groupId).child("fareName").setValue(etFareName.getText().toString());
                    groups.child(groupId).child("remind").setValue("5,10,15,20");
                    groups.child(groupId).child("startDate").setValue(txStartDate.getText() + " " + txStartTime.getText());
                    finish();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateFareActivity.this);
                    builder1.setMessage("กรุณากรอกข้อมูลให้ครบ");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setDateAndTimePicker();
        setNotificationLayout();

        icLocateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateFareActivity.this,TestSearchActivity.class);
                startActivity(intent);
            }
        });



    }

    private void setDateAndTimePicker(){
        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "EEE, d MMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                txStartDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        txStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateFareActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txStartTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateFareActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txStartTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    private void setNotificationLayout(){
        Resources res = getResources();
        notiItem = res.getStringArray(R.array.noti_arrays);
        spNumNoti = (Spinner) findViewById(R.id.sp_num_noti);
        spNumNoti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                notiLayout.removeAllViews();
                int num = Integer.parseInt(notiItem[position]);
                List<Integer> listRemind = new ArrayList<Integer>();
                int[] result = new int[num-1];
                int i = 0;
                for (i=1;i<num;i++) {
                    listRemind.add(0);
                    LinearLayout layout = new LinearLayout(CreateFareActivity.this);
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layout.setLayoutParams(layoutParams);

                    TextView label = new TextView(CreateFareActivity.this);
                    label.setText("ครั้งที่ "+i+" :");

                    EditText numRemind = new EditText(CreateFareActivity.this);
                    numRemind.setHint("ระยะทาง");
                    numRemind.setInputType(InputType.TYPE_CLASS_NUMBER);
                    int finalI = i-1;
                    numRemind.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                result[finalI] = Integer.parseInt(numRemind.getText().toString());
                            }
                        }
                    });
                    TextView label2 = new TextView(CreateFareActivity.this);
                    label2.setText(" KM.");

                    FrameLayout.LayoutParams ladderParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT);
                    label.setLayoutParams(ladderParams);
                    numRemind.setLayoutParams(ladderParams);
                    label2.setLayoutParams(ladderParams);

                    layout.addView(label);
                    layout.addView(numRemind);
                    layout.addView(label2);
                    notiLayout.addView(layout);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean validateFields(){
        boolean isComplete = false;
        if (etFareName.getText().toString().length()>0){
            isComplete = true;
        } else {
            isComplete = false;
        }
        return isComplete;
    }

}
