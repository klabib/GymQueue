package edu.umd.mguenzel.gymqueue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class UserPage extends Activity{
    private int elliptical, rec_bike, stair, stat_bike, treadmill, deadlift, lat_pull, row_mach,
                bench_press_norm, chest_fly, bench_press_incl, bench_press_decl, calf_raise,
                leg_ext, leg_press, squat, should_press;
    private TextView elliptical_num, rec_bike_num, stair_num, stat_bike_num, treadmill_num, deadlift_num,
            lat_pull_num, row_mach_num, bench_press_norm_num, chest_fly_num, bench_press_incl_num,
            bench_press_decl_num, calf_raise_num, leg_ext_num, leg_press_num, squat_num, should_press_num;
    private Firebase mFirebase;
    private String uid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://gymqueue.firebaseio.com");

        elliptical_num = (TextView)findViewById(R.id.elliptical_num);

        uid = getIntent().getStringExtra("UID");

        final Button back = (Button) findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //cardio
                elliptical = (int) dataSnapshot.child("User").child(uid).child("Elliptical").getValue();
                rec_bike = (int) dataSnapshot.child("User").child(uid).child("Recumbent Bike").getValue();
                stair = (int) dataSnapshot.child("User").child(uid).child("Stair Stepper").getValue();
                stat_bike = (int) dataSnapshot.child("User").child(uid).child("Stationary Bike").getValue();
                treadmill = (int) dataSnapshot.child("User").child(uid).child("Treadmill").getValue();

                //back
                deadlift = (int) dataSnapshot.child("User").child(uid).child("Deadlift").getValue();
                lat_pull = (int) dataSnapshot.child("User").child(uid).child("Lat Pulldown").getValue();
                row_mach = (int) dataSnapshot.child("User").child(uid).child("Row Machine").getValue();

                //chest
                bench_press_norm = (int) dataSnapshot.child("User").child(uid).child("Bench Press").getValue();
                chest_fly = (int) dataSnapshot.child("User").child(uid).child("Chest Flys").getValue();
                bench_press_incl = (int) dataSnapshot.child("User").child(uid).child("Incline Bench Press").getValue();
                bench_press_decl = (int) dataSnapshot.child("User").child(uid).child("Decline Bench Press").getValue();

                //legs
                calf_raise = (int) dataSnapshot.child("User").child(uid).child("Calf Raises").getValue();
                leg_ext = (int) dataSnapshot.child("User").child(uid).child("Leg Extension").getValue();
                leg_press = (int) dataSnapshot.child("User").child(uid).child("Leg Press").getValue();
                squat = (int) dataSnapshot.child("User").child(uid).child("Squat").getValue();

                //shoulders
                should_press = (int) dataSnapshot.child("User").child(uid).child("Shoulder Press").getValue();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "An error occured, please try again later", Toast.LENGTH_LONG).show();
            }
        });

        Toast.makeText(UserPage.this, "elliptical: " + elliptical, Toast.LENGTH_LONG).show();

        elliptical_num.setText(elliptical);
    }

}
