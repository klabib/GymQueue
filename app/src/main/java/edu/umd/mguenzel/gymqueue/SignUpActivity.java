package edu.umd.mguenzel.gymqueue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private Button submit, back;
    private EditText email, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        back = (Button) findViewById(R.id.back);

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

                if (emailString.contains("@")) {
                    if (passString.length() > 5) {
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
                                /*
                                switch (firebaseError.getCode()) {
                                    case FirebaseError.USER_DOES_NOT_EXIST:
                                        // handle a non existing user
                                        Toast.makeText(getApplicationContext(), "")
                                        break;
                                    case FirebaseError.INVALID_PASSWORD:
                                        // handle an invalid password
                                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                                        mPasswordView.requestFocus();
                                        break;
                                    default:
                                        // handle other errors
                                        Toast.makeText(getApplicationContext(), "Error: " + firebaseError.getCode(), Toast.LENGTH_LONG).show();
                                        break;
                                }*/
                                Toast.makeText(getApplicationContext(), "" + firebaseError, Toast.LENGTH_LONG).show();
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
