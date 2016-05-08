package edu.umd.mguenzel.gymqueue;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
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
    private String uid, machine, cat, hour = "", min = "";
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

    private boolean test = false, completed = false;

    private AlarmManager mAlarmManager;

    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_machine);

        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://gymqueue.firebaseio.com");

        Intent intent = getIntent();
        uid = intent.getStringExtra("UID");
        machine = intent.getStringExtra("machine");
        cat = intent.getStringExtra("cat");

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
                    min = (String) ((RadioButton) findViewById(rg_min.getCheckedRadioButtonId())).getText();
                    String ampm = (String) ((RadioButton) findViewById(rg_ampm.getCheckedRadioButtonId())).getText();

                    if (rg_hour1.getCheckedRadioButtonId() == -1) {
                        hour = (String) ((RadioButton) findViewById(rg_hour2.getCheckedRadioButtonId())).getText();
                    } else {
                        hour = (String) ((RadioButton) findViewById(rg_hour1.getCheckedRadioButtonId())).getText();
                    }
                    //hour = toMilitaryTime(hour, ampm);

                    String str = month + "/" + day + "/" + year + " " + hour + min + " " + ampm;
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    Date currDate = new Date();
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
                        //do nothing
                    } else { //time passed today, reserve for tomorrow
                        Log.i("test", "tomorrow" + " " + currDate.toString());

                        //day = 31;
                        //incrementDay(monthName, day);
                        //Log.i("test", "Month: " + monthName + " Day: " + day);

                        //TODO: increment day properly
                        day = day + 1;

                    }


                    test = true;
                    //store time in firebase to trigger onDataChanged
                    mFirebase.child("time_for_reservation").setValue(hour + min + " " + ampm);


                }
            }
        });

        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (test == false || completed == true)
                    return;
                String time = (String) snapshot.child("time_for_reservation").getValue();

                Long max = (Long) snapshot.child("Categories").child(cat).child(machine).getValue();
                Long currVal = (Long) (snapshot.child("Reservations").child(getMonthName(month)).child(Integer.toString(day)).child(time).child(machine).getValue());

                Long res_num;
                if (currVal == null) { //no reservations for this machine at this time yet
                    res_num = 1L;
                    mFirebase.child("Reservations").child(getMonthName(month)).child(Integer.toString(day)).child(time).child(machine).setValue(res_num);
                    Toast.makeText(getApplicationContext(), "You have successfully reserved " + machine + " 1 "
                            + "for " + getMonthName(month) + " " + day + " at " + time, Toast.LENGTH_LONG).show();
                    completed = true;

                    updateUserStats(snapshot, uid, machine);
                    setResult(RESULT_OK);
                    finish();
                } else { //at least one reservation at this time for this machine
                    if (currVal >= max) { //full for this time
                        Toast.makeText(getApplicationContext(), "There are no spots available at this time. Please select a new time", Toast.LENGTH_LONG).show();
                    } else { //set reservation
                        res_num = currVal + 1;
                        mFirebase.child("Reservations").child(getMonthName(month)).child(Integer.toString(day)).child(time).child(machine).setValue(res_num);
                        Toast.makeText(getApplicationContext(), "You have successfully reserved " + machine + " " + res_num
                                + " for " + getMonthName(month) + " " + day + " at " + time, Toast.LENGTH_LONG).show();
                        completed = true;

                        updateUserStats(snapshot, uid, machine);

                        /*
                        Intent intent_alarm = new Intent(getApplicationContext(), ReserveMachine.class);
                        PendingIntent pi = PendingIntent.getService(getApplicationContext(), 0, intent_alarm, PendingIntent.FLAG_ONE_SHOT);

                        long alarm_time = System.currentTimeMillis();

                        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, date.getTime() , pi);
                        Log.i("test", "" + date.toString());

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis((date.getTime()));
                        int mYear = calendar.get(Calendar.YEAR);
                        int mMonth = calendar.get(Calendar.MONTH) + 1;
                        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                        */

                        setResult(RESULT_OK);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "An error occured, please try again later", Toast.LENGTH_LONG).show();
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

    private void updateUserStats(DataSnapshot snapshot, String user, String machineName) {
        Long num = (Long) snapshot.child("Users").child(uid).child(machine).getValue();
        if (num == null) { //first time user is using this machine
            mFirebase.child("Users").child(uid).child(machineName).setValue(1);
        } else {
            mFirebase.child("Users").child(uid).child(machineName).setValue(num + 1);
        }
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

        return null;
    }

    private void incrementDay(String month, int day) {
        if (month.equals("January")) {
            if (day == 31)
                month = "February";
            else
                return;
        } else if (month.equals("February")) {
            if (day == 28)
                month = "March";
            else
                return;
        } else if (month.equals("March")) {
            if (day == 31)
                month = "April";
            else
                return;
        } else if (month.equals("April")) {
            if (day == 30)
                month = "May";
            else
                return;
        } else if (month.equals("May")) {
            if (day == 31)
                month = "June";
            else
                return;
        } else if (month.equals("June")) {
            if (day == 30)
                month = "July";
            else
                return;
        } else if (month.equals("July")) {
            if (day == 31)
                month = "August";
            else
                return;
        } else if (month.equals("August")) {
            if (day == 31)
                month = "September";
            else
                return;
        } else if (month.equals("September")) {
            if (day == 30)
                month = "October";
            else
                return;
        } else if (month.equals("October")) {
            if (day == 31)
                month = "November";
            else
                return;
        } else if (month.equals("November")) {
            if (day == 30)
                month = "December";
            else
                return;
        } else if (month.equals("December")) {
            if (day == 31)
                month = "January";
            else
                return;
        }
        day = 1;
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

