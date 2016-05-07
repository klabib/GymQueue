package edu.umd.mguenzel.gymqueue;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.TreeMap;

public class UserPage extends Activity{
    private int elliptical, rec_bike, stair, stat_bike, treadmill, deadlift, lat_pull, row_mach,
                bench_press_norm, chest_fly, bench_press_incl, bench_press_decl, calf_raise,
                leg_ext, leg_press, squat, should_press;
    private TextView elliptical_num, rec_bike_num, stair_num, stat_bike_num, treadmill_num, deadlift_num,
            lat_pull_num, row_mach_num, bench_press_norm_num, chest_fly_num, bench_press_incl_num,
            bench_press_decl_num, calf_raise_num, leg_ext_num, leg_press_num, squat_num, should_press_num;
    private int counter = 0;
    private long[] numbers;
    private Firebase mFirebase;
    private String uid;

    private TreeMap<String, Long> map = new TreeMap<String, Long>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://gymqueue.firebaseio.com");

        elliptical_num = (TextView)findViewById(R.id.elliptical_num);
        rec_bike_num = (TextView)findViewById(R.id.rec_bike_num);
        stair_num = (TextView)findViewById(R.id.stair_num);
        stat_bike_num = (TextView)findViewById(R.id.stat_bike_num);
        treadmill_num = (TextView)findViewById(R.id.treadmill_num);
        deadlift_num = (TextView)findViewById(R.id.deadlift_num);
        lat_pull_num = (TextView)findViewById(R.id.lat_pull_num);
        row_mach_num = (TextView)findViewById(R.id.row_mach_num);
        bench_press_norm_num = (TextView)findViewById(R.id.bench_press_norm_num);
        chest_fly_num = (TextView)findViewById(R.id.chest_fly_num);
        bench_press_incl_num = (TextView)findViewById(R.id.bench_press_incl_num);
        bench_press_decl_num = (TextView)findViewById(R.id.bench_press_decl_num);
        calf_raise_num = (TextView)findViewById(R.id.calf_raise_num);
        leg_ext_num = (TextView)findViewById(R.id.leg_ext_num);
        leg_press_num = (TextView)findViewById(R.id.leg_press_num);
        squat_num = (TextView)findViewById(R.id.squat_num);
        should_press_num = (TextView)findViewById(R.id.should_press_num);

        numbers = new long[16];

        uid = getIntent().getStringExtra("UID");

        final Button back = (Button) findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });

        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i("test", "here");

                for (DataSnapshot child : dataSnapshot.child("Users").child(uid).getChildren()) {

                    if (child.getKey() != "email") {
                        String key = child.getKey();
                        Long val;

                        if(child.getValue() == null) {
                            val = (long) 0;
                        }
                        else {
                            val = (Long) child.getValue();
                        }

                        if (key.equals("Elliptical")) {
                            elliptical_num.setText(val.toString());
                        }
                        else if (key.equals("Recumbent Bike")) {
                            rec_bike_num.setText(val.toString());
                        }
                        else if (key.equals("Stair Stepper")) {
                            stair_num.setText(val.toString());
                        }
                        else if (key.equals("Stationary Bike")) {
                            stat_bike_num.setText(val.toString());
                        }
                        else if (key.equals("Treadmill")) {
                            treadmill_num.setText(val.toString());
                        }
                        else if (key.equals("Deadlift")) {
                            deadlift_num.setText(val.toString());
                        }
                        else if (key.equals("Lat Pulldown")) {
                            lat_pull_num.setText(val.toString());
                        }
                        else if (key.equals("Row Machine")) {
                            row_mach_num.setText(val.toString());
                        }
                        else if (key.equals("Bench Press")) {
                            bench_press_norm_num.setText(val.toString());
                        }
                        else if (key.equals("Chest Flys")) {
                            chest_fly_num.setText(val.toString());
                        }
                        else if (key.equals("Decline Bench Press")) {
                            bench_press_decl_num.setText(val.toString());
                        }
                        else if (key.equals("Incline Bench Press")) {
                            bench_press_incl_num.setText(val.toString());
                        }
                        else if (key.equals("Calf Raises")) {
                            calf_raise_num.setText(val.toString());
                        }
                        else if (key.equals("Leg Extension")) {
                            leg_ext_num.setText(val.toString());
                        }
                        else if (key.equals("Leg Press")) {
                            leg_press_num.setText(val.toString());
                        }
                        else if (key.equals("Squat")) {
                            squat_num.setText(val.toString());
                        }
                        else if (key.equals("Shoulder Press")) {
                            should_press_num.setText(val.toString());
                        }
                        //Log.i("test", child.toString());
                        map.put(key, val);
                        Log.i("test", "key: " + key + " val: " + val);
                        //numbers[counter] = (Long) child.getValue();
                        //counter++;
                    }
                }
                /*
                //cardio
                elliptical = (int) dataSnapshot.child("Users").child(uid).child("Elliptical").getValue();
                rec_bike = (int) dataSnapshot.child("Users").child(uid).child("Recumbent Bike").getValue();
                stair = (int) dataSnapshot.child("Users").child(uid).child("Stair Stepper").getValue();
                stat_bike = (int) dataSnapshot.child("Users").child(uid).child("Stationary Bike").getValue();
                treadmill = (int) dataSnapshot.child("Users").child(uid).child("Treadmill").getValue();

                //back
                deadlift = (int) dataSnapshot.child("Users").child(uid).child("Deadlift").getValue();
                lat_pull = (int) dataSnapshot.child("Users").child(uid).child("Lat Pulldown").getValue();
                row_mach = (int) dataSnapshot.child("Users").child(uid).child("Row Machine").getValue();

                //chest
                bench_press_norm = (int) dataSnapshot.child("Users").child(uid).child("Bench Press").getValue();
                chest_fly = (int) dataSnapshot.child("Users").child(uid).child("Chest Flys").getValue();
                bench_press_incl = (int) dataSnapshot.child("Users").child(uid).child("Incline Bench Press").getValue();
                bench_press_decl = (int) dataSnapshot.child("Users").child(uid).child("Decline Bench Press").getValue();

                //legs
                calf_raise = (int) dataSnapshot.child("Users").child(uid).child("Calf Raises").getValue();
                leg_ext = (int) dataSnapshot.child("Users").child(uid).child("Leg Extension").getValue();
                leg_press = (int) dataSnapshot.child("Users").child(uid).child("Leg Press").getValue();
                squat = (int) dataSnapshot.child("Users").child(uid).child("Squat").getValue();

                //shoulders
                should_press = (int) dataSnapshot.child("Users").child(uid).child("Shoulder Press").getValue();
                */
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "An error occured, please try again later", Toast.LENGTH_LONG).show();
            }
        });

        //Long l = map.get("Elliptical");
        //Log.i("test", "" + l);
        //elliptical_num.setText(map.get("Elliptical").toString());
    }

}
