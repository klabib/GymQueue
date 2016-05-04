package edu.umd.mguenzel.gymqueue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class ReserveMachine extends Activity {

    private final String[] RESERVABLE_TIMES = new String[57];
    String uid;
    String machine;
    Firebase mFirebase;
    String month;
    int day;
    private boolean isChecking = true;
    private int mCheckedId = R.id.one;
    private RadioGroup mFirstGroup;
    private RadioGroup mSecondGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_machine);

        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://gymqueue.firebaseio.com");

        Intent intent = getIntent();
        uid = intent.getStringExtra("UID");
        machine = intent.getStringExtra("machine");

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

        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


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
}

