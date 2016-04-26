package edu.umd.mguenzel.gymqueue;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private Button submit;
    private EditText email, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passString = pass.getText().toString();

                if (emailString.contains("@")) {
                    if (passString.length() > 5) {
                        Firebase ref = new Firebase("https://gymqueue.firebaseio.com/");
                        ref.createUser(emailString, passString, new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> result) {
                                Context context = getApplicationContext();
                                CharSequence text = "You have Successfully Created an Account!";
                                int duration = Toast.LENGTH_SHORT;
                                Toast success = Toast.makeText(context, text, duration);
                                success.show();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);

                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Context context = getApplicationContext();
                                CharSequence text = "There was an Error. Please Try Again";
                                int duration = Toast.LENGTH_SHORT;

                                Toast fail = Toast.makeText(context, text, duration);
                                fail.show();
                            }
                        });
                    }
                }

                //Intent intent = new Intent();
                //intent.putExtra("lat",latString);
                //intent.putExtra("lon",lonString);
                //setResult(RESULT_OK, intent);
                //finish();
            }
        });
    }

}
