package edu.umd.mguenzel.gymqueue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class UserPage extends Activity{
    private int elliptical, rec_bike, stair, stat_bike, treadmill, deadlift, lat_pull, row_mach,
                bench_press_norm, chest_fly, bench_press_incl, bench_press_decl, calf_raise,
                leg_ext, leg_press, squat, should_press;
    private Firebase mFirebase;
    private String uid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://gymqueue.firebaseio.com");

        uid = getIntent().getStringExtra("UID");

        final Button back = (Button) findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                elliptical = (int) dataSnapshot.child("User").child(uid).child("Elliptical").getValue();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Toast.makeText(UserPage.this, "elliptical: " + elliptical, Toast.LENGTH_LONG).show();
    }

}
