package sg.edu.np.cosign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sg.edu.np.cosign.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity{
// implements View.OnTouchListener
    private static final String TAG = "MainActivity.java";
    private TextView tv_NewUser;
    //Context context = this;
    //private Session session;

    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_NewUser = findViewById(R.id.registerText);
        //tv_NewUser.setOnTouchListener(this);
        if((dbHandler.findUser("CoSign") == null) || (dbHandler.findUser("cosign@gmail.com") == null)){
            Log.d(TAG, "onCreate: test account created");
            dbHandler.createTestAccount();
        }

        //session = new Session(context);
    }


/*
    //For new users onTouch listener to launch a new intent page of registration
    public boolean onTouch(View v, MotionEvent event)
    {
        Log.v(TAG, "Touch Start");
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        return true;
    }
*/

    //For Login action nad checking of username and password
    public void onClick(View v)
    {
        final EditText etPassword = findViewById(R.id.passwordText);
        final EditText etUsername = findViewById(R.id.usernameText);
        //final EditText etEmail = findViewById(R.id.usernameText);

        Log.v(TAG, "Login with: " + etUsername.getText().toString() + ", " + etPassword.getText().toString());
        //if(isValidUsername(etUsername.getText().toString()) && isValidPassword(etPassword.getText().toString()))
        if(isValidUser(etUsername.getText().toString(),etPassword.getText().toString()))
        {
            Intent intent = new Intent(MainActivity.this, BottomNavigation.class);
            Toast.makeText(MainActivity.this, "Valid User", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
        else
        {
            Toast.makeText(MainActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
        }
    }

    // A function to check with the database if what they user enter exist, returns true/false, passed in onClick
    public boolean isValidUser(String username, String password)
    {
        UserData dbData = dbHandler.findUser(username);
        Boolean valid = false;
        // added the or statement so that they can log in through both email and username
        try {
            if ((dbData.getMyUsername().equals(username) || (dbData.getMyEmail().equals(username)))) {
                if (dbData.getMyPassword().equals(password)){
                    valid = true;
                }
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            valid = false;
        }
        //Log.d
        return valid;
    }



    public void sendToRegister(View view) {
        Intent goToRegister = new Intent(this, RegisterActivity.class);
        startActivity(goToRegister);

    }


}