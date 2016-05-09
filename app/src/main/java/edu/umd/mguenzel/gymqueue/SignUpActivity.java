package edu.umd.mguenzel.gymqueue;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private Button submit, back;
    private EditText email, pass, passConfirm;
    private Firebase mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView tv=(TextView)findViewById(R.id.textView);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/quicksand_regular.ttf");
        tv.setTypeface(face);


        TextView email_text =(TextView)findViewById(R.id.email_text);
        email_text.setTypeface(face);
        email = (EditText) findViewById(R.id.email);

        TextView password_text =(TextView)findViewById(R.id.password_text);
        password_text.setTypeface(face);
        pass = (EditText) findViewById(R.id.password);

        TextView con_pass_text =(TextView)findViewById(R.id.confirm_text);
        con_pass_text.setTypeface(face);
        passConfirm = (EditText) findViewById(R.id.cpassword);

        submit = (Button) findViewById(R.id.submit);
        submit.setTypeface(face);
        back = (Button) findViewById(R.id.back);
        back.setTypeface(face);
        mFirebase = new Firebase("https://gymqueue.firebaseio.com");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passString = pass.getText().toString();
                String cpassString = passConfirm.getText().toString();

                    if (passString.length() > 5) {
                        if (passString.equals(cpassString)) {
                            Firebase ref = new Firebase("https://gymqueue.firebaseio.com/");
                            ref.createUser(emailString, passString, new Firebase.ValueResultHandler<Map<String, Object>>() {
                                @Override
                                public void onSuccess(Map<String, Object> result) {
                                    setResult(RESULT_OK, getIntent());
                                    finish();
                                }

                                @Override
                                public void onError(FirebaseError firebaseError) {
                                    //TODO: handle firebase error case

                                switch (firebaseError.getCode()) {
                                    case FirebaseError.EMAIL_TAKEN:
                                        // handle a non existing user
                                        Toast.makeText(getApplicationContext(), "Email has been taken", Toast.LENGTH_LONG).show();
                                        break;
                                    case FirebaseError.INVALID_EMAIL:
                                        // handle a non existing user
                                        Toast.makeText(getApplicationContext(), "Email is not valid", Toast.LENGTH_LONG).show();
                                        Log.i("test",""+firebaseError.toString());
                                        break;
                                    default:
                                        // handle other errors
                                        Toast.makeText(getApplicationContext(), "Error: " + firebaseError.getCode(), Toast.LENGTH_LONG).show();
                                        break;
                                }
                                }
                            });
                        } else { //passwords do not match
                            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
                        }
                    } else { //pass too short
                        Toast.makeText(getApplicationContext(), "Password must be at least 6 characters long", Toast.LENGTH_LONG).show();
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
