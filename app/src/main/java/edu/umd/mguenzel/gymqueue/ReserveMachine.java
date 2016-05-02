package edu.umd.mguenzel.gymqueue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;

public class ReserveMachine extends Activity {

    private final String[] RESERVABLE_TIMES = new String[57];
    String uid;
    String machine;
    Firebase mFirebase;

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

        //TODO: add DataSnapshot to be abel to use get value
        Firebase test = mFirebase.child("Categories").child("Back").child("Deadlift");
        Firebase test2 = mFirebase.child("Categories").child("Back").child("asdasdas");

        if (test != null) {
            Log.i("test", ""+test);
        }

        if (test2 != null) {
            Log.i("test", "should not get here "+test2);
        } else {
            Log.i("test", "null");
        }

        test.getParent();


    }

}
