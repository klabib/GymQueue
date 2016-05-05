package edu.umd.mguenzel.gymqueue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;



/**
 * Created by sandrasoltz on 4/21/16.
 */
public class CategorySelect extends AppCompatActivity {

    private String uid;
    private Firebase mFirebase = new Firebase("https://gymqueue.firebaseio.com");


    private static final int MENU_USERPAGE = Menu.FIRST;
    private static final int MENU_LOGOUT = Menu.FIRST + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_select);

        uid = getIntent().getStringExtra("UID");

        Log.i("CATSELECT", "arrived to cat select page UID: " + uid);
        final Button back = (Button) findViewById(R.id.back_button);
        registerForContextMenu(back);

        final Button cardio = (Button) findViewById(R.id.cardio_button);
        registerForContextMenu(cardio);

        final Button chest = (Button) findViewById(R.id.chest_button);
        registerForContextMenu(chest);

        final Button legs = (Button) findViewById(R.id.legs_button);
        registerForContextMenu(legs);

        final Button shoulders = (Button) findViewById(R.id.shoulders_button);
        registerForContextMenu(shoulders);


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
        String machineName = (String) item.getTitle();

        String cat;
        if (machineName.equals("Deadlift") || machineName.equals("Lat Pulldown") || machineName.equals("Row Machine"))
            cat = "Back";
        else if (machineName.equals("Elliptical") || machineName.equals("Recumbent Bike") || machineName.equals("Stair Stepper") ||
                machineName.equals("Stationary Bike") || machineName.equals("Treadmill"))
            cat = "Cardio";
        else if (machineName.equals("Bench Press") || machineName.equals("Chest Flys") || machineName.equals("Decline Bench Press") ||
                machineName.equals("Incline Bench Press"))
            cat = "Chest";
        else if (machineName.equals("Shoulder Press"))
            cat = "Shoulders";
        else
            cat = "Legs";

        Intent intent = new Intent(getApplicationContext(), ReserveMachine.class);
        intent.putExtra("cat", cat);
        intent.putExtra("UID", uid);
        intent.putExtra("machine", machineName);
        startActivityForResult(intent, 1);

        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //Toast.makeText(getApplicationContext(), "You have successfully reserved MACHINE_NAME for TIME", Toast.LENGTH_LONG).show();
            }else {
                Log.i("test", "back button pressed");
            }
        } else {
            Log.i("test", "request code error");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_USERPAGE, Menu.NONE, "User Statistics");
        menu.add(Menu.NONE, MENU_LOGOUT, Menu.NONE, "Logout");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_USERPAGE:
                Intent intent = new Intent(getApplicationContext(), UserPage.class);
                startActivity(intent);
                return true;
            case MENU_LOGOUT:
                mFirebase.unauth();
                setResult(RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
