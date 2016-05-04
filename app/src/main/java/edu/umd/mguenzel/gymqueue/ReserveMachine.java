package edu.umd.mguenzel.gymqueue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReserveMachine extends Activity {

    private final String[] RESERVABLE_TIMES = new String[57];
    private String uid, machine;
    private Firebase mFirebase;
    private int day, month, year;
    private boolean isChecking = true;
    private int mCheckedId = R.id.one;
    private RadioGroup mFirstGroup;
    private RadioGroup mSecondGroup;
    private Button submit;

    private RadioGroup rg_hour1, rg_hour2, rg_min, rg_ampm;
    private RadioButton rb_hour, rb_min, rb_ampm;

    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_machine);

        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://gymqueue.firebaseio.com");

        Intent intent = getIntent();
        uid = intent.getStringExtra("UID");
        machine = intent.getStringExtra("machine");

        submit = (Button) findViewById(R.id.submit);
        rg_hour1 = (RadioGroup) findViewById(R.id.hour1);
        rg_hour2 = (RadioGroup) findViewById(R.id.hour2);
        rg_min = (RadioGroup) findViewById(R.id.minute);
        rg_ampm = (RadioGroup) findViewById(R.id.ampm);

        Date d = new Date();
        cal = Calendar.getInstance();
        cal.setTime(d);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        year = cal.get(Calendar.YEAR);

        //setting up the reservable times, from 8:00-22:00
        int index = 0;
        for (int i = 8; i < 22; i++) {
            RESERVABLE_TIMES[index] = i + ":00";
            RESERVABLE_TIMES[index + 1] = i + ":15";
            RESERVABLE_TIMES[index + 2] = i + ":30";
            RESERVABLE_TIMES[index + 3] = i + ":45";
            index += 4;
        }
        RESERVABLE_TIMES[index] = "22:00";

        //for (int i = 0; i <= 56; i++) {
         //   Log.i("test", RESERVABLE_TIMES[i]);
        //}

        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //int currval = (Integer) snapshot.child("April").child("25").child("6:00").child("Treadmill").getValue();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rg_hour1.getCheckedRadioButtonId() == -1 && rg_hour2.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select an hour", Toast.LENGTH_LONG).show();
                } else if (rg_min.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select a minute interval", Toast.LENGTH_LONG).show();
                } else if (rg_ampm.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select either am or pm", Toast.LENGTH_LONG).show();
                } else { //valid
                    String min = (String) ((RadioButton) findViewById(rg_min.getCheckedRadioButtonId())).getText();
                    String ampm = (String) ((RadioButton) findViewById(rg_ampm.getCheckedRadioButtonId())).getText();
                    String hour = "";

                    if (rg_hour1.getCheckedRadioButtonId() == -1) {
                        hour = (String) ((RadioButton) findViewById(rg_hour2.getCheckedRadioButtonId())).getText();
                    } else {
                        hour = (String) ((RadioButton) findViewById(rg_hour1.getCheckedRadioButtonId())).getText();
                    }
                    //hour = toMilitaryTime(hour, ampm);

                    //if time is later today
                        //reserve for today
                    //else
                        //reserve for tomorrow

                    String str = month + "/" + day + "/" + year + " " + hour + min  + " " + ampm;
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    Date date, currDate = new Date();
                    try {
                        date = formatter.parse(str);
                        Log.i("date", date.toString());
                    } catch (ParseException e) {
                        Log.i("exception", "Time parse error (should never get here)");
                        return;
                    }

                    String monthName = getMonthName(month);



                    if (date.after(currDate)) { //time is later today, reserve for today
                        Log.i("test", "today");
                        //create reservation in firebase

                        mFirebase.child("Reservations").child(monthName).child(Integer.toString(day)).child(hour + min + " " + ampm).child(machine).setValue(1);

                        //IN ON DATA CHANGED
                        //if reservation value at time > max
                            //change it back and display toast

                    } else { //time passed today, reserve for tomorrow
                        Log.i("test", "tomorrow" +  " " + currDate.toString());

                        //TODO: increment day properly
                        mFirebase.child("Reservations").child(monthName).child(Integer.toString(day + 1)).child(hour + min + " " + ampm).child(machine).setValue(1);
                    }


                }
            }
        });


        //listeners to help with selecting hours
        mFirstGroup = (RadioGroup) findViewById(R.id.hour1);
        mSecondGroup = (RadioGroup) findViewById(R.id.hour2);

        mFirstGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    mSecondGroup.clearCheck();
                    mCheckedId = checkedId;
                }
                isChecking = true;
            }
        });

        mSecondGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    mFirstGroup.clearCheck();
                    mCheckedId = checkedId;
                }
                isChecking = true;
            }
        });
    }

    private String getMonthName(int month) {
        if (month == 1) {
            return "January";
        } else if (month == 2) {
            return "February";
        } else if (month == 3) {
            return "March";
        } else if (month == 4) {
            return "April";
        } else if (month == 5) {
            return "May";
        } else if (month == 6) {
            return "June";
        } else if (month == 7) {
            return "July";
        } else if (month == 8) {
            return "August";
        } else if (month == 9) {
            return "September";
        } else if (month == 10) {
            return "October";
        } else if (month == 11) {
            return "November";
        } else if (month == 12) {
            return "December";
        }

        return "never here";
    }

    private String toMilitaryTime(String time, String ampm) {
        int t = Integer.parseInt(time);
        if (ampm.equals("am")) {
            if (t == 12) {
                return  "00";
            }
            return time;
        } else { //pm, convert to military
            if (t == 12) {
                return time;
            }
            t += 12;
            return Integer.toString(t);
        }
    }
}

