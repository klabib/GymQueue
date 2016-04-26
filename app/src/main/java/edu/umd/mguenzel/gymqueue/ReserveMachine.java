package edu.umd.mguenzel.gymqueue;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ReserveMachine extends Activity {

    private final String[] RESERVABLE_TIMES = new String[56];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_machine);

        //setting up the reservable times, from 8:00-22:00
        int index = 0;
        for (int i = 8; i <= 22; i++) {
            RESERVABLE_TIMES[index] = i + ":00";
            RESERVABLE_TIMES[index + 1] = i + ":15";
            RESERVABLE_TIMES[index + 2] = i + ":30";
            RESERVABLE_TIMES[index + 3] = i + ":45";
            index += 4;
            Log.i("test", RESERVABLE_TIMES[index]);
            Log.i("test", RESERVABLE_TIMES[index+1]);
            Log.i("test", RESERVABLE_TIMES[index+2]);
            Log.i("test", RESERVABLE_TIMES[index+3]);
        }
    }
}
