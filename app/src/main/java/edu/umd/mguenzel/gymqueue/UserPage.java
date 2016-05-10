package edu.umd.mguenzel.gymqueue;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.TreeMap;

public class UserPage extends Activity{

    private TextView elliptical_num, rec_bike_num, stair_num, stat_bike_num, treadmill_num, deadlift_num,
            lat_pull_num, row_mach_num, bench_press_norm_num, chest_fly_num, bench_press_incl_num,
            bench_press_decl_num, calf_raise_num, leg_ext_num, leg_press_num, squat_num, should_press_num;

    private Firebase mFirebase;
    private String uid;

    private TreeMap<String, Long> map = new TreeMap<String, Long>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://gymqueue.firebaseio.com");

        TextView tv=(TextView)findViewById(R.id.title);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/quicksand_regular.ttf");
        tv.setTypeface(face);


        TextView elliptical =(TextView)findViewById(R.id.elliptical);
        elliptical.setTypeface(face);
        elliptical_num = (TextView)findViewById(R.id.elliptical_num);
        elliptical_num.setTypeface(face);

        TextView rec_bike =(TextView)findViewById(R.id.rec_bike);
        rec_bike.setTypeface(face);
        rec_bike_num = (TextView)findViewById(R.id.rec_bike_num);
        rec_bike_num.setTypeface(face);

        TextView stair =(TextView)findViewById(R.id.stair);
        stair.setTypeface(face);
        stair_num = (TextView)findViewById(R.id.stair_num);
        stair_num.setTypeface(face);

        TextView stat_bike =(TextView)findViewById(R.id.stat_bike);
        stat_bike.setTypeface(face);
        stat_bike_num = (TextView)findViewById(R.id.stat_bike_num);
        stat_bike_num.setTypeface(face);

        TextView treadmill =(TextView)findViewById(R.id.treadmill);
        treadmill.setTypeface(face);
        treadmill_num = (TextView)findViewById(R.id.treadmill_num);
        treadmill_num.setTypeface(face);

        TextView deadlift =(TextView)findViewById(R.id.deadlift);
        deadlift.setTypeface(face);
        deadlift_num = (TextView)findViewById(R.id.deadlift_num);
        deadlift_num.setTypeface(face);

        TextView lat_pull =(TextView)findViewById(R.id.lat_pull);
        lat_pull.setTypeface(face);
        lat_pull_num = (TextView)findViewById(R.id.lat_pull_num);
        lat_pull_num.setTypeface(face);

        TextView row_mach =(TextView)findViewById(R.id.row_mach);
        row_mach.setTypeface(face);
        row_mach_num = (TextView)findViewById(R.id.row_mach_num);
        row_mach_num.setTypeface(face);

        TextView bench_press_norm =(TextView)findViewById(R.id.bench_press_norm);
        bench_press_norm.setTypeface(face);
        bench_press_norm_num = (TextView)findViewById(R.id.bench_press_norm_num);
        bench_press_norm_num.setTypeface(face);

        TextView chest_fly =(TextView)findViewById(R.id.chest_fly);
        chest_fly.setTypeface(face);
        chest_fly_num = (TextView)findViewById(R.id.chest_fly_num);
        chest_fly_num.setTypeface(face);

        TextView bench_press_incl =(TextView)findViewById(R.id.bench_press_incl);
        bench_press_incl.setTypeface(face);
        bench_press_incl_num = (TextView)findViewById(R.id.bench_press_incl_num);
        bench_press_incl_num.setTypeface(face);

        TextView bench_press_decl =(TextView)findViewById(R.id.bench_press_decl);
        bench_press_decl.setTypeface(face);
        bench_press_decl_num = (TextView)findViewById(R.id.bench_press_decl_num);
        bench_press_decl_num.setTypeface(face);

        TextView calf_raise =(TextView)findViewById(R.id.calf_raise);
        calf_raise.setTypeface(face);
        calf_raise_num = (TextView)findViewById(R.id.calf_raise_num);
        calf_raise_num.setTypeface(face);

        TextView leg_ext =(TextView)findViewById(R.id.leg_ext);
        leg_ext.setTypeface(face);
        leg_ext_num = (TextView)findViewById(R.id.leg_ext_num);
        leg_ext_num.setTypeface(face);

        TextView leg_press =(TextView)findViewById(R.id.leg_press);
        leg_press.setTypeface(face);
        leg_press_num = (TextView)findViewById(R.id.leg_press_num);
        leg_press_num.setTypeface(face);

        TextView squat =(TextView)findViewById(R.id.squat);
        squat.setTypeface(face);
        squat_num = (TextView)findViewById(R.id.squat_num);
        squat_num.setTypeface(face);

        TextView should_press =(TextView)findViewById(R.id.should_press);
        should_press.setTypeface(face);
        should_press_num = (TextView)findViewById(R.id.should_press_num);
        should_press_num.setTypeface(face);


        uid = getIntent().getStringExtra("UID");

        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


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

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "An error occured, please try again later", Toast.LENGTH_LONG).show();
            }
        });

    }

}
