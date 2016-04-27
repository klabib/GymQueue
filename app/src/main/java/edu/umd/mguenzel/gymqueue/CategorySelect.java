package edu.umd.mguenzel.gymqueue;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.ContextMenu.ContextMenuInfo;

/**
 * Created by sandrasoltz on 4/21/16.
 */
public class CategorySelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_select);

        Log.i("CATSELECT", "arrived to cat select page");
        final Button back = (Button) findViewById(R.id.back_button);
        registerForContextMenu(back);
        final Button cardio = (Button) findViewById(R.id.cardio_button);
        registerForContextMenu(cardio);

        final Button chest = (Button) findViewById(R.id.chest_button);
        registerForContextMenu(chest);
        final Button legs = (Button) findViewById(R.id.legs_button);
        final Button shoulders = (Button) findViewById(R.id.shoulders_button);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        Log.i("CATSELECT", "creating the context menu");
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.cardio_button){
            menu.setHeaderTitle("Cardio Options");
            menu.add(0, v.getId(), 0, "Elliptical");
            menu.add(0, v.getId(), 0, "Recumbent Bike");
            menu.add(0, v.getId(), 0, "Stair Stepper");
            menu.add(0, v.getId(), 0, "Stationary Bike");
            menu.add(0, v.getId(), 0, "Treadmill");
        }

        else if(v.getId() == R.id.back_button){
            menu.setHeaderTitle("Back Options");
            menu.add(0, v.getId(), 0, "Deadlift");
            menu.add(0, v.getId(), 0, "Lat Pulldown");
            menu.add(0, v.getId(), 0, "Row Machine");
        }
        else if(v.getId() == R.id.chest_button){
            menu.setHeaderTitle("Chest Options");
            menu.add(0, v.getId(), 0, "Bench Press");
            menu.add(0, v.getId(), 0, "Chest Flys");
            menu.add(0, v.getId(), 0, "Decline Bench Press");
            menu.add(0, v.getId(), 0, "Incline Bench Press");
        }
        else if(v.getId() == R.id.legs_button){
            menu.setHeaderTitle("Legs Options");
            menu.add(0, v.getId(), 0, "Calf Raises");
            menu.add(0, v.getId(), 0, "Leg Extension");
            menu.add(0, v.getId(), 0, "Leg Press");
            menu.add(0, v.getId(), 0, "Squat");
        }
        else if(v.getId() == R.id.shoulders_button){
            menu.setHeaderTitle("Shoulders Options");
            menu.add(0, v.getId(), 0, "Shoulder Press");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Action 1") {
            Toast.makeText(this, "Action 1 invoked", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle() == "Action 2") {
            Toast.makeText(this, "Action 2 invoked", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle() == "Action 3") {
            Toast.makeText(this, "Action 3 invoked", Toast.LENGTH_SHORT).show();
        } else {
            return false;
        }
        return true;
    }


    private void showCardioMachineOptions() {

    }


}
